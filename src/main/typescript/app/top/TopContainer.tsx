import React, {useEffect} from 'react';

import Top from '../../components/top/TopComponent';
import {initialContainer} from "../../utils/containerUtils";
import {useDispatch, useSelector} from "react-redux";
import {currentUser} from "../../features/auth/authSlice";
import {Redirect} from "react-router-dom";

const TopContainer = () => {
    const dispatch = useDispatch();
    useEffect(() => {
        console.log('TopContainer:useEffectによる初回処理');
        initialContainer(dispatch);
    }, []);

    let user = useSelector(currentUser);
    if (!user) {
      user = {
        userId: { value: "aaaa" },
        name: { firstName: "DUMMY", lastName: "DUMMY" },
        password: { value: "DUMMY" },
        roleName: "USER",
      };
    }

    return (
        <div>
            <Top user={user}/>
        </div>
    );
};

export default TopContainer;
