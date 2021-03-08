import React, { useState } from 'react'
import {Todo} from './Todo'
import {Counter} from "./Counter";

interface State {
    counterView: {}
    todoView: {}
}

const initial: State = {
    counterView: {},
    todoView: {}
}

const [state, setState] = useState(initial)

const actions = {}

const App: React.FC = () => (
    <div>
        <Todo props={state.todoView} actions={actions} />
        <Counter props={state.counterView} actions={actions} />
    </div>
)

export default App
