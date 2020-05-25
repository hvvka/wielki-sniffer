export default {
    getSearchData: state => state.searchData,
    getSearchQuery: state => state.searchQuery,
    getSearchResults: state => state.searchResults,
    getCurrentCategories: state => state.searchData.categories,
    getCurrentContributors: state => state.searchData.contributors,
    getFromDate: state => state.searchData.fromDate,
    getToDate: state => state.searchData.toDate,
}
