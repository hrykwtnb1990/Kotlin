package com.example.greenSafe.todo
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
@RestController
@RequestMapping("/api/todos")
class TodoController(
    private val todoRepository: TodoRepository
) {
    // 一覧 GET /api/todos
    @GetMapping
    fun getAll(): List<Todo> = todoRepository.findAll()
    // 1件取得 GET /api/todos/{id}
    @GetMapping("/{id}")
    fun getOne(@PathVariable id: Long): Todo =
        todoRepository.findById(id)
            .orElseThrow { RuntimeException("Todo not found") }
    // 作成 POST /api/todos
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun create(@RequestBody body: CreateTodoRequest): Todo {
        val todo = Todo(
            title = body.title,
            completed = false
        )
        return todoRepository.save(todo)
    }
    // 更新 PUT /api/todos/{id}
    @PutMapping("/{id}")
    fun update(
        @PathVariable id: Long,
        @RequestBody body: UpdateTodoRequest
    ): Todo {
        val todo = todoRepository.findById(id)
            .orElseThrow { RuntimeException("Todo not found") }
        todo.title = body.title
        todo.completed = body.completed
        return todoRepository.save(todo)
    }
    // 削除 DELETE /api/todos/{id}
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun delete(@PathVariable id: Long) {
        todoRepository.deleteById(id)
    }
}
data class CreateTodoRequest(
    val title: String
)
data class UpdateTodoRequest(
    val title: String,
    val completed: Boolean
)







