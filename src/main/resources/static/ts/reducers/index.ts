import { combineReducers } from 'redux'
import counterReducer from '../features/counter/counterSlice';
import todosReducer from '../features/todos/todosSlice'
import visibilityFilterReducer from '../features/filters/filtersSlice'

const rootReducer = combineReducers({
  counter: counterReducer,
  todos: todosReducer,
  visibilityFilter: visibilityFilterReducer
})

export type RootState = ReturnType<typeof rootReducer>

export default rootReducer
