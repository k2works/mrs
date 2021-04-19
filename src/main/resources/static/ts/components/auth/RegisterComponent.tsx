import React, {useRef, useState} from "react";
import {useDispatch, useSelector} from "react-redux";

import Form from "react-validation/build/form";
import Input from "react-validation/build/input";
import CheckButton from "react-validation/build/button";
import validator from "validator";

import {authRegister} from "../../features/auth/authSlice";
import {selectMessage, setMessage} from "../../features/message/messageSlice";

const required = (value: any) => {
    if (!value) {
        return (
            <div className="alert alert-danger" role="alert">
                This field is required!
            </div>
        );
    }
};

const validEmail = (value: any) => {
    if (!validator.isEmail(value)) {
        return (
            <div className="alert alert-danger" role="alert">
                This is not a valid email.
            </div>
        );
    }
};

const vuserid = (value: any) => {
    if (value.length < 4 || value.length > 20) {
        return (
            <div className="alert alert-danger" role="alert">
                The userid must be between 4 and 20 characters.
            </div>
        );
    }
};

const vusername = (value: any) => {
    if (value.length < 1 || value.length > 20) {
        return (
            <div className="alert alert-danger" role="alert">
                The username must be between 1 and 20 characters.
            </div>
        );
    }
};

const vpassword = (value: any) => {
    if (value.length < 6 || value.length > 40) {
        return (
            <div className="alert alert-danger" role="alert">
                The password must be between 6 and 40 characters.
            </div>
        );
    }
};

const Register = () => {
    const form = useRef();
    const checkBtn = useRef();

    const [userid, setUserid] = useState("");
    const [usernameFirst, setUsernameFirst] = useState("");
    const [usernameLast, setUsernameLast] = useState("");
    const [email, setEmail] = useState("");
    const [password, setPassword] = useState("");
    const [successful, setSuccessful] = useState(false);

    const {message} = useSelector(selectMessage);
    const dispatch = useDispatch();

    const onChangeUserid = (e: any) => {
        const userid = e.target.value;
        setUserid(userid);
    };

    const onChangeUsernameFirst = (e: any) => {
        const username = e.target.value;
        setUsernameFirst(username);
    };

    const onChangeUsernameLast = (e: any) => {
        const username = e.target.value;
        setUsernameLast(username);
    };

    const onChangeEmail = (e: any) => {
        const email = e.target.value;
        setEmail(email);
    };

    const onChangePassword = (e: any) => {
        const password = e.target.value;
        setPassword(password);
    };

    const handleRegister = async (e: any) => {
        e.preventDefault();

        setSuccessful(false);

        // @ts-ignore
        form.current.validateAll();

        // @ts-ignore
        if (checkBtn.current.context._errors.length === 0) {
            const resultAction: any = await dispatch(authRegister({
                id: userid,
                password,
                email,
                name: {first: usernameFirst, last: usernameLast}
            }))
            if (authRegister.fulfilled.match(resultAction)) {
                dispatch(setMessage(resultAction.payload.message))
                setSuccessful(true);
            } else {
                if(resultAction.payload) {
                    dispatch(setMessage(resultAction.payload.message))
                } else {
                    dispatch(setMessage(resultAction.error.message))
                }
                setSuccessful(false);
            }
        }
    };

    return (
        <div>
            <div>
                <Form onSubmit={handleRegister} ref={form}>
                    {!successful && (
                        <div>
                            <div>
                                <label htmlFor="userid">ID</label>
                                <Input
                                    type="text"
                                    className=""
                                    name="userid"
                                    value={userid}
                                    onChange={onChangeUserid}
                                    validations={[required, vuserid]}
                                />
                            </div>

                            <div>
                                <label htmlFor="usernamefirst">姓</label>
                                <Input
                                    type="text"
                                    className=""
                                    name="usernamefirst"
                                    value={usernameFirst}
                                    onChange={onChangeUsernameFirst}
                                    validations={[required, vusername]}
                                />
                            </div>

                            <div>
                                <label htmlFor="usernamelast">名</label>
                                <Input
                                    type="text"
                                    className=""
                                    name="usernamelast"
                                    value={usernameLast}
                                    onChange={onChangeUsernameLast}
                                    validations={[required, vusername]}
                                />
                            </div>

                            <div>
                                <label htmlFor="email">Email</label>
                                <Input
                                    type="text"
                                    className=""
                                    name="email"
                                    value={email}
                                    onChange={onChangeEmail}
                                    validations={[required, validEmail]}
                                />
                            </div>

                            <div className="form-group">
                                <label htmlFor="password">パスワード</label>
                                <Input
                                    type="password"
                                    className=""
                                    name="password"
                                    value={password}
                                    onChange={onChangePassword}
                                    validations={[required, vpassword]}
                                />
                            </div>

                            <div>
                                <button>登録</button>
                            </div>
                        </div>
                    )}

                    {message && (
                        <div>
                            <div className={ successful ? "alert alert-success" : "alert alert-danger" } role="alert">
                                {message}
                            </div>
                        </div>
                    )}
                    <CheckButton style={{ display: "none" }} ref={checkBtn} />
                </Form>
            </div>
        </div>
    );
};

export default Register;

