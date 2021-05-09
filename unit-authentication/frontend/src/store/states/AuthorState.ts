import {PaginatedList} from "../../services/PaginatedList";
import Author from "../../domain/Author";

export interface AuthorState {
    authors: PaginatedList<Author>;
    loading: boolean;
    error?: string;
}