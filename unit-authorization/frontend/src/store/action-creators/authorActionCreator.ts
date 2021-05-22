import {Dispatch} from "redux";
import {AuthorAction, AuthorActionTypes} from "../actions/authorAction";
import AuthorService from "../../services/AuthorService";
import {PageRequest} from "../../services/PageRequest";

export const fetchAuthors = (request?: PageRequest) => {
    return async (dispatch: Dispatch<AuthorAction>) => {
        try {
            dispatch({type: AuthorActionTypes.FETCH_AUTHORS_REQUESTED});
            const authors = await AuthorService.getAll(request);
            setTimeout(() => {
                dispatch({type: AuthorActionTypes.FETCH_AUTHORS_COMPLETED, payload: authors});
            }, 500);
        } catch (e) {
            dispatch({
                type: AuthorActionTypes.FETCH_AUTHORS_FAILED,
                payload: 'Error occurred while trying to fetch authors.',
            });
        }
    };
};
