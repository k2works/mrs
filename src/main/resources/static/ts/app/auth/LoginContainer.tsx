import React, {useEffect} from 'react';

import Login from '../../components/auth/LoginComponent';
import {clearMessage} from "../../features/message/messageSlice";
import {useDispatch} from "react-redux";

const LoginContainer = () => {
    const dispatch = useDispatch();
    useEffect(() => {
        console.log('LoginContainer:useEffectによる初回処理');
        dispatch(clearMessage())
    }, []);
    return (
        <div>
            <Login/>
        </div>
    );
};

export default LoginContainer;
