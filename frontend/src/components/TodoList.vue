<script setup>
import { ref, onMounted } from 'vue'
import {
  fetchTodos,
  createTodo,
  updateTodo,
  deleteTodo,
} from '../api/todos.js'
const todos = ref([])
const newTitle = ref('')
const loading = ref(false)
const error = ref('')
// 一覧読み込み
async function loadTodos() {
  loading.value = true
  error.value = ''
  try {
    todos.value = await fetchTodos()
  } catch (e) {
    console.error(e)
    error.value = 'Todoの取得に失敗しました'
  } finally {
    loading.value = false
  }
}
// 追加
async function handleAdd() {
  if (!newTitle.value.trim()) return
  try {
    const created = await createTodo(newTitle.value.trim())
    todos.value = [...todos.value, created]
    newTitle.value = ''
  } catch (e) {
    console.error(e)
    error.value = 'Todoの追加に失敗しました'
  }
}
// 完了チェック切り替え
async function toggleCompleted(todo) {
  try {
    const updated = await updateTodo({
      ...todo,
      completed: !todo.completed,
    })
    todos.value = todos.value.map((t) =>
      t.id === updated.id ? updated : t,
    )
  } catch (e) {
    console.error(e)
    error.value = '更新に失敗しました'
  }
}
// 削除
async function handleDelete(todo) {
  try {
    await deleteTodo(todo.id)
    todos.value = todos.value.filter((t) => t.id !== todo.id)
  } catch (e) {
    console.error(e)
    error.value = '削除に失敗しました'
  }
}
onMounted(loadTodos)
</script>
<template>
  <v-container class="mt-8" max-width="600">
    <v-card>
      <v-card-title class="text-h5">GreenSafe Todo</v-card-title>
      <v-card-text>
        <!-- 入力＋追加ボタン -->
        <v-row class="mb-4" align="center">
          <v-col cols="8">
            <v-text-field
              v-model="newTitle"
              label="やることを入力"
              variant="outlined"
              density="comfortable"
              @keyup.enter="handleAdd"
            />
          </v-col>
          <v-col cols="4">
            <v-btn
              color="primary"
              block
              @click="handleAdd"
            >
              追加
            </v-btn>
          </v-col>
        </v-row>
        <!-- ローディング・エラー表示 -->
        <div class="mt-2">
          <div v-if="loading">読み込み中...</div>
          <div v-if="error" class="text-red">
            {{ error }}
          </div>
        </div>
        <!-- ★ 一覧：チェックボックス + 削除ボタン付き -->
        <v-list class="mt-4">
          <v-list-item
            v-for="todo in todos"
            :key="todo.id"
          >
            <!-- 左側：チェックボックス -->
            <template #prepend>
              <v-checkbox
                :model-value="todo.completed"
                @update:model-value="() => toggleCompleted(todo)"
              />
            </template>
            <!-- 中央：タイトル -->
            <v-list-item-title>
              <span :style="{ textDecoration: todo.completed ? 'line-through' : 'none' }">
                {{ todo.title }}
              </span>
            </v-list-item-title>
            <!-- 右側：削除ボタン -->
            <template #append>
              <v-btn
                color="red"
                variant="text"
                @click="() => handleDelete(todo)"
              >
                削除
              </v-btn>
            </template>
          </v-list-item>
        </v-list>
      </v-card-text>
    </v-card>
  </v-container>
</template>










