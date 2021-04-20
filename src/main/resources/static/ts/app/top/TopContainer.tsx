import React, {useEffect} from 'react';

import Top from '../../components/top/TopComponent';
import {initialContainer} from "../../utils/containerUtils";
import {useDispatch} from "react-redux";

const TopContainer = () => {
    const dispatch = useDispatch();
    useEffect(() => {
        console.log('TopContainer:useEffectによる初回処理');
        initialContainer(dispatch);
    }, []);
    return (
        <div>
            <Top/>
        </div>
    );
};

export default TopContainer;
