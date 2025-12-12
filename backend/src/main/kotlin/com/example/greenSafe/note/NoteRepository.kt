package com.example.greenSafe.note

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.time.LocalDate

@Repository
interface NoteRepository : JpaRepository<Note, Long> {
    fun findByDateBetweenOrderByDateAsc(start : LocalDate, end: LocalDate): List<Note>
    }
