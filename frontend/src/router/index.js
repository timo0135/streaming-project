import AccountView from '@/views/AccountView.vue'
import CheckoutView from '@/views/CheckoutView.vue'
import HomeView from '@/views/HomeView.vue'
import LoginView from '@/views/LoginView.vue'
import MovieDetailsView from '@/views/MovieDetailsView.vue'
import PlayerView from '@/views/PlayerView.vue'
import RentalsView from '@/views/RentalsView.vue'
import { createRouter, createWebHistory } from 'vue-router'


const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      name: 'home',
      path: '/',
      component: HomeView,
    },
    {
      name: 'movies',
      path: '/movies',
      children: [
        {
          name: 'movie',
          path: ':id',
          component: MovieDetailsView,
        }
      ],
    },
    {
      name: 'checkout',
      path: '/checkout',
      component: CheckoutView,
    },
    {
      name: 'player',
      path: '/player/:movieId',
      component: PlayerView,
    },
    {
      name: 'rentals',
      path: '/rentals',
      component: RentalsView,
    },
    {
      name: 'account',
      path: '/account',
      component: AccountView,
    },
    {
      name: 'login',
      path: '/login',
      component: LoginView,
    }
  ],
})

export default router
