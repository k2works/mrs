import React, {useRef, useState} from "react";
import {useDispatch, useSelector} from "react-redux";
import {useHistory} from 'react-router';

import Form from "react-validation/build/form";
import Input from "react-validation/build/input";
import CheckButton from "react-validation/build/button";

import {authSignin, currentUser} from "../../features/auth/authSlice";
import {selectMessage, setMessage} from "../../features/message/messageSlice";
import {Redirect} from "react-router-dom";

const required = (value: any) => {
    if (!value) {
        return (
            <div className="alert alert-danger" role="alert">
                This field is required!
            </div>
        );
    }
};

const vusername = (value: any) => {
    if (value.length < 3 || value.length > 20) {
        return (
            <div className="alert alert-danger" role="alert">
                The username must be between 3 and 20 characters.
            </div>
        );
    }
};

const vpassword = (value: any) => {
    if (value.length < 4 || value.length > 40) {
        return (
            <div className="alert alert-danger" role="alert">
                The password must be between 4 and 40 characters.
            </div>
        );
    }
};

const Signin = () => {
    const history = useHistory();

    const form = useRef();
    const checkBtn = useRef();

    const [username, setUsername] = useState("aaaa");
    const [password, setPassword] = useState("demo");
    const [successful, setSuccessful] = useState(false);

    const {message} = useSelector(selectMessage);
    const dispatch = useDispatch();

    const onChangeUsername = (e: any) => {
        const username = e.target.value;
        setUsername(username);
    };

    const onChangePassword = (e: any) => {
        const password = e.target.value;
        setPassword(password);
    };

    const handleLogin = async (e: any) => {
        e.preventDefault();

        setSuccessful(false);

        // @ts-ignore
        form.current.validateAll();

        // @ts-ignore
        if (checkBtn.current.context._errors.length === 0) {
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
    };

    const user = useSelector(currentUser);
    if (user) {
        return <Redirect to="/"/>;
    }

    return (
        <div>
            <main>
                <h2>ログイン</h2>

                {message && (
                    <div>
                        <div className={successful ? "alert alert-success" : "alert alert-danger"} role="alert">
                            {message}
                        </div>
                    </div>
                )}

                <Form onSubmit={handleLogin} ref={form}>
                    {!successful && (
                        <table>
                            <tr>
                                <td><label htmlFor="username">User</label></td>
                                <td><Input
                                    type="text"
                                    className=""
                                    name="username"
                                    value={username}
                                    onChange={onChangeUsername}
                                    validations={[required, vusername]}
                                /></td>
                            </tr>
                            <tr>
                                <td><label htmlFor="password">Password</label></td>
                                <td><Input
                                    type="password"
                                    className=""
                                    name="password"
                                    value={password}
                                    onChange={onChangePassword}
                                    validations={[required, vpassword]}
                                /></td>
                            </tr>
                            <tr>
                                <td>&nbsp;</td>
                                <td>
                                    <button id={"login"} onClick={handleLogin} type="submit">ログイン</button>
                                </td>
                            </tr>
                        </table>
                    )}

                    <CheckButton style={{display: "none"}} ref={checkBtn}/>
                </Form>
            </main>
        </div>
    );
};

export default Signin;
