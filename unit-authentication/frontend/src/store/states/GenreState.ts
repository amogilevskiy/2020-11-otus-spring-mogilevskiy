import {PaginatedList} from "../../services/PaginatedList";
import Genre from "../../domain/Genre";

export interface GenreState {
    genres: PaginatedList<Genre>;
    loading: boolean;
    error?: string;
}