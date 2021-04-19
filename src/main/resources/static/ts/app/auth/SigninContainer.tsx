import React, {useEffect} from 'react';

import Signin from '../../components/auth/SigninComponent';
import {clearMessage} from "../../features/message/messageSlice";
import {useDispatch} from "react-redux";

const SigninContainer = () => {
    const dispatch = useDispatch();
    useEffect(() => {
        console.log('SigninContainer:useEffectによる初回処理');
        dispatch(clearMessage())
    }, []);
    return (
        <div>
            <Signin/>
        </div>
    );
};

export default SigninContainer;
