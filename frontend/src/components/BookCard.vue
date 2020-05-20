<template>
<!--    <div style="border: 1px solid;">-->
<!--        Rust in W203 After Face Lifting-->
<!--        08.11.2010-->
<!--        martin93-->
<!--        Motorization > ... > Mercedes > Rust-->
<!--        1. Suspension-->
<!--        1.1. Depressuring the hydraulic system-->
<!--        1.2. Removing the Suspension spheres-->
<!--        1.2.1 Rear Spheres-->
<!--        2. Replacing the Accumulator sphere-->
<!--        3. Steering-->
<!--        3.1. Lock Issues-->
<!--        3.2. Changing Oil in Manual box-->
<!--        4. Exhaust system-->
<!--        Foo Img-->
<!--    </div>-->

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
<!--                <img alt="user header" src="https://picsum.photos/150/100?random" class="mb-3">-->
                <img alt="user header" :src="book.coverImage" style="height: 150px; max-width: 165px;" class="mb-3">
            </div>
            <div>
                Lorem ipsum dolor sit amet, consectetur adipisicing elit. Inventore sed consequuntur error repudiandae numquam deserunt
                quisquam repellat libero asperiores earum nam nobis, culpa ratione quam perferendis esse, cupiditate neque quas!
                {{book.contents}}
            </div>
            <div class="text-right small">
                {{book.timestamp}}
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
