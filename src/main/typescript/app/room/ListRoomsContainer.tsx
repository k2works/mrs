import React, {useEffect, useState} from 'react';
import ListRooms from '../../components/room/ListRoomsComponent';
import {selectMessage, setMessage} from "../../features/message/messageSlice";
import {roomList} from "../../features/room/roomSlice";
import {useDispatch, useSelector} from "react-redux";
import {Dispatch} from "@reduxjs/toolkit";
import {initialContainer} from "../../utils/containerUtils";
import {currentUser} from "../../features/auth/authSlice";
import {Redirect} from "react-router-dom";

export const listRooms = async (setSuccessful: (value: (((prevState: boolean) => boolean) | boolean)) => void, dispatch: Dispatch<any>) => {
    setSuccessful(false);

    const resultAction: any = await dispatch(roomList(new Date()))
    if (roomList.fulfilled.match(resultAction)) {
        dispatch(setMessage(resultAction.payload.message))
        setSuccessful(true);
    } else {
        if (resultAction.payload) {
            dispatch(setMessage(resultAction.payload.message))
        } else {
            dispatch(setMessage(resultAction.error.message))
        }
        setSuccessful(false);
    }
}

const ListRoomsContainer = () => {
    const dispatch = useDispatch();
    const [successful, setSuccessful] = useState(false);

    useEffect(() => {
        console.log('ListRoomsContainer:useEffectによる初回処理');
        initialContainer(dispatch);
        list()
    }, []);

    const list = async () => {
        await listRooms(setSuccessful, dispatch);
    }

    const {message} = useSelector(selectMessage)

    const user = useSelector(currentUser);
    if (!user) {
        return <Redirect to="/signin"/>;
    }
    return (
        <div>
            {successful ? (<ListRooms user={user}/>) : (message)}
        </div>
    )
}

export default ListRoomsContainer;
