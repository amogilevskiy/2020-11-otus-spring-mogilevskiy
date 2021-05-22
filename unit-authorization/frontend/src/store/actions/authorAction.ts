import {PaginatedList} from "../../services/PaginatedList";
import Author from "../../domain/Author";

export enum AuthorActionTypes {
    FETCH_AUTHORS_REQUESTED = 'FETCH_AUTHORS_REQUESTED',
    FETCH_AUTHORS_COMPLETED = 'FETCH_AUTHORS_COMPLETED',
    FETCH_AUTHORS_FAILED = 'FETCH_AUTHORS_FAILED',
}


interface FetchAuthorsRequestedAction {
    type: AuthorActionTypes.FETCH_AUTHORS_REQUESTED;
}

interface FetchAuthorsCompletedAction {
    type: AuthorActionTypes.FETCH_AUTHORS_COMPLETED;
    payload: PaginatedList<Author>;
}

interface FetchAuthorsFailedAction {
    type: AuthorActionTypes.FETCH_AUTHORS_FAILED;
    payload: string;
}


export type AuthorAction = FetchAuthorsRequestedAction | FetchAuthorsCompletedAction | FetchAuthorsFailedAction;