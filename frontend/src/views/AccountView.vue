<script setup>
import { computed, ref } from 'vue'
import { useRouter } from 'vue-router'
import { useRentalsStore } from '@/stores/rentals'
import StarRatingComponent from '@/components/StarRatingComponent.vue'

const router = useRouter()
const rentalsStore = useRentalsStore()

const profilIcon = new URL('../assets/profil.png', import.meta.url).href

// Données du profil (statiques pour l'instant)
const user = {
  name: 'Utilisateur',
  email: 'user@streamit.fr',
  memberSince: '2024-01-15',
}

const watchHistory = computed(() => rentalsStore.watchHistory)
const rentals = computed(() => rentalsStore.rentals)

// Stats
const totalWatched = computed(() => watchHistory.value.length)
const totalRated = computed(() => watchHistory.value.filter((h) => h.userRating !== null).length)
const averageRating = computed(() => {
  const rated = watchHistory.value.filter((h) => h.userRating)
  if (!rated.length) return null
  return (rated.reduce((sum, h) => sum + h.userRating, 0) / rated.length).toFixed(1)
})

// Onglets
const activeTab = ref('history') // 'history' | 'rentals'

const formatDate = (isoString) => {
  return new Date(isoString).toLocaleDateString('fr-FR', {
    day: '2-digit',
    month: 'long',
    year: 'numeric',
  })
}

const formatDateShort = (isoString) => {
  return new Date(isoString).toLocaleDateString('fr-FR', {
    day: '2-digit',
    month: 'short',
    year: 'numeric',
  })
}

const ratingLabels = [null, 'Mauvais', 'Passable', 'Bien', 'Très bien', 'Excellent']

const rateMovie = (movieId, rating) => {
  rentalsStore.rateMovie(movieId, rating)
}
</script>

<template>
  <div class="min-h-screen bg-(--bg-main) pb-16">

    <!-- Bannière profil -->
    <div class="relative h-40 bg-gradient-to-r from-indigo-900/60 via-purple-900/40 to-transparent">
      <div class="absolute inset-0 bg-linear-to-b from-transparent to-(--bg-main)"></div>
    </div>

    <div class="max-w-5xl mx-auto px-4 md:px-8 -mt-16 relative">

      <!-- Carte profil -->
      <div class="flex flex-col sm:flex-row items-start sm:items-end gap-5 mb-10">
        <div class="relative">
          <img
            :src="profilIcon"
            alt="Photo de profil"
            class="w-24 h-24 rounded-full object-cover ring-4 ring-(--bg-main) shadow-xl"
          />
          <div class="absolute bottom-1 right-1 w-4 h-4 bg-green-500 rounded-full ring-2 ring-(--bg-main)"></div>
        </div>

        <div class="flex-1">
          <h1 class="text-white text-3xl font-bold">{{ user.name }}</h1>
          <p class="text-gray-400 text-sm">{{ user.email }} · Membre depuis {{ formatDate(user.memberSince) }}</p>
        </div>

        <button class="border border-gray-700 hover:border-indigo-500 text-gray-300 hover:text-white px-5 py-2 rounded-lg text-sm transition-colors">
          ✏️ Modifier le profil
        </button>
      </div>

      <!-- Stats rapides -->
      <div class="grid grid-cols-2 sm:grid-cols-4 gap-4 mb-10">
        <div class="bg-gray-900/60 border border-gray-800 rounded-2xl p-5 text-center">
          <p class="text-3xl font-bold text-white">{{ totalWatched }}</p>
          <p class="text-gray-400 text-sm mt-1">Films vus</p>
        </div>
        <div class="bg-gray-900/60 border border-gray-800 rounded-2xl p-5 text-center">
          <p class="text-3xl font-bold text-white">{{ rentals.length }}</p>
          <p class="text-gray-400 text-sm mt-1">Locations actives</p>
        </div>
        <div class="bg-gray-900/60 border border-gray-800 rounded-2xl p-5 text-center">
          <p class="text-3xl font-bold text-white">{{ totalRated }}</p>
          <p class="text-gray-400 text-sm mt-1">Films notés</p>
        </div>
        <div class="bg-gray-900/60 border border-gray-800 rounded-2xl p-5 text-center">
          <p class="text-3xl font-bold text-yellow-400">
            {{ averageRating ? `${averageRating}★` : '—' }}
          </p>
          <p class="text-gray-400 text-sm mt-1">Note moyenne</p>
        </div>
      </div>

      <!-- Onglets -->
      <div class="flex gap-1 mb-8 bg-gray-900/40 p-1 rounded-xl w-fit">
        <button
          @click="activeTab = 'history'"
          class="px-5 py-2.5 rounded-lg text-sm font-medium transition-colors"
          :class="activeTab === 'history' ? 'bg-indigo-600 text-white' : 'text-gray-400 hover:text-white'"
        >
          📽️ Historique de visionnage
        </button>
        <button
          @click="activeTab = 'rentals'"
          class="px-5 py-2.5 rounded-lg text-sm font-medium transition-colors"
          :class="activeTab === 'rentals' ? 'bg-indigo-600 text-white' : 'text-gray-400 hover:text-white'"
        >
          🎬 Locations ({{ rentals.length }})
        </button>
      </div>

      <!-- Onglet Historique -->
      <div v-if="activeTab === 'history'">
        <!-- État vide -->
        <div v-if="watchHistory.length === 0" class="text-center py-20">
          <div class="text-7xl mb-5">📽️</div>
          <h2 class="text-white text-xl font-semibold mb-2">Aucun film visionné</h2>
          <p class="text-gray-400 mb-6">Louez un film et regardez-le pour qu'il apparaisse ici.</p>
          <button
            @click="router.push({ name: 'home' })"
            class="bg-indigo-600 hover:bg-indigo-500 text-white px-6 py-2.5 rounded-lg font-semibold transition-colors"
          >
            Explorer les films
          </button>
        </div>

        <!-- Liste historique -->
        <div v-else class="space-y-4">
          <div
            v-for="entry in watchHistory"
            :key="entry.id"
            class="bg-gray-900/50 border border-gray-800 hover:border-indigo-500/40 rounded-2xl p-4 flex gap-4 items-center transition-all group"
          >
            <!-- Affiche -->
            <div
              class="shrink-0 w-20 h-14 rounded-lg overflow-hidden cursor-pointer"
              @click="router.push({ name: 'movie', params: { id: entry.id } })"
            >
              <img
                :src="entry.poster || '/src/assets/poster.png'"
                :alt="entry.title"
                class="w-full h-full object-cover group-hover:scale-105 transition-transform duration-300"
              />
            </div>

            <!-- Infos -->
            <div class="flex-1 min-w-0">
              <h3
                class="text-white font-semibold cursor-pointer hover:text-indigo-400 transition-colors truncate"
                @click="router.push({ name: 'movie', params: { id: entry.id } })"
              >
                {{ entry.title }}
              </h3>
              <div class="flex flex-wrap items-center gap-3 mt-1">
                <span class="text-gray-500 text-xs">Vu le {{ formatDateShort(entry.watchedAt) }}</span>
                <span v-if="entry.watchCount > 1" class="text-indigo-400 text-xs">
                  × {{ entry.watchCount }} visionnages
                </span>
              </div>
            </div>

            <!-- Notation -->
            <div class="shrink-0 flex flex-col items-end gap-1">
              <p class="text-gray-500 text-xs">
                {{ entry.userRating ? ratingLabels[entry.userRating] : 'Pas encore noté' }}
              </p>
              <StarRatingComponent
                :model-value="entry.userRating"
                size="sm"
                @update:modelValue="(v) => rateMovie(entry.id, v)"
              />
            </div>

            <!-- Bouton regarder -->
            <button
              @click="router.push({ name: 'player', params: { movieId: entry.id } })"
              class="shrink-0 bg-gray-800 hover:bg-indigo-600 text-gray-300 hover:text-white p-2.5 rounded-lg transition-colors"
              title="Revoir ce film"
            >
              ▶
            </button>
          </div>
        </div>
      </div>

      <!-- Onglet Locations -->
      <div v-if="activeTab === 'rentals'">
        <div v-if="rentals.length === 0" class="text-center py-20">
          <div class="text-7xl mb-5">🎬</div>
          <h2 class="text-white text-xl font-semibold mb-2">Aucune location active</h2>
          <button
            @click="router.push({ name: 'home' })"
            class="bg-indigo-600 hover:bg-indigo-500 text-white px-6 py-2.5 rounded-lg font-semibold transition-colors mt-4"
          >
            Explorer les films
          </button>
        </div>

        <div v-else class="space-y-4">
          <div
            v-for="rental in rentals"
            :key="rental.id"
            class="bg-gray-900/50 border border-gray-800 hover:border-indigo-500/40 rounded-2xl p-4 flex gap-4 items-center transition-all group"
          >
            <!-- Affiche -->
            <div
              class="shrink-0 w-20 h-14 rounded-lg overflow-hidden cursor-pointer"
              @click="router.push({ name: 'movie', params: { id: rental.id } })"
            >
              <img
                :src="rental.poster || '/src/assets/poster.png'"
                :alt="rental.title"
                class="w-full h-full object-cover group-hover:scale-105 transition-transform duration-300"
              />
            </div>

            <!-- Infos -->
            <div class="flex-1 min-w-0">
              <h3
                class="text-white font-semibold cursor-pointer hover:text-indigo-400 transition-colors truncate"
                @click="router.push({ name: 'movie', params: { id: rental.id } })"
              >
                {{ rental.title }}
              </h3>
              <div class="flex flex-wrap items-center gap-3 mt-1">
                <span class="text-gray-500 text-xs">Loué le {{ formatDateShort(rental.rentedAt) }}</span>
                <span class="text-gray-500 text-xs">· expire le {{ formatDateShort(rental.expiresAt) }}</span>
                <span
                  v-if="rentalsStore.hasWatched(rental.id)"
                  class="text-green-400 text-xs font-medium"
                >✓ Visionné</span>
              </div>
            </div>

            <!-- Note si visionné -->
            <div v-if="rentalsStore.hasWatched(rental.id)" class="shrink-0 flex flex-col items-end gap-1">
              <p class="text-gray-500 text-xs">
                {{ rentalsStore.getUserRating(rental.id) ? ratingLabels[rentalsStore.getUserRating(rental.id)] : 'Pas encore noté' }}
              </p>
              <StarRatingComponent
                :model-value="rentalsStore.getUserRating(rental.id)"
                size="sm"
                @update:modelValue="(v) => rateMovie(rental.id, v)"
              />
            </div>

            <!-- Bouton regarder -->
            <button
              @click="router.push({ name: 'player', params: { movieId: rental.id } })"
              class="shrink-0 bg-gray-800 hover:bg-indigo-600 text-gray-300 hover:text-white p-2.5 rounded-lg transition-colors"
            >
              ▶
            </button>
          </div>
        </div>
      </div>

    </div>
  </div>
</template>

<style scoped></style>
