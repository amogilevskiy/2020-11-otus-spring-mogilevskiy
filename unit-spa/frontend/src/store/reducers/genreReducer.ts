import {GenreAction, GenreActionTypes} from "../actions/genreAction";

import {emptyPaginatedList} from "../../services/PaginatedList";
import {GenreState} from "../states/GenreState";


const initialState: GenreState = {
    genres: emptyPaginatedList(),
    loading: true,
    error: undefined,
};

export const genreReducer = (state = initialState, action: GenreAction): GenreState => {
    switch (action.type) {
        case GenreActionTypes.FETCH_GENRES_REQUESTED:
            return {loading: true, error: undefined, genres: state.genres};
        case GenreActionTypes.FETCH_GENRES_COMPLETED:
            return {loading: false, error: undefined, genres: action.payload};
        case GenreActionTypes.FETCH_GENRES_FAILED:
            return {loading: false, error: action.payload, genres: state.genres};
        default:
            return state;
    }
};
