<script setup>
import { computed } from 'vue'
import { useRouter } from 'vue-router'
import { useRentalsStore } from '@/stores/rentals'
import StarRatingComponent from '@/components/StarRatingComponent.vue'

const router = useRouter()
const rentalsStore = useRentalsStore()

const rentals = computed(() => rentalsStore.rentals)

const formatDate = (isoString) => {
  return new Date(isoString).toLocaleDateString('fr-FR', {
    day: '2-digit',
    month: 'long',
    year: 'numeric',
  })
}

const daysLeft = (expiresAt) => {
  const diff = new Date(expiresAt) - Date.now()
  return Math.max(0, Math.ceil(diff / (1000 * 60 * 60 * 24)))
}

const goToDetails = (id) => {
  router.push({ name: 'movie', params: { id } })
}

const goToPlayer = (movieId) => {
  router.push({ name: 'player', params: { movieId } })
}

const cancelRental = (movieId) => {
  rentalsStore.removeRental(movieId)
}

const rateMovie = (movieId, rating) => {
  rentalsStore.rateMovie(movieId, rating)
}
</script>

<template>
  <div class="min-h-screen bg-(--bg-main) py-12 px-4 md:px-8">
    <div class="max-w-6xl mx-auto">

      <!-- En-tête -->
      <div class="mb-10">
        <h1 class="text-white text-4xl font-bold mb-2">Mes locations</h1>
        <p class="text-gray-400">
          {{ rentals.length > 0 ? `${rentals.length} film${rentals.length > 1 ? 's' : ''} loué${rentals.length > 1 ? 's' : ''}` : 'Aucun film loué pour le moment' }}
        </p>
      </div>

      <!-- État vide -->
      <div
        v-if="rentals.length === 0"
        class="flex flex-col items-center justify-center py-24 text-center"
      >
        <div class="text-8xl mb-6">🎬</div>
        <h2 class="text-white text-2xl font-semibold mb-3">Votre liste est vide</h2>
        <p class="text-gray-400 mb-8 max-w-md">
          Parcourez notre catalogue et louez vos films préférés pour les retrouver ici.
        </p>
        <button
          @click="router.push({ name: 'home' })"
          class="bg-indigo-600 hover:bg-indigo-500 text-white px-8 py-3 rounded-lg font-semibold transition-colors"
        >
          Découvrir des films
        </button>
      </div>

      <!-- Liste des locations -->
      <div v-else class="grid grid-cols-1 sm:grid-cols-2 lg:grid-cols-3 xl:grid-cols-4 gap-6">
        <div
          v-for="rental in rentals"
          :key="rental.id"
          class="bg-gray-900/60 border border-gray-800 rounded-2xl overflow-hidden hover:border-indigo-500/50 transition-all duration-300 group"
        >
          <!-- Affiche cliquable -->
          <div
            class="relative aspect-video cursor-pointer overflow-hidden"
            @click="goToDetails(rental.id)"
          >
            <img
              :src="rental.poster || '/src/assets/poster.png'"
              :alt="rental.title"
              class="w-full h-full object-cover group-hover:scale-105 transition-transform duration-500"
            />
            <!-- Overlay lecture -->
            <div class="absolute inset-0 bg-black/50 opacity-0 group-hover:opacity-100 transition-opacity flex items-center justify-center">
              <button
                @click.stop="goToPlayer(rental.id)"
                class="bg-white/90 text-black rounded-full w-14 h-14 flex items-center justify-center text-2xl hover:bg-white transition-colors shadow-lg"
              >
                ▶
              </button>
            </div>

            <!-- Badge jours restants -->
            <div
              class="absolute top-2 left-2 px-2 py-1 rounded-full text-xs font-bold"
              :class="daysLeft(rental.expiresAt) <= 3 ? 'bg-red-500 text-white' : 'bg-indigo-500/90 text-white'"
            >
              {{ daysLeft(rental.expiresAt) }}j restants
            </div>

            <!-- Badge "Visionné" -->
            <div
              v-if="rentalsStore.hasWatched(rental.id)"
              class="absolute top-2 right-2 px-2 py-1 rounded-full text-xs font-bold bg-green-600/90 text-white"
            >
              ✓ Vu
            </div>
          </div>

          <!-- Informations -->
          <div class="p-4">
            <h3
              class="text-white font-semibold text-lg cursor-pointer hover:text-indigo-400 transition-colors line-clamp-1 mb-1"
              @click="goToDetails(rental.id)"
            >
              {{ rental.title }}
            </h3>
            <p class="text-gray-500 text-xs mb-3">
              Loué le {{ formatDate(rental.rentedAt) }} · expire le {{ formatDate(rental.expiresAt) }}
            </p>

            <!-- Notation (uniquement si déjà visionné) -->
            <div v-if="rentalsStore.hasWatched(rental.id)" class="mb-3">
              <p class="text-gray-500 text-xs mb-1">
                {{ rentalsStore.getUserRating(rental.id) ? 'Votre note' : 'Noter ce film' }}
              </p>
              <StarRatingComponent
                :model-value="rentalsStore.getUserRating(rental.id)"
                size="sm"
                @update:modelValue="(v) => rateMovie(rental.id, v)"
              />
            </div>

            <!-- Boutons -->
            <div class="flex gap-2">
              <button
                @click="goToPlayer(rental.id)"
                class="flex-1 bg-indigo-600 hover:bg-indigo-500 text-white py-2 rounded-lg text-sm font-semibold transition-colors flex items-center justify-center gap-1"
              >
                ▶ Regarder
              </button>
              <button
                @click="cancelRental(rental.id)"
                class="border border-gray-700 hover:border-red-500 text-gray-400 hover:text-red-400 px-3 py-2 rounded-lg text-sm transition-colors"
                title="Supprimer la location"
              >
                🗑
              </button>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<style scoped></style>
