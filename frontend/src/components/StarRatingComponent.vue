<script setup>
import { ref, computed } from 'vue'

const props = defineProps({
  modelValue: {
    type: Number,
    default: null,
  },
  readonly: {
    type: Boolean,
    default: false,
  },
  size: {
    type: String,
    default: 'md', // 'sm' | 'md' | 'lg'
  },
})

const emit = defineEmits(['update:modelValue'])

const hovered = ref(null)

const activeRating = computed(() => hovered.value ?? props.modelValue)

const sizeClass = computed(() => {
  return { sm: 'text-lg', md: 'text-2xl', lg: 'text-4xl' }[props.size] || 'text-2xl'
})

const setRating = (star) => {
  if (!props.readonly) emit('update:modelValue', star)
}

const labels = ['Mauvais', 'Passable', 'Bien', 'Très bien', 'Excellent']
</script>

<template>
  <div class="flex items-center gap-1">
    <button
      v-for="star in 5"
      :key="star"
      @click="setRating(star)"
      @mouseenter="!readonly && (hovered = star)"
      @mouseleave="!readonly && (hovered = null)"
      :disabled="readonly"
      :class="[
        sizeClass,
        'transition-transform duration-100 leading-none',
        !readonly ? 'hover:scale-125 cursor-pointer' : 'cursor-default',
        star <= (activeRating ?? 0) ? 'text-yellow-400' : 'text-gray-600',
      ]"
      :title="!readonly ? labels[star - 1] : ''"
    >
      ★
    </button>
    <span v-if="!readonly && activeRating" class="ml-2 text-sm text-gray-400">
      {{ labels[(activeRating ?? 1) - 1] }}
    </span>
  </div>
</template>

