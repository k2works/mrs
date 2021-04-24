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
                <h3>メニュー</h3>
                <ul className={"list-inline"}>
                    {props.user && (
                        <div>
                            <li className={"list-inline-item"}><a onClick={() => history.push('/')} id="home">ホーム</a>
                            </li>
                            <li className={"list-inline-item"}><a onClick={() => history.push('/rooms')}
                                                                  id="rooms">会議室一覧</a></li>
                            <li className={"list-inline-item"}><a onClick={handleOnClickLogout}>ログアウト</a></li>
                        </div>
                    )}
                    {!props.user && (
                        <div>
                            <li className={"list-inline-item"}><a onClick={() => history.push('/signin')}
                                                                  id="signin">ログイン</a></li>
                            <li className={"list-inline-item"}><a onClick={() => history.push('/signup')}
                                                                  id="signup">利用者登録</a></li>
                        </div>
                    )}
                </ul>
            </nav>
        </div>
    );
};

export default NavComponent;
