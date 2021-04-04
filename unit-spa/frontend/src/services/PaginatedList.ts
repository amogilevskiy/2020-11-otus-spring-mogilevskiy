import {Pagination} from "./Pagination";

export interface PaginatedList<T> extends Pagination {

    content: T[];

}


export const emptyPaginatedList = <T>(size = 20): PaginatedList<T> => {
    return {
        content: [],
        totalElements: 0,
        number: 0,
        size: size,
    };

};