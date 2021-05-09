import {PaginatedList} from "../../services/PaginatedList";
import Genre from "../../domain/Genre";

export enum GenreActionTypes {
    FETCH_GENRES_REQUESTED = 'FETCH_GENRES_REQUESTED',
    FETCH_GENRES_COMPLETED = 'FETCH_GENRES_COMPLETED',
    FETCH_GENRES_FAILED = 'FETCH_GENRES_FAILED',
}


interface FetchGenresRequestedAction {
    type: GenreActionTypes.FETCH_GENRES_REQUESTED;
}

interface FetchGenresCompletedAction {
    type: GenreActionTypes.FETCH_GENRES_COMPLETED;
    payload: PaginatedList<Genre>;
}

interface FetchGenresFailedAction {
    type: GenreActionTypes.FETCH_GENRES_FAILED;
    payload: string;
}


export type GenreAction = FetchGenresRequestedAction | FetchGenresCompletedAction | FetchGenresFailedAction;