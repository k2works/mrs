import React, {useState} from 'react';
import {useHistory} from "react-router";
import {useDispatch, useSelector} from "react-redux";
import {currentUser} from "../../features/auth/authSlice";
import {Redirect} from "react-router-dom";
import {
    currentReservedDate,
    reservationCancel,
    reservationList,
    reservationReserve,
    reservationState
} from "../../features/reservation/reservationSlice";
import {selectMessage, setMessage} from "../../features/message/messageSlice";

const ReservationComponent = () => {
    const history = useHistory();
    const user = useSelector(currentUser);
    if (!user) {
        return <Redirect to="/signin"/>;
    }
    const dispatch = useDispatch()
    const state = useSelector(reservationState)
    const reservedDate = useSelector(currentReservedDate)

    const handleRooms = () => {
        history.push('/rooms')
    }

    const [startTime, setStartTime] = useState("09:00")
    const [endTime, setEndTime] = useState("10:00")
    const handleChangeStartTime = (e: any) => {
        const value = e.target.value
        setStartTime(value)
    }
    const handleChangeEndTime = (e: any) => {
        const value = e.target.value
        setEndTime(value)
    }

    const [successful, setSuccessful] = useState(false);
    const {message} = useSelector(selectMessage)
    const handleSubmit = async () => {
        setSuccessful(false);

        const params = {
            date: new Date(reservedDate),
            start: startTime,
            end: endTime,
            roomId: state.roomId,
            userid: user.userId.value
        };
        const resultAction: any = await dispatch(reservationReserve(params))
        if (reservationReserve.fulfilled.match(resultAction)) {
            dispatch(setMessage(resultAction.payload.message))
            setSuccessful(true);
            list()
        } else {
            if (resultAction.payload) {
                dispatch(setMessage(resultAction.payload.message))
            } else {
                dispatch(setMessage(resultAction.error.message))
            }
            setSuccessful(false);
        }
    }

    const handleCancel = async (e: any) => {
        setSuccessful(false);

        const reservationId = e.target.dataset["reservationid"]
        const username = e.target.dataset["username"]

        const params = {
            date: new Date(reservedDate),
            roomId: state.roomId,
            reservationId: reservationId,
            username: username
        };
        const resultAction: any = await dispatch(reservationCancel(params))
        if (reservationCancel.fulfilled.match(resultAction)) {
            dispatch(setMessage(resultAction.payload.message))
            setSuccessful(true);
            list()
        } else {
            if (resultAction.payload) {
                dispatch(setMessage(resultAction.payload.message))
            } else {
                dispatch(setMessage(resultAction.error.message))
            }
            setSuccessful(false);
        }
    }

    const showCancelButton = (params: { username: string, reservationId: number }) => {
        if (user.userId.value === params.username || user.roleName === "ADMIN")
            return (
                <button
                    id={"cancel"}
                    onClick={handleCancel}
                    type="submit"
                    data-username={params.username}
                    data-reservationid={params.reservationId}
                >取消</button>
            )
    }

    const list = async () => {
        setSuccessful(false);

        const resultAction: any = await dispatch(reservationList({date: state.reservedDate, roomId: state.roomId}))
        if (reservationList.fulfilled.match(resultAction)) {
            dispatch(setMessage(resultAction.payload.message))
            setSuccessful(true);
        } else {
            if (resultAction.payload) {
                dispatch(setMessage(resultAction.payload.message))
            } else {
                dispatch(setMessage(resultAction.error.message))
            }
            setSuccessful(false);
        }
    }

    return (
        <div>
            <main>
                <div>
                    <a onClick={handleRooms}>会議室一覧へ</a>
                </div>
                {!successful && (
                    <p style={{color: 'red'}}>{message}</p>
                )}

                <form onSubmit={e => {
                    e.preventDefault()
                    handleSubmit()
                }}
                >
                    会議室: <span>{state.roomName}</span>
                    <br/>
                    予約者名: <span>{user.name.firstName} {user.name.lastName}</span>
                    <br/>
                    日付: <span>{reservedDate}</span>
                    <br/>
                    時間帯:
                    <select id="startTime" name="startTime" value={startTime} onChange={handleChangeStartTime}>
                        <option value="00:00">00:00</option>
                        <option value="00:30">00:30</option>
                        <option value="01:00">01:00</option>
                        <option value="01:30">01:30</option>
                        <option value="02:00">02:00</option>
                        <option value="02:30">02:30</option>
                        <option value="03:00">03:00</option>
                        <option value="03:30">03:30</option>
                        <option value="04:00">04:00</option>
                        <option value="04:30">04:30</option>
                        <option value="05:00">05:00</option>
                        <option value="05:30">05:30</option>
                        <option value="06:00">06:00</option>
                        <option value="06:30">06:30</option>
                        <option value="07:00">07:00</option>
                        <option value="07:30">07:30</option>
                        <option value="08:00">08:00</option>
                        <option value="08:30">08:30</option>
                        <option value="09:00">09:00</option>
                        <option value="09:30">09:30</option>
                        <option value="10:00">10:00</option>
                        <option value="10:30">10:30</option>
                        <option value="11:00">11:00</option>
                        <option value="11:30">11:30</option>
                        <option value="12:00">12:00</option>
                        <option value="12:30">12:30</option>
                        <option value="13:00">13:00</option>
                        <option value="13:30">13:30</option>
                        <option value="14:00">14:00</option>
                        <option value="14:30">14:30</option>
                        <option value="15:00">15:00</option>
                        <option value="15:30">15:30</option>
                        <option value="16:00">16:00</option>
                        <option value="16:30">16:30</option>
                        <option value="17:00">17:00</option>
                        <option value="17:30">17:30</option>
                        <option value="18:00">18:00</option>
                        <option value="18:30">18:30</option>
                        <option value="19:00">19:00</option>
                        <option value="19:30">19:30</option>
                        <option value="20:00">20:00</option>
                        <option value="20:30">20:30</option>
                        <option value="21:00">21:00</option>
                        <option value="21:30">21:30</option>
                        <option value="22:00">22:00</option>
                        <option value="22:30">22:30</option>
                        <option value="23:00">23:00</option>
                        <option value="23:30">23:30</option>
                    </select>
                    -
                    <select id="endTime" name="endTime" value={endTime} onChange={handleChangeEndTime}>
                        <option value="00:00">00:00</option>
                        <option value="00:30">00:30</option>
                        <option value="01:00">01:00</option>
                        <option value="01:30">01:30</option>
                        <option value="02:00">02:00</option>
                        <option value="02:30">02:30</option>
                        <option value="03:00">03:00</option>
                        <option value="03:30">03:30</option>
                        <option value="04:00">04:00</option>
                        <option value="04:30">04:30</option>
                        <option value="05:00">05:00</option>
                        <option value="05:30">05:30</option>
                        <option value="06:00">06:00</option>
                        <option value="06:30">06:30</option>
                        <option value="07:00">07:00</option>
                        <option value="07:30">07:30</option>
                        <option value="08:00">08:00</option>
                        <option value="08:30">08:30</option>
                        <option value="09:00">09:00</option>
                        <option value="09:30">09:30</option>
                        <option value="10:00">10:00</option>
                        <option value="10:30">10:30</option>
                        <option value="11:00">11:00</option>
                        <option value="11:30">11:30</option>
                        <option value="12:00">12:00</option>
                        <option value="12:30">12:30</option>
                        <option value="13:00">13:00</option>
                        <option value="13:30">13:30</option>
                        <option value="14:00">14:00</option>
                        <option value="14:30">14:30</option>
                        <option value="15:00">15:00</option>
                        <option value="15:30">15:30</option>
                        <option value="16:00">16:00</option>
                        <option value="16:30">16:30</option>
                        <option value="17:00">17:00</option>
                        <option value="17:30">17:30</option>
                        <option value="18:00">18:00</option>
                        <option value="18:30">18:30</option>
                        <option value="19:00">19:00</option>
                        <option value="19:30">19:30</option>
                        <option value="20:00">20:00</option>
                        <option value="20:30">20:30</option>
                        <option value="21:00">21:00</option>
                        <option value="21:30">21:30</option>
                        <option value="22:00">22:00</option>
                        <option value="22:30">22:30</option>
                        <option value="23:00">23:00</option>
                        <option value="23:30">23:30</option>
                    </select>
                    <br/>
                    <button id="reserve" type="submit">予約</button>
                </form>

                <table>
                    <tbody>
                    <tr>
                        <th>時間帯</th>
                        <th>予約者</th>
                        <th>操作</th>
                    </tr>
                    {
                        state.reservations.value.map(item => (
                            <tr>
                                <td>
                                    <span>{item.reservedDateTime.time.start}</span></td>
                                <td>
                                    <span>{item.user.name.lastName}</span></td>
                                <td>
                                    {showCancelButton({
                                        username: item.user.userId.value,
                                        reservationId: item.reservationId.value
                                    })}
                                </td>
                            </tr>
                        ))
                    }
                    </tbody>
                </table>
            </main>
        </div>
    );
};

export default ReservationComponent;
