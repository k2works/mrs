import React from 'react';

import notFoundImage from '../assets/img/test/mchammer.gif';

const NotFound = () => (
    <div style={{textAlign: 'center'}}>
            <br/>
            <br/>
            <br/>
            {
                    Array.from(Array(50).keys()).map(i =>
                        <img
                            src={notFoundImage}
                            alt="NotFound"
                        />
                    )
            }
            <h1>404 Not Found</h1>
            <br/>
            <h3>このURLは存在しません。</h3>
    </div>
);

export default NotFound;
