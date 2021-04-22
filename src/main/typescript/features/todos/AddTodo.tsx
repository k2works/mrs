import React, {useState} from 'react'
import {useDispatch} from 'react-redux'
import {addTodo} from './todosSlice'

const AddTodo: React.FC = () => {
    const [todoText, setTodoText] = useState('')
    const dispatch = useDispatch()

    const onChange = (e: React.ChangeEvent<HTMLInputElement>) =>
        setTodoText(e.target.value)

    return (
        <div>
      <form
        onSubmit={e => {
          e.preventDefault()
          if (!todoText.trim()) {
            return
          }
          dispatch(addTodo(todoText))
          setTodoText('')
        }}
      >
        <input value={todoText} onChange={onChange} />
        <button type="submit">Add Todo</button>
      </form>
    </div>
  )
}

export default AddTodo
