import * as BookActionCreators from './bookActionCreator';
import * as AuthorActionCreators from './authorActionCreator';
import * as GenreActionCreators from './genreActionCreator';
import * as AuthActionCreators from './authActionCreator';

export default {
    ...BookActionCreators,
    ...AuthorActionCreators,
    ...GenreActionCreators,
    ...AuthActionCreators,
};