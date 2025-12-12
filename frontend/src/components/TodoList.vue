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
        <v-row class="mb-4" align="stretch">
          <v-col cols="8" class="d-flex">
            <v-text-field
              v-model="newTitle"
              label="やることを入力"
              variant="outlined"
              density="comfortable"
              hide-details
              class="flex-grow-1"
              @keyup.enter="handleAdd"
            />
          </v-col>
          <v-col cols="4" class="d-flex">
            <v-btn
              color="primary"
              class="flex-grow-1 h-100"
              size="large"
              @click="handleAdd"
            >
              追加
            </v-btn>
          </v-col>
        </v-row>
        <div class="mt-2">
          <div v-if="loading">読み込み中...</div>
          <div v-if="error" class="text-red">
            {{ error }}
          </div>
        </div>
        <v-list class="mt-4" lines="one">
          <v-list-item
            v-for="todo in todos"
            :key="todo.id"
            class="todo-item"
            density="compact"
          >
            <template #prepend>
              <v-checkbox
                :model-value="todo.completed"
                @update:model-value="(val) => toggleCompleted(todo, val)"
                hide-details
              />
            </template>

            <v-list-item-title
              class="todo-title"
              :class="{ 'todo-title--completed': todo.completed }"
            >
              {{ todo.title }}
            </v-list-item-title>
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
<style scoped>
.todo-item {
  min-height: 40px;
  display: flex;
  align-items: center;
}
.todo-title {
  white-space: nowrap;
  overflow: hidden;
  text-overflow: ellipsis;
}
.todo-title--completed {
  text-decoration: line-through;
}
</style>

















