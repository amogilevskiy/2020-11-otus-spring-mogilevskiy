import {Redirect, Route, RouteProps} from 'react-router';
import React from "react";
import {useTypedSelector} from "../../hooks/useTypedSelector";
import AuthStatus from "../../store/states/AuthStatus";

export default function PrivateRoute({...routeProps}: RouteProps) {
    const {status} = useTypedSelector(state => state.auth);

    if (status === AuthStatus.AUTHORIZED) {
        return <Route {...routeProps} />;
    } else {
        return <Redirect to={{pathname: "/login"}}/>;
    }
};