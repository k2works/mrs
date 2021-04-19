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

export interface LoginUser {
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

export const authLogin = createAsyncThunk<any,
    LoginUser,
    {
        rejectValue: ValidationErrors
    }>(
    'auth/login',
    async (user: LoginUser, {rejectWithValue}) => {
        try {
            return await AuthService.login(user.id, user.password)
        } catch (err) {
            let error: AxiosError<ValidationErrors> = err
            if (!error.response) {
                throw err
            }
            return rejectWithValue(error.response.data)
        }
    }
)

export const registerAsync = (user: SignupUser) => (dispatch: Dispatch<any>) => {
    return AuthService.signup(user.id, user.email, user.password, user.name.first, user.name.last).then(
        (response) => {
            dispatch(register(user));
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
            dispatch(register(user));
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
        register: (state, action) => {
            state.isLoggedIn = false
        },
        logout: state => {
            authService.logout()
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
        builder.addCase(authLogin.fulfilled, (state, action) => {
            state.isLoggedIn = true
            state.session = action.payload
        })
        builder.addCase(authLogin.rejected, (state, action) => {
            if (action.payload) {
                state.error = action.payload.message
            } else {
                state.error = action.error.message
            }
        })
    }
})

export const currentUser = (state: RootState) => state.auth.session.user

export const {register, logout} = authSlice.actions

export default authSlice.reducer
