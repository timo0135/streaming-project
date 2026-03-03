<script setup>
import { ref, computed, reactive } from 'vue'
import { useRoute, useRouter } from 'vue-router'
import { useRentalsStore } from '@/stores/rentals'

const route = useRoute()
const router = useRouter()
const rentalsStore = useRentalsStore()

// ── Données films (à centraliser via API plus tard) ──────────────────────────
const allMovies = [
  { id: 1, title: 'Avengers: Endgame', poster: '/src/assets/poster.png', duration: '3h 2min', year: 2019, genre: 'Action / Science-Fiction', price: 4.99 },
  { id: 2, title: 'Avengers: Endgame', poster: '/src/assets/poster.png', duration: '3h 2min', year: 2019, genre: 'Action / Science-Fiction', price: 4.99 },
  { id: 3, title: 'Avengers: Endgame', poster: '/src/assets/poster.png', duration: '3h 2min', year: 2019, genre: 'Action / Science-Fiction', price: 4.99 },
  { id: 4, title: 'Avengers: Endgame', poster: '/src/assets/poster.png', duration: '3h 2min', year: 2019, genre: 'Action / Science-Fiction', price: 4.99 },
  { id: 5, title: 'Avengers: Endgame', poster: '/src/assets/poster.png', duration: '3h 2min', year: 2019, genre: 'Action / Science-Fiction', price: 4.99 },
  { id: 6, title: 'Avengers: Endgame', poster: '/src/assets/poster.png', duration: '3h 2min', year: 2019, genre: 'Action / Science-Fiction', price: 4.99 },
  { id: 7, title: 'Avengers: Endgame', poster: '/src/assets/poster.png', duration: '3h 2min', year: 2019, genre: 'Action / Science-Fiction', price: 4.99 },
  { id: 8, title: 'Avengers: Endgame', poster: '/src/assets/poster.png', duration: '3h 2min', year: 2019, genre: 'Action / Science-Fiction', price: 4.99 },
]

const movie = computed(
  () => allMovies.find((m) => m.id === Number(route.query.movieId)) || allMovies[0],
)

// ── Formulaire ───────────────────────────────────────────────────────────────
const form = reactive({
  cardHolder: '',
  cardNumber: '',
  expiry: '',
  cvv: '',
})

const touched = reactive({
  cardHolder: false,
  cardNumber: false,
  expiry: false,
  cvv: false,
})

// ── Formatage automatique ────────────────────────────────────────────────────
const formatCardNumber = (e) => {
  let v = e.target.value.replace(/\D/g, '').slice(0, 16)
  form.cardNumber = v.replace(/(.{4})/g, '$1 ').trim()
}

const formatExpiry = (e) => {
  let v = e.target.value.replace(/\D/g, '').slice(0, 4)
  if (v.length >= 3) v = v.slice(0, 2) + '/' + v.slice(2)
  form.expiry = v
}

const formatCvv = (e) => {
  form.cvv = e.target.value.replace(/\D/g, '').slice(0, 4)
}

// ── Détection réseau carte ───────────────────────────────────────────────────
const cardNetwork = computed(() => {
  const n = form.cardNumber.replace(/\s/g, '')
  if (/^4/.test(n)) return 'visa'
  if (/^5[1-5]|^2[2-7]/.test(n)) return 'mastercard'
  if (/^3[47]/.test(n)) return 'amex'
  return null
})

// ── Validation ───────────────────────────────────────────────────────────────
const errors = computed(() => {
  const e = {}
  const digits = form.cardNumber.replace(/\s/g, '')

  if (!form.cardHolder.trim()) e.cardHolder = 'Le nom est requis'
  else if (form.cardHolder.trim().length < 3) e.cardHolder = 'Nom trop court'

  if (!digits) e.cardNumber = 'Le numéro de carte est requis'
  else if (digits.length < 16) e.cardNumber = 'Numéro incomplet (16 chiffres)'

  if (!form.expiry) e.expiry = "La date d'expiration est requise"
  else {
    const [mm, yy] = form.expiry.split('/')
    const month = parseInt(mm)
    const year = 2000 + parseInt(yy || '0')
    const now = new Date()
    if (month < 1 || month > 12) e.expiry = 'Mois invalide'
    else if (year < now.getFullYear() || (year === now.getFullYear() && month < now.getMonth() + 1))
      e.expiry = 'Carte expirée'
  }

  if (!form.cvv) e.cvv = 'Le CVV est requis'
  else if (form.cvv.length < 3) e.cvv = 'CVV incomplet'

  return e
})

const isFormValid = computed(() => Object.keys(errors.value).length === 0)

// ── États du paiement ────────────────────────────────────────────────────────
const step = ref('form') // 'form' | 'processing' | 'success' | 'error'
const paymentError = ref('')

const submitPayment = async () => {
  // Marquer tous les champs comme touchés pour afficher les erreurs
  Object.keys(touched).forEach((k) => (touched[k] = true))
  if (!isFormValid.value) return

  step.value = 'processing'

  // Simuler un appel réseau de 2.5 secondes
  await new Promise((r) => setTimeout(r, 2500))

  // Simuler 90% de succès
  if (Math.random() > 0.1) {
    rentalsStore.addRental(movie.value)
    step.value = 'success'
  } else {
    paymentError.value = 'Paiement refusé par votre banque. Veuillez vérifier vos informations.'
    step.value = 'error'
  }
}

const retryPayment = () => {
  step.value = 'form'
  paymentError.value = ''
}

// Masquer partiellement le numéro pour l'écran succès
const maskedCard = computed(() => {
  const digits = form.cardNumber.replace(/\s/g, '')
  return '**** **** **** ' + digits.slice(-4)
})
</script>

<template>
  <div class="min-h-screen bg-(--bg-main) flex flex-col items-center justify-center px-4 py-12">

    <!-- Bouton retour -->
    <div class="w-full max-w-5xl mb-6">
      <button
        @click="router.back()"
        class="text-gray-400 hover:text-white flex items-center gap-2 transition-colors text-sm"
      >
        ← Retour
      </button>
    </div>

    <div class="w-full max-w-5xl grid grid-cols-1 lg:grid-cols-2 gap-8 items-start">

      <!-- ── Colonne gauche : résumé de commande ── -->
      <div class="bg-gray-900/60 border border-gray-800 rounded-2xl p-6 order-2 lg:order-1">
        <h2 class="text-white text-lg font-bold mb-5">Résumé de la commande</h2>

        <!-- Détail film -->
        <div class="flex gap-4 mb-6">
          <img
            :src="movie.poster || '/src/assets/poster.png'"
            :alt="movie.title"
            class="w-20 h-28 object-cover rounded-lg shrink-0 shadow-lg"
          />
          <div>
            <p class="text-white font-semibold text-lg">{{ movie.title }}</p>
            <p class="text-gray-400 text-sm mt-1">{{ movie.genre }}</p>
            <p class="text-gray-400 text-sm">{{ movie.duration }} · {{ movie.year }}</p>
            <div class="mt-3 inline-flex items-center gap-1.5 bg-indigo-500/15 border border-indigo-500/40 rounded-lg px-3 py-1">
              <span class="text-indigo-400 text-xs">📅 Valable 30 jours</span>
            </div>
          </div>
        </div>

        <!-- Ligne de prix -->
        <div class="border-t border-gray-800 pt-4 space-y-3">
          <div class="flex justify-between text-sm text-gray-400">
            <span>Location</span>
            <span>{{ movie.price }} €</span>
          </div>
          <div class="flex justify-between text-sm text-gray-400">
            <span>Frais de service</span>
            <span>0,00 €</span>
          </div>
          <div class="flex justify-between font-bold text-white text-lg border-t border-gray-700 pt-3 mt-2">
            <span>Total</span>
            <span class="text-indigo-400">{{ movie.price }} €</span>
          </div>
        </div>

        <!-- Sécurité -->
        <div class="mt-6 flex flex-col gap-2">
          <div class="flex items-center gap-2 text-gray-500 text-xs">
            <span>🔒</span>
            <span>Paiement sécurisé SSL 256 bits</span>
          </div>
          <div class="flex items-center gap-2 text-gray-500 text-xs">
            <span>🛡️</span>
            <span>Vos données ne sont pas stockées</span>
          </div>
          <div class="flex items-center gap-2 text-gray-500 text-xs">
            <span>↩️</span>
            <span>Remboursement sous 14 jours</span>
          </div>
        </div>
      </div>

      <!-- ── Colonne droite : formulaire / états ── -->
      <div class="order-1 lg:order-2">

        <!-- ═══ ÉTAT : FORMULAIRE ═══ -->
        <div v-if="step === 'form'" class="bg-gray-900/60 border border-gray-800 rounded-2xl p-6">
          <h2 class="text-white text-lg font-bold mb-1">Paiement par carte</h2>
          <p class="text-gray-500 text-sm mb-6">Saisissez vos coordonnées bancaires</p>

          <!-- Logos réseaux -->
          <div class="flex items-center gap-3 mb-6">
            <!-- Visa -->
            <div
              class="px-3 py-1.5 rounded-lg border text-xs font-bold tracking-wider transition-all"
              :class="cardNetwork === 'visa' ? 'bg-blue-600 border-blue-500 text-white' : 'bg-gray-800 border-gray-700 text-gray-500'"
            >
              VISA
            </div>
            <!-- Mastercard -->
            <div
              class="px-3 py-1.5 rounded-lg border text-xs font-bold tracking-wider transition-all"
              :class="cardNetwork === 'mastercard' ? 'bg-red-700 border-red-600 text-white' : 'bg-gray-800 border-gray-700 text-gray-500'"
            >
              MC
            </div>
            <!-- Amex -->
            <div
              class="px-3 py-1.5 rounded-lg border text-xs font-bold tracking-wider transition-all"
              :class="cardNetwork === 'amex' ? 'bg-cyan-700 border-cyan-600 text-white' : 'bg-gray-800 border-gray-700 text-gray-500'"
            >
              AMEX
            </div>
          </div>

          <form @submit.prevent="submitPayment" class="space-y-5">

            <!-- Nom sur la carte -->
            <div>
              <label class="block text-gray-300 text-sm font-medium mb-1.5">
                Nom sur la carte
              </label>
              <input
                v-model="form.cardHolder"
                @blur="touched.cardHolder = true"
                type="text"
                placeholder="JEAN DUPONT"
                autocomplete="cc-name"
                class="w-full bg-gray-800/80 border rounded-xl px-4 py-3 text-white placeholder-gray-600 focus:outline-none focus:ring-2 transition-all uppercase tracking-wider"
                :class="touched.cardHolder && errors.cardHolder
                  ? 'border-red-500 focus:ring-red-500/50'
                  : 'border-gray-700 focus:ring-indigo-500/50 focus:border-indigo-500'"
              />
              <p v-if="touched.cardHolder && errors.cardHolder" class="mt-1.5 text-red-400 text-xs flex items-center gap-1">
                ⚠ {{ errors.cardHolder }}
              </p>
            </div>

            <!-- Numéro de carte -->
            <div>
              <label class="block text-gray-300 text-sm font-medium mb-1.5">
                Numéro de carte
              </label>
              <div class="relative">
                <input
                  :value="form.cardNumber"
                  @input="formatCardNumber"
                  @blur="touched.cardNumber = true"
                  type="text"
                  placeholder="1234 5678 9012 3456"
                  autocomplete="cc-number"
                  inputmode="numeric"
                  maxlength="19"
                  class="w-full bg-gray-800/80 border rounded-xl px-4 py-3 text-white placeholder-gray-600 focus:outline-none focus:ring-2 transition-all tracking-widest pr-12"
                  :class="touched.cardNumber && errors.cardNumber
                    ? 'border-red-500 focus:ring-red-500/50'
                    : 'border-gray-700 focus:ring-indigo-500/50 focus:border-indigo-500'"
                />
                <!-- Icône réseau détecté -->
                <div class="absolute right-3 top-1/2 -translate-y-1/2 text-lg">
                  <span v-if="cardNetwork === 'visa'" title="Visa">💳</span>
                  <span v-else-if="cardNetwork === 'mastercard'" title="Mastercard">💳</span>
                  <span v-else-if="cardNetwork === 'amex'" title="Amex">💳</span>
                  <span v-else class="text-gray-600">💳</span>
                </div>
              </div>
              <p v-if="touched.cardNumber && errors.cardNumber" class="mt-1.5 text-red-400 text-xs flex items-center gap-1">
                ⚠ {{ errors.cardNumber }}
              </p>
            </div>

            <!-- Expiration + CVV -->
            <div class="grid grid-cols-2 gap-4">
              <!-- Date d'expiration -->
              <div>
                <label class="block text-gray-300 text-sm font-medium mb-1.5">
                  Date d'expiration
                </label>
                <input
                  :value="form.expiry"
                  @input="formatExpiry"
                  @blur="touched.expiry = true"
                  type="text"
                  placeholder="MM/AA"
                  autocomplete="cc-exp"
                  inputmode="numeric"
                  maxlength="5"
                  class="w-full bg-gray-800/80 border rounded-xl px-4 py-3 text-white placeholder-gray-600 focus:outline-none focus:ring-2 transition-all tracking-widest"
                  :class="touched.expiry && errors.expiry
                    ? 'border-red-500 focus:ring-red-500/50'
                    : 'border-gray-700 focus:ring-indigo-500/50 focus:border-indigo-500'"
                />
                <p v-if="touched.expiry && errors.expiry" class="mt-1.5 text-red-400 text-xs flex items-center gap-1">
                  ⚠ {{ errors.expiry }}
                </p>
              </div>

              <!-- CVV -->
              <div>
                <label class="block text-gray-300 text-sm font-medium mb-1.5">
                  CVV
                  <span
                    class="ml-1 text-gray-600 cursor-help"
                    title="3 chiffres au dos de votre carte (4 pour Amex)"
                  >ⓘ</span>
                </label>
                <input
                  :value="form.cvv"
                  @input="formatCvv"
                  @blur="touched.cvv = true"
                  type="password"
                  placeholder="•••"
                  autocomplete="cc-csc"
                  inputmode="numeric"
                  maxlength="4"
                  class="w-full bg-gray-800/80 border rounded-xl px-4 py-3 text-white placeholder-gray-600 focus:outline-none focus:ring-2 transition-all tracking-widest"
                  :class="touched.cvv && errors.cvv
                    ? 'border-red-500 focus:ring-red-500/50'
                    : 'border-gray-700 focus:ring-indigo-500/50 focus:border-indigo-500'"
                />
                <p v-if="touched.cvv && errors.cvv" class="mt-1.5 text-red-400 text-xs flex items-center gap-1">
                  ⚠ {{ errors.cvv }}
                </p>
              </div>
            </div>

            <!-- Bouton payer -->
            <button
              type="submit"
              class="w-full bg-indigo-600 hover:bg-indigo-500 text-white py-4 rounded-xl font-bold text-lg transition-all mt-2 flex items-center justify-center gap-2 shadow-lg shadow-indigo-900/40"
              :class="!isFormValid && Object.keys(touched).some(k => touched[k]) ? 'opacity-60' : ''"
            >
              🔒 Payer {{ movie.price }} €
            </button>

            <p class="text-center text-gray-600 text-xs">
              En confirmant, vous acceptez nos conditions de location.
            </p>
          </form>
        </div>

        <!-- ═══ ÉTAT : TRAITEMENT ═══ -->
        <div
          v-else-if="step === 'processing'"
          class="bg-gray-900/60 border border-gray-800 rounded-2xl p-12 flex flex-col items-center justify-center text-center min-h-80"
        >
          <!-- Spinner animé -->
          <div class="relative w-20 h-20 mb-8">
            <div class="absolute inset-0 rounded-full border-4 border-gray-700"></div>
            <div class="absolute inset-0 rounded-full border-4 border-indigo-500 border-t-transparent animate-spin"></div>
            <div class="absolute inset-0 flex items-center justify-center text-2xl">💳</div>
          </div>
          <h3 class="text-white text-xl font-bold mb-2">Traitement en cours…</h3>
          <p class="text-gray-400 text-sm">Veuillez ne pas fermer cette page</p>

          <!-- Étapes animées -->
          <div class="mt-8 space-y-2 text-left w-full max-w-xs">
            <div class="flex items-center gap-3 text-sm">
              <span class="text-green-400">✓</span>
              <span class="text-gray-300">Vérification des coordonnées</span>
            </div>
            <div class="flex items-center gap-3 text-sm">
              <div class="w-3 h-3 rounded-full bg-indigo-500 animate-pulse"></div>
              <span class="text-gray-300">Autorisation bancaire…</span>
            </div>
            <div class="flex items-center gap-3 text-sm opacity-40">
              <div class="w-3 h-3 rounded-full bg-gray-600"></div>
              <span class="text-gray-500">Activation de la location</span>
            </div>
          </div>
        </div>

        <!-- ═══ ÉTAT : SUCCÈS ═══ -->
        <div
          v-else-if="step === 'success'"
          class="bg-gray-900/60 border border-gray-800 rounded-2xl p-10 flex flex-col items-center text-center"
        >
          <!-- Cercle succès animé -->
          <div class="w-24 h-24 rounded-full bg-green-500/15 border-2 border-green-500 flex items-center justify-center text-5xl mb-6 animate-bounce-once">
            ✅
          </div>

          <h2 class="text-white text-2xl font-bold mb-2">Paiement accepté !</h2>
          <p class="text-gray-400 mb-6">Votre location a été activée avec succès.</p>

          <!-- Récap paiement -->
          <div class="w-full bg-gray-800/60 rounded-xl p-4 mb-6 text-left space-y-2">
            <div class="flex justify-between text-sm">
              <span class="text-gray-500">Film</span>
              <span class="text-white font-medium">{{ movie.title }}</span>
            </div>
            <div class="flex justify-between text-sm">
              <span class="text-gray-500">Montant débité</span>
              <span class="text-white font-medium">{{ movie.price }} €</span>
            </div>
            <div class="flex justify-between text-sm">
              <span class="text-gray-500">Carte</span>
              <span class="text-white font-medium font-mono">{{ maskedCard }}</span>
            </div>
            <div class="flex justify-between text-sm">
              <span class="text-gray-500">Expire le</span>
              <span class="text-white font-medium">
                {{ new Date(Date.now() + 30 * 24 * 60 * 60 * 1000).toLocaleDateString('fr-FR', { day: '2-digit', month: 'long', year: 'numeric' }) }}
              </span>
            </div>
          </div>

          <div class="flex flex-col sm:flex-row gap-3 w-full">
            <button
              @click="router.push({ name: 'player', params: { movieId: movie.id } })"
              class="flex-1 bg-white text-black py-3 rounded-xl font-bold hover:bg-gray-100 transition-colors flex items-center justify-center gap-2"
            >
              ▶ Regarder maintenant
            </button>
            <button
              @click="router.push({ name: 'rentals' })"
              class="flex-1 border border-gray-700 text-gray-300 hover:text-white hover:border-indigo-500 py-3 rounded-xl font-semibold transition-colors"
            >
              Mes locations
            </button>
          </div>
        </div>

        <!-- ═══ ÉTAT : ERREUR ═══ -->
        <div
          v-else-if="step === 'error'"
          class="bg-gray-900/60 border border-red-900/50 rounded-2xl p-10 flex flex-col items-center text-center"
        >
          <div class="w-20 h-20 rounded-full bg-red-500/15 border-2 border-red-500 flex items-center justify-center text-4xl mb-6">
            ❌
          </div>
          <h2 class="text-white text-xl font-bold mb-2">Paiement refusé</h2>
          <p class="text-red-400 text-sm mb-8">{{ paymentError }}</p>

          <div class="flex flex-col sm:flex-row gap-3 w-full">
            <button
              @click="retryPayment"
              class="flex-1 bg-indigo-600 hover:bg-indigo-500 text-white py-3 rounded-xl font-bold transition-colors"
            >
              Réessayer
            </button>
            <button
              @click="router.back()"
              class="flex-1 border border-gray-700 text-gray-300 hover:text-white py-3 rounded-xl font-semibold transition-colors"
            >
              Retour
            </button>
          </div>
        </div>

      </div>
    </div>
  </div>
</template>

<style scoped>
@keyframes bounce-once {
  0%, 100% { transform: translateY(0); }
  40% { transform: translateY(-16px); }
  60% { transform: translateY(-8px); }
}
.animate-bounce-once {
  animation: bounce-once 0.7s ease-out;
}
</style>


