import React from 'react'
import { createSelector } from '@reduxjs/toolkit'
import { toggleTodo } from './todosSlice'
import TodoList from './TodoList'
import { RootState } from '../../reducers'
import { useSelector } from 'react-redux'

const VisibleTodoList: React.FC = () => {
  const todos = useSelector(selectVisibleTodos)
  return <TodoList todos={todos} toggleTodo={toggleTodo} />
}

const selectTodos = (state: RootState) => state.todos
const selectFilter = (state: RootState) => state.visibilityFilter

const selectVisibleTodos = createSelector(
  [selectTodos, selectFilter],
  (todos, filter) => {
    switch (filter) {
      case 'SHOW_ALL':
        return todos
      case 'SHOW_COMPLETED':
        return todos.filter(t => t.completed)
      case 'SHOW_ACTIVE':
        return todos.filter(t => !t.completed)
      default:
        throw new Error('Unknown filter: ' + filter)
    }
  }
)

export default VisibleTodoList
