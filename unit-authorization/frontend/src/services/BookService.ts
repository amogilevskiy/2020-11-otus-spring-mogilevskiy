import axios, {AxiosInstance} from "axios";
import applyCaseMiddleware from "axios-case-converter";
import BookDto from "../dtos/BookDto";
import Book from "../domain/Book";
import {PaginatedList} from "./PaginatedList";
import {PageRequest} from "./PageRequest";

class BookService {

    private client: AxiosInstance;

    constructor(client: AxiosInstance) {
        this.client = client;
    }

    async save(dto: BookDto): Promise<Book> {
        let response;
        if (dto.id) {
            response = await this.client.patch(`/api/1.0/books/${dto.id}`, dto);
        } else {
            response = await this.client.post('/api/1.0/books', dto);
        }
        return response.data;
    }

    async getAll(request?: PageRequest): Promise<PaginatedList<Book>> {
        const response = await this.client.get('/api/1.0/books', {
            params: {
                ...request,
            },
        });
        return response.data;
    }

    async delete(id: number): Promise<boolean> {
        await this.client.delete(`/api/1.0/books/${id}`);
        return true;
    }

}

export default new BookService(applyCaseMiddleware(axios.create()));