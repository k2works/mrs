import React, {useEffect} from 'react';

import Reservation from "../../components/reservation/ReservationComponent";
import {useLocation} from "react-router-dom";
import NotFound from "../NotFound";

const useQuery = () => {
    const location = useLocation();
    return new URLSearchParams(location.search);
};

const ReservationContainer = () => {
    useEffect(() => {
        console.log('ReservationContainer:useEffectによる初回処理');
    }, []);
    const query = useQuery();
    const targetPage = query.get('page');

    if (targetPage) {
        return (
            <div>
                <Reservation/>
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
