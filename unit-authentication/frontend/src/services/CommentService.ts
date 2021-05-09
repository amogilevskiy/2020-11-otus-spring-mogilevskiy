import axios, {AxiosInstance} from "axios";
import {PageRequest} from "./PageRequest";
import {PaginatedList} from "./PaginatedList";
import applyCaseMiddleware from "axios-case-converter";
import Comment from "../domain/Comment";

class CommentService {

    private client: AxiosInstance;

    constructor(client: AxiosInstance) {
        this.client = client;
    }

    async getAll(bookId: number, request?: PageRequest): Promise<PaginatedList<Comment>> {
        const response = await this.client.get('/api/1.0/comments', {
            params: {
                bookId,
                ...request,
            },
        });
        return response.data;
    }

}

export default new CommentService(applyCaseMiddleware(axios.create()));