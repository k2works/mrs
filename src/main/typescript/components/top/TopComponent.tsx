import React from 'react';
import {useHistory} from 'react-router';
import {User} from "../../features/auth/authSlice";

type Props = {
    user: User
}

const TopComponent: React.FC<Props> = (props: Props) => {
    const history = useHistory();

    // @ts-ignore
    return (
        <div>
            <main>
                <header className="jumbotron subhead" id="overview">
                    <div className="row">
                        <div className="span6">
                            <h1>
                                <blink>
                                    <font color="#00ffB4">会</font>
                                    <font color="#00ffff">議</font>
                                    <font color="#00B4ff">室</font>
                                    <font color="#005Aff">予</font>
                                    <font color="#0000ff">約</font>
                                    <font color="#4B00ff">シ</font>
                                    <font color="#A500ff">ス</font>
                                    <font color="#FF00ff">テ</font>
                                    <font color="#FF00B4">ム</font>
                                </blink>
                            </h1>
                            <p className="lead">ようこそ {props.user.name.firstName} {props.user.name.lastName} さん</p>
                        </div>
                        <div className="span6">
                        </div>
                    </div>
                    <marquee>このシステムは、利用者がインターネット経由で予約できる画期的なシステムです</marquee>
                    <div className="subnav">
                        <ul className="nav nav-pills">
                            <h3>お知らせ</h3>
                            <dl>
                                <dt>会議室予約機能をリリースしました</dt>
                            </dl>
                        </ul>
                    </div>
                </header>
            </main>
        </div>
);
};

export default TopComponent;
