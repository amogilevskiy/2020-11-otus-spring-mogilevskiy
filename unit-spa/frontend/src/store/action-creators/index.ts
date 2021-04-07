import * as BookActionCreators from './bookActionCreator';
import * as AuthorActionCreators from './authorActionCreator';
import * as GenreActionCreators from './genreActionCreator';

export default {
    ...BookActionCreators,
    ...AuthorActionCreators,
    ...GenreActionCreators,
};