import React, { useEffect } from 'react';

import Login from '../../components/login/LoginComponent';

const LoginContainer = () => {
    useEffect(() => {
        console.log('LoginContainer:useEffectによる初回処理');
    }, []);
    return (
        <div>
            <Login />
        </div>
    );
};

export default LoginContainer;
