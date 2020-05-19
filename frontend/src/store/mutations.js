import MUTATION_TYPES from './mutation-types'

export default {
    [MUTATION_TYPES.SET_SEARCH_DATA](state, data) {
        state.searchData = data
    },
    [MUTATION_TYPES.SET_SEARCH_RESULTS](state, data) {
        state.searchResults = data
    },
    [MUTATION_TYPES.APPEND_MORE_RESULTS](state, data) {
        state.searchResults = data;
    }
}
