import {useHistory, useLocation} from "react-router-dom";

export const useSearchPagination = () => {

    const history = useHistory();
    const location = useLocation();

    const searchParams = new URLSearchParams(location.search);
    const page = Number(searchParams.get('page')) || 1;
    const size = Number(searchParams.get('size')) || undefined;

    const paginate = (page: number) => {
        let search = `page=${page}`;
        if (size) {
            search += `&size=${size}`;
        }
        history.push({
            search: search,
        });
    };

    return {
        page,
        size,
        paginate,
    };
};