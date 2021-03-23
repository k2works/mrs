import React, { useEffect } from 'react';
import { useDispatch, useSelector } from "react-redux";

import Register from '../../components/auth/RegisterComponent';
import {clearMessage} from "../../features/message/messageSlice";

const RegisterContainer = () => {
    const dispatch = useDispatch();
    useEffect(() => {
        console.log('RegisterContainer:useEffectによる初回処理');
        dispatch(clearMessage())
    }, []);
    return (
        <div>
            <Register />
        </div>
    );
};

export default RegisterContainer;
