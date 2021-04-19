import {createAsyncThunk, createSlice} from '@reduxjs/toolkit'
import {Dispatch} from "react";
import AuthService from "../../services/authService";
import authService from "../../services/authService";
import {setMessage} from '../message/messageSlice';
import {AxiosError} from "axios";
import {RootState} from "../../reducers";

export interface RegistUser {
    name: string
    password: string
    email?: string
}

export interface User {
    userId: { value: string }
    name: { firstName: string, lastName: string }
    password: { value: string }
    roleName: []
}

interface ValidationErrors {
    message: string
}

export const authRegister = createAsyncThunk<any,
    RegistUser,
    {
        rejectValue: ValidationErrors
    }>(
    'auth/register',
    async (user: RegistUser, {rejectWithValue}) => {
        try {
            return await AuthService.register(user.name, user.email, user.password)
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
    { name: string, password: string },
    {
        rejectValue: ValidationErrors
    }>(
    'auth/login',
    async (user: { name: string, password: string }, {rejectWithValue}) => {
        try {
            return await AuthService.login(user.name, user.password)
        } catch (err) {
            let error: AxiosError<ValidationErrors> = err
            if (!error.response) {
                throw err
            }
            return rejectWithValue(error.response.data)
        }
    }
)

export const registerAsync = (user: RegistUser) => (dispatch: Dispatch<any>) => {
    return AuthService.register(user.name, user.email, user.password).then(
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
    session: { token: string, type: string, userId: string, roles: [], user: User } | {}
    user: User | null
    error: string | null | undefined
}

const session = JSON.parse(<string>localStorage.getItem("session"));
const initialState: SliceState = session
    ? {isLoggedIn: true, session, user: null, error: null}
    : {isLoggedIn: false, session: {}, user: null, error: null};

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
            state.session = {}
            state.user = null
        }
    },
    extraReducers: builder => {
        builder.addCase(authRegister.fulfilled, (state,{ payload }) => {
            state.isLoggedIn = false
        })
        builder.addCase(authRegister.rejected, (state, action) => {
            if (action.payload) {
                state.error = action.payload.message
            } else {
                state.error = action.error.message
            }
        })
        builder.addCase(authLogin.fulfilled, (state, action) => {
            state.isLoggedIn = true
            state.user = action.payload.user
        })
        builder.addCase(authLogin.rejected, (state, action) => {
            if (action.payload) {
                state.error = action.payload.message
            } else {
                state.error = action.error.message
            }
            state.user = null
        })
    }
})

export const currentUser = (state: RootState) => state.auth.user

export const {register, logout} = authSlice.actions

export default authSlice.reducer
