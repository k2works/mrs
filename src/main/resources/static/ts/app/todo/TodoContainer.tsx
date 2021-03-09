import React, { useEffect, useState } from 'react'
import {
    TodoComponent,
    Props as TodoViewProps
} from '../../components/todo/TodoComponent'
import CounterContainer from "../counter/CounterContainer";
interface Props {
    props: {}
}
export const TodoContainer: React.FC = () => {
    useEffect(() => {
        console.log('TopContainer:useEffectによる初回処理');
    }, []);
    const counterViewProps = {}

    return <TodoComponent props={counterViewProps} />
}

export default TodoContainer;
