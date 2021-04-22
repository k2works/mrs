import React, {useEffect} from 'react';

import Top from '../../components/top/TopComponent';
import {initialContainer} from "../../utils/containerUtils";
import {useDispatch, useSelector} from "react-redux";
import {currentUser} from "../../features/auth/authSlice";
import {Redirect} from "react-router-dom";

const TopContainer = () => {
    const dispatch = useDispatch();
    useEffect(() => {
        console.log('TopContainer:useEffectによる初回処理');
        initialContainer(dispatch);
    }, []);

    const user = useSelector(currentUser);
    if (!user) {
        return <Redirect to="/signin"/>;
    }

    return (
        <div>
            <Top user={user}/>
        </div>
    );
};

export default TopContainer;
