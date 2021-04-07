import axios, {AxiosInstance} from "axios";
import Author from "../domain/Author";
import AuthorDto from "../dtos/AuthorDto";
import applyCaseMiddleware from "axios-case-converter";
import {PageRequest} from "./PageRequest";
import {PaginatedList} from "./PaginatedList";

class AuthorService {

    private client: AxiosInstance;

    constructor(client: AxiosInstance) {
        this.client = client;
    }

    async save(dto: AuthorDto): Promise<Author> {
        let response;
        if (dto.id) {
            console.log(dto);
            response = await this.client.patch(`/api/1.0/authors/${dto.id}`, dto);
        } else {
            response = await this.client.post('/api/1.0/authors', dto);
        }
        return response.data;
    }

    async getAll(request?: PageRequest): Promise<PaginatedList<Author>> {
        const response = await this.client.get('/api/1.0/authors', {
            params: {
                ...request,
            },
        });
        return response.data;
    }

}

export default new AuthorService(applyCaseMiddleware(axios.create()));