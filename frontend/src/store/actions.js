import Vue from 'vue'
import MUTATION_TYPES from './mutation-types'
//'http://www.mocky.io/v2/5ec3b373300000850039c302'

const buildSearchRequestBody = (title, notTitle, text, notText, fromDate, toDate, contributors, categories, sortField) => {
    let requestBody = {
        timestampRange: {
            from: fromDate,
            to: toDate
        },
        sortField: sortField,
        filterFields: [],
        searchFields: []
    };
    let addSearchedData = (array, notArray, fieldName, requestBody) => {
        if (array !== []) {
            var mustArray = [];
            var tmp = '';

            array.forEach(chipsElement => {
                var splitted = chipsElement.split(' ');

                splitted.forEach(splittedChipsElement => {
                    if (splittedChipsElement.startsWith('"')) tmp = splittedChipsElement;
                    else if (tmp !== '') {
                        mustArray.push(tmp + " " + splittedChipsElement);
                        tmp = '';
                    } else mustArray.push(splittedChipsElement);
                });
                requestBody.searchFields.push({
                    field: fieldName,
                    should: [
                        {
                            must: mustArray,
                            not: notArray.includes(chipsElement)
                        }
                    ]
                });
                mustArray = [];
                tmp = '';
            });
        }
    };
    addSearchedData(title, notTitle, 'TITLE', requestBody);
    addSearchedData(text, notText, 'TEXT', requestBody);
    let addFilteredData = (array, fieldName, requestBody) => {
        array.forEach(el => {
            requestBody.filterFields.push({
                field: fieldName,
                value: el
            })
        })
    };
    addFilteredData(contributors, 'CONTRIBUTOR', requestBody);
    addFilteredData(categories, 'CATEGORIES', requestBody);

    if (fromDate === null || toDate === null) {
        delete requestBody.timestampRange;
    }

    return requestBody;
};

const buildSearchRequestBodyFromState = (searchData) => {
    return buildSearchRequestBody(
        searchData.title,
        searchData.notTitle,
        searchData.text,
        searchData.notText,
        searchData.fromDate,
        searchData.toDate,
        searchData.contributors,
        searchData.categories,
        searchData.sortField
    )
};

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
                Vue.axios.get(`http://localhost:8080/v1/search?query=${state.searchQuery}&pageSize=${state.searchPageSize}&pageNumber=${state.searchPageNumber + 1}`)
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
        } else if (state.lastPerformedSearchWasSimple === false) {
            return new Promise((resolve, reject) => {
                Vue.axios.post(`http://localhost:8080/v1/search?pageSize=${state.searchPageSize}&pageNumber=${state.searchPageNumber + 1}`, state.searchBody)
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
                    });
            });
        } else return new Promise((resolve, reject) => reject());

    },
    advancedSearch: ({commit, state}, data) => {
        commit(MUTATION_TYPES.CLEAR_SEARCH);

        let requestBody = buildSearchRequestBody(
            data.title,
            data.notTitle,
            data.text,
            data.notText,
            data.fromDate,
            data.toDate,
            data.contributors,
            data.categories,
            data.sortField
        );

        return new Promise((resolve, reject) => {
            commit(MUTATION_TYPES.SET_SEARCH_DATA, data);
            Vue.axios.post(`http://localhost:8080/v1/search?pageSize=${state.searchPageSize}&pageNumber=0`, requestBody)
                .then(response => {
                    commit(MUTATION_TYPES.SET_SEARCH_RESULTS, response.data);
                    commit(MUTATION_TYPES.SET_SEARCH_BODY, requestBody);
                    resolve()
                })
                .catch(error => {
                    console.log(error);
                    reject()
                })
        });

    },
    updateResults: ({commit, state}) => {
        return new Promise((resolve, reject) => {
            Vue.axios.post(`http://localhost:8080/v1/search?pageSize=${state.searchPageSize}&pageNumber=0`, state.searchBody)
                .then(response => {
                    commit(MUTATION_TYPES.SET_SEARCH_RESULTS, response.data);
                    resolve()
                })
                .catch(error => {
                    console.log(error);
                    reject()
                })
        });
    },
    addCategory: ({commit, dispatch, state}, data) => {
        let newSearchData = state.searchData;

        if (data !== null) {
            newSearchData.categories = [data];
        } else {
            newSearchData.categories = [];
        }

        let requestBody = buildSearchRequestBodyFromState(state.searchData);

        commit(MUTATION_TYPES.RESET_SEARCH_PAGE_NUMBER);
        commit(MUTATION_TYPES.SET_SEARCH_DATA, newSearchData);
        commit(MUTATION_TYPES.SET_SEARCH_BODY, requestBody);

        return dispatch('updateResults')
    },
    removeCategory: ({dispatch}) => {
        return dispatch('addCategory', null)
    },
    addContributor: ({commit, dispatch, state}, data) => {
        let newSearchData = state.searchData;

        if (data !== null) {
            newSearchData.contributors = [data];
        } else {
            newSearchData.contributors = [];
        }

        let requestBody = buildSearchRequestBodyFromState(state.searchData);

        commit(MUTATION_TYPES.RESET_SEARCH_PAGE_NUMBER);
        commit(MUTATION_TYPES.SET_SEARCH_DATA, newSearchData);
        commit(MUTATION_TYPES.SET_SEARCH_BODY, requestBody);

        return dispatch('updateResults')
    },
    removeContributor: ({dispatch}) => {
        return dispatch('addContributor', null)
    },
    setSort: ({commit, dispatch, state}, data) => {
        let newSearchData = state.searchData;
        newSearchData.sortField = data;

        let requestBody = buildSearchRequestBodyFromState(state.searchData);

        commit(MUTATION_TYPES.RESET_SEARCH_PAGE_NUMBER);
        commit(MUTATION_TYPES.SET_SEARCH_DATA, newSearchData);
        commit(MUTATION_TYPES.SET_SEARCH_BODY, requestBody);

        return dispatch('updateResults')
    },
    setFromDate: ({commit, dispatch, state}, data) => {
        let newSearchData = state.searchData;
        newSearchData.fromDate = data;

        let requestBody = buildSearchRequestBodyFromState(state.searchData);

        commit(MUTATION_TYPES.RESET_SEARCH_PAGE_NUMBER);
        commit(MUTATION_TYPES.SET_SEARCH_DATA, newSearchData);
        commit(MUTATION_TYPES.SET_SEARCH_BODY, requestBody);

        return dispatch('updateResults')
    },

    setToDate: ({commit, dispatch, state}, data) => {
        let newSearchData = state.searchData;
        newSearchData.toDate = data;

        let requestBody = buildSearchRequestBodyFromState(state.searchData);

        commit(MUTATION_TYPES.RESET_SEARCH_PAGE_NUMBER);
        commit(MUTATION_TYPES.SET_SEARCH_DATA, newSearchData);
        commit(MUTATION_TYPES.SET_SEARCH_BODY, requestBody);

        return dispatch('updateResults')
    },
}
