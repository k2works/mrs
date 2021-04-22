import {createAsyncThunk, createSlice} from "@reduxjs/toolkit";
import RoomService from "../../services/roomService";
import {AxiosError} from "axios";
import {RootState} from "../../reducers";

interface ValidationErrors {
    message: string
}

export const roomList = createAsyncThunk<any,
    Date,
    {
        rejectValue: ValidationErrors
    }>(
    'room/list',
    async (reservedDate: Date, {rejectWithValue}) => {
        try {
            return await RoomService.list(reservedDate)
        } catch (err) {
            let error: AxiosError<ValidationErrors> = err
            if (!error.response) {
                throw err
            }
            return rejectWithValue(error.response.data)
        }
    }
)

interface ReservableRoom {
    meetingRoom: {
        roomId: { value: number }
        roomName: { value: string }
    }
    reservableRoomId: {
        reservedDate: { value: number }
        roomId: { value: string }
    }
}

interface ReservableRooms {
    value: ReservableRoom[]
}

export type SliceState = {
    reservedDate: Date
    reservableRooms: ReservableRooms
    error: string | null | undefined
}

const preDay = (date: Date) => {
    return new Date(date.setDate(date.getDate() - 1))
}

const nextDay = (date: Date) => {
    return new Date(date.setDate(date.getDate() + 1))
}

const initialState: SliceState = {
    reservedDate: new Date(),
    reservableRooms: {value: []},
    error: null
}

export const roomSlice = createSlice({
    name: 'room',
    initialState: initialState,
    reducers: {
        incrementReservedDate: (state, action) => {
            state.reservedDate = nextDay(state.reservedDate)
        },
        decrementReservedDate: (state, action) => {
            state.reservedDate = preDay(state.reservedDate)
        }
    },
    extraReducers: builder => {
        builder.addCase(roomList.fulfilled, (state, {payload}) => {
            state.reservableRooms = payload.data
        })
        builder.addCase(roomList.rejected, (state, action) => {
            if (action.payload) {
                state.error = action.payload.message
            } else {
                state.error = action.error.message
            }
        })
    }
})

export const roomState = (state: RootState) => state.room
export const currentReservedDate = (state: RootState) => state.room.reservedDate.toLocaleDateString('ja')

export const {incrementReservedDate, decrementReservedDate} = roomSlice.actions

export default roomSlice.reducer
