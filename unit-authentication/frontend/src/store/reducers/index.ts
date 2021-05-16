import {combineReducers} from "redux";
import {bookReducer} from "./bookReducer";
import {genreReducer} from "./genreReducer";
import {authorReducer} from "./authorReducer";
import {authReducer} from "./authReducer";


export const rootReducer = combineReducers({
    book: bookReducer,
    author: authorReducer,
    genre: genreReducer,
    auth: authReducer,
});

export type RootState = ReturnType<typeof rootReducer>