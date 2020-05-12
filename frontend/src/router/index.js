import Vue from 'vue'
import VueRouter from 'vue-router'
import AdvancedSearch from '../views/AdvancedSearch.vue'
import ResultPreviewPage from "../views/ResultPreviewPage";
import SearchEngineResultPage from "../views/SearchEngineResultPage";

Vue.use(VueRouter);

const routes = [
    {
        path: '/advanced',
        name: 'Advanced',
        component: AdvancedSearch
    },
    {
        path: '/page/:id',
        name: 'Document preview',
        component: ResultPreviewPage,
        props: true
    },
    {
        path: '/search-results',
        name: 'Search Engine Result Page',
        component: SearchEngineResultPage
    },
    {
        path: '/about',
        name: 'About',
        // route level code-splitting
        // this generates a separate chunk (about.[hash].js) for this route
        // which is lazy-loaded when the route is visited.
        component: () => import(/* webpackChunkName: "about" */ '../views/About.vue')
    }
];

const router = new VueRouter({
    routes
});

export default router
