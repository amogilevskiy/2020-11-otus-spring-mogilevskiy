import {BookAction, BookActionTypes} from "../actions/bookAction";
import {emptyPaginatedList} from "../../services/PaginatedList";
import {BookState} from "../states/BookState";


const initialState: BookState = {
    books: emptyPaginatedList(),
    loading: true,
    error: undefined,
};

export const bookReducer = (state = initialState, action: BookAction): BookState => {
    switch (action.type) {
        case BookActionTypes.FETCH_BOOKS_REQUESTED:
            return {loading: true, error: undefined, books: state.books};
        case BookActionTypes.FETCH_BOOKS_COMPLETED:
            return {loading: false, error: undefined, books: action.payload};
        case BookActionTypes.FETCH_BOOKS_FAILED:
            return {loading: false, error: action.payload, books: state.books};
        default:
            return state;
    }
};