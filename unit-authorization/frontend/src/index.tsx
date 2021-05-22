import React from 'react';
import ReactDOM from 'react-dom';
import './index.css';
import App from './App';
import reportWebVitals from './reportWebVitals';
import {Provider} from "react-redux";
import {store} from "./store";
import 'antd/dist/antd.css';
import {BrowserRouter} from "react-router-dom";
import {AuthActionTypes} from "./store/actions/authAction";
import axiosClient from './services/axiosClient';

axiosClient.interceptors.response.use(undefined, (error) => {
    console.log("interceptors");
    if (error.response.status === 401) {
        console.log("will autologout");
        store.dispatch({type: AuthActionTypes.LOGIN_EXPIRED});
    }
    return Promise.reject(error);
});

ReactDOM.render(
    <Provider store={store}>
        <BrowserRouter>
            <App/>
        </BrowserRouter>
    </Provider>,
    document.getElementById('root'),
);

// If you want to start measuring performance in your app, pass a function
// to log results (for example: reportWebVitals(console.log))
// or send to an analytics endpoint. Learn more: https://bit.ly/CRA-vitals
reportWebVitals();
