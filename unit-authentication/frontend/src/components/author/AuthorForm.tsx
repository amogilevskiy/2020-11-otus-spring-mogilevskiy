import React, {useEffect, useState} from "react";
import {Alert, Form, Input, Modal} from "antd";
import {useOnCloseAction} from "../../hooks/useOnCloseAction";
import Author from "../../domain/Author";
import AuthorService from "../../services/AuthorService";
import ErrorHandler from "../../services/ErrorHandler";

interface AuthorFormProps {
    visible: boolean;
    onSaved: (author: Author) => void;
    onCancelled: () => void;
    author?: Author;
}


const AuthorForm: React.FC<AuthorFormProps> = ({visible, onSaved, onCancelled, author}) => {

    const [form] = Form.useForm();
    const [error, setError] = useState<string>();
    const [loading, setLoading] = useState<boolean>(false);

    const onClose = () => {
        form.resetFields();
        setError(undefined);
        setLoading(false);
    };

    useOnCloseAction(onClose, visible);

    useEffect(() => {
        if (author) {
            form.setFieldsValue({
                'first_name': author.firstName,
                'last_name': author.lastName,
                'middle_name': author.middleName,
            });
        }
    }, [author]);

    const title = author ? "Edit author" : "Add an author";
    const okTitle = author ? "Update" : "Create";

    const save = async () => {
        setError(undefined);
        setLoading(true);
        form.validateFields()
            .then(values => {
                return AuthorService.save({...values, id: author?.id});
            })
            .then(onSaved)
            .catch(e => {
                setError(ErrorHandler.getMessage(e, "Please check the form for errors."));
            })
            .finally(() => {
                setLoading(false);
            });
    };

    const onOkHandler = async () => {
        await save();
    };

    return (
        <Modal title={title} visible={visible} onCancel={onCancelled}
               okText={okTitle}
               onOk={onOkHandler}
               confirmLoading={loading}>
            <Form form={form} layout="vertical" name="authorForm">
                <Form.Item name="first_name" label="First name" rules={[{required: true}]}>
                    <Input/>
                </Form.Item>
                <Form.Item name="last_name" label="Last name" rules={[{required: true}]}>
                    <Input/>
                </Form.Item>
                <Form.Item name="middle_name" label="Middle name">
                    <Input/>
                </Form.Item>
            </Form>
            {error ? <Alert message={error} type="error"/> : null}
        </Modal>
    );
};

export default AuthorForm;