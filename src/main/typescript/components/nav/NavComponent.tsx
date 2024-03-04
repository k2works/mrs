import React from 'react';
import {useHistory} from "react-router";
import {useDispatch} from "react-redux";
import {signout, User} from "../../features/auth/authSlice";

import newImg from '../../assets/img/test/new.gif';

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
                  <ul className={"nav"}>
                    <li>
                      <a onClick={() => history.push("/")} id="home">
                        ホーム
                      </a>
                    </li>
                    <li>
                      <a onClick={() => history.push("/rooms")} id="rooms">
                        会議室一覧
                      </a>
                    </li>
                    <li>
                      <a onClick={() => history.push("/bbs")} id="rooms">
                        <img className="pull-left" src={newImg} />
                        掲示板
                      </a>
                    </li>
                    <li>
                      <a onClick={handleOnClickLogout}>ログアウト</a>
                    </li>
                  </ul>
                </div>
              </div>
            </div>
          </div>
        </nav>
      </div>
    );
};

export default NavComponent;
