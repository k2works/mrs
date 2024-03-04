import React, {useEffect, useState} from 'react';

import Reservation from "../../components/reservation/ReservationComponent";
import {Redirect, useLocation} from "react-router-dom";
import NotFound from "../NotFound";
import {selectMessage, setMessage} from "../../features/message/messageSlice";
import {useDispatch, useSelector} from "react-redux";
import {
    reservationCancel,
    reservationList,
    reservationReserve,
    reservationState
} from "../../features/reservation/reservationSlice";
import {Dispatch} from "@reduxjs/toolkit";
import {initialContainer} from "../../utils/containerUtils";
import {currentUser} from "../../features/auth/authSlice";

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

export const reserve = async (dispatch: Dispatch<any>, params: { date: Date; start: string; end: string; userid: string; roomId: any }, setSuccessful: (value: (((prevState: boolean) => boolean) | boolean)) => void, state: any) => {
    const resultAction: any = await dispatch(reservationReserve(params))
    if (reservationReserve.fulfilled.match(resultAction)) {
        dispatch(setMessage(resultAction.payload.message))
        setSuccessful(true);
        await listReservations(setSuccessful, dispatch, state);
        dispatch(setMessage('会議室を予約しました。'))
    } else {
        if (resultAction.payload) {
            dispatch(setMessage(resultAction.payload.message))
        } else {
            dispatch(setMessage(resultAction.error.message))
        }
        setSuccessful(false);
    }
}

export const cancel = async (dispatch: Dispatch<any>, params: { date: Date; reservationId: number; roomId: any; username: string }, setSuccessful: (value: (((prevState: boolean) => boolean) | boolean)) => void, state: any) => {
    const resultAction: any = await dispatch(reservationCancel(params))
    if (reservationCancel.fulfilled.match(resultAction)) {
        dispatch(setMessage(resultAction.payload.message))
        setSuccessful(true);
        await listReservations(setSuccessful, dispatch, state);
        dispatch(setMessage('会議室の予約をキャンセルしました。'))
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

    let user = useSelector(currentUser);
    if (!user) {
      user = {
        userId: { value: "aaaa" },
        name: { firstName: "DUMMY", lastName: "DUMMY" },
        password: { value: "DUMMY" },
        roleName: "USER",
      };
    }

    if (targetPage) {
        return (
            <div>
                {successful ? (<Reservation user={user}/>) : (message)}
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
