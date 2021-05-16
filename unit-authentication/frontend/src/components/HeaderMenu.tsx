import {Link, useLocation} from "react-router-dom";
import {Menu} from "antd";
import React from "react";

interface HeaderMenuProps {
    items: any[],
    isAuthenticated: boolean;
}

const HeaderMenu = ({isAuthenticated, items}: HeaderMenuProps) => {

    const location = useLocation();

    return (
        <Menu theme="dark" mode="horizontal" selectedKeys={[location.pathname]}>
            {items.filter((item) => item.isProtected === isAuthenticated)
                .map((item) => {
                    if (item.link) {
                        return (
                            <Menu.Item key={item.name}>
                                <Link to={item.link}>{item.name}</Link>
                            </Menu.Item>
                        );
                    } else {
                        return (
                            <Menu.Item key={item.name} onClick={item.action}>
                                {item.name}
                            </Menu.Item>
                        );
                    }
                })}
        </Menu>
    );
};

export default HeaderMenu;