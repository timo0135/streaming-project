<script setup>
import { useRouter } from 'vue-router'
import { useRentalsStore } from '@/stores/rentals'

const props = defineProps({
  movie: {
    type: Object,
    required: true,
  },
})

const router = useRouter()
const rentalsStore = useRentalsStore()

const goToDetails = () => {
  router.push({ name: 'movie', params: { id: props.movie.id } })
}
</script>

<template>
  <div
    @click="goToDetails"
    class="relative group cursor-pointer rounded-lg overflow-hidden shadow-lg hover:shadow-2xl hover:scale-105 transition-all duration-300"
  >
    <!-- Badge "Loué" -->
    <div
      v-if="rentalsStore.isRented(movie.id)"
      class="absolute top-2 right-2 z-10 bg-indigo-500 text-white text-xs font-bold px-2 py-1 rounded-full"
    >
      ✓ Loué
    </div>

    <!-- Affiche du film -->
    <div class="aspect-video w-full">
      <img
        :src="movie.poster || 'https://via.placeholder.com/400x225?text=No+Image'"
        :alt="movie.title"
        class="w-full h-full object-cover"
      />
    </div>

    <!-- Titre du film en bas à gauche -->
    <div
      class="absolute bottom-0 left-0 right-0 bg-gradient-to-t from-black/80 via-black/50 to-transparent p-4"
    >
      <h3 class="text-white font-semibold text-lg line-clamp-2">
        {{ movie.title }}
      </h3>
    </div>
  </div>
</template>

<style scoped></style>
