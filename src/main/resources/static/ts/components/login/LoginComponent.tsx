import React from 'react';
import { useHistory } from 'react-router';

const LoginComponent = () => {
    const history = useHistory();

    const handleOnClickLogin = () => {
        alert('login clicked');
        history.push('/');
    };

    return (
        <div style={{ width: '100%' }}>
            <div className="centerTable">
                <h2>
                    ログインページ
                </h2>
                <a onClick={handleOnClickLogin}>ログイン</a>
            </div>
        </div>
    );
};

export default LoginComponent;
