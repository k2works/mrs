import React, {useEffect, useState} from 'react';

import {
    CounterView,
    Props as CounterViewProps
} from '../../components/CounterView'

const CounterContainer = () => {
    useEffect(() => {
        console.log('TopContainer:useEffectによる初回処理');
    }, []);

    const counterViewProps = {}
    return (
        <div>
            <CounterView props={counterViewProps} />
        </div>
    );
};

export default CounterContainer;
