<template>
  <v-app id="inspire">
    <v-app-bar :clipped-left="$vuetify.breakpoint.lgAndUp" app color="blue darken-3" dark>
      <v-container fluid>
          <v-row justify="center" align="end">
            <v-col cols="6">
            <v-text-field flat solo-inverted hide-details prepend-inner-icon="mdi-magnify" label="Search"
                          class="hidden-sm-and-down" />
            </v-col>
            <v-col cols="2" class="mb-0 pl-0">
              <router-link to="/advanced">
                <span class="whiteAnchor">Advanced search</span>
              </router-link>
            </v-col>
          </v-row>
      </v-container>
      <v-spacer />
    </v-app-bar>

    <v-content>
      <router-view/>
    </v-content>

    <v-footer>
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

    }),
  }
</script>

<style>
  .whiteAnchor {
    color: white;
  }
</style>
