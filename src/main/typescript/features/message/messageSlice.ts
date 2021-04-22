import {createSlice} from "@reduxjs/toolkit";
import {RootState} from "../../reducers";

type SliceState = {
    message: string
}

const initialState: SliceState = { message: "" }

export const messageSlice = createSlice({
    name: 'message',
    initialState,
    reducers: {
        setMessage: (state, action ) => { state.message = action.payload } ,
        clearMessage: state  => { state.message = "" }
    }
})

export const {setMessage, clearMessage} = messageSlice.actions

export const selectMessage = (state: RootState) => state.message

export default messageSlice.reducer
