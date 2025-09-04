package com.litvin.batumichill.config

import com.litvin.batumichill.model.Category
import com.litvin.batumichill.model.CoolnessRating
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
                    category = Category.PARK,
                    latitude = 41.6504,
                    longitude = 41.6352,
                    photos = mutableListOf(
                        "https://upload.wikimedia.org/wikipedia/commons/thumb/8/8d/Batumi_Boulevard.jpg/1280px-Batumi_Boulevard.jpg",
                        "https://dynamic-media-cdn.tripadvisor.com/media/photo-o/17/8c/15/3e/batumi-boulevard.jpg?w=1200&h=-1&s=1"
                    ),
                    address = "Batumi Beach, Batumi, Georgia",
                    openingHours = "Open 24 hours",
                    coolnessRating = CoolnessRating.MUST_SEE,
                    externalMapUrl = "https://maps.google.com/?q=41.6504,41.6352"
                ),
                Location(
                    name = "Batumi Botanical Garden",
                    description = "One of the largest botanical gardens in the former Soviet Union, featuring plants from nine different geographical zones.",
                    category = Category.PARK,
                    latitude = 41.7019,
                    longitude = 41.7061,
                    photos = mutableListOf(
                        "https://upload.wikimedia.org/wikipedia/commons/thumb/4/47/Batumi_Botanical_Garden_entrance.jpg/1280px-Batumi_Botanical_Garden_entrance.jpg",
                        "https://dynamic-media-cdn.tripadvisor.com/media/photo-o/0d/b4/60/eb/batumi-botanical-gardens.jpg?w=1200&h=-1&s=1"
                    ),
                    address = "Mtsvane Kontskhi, Batumi, Georgia",
                    phone = "+995 422 27 15 00",
                    website = "http://bbg.ge/en/",
                    openingHours = "09:00-19:00 daily (May-October), 09:00-17:00 daily (November-April)",
                    coolnessRating = CoolnessRating.MUST_SEE,
                    externalMapUrl = "https://maps.google.com/?q=41.7019,41.7061"
                ),
                Location(
                    name = "Piazza Square",
                    description = "A beautiful square built in Italian architectural style, home to many restaurants and cafes.",
                    category = Category.HISTORICAL_SITE,
                    latitude = 41.6518,
                    longitude = 41.6369,
                    photos = mutableListOf(
                        "https://upload.wikimedia.org/wikipedia/commons/thumb/3/3b/Batumi_Piazza_Square.jpg/1280px-Batumi_Piazza_Square.jpg",
                        "https://dynamic-media-cdn.tripadvisor.com/media/photo-o/0d/b4/5f/a9/piazza-batumi.jpg?w=1200&h=-1&s=1"
                    ),
                    address = "Piazza Square, Batumi, Georgia",
                    openingHours = "Open 24 hours",
                    coolnessRating = CoolnessRating.COOL,
                    externalMapUrl = "https://maps.google.com/?q=41.6518,41.6369"
                ),
                Location(
                    name = "Ali and Nino Moving Sculpture",
                    description = "A 7-meter-tall moving metal sculpture based on the famous novel, symbolizing love between different nations.",
                    category = Category.VIEWPOINT,
                    latitude = 41.6558,
                    longitude = 41.6376,
                    photos = mutableListOf(
                        "https://upload.wikimedia.org/wikipedia/commons/thumb/f/f8/Ali_and_Nino_statue_in_Batumi.jpg/1280px-Ali_and_Nino_statue_in_Batumi.jpg",
                        "https://dynamic-media-cdn.tripadvisor.com/media/photo-o/0e/af/19/dd/ali-nino-statue.jpg?w=1200&h=-1&s=1"
                    ),
                    address = "Batumi Boulevard, Batumi, Georgia",
                    openingHours = "Best viewed at sunset",
                    coolnessRating = CoolnessRating.COOL,
                    externalMapUrl = "https://maps.google.com/?q=41.6558,41.6376"
                ),
                Location(
                    name = "Batumi Old Town",
                    description = "The historical center of Batumi with 19th-century architecture, small cafes, and souvenir shops.",
                    category = Category.HISTORICAL_SITE,
                    latitude = 41.6491,
                    longitude = 41.6387,
                    photos = mutableListOf(
                        "https://dynamic-media-cdn.tripadvisor.com/media/photo-o/10/03/a0/ae/old-town.jpg?w=1200&h=-1&s=1",
                        "https://dynamic-media-cdn.tripadvisor.com/media/photo-o/13/32/da/a9/batumi-old-town.jpg?w=1200&h=-1&s=1"
                    ),
                    address = "Old Town, Batumi, Georgia",
                    openingHours = "Open 24 hours, shops typically open 10:00-20:00",
                    coolnessRating = CoolnessRating.MUST_SEE,
                    externalMapUrl = "https://maps.google.com/?q=41.6491,41.6387"
                ),
                Location(
                    name = "Batumi Archeological Museum",
                    description = "Houses a collection of archaeological findings from the region, including artifacts from the Bronze Age.",
                    category = Category.MUSEUM,
                    latitude = 41.6506,
                    longitude = 41.6360,
                    photos = mutableListOf(
                        "https://dynamic-media-cdn.tripadvisor.com/media/photo-o/0d/b4/5f/a5/batumi-archaeological.jpg?w=1200&h=-1&s=1"
                    ),
                    address = "Chavchavadze St, Batumi, Georgia",
                    phone = "+995 422 27 75 27",
                    openingHours = "10:00-18:00 Tuesday-Sunday, Closed on Monday",
                    coolnessRating = CoolnessRating.JUST_OK,
                    externalMapUrl = "https://maps.google.com/?q=41.6506,41.6360"
                ),
                Location(
                    name = "Sarpi Beach",
                    description = "A pebble beach near the Turkish border with crystal clear water, one of the cleanest beaches in the region.",
                    category = Category.BEACH,
                    latitude = 41.5232,
                    longitude = 41.5465,
                    photos = mutableListOf(
                        "https://dynamic-media-cdn.tripadvisor.com/media/photo-o/0d/b4/60/e9/sarpi-beach.jpg?w=1200&h=-1&s=1",
                        "https://dynamic-media-cdn.tripadvisor.com/media/photo-o/13/32/db/1c/sarpi-beach.jpg?w=1200&h=-1&s=1"
                    ),
                    address = "Sarpi, Georgia",
                    openingHours = "Open 24 hours",
                    coolnessRating = CoolnessRating.COOL,
                    externalMapUrl = "https://maps.google.com/?q=41.5232,41.5465"
                )
            )

            locationRepository.saveAll(sampleLocations)
        }
    }
}
