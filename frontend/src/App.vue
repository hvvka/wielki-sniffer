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
                          item-text="title"
                          :loading="searching"
                          @input="hintClickedHandler"
                          @update:search-input="requestForHints"
                          :filter="allowAllFilterFunction"
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
      <router-link to="/page/:id">
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
  </v-app>
</template>

<script>
  export default {
    data: () => ({
      searching: false,
      hints: []
    }),
    methods: {
      hintClickedHandler(inputValue) {
        if (typeof inputValue === 'object') { // or alternativly this.hints.includes(inputValue)
          console.log('hint: ' + JSON.stringify(inputValue))
        } else {
          console.log("custom:" + inputValue);
        }
      },
      requestForHints(input) {
        if (!input) {
          this.hints = []; // fix for clearing hints when user delete all input
                            // this prevents from entering enter key by user and injecting value from hints array
          return;
        }
        
        this.searching = true;
        this.axios.get('http://localhost:8080/v1/search/hint?query='+input)
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
        return true
        // The function used for filtering items
        // (item: object, queryText: string, itemText: string) => boolean
        // because results from backend can contain items that doesnt contain inputed value by user
        // these values are filtered by default and in result they are not displayed
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
</style>
