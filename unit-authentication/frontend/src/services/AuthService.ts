import {AxiosInstance} from "axios";
import LoginDto from "../dtos/LoginDto";
import User from "../domain/User";
import axiosClient from "./axiosClient";

class AuthService {

    private client: AxiosInstance;

    constructor(client: AxiosInstance) {
        this.client = client;
    }

    async login(dto: LoginDto): Promise<User> {
        const response = await this.client.post('/api/1.0/login', null, {params: {...dto}});
        return response.data;
    }

    async logout(): Promise<void> {
        const response = await this.client.post('/api/1.0/logout');
        return response.data;
    }

    async getProfile(): Promise<User> {
        const response = await this.client.get('/api/1.0/profile');
        return response.data;
    }

}

export default new AuthService(axiosClient);