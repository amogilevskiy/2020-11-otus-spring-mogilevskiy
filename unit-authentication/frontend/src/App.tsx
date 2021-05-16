import React from 'react';
import './App.css';
import {Layout, Spin} from "antd";
import {Route} from 'react-router-dom';
import BookPage from "./components/book/BookPage";
import GenrePage from "./components/genre/GenrePage";
import HeaderMenu from "./components/HeaderMenu";
import AuthorPage from "./components/author/AuthorPage";
import LoginPage from "./components/login/LoginPage";
import PrivateRoute from "./components/navigation/PrivateRoute";
import {useTypedSelector} from "./hooks/useTypedSelector";
import {useActions} from "./hooks/useActions";
import AuthStatus from "./store/states/AuthStatus";
import OperationState from "./components/common/OperationState";

const {Header, Content} = Layout;

const App = () => {

    const {status, loginOperationStatus} = useTypedSelector(state => state.auth);
    const {autologin, logout} = useActions();

    const menuItems = [
        {
            name: 'Books',
            link: '/',
            isProtected: true,
        },
        {
            name: 'Authors',
            link: '/authors',
            isProtected: true,
        },
        {
            name: 'Genres',
            link: '/genres',
            isProtected: true,
        },
        {
            name: 'Login',
            link: '/login',
            isProtected: false,
        },
        {
            name: 'Logout',
            link: undefined,
            isProtected: true,
            action: () => {
                logout();
            },
        },
    ];

    const needsAutologin = status === AuthStatus.UNKNOWN && loginOperationStatus === OperationState.IDLE;

    if (needsAutologin) {
        autologin();
    }

    const isAuthenticated = status === AuthStatus.AUTHORIZED;
    
    const renderRoutes = () => {
        return (
            <div className="site-layout-background" style={{padding: 24, minHeight: '1200px'}}>
                <PrivateRoute path="/" exact><BookPage/></PrivateRoute>
                <PrivateRoute path="/authors"><AuthorPage/></PrivateRoute>
                <PrivateRoute path="/genres"><GenrePage/></PrivateRoute>
                <Route path="/login"><LoginPage/></Route>
            </div>
        );
    };

    const renderSpinner = () => {
        return (
            <div style={{display: "flex", justifyContent: "center", alignItems: "center"}}>
                <Spin/>
            </div>);
    };

    const showsRoutes = status !== AuthStatus.UNKNOWN && loginOperationStatus !== OperationState.PROGRESS;

    return (
        <Layout>
            <Header style={{position: 'fixed', zIndex: 1, width: '100%'}}>
                <div className="logo"/>
                <HeaderMenu items={menuItems} isAuthenticated={isAuthenticated}/>
            </Header>
            <Content className="site-layout" style={{padding: '50px 50px', marginTop: 64}}>
                {showsRoutes ? renderRoutes() : renderSpinner()}
            </Content>
        </Layout>
    );
};

export default App;
