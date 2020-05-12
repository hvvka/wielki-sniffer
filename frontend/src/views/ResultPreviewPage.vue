<template>
    <v-container fluid>
        <h1 class="header-panel">{{title}} (document id={{id}})</h1>
        <div class="card-subtitle mb-2 text-muted">{{categories.join(' > ')}}</div>
        <div class="panel-group">
            <div :key="chapter.title" class="panel panel-default" v-for="chapter in chapters">
                <h3 class="panel-heading">{{ chapter.title }}</h3>
                <p class="panel-body">{{ chapter.text }}</p>
            </div>
        </div>
    </v-container>
</template>

<script>
    export default {
        props: ['id'],
        name: "ResultPreviewPage",
        data() {
            return {
                title: "Rust in W202",
                categories: ["Motorization", "Cars", "Mercedes", "Rust"],
                chapters:
                    [
                        {
                            title: "Rust in trunk",
                            text: "All that city... You just couldn't see an end to it. The end! Please, could you show me where it ends? It was all very fine on that gangway and I was grand, too, in my overcoat. I cut quite a figure and I had no doubts about getting off. Guaranteed. That wasn't a problem. It wasn't what I saw that stopped me, Max. It was what I didn't see. Can you understand that? What I didn't see. In all that sprawling city, there was everything except an end. There was everything. But there wasn't an end. What I couldn't see was where all that came to an end. The end of the world. Take a piano. The keys begin, the keys end. You know there are 88 of them and no-one can tell you differently. They are not infinite, you are infinite. And on those 88 keys the music that you can make is infinite. I like that. That I can live by."
                        },
                        {
                            title: "Rust in suspension",
                            text: "But you get me up on that gangway and roll out a keyboard with millions of keys, and that's the truth, there's no end to them, that keyboard is infinite. But if that keyboard is infinite there's no music you can play. You're sitting on the wrong bench. That's God's piano. Christ, did you see the streets? There were thousands of them! How do you choose just one? One woman, one house, one piece of land to call your own, one landscape to look at, one way to die. All that world weighing down on you without you knowing where it ends. Aren't you scared of just breaking apart just thinking about it, the enormity of living in it? I was born on this ship."
                        },
                        {
                            title: "Rust in bodywork",
                            text: "The world passed me by, but two thousand people at a time. And there were wishes here, but never more than could fit on a ship, between prow and stern. You played out your happiness on a piano that was not infinite. I learned to live that way. Land is a ship too big for me. It's a woman too beautiful. It's a voyage too long. Perfume too strong. It's music I don't know how to make. I can't get off this ship. At best, I can step off my life. After all, it's as though I never existed. You're the exception, Max. You're the only one who knows that I'm here. You're a minority. You'd better get used to it. Forgive me, my friend. But I'm not getting off."
                        }
                    ]
            }
        },
        methods: {
            /* TODO: load before the page, test with backend */
            getBook(id) {
                this.axios.get('http://localhost:8080/v1/book/' + id)
            }
        }
    }
</script>

<style scoped>

</style>