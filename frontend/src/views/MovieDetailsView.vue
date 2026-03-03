<script setup>
import { computed } from 'vue'
import { useRoute, useRouter } from 'vue-router'
import { useRentalsStore } from '@/stores/rentals'
import StarRatingComponent from '@/components/StarRatingComponent.vue'

const route = useRoute()
const router = useRouter()
const rentalsStore = useRentalsStore()

// Données de test — à remplacer par un appel API
const allMovies = [
  {
    id: 1,
    title: 'Avengers: Endgame',
    poster: '/src/assets/poster.png',
    description:
      'Après les événements dévastateurs d\'Avengers: Infinity War, les Avengers se regroupent une dernière fois pour défaire les actions de Thanos et restaurer l\'équilibre de l\'univers.',
    genre: 'Action / Science-Fiction',
    duration: '3h 2min',
    year: 2019,
    rating: 8.4,
    price: 4.99,
  },
  {
    id: 2,
    title: 'Avengers: Endgame',
    poster: '/src/assets/poster.png',
    description: 'Une aventure épique qui conclut l\'histoire de cette équipe héroïque.',
    genre: 'Action / Science-Fiction',
    duration: '3h 2min',
    year: 2019,
    rating: 8.4,
    price: 4.99,
  },
  {
    id: 3,
    title: 'Avengers: Endgame',
    poster: '/src/assets/poster.png',
    description: 'Le combat final pour sauver l\'univers commence maintenant.',
    genre: 'Action / Science-Fiction',
    duration: '3h 2min',
    year: 2019,
    rating: 8.4,
    price: 4.99,
  },
  {
    id: 4,
    title: 'Avengers: Endgame',
    poster: '/src/assets/poster.png',
    description: 'Le dernier chapitre d\'une saga légendaire.',
    genre: 'Action / Science-Fiction',
    duration: '3h 2min',
    year: 2019,
    rating: 8.4,
    price: 4.99,
  },
  {
    id: 5,
    title: 'Avengers: Endgame',
    poster: '/src/assets/poster.png',
    description: 'Un film qui a marqué une génération.',
    genre: 'Action / Science-Fiction',
    duration: '3h 2min',
    year: 2019,
    rating: 8.4,
    price: 4.99,
  },
  {
    id: 6,
    title: 'Avengers: Endgame',
    poster: '/src/assets/poster.png',
    description: 'La bataille finale contre les forces du mal.',
    genre: 'Action / Science-Fiction',
    duration: '3h 2min',
    year: 2019,
    rating: 8.4,
    price: 4.99,
  },
  {
    id: 7,
    title: 'Avengers: Endgame',
    poster: '/src/assets/poster.png',
    description: 'Tout finit ici.',
    genre: 'Action / Science-Fiction',
    duration: '3h 2min',
    year: 2019,
    rating: 8.4,
    price: 4.99,
  },
  {
    id: 8,
    title: 'Avengers: Endgame',
    poster: '/src/assets/poster.png',
    description: 'Un voyage à travers le temps et l\'espace.',
    genre: 'Action / Science-Fiction',
    duration: '3h 2min',
    year: 2019,
    rating: 8.4,
    price: 4.99,
  },
]

const movie = computed(() => allMovies.find((m) => m.id === Number(route.params.id)) || allMovies[0])
const isRented = computed(() => rentalsStore.isRented(movie.value.id))
const rental = computed(() => rentalsStore.getRental(movie.value.id))
const hasWatched = computed(() => rentalsStore.hasWatched(movie.value.id))
const userRating = computed({
  get: () => rentalsStore.getUserRating(movie.value.id),
  set: (val) => rentalsStore.rateMovie(movie.value.id, val),
})

const openCheckout = () => {
  router.push({ name: 'checkout', query: { movieId: movie.value.id } })
}

const cancelRental = () => {
  rentalsStore.removeRental(movie.value.id)
}

const goToPlayer = () => {
  router.push({ name: 'player', params: { movieId: movie.value.id } })
}

const formatDate = (isoString) => {
  return new Date(isoString).toLocaleDateString('fr-FR', {
    day: '2-digit',
    month: 'long',
    year: 'numeric',
  })
}
</script>

<template>
  <div class="min-h-screen bg-(--bg-main)">
    <!-- Hero avec image -->
    <div class="relative w-full h-[60vh] overflow-hidden">
      <img
        :src="movie.poster || '/src/assets/poster.png'"
        :alt="movie.title"
        class="w-full h-full object-cover"
      />
      <div class="absolute inset-0 bg-gradient-to-t from-[var(--bg-main)] via-black/40 to-transparent"></div>

      <!-- Bouton retour -->
      <button
        @click="router.back()"
        class="absolute top-6 left-6 bg-black/50 hover:bg-black/80 text-white px-4 py-2 rounded-full flex items-center gap-2 transition-colors"
      >
        ← Retour
      </button>
    </div>

    <!-- Contenu principal -->
    <div class="max-w-5xl mx-auto px-6 -mt-32 relative z-10 pb-16">
      <div class="flex flex-col md:flex-row gap-8">
        <!-- Affiche -->
        <div class="shrink-0 w-48 md:w-64 rounded-xl overflow-hidden shadow-2xl">
          <img
            :src="movie.poster || '/src/assets/poster.png'"
            :alt="movie.title"
            class="w-full h-full object-cover"
          />
        </div>

        <!-- Infos -->
        <div class="flex-1 pt-4">
          <!-- Badge loué -->
          <div v-if="isRented" class="inline-flex items-center gap-2 bg-indigo-500/20 border border-indigo-500 text-indigo-400 px-3 py-1 rounded-full text-sm font-medium mb-4">
            <span>✓</span>
            <span>Dans vos locations — expire le {{ formatDate(rental.expiresAt) }}</span>
          </div>

          <h1 class="text-white text-4xl md:text-5xl font-bold mb-2">{{ movie.title }}</h1>

          <div class="flex flex-wrap items-center gap-4 mb-4 text-gray-400 text-sm">
            <span>{{ movie.year }}</span>
            <span class="w-1 h-1 rounded-full bg-gray-500"></span>
            <span>{{ movie.duration }}</span>
            <span class="w-1 h-1 rounded-full bg-gray-500"></span>
            <span>{{ movie.genre }}</span>
            <span class="w-1 h-1 rounded-full bg-gray-500"></span>
            <span class="flex items-center gap-1 text-yellow-400 font-semibold">
              ★ {{ movie.rating }}/10
            </span>
          </div>

          <p class="text-gray-300 text-lg leading-relaxed mb-8">{{ movie.description }}</p>

          <!-- Actions -->
          <div class="flex flex-wrap gap-4">
            <!-- Déjà loué : bouton lecture + annuler -->
            <template v-if="isRented">
              <button
                @click="goToPlayer"
                class="bg-white text-black px-8 py-3 rounded-lg font-semibold hover:bg-gray-200 transition-colors flex items-center gap-2"
              >
                ▶ Regarder
              </button>
              <button
                @click="cancelRental"
                class="border border-red-500 text-red-400 px-8 py-3 rounded-lg font-semibold hover:bg-red-500/10 transition-colors"
              >
                Annuler la location
              </button>
            </template>

            <!-- Pas encore loué -->
            <template v-else>
              <button
                @click="openCheckout"
                class="bg-indigo-600 hover:bg-indigo-500 text-white px-8 py-3 rounded-lg font-semibold transition-colors flex items-center gap-2"
              >
                🎬 Louer — {{ movie.price }}€
              </button>
              <button
                class="bg-gray-700/60 hover:bg-gray-600/60 text-white px-8 py-3 rounded-lg font-semibold transition-colors"
              >
                + Ma liste
              </button>
            </template>
          </div>

          <!-- Notation (si déjà visionné) -->
          <div v-if="hasWatched" class="mt-6 p-4 bg-gray-900/60 border border-gray-800 rounded-xl inline-flex flex-col gap-2">
            <p class="text-gray-400 text-sm font-medium">
              {{ userRating ? '⭐ Votre note' : '🎬 Notez ce film' }}
            </p>
            <StarRatingComponent v-model="userRating" size="md" />
          </div>
        </div>
      </div>
    </div>

    <!-- Modal de confirmation de location -->
    <Transition name="fade">
      <div
        v-if="showModal"
        class="fixed inset-0 z-50 flex items-center justify-center p-4 bg-black/70 backdrop-blur-sm"
        @click.self="closeModal"
      >
        <div class="bg-gray-900 border border-gray-700 rounded-2xl p-8 max-w-md w-full shadow-2xl">
          <!-- Succès -->
          <Transition name="fade" mode="out-in">
            <div v-if="confirmSuccess" class="text-center py-4">
              <div class="text-6xl mb-4">🎉</div>
              <h3 class="text-white text-2xl font-bold mb-2">Location confirmée !</h3>
              <p class="text-gray-400">Bon visionnage !</p>
            </div>

            <!-- Formulaire de confirmation -->
            <div v-else>
              <div class="flex justify-between items-start mb-6">
                <h3 class="text-white text-xl font-bold">Confirmer la location</h3>
                <button @click="closeModal" class="text-gray-400 hover:text-white text-2xl leading-none">×</button>
              </div>

              <!-- Résumé film -->
              <div class="flex gap-4 mb-6 p-4 bg-gray-800/60 rounded-xl">
                <img
                  :src="movie.poster || '/src/assets/poster.png'"
                  :alt="movie.title"
                  class="w-16 h-24 object-cover rounded-lg"
                />
                <div>
                  <p class="text-white font-semibold">{{ movie.title }}</p>
                  <p class="text-gray-400 text-sm mt-1">{{ movie.duration }} · {{ movie.year }}</p>
                  <p class="text-gray-400 text-sm">Valide 30 jours</p>
                  <p class="text-indigo-400 font-bold text-lg mt-2">{{ movie.price }} €</p>
                </div>
              </div>

              <!-- Boutons -->
              <div class="flex gap-3">
                <button
                  @click="closeModal"
                  class="flex-1 border border-gray-600 text-gray-300 py-3 rounded-lg hover:bg-gray-700/40 transition-colors"
                >
                  Annuler
                </button>
                <button
                  @click="confirmRental"
                  class="flex-1 bg-indigo-600 hover:bg-indigo-500 text-white py-3 rounded-lg font-semibold transition-colors"
                >
                  Confirmer
                </button>
              </div>
            </div>
          </Transition>
        </div>
      </div>
    </Transition>
  </div>
</template>

<style>
.fade-enter-active,
.fade-leave-active {
  transition: opacity 0.3s ease;
}
.fade-enter-from,
.fade-leave-to {
  opacity: 0;
}
</style>

