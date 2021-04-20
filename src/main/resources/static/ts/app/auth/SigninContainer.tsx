import React, {useEffect} from 'react';

import Signin from '../../components/auth/SigninComponent';
import {useDispatch, useSelector} from "react-redux";
import {initialContainer} from "../../utils/containerUtils";
import {Dispatch} from "@reduxjs/toolkit";
import {authSignin, currentUser} from "../../features/auth/authSlice";
import {setMessage} from "../../features/message/messageSlice";
import {Redirect} from "react-router-dom";

export const signin = async (dispatch: Dispatch<any>, username: string, password: string, setSuccessful: (value: (((prevState: boolean) => boolean) | boolean)) => void, history: any) => {
    const resultAction: any = await dispatch(authSignin({id: username, password}))
    if (authSignin.fulfilled.match(resultAction)) {
        dispatch(setMessage(resultAction.payload.message))
        setSuccessful(true);
        history.push('/');
    } else {
        if (resultAction.payload) {
            dispatch(setMessage(resultAction.payload.message))
        } else {
            dispatch(setMessage(resultAction.error.message))
        }
        setSuccessful(false);
    }
}

const SigninContainer = () => {
    const dispatch = useDispatch();
    useEffect(() => {
        console.log('SigninContainer:useEffectによる初回処理');
        initialContainer(dispatch);
    }, []);

    const user = useSelector(currentUser);
    if (user) {
        return <Redirect to="/"/>;
    }

    return (
        <div>
            <Signin/>
        </div>
    );
};

export default SigninContainer;
