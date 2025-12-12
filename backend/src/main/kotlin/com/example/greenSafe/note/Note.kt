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

    // タイトル（テーマ・概要）
    @Column(nullable = false)
    var title: String = "",

    // 記入者
    @Column(nullable = true)
    var author: String? = null,

    // 工程
    @Column(nullable = true)
    var process: String? = null,

    // 不具合内容
    @Column(columnDefinition = "text", nullable = true)
    var defectContent: String? = null,

    // 対策内容
    @Column(columnDefinition = "text", nullable = true)
    var countermeasureContent: String? = null
)










