import Vue from 'vue'

import 'primevue/resources/themes/nova-light/theme.css';
import 'primevue/resources/primevue.min.css';
import 'primeicons/primeicons.css';

import Chips from 'primevue/chips'
Vue.component('Chips', Chips);

import Chips2 from '../components/primevue_custom/Chips2'
Vue.component('Chips2', Chips2);

import Calendar from 'primevue/calendar'
Vue.component('Calendar', Calendar);

import AutoComplete from 'primevue/autocomplete';
Vue.component('AutoComplete', AutoComplete);

import Dropdown from 'primevue/dropdown';
Vue.component('Dropdown', Dropdown);

import Card from 'primevue/card';
Vue.component('Card', Card);
