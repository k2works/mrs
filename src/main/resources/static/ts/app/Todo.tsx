import React, { useEffect, useState } from 'react'
import {
    TodoView,
    Props as TodoViewProps
} from '../components/TodoView'
interface Props {
    props: {}
}
export const Todo: React.FC<Props> = ({ props }) => {
    const initial: TodoViewProps = {}
    const [state, setState] = useState(initial)
    useEffect(() => {
        setState(props)
    }, [state])
    const counterViewProps = {}

    return <TodoView props={counterViewProps} />
}
