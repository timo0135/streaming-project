<script setup>
import { ref, computed } from 'vue'
import { RouterLink, useRoute } from 'vue-router'
import { useRentalsStore } from '@/stores/rentals'

const route = useRoute()
const rentalsStore = useRentalsStore()
const rentalsCount = computed(() => rentalsStore.rentals.length)

const isMenuOpen = ref(false)
const searchQuery = ref('')

const profilIcon = new URL('../assets/profil.png', import.meta.url).href
const searchIcon = new URL('../assets/search.png', import.meta.url).href

const toggleMenu = () => {
  isMenuOpen.value = !isMenuOpen.value
}

const navLinks = [
  { name: 'Accueil', to: { name: 'home' } },
  { name: 'Films', to: { name: 'movies' } },
  { name: 'Mes locations', to: { name: 'rentals' } },
]
</script>

<template>
  <header class="bg-(--bg-main) border-b border-white/5 sticky top-0 z-50">
    <div class="px-4 py-2 sm:px-6 lg:px-8">
      <div class="text-(--text-primary) flex justify-between items-center h-16">

        <!-- Logo + nav -->
        <div class="flex items-center gap-10">
          <RouterLink :to="{ name: 'home' }" class="shrink-0">
            <h1 class="text-2xl font-bold tracking-tight text-white">
              🎬 <span class="text-(--accent)">Stream</span>it
            </h1>
          </RouterLink>

          <nav class="hidden md:flex items-center gap-1">
            <RouterLink
              v-for="link in navLinks"
              :key="link.name"
              :to="link.to"
              class="relative px-3 py-2 rounded-lg font-medium transition-colors hover:text-(--accent) hover:bg-white/5"
              :class="route.name === link.to.name ? 'text-(--accent)' : 'text-gray-300'"
            >
              {{ link.name }}
              <!-- Badge pour les locations -->
              <span
                v-if="link.name === 'Mes locations' && rentalsCount > 0"
                class="absolute -top-1 -right-1 bg-indigo-500 text-white text-[10px] font-bold rounded-full w-4 h-4 flex items-center justify-center"
              >
                {{ rentalsCount }}
              </span>
            </RouterLink>
          </nav>
        </div>

        <!-- Droite : search + profil -->
        <div class="flex items-center gap-3">
          <!-- Barre de recherche (icône seule) -->
          <div class="relative hidden md:flex items-center">
            <button class="p-2 rounded-full hover:bg-white/10 transition-colors">
              <img :src="searchIcon" alt="Rechercher" class="w-5 h-5 opacity-70" />
            </button>
          </div>

          <!-- Photo de profil -->
          <RouterLink :to="{ name: 'account' }" class="hidden md:block">
            <img
              :src="profilIcon"
              alt="Profil"
              class="w-9 h-9 rounded-full object-cover ring-2 ring-transparent hover:ring-(--accent) transition-all"
            />
          </RouterLink>

          <!-- Menu burger mobile -->
          <button
            @click="toggleMenu"
            class="md:hidden flex flex-col gap-1.5 p-2"
            aria-label="Toggle menu"
          >
            <span
              class="w-6 h-0.5 bg-(--text-primary) transition-all"
              :class="isMenuOpen ? 'rotate-45 translate-y-2' : ''"
            ></span>
            <span
              class="w-6 h-0.5 bg-(--text-primary) transition-all"
              :class="isMenuOpen ? 'opacity-0' : ''"
            ></span>
            <span
              class="w-6 h-0.5 bg-(--text-primary) transition-all"
              :class="isMenuOpen ? '-rotate-45 -translate-y-2' : ''"
            ></span>
          </button>
        </div>
      </div>

      <!-- Navigation Mobile -->
      <nav v-show="isMenuOpen" class="md:hidden pb-4 space-y-1 text-(--text-primary) border-t border-white/5 pt-3 mt-1">
        <RouterLink
          v-for="link in navLinks"
          :key="link.name"
          :to="link.to"
          class="flex items-center justify-between py-2 px-3 rounded-lg hover:text-(--accent) hover:bg-white/5 font-medium transition-colors"
          @click="isMenuOpen = false"
        >
          {{ link.name }}
          <span
            v-if="link.name === 'Mes locations' && rentalsCount > 0"
            class="bg-indigo-500 text-white text-xs font-bold rounded-full px-2 py-0.5"
          >
            {{ rentalsCount }}
          </span>
        </RouterLink>

        <RouterLink
          :to="{ name: 'account' }"
          class="flex items-center gap-3 py-2 px-3 rounded-lg hover:text-(--accent) hover:bg-white/5 font-medium transition-colors"
          @click="isMenuOpen = false"
        >
          <img :src="profilIcon" alt="Profil" class="w-7 h-7 rounded-full object-cover" />
          Mon compte
        </RouterLink>
      </nav>
    </div>
  </header>
</template>
