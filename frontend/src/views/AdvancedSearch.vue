<template>
    <v-container fluid>
        <v-row align="center" justify="center">
            {{requestBody}}<br />
            {{title}}
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
                                <Calendar :show-icon="true" :showButtonBar="true" v-model="fromDate"/>
                            </v-col>
                            <v-col cols="6">
                                <Calendar :show-icon="true" :showButtonBar="true" v-model="toDate"/>
                            </v-col>
                        </v-row>
                    </v-col>
                </v-row>

                <v-row>
                    <v-col cols="2">
                        <span>Category</span>
                    </v-col>
                    <v-col cols="10" class="p-fluid">
                        <AutoComplete :multiple="true" v-model="categories"
                                      :suggestions="categorySuggestions"
                                      @complete="suggestCategories($event) "/>
                    </v-col>
                </v-row>

                <v-row>
                    <v-col cols="2">
                        <span>Contributor</span>
                    </v-col>
                    <v-col cols="10" class="p-fluid">
                        <AutoComplete :multiple="true" v-model="contributors"
                                      :suggestions="contributorSuggestions"
                                      @complete="suggestContributors($event)"/>
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
                requestBody: {
                    timestampRange: {
                        from: "2020-05-19T22:05:18.093Z",
                        to: "2020-05-19T22:05:18.093Z"
                    },
                    sortField: {
                        field: "RELEVANCE",
                        direction: "ASC"
                    },
                    filterFields: [
                        {
                            field: "CATEGORIES",
                            value: "Automobile Repair"
                        }
                    ],
                    searchFields: [
                        {
                            field: "TITLE",
                            should: [
                                {
                                    must: [
                                        "car",
                                        "repair",
                                        "mercedes w202"
                                    ],
                                    not: false
                                }
                            ]
                        }
                    ]
                }
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
                this.requestBody = this.buildRequestBody();
                // this.$store.dispatch('simpleSearch', 'Mercedes W202')
                //     .then(() => {
                //         this.$router.push({ name: 'Search Engine Result Page'})
                //     })
                //     .catch(error => {
                //         console.error(error);
                //         console.error('Something went absolutely wrong. You should consider restarting computer.');
                //     })
            },
            buildRequestBody() {
                let requestBody = {
                    timestampRange: {
                        from: this.fromDate,
                        to: this.toDate
                    },
                    sortField: {
                        field: "RELEVANCE",
                        direction: "ASC"
                    },
                    filterFields: [],
                    searchFields: []
                };
                if (this.title !== []) {

                    var mustTitle = [];
                    var tmp = '';

                    this.title.forEach(el => {
                        console.log(1);
                        var splitted = el.split(' ');
                        splitted.forEach(el => {
                            if (el.startsWith('"')) tmp = el;
                            else if (tmp !== '') { mustTitle.push(tmp + " " + el); tmp = ''; }
                            else mustTitle.push(el);
                        });
                        requestBody.searchFields.push({
                            field: "TITLE",
                            should: [
                                {
                                    must: mustTitle,
                                    not: this.notTitle.includes(el)
                                }
                            ]
                        });
                        mustTitle = [];
                        tmp = '';
                    });
                }


                return requestBody;
            }
        }
    }
</script>
