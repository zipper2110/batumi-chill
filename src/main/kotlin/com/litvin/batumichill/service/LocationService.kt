package com.litvin.batumichill.service

import com.litvin.batumichill.model.Category
import com.litvin.batumichill.model.Location
import com.litvin.batumichill.repository.LocationRepository
import org.springframework.stereotype.Service

@Service
class LocationService(private val locationRepository: LocationRepository) {

    fun getAllLocations(): List<Location> {
        return locationRepository.findAll()
    }

    fun getLocationById(id: Long): Location? {
        return locationRepository.findById(id).orElse(null)
    }

    fun getLocationsByCategory(category: Category): List<Location> {
        return locationRepository.findByCategory(category)
    }

    fun getVisitedLocations(visited: Boolean): List<Location> {
        return locationRepository.findByVisited(visited)
    }

    fun updateVisitedStatus(id: Long, visited: Boolean): Location? {
        val location = getLocationById(id) ?: return null
        location.visited = visited
        return locationRepository.save(location)
    }
}
