import React, { useEffect } from 'react';
import { useLocation } from 'react-router-dom';

import Sample1 from '../../components/sample/Sample1Component';
import Sample2 from '../../components/sample/Sample2Component';
import NotFound from '../NotFound';

const useQuery = () => {
    // useLocationでlocationを取得
    const location = useLocation();

    // locationからクエリストリングを取得
    return new URLSearchParams(location.search);
};

const SampleContainer = () => {
    useEffect(() => {
        console.log('SampleContainer:useEffectによる初回処理');
    }, []);

    // 対象のページをクエリストリングから取得
    const query = useQuery();
    const targetPage = query.get('page');

    if (targetPage === '1') {
        return (
            <div>
                <Sample1 />
            </div>
        );
    }
    if (targetPage === '2') {
        return (
            <div>
                <Sample2 />
            </div>
        );
    }
    return (
        <div>
            <NotFound />
        </div>
    );
};

export default SampleContainer;
