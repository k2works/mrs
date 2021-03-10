import React from 'react';
import {useHistory} from "react-router";

const NavComponent = () => {
    const history = useHistory();

    const handleOnClickLogout = () => {
        alert('logout clicked');
        history.push('/login');
    };

    return (
        <div className={"nav"}>
            <nav>
                <h3>メニュー</h3>
                <ul>
                    <li><a onClick={() => history.push('/')} id="home">ホーム</a></li>
                    <li><a onClick={() => history.push('/rooms')} id="rooms">会議室一覧</a></li>
                    <li><a onClick={handleOnClickLogout}>ログアウト</a></li>
                </ul>
            </nav>
        </div>
    );
};

export default NavComponent;
