import Book from "../../domain/Book";
import React, {useEffect, useState} from "react";
import {Alert, Form, Input, Modal, Select, Spin} from "antd";
import {useOnCloseAction} from "../../hooks/useOnCloseAction";
import BookService from "../../services/BookService";
import GenreService from "../../services/GenreService";
import Genre from "../../domain/Genre";
import Author from "../../domain/Author";
import AuthorService from "../../services/AuthorService";
import OperationState from "../common/OperationState";
import ErrorHandler from "../../services/ErrorHandler";

const {Option} = Select;

interface BookFormProps {
    visible: boolean;
    onSaved: (book: Book) => void;
    onCancelled: () => void;
    book?: Book;
}

const BookForm: React.FC<BookFormProps> = ({visible, onSaved, onCancelled, book}) => {

    const [form] = Form.useForm();
    const [error, setError] = useState<string>();
    const [loading, setLoading] = useState<boolean>(false);
    const [genres, setGenres] = useState<Genre[]>([]);
    const [authors, setAuthors] = useState<Author[]>([]);
    const [loadingFormValues, setLoadingFormValues] = useState<OperationState>(OperationState.IDLE);

    const onClose = () => {
        form.resetFields();
        setError(undefined);
        setLoading(false);
        setGenres([]);
        setAuthors([]);
        setLoadingFormValues(OperationState.IDLE);
    };

    useOnCloseAction(onClose, visible);

    useEffect(() => {
        if (book && !loadingFormValues) {
            form.setFieldsValue({
                'title': book.title,
                'genre_id': book.genre.id,
                'author_ids': book.authors.map(author => author.id),
            });
        }
    }, [book, loadingFormValues]);

    useEffect(() => {
        if (visible) {
            fetchValues();
        }
    }, [visible]);

    const title = book ? "Edit book" : "Add a book";
    const okTitle = book ? "Update" : "Create";

    const save = async () => {
        setError(undefined);
        setLoading(true);
        form.validateFields()
            .then(values => {
                return BookService.save({...values, id: book?.id});
            })
            .then(onSaved)
            .catch(e => {
                setError(ErrorHandler.getMessage(e, "Please check the form for errors."));
            })
            .finally(() => {
                setLoading(false);
            });
    };


    const fetchValues = () => {
        if (loadingFormValues === OperationState.IDLE || loadingFormValues === OperationState.FAILED) {
            setLoadingFormValues(OperationState.PROGRESS);
            Promise.all([GenreService.getAll(), AuthorService.getAll()])
                .then(v => {
                    setGenres(v[0].content);
                    setAuthors(v[1].content);
                    setLoadingFormValues(OperationState.COMPLETED);
                })
                .catch(e => {
                    setError(ErrorHandler.getMessage(e, "Please check the form for errors."));
                    setLoadingFormValues(OperationState.FAILED);
                });
        }

    };

    const onOkHandler = async () => {
        await save();
    };

    const renderGenres = (genres: Genre[]) => {
        return (genres.map(genre => {
            return (
                <Option value={genre.id} key={genre.id}>{genre.title}</Option>
            );
        }));
    };

    const renderAuthors = (authors: Author[]) => {
        return (authors.map(author => {
                return (
                    <Option value={author.id} key={author.id}>{author.firstName} {author.lastName}</Option>
                );
            })
        );
    };

    const renderFormOrSpinner = (loadingFormValues: OperationState) => {
        switch (loadingFormValues) {
            case OperationState.IDLE:
            case OperationState.PROGRESS:
                return (
                    <div style={{display: "flex", justifyContent: "center", alignItems: "center"}}>
                        <Spin/>
                    </div>);
            case OperationState.COMPLETED:
                return renderForm();
        }
    };

    const renderForm = () => {
        return (
            <Form form={form} layout="vertical" name="book_form">
                <Form.Item name="title" label="Title" rules={[{required: true}]}>
                    <Input/>
                </Form.Item>

                <Form.Item name="genre_id" label="Genre" rules={[{required: true}]}>
                    <Select style={{width: '100%'}}
                            placeholder="Please select a genre">
                        {renderGenres(genres)}
                    </Select>
                </Form.Item>

                <Form.Item name="author_ids" label="Authors" rules={[{required: true}]}>
                    <Select mode="multiple"
                            allowClear
                            style={{width: '100%'}}
                            placeholder="Please select authors">
                        {renderAuthors(authors)}
                    </Select>
                </Form.Item>

            </Form>
        );
    };

    const renderError = (error: string | undefined) => {
        return error ? <Alert message={error} type="error"/> : null;
    };

    const okButtonDisabled = loadingFormValues !== OperationState.COMPLETED;

    return (
        <Modal title={title} visible={visible} onCancel={onCancelled}
               okText={okTitle}
               onOk={onOkHandler}
               okButtonProps={{disabled: okButtonDisabled}}
               confirmLoading={loading}>
            {renderFormOrSpinner(loadingFormValues)}
            {renderError(error)}
        </Modal>
    );
};

export default BookForm;