import {combineReducers} from "redux";
import {bookReducer} from "./bookReducer";
import {genreReducer} from "./genreReducer";
import {authorReducer} from "./authorReducer";


export const rootReducer = combineReducers({
    book: bookReducer,
    author: authorReducer,
    genre: genreReducer,
});

export type RootState = ReturnType<typeof rootReducer>