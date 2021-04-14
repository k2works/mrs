import React, {useEffect, useState} from 'react';
import ListRooms from '../../components/room/ListRoomsComponent';
import {clearMessage, selectMessage, setMessage} from "../../features/message/messageSlice";
import {roomList} from "../../features/room/roomSlice";
import {useDispatch, useSelector} from "react-redux";

const ListRoomsContainer = () => {
    const dispatch = useDispatch();
    const [successful, setSuccessful] = useState(false);

    useEffect(() => {
        console.log('ListRoomsContainer:useEffectによる初回処理');
        dispatch(clearMessage())
        list()
    }, []);

    const list = async () => {
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

    const {message} = useSelector(selectMessage)
    return (
        <div>
            {successful ? (<ListRooms/>) : (message)}
        </div>
    )
}

export default ListRoomsContainer;
