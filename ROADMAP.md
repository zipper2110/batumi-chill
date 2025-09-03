# Batumi Guide - Implementation Roadmap

This document outlines the development plan for the Batumi Guide application, divided into 10 iterations. 
The approach is to start with a small, working MVP and incrementally add features.

## ✅ COMPLETED: Iteration 1: Project Setup & Basic Structure ✅
- ✓ Initialize Kotlin Spring Boot project with Maven
- ✓ Set up basic project structure (packages, configurations)
- ✓ Configure Vaadin integration
- ✓ Create a simple "Hello Batumi" landing page
- ✓ Use H2 in-memory database for now
- ✓ Create basic entity models for locations
- ✓ Implement a basic repository layer

**Status: COMPLETED on 2025-09-03**

## ✅ COMPLETED: Iteration 2: Core Data Model & Simple UI ✅
- ✓ Create the Location entity with essential fields (name, description, category, visited flag)
- ✓ Implement the Category entity/enum
- ✓ Create a simple data repository
- ✓ Add seed data with 5-10 popular locations in Batumi
- ✓ Implement a basic list view that displays all locations
- ✓ Add a simple header with application title

**Status: COMPLETED on 2025-09-03**

## ✅ COMPLETED: Iteration 3: Location Cards MVP ✅
- ✓ Design and implement location card component
- ✓ Display basic information on cards (name, category, short description)
- ✓ Implement a simple grid layout for cards
- ✓ Add basic responsive design for mobile and desktop views
- ✓ Create a simple main layout with navigation placeholder

**Status: COMPLETED on 2025-09-03**

## Iteration 4: Location Details & Visit Tracking
- Enhance location cards with more details
- Add the ability to mark places as visited/not visited
- Implement persistent storage of visited status
- Create a location detail view that shows when a card is clicked
- Add "back to list" navigation

## Iteration 5: Category Filtering
- Implement category filtering functionality
- Create a category filter component in the UI
- Add visual indicators for different categories
- Enhance the UI to show active filters
- Implement "show all/visited/not visited" toggle

## Iteration 6: Improved UI & UX
- Enhance overall application styling
- Implement proper mobile-first responsive design
- Add loading indicators and proper error handling
- Improve navigation between views
- Add sorting options (by name, by category)

## Iteration 7: Map View - Basic Implementation
- Integrate Leaflet.js with Vaadin
- Create a basic map view showing Batumi
- Add simple markers for all locations
- Implement toggle between list and map views
- Connect markers to location data

## Iteration 8: Enhanced Map Features
- Add popup information when markers are clicked
- Implement category filtering on the map
- Add color coding for markers based on category
- Implement the ability to filter visited/not visited places on the map
- Add a "center map" button

## Iteration 9: Location Details Enhancement
- Add support for multiple photos per location
- Implement opening hours display
- Add address and contact information
- Create a "coolness rating" feature (must-see, cool, just ok)
- Implement address display and linking to external maps

## Iteration 10: Final Features & Polish
- Implement user location detection (if browser permits)
- Add "distance from current location" feature
- Sort locations by proximity
- Implement "get directions" functionality
- Add progress tracking (% of places visited)
- Final UI polish and performance optimization
- Comprehensive testing and bug fixing

## Future Enhancements (Post MVP)
- User accounts and personalization
- Ability to add custom places
- Social sharing features
- Offline support
- Customizable itineraries
- User reviews and ratings
- Multiple language support

---
This roadmap provides a structured approach to building the Batumi Guide application, starting with the most essential features and gradually adding more advanced functionality. Each iteration builds upon the previous one, ensuring that there's always a working version of the application.
