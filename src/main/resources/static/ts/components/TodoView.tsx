import React, { useState } from 'react'
import { useSelector, useDispatch } from 'react-redux'
import AddTodo from '../features/todos/AddTodo'
import VisibleTodoList from '../features/todos/VisibleTodoList'
import Footer from '../features/filters/Footer'

export interface Props {}
export const TodoView: React.FC<{
  props: Props
}> = ({ props }) => {
  return (
    <>
      <AddTodo />
      <VisibleTodoList />
      <Footer />
    </>
  )
}
