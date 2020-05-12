<template>
    <v-container fluid>
        <v-row justify="center">
            <v-col cols="2">
                Filters<br/>
                Contributors<br/>
                <div>johny32 (15)</div>
                <div>corman (12)</div>
                <div>michael_t (8)</div>
                <div>borry (2)</div>
                Publication date<br/>
                <div>2001 - 2019</div>
                <div>---*------*----</div>
                Category<br/>
                <div>Motorization (8)</div>
                <div>Repair (10)</div>
                <div>Honda (5)</div>
                <div>Mercedes (2)</div>
                <div>Cars (7)</div>
                <div>Kia (1)</div>
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
                    <v-col v-for="card in searchResults" :key="card.id" cols="6" sm="12" md="6">
                        <BookCard :data="card"></BookCard>
                    </v-col>
                </v-row>

            </v-col>
        </v-row>
    </v-container>
</template>

<script>
    import BookCard from '../components/BookCard'

    export default {
        name: "SearchEngineResultPage",
        components: {
            BookCard
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
                }]
            }
        }
    }
</script>

<style scoped>

</style>
