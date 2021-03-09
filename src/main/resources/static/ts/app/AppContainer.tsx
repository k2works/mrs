import React, { useEffect } from 'react';
import { BrowserRouter as Router, Route, Switch } from 'react-router-dom';

import "../../css/style.css"
import Header from '../components/header/HeaderComponent';
import TopContainer from './top/TopContainer';
import LoginContainer from './login/LoginContainer';
import CounterContainer from './counter/CounterContainer';
import SampleContainer from './sample/SampleContainer';
import NotFound from './NotFound';

const AppContainer = () => {
    useEffect(() => {
        console.log('AppContainer:useEffectによる初回処理');
    }, []);
    return (
        <div>
            <Header />
            <div id="contents">
                <Router>
                    <Switch>
                        <Route path="/" exact>
                            <TopContainer />
                        </Route>
                        <Route
                            path="/login"
                            exact
                            component={LoginContainer}
                        />
                        <Route
                            path="/counter"
                            exact
                            render={() => <CounterContainer />}
                        />
                        <Route
                            path="/sample"
                            exact
                            render={() => <SampleContainer />}
                        />
                        <Route component={NotFound} />
                    </Switch>
                </Router>
            </div>
        </div>
    );
};

export default AppContainer;
