<template>
    <v-container fluid>
        <div class="card">
            <img alt="" class="card-img-top" src="https://picsum.photos/1920/300?random">
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
                image: "",
                title: "",
                timestamp: new Date().toLocaleString(),
                categories: [],
                chapters: [],
                text: ""
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
                // const xd = sampleData;
                this.axios.get('http://localhost:8080/v1/book/' + this.id)
                    .then((response) => {
                        let responseBody = response.data;

                        this.image = responseBody.image;
                        this.title = responseBody.title;
                        this.timestamp = new Date(responseBody.timestamp).toLocaleString();
                        this.categories = responseBody.categories || "";
                        // TODO: this.chapters = xd.
                        this.text = responseBody.text;
                    });
            }
        }
    }

    // eslint-disable-next-line no-unused-vars
    const sampleData = {
        "id": "7",
        "title": "Foundational concepts of organic chemistry",
        "firstImage": "Go To Organic Chemistry Contents .png",
        "text": "<div class=\"mw-parser-output\"><p>The purpose of this section is to review topics from freshman <a href=\"/wiki/General_Chemistry\" title=\"General Chemistry\">chemistry</a> and build the foundation for further studies in organic chemistry.\n</p>\n<ol><li><a href=\"/wiki/Organic_Chemistry/Foundational_concepts_of_organic_chemistry/History_of_organic_chemistry\" title=\"Organic Chemistry/Foundational concepts of organic chemistry/History of organic chemistry\">History of organic chemistry</a> <span class=\"noprint\"><a href=\"/wiki/Help:Development_stages\" title=\"100% developed&#160; as of Jan 12, 2005\"><img alt=\"100% developed&#160; as of Jan 12, 2005\" src=\"//upload.wikimedia.org/wikipedia/commons/thumb/2/24/100_percent.svg/9px-100_percent.svg.png\" decoding=\"async\" width=\"9\" height=\"9\" srcset=\"//upload.wikimedia.org/wikipedia/commons/thumb/2/24/100_percent.svg/14px-100_percent.svg.png 1.5x, //upload.wikimedia.org/wikipedia/commons/thumb/2/24/100_percent.svg/18px-100_percent.svg.png 2x\" data-file-width=\"9\" data-file-height=\"9\" /></a></span></li>\n<li><a href=\"/wiki/Organic_chemistry/Foundational_concepts_of_organic_chemistry/Atomic_structure\" class=\"mw-redirect\" title=\"Organic chemistry/Foundational concepts of organic chemistry/Atomic structure\">Atomic structure</a> <span class=\"noprint\"><a href=\"/wiki/Help:Development_stages\" title=\"100% developed&#160; as of Jan 12, 2005\"><img alt=\"100% developed&#160; as of Jan 12, 2005\" src=\"//upload.wikimedia.org/wikipedia/commons/thumb/2/24/100_percent.svg/9px-100_percent.svg.png\" decoding=\"async\" width=\"9\" height=\"9\" srcset=\"//upload.wikimedia.org/wikipedia/commons/thumb/2/24/100_percent.svg/14px-100_percent.svg.png 1.5x, //upload.wikimedia.org/wikipedia/commons/thumb/2/24/100_percent.svg/18px-100_percent.svg.png 2x\" data-file-width=\"9\" data-file-height=\"9\" /></a></span></li>\n<li><a href=\"/wiki/Organic_Chemistry/Foundational_concepts_of_organic_chemistry/Electronegativity\" title=\"Organic Chemistry/Foundational concepts of organic chemistry/Electronegativity\">Electronegativity</a> <span class=\"noprint\"><a href=\"/wiki/Help:Development_stages\" title=\"100% developed&#160; as of April 28, 2006\"><img alt=\"100% developed&#160; as of April 28, 2006\" src=\"//upload.wikimedia.org/wikipedia/commons/thumb/2/24/100_percent.svg/9px-100_percent.svg.png\" decoding=\"async\" width=\"9\" height=\"9\" srcset=\"//upload.wikimedia.org/wikipedia/commons/thumb/2/24/100_percent.svg/14px-100_percent.svg.png 1.5x, //upload.wikimedia.org/wikipedia/commons/thumb/2/24/100_percent.svg/18px-100_percent.svg.png 2x\" data-file-width=\"9\" data-file-height=\"9\" /></a></span></li>\n<li><a href=\"/wiki/Organic_Chemistry/Foundational_concepts_of_organic_chemistry/Bonding\" title=\"Organic Chemistry/Foundational concepts of organic chemistry/Bonding\">Bonding</a> <span class=\"noprint\"><a href=\"/wiki/Help:Development_stages\" title=\"100% developed&#160; as of April 28, 2006\"><img alt=\"100% developed&#160; as of April 28, 2006\" src=\"//upload.wikimedia.org/wikipedia/commons/thumb/2/24/100_percent.svg/9px-100_percent.svg.png\" decoding=\"async\" width=\"9\" height=\"9\" srcset=\"//upload.wikimedia.org/wikipedia/commons/thumb/2/24/100_percent.svg/14px-100_percent.svg.png 1.5x, //upload.wikimedia.org/wikipedia/commons/thumb/2/24/100_percent.svg/18px-100_percent.svg.png 2x\" data-file-width=\"9\" data-file-height=\"9\" /></a></span></li>\n<li><a href=\"/wiki/Organic_Chemistry/Foundational_concepts_of_organic_chemistry/Electron_dot_structures\" title=\"Organic Chemistry/Foundational concepts of organic chemistry/Electron dot structures\">Electron dot structures</a> <span class=\"noprint\"><a href=\"/wiki/Help:Development_stages\" title=\"100% developed&#160; as of April 28, 2006\"><img alt=\"100% developed&#160; as of April 28, 2006\" src=\"//upload.wikimedia.org/wikipedia/commons/thumb/2/24/100_percent.svg/9px-100_percent.svg.png\" decoding=\"async\" width=\"9\" height=\"9\" srcset=\"//upload.wikimedia.org/wikipedia/commons/thumb/2/24/100_percent.svg/14px-100_percent.svg.png 1.5x, //upload.wikimedia.org/wikipedia/commons/thumb/2/24/100_percent.svg/18px-100_percent.svg.png 2x\" data-file-width=\"9\" data-file-height=\"9\" /></a></span></li>\n<li><a href=\"/wiki/Organic_Chemistry/Foundational_concepts_of_organic_chemistry/Visualization\" title=\"Organic Chemistry/Foundational concepts of organic chemistry/Visualization\">Visualization</a><span class=\"noprint\"><a href=\"/wiki/Help:Development_stages\" title=\"75% developed&#160; as of November 12, 2006\"><img alt=\"75% developed&#160; as of November 12, 2006\" src=\"//upload.wikimedia.org/wikipedia/commons/thumb/6/62/75_percent.svg/9px-75_percent.svg.png\" decoding=\"async\" width=\"9\" height=\"9\" srcset=\"//upload.wikimedia.org/wikipedia/commons/thumb/6/62/75_percent.svg/14px-75_percent.svg.png 1.5x, //upload.wikimedia.org/wikipedia/commons/thumb/6/62/75_percent.svg/18px-75_percent.svg.png 2x\" data-file-width=\"9\" data-file-height=\"9\" /></a></span></li>\n<li><a href=\"/wiki/Organic_Chemistry/Foundational_concepts_of_organic_chemistry/Resonance\" title=\"Organic Chemistry/Foundational concepts of organic chemistry/Resonance\">Resonance</a> <span class=\"noprint\"><a href=\"/wiki/Help:Development_stages\" title=\"100% developed&#160; as of April 28, 2006\"><img alt=\"100% developed&#160; as of April 28, 2006\" src=\"//upload.wikimedia.org/wikipedia/commons/thumb/2/24/100_percent.svg/9px-100_percent.svg.png\" decoding=\"async\" width=\"9\" height=\"9\" srcset=\"//upload.wikimedia.org/wikipedia/commons/thumb/2/24/100_percent.svg/14px-100_percent.svg.png 1.5x, //upload.wikimedia.org/wikipedia/commons/thumb/2/24/100_percent.svg/18px-100_percent.svg.png 2x\" data-file-width=\"9\" data-file-height=\"9\" /></a></span></li>\n<li><a href=\"/wiki/Organic_Chemistry/Foundational_concepts_of_organic_chemistry/Acids_and_bases\" title=\"Organic Chemistry/Foundational concepts of organic chemistry/Acids and bases\">Acids and bases</a> <span class=\"noprint\"><a href=\"/wiki/Help:Development_stages\" title=\"100% developed&#160; as of April 28, 2006\"><img alt=\"100% developed&#160; as of April 28, 2006\" src=\"//upload.wikimedia.org/wikipedia/commons/thumb/2/24/100_percent.svg/9px-100_percent.svg.png\" decoding=\"async\" width=\"9\" height=\"9\" srcset=\"//upload.wikimedia.org/wikipedia/commons/thumb/2/24/100_percent.svg/14px-100_percent.svg.png 1.5x, //upload.wikimedia.org/wikipedia/commons/thumb/2/24/100_percent.svg/18px-100_percent.svg.png 2x\" data-file-width=\"9\" data-file-height=\"9\" /></a></span></li>\n<li><a href=\"/wiki/Organic_Chemistry/Foundational_concepts_of_organic_chemistry/Nomenclature\" title=\"Organic Chemistry/Foundational concepts of organic chemistry/Nomenclature\">Nomenclature</a> <span class=\"noprint\"><a href=\"/wiki/Help:Development_stages\" title=\"100% developed&#160; as of Nov 25, 2006\"><img alt=\"100% developed&#160; as of Nov 25, 2006\" src=\"//upload.wikimedia.org/wikipedia/commons/thumb/2/24/100_percent.svg/9px-100_percent.svg.png\" decoding=\"async\" width=\"9\" height=\"9\" srcset=\"//upload.wikimedia.org/wikipedia/commons/thumb/2/24/100_percent.svg/14px-100_percent.svg.png 1.5x, //upload.wikimedia.org/wikipedia/commons/thumb/2/24/100_percent.svg/18px-100_percent.svg.png 2x\" data-file-width=\"9\" data-file-height=\"9\" /></a></span></li></ol>\n<hr />\n<p><a href=\"/wiki/File:Go_To_Organic_Chemistry_Contents.png\" class=\"image\"><img alt=\"Go To Organic Chemistry Contents.png\" src=\"//upload.wikimedia.org/wikipedia/commons/a/aa/Go_To_Organic_Chemistry_Contents.png\" decoding=\"async\" width=\"36\" height=\"33\" data-file-width=\"36\" data-file-height=\"33\" /></a> | <a href=\"/wiki/Organic_Chemistry/Alkanes\" title=\"Organic Chemistry/Alkanes\">Alkanes &gt;&gt;</a>\n</p>\n<!-- \nNewPP limit report\nParsed by mw1308\nCached time: 20200510150151\nCache expiry: 2592000\nDynamic content: false\nComplications: []\nCPU time usage: 0.072 seconds\nReal time usage: 0.095 seconds\nPreprocessor visited node count: 449/1000000\nPost\u2010expand include size: 2354/2097152 bytes\nTemplate argument size: 845/2097152 bytes\nHighest expansion depth: 11/40\nExpensive parser function count: 0/500\nUnstrip recursion depth: 0/20\nUnstrip post\u2010expand size: 0/5000000 bytes\nNumber of Wikibase entities loaded: 0/400\nLua time usage: 0.019/10.000 seconds\nLua memory usage: 819 KB/50 MB\n-->\n<!--\nTransclusion expansion time report (%,ms,calls,template)\n100.00%   63.281      1 -total\n 88.50%   56.003      1 Template:BookCat\n 18.87%   11.943      1 Template:Evalx\n  9.88%    6.254      9 Template:Stage_short\n  8.36%    5.292      1 Template:BOOKCATEGORY\n  4.02%    2.546      1 Template:NAIVEBOOKNAME\n-->\n\n<!-- Saved in parser cache with key enwikibooks:pcache:idhash:7-0!canonical and timestamp 20200510150151 and revision id 1925731\n -->\n</div>",
        "categories": ["Organic Chemistry"],
        "timestamp": "2010-09-02T06:08:27Z"
    };

</script>

<style scoped>

</style>
