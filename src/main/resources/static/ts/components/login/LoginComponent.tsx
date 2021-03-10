import React from 'react';
import { useHistory } from 'react-router';

const LoginComponent = () => {
    const history = useHistory();

    const handleOnClickLogin = () => {
        alert('login clicked');
        history.push('/');
    };

    return (
        <div>
            <main>
                <h2>ログインフォーム</h2>
                <p>
                    Error!
                </p>
                <form method="POST">
                    <table>
                        <tr>
                            <td><label htmlFor="username">User:</label></td>
                            <input id="username" name="username" type="text" value="aaaa"/>
                        </tr>
                        <tr>
                            <td><label htmlFor="password">Password:</label></td>
                            <td><input id="password" name="password" type="password" value="demo"/></td>
                        </tr>
                        <tr>
                            <td>&nbsp;</td>
                            <td>
                                <button onClick={handleOnClickLogin} type="submit">ログイン</button>
                            </td>
                        </tr>
                    </table>
                </form>
            </main>
        </div>
    );
};

export default LoginComponent;
