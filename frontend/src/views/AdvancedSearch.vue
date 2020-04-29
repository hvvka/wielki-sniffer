<template>
    <div>
        <h1>Advanced search</h1>
        <v-row no-gutters v-for="i in titleAlternatives.length + 1" :key="i">
            <v-col sm="1">
                <v-card class="pa-2" flat>
                    <span v-if="i > 1">Or</span>
                </v-card>
            </v-col>
            <v-col sm="11">
                <v-card class="pa-2" flat>
                    <v-text-field label="Title" v-model="titleAlternatives[i-1]"></v-text-field>
                </v-card>
            </v-col>
        </v-row>

        <v-row no-gutters v-for="i in textAlternatives.length + 1" :key="i">
            <v-col sm="1">
                <v-card class="pa-2" flat>
                    <span v-if="i > 1">Or</span>
                </v-card>
            </v-col>
            <v-col sm="11">
                <v-card class="pa-2" flat>
                    <v-text-field label="Text" v-model="textAlternatives[i-1]"></v-text-field>
                </v-card>
            </v-col>
        </v-row>


        <v-row no-gutters v-for="i in textAlternatives.length + 1" :key="i">
            <v-col sm="1">
                <v-card class="pa-2" flat>
                </v-card>
            </v-col>
            <v-col sm="4">
                <v-card class="pa-2" flat>
                    <v-menu
                            v-model="menu1"
                            :close-on-content-click="false"
                            :nudge-right="40"
                            transition="scale-transition"
                            offset-y
                            min-width="290px"
                    >
                        <template v-slot:activator="{ on }">
                            <v-text-field
                                    v-model="date"
                                    label="Publication date from"
                                    readonly
                                    v-on="on"
                            ></v-text-field>
                        </template>
                        <v-date-picker v-model="date" @input="menu2 = false"></v-date-picker>
                    </v-menu>
                </v-card>
            </v-col>
            <v-col sm="4">
                <v-card class="pa-2" flat>
                    <v-menu
                            v-model="menu2"
                            :close-on-content-click="false"
                            :nudge-right="40"
                            transition="scale-transition"
                            offset-y
                            min-width="290px"
                    >
                        <template v-slot:activator="{ on }">
                            <v-text-field
                                    v-model="date"
                                    label="Publication date to"
                                    readonly
                                    v-on="on"
                            ></v-text-field>
                        </template>
                        <v-date-picker v-model="date" @input="menu2 = false"></v-date-picker>
                    </v-menu>
                </v-card>
            </v-col>
        </v-row>


        <v-row no-gutters>
            <v-col sm="1">
                <v-card class="pa-2" flat>

                </v-card>
            </v-col>
            <v-col sm="4">
                <v-card class="pa-2" flat>
                    <v-select
                            v-model="contributors"
                            :items="items"
                            chips
                            label="Contributors includes"
                            multiple
                            outlined
                    ></v-select>
                </v-card>
            </v-col>
            <v-col sm="4">
                <v-card class="pa-2" flat>
                    <v-select
                            v-model="contributors2"
                            :items="items"
                            chips
                            label="Contributors excludes"
                            multiple
                            outlined
                    ></v-select>
                </v-card>
            </v-col>
        </v-row>


    </div>
</template>

<script type="text/javascript">
    export default {
        data() {
            return {
                titleAlternatives: [],
                textAlternatives: [],
                menu1: '',
                menu2: '',
                date: '',
                contributors: '',
                contributors2: '',
                items: ['Jan kowalski', 'inny ziomek']
            }
        }
    }
</script>
