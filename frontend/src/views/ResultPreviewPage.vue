<template>
    <v-container fluid>
        <div class="card">
            <img alt="" class="card-img-top" src="https://picsum.photos/1920/300?random">
            <div class="card-body">
                <h1 class="card-title">{{title}}</h1>
                <div class="card-subtitle mb-2 text-muted">Last edited: {{timestamp}}</div>
                <div class="card-header" v-if="categories">{{categories.join(' > ')}}</div>
                <div class="card-body">
                    <p class="card-text" v-html="text"/>
                </div>
            </div>
        </div>
        <a class="card-link" href="#">Back</a>
    </v-container>
</template>

<script>
    export default {
        props: ['id'],
        name: "ResultPreviewPage",
        data() {
            return {
                image: "",
                title: "",
                timestamp: new Date().toLocaleString(),
                categories: [],
                chapters: [],
                text: ""
            }
        },
        created: async function () {
            // const xd = sampleData;
            const xd = (await this.axios.get('http://localhost:8080/v1/book/' + this.id)).data;
            this.image = xd.image;
            this.title = xd.title;
            this.timestamp = new Date(xd.timestamp).toLocaleString();
            this.categories = xd.categories || "";
            // TODO: this.chapters = xd.
            this.text = xd.text;
        },
        methods: {}
    }

    // eslint-disable-next-line no-unused-vars
    const sampleData = {
        "id": "7",
        "title": "Foundational concepts of organic chemistry",
        "firstImage": "Go To Organic Chemistry Contents .png",
        "text": " The purpose of this section is to review topics from freshman General Chemistry chemistry and build the foundation for further studies in organic chemistry . <br> Organic Chemistry \\/ Foundational concepts of organic chemistry \\/ History of organic chemistry History of organic chemistry stage short 100% Jan 12 , 2005  <br> Organic chemistry \\/ Foundational concepts of organic chemistry \\/ Atomic structure Atomic structure stage short 100% Jan 12 , 2005  <br> Organic Chemistry \\/ Foundational concepts of organic chemistry \\/ Electronegativity Electronegativity stage short 100% April 28 , 2006  <br> Organic Chemistry \\/ Foundational concepts of organic chemistry \\/ Bonding Bonding stage short 100% April 28 , 2006  <br> Organic Chemistry \\/ Foundational concepts of organic chemistry \\/ Electron dot structures Electron dot structures stage short 100% April 28 , 2006  <br> Organic Chemistry \\/ Foundational concepts of organic chemistry \\/ Visualization Visualization stage short 75% November 12 , 2006  <br> Organic Chemistry \\/ Foundational concepts of organic chemistry \\/ Resonance Resonance stage short 100% April 28 , 2006  <br> Organic Chemistry \\/ Foundational concepts of organic chemistry \\/ Acids and bases Acids and bases stage short 100% April 28 , 2006  <br> Organic_Chemistry \\/ Foundational_concepts_of_organic_chemistry \\/ Nomenclature Nomenclature stage short 100% Nov 25 , 2006  <br> ---- <br> <img src=https://upload.wikimedia.org/wikipedia/commons/a/aa/Go_To_Organic_Chemistry_Contents.png alt=\"Go_To_Organic_Chemistry_Contents.png\"> Organic Chemistry \\/ Alkanes Alkanes  <br> BookCat ",
        "categories": ["Organic Chemistry"],
        "timestamp": "2010-09-02T06:08:27Z"
    };

</script>

<style scoped>

</style>
