import React, {useEffect} from 'react';
import {useDispatch} from "react-redux";

import Signup from '../../components/auth/SignupComponent';
import {initialContainer} from "../../utils/containerUtils";

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
