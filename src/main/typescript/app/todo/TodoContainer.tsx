import React, {useEffect} from 'react'
import {TodoComponent} from '../../components/todo/TodoComponent'

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
