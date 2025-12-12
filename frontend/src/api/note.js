const API_BASE = 'http://localhost:8080/api/notes'

export async function fetchNotesByMonth(year, month) {
  const params = new URLSearchParams({ year: String(year), month: String(month) })
  const res = await fetch(`${API_BASE}?${params.toString()}`)
  if (!res.ok) throw new Error('Failed to fetch notes')
  return await res.json()
}

export async function createNote({ date, title, author, process, defectContent, countermeasureContent }) {
  const res = await fetch(API_BASE, {
    method: 'POST',
    headers: { 'Content-Type': 'application/json' },
    body: JSON.stringify({ date, title, author, process, defectContent, countermeasureContent }),
  })
  if (!res.ok) throw new Error('Failed to create note')
  return await res.json()
}

export async function updateNote(note) {
  const res = await fetch(`${API_BASE}/${note.id}`, {
    method: 'PUT',
    headers: { 'Content-Type': 'application/json' },
    body: JSON.stringify({
      date: note.date,
      title: note.title,
      author: note.author ?? '',
      process: note.process ?? '',
      defectContent: note.defectContent ?? '',
      countermeasureContent: note.countermeasureContent ?? '',
    }),
  })
  if (!res.ok) throw new Error('Failed to update note')
  return await res.json()
}








