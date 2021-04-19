import React, {useEffect} from 'react';
import {useDispatch} from "react-redux";

import Signup from '../../components/auth/SignupComponent';
import {clearMessage} from "../../features/message/messageSlice";

const SignupContainer = () => {
    const dispatch = useDispatch();
    useEffect(() => {
        console.log('SignupContainer:useEffectによる初回処理');
        dispatch(clearMessage())
    }, []);
    return (
        <div>
            <Signup/>
        </div>
    );
};

export default SignupContainer;
