import React, {useEffect} from 'react';

import {
    CounterComponent
} from '../../components/counter/CounterComponent'

const CounterContainer = () => {
    useEffect(() => {
        console.log('TopContainer:useEffectによる初回処理');
    }, []);

    const counterViewProps = {}
    return (
        <div>
            <CounterComponent props={counterViewProps} />
        </div>
    );
};

export default CounterContainer;
