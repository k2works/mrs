import React, { useEffect } from 'react';

import ListRooms from '../../components/room/ListRoomsComponent';

const ListRoomsContainer = () => {
    useEffect(() => {
        console.log('ListRoomsContainer:useEffectによる初回処理');
    }, []);
    return (
        <div>
            <ListRooms />
        </div>
    );
};

export default ListRoomsContainer;
