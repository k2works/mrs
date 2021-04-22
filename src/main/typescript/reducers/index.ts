import {combineReducers} from 'redux'
import counterReducer from '../features/counter/counterSlice';
import todosReducer from '../features/todos/todosSlice'
import visibilityFilterReducer from '../features/filters/filtersSlice'
import authReducer from '../features/auth/authSlice'
import messageReducer from '../features/message/messageSlice'
import roomReducer from '../features/room/roomSlice'
import reservationReducer from '../features/reservation/reservationSlice'

const rootReducer = combineReducers({
  counter: counterReducer,
  todos: todosReducer,
  visibilityFilter: visibilityFilterReducer,
  auth: authReducer,
  message: messageReducer,
  room: roomReducer,
  reservation: reservationReducer
})

export type RootState = ReturnType<typeof rootReducer>

export default rootReducer