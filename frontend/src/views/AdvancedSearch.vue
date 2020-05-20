<template>
    <v-container fluid style="height: 700px;">
        <v-row align="center" justify="center">
            <v-col cols="6">
                <v-row>
                    <v-col>
                        <h1 class="header-panel">Advanced search</h1>
                    </v-col>
                </v-row>

                <v-row>
                    <v-col cols="2">
                        <span>Title</span>
                    </v-col>
                    <v-col cols="10" class="p-fluid">
                        <Chips2 v-model="title" v-bind:notValue="notTitle" v-on:notInput="notTitle = $event"/>
                    </v-col>
                </v-row>

                <v-row>
                    <v-col cols="2">
                        <span>Text</span>
                    </v-col>
                    <v-col cols="10" class="p-fluid">
                        <Chips2 v-model="text" v-bind:notValue="notText" v-on:notInput="notText = $event"/>
                    </v-col>
                </v-row>

                <v-row justify="center">
                    <v-col cols="2">
                        Publication date
                    </v-col>
                    <v-col>
                        <v-row justify="center" class="p-fluid">
                            <v-col cols="6">
                                <Calendar :show-icon="true" :showButtonBar="true" v-model="fromDate"
                                          :monthNavigator="true" :yearNavigator="true" yearRange="1800:2020"
                                          :locale="pl"/>
                            </v-col>
                            <v-col cols="6">
                                <Calendar :show-icon="true" :showButtonBar="true" v-model="toDate"
                                          :monthNavigator="true" :yearNavigator="true" yearRange="1800:2020"
                                          :locale="pl"/>
                            </v-col>
                        </v-row>
                    </v-col>
                </v-row>

                <v-row>
                    <v-col cols="2">
                        <span>Category</span>
                    </v-col>
                    <v-col cols="10" class="p-fluid">
<!--                        <AutoComplete :multiple="true" v-model="categories"-->
<!--                                      :suggestions="categorySuggestions"-->
<!--                                      @complete="suggestCategories($event)"/>-->
                        <Chips v-model="categories" />
                    </v-col>
                </v-row>

                <v-row>
                    <v-col cols="2">
                        <span>Contributor</span>
                    </v-col>
                    <v-col cols="10" class="p-fluid">
<!--                        <AutoComplete :multiple="true" v-model="contributors"-->
<!--                                      :suggestions="contributorSuggestions"-->
<!--                                      @complete="suggestContributors($event)"/>-->
                        <Chips v-model="contributors" />
                    </v-col>
                </v-row>

                <v-row justify="end">
                    <v-btn outlined color="blue" @click="searchButtonClickHandler">Search</v-btn>
                </v-row>
            </v-col>
        </v-row>
    </v-container>
</template>

<script type="text/javascript">
    export default {
        data() {
            return {
                title: [],
                notTitle: [],
                text: [],
                notText: [],
                fromDate: null,
                toDate: null,
                contributors: [],
                contributorSuggestions: [],
                categories: [],
                categorySuggestions: [],
                pl: {
                    firstDayOfWeek: 1,
                    dayNames: ["Niedziela", "Poniedziałek", "Wtorek", "Środa", "Czwartek", "Piątek", "Sobota"],
                    dayNamesShort: ["Nie", "Pon", "Wto", "Śro", "Czw", "Pią", "Sob"],
                    dayNamesMin: ["Nd", "Pn", "Wt", "Śr", "Cz", "Pt", "Sb"],
                    monthNames: ["Styczeń", "Luteń", "Marzeń", "Kwiecień", "Majeń", "Czerwień", "Lipień", "Sierpień", "Wrzesień", "Październień", "Listopadzień", "Grudzień"],
                    monthNamesShort: ["Sty", "Lut", "Mar", "Kwi", "Maj", "Cze", "Lip", "Sie", "Wrz", "Paź", "Lis", "Gru"],
                    today: 'Dzisiaj',
                    clear: 'Wyczyść',
                    dateFormat: 'mm/dd/yy',
                    weekHeader: 'Wk'
                },
            }
        },
        methods: {
            suggestContributors(event) {
                console.log(event);
                this.contributorSuggestions = ['user1', 'user2', 'user3']
            },
            suggestCategories(event) {
                console.log(event);
                this.categorySuggestions = ['Automotive']
            },
            searchButtonClickHandler() {
                this.$store.dispatch('advancedSearch', {
                    title: this.title,
                    notTitle: this.notTitle,
                    text: this.text,
                    notText: this.notText,
                    fromDate: this.fromDate,
                    toDate: this.toDate,
                    contributors: this.contributors,
                    categories: this.categories,
                    sortField: {
                        field: "RELEVANCE",
                        direction: "ASC"
                    }
                }).then(() => {
                    this.$router.push({name: 'Search Engine Result Page'})
                }).catch(error => {
                    console.error(error);
                    console.error('Something went absolutely wrong. You should consider restarting computer.');
                })
                // this.$store.dispatch('simpleSearch', 'Mercedes W202')
                //     .then(() => {
                //         this.$router.push({ name: 'Search Engine Result Page'})
                //     })
                //     .catch(error => {
                //         console.error(error);
                //         console.error('Something went absolutely wrong. You should consider restarting computer.');
                //     })
            },
        }
    }
</script>
