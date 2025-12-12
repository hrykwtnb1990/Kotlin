package com.example.greenSafe.note
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import java.time.LocalDate
@RestController
@RequestMapping("/api/notes")
class NoteController(
    private val noteRepository: NoteRepository
) {
    // 1ヶ月分のノートを取得（例: /api/notes?year=2025&month=1）
    @GetMapping
    fun getByMonth(
        @RequestParam year: Int,
        @RequestParam month: Int
    ): List<Note> {
        val start = LocalDate.of(year, month, 1)
        val end = start.withDayOfMonth(start.lengthOfMonth())
        return noteRepository.findByDateBetweenOrderByDateAsc(start, end)
    }
    // 1件取得
    @GetMapping("/{id}")
    fun getOne(@PathVariable id: Long): Note =
        noteRepository.findById(id)
            .orElseThrow { RuntimeException("Note not found") }
    // 作成
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun create(@RequestBody body: CreateNoteRequest): Note {
        val note = Note(
            date = LocalDate.parse(body.date),
            title = body.title,
            author = body.author,
            process = body.process,
            defectContent = body.defectContent,
            countermeasureContent = body.countermeasureContent
        )
        return noteRepository.save(note)
    }
    @PutMapping("/{id}")
    fun update(
        @PathVariable id: Long,
        @RequestBody body: UpdateNoteRequest
    ): Note {
        val note = noteRepository.findById(id)
            .orElseThrow { RuntimeException("Note not found") }
        note.date = LocalDate.parse(body.date)
        note.title = body.title
        note.author = body.author
        note.process = body.process
        note.defectContent = body.defectContent
        note.countermeasureContent = body.countermeasureContent
        return noteRepository.save(note)
    }
    // ･･･ delete はそのままでOK
}
data class CreateNoteRequest(
    val date: String,
    val title: String,
    val author: String?,
    val process: String?,
    val defectContent: String?,
    val countermeasureContent: String?
)
data class UpdateNoteRequest(
    val date: String,
    val title: String,
    val author: String?,
    val process: String?,
    val defectContent: String?,
    val countermeasureContent: String?
)
























