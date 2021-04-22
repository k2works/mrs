import {combineReducers} from 'redux'
import authReducer from '../features/auth/authSlice'
import messageReducer from '../features/message/messageSlice'
import roomReducer from '../features/room/roomSlice'
import reservationReducer from '../features/reservation/reservationSlice'

const rootReducer = combineReducers({
  auth: authReducer,
  message: messageReducer,
  room: roomReducer,
  reservation: reservationReducer
})

export type RootState = ReturnType<typeof rootReducer>

export default rootReducer
