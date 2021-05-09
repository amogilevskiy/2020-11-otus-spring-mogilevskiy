import React, {useEffect, useState} from "react";
import {Alert, Form, Input, Modal} from "antd";
import Genre from "../../domain/Genre";
import {useOnCloseAction} from "../../hooks/useOnCloseAction";
import GenreService from "../../services/GenreService";
import ErrorHandler from "../../services/ErrorHandler";

interface GenreFormProps {
    visible: boolean;
    onSaved: (genre: Genre) => void;
    onCancelled: () => void;
    genre?: Genre;
}


const GenreForm: React.FC<GenreFormProps> = ({visible, onSaved, onCancelled, genre}) => {

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
        if (genre) {
            form.setFieldsValue({
                'title': genre.title,
            });
        }
    }, [genre]);

    const title = genre ? "Edit genre" : "Add a genre";
    const okTitle = genre ? "Update" : "Create";

    const save = async () => {
        setError(undefined);
        setLoading(true);
        form.validateFields()
            .then(values => {
                return GenreService.save({...values, id: genre?.id});
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
            <Form form={form} layout="vertical" name="genreForm">
                <Form.Item name="title" label="Title" rules={[{required: true}]}>
                    <Input/>
                </Form.Item>
            </Form>
            {error ? <Alert message={error} type="error"/> : null}
        </Modal>
    );
};

export default GenreForm;