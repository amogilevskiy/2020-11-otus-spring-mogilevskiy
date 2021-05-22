import {AuthorState} from "../states/AuthorState";
import {AuthorAction, AuthorActionTypes} from "../actions/authorAction";
import {emptyPaginatedList} from "../../services/PaginatedList";


const initialState: AuthorState = {
    authors: emptyPaginatedList(),
    loading: true,
    error: undefined,
};

export const authorReducer = (state = initialState, action: AuthorAction): AuthorState => {
    switch (action.type) {
        case AuthorActionTypes.FETCH_AUTHORS_REQUESTED:
            return {loading: true, error: undefined, authors: state.authors};
        case AuthorActionTypes.FETCH_AUTHORS_COMPLETED:
            return {loading: false, error: undefined, authors: action.payload};
        case AuthorActionTypes.FETCH_AUTHORS_FAILED:
            return {loading: false, error: action.payload, authors: state.authors};
        default:
            return state;
    }
};
