export default {
    getSearchData: state => state.searchData,
    getSearchQuery: state => state.searchQuery,
    getSearchResults: state => state.searchResults,
    getCurrentCategories: state => state.searchData && state.searchData.categories,
    getCurrentContributors: state => state.searchData && state.searchData.contributors,
    getFromDate: state => state.searchData && state.searchData.fromDate,
    getToDate: state => state.searchData && state.searchData.toDate,
    getFullScreenLoader: state => state.fullScreenLoader,
}
