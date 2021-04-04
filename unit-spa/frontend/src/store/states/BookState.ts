import {PaginatedList} from "../../services/PaginatedList";
import Book from "../../domain/Book";

export interface BookState {
    books: PaginatedList<Book>;
    loading: boolean;
    error?: string;
}
