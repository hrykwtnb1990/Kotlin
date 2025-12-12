<script setup>
import { ref, computed, onMounted, watch } from "vue"
import {
  fetchNotesByMonth,
  createNote,
  updateNote,
} from '../api/note.js'
const year = ref(new Date().getFullYear())
const selectedMonth = ref(new Date().getMonth() + 1)
const selectedDay = ref(null)
const notes = ref([])
const loading = ref(false)
const error = ref('')
// ★ 編集用の状態
const editTitle = ref('')
const editAuthor = ref('')
const editProcess = ref('')
const editDefect = ref('')
const editCountermeasure = ref('')
const daysInMonth = computed(() => {
  return new Date(year.value, selectedMonth.value, 0).getDate()
})
function formatDate(y, m, d) {
  const mm = String(m).padStart(2, '0')
  const dd = String(d).padStart(2, '0')
  return `${y}-${mm}-${dd}`
}
const isWeekend = (day) => {
  const d = new Date(year.value, selectedMonth.value - 1, day)
  const w = d.getDay()
  return w === 0 || w === 6
}
const notesMap = computed(() => {
  const map = {}
  for (const n of notes.value) {
    map[n.date] = n
  }
  return map
})
async function loadNotes() {
  loading.value = true
  error.value = ''
  try {
    notes.value = await fetchNotesByMonth(year.value, selectedMonth.value)
  } catch (e) {
    console.error(e)
    error.value = 'ノートの取得に失敗しました'
  } finally {
    loading.value = false
  }
}
function clearForm() {
  editTitle.value = ''
  editAuthor.value = ''
  editProcess.value = ''
  editDefect.value = ''
  editCountermeasure.value = ''
}
function selectDay(day) {
  selectedDay.value = day
  const key = formatDate(year.value, selectedMonth.value, day)
  const existing = notesMap.value[key]
  if (existing) {
    editTitle.value = existing.title ?? ''
    editAuthor.value = existing.author ?? ''
    editProcess.value = existing.process ?? ''
    editDefect.value = existing.defectContent ?? ''
    editCountermeasure.value = existing.countermeasureContent ?? ''
  } else {
    clearForm()
  }
}
async function handleSave() {
  if (!selectedDay.value) return
  const dateStr = formatDate(year.value, selectedMonth.value, selectedDay.value)
  const existing = notesMap.value[dateStr]
  try {
    let saved
    if (existing) {
      saved = await updateNote({
        ...existing,
        date: dateStr,
        title: editTitle.value,
        author: editAuthor.value,
        process: editProcess.value,
        defectContent: editDefect.value,
        countermeasureContent: editCountermeasure.value,
      })
      notes.value = notes.value.map((n) => (n.id === saved.id ? saved : n))
    } else {
      saved = await createNote({
        date: dateStr,
        title: editTitle.value || `${selectedMonth.value}月${selectedDay.value}日`,
        author: editAuthor.value,
        process: editProcess.value,
        defectContent: editDefect.value,
        countermeasureContent: editCountermeasure.value,
      })
      notes.value = [...notes.value, saved]
    }
  } catch (e) {
    console.error(e)
    error.value = '保存に失敗しました'
  }
}
onMounted(loadNotes)
watch(selectedMonth, () => {
  selectedDay.value = null
  clearForm()
  loadNotes()
})
</script>
<template>
  <v-container class="mt-8">
    <v-card>
      <v-card-title class="text-h5">
        Note カレンダー（{{ year }}年）
      </v-card-title>
      <v-card-text>
        <!-- 月タブ -->
        <v-tabs v-model="selectedMonth" grow>
          <v-tab
            v-for="m in 12"
            :key="m"
            :value="m"
          >
            {{ m }}月
          </v-tab>
        </v-tabs>
        <!-- 状態表示 -->
        <div class="mt-2">
          <div v-if="loading">読み込み中...</div>
          <div v-if="error" class="text-red">{{ error }}</div>
        </div>
        <v-row class="mt-4" align="start">
          <!-- 左：日ボタン -->
          <v-col cols="7">
            <v-row dense>
              <v-col
                v-for="day in daysInMonth"
                :key="day"
                cols="2"
                class="mb-2"
              >
                <v-btn
                  :variant="selectedDay === day ? 'flat' : 'outlined'"
                  :color="notesMap[formatDate(year, selectedMonth, day)] ? 'primary' : 'default'"
                  block
                  @click="selectDay(day)"
                >
                  <span :class = "{ weekend: isWeekend(day) }">
                  {{ day }}
                  </span>
                </v-btn>
              </v-col>
            </v-row>
          </v-col>
          <!-- 右：ノート編集 -->
          <v-col cols="5">
            <div v-if="selectedDay">
              <h3 class="mb-2">
                {{ selectedMonth }}月{{ selectedDay }}日のノート
              </h3>
              <!-- タイトル -->
              <v-text-field
                v-model="editTitle"
                label="タイトル"
                variant="outlined"
                density="comfortable"
                class="mb-2"
              />
              <!-- 記入者 -->
              <v-text-field
                v-model="editAuthor"
                label="記入者"
                variant="outlined"
                density="comfortable"
                class="mb-2"
              />
              <!-- 工程 -->
              <v-text-field
                v-model="editProcess"
                label="工程"
                variant="outlined"
                density="comfortable"
                class="mb-4"
              />
              <!-- 不具合内容 -->
              <v-textarea
                v-model="editDefect"
                label="不具合内容"
                variant="outlined"
                rows="4"
                class="mb-3"
              />
              <!-- 対策内容 -->
              <v-textarea
                v-model="editCountermeasure"
                label="対策内容"
                variant="outlined"
                rows="4"
              />
              <v-btn
                class="mt-3"
                color="primary"
                @click="handleSave"
              >
                保存
              </v-btn>
            </div>
            <div v-else>
              日付をクリックするとノートを編集できます。
            </div>
          </v-col>
        </v-row>
      </v-card-text>
    </v-card>
  </v-container>
</template>
<style scoped>
.weekend {
  color:red;
}
</style>










