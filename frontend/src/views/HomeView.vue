<script setup>
import { ref } from 'vue'
import MovieBigCardComponent from '@/components/movie/MovieBigCardComponent.vue'
import MovieGridComponent from '@/components/movie/MovieGridComponent.vue'

// Données de test pour le carrousel principal
const heroMovies = ref([
  {
    id: 1,
    title: 'Avengers: Endgame',
    poster: '/src/assets/poster.png',
    description: 'Après les événements dévastateurs d\'Avengers: Infinity War, les Avengers se regroupent une dernière fois pour défaire les actions de Thanos et restaurer l\'équilibre de l\'univers.',
  },
  {
    id: 2,
    title: 'Avengers: Endgame',
    poster: '/src/assets/poster.png',
    description: 'Une aventure épique qui conclut l\'histoire de cette équipe héroïque.',
  },
  {
    id: 3,
    title: 'Avengers: Endgame',
    poster: '/src/assets/poster.png',
    description: 'Le combat final pour sauver l\'univers commence maintenant.',
  },
])
</script>

<template>
  <div class="min-h-screen bg-(--bg-main)">
    <!-- Hero Carrousel -->
    <MovieBigCardComponent :movies="heroMovies" />

    <!-- Grilles de films -->
    <div class="py-8 space-y-8">
      <MovieGridComponent title="Films populaires" />
      <MovieGridComponent title="Tendances actuelles" />
      <MovieGridComponent title="Nouveautés" />
    </div>
  </div>
</template>

<style scoped></style>
