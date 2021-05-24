import React from 'react';
import {useHistory} from 'react-router';
import {User} from "../../features/auth/authSlice";

import mchammerImg from '../../assets/img/test/mchammer.gif';
import newImg from '../../assets/img/test/new2.gif';
import hotImg from '../../assets/img/test/hot.gif';
import ieLogImg from '../../assets/img/test/ie_logo.gif';
import nsLogoImg from '../../assets/img/test/ns_logo.gif';
import noFramesImg from '../../assets/img/test/noframes.gif';
import notepadImg from '../../assets/img/test/notepad.gif';


type Props = {
    user: User
}

const TopComponent: React.FC<Props> = (props: Props) => {
    const history = useHistory();

    const mchammers = (count: number) => (
        Array.from(Array(count).keys()).map(i =>
            <img
                src={mchammerImg}
                alt="uzeee"
            />
        )
    )

    return (
        <div>
            <main>

                <div className={"hero-unit"}>
                    <h1>会議室予約システム</h1>
                    <h4>こんにちは {props.user.name.firstName} {props.user.name.lastName} さん</h4>

                    <marquee><p className={"lead"}>このシステムは、利用者がインターネット経由で予約できる画期的なシステムです</p></marquee>
                </div>

                <center>
                    {mchammers(10)}
                </center>


                <div className={"page-header"}>
                    <h3>
                        お知らせ
                        <img src={newImg}/>
                    </h3>
                    <dl>
                        <img className="pull-left" src={hotImg}/>
                        <dt>サイトリニューアルしました</dt>
                        <dt>会議室予約機能をリリースしました</dt>
                    </dl>
                </div>

                <center>
                    <img src={ieLogImg}/>
                    <img src={nsLogoImg}/>
                    <img src={noFramesImg}/>
                    <img src={notepadImg}/>
                </center>
            </main>
        </div>
);
};

export default TopComponent;
