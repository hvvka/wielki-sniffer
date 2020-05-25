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
    <div class="overlay" style="" v-if="getFullScreenLoader">
      <div class="loading-spinner">
        <div class="dot dotOne"></div>
        <div class="dot dotTwo"></div>
        <div class="dot dotThree"></div>
      </div>
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
    text-shadow: 0.1px 0.1px black;
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
</style>
