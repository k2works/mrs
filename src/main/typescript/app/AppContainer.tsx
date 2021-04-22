import React, {useEffect} from 'react';
import {BrowserRouter as Router, Route, Switch} from 'react-router-dom';

import "../assets/css/style.css"
import Header from '../components/header/HeaderComponent';
import Footer from "../components/footer/FooterComponent";
import Nav from "../components/nav/NavComponent";
import TopContainer from './top/TopContainer';
import SigninContainer from './auth/SigninContainer';
import CounterContainer from './counter/CounterContainer';
import TodoContainer from "./todo/TodoContainer";
import SampleContainer from './sample/SampleContainer';
import NotFound from './NotFound';
import ListRoomsContainer from "./room/ListRoomsContainer";
import ReservationContainer from "./reservation/ReservationContainer";
import SignupContainer from "./auth/SignupContainer";
import {useSelector} from "react-redux";
import {currentUser} from "../features/auth/authSlice";

const AppContainer = () => {
    useEffect(() => {
        console.log('AppContainer:useEffectによる初回処理');
    }, []);

    const user = useSelector(currentUser);

    return (
        <div className={"container"}>
            <Header/>
            <Router>
                <Nav user={user}/>
                <div className={"main"}>
                    <Switch>
                        <Route path="/" exact>
                            <TopContainer/>
                        </Route>
                        <Route
                            path="/signup"
                            exact
                            component={SignupContainer}
                        />
                        <Route
                            path="/signin"
                            exact
                            component={SigninContainer}
                        />
                        <Route
                            path="/rooms"
                            exact
                            render={() => <ListRoomsContainer/>}
                        />
                        <Route
                            path="/reservations"
                            exact
                            render={() => <ReservationContainer />}
                        />
                        <Route
                            path="/counter"
                            exact
                            render={() => <CounterContainer />}
                        />
                        <Route
                            path="/todo"
                            exact
                            render={() => <TodoContainer />}
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