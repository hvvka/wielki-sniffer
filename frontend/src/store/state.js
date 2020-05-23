export default {
    lastPerformedSearchWasSimple: null, // this flag is used in fetching more data method to know if get
                                        // next page using searchQuery or searchData
    searchQuery: null,                  // last search query string
    searchData: null,                   // last entered advanced search data
    searchBody: {
        timestampRange: {},
        sortField: {},
        filterFields: [],
        searchFields: []
    },                                  // last search request body
    searchResults: null,                // actual displaying data
    searchPageNumber: 0,                // last fetched page
    searchPageSize: 6,                  // how many pages per result. should be changed programmatically
    coverImage: null,
}
