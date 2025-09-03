package com.litvin.batumichill.model

import jakarta.persistence.*

@Entity
@Table(name = "locations")
data class Location(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,

    val name: String,

    @Column(length = 1000)
    val description: String,

    @Enumerated(EnumType.STRING)
    val category: Category,

    val visited: Boolean = false
)
