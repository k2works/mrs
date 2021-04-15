import React, {useEffect, useState} from 'react';

import Reservation from "../../components/reservation/ReservationComponent";
import {useLocation} from "react-router-dom";
import NotFound from "../NotFound";
import {clearMessage, selectMessage, setMessage} from "../../features/message/messageSlice";
import {useDispatch, useSelector} from "react-redux";
import {reservationList, reservationState} from "../../features/reservation/reservationSlice";

const useQuery = () => {
    const location = useLocation();
    return new URLSearchParams(location.search);
};

const ReservationContainer = () => {
    const dispatch = useDispatch();
    const [successful, setSuccessful] = useState(false);
    const state = useSelector(reservationState)
    useEffect(() => {
        console.log('ReservationContainer:useEffectによる初回処理');
        dispatch(clearMessage())
        list()
    }, []);

    const list = async () => {
        setSuccessful(false);


        const resultAction: any = await dispatch(reservationList({date: state.reservedDate, roomId: state.roomId}))
        if (reservationList.fulfilled.match(resultAction)) {
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
