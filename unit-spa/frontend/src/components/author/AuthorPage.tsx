import React, {useEffect, useState} from "react";
import {useTypedSelector} from "../../hooks/useTypedSelector";
import {useActions} from "../../hooks/useActions";
import {Button, PageHeader} from "antd";
import Author from "../../domain/Author";
import AuthorList from "./AuthorList";
import AuthorForm from "./AuthorForm";
import ErrorPlaceholder from "../common/ErrorPlaceholder";
import {useSearchPagination} from "../../hooks/useSearchPagination";

const AuthorPage: React.FC = () => {

    const [formVisible, setFormVisible] = useState(false);
    const [selectedAuthor, setSelectedAuthor] = useState<Author>();
    const {authors, error, loading} = useTypedSelector(state => state.author);

    const {fetchAuthors} = useActions();

    const {page, size, paginate} = useSearchPagination();

    useEffect(() => {
        fetchAuthors({page, size});
    }, [page, size]);

    const onFormCancelledHandler = () => {
        showForm(false);
    };

    const onAuthorSavedHandler = (author: Author) => {
        showForm(false);
        fetchAuthors({page, size});
    };

    const onEditHandler = (author: Author) => {
        setSelectedAuthor(author);
        showForm(true);
    };

    const showForm = (show: boolean) => {
        setFormVisible(show);
        if (!show) {
            setSelectedAuthor(undefined);
        }
    };

    if (error) {
        return (
            <ErrorPlaceholder message={error}/>
        );
    }

    return (
        <>
            <PageHeader
                className="site-page-header"
                title="Authors"
                extra={[
                    <Button key="1" type="primary" onClick={() => showForm(true)}>
                        + Add author
                    </Button>,
                ]}
            />
            <AuthorList authors={authors} loading={loading} onEdit={onEditHandler} onPageChange={paginate}/>
            <AuthorForm visible={formVisible} onSaved={onAuthorSavedHandler} onCancelled={onFormCancelledHandler}
                        author={selectedAuthor}/>
        </>
    );
};

export default AuthorPage;