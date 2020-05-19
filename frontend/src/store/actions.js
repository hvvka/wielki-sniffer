import Vue from 'vue'
import MUTATION_TYPES from './mutation-types'
//'http://www.mocky.io/v2/5ec3b373300000850039c302'

export default {
    simpleSearch: ({commit, state}, searchString) => {
        commit(MUTATION_TYPES.CLEAR_SEARCH);
        return new Promise((resolve, reject) => {
            commit(MUTATION_TYPES.SET_SEARCH_QUERY, searchString);
            Vue.axios.get(`http://localhost:8080/v1/search?query=${searchString}&pageSize=${state.searchPageSize}&pageNumber=0`)
                .then(response => {
                    commit(MUTATION_TYPES.SET_SEARCH_RESULTS, response.data);
                    resolve()
                })
                .catch(error => {
                    console.log(error);
                    reject()
                })
        })
    },
    moreResults: ({commit, state}) => {
        if (state.lastPerformedSearchWasSimple === true) {
            return new Promise((resolve, reject) => {
                Vue.axios.get(`http://localhost:8080/v1/search?query=${state.searchQuery}&pageSize=${state.searchPageNumber + 1}&pageNumber=${state.searchPageSize}`)
                    .then(response => {

                        if (response.data.books.length !== 0) {
                            commit(MUTATION_TYPES.INCREASE_SEARCH_PAGE);
                        }

                        commit(MUTATION_TYPES.APPEND_MORE_RESULTS, response.data);
                        resolve()
                    })
                    .catch(error => {
                        console.log(error);
                        reject()
                    })
            });

        } else {
            console.error('NotImplemented Exception.')
        }

    },
    advancedSearch: () => {

    },
}
