import React from 'react'
import Todo from './Todo'
import { useDispatch } from 'react-redux'
import { TodoType } from './todosSlice'

const TodoList: React.FC<Props> = ({ todos, toggleTodo }) => {
  const dispatch = useDispatch()
  return (
    <ul>
      {todos.map(todo => (
        <Todo
          key={todo.id}
          {...todo}
          onClick={() => dispatch(toggleTodo(todo.id))}
        />
      ))}
    </ul>
  )
}

interface Props {
  todos: TodoType[]
  toggleTodo: (id: number) => any
}

export default TodoList
