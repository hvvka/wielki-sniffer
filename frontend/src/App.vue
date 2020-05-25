<template>
  <v-app id="inspire">
    <v-app-bar :clipped-left="$vuetify.breakpoint.lgAndUp" app color="blue darken-3" dark
      src="https://picsum.photos/1920/1080?random">
      <v-container fluid>
          <v-row justify="center" align="end">
            <v-col cols="6">
            <v-combobox flat solo-inverted hide-details prepend-inner-icon="mdi-magnify" label="Search"
                          class="hidden-sm-and-down "
                          :items="hints"
                          :item-text="displayHintFunc"
                          :loading="searching"
                          @input="hintClickedHandler"
                          @update:search-input="requestForHints"
                          :filter="allowAllFilterFunction"
                          ref="search"
                          >
            </v-combobox>
            </v-col>
            <v-col cols="2" class="mb-0 pl-0">
              <router-link to="/advanced">
                <span class="whiteAnchor shadow">Advanced search</span>
              </router-link>
            </v-col>
          </v-row>
      </v-container>
      <v-spacer />
    </v-app-bar>

    <v-content>
      <transition name="custom_animation"
                  enter-active-class="animated zoomIn"
                  leave-active-class="animated zoomOut"
                  :duration="600">
        <router-view/>
      </transition>
    </v-content>

    <v-footer>
      <router-link to="/">
        <span style="color: black; font-size: 9px;">Home</span>
      </router-link>
      |
      <router-link to="/page/18778">
        <span style="color: black; font-size: 9px;">Sample page</span>
      </router-link>
      |
      <router-link to="/search-results">
        <span style="color: black; font-size: 9px;">SERP</span>
      </router-link>
      <v-spacer></v-spacer>
      <router-link to="/about">
        <span style="color: black;">About</span>
      </router-link>
      <v-spacer></v-spacer>
      <div>&copy; {{ new Date().getFullYear() }}</div>
    </v-footer>
    <!--    <div class="overlay" style="" v-if="getFullScreenLoader">-->
    <!--      <div class="loading-spinner">-->
    <!--        <div class="dot dotOne"></div>-->
    <!--        <div class="dot dotTwo"></div>-->
    <!--        <div class="dot dotThree"></div>-->
    <!--      </div>-->
    <!--    </div>-->
    <div class="loading-cat" v-if="getFullScreenLoader">
      <div class="body"></div>
      <div class="head">
        <div class="face"></div>
      </div>
      <div class="foot">
        <div class="tummy-end"></div>
        <div class="bottom"></div>
        <div class="legs left"></div>
        <div class="legs right"></div>
      </div>
      <div class="hands left"></div>
      <div class="hands right"></div>
    </div>
  </v-app>
</template>

<script>
  import {mapGetters} from 'vuex'

  export default {
    data: () => ({
      searching: false,
      hints: []
    }),
    computed: {
      ...mapGetters(['getFullScreenLoader']),
    },
    methods: {
      displayHintFunc(item) {
        return item.title + " (" + item.id + ")";
      },
      hintClickedHandler(inputValue) {
        if (inputValue == null) { // fix for executing this method when blur() on empty input
          return;
        }

        if (typeof inputValue === 'object') { // or alternativly this.hints.includes(inputValue)
          console.log('hint: ' + JSON.stringify(inputValue));
          this.$store.coverImage = inputValue.coverImage;
          this.$router.push({path: `/page/${inputValue.id}`});
          this.clearSearchInput();
        } else {
          console.log("custom:" + inputValue);
          this.doSimpleSearch(inputValue);
          this.clearSearchInput();
        }
      },
      requestForHints(input) {
        if (!input) {
          this.hints = []; // fix for clearing hints when user delete all input
          // this prevents from entering enter key by user and injecting value from hints array
          return;
        }

        if (input.length <= 3) { //dont hint if not typed less than 4 characters
          return;
        }

        this.searching = true;
        this.axios.get('http://localhost:8080/v1/search/hint?query=' + input + '&hintCount=6')
          .then(response => {
            // console.log(response);
            this.hints = response.data;
          })
          .catch(error => {
            console.error(error);
          })
          .finally(() => {
            this.searching = false;
          })
      },
      allowAllFilterFunction() {
        // console.log("item: " + JSON.stringify(item) + ", queryText: " + queryText + ", itemText: " + itemText)
        return true;
        // The function used for filtering items
        // (item: object, queryText: string, itemText: string) => boolean
        // because results from backend can contain items that doesnt contain inputed value by user
        // these values are filtered by default and in result they are not displayed
      },
      doSimpleSearch(queryString) {
        this.$store.dispatch('simpleSearch', queryString)
          .then(() => {
            this.$router.push({name: 'Search Engine Result Page'})
          })
          .catch(error => {
            console.error(error);
            console.error('Something went absolutely wrong. You should consider restarting computer.');
          })
      },
      clearSearchInput() {
        this.$refs.search.clearableCallback();
        this.$refs.search.blur();
      }
    }
  }
</script>

<style>
  .whiteAnchor {
    color: white;
  }

  .shadow:not(:active) {
    text-shadow: 0 0 black;
    -webkit-text-stroke: 0.1px gray;
  }

  .theme--light.v-list-item .v-list-item__mask {
    background: white !important;
    color: rgba(0, 0, 0, 0.87) !important;
  }

  .overlay {
    background: rgba(255, 255, 255, 0.6);
    position: fixed;
    top: 0;
    left: 0;
    width: 100%;
    height: 100%;
  }

  .loading-spinner {
    position: absolute;
    top: 50%;
    left: 50%;
    -webkit-transform: translateX(-50%) translateY(-50%);
    transform: translateX(-50%) translateY(-50%);
  }

  .dot {
    background: black;
    border-radius: 100%;
    color: white;
    height: 8px;
    line-height: 8px;
    text-align: center;
    width: 8px;
  }

  .dotOne {
    -webkit-animation: dotOneKeyframes 5s ease infinite;
    background: #12991b;
    position: absolute;
    top: 0;
    left: 0;
  }

  .dotTwo {
    -webkit-animation: dotTwoKeyframes 5s ease 0.4166666666666s infinite;
    background: #14a91e;
    position: absolute;
    top: 0;
    left: 14px;
  }

  .dotThree {
    -webkit-animation: dotThreeKeyframes 5s ease 0.83333333333s infinite;
    background: #16b821;
    position: absolute;
    top: 14px;
    left: 14px;
  }

  @-webkit-keyframes dotOneKeyframes {
    0% {
      top: 0;
      left: 0;
    }
    8.3333333333% {
      top: 14px;
      left: 0;
    }
    16.6666666666% {

    }
    25% {
      top: 14px;
      left: 0;
    }
    33.3333333333% {
      top: 14px;
      left: 14px;
    }
    41.6666666666% {

    }
    50% {
      top: 14px;
      left: 14px;
    }
    58.3333333333% {
      top: 0;
      left: 14px;
    }
    66.6666666666% {

    }
    75% {
      top: 0;
      left: 14px;
    }
    83.3333333333% {
      top: 0;
      left: 0;
    }
    91.6666666666% {

    }
    100% {

    }
  }

  @keyframes dotOneKeyframes {
    0% {
      top: 0;
      left: 0;
    }
    8.3333333333% {
      top: 14px;
      left: 0;
    }
    16.6666666666% {

    }
    25% {
      top: 14px;
      left: 0;
    }
    33.3333333333% {
      top: 14px;
      left: 14px;
    }
    41.6666666666% {

    }
    50% {
      top: 14px;
      left: 14px;
    }
    58.3333333333% {
      top: 0;
      left: 14px;
    }
    66.6666666666% {

    }
    75% {
      top: 0;
      left: 14px;
    }
    83.3333333333% {
      top: 0;
      left: 0;
    }
    91.6666666666% {

    }
    100% {

    }
  }

  @-webkit-keyframes dotTwoKeyframes {
    0% {
      top: 0;
      left: 14px;
    }
    8.3333333333% {
      top: 0;
      left: 0;
    }
    16.6666666666% {

    }
    25% {
      top: 0;
      left: 0;
    }
    33.3333333333% {
      top: 14px;
      left: 0;
    }
    41.6666666666% {

    }
    50% {
      top: 14px;
      left: 0;
    }
    58.3333333333% {
      top: 14px;
      left: 14px;
    }
    66.6666666666% {

    }
    75% {
      top: 14px;
      left: 14px;
    }
    83.3333333333% {
      top: 0;
      left: 14px;
    }
    91.6666666666% {

    }
    100% {

    }
  }

  @keyframes dotTwoKeyframes {
    0% {
      top: 0;
      left: 14px;
    }
    8.3333333333% {
      top: 0;
      left: 0;
    }
    16.6666666666% {

    }
    25% {
      top: 0;
      left: 0;
    }
    33.3333333333% {
      top: 14px;
      left: 0;
    }
    41.6666666666% {

    }
    50% {
      top: 14px;
      left: 0;
    }
    58.3333333333% {
      top: 14px;
      left: 14px;
    }
    66.6666666666% {

    }
    75% {
      top: 14px;
      left: 14px;
    }
    83.3333333333% {
      top: 0;
      left: 14px;
    }
    91.6666666666% {

    }
    100% {

    }
  }

  @-webkit-keyframes dotThreeKeyframes {
    0% {
      top: 14px;
      left: 14px;
    }
    8.3333333333% {
      top: 0;
      left: 14px;
    }
    16.6666666666% {

    }
    25% {
      top: 0;
      left: 14px;
    }
    33.3333333333% {
      top: 0;
      left: 0;
    }
    41.6666666666% {

    }
    50% {
      top: 0;
      left: 0;
    }
    58.3333333333% {
      top: 14px;
      left: 0;
    }
    66.6666666666% {

    }
    75% {
      top: 14px;
      left: 0;
    }
    83.3333333333% {
      top: 14px;
      left: 14px;
    }
    91.6666666666% {

    }
    100% {

    }
  }

  @keyframes dotThreeKeyframes {
    0% {
      top: 14px;
      left: 14px;
    }
    8.3333333333% {
      top: 0;
      left: 14px;
    }
    16.6666666666% {

    }
    25% {
      top: 0;
      left: 14px;
    }
    33.3333333333% {
      top: 0;
      left: 0;
    }
    41.6666666666% {

    }
    50% {
      top: 0;
      left: 0;
    }
    58.3333333333% {
      top: 14px;
      left: 0;
    }
    66.6666666666% {

    }
    75% {
      top: 14px;
      left: 0;
    }
    83.3333333333% {
      top: 14px;
      left: 14px;
    }
    91.6666666666% {

    }
    100% {

    }
  }

  .loading-cat {
    position: fixed;
    top: -100%;
    right: -100%;
    left: -100%;
    bottom: -100%;
    margin: auto;
    z-index: 0;
    width: 480px;
    height: 360px;
    animation: 2.74s linear infinite loading-cat;
  }

  .loading-cat .head, .loading-cat .foot, .loading-cat .body {
    position: absolute;
    top: 0;
    right: 0;
    bottom: 0;
    left: 0;
    margin: auto;
    border-radius: 50%;
    width: 240px;
    height: 240px;
  }

  .loading-cat .body {
    background-image: radial-gradient(transparent 0%, transparent 35%, #383c4b 35%, #383c4b 39%, #eda65d 39%, #eda65d 46%, #f2c089 46%, #f2c089 60%, #eda65d 60%, #eda65d 67%, #383c4b 67%, #383c4b 100%);
  }

  .loading-cat .head:before, .loading-cat .foot:before {
    background-image: radial-gradient(transparent 0%, transparent 35%, #383c4b 35%, #383c4b 39%, #eda65d 39%, #eda65d 67%, #383c4b 67%, #383c4b 100%);
  }

  .loading-cat .head:before {
    content: '';
    width: 100%;
    height: 100%;
    position: absolute;
    border-radius: 50%;
    clip-path: polygon(100% 20%, 50% 50%, 70% -10%);
    -webkit-clip-path: polygon(100% 20%, 50% 50%, 70% -10%);
  }

  .loading-cat .head:after {
    content: '';
    width: 66px;
    height: 40px;
    position: absolute;
    top: 13px;
    right: 63px;
    background-image: linear-gradient(white 65%, transparent 65%), radial-gradient(white 51%, #383c4b 55%, #383c4b 68%, transparent 70%);
    transform: rotate(-66deg);
  }

  .loading-cat .head .face {
    width: 80px;
    height: 60px;
    left: 145px;
    top: 29px;
    position: absolute;
    transform: rotate(-47deg);
    background: radial-gradient(circle, #f2c089 0%, #f2c089 23%, transparent 23%) -3px 17px no-repeat, radial-gradient(circle, #383c4b 0%, #383c4b 6%, transparent 6%) 12px -12px no-repeat, radial-gradient(circle, #383c4b 0%, #383c4b 6%, transparent 6%) -12px -12px no-repeat, radial-gradient(#eda65d 0%, #eda65d 15%, transparent 15%) 0 -11px no-repeat, radial-gradient(circle, transparent 5%, #383c4b 5%, #383c4b 10%, transparent 10%) -3px -5px no-repeat, radial-gradient(circle, transparent 5%, #383c4b 5%, #383c4b 10%, transparent 10%) 3px -5px no-repeat, radial-gradient(circle, #eda65d 45%, transparent 45%) 0 -3px, linear-gradient(transparent 35%, #383c4b 35%, #383c4b 41%, transparent 41%, transparent 44%, #383c4b 44%, #383c4b 50%, transparent 50%, transparent 53%, #383c4b 53%, #383c4b 59%, transparent 59%);
  }

  .loading-cat .foot:before, .loading-cat .foot:after {
    content: '';
    width: 100%;
    height: 100%;
    position: absolute;
  }

  .loading-cat .foot:before {
    border-radius: 50%;
    clip-path: polygon(50% 50%, 0% 50%, 0% 25%);
    -webkit-clip-path: polygon(50% 50%, 0% 50%, 0% 25%);
  }

  .loading-cat .foot .tummy-end {
    width: 24px;
    height: 24px;
    position: absolute;
    border-radius: 50%;
    background-color: #f2c089;
    left: 19px;
    top: 105px;
  }

  .loading-cat .foot .bottom {
    width: 35px;
    height: 15px;
    position: absolute;
    top: 78px;
    left: 12px;
    border: 6px solid #383c4b;
    border-bottom: 0;
    border-radius: 50%;
    transform: rotate(21deg);
    background: #eda65d;
  }

  .loading-cat .hands, .loading-cat .legs, .loading-cat .foot:after {
    width: 10px;
    height: 25px;
    position: absolute;
    border: 6px solid #383c4b;
    background-color: #eda65d;
  }

  .loading-cat .hands {
    border-top: 0;
    border-radius: 0 0 12px 12px;
  }

  .loading-cat .hands.left {
    top: 144px;
    right: 163px;
    transform: rotate(-20deg);
  }

  .loading-cat .hands.right {
    top: 123px;
    right: 128px;
    transform: rotate(-25deg);
  }

  .loading-cat .legs {
    border-bottom: 0;
    border-radius: 12px 12px 0 0;
  }

  .loading-cat .legs.left {
    top: 65px;
    left: 50px;
    transform: rotate(25deg);
  }

  .loading-cat .legs.right {
    top: 53px;
    left: 12px;
    transform: rotate(22deg);
  }

  .loading-cat .foot:after {
    width: 8px;
    height: 40px;
    top: 42px;
    left: 36px;
    z-index: -1;
    transform: rotate(25deg);
    background-color: #c6823b;
    border-bottom: 0;
    border-radius: 12px 12px 0 0;
  }

  @keyframes body {
    0% {
      clip-path: polygon(50% 50%, 0% 50%, 0% 100%, 100% 100%, 100% 20%);
      -webkit-clip-path: polygon(50% 50%, 0% 50%, 0% 100%, 100% 100%, 100% 20%);
    }
    10% {
      clip-path: polygon(50% 50%, 30% 120%, 50% 100%, 100% 100%, 100% 20%);
      -webkit-clip-path: polygon(50% 50%, 30% 120%, 50% 100%, 100% 100%, 100% 20%);
    }
    20% {
      clip-path: polygon(50% 50%, 100% 90%, 120% 90%, 100% 100%, 100% 20%);
      -webkit-clip-path: polygon(50% 50%, 100% 90%, 120% 90%, 100% 100%, 100% 20%);
    }
    40% {
      clip-path: polygon(50% 50%, 100% 45%, 120% 45%, 120% 50%, 100% 20%);
      -webkit-clip-path: polygon(50% 50%, 100% 45%, 120% 45%, 120% 50%, 100% 20%);
    }
    50% {
      clip-path: polygon(50% 50%, 100% 45%, 120% 45%, 120% 50%, 100% 20%);
      -webkit-clip-path: polygon(50% 50%, 100% 45%, 120% 45%, 120% 50%, 100% 20%);
    }
    65% {
      clip-path: polygon(50% 50%, 100% 65%, 120% 65%, 120% 50%, 100% 20%);
      -webkit-clip-path: polygon(50% 50%, 100% 65%, 120% 65%, 120% 50%, 100% 20%);
    }
    80% {
      clip-path: polygon(50% 50%, 75% 130%, 120% 65%, 120% 50%, 100% 20%);
      -webkit-clip-path: polygon(50% 50%, 75% 130%, 120% 65%, 120% 50%, 100% 20%);
    }
    90% {
      clip-path: polygon(50% 50%, -20% 110%, 50% 120%, 100% 100%, 100% 20%);
      -webkit-clip-path: polygon(50% 50%, -20% 110%, 50% 120%, 100% 100%, 100% 20%);
    }
    100% {
      clip-path: polygon(50% 50%, 0% 50%, 0% 100%, 100% 100%, 100% 20%);
      -webkit-clip-path: polygon(50% 50%, 0% 50%, 0% 100%, 100% 100%, 100% 20%);
    }
  }

  @keyframes loading-cat {
    0% {
      transform: rotate(0deg);
    }
    10% {
      transform: rotate(-80deg);
    }
    20% {
      transform: rotate(-180deg);
    }
    40% {
      transform: rotate(-245deg);
    }
    50% {
      transform: rotate(-250deg);
    }
    68% {
      transform: rotate(-300deg);
    }
    90% {
      transform: rotate(-560deg);
    }
    100% {
      transform: rotate(-720deg);
    }
  }

  @keyframes foot {
    0% {
      transform: rotate(-10deg);
    }
    10% {
      transform: rotate(-100deg);
    }
    20% {
      transform: rotate(-145deg);
    }
    35% {
      transform: rotate(-190deg);
    }
    50% {
      transform: rotate(-195deg);
    }
    70% {
      transform: rotate(-165deg);
    }
    100% {
      transform: rotate(-10deg);
    }
  }

  .loading-cat .body {
    animation: 2.74s linear infinite body;
  }

  .loading-cat .foot {
    animation: 2.74s linear infinite foot;
  }
</style>
