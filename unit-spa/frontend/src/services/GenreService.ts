import GenreDto from "../dtos/GenreDto";
import axios, {AxiosInstance} from "axios";
import Genre from "../domain/Genre";
import applyCaseMiddleware from "axios-case-converter";
import {PageRequest} from "./PageRequest";
import {PaginatedList} from "./PaginatedList";

class GenreService {

    private client: AxiosInstance;

    constructor(client: AxiosInstance) {
        this.client = client;
    }

    async save(dto: GenreDto): Promise<Genre> {
        let response;
        if (dto.id) {
            response = await this.client.patch(`/api/1.0/genres/${dto.id}`, dto);
        } else {
            response = await this.client.post('/api/1.0/genres', dto);
        }
        return response.data;
    }

    async getAll(request?: PageRequest): Promise<PaginatedList<Genre>> {
        const response = await this.client.get('/api/1.0/genres', {
            params: {
                ...request,
            },
        });
        return response.data;
    }

}

export default new GenreService(applyCaseMiddleware(axios.create()));
