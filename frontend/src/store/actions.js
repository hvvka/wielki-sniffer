import Vue from 'vue'
import MUTATION_TYPES from './mutation-types'

export default {
    search: ({commit}, searchString) =>
        new Promise((resolve, reject) => {
            commit(MUTATION_TYPES.SET_SEARCH_DATA, searchString);
            Vue.axios.get('http://www.mocky.io/v2/5ec3b373300000850039c302')
                .then(response => {
                    commit(MUTATION_TYPES.SET_SEARCH_RESULTS, response.data);
                    resolve()
                })
                .catch(error => {
                    console.log(error);
                    reject()
                })

        }),
    moreResults: ({commit, state}) => {
        // :O
        var mergeSearchResults = (searchResults, newSearchResults, mergingProperty) => {
            newSearchResults[mergingProperty].forEach(newItem => {
                var found = searchResults[mergingProperty].find(it => it.name === newItem.name);
                if (found) found.count += newItem.count;
                else searchResults[mergingProperty].push(newItem)
            });
        };
        return new Promise((resolve, reject) => {
            Vue.axios.get('http://www.mocky.io/v2/5ec3b373300000850039c302')
                .then(response => {
                    let searchResults = state.searchResults;
                    let newData = response.data;

                    newData.books.forEach(newBook => {
                        searchResults.books.push(newBook)
                    });
                    mergeSearchResults(searchResults, newData, 'contributors');
                    mergeSearchResults(searchResults, newData, 'categories');

                    commit(MUTATION_TYPES.SET_SEARCH_RESULTS, searchResults);
                    resolve()
                })
                .catch(error => {
                    console.log(error);
                    reject()
                })
        })
    }
}
