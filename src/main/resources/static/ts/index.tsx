import React from 'react'
import { render } from 'react-dom'
import { configureStore } from '@reduxjs/toolkit'
import { Provider } from 'react-redux'
import AppContainer from "./app/AppContainer";
import rootReducer from './reducers'

const store = configureStore({
    reducer: rootReducer
})

render(
    <Provider store={store}>
        <AppContainer />
    </Provider>,
    document.getElementById('root')
)
