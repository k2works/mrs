import React from 'react'

const Todo: React.FC<Props> = ({ onClick, completed, text }) => (
  <li
    onClick={onClick}
    style={{
      textDecoration: completed ? 'line-through' : 'none'
    }}
  >
    {text}
  </li>
)

interface Props {
  onClick: (event: React.MouseEvent) => any
  completed: boolean
  text: string
}

export default Todo
