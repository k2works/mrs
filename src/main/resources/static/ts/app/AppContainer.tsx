import React, { useEffect } from 'react';
import { BrowserRouter as Router, Route, Switch } from 'react-router-dom';

import "../../css/style.css"
import Header from '../components/header/HeaderComponent';
import Footer from "../components/footer/FooterComponent";
import Nav from "../components/nav/NavComponent";
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
        <div className={"container"}>
            <Header />
            <Router>
                <Nav />
                <div className={"main"}>
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
                </div>
            </Router>
            <Footer />
        </div>
    );
};

export default AppContainer;
