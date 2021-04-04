import {Link, useLocation} from "react-router-dom";
import {Menu} from "antd";
import React from "react";

const menuItems = [
    {
        name: 'Books',
        link: '/',
    },
    {
        name: 'Authors',
        link: '/authors',
    },
    {
        name: 'Genres',
        link: '/genres',
    },
];

const HeaderMenu = () => {

    const location = useLocation();

    return (
        <Menu theme="dark" mode="horizontal" selectedKeys={[location.pathname]}>
            {menuItems.map((item) => (
                <Menu.Item key={item.link}>
                    <Link to={item.link}>{item.name}</Link>
                </Menu.Item>
            ))}
        </Menu>
    );
};

export default HeaderMenu;