import { defineStore } from 'pinia'
import { ref } from 'vue'

export const useRentalsStore = defineStore(
  'rentals',
  () => {
    const rentals = ref([])
    const watchHistory = ref([]) // [{ ...movie, watchedAt, userRating }]

    const isRented = (movieId) => {
      return rentals.value.some((r) => r.id === movieId)
    }

    const addRental = (movie) => {
      if (!isRented(movie.id)) {
        rentals.value.push({
          ...movie,
          rentedAt: new Date().toISOString(),
          expiresAt: new Date(Date.now() + 30 * 24 * 60 * 60 * 1000).toISOString(),
        })
      }
    }

    const removeRental = (movieId) => {
      rentals.value = rentals.value.filter((r) => r.id !== movieId)
    }

    const getRental = (movieId) => {
      return rentals.value.find((r) => r.id === movieId)
    }

    // Enregistrer un visionnage (appelé depuis PlayerView)
    const addToHistory = (movie) => {
      const existing = watchHistory.value.find((h) => h.id === movie.id)
      if (existing) {
        existing.watchedAt = new Date().toISOString()
        existing.watchCount = (existing.watchCount || 1) + 1
      } else {
        watchHistory.value.unshift({
          ...movie,
          watchedAt: new Date().toISOString(),
          watchCount: 1,
          userRating: null,
        })
      }
    }

    // Attribuer une note (1–5 étoiles) à un film visionné
    const rateMovie = (movieId, rating) => {
      const entry = watchHistory.value.find((h) => h.id === movieId)
      if (entry) {
        entry.userRating = rating
      }
      // Aussi mettre à jour dans rentals si présent
      const rental = rentals.value.find((r) => r.id === movieId)
      if (rental) {
        rental.userRating = rating
      }
    }

    const getUserRating = (movieId) => {
      return watchHistory.value.find((h) => h.id === movieId)?.userRating ?? null
    }

    const hasWatched = (movieId) => {
      return watchHistory.value.some((h) => h.id === movieId)
    }

    return {
      rentals,
      watchHistory,
      isRented,
      addRental,
      removeRental,
      getRental,
      addToHistory,
      rateMovie,
      getUserRating,
      hasWatched,
    }
  },
  {
    persist: true,
  },
)
