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
        <v-tabs
          v-model="activeTab"
          grow
          class="gs-tabs"
        >
          <v-tab
            value="todo"
            class="gs-tab"
            selected-class="gs-tab--active"
          >
            Todo
          </v-tab>

          <v-tab
            value="note"
            class="gs-tab"
            selected-class="gs-tab--active"
          >
            職場安全活動表
          </v-tab>
        </v-tabs>

        <div class="mt-4">
          <TodoList v-if="activeTab === 'todo'" />
          <NoteCalendar v-else />
        </div>
      </v-container>
    </v-main>
  </v-app>
</template>
<style scoped>

.gs-tabs {
  border: 2px solid #2e7d32;
  border-radius: 9999px;
}
.gs-tab {
  text-transform: none;
}
.gs-tab--active {
  background-color: #1b5e20;
  color: #ffffff !important;
  border-radius: 9999px;
}
</style>





