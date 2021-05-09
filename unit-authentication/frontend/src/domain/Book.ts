import Genre from "./Genre";
import Author from "./Author";

interface Book {
    id: number;
    title: string;
    genre: Genre;
    authors: Author[];
}

export default Book;