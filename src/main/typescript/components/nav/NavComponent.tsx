import React from 'react';
import {useHistory} from "react-router";
import {useDispatch} from "react-redux";
import {signout, User} from "../../features/auth/authSlice";

type Props = {
    user: User | null
}

const NavComponent: React.FC<Props> = (props: Props) => {
    const history = useHistory();
    const dispatch = useDispatch();

    const handleOnClickLogout = () => {
        dispatch(signout())
        history.push('/signin');
    };

    return (
        <div>
            <nav>
                <div className={"navbar"}>
                    <div className={"navbar-inner"}>
                        <div className={"container"}>
                            <a className={"brand"}>メニュー</a>

                            <div className={"nav-collapse"}>
                                {props.user && (
                                    <ul className={"nav"}>
                                        <li><a onClick={() => history.push('/')} id="home">ホーム</a>
                                        </li>
                                        <li><a onClick={() => history.push('/rooms')}
                                               id="rooms">会議室一覧</a></li>
                                        <li><a onClick={handleOnClickLogout}>ログアウト</a></li>
                                    </ul>
                                )}
                                {!props.user && (
                                    <ul className={"nav"}>
                                        <li><a onClick={() => history.push('/signin')}
                                               id="signin">ログイン</a></li>
                                        <li><a onClick={() => history.push('/signup')}
                                               id="signup">利用者登録</a></li>
                                    </ul>
                                )}
                            </div>
                        </div>
                    </div>
                </div>
            </nav>
        </div>
    );
};

export default NavComponent;
