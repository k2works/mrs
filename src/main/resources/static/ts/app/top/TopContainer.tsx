import React, { useEffect } from 'react';

import Top from '../../components/top/TopComponent';

const TopContainer = () => {
    useEffect(() => {
        console.log('TopContainer:useEffectによる初回処理');
    }, []);
    return (
        <div>
            <Top />
        </div>
    );
};

export default TopContainer;
