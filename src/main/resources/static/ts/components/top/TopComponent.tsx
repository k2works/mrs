import React from 'react';
import {useHistory} from 'react-router';
import {useSelector} from "react-redux";
import {currentUser} from "../../features/auth/authSlice";
import {Redirect} from "react-router-dom";

const TopComponent = () => {
    const history = useHistory();
    const user = useSelector(currentUser);
    if (!user) {
        return <Redirect to="/login"/>;
    }

    return (
        <div>
            <main>
                <h2>会議室予約システムへようこそ {user.name.firstName} {user.name.lastName} さん</h2>

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
