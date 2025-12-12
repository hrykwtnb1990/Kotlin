package com.example.greenSafe.note

import jakarta.persistence.*
import java.time.LocalDate

@Entity
@Table(name = "notes")
data class Note(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    @Column(nullable = false)
    var date: LocalDate = LocalDate.now(),

    @Column(nullable = false)
    var title: String = "",

    @Column(nullable = true)
    var author: String? = null,

    @Column(nullable = true)
    var process: String? = null,

    @Column(columnDefinition = "text", nullable = true)
    var defectContent: String? = null,

    @Column(columnDefinition = "text", nullable = true)
    var countermeasureContent: String? = null
)










