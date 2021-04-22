import {createAsyncThunk, createSlice} from '@reduxjs/toolkit'
import {Dispatch} from "react";
import AuthService from "../../services/authService";
import authService from "../../services/authService";
import {setMessage} from '../message/messageSlice';
import {AxiosError} from "axios";
import {RootState} from "../../reducers";

export interface SignupUser {
    id: string
    password: string
    name: {
        first: string
        last: string
    }
    email?: string
}

export interface SigninUser {
    id: string
    password: string
}

export interface User {
    userId: { value: string }
    name: { firstName: string, lastName: string }
    password: { value: string }
    roleName: string
}

interface Session {
    token: string
    type: string
    userId: string
    roles: string[]
    user: User | null
}

interface ValidationErrors {
    message: string
}

export const authSignup = createAsyncThunk<any,
    SignupUser,
    {
        rejectValue: ValidationErrors
    }>(
    'auth/signup',
    async (user: SignupUser, {rejectWithValue}) => {
        try {
            return await AuthService.signup(user.id, user.email, user.password, user.name.first, user.name.last)
        } catch (err) {
            let error: AxiosError<ValidationErrors> = err
            if (!error.response) {
                throw err
            }
            return rejectWithValue(error.response.data)
        }
    }
)

export const authSignin = createAsyncThunk<any,
    SigninUser,
    {
        rejectValue: ValidationErrors
    }>(
    'auth/login',
    async (user: SigninUser, {rejectWithValue}) => {
        try {
            return await AuthService.signin(user.id, user.password)
        } catch (err) {
            let error: AxiosError<ValidationErrors> = err
            if (!error.response) {
                throw err
            }
            return rejectWithValue(error.response.data)
        }
    }
)

export const signupAsync = (user: SignupUser) => (dispatch: Dispatch<any>) => {
    return AuthService.signup(user.id, user.email, user.password, user.name.first, user.name.last).then(
        (response) => {
            dispatch(signup(user));
            dispatch(setMessage(response.data.message));
            return Promise.resolve();
        },
        (error) => {
            const message =
                (error.response &&
                    error.response.data &&
                    error.response.data.message) ||
                error.message ||
                error.toString();
            dispatch(signup(user));
            dispatch(setMessage(message));
            return Promise.reject();
        }
    );
}

export type SliceState = {
    isLoggedIn: boolean
    session: Session
    error: string | null | undefined
}

const defaultSession = {token: '', type: '', userId: '', roles: [], user: null};
const session = JSON.parse(<string>localStorage.getItem("session"));
const initialState: SliceState = session
    ? {isLoggedIn: true, session, error: null}
    : {isLoggedIn: false, session: defaultSession, error: null};

export const authSlice = createSlice({
    name: 'auth',
    initialState: initialState,
    reducers: {
        signup: (state, action) => {
            state.isLoggedIn = false
        },
        signout: state => {
            authService.signout()
            state.isLoggedIn = false
            state.session = defaultSession
        }
    },
    extraReducers: builder => {
        builder.addCase(authSignup.fulfilled, (state, {payload}) => {
            state.isLoggedIn = false
        })
        builder.addCase(authSignup.rejected, (state, action) => {
            if (action.payload) {
                state.error = action.payload.message
            } else {
                state.error = action.error.message
            }
        })
        builder.addCase(authSignin.fulfilled, (state, action) => {
            state.isLoggedIn = true
            state.session = action.payload
        })
        builder.addCase(authSignin.rejected, (state, action) => {
            if (action.payload) {
                state.error = action.payload.message
            } else {
                state.error = action.error.message
            }
        })
    }
})

export const currentUser = (state: RootState) => state.auth.session.user

export const {signup, signout} = authSlice.actions

export default authSlice.reducer
