import React, { useState } from 'react'
import { useSelector, useDispatch } from 'react-redux'
import {
    decrement,
    increment,
    incrementByAmount,
    incrementAsync,
    selectCount
} from '../../features/counter/counterSlice'

export interface Props {}
export const CounterComponent: React.FC<{
    props: Props
}> = ({ props }) => {
    const count = useSelector(selectCount)
    const dispatch = useDispatch()
    const [incrementAmount, setIncrementAmount] = useState('2')

    return (
        <>
            <div className={'hoge'}>
                <button
                    className={'hoge'}
                    aria-label="Increment value"
                    onClick={() => dispatch(increment())}
                >
                    +
                </button>
                <span className={'hoge'}>{count}</span>
                <button
                    className={'hoge'}
                    aria-label="Decrement value"
                    onClick={() => dispatch(decrement())}
                >
                    -
                </button>
            </div>
            <div className={'hoge'}>
                <input
                    className={'hoge'}
                    aria-label="Set increment amount"
                    value={incrementAmount}
                    onChange={e => setIncrementAmount(e.target.value)}
                />
                <button
                    className={'hoge'}
                    onClick={() =>
                        dispatch(incrementByAmount(Number(incrementAmount) || 0))
                    }
                >
                    Add Amount
                </button>
                <button
                    className={'hoge'}
                    onClick={() => dispatch(incrementAsync(Number(incrementAmount) || 0))}
                >
                    Add Async
                </button>
            </div>
        </>
    )
}
