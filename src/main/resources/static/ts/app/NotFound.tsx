import React from 'react';

import notFoundImage from '../../image/not_found.png';

const NotFound = () => (
    <div className="row" style={{ textAlign: 'center', height: '100%' }}>
        <br />
        <br />
        <br />
        <img
            src={notFoundImage}
            style={{
                minWidth: '100px',
                maxWidth: '300px',
                width: '100%',
                height: '100%',
            }}
            alt="NotFound"
        />
        <h1>404 Not Found</h1>
        <br />
        <h3>このURLは存在しません。</h3>
    </div>
);

export default NotFound;
