<script setup>
import { ref } from 'vue'

const props = defineProps({
  movies: {
    type: Array,
    default: () => [],
  },
})

const currentIndex = ref(0)

const nextSlide = () => {
  if (currentIndex.value < props.movies.length - 1) {
    currentIndex.value++
  } else {
    currentIndex.value = 0
  }
}

const prevSlide = () => {
  if (currentIndex.value > 0) {
    currentIndex.value--
  } else {
    currentIndex.value = props.movies.length - 1
  }
}

const goToSlide = (index) => {
  currentIndex.value = index
}
</script>

<template>
  <div class="relative w-full h-[500px] md:h-[600px] lg:h-[700px] overflow-hidden group">
    <!-- Carrousel principal -->
    <div class="relative h-full">
      <!-- Slides -->
      <div
        v-for="(movie, index) in movies"
        :key="index"
        class="absolute inset-0 transition-opacity duration-700 ease-in-out"
        :class="index === currentIndex ? 'opacity-100' : 'opacity-0'"
      >
        <!-- Image du film -->
        <img
          :src="movie.poster || '/src/assets/poster.png'"
          :alt="movie.title"
          class="w-full h-full object-cover"
        />

        <!-- Gradient overlay -->
        <div
          class="absolute inset-0 bg-gradient-to-t from-black via-black/50 to-transparent"
        ></div>

        <!-- Contenu du film -->
        <div class="absolute bottom-0 left-0 right-0 p-8 md:p-12 lg:p-16">
          <div class="max-w-2xl">
            <h2 class="text-white text-4xl md:text-5xl lg:text-6xl font-bold mb-4">
              {{ movie.title }}
            </h2>
            <p v-if="movie.description" class="text-white text-lg md:text-xl mb-6 line-clamp-3">
              {{ movie.description }}
            </p>
            <div class="flex gap-4">
              <button
                class="bg-white text-black px-8 py-3 rounded-md font-semibold hover:bg-gray-200 transition-colors flex items-center gap-2"
              >
                ▶ Lecture
              </button>
              <button
                class="bg-gray-500/70 text-white px-8 py-3 rounded-md font-semibold hover:bg-gray-500 transition-colors"
              >
                ℹ Plus d'infos
              </button>
            </div>
          </div>
        </div>
      </div>
    </div>

    <!-- Boutons de navigation -->
    <button
      @click="prevSlide"
      class="absolute left-4 top-1/2 -translate-y-1/2 bg-black/50 text-white p-3 rounded-full hover:bg-black/80 transition-all opacity-0 group-hover:opacity-100"
    >
      ❮
    </button>
    <button
      @click="nextSlide"
      class="absolute right-4 top-1/2 -translate-y-1/2 bg-black/50 text-white p-3 rounded-full hover:bg-black/80 transition-all opacity-0 group-hover:opacity-100"
    >
      ❯
    </button>

    <!-- Indicateurs -->
    <div class="absolute bottom-4 right-8 flex gap-2">
      <button
        v-for="(movie, index) in movies"
        :key="index"
        @click="goToSlide(index)"
        class="h-1 rounded-full transition-all"
        :class="
          index === currentIndex ? 'w-8 bg-white' : 'w-2 bg-white/50 hover:bg-white/80'
        "
      ></button>
    </div>
  </div>
</template>

<style scoped></style>
