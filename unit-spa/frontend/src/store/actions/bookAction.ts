import {PaginatedList} from "../../services/PaginatedList";
import Book from "../../domain/Book";

export enum BookActionTypes {
    FETCH_BOOKS_REQUESTED = 'FETCH_BOOKS_REQUESTED',
    FETCH_BOOKS_COMPLETED = 'FETCH_BOOKS_COMPLETED',
    FETCH_BOOKS_FAILED = 'FETCH_BOOKS_FAILED',
}

interface FetchBooksAction {
    type: BookActionTypes.FETCH_BOOKS_REQUESTED;
}

interface FetchBooksSuccessAction {
    type: BookActionTypes.FETCH_BOOKS_COMPLETED;
    payload: PaginatedList<Book>
}

interface FetchBooksErrorAction {
    type: BookActionTypes.FETCH_BOOKS_FAILED;
    payload: string;
}

export type BookAction = FetchBooksAction | FetchBooksSuccessAction | FetchBooksErrorAction;