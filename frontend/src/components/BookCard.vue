<template>
    <Card class="link" @click.native="navigateUser(book.id, book.coverImage)">
        <template slot="title">
            <span>{{book.title}}</span>
        </template>
        <template slot="subtitle">
            <!--            Motorization > ... > Mercedes > Rust-->
            <span v-for="(category, index) in book.categories" :key="index">
                {{category}}<span v-if="index !== book.categories.length - 1">&nbsp;>&nbsp;</span>
            </span>
        </template>
        <template slot="content" style="min-height: 100px">
            <div v-if="book.coverImage" style="float: left;" class="pr-2">
                <img alt="user header" :src="book.coverImage" style="height: 150px; max-width: 165px;" class="mb-3">
            </div>
            <div>
                {{book.text}}
            </div>
            <div class="text-right small">
                {{new Date(book.timestamp).toLocaleString()}}
            </div>
        </template>
    </Card>
</template>

<script>
    export default {
        name: "BookCard",
        props: ['book'],
        methods: {
            navigateUser(bookId, coverImage) {
                this.$store.coverImage = coverImage;
                this.$router.push({path: `/page/${bookId}`});
                // scroll user top, because user will have scrollbar from previous screen
                window.scrollTo({
                    top: 0,
                    behavior: 'smooth'
                });
            }
        }
    }
</script>

<style>
    .p-card.p-component {
        height: 100%;
    }

    .p-card-body {
        height: 100%;
    }

    .link:hover {
        text-decoration: underline;
        cursor: pointer;
    }
</style>
