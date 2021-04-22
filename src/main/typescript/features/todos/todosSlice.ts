import {createSlice, PayloadAction} from '@reduxjs/toolkit'

export interface TodoType {
  id: number
  completed: boolean
  text: string
}

type TodoState = TodoType[]

let nextTodoId = 0

const todosSlice = createSlice({
  name: 'todos',
  initialState: [] as TodoState,
  reducers: {
    addTodo: {
      reducer(state, action: PayloadAction<Omit<TodoType, 'completed'>>) {
        const { id, text } = action.payload
        state.push({ id, text, completed: false })
      },
      prepare(text: string) {
        return { payload: { text, id: nextTodoId++ } }
      }
    },
    toggleTodo(state, action) {
      const todo = state.find(todo => todo.id === action.payload)
      if (todo) {
        todo.completed = !todo.completed
      }
    }
  }
})

export const { addTodo, toggleTodo } = todosSlice.actions

export default todosSlice.reducer
