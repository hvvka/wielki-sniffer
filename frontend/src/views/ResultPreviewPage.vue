<template>
    <v-container fluid>
        <div class="card">
            <img v-if="image" :alt="image" :src="image" class="card-img-top cover-image" >
            <div class="card-body">
                <h1 class="card-title">{{title}}</h1>
                <div class="card-subtitle mb-2 text-muted">Last edited: {{timestamp}}</div>
                <div class="card-header" v-if="categories.length">{{categories.join(' > ')}}</div>
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
                image: null,
                title: null,
                timestamp: new Date().toLocaleString(),
                categories: [],
                chapters: [],
                text: null
            }
        },
        watch: {
          id() {
              this.fetchData()
          }
        },
        mounted() {
            this.fetchData()
        },
        methods: {
            fetchData() {
                this.axios.get('http://localhost:8080/v1/book/' + this.id)
                    .then((response) => {
                        let responseBody = response.data;

                        this.image = responseBody.image || this.$store.coverImage;
                        console.log(this.$store.coverImage);
                        console.log(responseBody.image);
                        this.title = responseBody.title;
                        this.timestamp = new Date(responseBody.timestamp).toLocaleString();
                        this.categories = responseBody.categories || "";
                        this.text = responseBody.text;
                    });
            }
        }
    }

</script>

<style scoped>
.cover-image {
    object-fit: cover;
    height: 150px;
}
</style>
