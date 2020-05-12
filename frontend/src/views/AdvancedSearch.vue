<template>
    <v-container fluid>
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
                        <Chips v-model="title"/>
                    </v-col>
                </v-row>

                <v-row>
                    <v-col cols="2">
                        <span>Text</span>
                    </v-col>
                    <v-col cols="10" class="p-fluid">
                        <Chips v-model="text"/>
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
                    <v-btn outlined color="blue">Search</v-btn>
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
                text: [],
                fromDate: null,
                toDate: null,
                contributors: [],
                contributorSuggestions: [],
                categories: [],
                categorySuggestions: []
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
            }
        }
    }
</script>
