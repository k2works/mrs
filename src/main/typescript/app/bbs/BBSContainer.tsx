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

    const user = useSelector(currentUser);
    if (!user) {
        return <Redirect to="/signin"/>;
    }
    return (
        <div>
            <BBSComponent user={user}/>
        </div>
    )
}

export default BBSContainer;
