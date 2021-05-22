import {AxiosInstance} from "axios";
import {PageRequest} from "./PageRequest";
import {PaginatedList} from "./PaginatedList";
import Comment from "../domain/Comment";
import axiosClient from "./axiosClient";

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

export default new CommentService(axiosClient);