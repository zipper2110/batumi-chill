package com.litvin.batumichill.config

import com.litvin.batumichill.model.Category
import com.litvin.batumichill.model.Location
import com.litvin.batumichill.repository.LocationRepository
import org.springframework.boot.CommandLineRunner
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class DataInitializer {

    @Bean
    fun initData(locationRepository: LocationRepository): CommandLineRunner {
        return CommandLineRunner {
            val sampleLocations = listOf(
                Location(
                    name = "Batumi Boulevard",
                    description = "A 7km-long seaside promenade along the Black Sea coast. Perfect for walking, cycling, and enjoying the sea views.",
                    category = Category.PARK
                ),
                Location(
                    name = "Batumi Botanical Garden",
                    description = "One of the largest botanical gardens in the former Soviet Union, featuring plants from nine different geographical zones.",
                    category = Category.PARK
                ),
                Location(
                    name = "Piazza Square",
                    description = "A beautiful square built in Italian architectural style, home to many restaurants and cafes.",
                    category = Category.HISTORICAL_SITE
                ),
                Location(
                    name = "Ali and Nino Moving Sculpture",
                    description = "A 7-meter-tall moving metal sculpture based on the famous novel, symbolizing love between different nations.",
                    category = Category.VIEWPOINT
                ),
                Location(
                    name = "Batumi Old Town",
                    description = "The historical center of Batumi with 19th-century architecture, small cafes, and souvenir shops.",
                    category = Category.HISTORICAL_SITE
                ),
                Location(
                    name = "Batumi Archeological Museum",
                    description = "Houses a collection of archaeological findings from the region, including artifacts from the Bronze Age.",
                    category = Category.MUSEUM
                ),
                Location(
                    name = "Sarpi Beach",
                    description = "A pebble beach near the Turkish border with crystal clear water, one of the cleanest beaches in the region.",
                    category = Category.BEACH
                )
            )

            locationRepository.saveAll(sampleLocations)
        }
    }
}
