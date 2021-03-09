import React from 'react';
import {useHistory} from "react-router";

const NavComponent = () => {
    const history = useHistory();

    const handleOnClickLogout = () => {
        alert('logout clicked');
        history.push('/');
    };

    return (
        <div className={"nav"}>
            <nav>
                <h3>メニュー</h3>
                <ul>
                    <li><a onClick={() => history.push('/')} id="home">ホーム</a></li>
                    <li><a onClick={() => history.push('/rooms')} id="rooms">会議室一覧</a></li>
                    <li><a onClick={handleOnClickLogout}>ログアウト</a></li>
                    <li><a onClick={() => history.push('/counter')}>カウンター</a></li>
                    <li><a onClick={() => history.push('/todo')}>Todo</a></li>
                    <li><a onClick={() => history.push('/sample?page=1')}>サンプル1</a></li>
                    <li><a onClick={() => history.push('/sample?page=2')}>サンプル2</a></li>
                </ul>
            </nav>
        </div>
    );
};

export default NavComponent;
