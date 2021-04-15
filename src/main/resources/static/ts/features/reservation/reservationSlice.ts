import {createAsyncThunk, createSlice} from "@reduxjs/toolkit";
import ReservationService from "../../services/reservationService"
import {AxiosError} from "axios";
import {RootState} from "../../reducers";

interface ValidationErrors {
    message: string
}

export const reservationList = createAsyncThunk<any,
    {
        date: string,
        roomId: number
    },
    {
        rejectValue: ValidationErrors
    }>(
    'reservation/list',
    async (params: { date: string, roomId: number }, {rejectWithValue}) => {
        try {
            return await ReservationService.list(params)
        } catch (err) {
            let error: AxiosError<ValidationErrors> = err
            if (!error.response) {
                throw err
            }
            return rejectWithValue(error.response.data)
        }
    }
)

interface Reservation {
    reservableRoom: {
        meetingRoom: {
            roomId: { value: number }
            roomName: { value: string }
        }
        reservableRoomId: {
            reservedDate: { value: string }
            roomId: { value: number }
        }
    }
    reservationId: {
        value: number
    }
    reservedDateTime: {
        date: { value: string }
        time: {
            start: string
            end: string
        }
    }
    user: {
        name: {
            firstName: string
            lastName: string
        }
        password: {
            value: string
        }
        roleName: []
        userId: { value: string }
    }
}

interface Reservations {
    value: Reservation[]
}

export type SliceState = {
    reservedDate: Date
    roomId: number | null
    reservations: Reservations
    error: string | null | undefined
}

const initialState: SliceState = {
    reservedDate: new Date(),
    roomId: null,
    reservations: {value: []},
    error: null
}

export const reservationSlice = createSlice({
    name: 'reservation',
    initialState: initialState,
    reducers: {},
    extraReducers: builder => {
        builder.addCase(reservationList.fulfilled, (state, {payload}) => {
            state.reservations = payload.data
        })
        builder.addCase(reservationList.rejected, (state, action) => {
            if (action.payload) {
                state.error = action.payload.message
            } else {
                state.error = action.error.message
            }
        })
    }
})

export const reservationState = (state: RootState) => state.reservation

export default reservationSlice.reducer
