interface BookDto {
    id?: number;
    title: string;
    genreId?: number;
    authorIds?: number[];
}

export default BookDto;