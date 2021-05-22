import React, {useEffect} from "react";
import {Button, Form, Input, message} from 'antd';
import {useTypedSelector} from "../../hooks/useTypedSelector";
import {useActions} from "../../hooks/useActions";
import {Redirect} from "react-router";

const layout = {
    labelCol: {span: 8},
    wrapperCol: {span: 16},
};
const tailLayout = {
    wrapperCol: {offset: 8, span: 16},
};

const LoginPage: React.FC = () => {

    const {user, error} = useTypedSelector(state => state.auth);

    const {login} = useActions();

    const onFinish = (values: any) => {
        login(values);
    };

    useEffect(() => {
        if (error) {
            message.error(error);
        }
    }, [error]);

    const renderForm = () => {
        return (
            <>
                <div style={{display: 'flex', justifyContent: 'center'}}>
                    <Form
                        {...layout}
                        style={{width: '600px'}}
                        name="basic"
                        onFinish={onFinish}
                    >
                        <Form.Item
                            label="Username"
                            name="username"
                            rules={[{required: true, message: 'Please input your username!'}]}
                        >
                            <Input/>
                        </Form.Item>

                        <Form.Item
                            label="Password"
                            name="password"
                            rules={[{required: true, message: 'Please input your password!'}]}
                        >
                            <Input.Password/>
                        </Form.Item>

                        <Form.Item {...tailLayout}>
                            <Button type="primary" htmlType="submit">
                                Login
                            </Button>
                        </Form.Item>
                    </Form>
                </div>
            </>
        );
    };

    if (user) {
        return <Redirect from="*" to="/"/>;
    } else {
        return renderForm();
    }


};
export default LoginPage;