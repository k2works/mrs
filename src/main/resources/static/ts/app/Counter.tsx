import React, { useEffect, useState } from 'react'
import {
    CounterView,
    Props as CounterViewProps
} from '../components/CounterView'
interface Props {
    props: {}
}
export const Counter: React.FC<Props> = ({ props }) => {
    const initial: CounterViewProps = {}
    const [state, setState] = useState(initial)
    useEffect(() => {
        setState(props)
    }, [state])
    const counterViewProps = {}

    return <CounterView props={counterViewProps} />
}
