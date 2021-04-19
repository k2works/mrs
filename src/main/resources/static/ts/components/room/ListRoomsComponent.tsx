import React from 'react';
import {useHistory} from "react-router";
import {useDispatch, useSelector} from "react-redux";
import {currentUser} from "../../features/auth/authSlice";
import {Redirect} from "react-router-dom";
import {
    currentReservedDate,
    decrementReservedDate,
    incrementReservedDate,
    roomList,
    roomState
} from "../../features/room/roomSlice";
import {setParams} from "../../features/reservation/reservationSlice";

const ListRoomComponent = () => {
    const history = useHistory();
    const dispatch = useDispatch();
    const user = useSelector(currentUser);
    if (!user) {
        return <Redirect to="/signin"/>;
    }

    const room = useSelector(roomState)
    const currentDay = useSelector(currentReservedDate)

    const handlePreDay = async (e: any) => {
        dispatch(decrementReservedDate(e.target.value))
        const current = new Date(currentDay)
        const preDay = new Date(current.setDate(current.getDate() - 1))
        await dispatch(roomList(preDay))
    }

    const handleNextDay = async (e: any) => {
        dispatch(incrementReservedDate(e.target.value))
        const current = new Date(currentDay)
        const nextDay = new Date(current.setDate(current.getDate() + 1))
        await dispatch(roomList(nextDay))
    }

    const handleReservableRoom = (e: any) => {
        const reservedDate = e.target.dataset["reserved_date"]
        const roomId = e.target.dataset["room_id"]
        const roomName = e.target.dataset["room_name"]
        dispatch(setParams({reservedDate, roomId, roomName}))
        history.push(`/reservations?page=${reservedDate}-${roomId}`)
    }

    return (
        <div>
            <main>
                <h3>会議室</h3>
                <a onClick={handlePreDay}>&lt; 前日 </a>
                <span>{currentDay} の会議室</span>
                <a onClick={handleNextDay}> 翌日 &gt;</a>

                <ul>
                    {
                        room.reservableRooms.value.map(item => (
                            <li>
                                <a onClick={handleReservableRoom}
                                   data-reserved_date={item.reservableRoomId.reservedDate.value}
                                   data-room_id={item.reservableRoomId.roomId.value}
                                   data-room_name={item.meetingRoom.roomName.value}
                                >
                                    {item.meetingRoom.roomName.value}
                                </a>
                            </li>
                        ))
                    }
                </ul>
            </main>
        </div>
    );
};

export default ListRoomComponent;
