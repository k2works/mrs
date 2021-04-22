import {Dispatch} from "@reduxjs/toolkit";
import {clearMessage} from "../features/message/messageSlice";

export const initialContainer = (dispatch: Dispatch<any>) => {
    dispatch(clearMessage())
}
