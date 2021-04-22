import React, {useEffect} from 'react';
import {useDispatch} from "react-redux";

import Signup from '../../components/auth/SignupComponent';
import {initialContainer} from "../../utils/containerUtils";
import {Dispatch} from "@reduxjs/toolkit";
import {authSignup} from "../../features/auth/authSlice";
import {setMessage} from "../../features/message/messageSlice";

export const signup = async (dispatch: Dispatch<any>, userid: string, password: string, email: string, usernameFirst: string, usernameLast: string, setSuccessful: (value: (((prevState: boolean) => boolean) | boolean)) => void) => {
    const resultAction: any = await dispatch(authSignup({
        id: userid,
        password,
        email,
        name: {first: usernameFirst, last: usernameLast}
    }))
    if (authSignup.fulfilled.match(resultAction)) {
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

const SignupContainer = () => {
    const dispatch = useDispatch();
    useEffect(() => {
        console.log('SignupContainer:useEffectによる初回処理');
        initialContainer(dispatch);
    }, []);
    return (
        <div>
            <Signup/>
        </div>
    );
};

export default SignupContainer;
