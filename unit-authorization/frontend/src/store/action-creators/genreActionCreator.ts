import {Dispatch} from "redux";
import {GenreAction, GenreActionTypes} from "../actions/genreAction";
import GenreService from "../../services/GenreService";
import {PageRequest} from "../../services/PageRequest";

export const fetchGenres = (request?: PageRequest) => {
    return async (dispatch: Dispatch<GenreAction>) => {
        try {
            dispatch({type: GenreActionTypes.FETCH_GENRES_REQUESTED});
            const genres = await GenreService.getAll(request);
            setTimeout(() => {
                dispatch({type: GenreActionTypes.FETCH_GENRES_COMPLETED, payload: genres});
            }, 500);
        } catch (e) {
            dispatch({
                type: GenreActionTypes.FETCH_GENRES_FAILED,
                payload: 'Error occurred while trying to fetch genres.'
            });
        }
    };
};
