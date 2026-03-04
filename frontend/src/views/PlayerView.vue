<script setup>
import { ref, computed, onMounted } from 'vue'
import { useRoute, useRouter } from 'vue-router'
import { useRentalsStore } from '@/stores/rentals'
import StarRatingComponent from '@/components/StarRatingComponent.vue'

const route = useRoute()
const router = useRouter()
const rentalsStore = useRentalsStore()

// Données de test (mêmes qu'ailleurs — à centraliser plus tard)
const allMovies = [
  { id: 1, title: 'Avengers: Endgame', poster: '/src/assets/poster.png', duration: '3h 2min', year: 2019, genre: 'Action / Science-Fiction', rating: 8.4, price: 4.99, description: 'Après les événements dévastateurs d\'Avengers: Infinity War...' },
  { id: 2, title: 'Avengers: Endgame', poster: '/src/assets/poster.png', duration: '3h 2min', year: 2019, genre: 'Action / Science-Fiction', rating: 8.4, price: 4.99, description: 'Une aventure épique.' },
  { id: 3, title: 'Avengers: Endgame', poster: '/src/assets/poster.png', duration: '3h 2min', year: 2019, genre: 'Action / Science-Fiction', rating: 8.4, price: 4.99, description: 'Le combat final.' },
  { id: 4, title: 'Avengers: Endgame', poster: '/src/assets/poster.png', duration: '3h 2min', year: 2019, genre: 'Action / Science-Fiction', rating: 8.4, price: 4.99, description: 'Le dernier chapitre.' },
  { id: 5, title: 'Avengers: Endgame', poster: '/src/assets/poster.png', duration: '3h 2min', year: 2019, genre: 'Action / Science-Fiction', rating: 8.4, price: 4.99, description: 'Un film légendaire.' },
  { id: 6, title: 'Avengers: Endgame', poster: '/src/assets/poster.png', duration: '3h 2min', year: 2019, genre: 'Action / Science-Fiction', rating: 8.4, price: 4.99, description: 'La bataille finale.' },
  { id: 7, title: 'Avengers: Endgame', poster: '/src/assets/poster.png', duration: '3h 2min', year: 2019, genre: 'Action / Science-Fiction', rating: 8.4, price: 4.99, description: 'Tout finit ici.' },
  { id: 8, title: 'Avengers: Endgame', poster: '/src/assets/poster.png', duration: '3h 2min', year: 2019, genre: 'Action / Science-Fiction', rating: 8.4, price: 4.99, description: 'Un voyage épique.' },
]

const movie = computed(() => allMovies.find((m) => m.id === Number(route.params.movieId)) || allMovies[0])

// Note de l'utilisateur (v-model)
const userRating = ref(rentalsStore.getUserRating(movie.value.id))
const ratingSubmitted = ref(userRating.value !== null)
const showRatingModal = ref(false)

// Simuler la fin de lecture — on enregistre le visionnage au montage
onMounted(() => {
  rentalsStore.addToHistory(movie.value)
  // Si pas encore noté, proposer la notation après un délai
  if (!rentalsStore.hasWatched(movie.value.id) || rentalsStore.getUserRating(movie.value.id) === null) {
    setTimeout(() => {
      showRatingModal.value = true
    }, 2000)
  }
})

const submitRating = () => {
  if (userRating.value) {
    rentalsStore.rateMovie(movie.value.id, userRating.value)
    ratingSubmitted.value = true
    showRatingModal.value = false
  }
}

const skipRating = () => {
  showRatingModal.value = false
}
</script>

<template>
  <div class="min-h-screen bg-black flex flex-col">
    <!-- Barre du haut -->
    <div class="flex items-center justify-between px-6 py-4 bg-black/80 backdrop-blur-sm z-10">
      <button
        @click="router.back()"
        class="text-white/70 hover:text-white flex items-center gap-2 transition-colors"
      >
        ← Retour
      </button>
      <h1 class="text-white font-semibold text-lg">{{ movie.title }}</h1>
      <div class="w-16"></div>
    </div>

    <!-- Zone vidéo (simulée) -->
    <div class="flex-1 flex items-center justify-center relative">
      <div class="w-full max-w-5xl mx-4 aspect-video bg-gray-900 rounded-xl overflow-hidden relative shadow-2xl">
        <!-- Affiche en fond -->
        <img
          :src="movie.poster || '/src/assets/poster.png'"
          :alt="movie.title"
          class="w-full h-full object-cover opacity-40"
        />
        <!-- Icône play au centre -->
        <div class="absolute inset-0 flex flex-col items-center justify-center gap-4">
          <div class="w-20 h-20 rounded-full bg-white/10 border-2 border-white/30 flex items-center justify-center text-4xl text-white">
            ▶
          </div>
          <p class="text-white/60 text-sm">Lecteur vidéo — à intégrer</p>
        </div>
      </div>
    </div>

    <!-- Infos film en bas -->
    <div class="px-6 py-5 bg-black/80">
      <div class="max-w-5xl mx-auto flex items-center justify-between gap-4">
        <div>
          <p class="text-gray-400 text-sm">{{ movie.genre }} · {{ movie.duration }}</p>
          <p class="text-white font-bold text-xl">{{ movie.title }}</p>
        </div>
        <!-- Notation rapide -->
        <div class="flex flex-col items-end gap-1">
          <p class="text-gray-400 text-xs">Votre note</p>
          <StarRatingComponent
            v-model="userRating"
            size="md"
            @update:modelValue="(v) => { rentalsStore.rateMovie(movie.id, v); ratingSubmitted = true }"
          />
          <p v-if="ratingSubmitted" class="text-green-400 text-xs">Note enregistrée ✓</p>
        </div>
      </div>
    </div>

    <!-- Modal de notation (apparaît automatiquement) -->
    <Transition name="fade">
      <div
        v-if="showRatingModal"
        class="fixed inset-0 z-50 flex items-center justify-center p-4 bg-black/70 backdrop-blur-sm"
      >
        <div class="bg-gray-900 border border-gray-700 rounded-2xl p-8 max-w-sm w-full text-center shadow-2xl">
          <div class="text-5xl mb-4">🎬</div>
          <h3 class="text-white text-xl font-bold mb-1">Vous venez de regarder</h3>
          <p class="text-indigo-400 font-semibold mb-6">{{ movie.title }}</p>

          <p class="text-gray-400 text-sm mb-4">Qu'avez-vous pensé du film ?</p>

          <div class="flex justify-center mb-6">
            <StarRatingComponent v-model="userRating" size="lg" />
          </div>

          <div class="flex gap-3">
            <button
              @click="skipRating"
              class="flex-1 border border-gray-600 text-gray-400 py-2.5 rounded-lg hover:bg-gray-700/40 transition-colors text-sm"
            >
              Plus tard
            </button>
            <button
              @click="submitRating"
              :disabled="!userRating"
              class="flex-1 bg-indigo-600 hover:bg-indigo-500 disabled:opacity-40 disabled:cursor-not-allowed text-white py-2.5 rounded-lg font-semibold transition-colors text-sm"
            >
              Envoyer
            </button>
          </div>
        </div>
      </div>
    </Transition>
  </div>
</template>

<style>
.fade-enter-active, .fade-leave-active { transition: opacity 0.3s ease; }
.fade-enter-from, .fade-leave-to { opacity: 0; }
</style>

