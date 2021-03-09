import React from 'react';
import { useHistory } from 'react-router';

const TopComponent = () => {
    const history = useHistory();

    return (
        <div style={{ width: '100%' }}>
            <div className="centerTable">
                <h2>
                    トップページ
                </h2>
                <div style={{ textAlign: 'center' }}>
                    <a onClick={() => history.push('/counter')}>カウンターページ</a>
                </div>
                <div>
                    <a onClick={() => history.push('/sample?page=1')}>サンプルページ1</a>
                    <a onClick={() => history.push('/sample?page=2')}>サンプルページ2</a>
                </div>

            </div>
        </div>
    );
};

export default TopComponent;
