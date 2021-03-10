import React, { useEffect } from 'react';

import Reservation from "../../components/reservation/ReservationComponent";
import {useLocation} from "react-router-dom";
import Sample1 from "../../components/sample/Sample1Component";
import Sample2 from "../../components/sample/Sample2Component";
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

    if (targetPage === '2021-03-09-1') {
        return (
            <div>
                <Reservation />
            </div>
        );
    }
    if (targetPage === '2021-03-09-7') {
        return (
            <div>
                <Reservation />
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
