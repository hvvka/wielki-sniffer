import MUTATION_TYPES from './mutation-types'

export default {
    [MUTATION_TYPES.SET_SEARCH_QUERY](state, data) {
        state.searchQuery = data;
        state.lastPerformedSearchWasSimple = true;
    },
    [MUTATION_TYPES.SET_SEARCH_DATA](state, data) {
        state.searchData = data;
        state.lastPerformedSearchWasSimple = false;
    },
    [MUTATION_TYPES.SET_SEARCH_BODY](state, data) {
        state.searchBody = data;
    },
    [MUTATION_TYPES.SET_SEARCH_RESULTS](state, data) {
        state.searchResults = data
    },
    [MUTATION_TYPES.APPEND_MORE_RESULTS](state, responseBody) {
        let searchResults = state.searchResults;
        responseBody.books.forEach(newBook => {
            searchResults.books.push(newBook)
        });
        state.searchResults = searchResults;
    },
    [MUTATION_TYPES.INCREASE_SEARCH_PAGE](state) {
        state.searchPageNumber += 1;
    },
    [MUTATION_TYPES.CLEAR_SEARCH](state) {
        state.lastPerformedSearchWasSimple = null;
        state.searchQuery = null;
        state.searchData = null;
        state.searchResults = null;
        state.searchPageNumber = 0;
    },
    [MUTATION_TYPES.RESET_SEARCH_PAGE_NUMBER](state) {
        state.searchPageNumber = 0;
    }
}
