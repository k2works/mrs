import React, {useEffect, useState} from 'react';
import {selectMessage} from "../../features/message/messageSlice";
import {useDispatch, useSelector} from "react-redux";
import {initialContainer} from "../../utils/containerUtils";
import {currentUser} from "../../features/auth/authSlice";
import {Redirect} from "react-router-dom";
import BBSComponent from "../../components/bbs/BBSComponent";

const BBSContainer = () => {
    const dispatch = useDispatch();
    const [successful, setSuccessful] = useState(false);

    useEffect(() => {
        console.log('BBSContainer:useEffectによる初回処理');
        initialContainer(dispatch);
    }, []);

    const {message} = useSelector(selectMessage)

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
            <BBSComponent user={user}/>
        </div>
    )
}

export default BBSContainer;
