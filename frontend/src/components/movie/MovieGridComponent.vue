<script setup>
import { ref } from 'vue'
import MovieCardComponent from './MovieCardComponent.vue'

defineProps({
  title: {
    type: String,
    default: 'Films populaires',
  },
})

// Données de test avec le même film répété
const movies = ref([
  { id: 1, title: 'Avengers: Endgame', poster: '/src/assets/poster.png' },
  { id: 2, title: 'Avengers: Endgame', poster: '/src/assets/poster.png' },
  { id: 3, title: 'Avengers: Endgame', poster: '/src/assets/poster.png' },
  { id: 4, title: 'Avengers: Endgame', poster: '/src/assets/poster.png' },
  { id: 5, title: 'Avengers: Endgame', poster: '/src/assets/poster.png' },
  { id: 6, title: 'Avengers: Endgame', poster: '/src/assets/poster.png' },
  { id: 7, title: 'Avengers: Endgame', poster: '/src/assets/poster.png' },
  { id: 8, title: 'Avengers: Endgame', poster: '/src/assets/poster.png' },
])

const scrollContainer = ref(null)

const scrollLeft = () => {
  if (scrollContainer.value) {
    scrollContainer.value.scrollBy({
      left: -400,
      behavior: 'smooth',
    })
  }
}

const scrollRight = () => {
  if (scrollContainer.value) {
    scrollContainer.value.scrollBy({
      left: 400,
      behavior: 'smooth',
    })
  }
}
</script>

<template>
  <div class="relative group/carousel mb-8">
    <!-- Titre de la section -->
    <h2 class="text-2xl font-bold text-(--text-primary) mb-4 px-4 md:px-8">
      {{ title }}
    </h2>

    <!-- Bouton de navigation gauche -->
    <button
      @click="scrollLeft"
      class="absolute left-0 top-1/2 -translate-y-1/2 z-10 bg-black/70 hover:bg-black/90 text-white p-3 rounded-r-lg opacity-0 group-hover/carousel:opacity-100 transition-opacity duration-300 hidden md:block"
      aria-label="Scroll left"
    >
      <svg
        xmlns="http://www.w3.org/2000/svg"
        class="h-6 w-6"
        fill="none"
        viewBox="0 0 24 24"
        stroke="currentColor"
      >
        <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M15 19l-7-7 7-7" />
      </svg>
    </button>

    <!-- Container du carrousel -->
    <div
      ref="scrollContainer"
      class="flex gap-4 overflow-x-auto scrollbar-hide scroll-smooth px-4 md:px-8 pb-4"
    >
      <div v-for="movie in movies" :key="movie.id" class="shrink-0 w-64 md:w-80">
        <MovieCardComponent :movie="movie" />
      </div>
    </div>

    <!-- Bouton de navigation droite -->
    <button
      @click="scrollRight"
      class="absolute right-0 top-1/2 -translate-y-1/2 z-10 bg-black/70 hover:bg-black/90 text-white p-3 rounded-l-lg opacity-0 group-hover/carousel:opacity-100 transition-opacity duration-300 hidden md:block"
      aria-label="Scroll right"
    >
      <svg
        xmlns="http://www.w3.org/2000/svg"
        class="h-6 w-6"
        fill="none"
        viewBox="0 0 24 24"
        stroke="currentColor"
      >
        <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M9 5l7 7-7 7" />
      </svg>
    </button>
  </div>
</template>

<style scoped>
/* Cacher la scrollbar mais permettre le scroll */
.scrollbar-hide {
  -ms-overflow-style: none;
  scrollbar-width: none;
}

.scrollbar-hide::-webkit-scrollbar {
  display: none;
}
</style>
