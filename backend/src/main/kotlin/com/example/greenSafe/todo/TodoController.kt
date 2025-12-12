package com.example.greenSafe.todo
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
@RestController
@RequestMapping("/api/todos")
class TodoController(
    private val todoRepository: TodoRepository
) {
    @GetMapping
    fun getAll(): List<Todo> = todoRepository.findAll()
    @GetMapping("/{id}")
    fun getOne(@PathVariable id: Long): Todo =
        todoRepository.findById(id)
            .orElseThrow { RuntimeException("Todo not found") }
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun create(@RequestBody body: CreateTodoRequest): Todo {
        val todo = Todo(
            title = body.title,
            completed = false
        )
        return todoRepository.save(todo)
    }
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







