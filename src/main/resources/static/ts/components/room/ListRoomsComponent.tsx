import React from 'react';
import {useHistory} from "react-router";

const ListRoomComponent = () => {
    const history = useHistory();

    return (
        <div>
            <main>
                <h3>会議室</h3>
                <a onClick={() => history.push('/rooms/2021-03-08')}>&lt; 前日 </a>
                <span>2021/3/9の会議室</span>
                <a onClick={() => history.push('/rooms/2021-03-10')}> 翌日 &gt;</a>

                <ul>
                    <li>
                        <a onClick={() => history.push('/reservations?page=2021-03-09-1')}>新木場</a>
                    </li>
                    <li>
                        <a onClick={() => history.push('/reservations?page=2021-03-09-7')}>有楽町</a>
                    </li>
                </ul>
            </main>
        </div>
    );
};

export default ListRoomComponent;
