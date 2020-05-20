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
}
