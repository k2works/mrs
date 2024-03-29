import React, {useRef, useState} from "react";
import {useDispatch, useSelector} from "react-redux";
import {useHistory} from 'react-router';
import {selectMessage} from "../../features/message/messageSlice";
import {signin} from "../../app/auth/SigninContainer";
import Form from "react-validation/build/form";
import Input from "react-validation/build/input";
import CheckButton from "react-validation/build/button";

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
            await signin(dispatch, username, password, setSuccessful, history);
        }
    };

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
                                    id={"username"}
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
                                    id={"password"}
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
                                    <button className={"btn btn-primary"} id={"login"} onClick={handleLogin}
                                            type="submit">ログイン
                                    </button>
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
