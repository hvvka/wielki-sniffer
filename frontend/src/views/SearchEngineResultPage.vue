<template>
    <v-container fluid v-if="getSearchResults">
        <v-row justify="center">
            <v-col cols="2">
                <div id="leftPanel">
                    <h3 class="mt-1">Filters</h3>
                    <div class="mt-7">
                        <h4>Contributors</h4>
                        <div v-for="contributor in getSearchResults.contributors"
                             :key="'contributor_'+contributor.name">
                            <span class="link" @click="contributorClickHandler">{{contributor.name}} ({{contributor.count}})</span>
                        </div>

                        <h4>Publication date</h4>
                        <div>2001 - 2019</div>
                        <div>---*------*----</div>

                        <h4>Category</h4>
                        <div v-for="category in getSearchResults.categories" :key="'category_'+category.name">
                            <span class="link"
                                  @click="categoryClickHandler">{{category.name}} ({{category.count}})</span>
                        </div>
                    </div>
                </div>
            </v-col>
            <v-col cols="7">
                <v-row dense>
                    <v-col>
                        <h3 class="header-panel ma-0">Results (37)</h3>
                    </v-col>
                    <v-spacer></v-spacer>
                    <v-col class="text-right">
                        <Dropdown v-model="sortBy" :options="sortOptions"
                                  optionLabel="displayOption"
                                  optionValue="value"
                                  placeholder="Sort by" width="200px"></Dropdown>
                        <v-icon
                                class="ml-2"
                                @click="sortAscending = !sortAscending">
                            {{ sortAscending ? 'mdi-sort-alphabetical-ascending' : 'mdi-sort-alphabetical-descending'}}
                        </v-icon>
                    </v-col>
                </v-row>

                <v-row>
                    <v-col v-for="(book, index) in getSearchResults.books" :key="index" xs="12" sm="12" md="6" lg="6"
                           xl="6">
                        <BookCard :book="book"></BookCard>
                    </v-col>
                </v-row>
                <v-row justify="center" v-if="loading">
                    <div class="lds-ripple">
                        <div></div>
                        <div></div>
                    </div>
                </v-row>

            </v-col>
        </v-row>
    </v-container>
</template>

<script>
    import BookCard from '../components/BookCard'
    import {mapGetters} from 'vuex'

    export default {
        name: "SearchEngineResultPage",
        components: {
            BookCard
        },
        computed: {
            ...mapGetters(['getSearchResults', 'getSearchData']),
        },
        data() {
            return {
                sortBy: 'RELEVANCE',
                sortAscending: true,
                sortOptions: [{
                    displayOption: 'Relevance',
                    value: 'RELEVANCE'
                }, {
                    displayOption: 'Created date',
                    value: 'TIMESTAMP',
                }],
                searchResults: [{
                    id: 1,
                    result: 'foo'
                }, {
                    id: 2,
                    result: 'foo'
                }, {
                    id: 3,
                    result: 'foo'
                }],
                pl: {
                    firstDayOfWeek: 1,
                    dayNames: ["Niedziela", "Poniedziałek", "Wtorek", "Środa", "Czwartek", "Piątek", "Sobota"],
                    dayNamesShort: ["Nie", "Pon", "Wto", "Śro", "Czw", "Pią", "Sob"],
                    dayNamesMin: ["Nd","Pn","Wt","Śr","Cz","Pt","Sb"],
                    monthNames: [ "Styczeń","Luteń","Marzeń","Kwiecień","Majeń","Czerwień","Lipień","Sierpień","Wrzesień","Październień","Listopadzień","Grudzień" ],
                    monthNamesShort: [ "Sty", "Lut", "Mar", "Kwi", "Maj", "Cze","Lip", "Sie", "Wrz", "Paź", "Lis", "Gru" ],
                    today: 'Dzisiaj',
                    clear: 'Wyczyść',
                    dateFormat: 'mm/dd/yy',
                    weekHeader: 'Wk'
                },
                loading: false,
                executingDelayLeftPanelTransition: false,
            }
        },
        methods: {
            scroll() {
                window.onscroll = () => {
                    if (this.loading) {
                        return;
                    }
                    var scrollTopPlusInnerHeight = document.documentElement.scrollTop + window.innerHeight;
                    var offsetHeight = document.documentElement.offsetHeight;
                    let pixelsBeforeEnd = 30;

                    let bottomOfWindow = scrollTopPlusInnerHeight >= offsetHeight ||
                        offsetHeight - scrollTopPlusInnerHeight <= pixelsBeforeEnd;
                    if (bottomOfWindow) {
                        // Do sth, ath
                        this.loading = true;
                        this.moreResults().finally(() => { this.loading = false;})
                    }

                    if (this.executingDelayLeftPanelTransition === true) {
                        return;
                    }
                    this.executingDelayLeftPanelTransition = true;
                    setTimeout(() => {
                        document.getElementById("leftPanel").style.marginTop = window.scrollY + 'px';
                        this.executingDelayLeftPanelTransition = false;
                    }, 2000);
                }
            },
            moreResults() {
                // do actions that will effect in render more data
                return this.$store.dispatch('moreResults')
            },
            contributorClickHandler() {
                alert('Nie wolno ☝️☝️☝️')
            },
            categoryClickHandler() {
                alert('Nie można ☝️☝️☝️')
            }
        },
        mounted() {
            this.scroll();
        }
    }
</script>

<style scoped>
    #leftPanel {
        transition: all 0.4s linear;
    }
    .link:hover {
        text-decoration: underline;
        cursor: pointer;
    }

    /*loading spinner*/
    .lds-ripple {
        display: inline-block;
        position: relative;
        width: 80px;
        height: 80px;
    }

    .lds-ripple div {
        position: absolute;
        border: 4px solid #2b63d0;
        opacity: 1;
        border-radius: 50%;
        animation: lds-ripple 1s cubic-bezier(0, 0.2, 0.8, 1) infinite;
    }

    .lds-ripple div:nth-child(2) {
        animation-delay: -0.5s;
    }

    @keyframes lds-ripple {
        0% {
            top: 36px;
            left: 36px;
            width: 0;
            height: 0;
            opacity: 1;
        }
        100% {
            top: 0px;
            left: 0px;
            width: 72px;
            height: 72px;
            opacity: 0;
        }
    }
</style>
