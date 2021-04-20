import React, {useEffect} from 'react';

import Signin from '../../components/auth/SigninComponent';
import {useDispatch} from "react-redux";
import {initialContainer} from "../../utils/containerUtils";

const SigninContainer = () => {
    const dispatch = useDispatch();
    useEffect(() => {
        console.log('SigninContainer:useEffectによる初回処理');
        initialContainer(dispatch);
    }, []);
    return (
        <div>
            <Signin/>
        </div>
    );
};

export default SigninContainer;
