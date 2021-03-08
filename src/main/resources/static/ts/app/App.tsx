import React, { useState } from 'react'
import {Todo} from './Todo'
import {Counter} from "./Counter";
import "../../css/style.css"

interface State {
    counterView: {}
    todoView: {}
}

const initial: State = {
    counterView: {},
    todoView: {}
}

const App: React.FC = () =>{
    const [state] = useState(initial)
    return (
            <div>
                <Todo props={state.todoView} />
                <Counter props={state.counterView} />
            </div>
    )
}
export default App
