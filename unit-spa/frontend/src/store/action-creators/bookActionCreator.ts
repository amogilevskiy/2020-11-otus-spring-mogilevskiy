import {Dispatch} from "redux";
import {BookAction, BookActionTypes} from "../actions/bookAction";
import BookService from "../../services/BookService";
import {PageRequest} from "../../services/PageRequest";

export const fetchBooks = (request?: PageRequest) => {
    return async (dispatch: Dispatch<BookAction>) => {
        try {
            dispatch({type: BookActionTypes.FETCH_BOOKS_REQUESTED});
            const books = await BookService.getAll(request);
            setTimeout(() => {
                dispatch({type: BookActionTypes.FETCH_BOOKS_COMPLETED, payload: books});
            }, 500);
        } catch (e) {
            dispatch({
                type: BookActionTypes.FETCH_BOOKS_FAILED,
                payload: 'Error occurred while trying to fetch books.',
            });
        }
    };
};
