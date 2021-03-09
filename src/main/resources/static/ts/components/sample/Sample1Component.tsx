import React from 'react';
import { useHistory } from 'react-router';

const Sample1Component = () => {
    const history = useHistory();

    const handleOnClickToTop = () => {
        history.push('/');
    };

    return (
        <div style={{ width: '100%' }}>
            <div className="centerTable">
                <h2>
                    サンプルページ1
                </h2>
                <a onClick={handleOnClickToTop}>TOPへ戻る</a>
            </div>
        </div>
    );
};

export default Sample1Component;
