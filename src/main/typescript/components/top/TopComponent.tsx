import React from 'react';
import {useHistory} from 'react-router';
import {User} from "../../features/auth/authSlice";

type Props = {
    user: User
}

const TopComponent: React.FC<Props> = (props: Props) => {
    const history = useHistory();

    return (
        <div>
            <main>
                <h2>会議室予約システムへようこそ {props.user.name.firstName} {props.user.name.lastName} さん</h2>

                <p>このシステムは、利用者がインターネット経由で予約できる画期的なシステムです</p>

                <h3>お知らせ</h3>
                <dl>
                    <dt>会議室予約機能をリリースしました</dt>
                </dl>
            </main>
        </div>
);
};

export default TopComponent;
