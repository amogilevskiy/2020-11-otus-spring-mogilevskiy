import Book from "../../domain/Book";
import React, {useEffect, useState} from "react";
import {Alert, List, Modal, Spin} from "antd";
import {useOnCloseAction} from "../../hooks/useOnCloseAction";
import OperationState from "../common/OperationState";
import {emptyPaginatedList, PaginatedList} from "../../services/PaginatedList";
import CommentService from "../../services/CommentService";
import Comment from "../../domain/Comment";
import ErrorHandler from "../../services/ErrorHandler";

interface CommentListPopupProps {
    visible: boolean;
    onCancelled: () => void;
    book?: Book;
}

const CommentListPopup: React.FC<CommentListPopupProps> = ({visible, onCancelled, book}) => {

    const [error, setError] = useState<string>();
    const [comments, setComments] = useState<PaginatedList<Comment>>(emptyPaginatedList());
    const [loading, setLoading] = useState<OperationState>(OperationState.IDLE);

    const onClose = () => {
        setError(undefined);
        setComments(emptyPaginatedList());
        setLoading(OperationState.IDLE);
    };

    useOnCloseAction(onClose, visible);

    useEffect(() => {
        if (visible) {
            fetchComments();
        }
    }, [visible]);

    const fetchComments = () => {
        if (book) {
            if (loading === OperationState.IDLE || loading === OperationState.FAILED) {
                setLoading(OperationState.PROGRESS);
                CommentService.getAll(book.id)
                    .then(comments => {
                        setComments(comments);
                        setLoading(OperationState.COMPLETED);
                    })
                    .catch(e => {
                        setError(ErrorHandler.getMessage(e, "Please check the form for errors."));
                        setLoading(OperationState.FAILED);
                    });
            }
        }
    };

    const renderComments = (comments: PaginatedList<Comment>) => {
        return (
            <List
                itemLayout="horizontal"
                dataSource={comments.content}
                renderItem={item => (
                    <List.Item>
                        <List.Item.Meta
                            title="Anonymous"
                            description={item.text}
                        />
                    </List.Item>
                )}
            />
        );
    };

    const renderCommentsOrSpinner = (loadingFormValues: OperationState, comments: PaginatedList<Comment>) => {
        switch (loadingFormValues) {
            case OperationState.IDLE:
            case OperationState.PROGRESS:
                return (
                    <div style={{display: "flex", justifyContent: "center", alignItems: "center"}}>
                        <Spin/>
                    </div>);
            case OperationState.COMPLETED:
                return renderComments(comments);
        }
    };

    const renderError = (error: string | undefined) => {
        return error ? <Alert message={error} type="error"/> : null;
    };

    const title = `Comments for: ${book?.title}`;

    return (
        <Modal title={title} visible={visible} onOk={onCancelled}
               onCancel={onCancelled}
               cancelButtonProps={{style: {display: 'none'}}}>
            {renderCommentsOrSpinner(loading, comments)}
            {renderError(error)}
        </Modal>
    );
};

export default CommentListPopup;