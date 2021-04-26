import React from 'react';
import {useHistory} from "react-router";
import {useDispatch} from "react-redux";
import {User} from "../../features/auth/authSlice";
import constructionImg from '../../assets/img/test/construction.gif';

type Props = {
    user: User
}

const BBSComponent: React.FC<Props> = (props: Props) => {
    const history = useHistory();
    const dispatch = useDispatch();
    return (
        <div>
            <main>
                <h3>掲示板</h3>

                <img src={constructionImg}/>
            </main>
        </div>
    );
};

export default BBSComponent;
