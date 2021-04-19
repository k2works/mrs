import React from 'react';
import {useHistory} from "react-router";
import {useDispatch, useSelector} from "react-redux";
import {currentUser, signout} from "../../features/auth/authSlice";

const NavComponent = () => {
    const history = useHistory();
    const dispatch = useDispatch();
    const user = useSelector(currentUser);

    const handleOnClickLogout = () => {
        dispatch(signout())
        history.push('/signin');
    };

    return (
        <div className={"nav"}>
            <nav>
                <h3>メニュー</h3>
                <ul>
                    {user && (
                        <div>
                            <li><a onClick={() => history.push('/')} id="home">ホーム</a></li>
                            <li><a onClick={() => history.push('/rooms')} id="rooms">会議室一覧</a></li>
                            <li><a onClick={handleOnClickLogout}>ログアウト</a></li>
                        </div>
                    )}
                    {!user && (
                        <div>
                            <li><a onClick={() => history.push('/signin')} id="signin">ログイン</a></li>
                            <li><a onClick={() => history.push('/signup')} id="signup">利用者登録</a></li>
                        </div>
                    )}
                </ul>
            </nav>
        </div>
    );
};

export default NavComponent;
