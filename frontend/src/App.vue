<script setup>
import { ref } from 'vue'
import TodoList from './components/TodoList.vue'
import NoteCalendar from './components/NoteCalendar.vue'

const activeTab = ref('todo')
</script>

<template>
  <v-app>
    <v-main>
      <v-container class="mt-4" max-width="900">
        <v-tabs v-model="activeTab" grow>
          <v-tab value="todo">Todo</v-tab>
          <v-tab value="note">職場安全活動表</v-tab>
        </v-tabs>
        <div class="mt-4">
          <TodoList v-if="activeTab === 'todo'" />
          <NoteCalendar v-else />
        </div>
      </v-container>
    </v-main>
  </v-app>
</template>






