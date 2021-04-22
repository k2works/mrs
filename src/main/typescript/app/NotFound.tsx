import React from 'react';

import notFoundImage from '../assets/image/not_found.png';

const NotFound = () => (
    <div style={{ textAlign: 'center' }}>
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
