const API_BASE = 'http://localhost:8080/api/todos'

export async function fetchTodos() {
  const res = await fetch(API_BASE)
  if (!res.ok) throw new Error('Failed to fetch todos')
  return await res.json()
}

export async function createTodo(title) {
  const res = await fetch(API_BASE, {
    method: 'POST',
    headers: { 'Content-Type': 'application/json' },
    body: JSON.stringify({ title }),
  })
  if (!res.ok) throw new Error('Failed to create todo')
  return await res.json()
}

export async function updateTodo(todo) {
  const res = await fetch(`${API_BASE}/${todo.id}`, {
    method: 'PUT',
    headers: { 'Content-Type': 'application/json' },
    body: JSON.stringify({
      title: todo.title,
      completed: todo.completed,
    }),
  })
  if (!res.ok) throw new Error('Failed to update todo')
  return await res.json()
}

export async function deleteTodo(id) {
  const res = await fetch(`${API_BASE}/${id}`, {
    method: 'DELETE',
  })
  if (!res.ok) throw new Error('Failed to delete todo')
}
