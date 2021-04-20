import React, {useEffect, useState} from 'react';

import Reservation from "../../components/reservation/ReservationComponent";
import {useLocation} from "react-router-dom";
import NotFound from "../NotFound";
import {selectMessage, setMessage} from "../../features/message/messageSlice";
import {useDispatch, useSelector} from "react-redux";
import {reservationList, reservationState} from "../../features/reservation/reservationSlice";
import {Dispatch} from "@reduxjs/toolkit";
import {initialContainer} from "../../utils/containerUtils";

const useQuery = () => {
    const location = useLocation();
    return new URLSearchParams(location.search);
};

export const listReservations = async (setSuccessful: (value: (((prevState: boolean) => boolean) | boolean)) => void, dispatch: Dispatch<any>, state: any) => {
    setSuccessful(false);

    const resultAction: any = await dispatch(reservationList({date: state.reservedDate, roomId: state.roomId}))
    if (reservationList.fulfilled.match(resultAction)) {
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

const ReservationContainer = () => {
    const dispatch = useDispatch();
    const [successful, setSuccessful] = useState(false);
    const state = useSelector(reservationState)
    useEffect(() => {
        console.log('ReservationContainer:useEffectによる初回処理');
        initialContainer(dispatch);
        list()
    }, []);

    const list = async () => {
        await listReservations(setSuccessful, dispatch, state);
    }

    const query = useQuery();
    const targetPage = query.get('page');
    const {message} = useSelector(selectMessage)

    if (targetPage) {
        return (
            <div>
                {successful ? (<Reservation/>) : (message)}
            </div>
        );
    }
    return (
        <div>
            <NotFound />
        </div>
    );
};

export default ReservationContainer;
