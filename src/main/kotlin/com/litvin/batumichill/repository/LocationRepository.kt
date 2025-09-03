package com.litvin.batumichill.repository

import com.litvin.batumichill.model.Category
import com.litvin.batumichill.model.Location
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface LocationRepository : JpaRepository<Location, Long> {
    fun findByCategory(category: Category): List<Location>
    fun findByVisited(visited: Boolean): List<Location>
}
