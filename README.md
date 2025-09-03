# Batumi Guide

## Project Goal & Value

Batumi Guide is a personalized web application designed to help visitors discover and explore the beautiful coastal town of Batumi, Georgia. The application serves as a digital guide for tourists, allowing them to browse through various points of interest, organize their visit by categories, track visited locations, and visualize all places on an interactive map.

The primary value of this application is to enhance the visitor experience by providing a curated list of local attractions, helping tourists to efficiently plan their stay and not miss any must-see locations in Batumi.

The app is mobile-first and intended to be used primarily on Android phones in a browser.

## Features

### Location Library
- Browse places of interest categorized by type (e.g., Restaurants, Historical Sites, Parks, Beaches, etc.)
and coolness (3 degrees of must-see, cool and just ok)
- Detailed cards for each location including:
  - Name and description
  - Address and contact information
  - Photos
  - Opening hours (if applicable)
- Mark places as visited/not visited
- Filter places by category and visited status
- Sort places by remoteness from you current location

### Interactive Map View
- Visual representation of all points of interest on a map
- Filter map markers by category
- Toggle between showing all places or only unvisited places
- Click on markers to view location details
- Get directions to selected locations

### User Experience
- Mobile-first design for both desktop and mobile use
- Intuitive navigation between list and map views
- Progress tracking for visited places

## Technical Stack

### Backend
- Kotlin - Primary programming language
- Spring Boot - Application framework
- Spring Data JPA - Data persistence
- PostgreSQL - Database for storing location data and user preferences

### Frontend
- Vaadin - UI framework for building web applications in Kotlin
- Leaflet.js (integrated with Vaadin) - For interactive maps
- Vaadin components for responsive layouts, cards, and form elements

### Development Tools
- Maven - Build and dependency management
- Docker - Containerization for development and deployment
- Git - Version control

### APIs
- OpenStreetMap or Google Maps API - For map data and geolocation services

---

The application will be built using Java SDK 22 and Kotlin 2.1, leveraging modern features of both technologies to create an efficient and maintainable codebase.
