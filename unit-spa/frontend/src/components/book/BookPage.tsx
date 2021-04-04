import React, {useEffect, useState} from 'react';
import {useTypedSelector} from "../../hooks/useTypedSelector";
import {useActions} from "../../hooks/useActions";
import {Button, PageHeader} from 'antd';
import BookList from "./BookList";
import {fetchBooks} from "../../store/action-creators/bookActionCreator";
import Book from "../../domain/Book";
import BookForm from "./BookForm";
import {useSearchPagination} from "../../hooks/useSearchPagination";
import ErrorPlaceholder from "../common/ErrorPlaceholder";
import BookService from "../../services/BookService";
import CommentListPopup from "../comment/CommentListPopup";


const BookPage: React.FC = () => {

    const [formVisible, setFormVisible] = useState(false);
    const [commentsPopupVisible, setCommentsPopupVisible] = useState(false);
    const [selectedBook, setSelectedBook] = useState<Book>();
    const {books, error, loading} = useTypedSelector(state => state.book);

    const {fetchBooks} = useActions();
    const {page, size, paginate} = useSearchPagination();

    useEffect(() => {
        fetchBooks({page, size});
    }, [page, size]);

    const onFormCancelledHandler = () => {
        showForm(false);
    };

    const refreshData = () => {
        fetchBooks({page, size});
    };

    const onBookSavedHandler = (book: Book) => {
        showForm(false);
        refreshData();
    };

    const onCommentsPopupVisibleCancelledHandler = () => {
        showCommentsPopup(false);
    };

    const onClickHandler = (book: Book) => {
        setSelectedBook(book);
        showCommentsPopup(true);
    };

    const onEditHandler = (book: Book) => {
        setSelectedBook(book);
        showForm(true);
    };

    const onDeleteHandler = (book: Book) => {
        BookService.delete(book.id)
            .then(refreshData)
            .catch(() => {
            });
    };

    const showForm = (show: boolean) => {
        setFormVisible(show);
        if (!show) {
            setSelectedBook(undefined);
        }
    };

    const showCommentsPopup = (show: boolean) => {
        setCommentsPopupVisible(show);
        if (!show) {
            setSelectedBook(undefined);
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
                title="Books"
                extra={[
                    <Button key="1" type="primary" onClick={() => showForm(true)}>
                        + Add book
                    </Button>,
                ]}
            />
            <BookList books={books} loading={loading} onClick={onClickHandler} onEdit={onEditHandler}
                      onDelete={onDeleteHandler} onPageChange={paginate}/>
            <BookForm visible={formVisible} onSaved={onBookSavedHandler} onCancelled={onFormCancelledHandler}
                      book={selectedBook}/>
            <CommentListPopup book={selectedBook} visible={commentsPopupVisible}
                              onCancelled={onCommentsPopupVisibleCancelledHandler}/>
        </>
    );
};

export default BookPage;