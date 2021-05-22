import React, {useEffect, useState} from 'react';
import {Button, PageHeader} from 'antd';
import GenreList from "./GenreList";
import GenreForm from "./GenreForm";
import {useActions} from "../../hooks/useActions";
import {useTypedSelector} from "../../hooks/useTypedSelector";
import Genre from "../../domain/Genre";
import {useSearchPagination} from "../../hooks/useSearchPagination";
import ErrorPlaceholder from "../common/ErrorPlaceholder";

const GenrePage: React.FC = () => {

    const [formVisible, setFormVisible] = useState(false);
    const [selectedGenre, setSelectedGenre] = useState<Genre>();
    const {genres, error, loading} = useTypedSelector(state => state.genre);

    const {fetchGenres} = useActions();
    const {page, size, paginate} = useSearchPagination();

    useEffect(() => {
        fetchGenres({page, size});
    }, [page, size]);

    const onFormCancelledHandler = () => {
        showForm(false);
    };

    const onGenreSavedHandler = (genre: Genre) => {
        showForm(false);
        updateGenresWithDelay();
    };

    const onEditHandler = (genre: Genre) => {
        setSelectedGenre(genre);
        showForm(true);
    };

    const updateGenresWithDelay = () => {
        setTimeout(() => {
            fetchGenres();
        }, 500);
    };

    const showForm = (show: boolean) => {
        setFormVisible(show);
        if (!show) {
            setSelectedGenre(undefined);
        }
    };

    if (error) {
        return (
            <ErrorPlaceholder message={error}/>
        );
    }

    return (
        <>
            <PageHeader
                className="site-page-header"
                title="Genres"
                extra={[
                    <Button key="1" type="primary" onClick={() => showForm(true)}>
                        + Add genre
                    </Button>,
                ]}
            />
            <GenreList genres={genres} loading={loading} onEdit={onEditHandler} onPageChange={paginate}/>
            <GenreForm visible={formVisible} onSaved={onGenreSavedHandler} onCancelled={onFormCancelledHandler}
                       genre={selectedGenre}/>
        </>
    );
};

export default GenrePage;