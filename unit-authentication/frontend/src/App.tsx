import React from 'react';
import './App.css';

import {Layout} from "antd";
import {Route} from 'react-router-dom';
import BookPage from "./components/book/BookPage";
import GenrePage from "./components/genre/GenrePage";
import HeaderMenu from "./components/HeaderMenu";
import AuthorPage from "./components/author/AuthorPage";

const {Header, Content} = Layout;


const App = () => {
    return (
        <Layout>
            <Header style={{position: 'fixed', zIndex: 1, width: '100%'}}>
                <div className="logo"/>
                <HeaderMenu/>
            </Header>
            <Content className="site-layout" style={{padding: '50px 50px', marginTop: 64}}>
                <div className="site-layout-background" style={{padding: 24, minHeight: '1200px'}}>
                    <Route path="/" exact><BookPage/></Route>
                    <Route path="/authors"><AuthorPage/></Route>
                    <Route path="/genres"><GenrePage/></Route>
                </div>
            </Content>
        </Layout>
    );
};

export default App;
