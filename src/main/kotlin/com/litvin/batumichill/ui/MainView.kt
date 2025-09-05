package com.litvin.batumichill.ui

import com.litvin.batumichill.model.Category
import com.litvin.batumichill.model.Location
import com.litvin.batumichill.service.LocationService
import com.litvin.batumichill.ui.components.FilterBar
import com.litvin.batumichill.ui.components.LocationCard
import com.litvin.batumichill.ui.components.MapView
import com.vaadin.flow.component.UI
import com.vaadin.flow.component.dependency.CssImport
import com.vaadin.flow.component.html.H2
import com.vaadin.flow.component.html.Paragraph
import com.vaadin.flow.component.notification.Notification
import com.vaadin.flow.component.orderedlayout.FlexComponent
import com.vaadin.flow.component.orderedlayout.FlexLayout
import com.vaadin.flow.component.orderedlayout.HorizontalLayout
import com.vaadin.flow.component.orderedlayout.VerticalLayout
import com.vaadin.flow.component.progressbar.ProgressBar
import com.vaadin.flow.component.select.Select
import com.vaadin.flow.router.PageTitle
import com.vaadin.flow.router.Route
import com.vaadin.flow.router.RouteAlias
import com.vaadin.flow.theme.lumo.LumoUtility.*

@Route(value = "locations", layout = MainLayout::class)
@RouteAlias(value = "", layout = MainLayout::class)
@PageTitle("Locations | Batumi Chill Guide")
@CssImport("./styles/main-view.css")
class MainView(private val locationService: LocationService) : VerticalLayout() {

    private val cardsLayout = FlexLayout()
    private val mapView = MapView()
    private val filterBar = FilterBar()
    private val loadingIndicator = ProgressBar()
    private val sortSelect = Select<String>()

    // View state
    private var currentView = "both" // Both views are visible simultaneously

    // Filter and sort state
    private var selectedCategories: Set<Category> = emptySet()
    private var visitedFilter: String = "All"
    private var sortBy: String = "Name (A-Z)"

    init {
        addClassName(Padding.LARGE)
        setWidthFull()
        addClassName("main-view")

        // Page title and description
        val pageTitle = H2("Discover Batumi")
        pageTitle.addClassNames(
            FontSize.XXXLARGE,
            Margin.NONE,
            TextColor.PRIMARY
        )

        val description = Paragraph("Explore the beautiful coastal city of Batumi with our curated list of must-visit locations")
        description.addClassNames(
            Margin.Top.SMALL,
            Margin.Bottom.MEDIUM,
            TextColor.SECONDARY,
            MaxWidth.SCREEN_MEDIUM
        )

        // Configure loading indicator
        configureLoadingIndicator()

        // Configure filter bar
        configureFilterBar()

        // Configure sorting
        configureSorting()

        // Configure cards layout
        configureCardsLayout()

        // Configure map view
        configureMapView()

        // Create sort layout (removed toggle button)
        val sortLayout = HorizontalLayout(sortSelect)
        sortLayout.setWidthFull()
        sortLayout.justifyContentMode = FlexComponent.JustifyContentMode.END
        sortLayout.defaultVerticalComponentAlignment = FlexComponent.Alignment.BASELINE

        // Add components to layout
        add(pageTitle, description, filterBar, sortLayout, loadingIndicator)

        // Add the current view component (cards layout)
        add(cardsLayout)

        // MapView is already added in configureMapView() and is always visible

        // Load data
        updateList()

        // Initialize map and update markers
        try {
            mapView.initializeMap()
            updateMapMarkers()
        } catch (e: Exception) {
            println("Error initializing map: ${e.message}")
            Notification.show(
                "Error initializing map: ${e.message}",
                3000,
                Notification.Position.BOTTOM_START
            )
        }
    }

    private fun configureMapView() {
        println("Configuring map view")

        // Ensure the map container has proper dimensions
        mapView.setWidthFull()
        mapView.style.set("height", "auto") // Allow height to adjust based on content
        mapView.style.set("margin-top", "1rem")

        // Make the map view visible all the time
        mapView.style.set("display", "flex")

        // Add the map view to the layout
        add(mapView)

        println("Map view added to layout with ID: ${mapView.element.getAttribute("id")}")
    }


    private fun updateMapMarkers() {
        println("Updating map markers")

        // Get filtered locations
        val filteredLocations = getFilteredAndSortedLocations()

        println("Found ${filteredLocations.size} locations to display on map")

        // Log some details about the locations
        filteredLocations.forEach { location ->
            println("Location: ${location.name}, Coordinates: ${location.latitude}, ${location.longitude}")
        }

        // Update map markers
        try {
            mapView.updateMarkers(filteredLocations)
            println("Map markers update initiated")
        } catch (e: Exception) {
            println("Error updating map markers: ${e.message}")
            throw e  // Re-throw to be caught by the caller
        }
    }

    private fun getFilteredAndSortedLocations(): List<Location> {
        // Get all locations
        var filteredLocations = locationService.getAllLocations()

        // Apply category filter if any categories are selected
        if (selectedCategories.isNotEmpty()) {
            filteredLocations = filteredLocations.filter { location ->
                location.category in selectedCategories
            }
        }

        // Apply visited filter
        when (visitedFilter) {
            "Visited" -> filteredLocations = filteredLocations.filter { it.visited }
            "Not Visited" -> filteredLocations = filteredLocations.filter { !it.visited }
        }

        // Apply sorting
        filteredLocations = when (sortBy) {
            "Name (A-Z)" -> filteredLocations.sortedBy { it.name }
            "Name (Z-A)" -> filteredLocations.sortedByDescending { it.name }
            "Category (A-Z)" -> filteredLocations.sortedBy { it.category.name }
            "Category (Z-A)" -> filteredLocations.sortedByDescending { it.category.name }
            else -> filteredLocations
        }

        return filteredLocations
    }

    private fun configureLoadingIndicator() {
        loadingIndicator.isIndeterminate = true
        loadingIndicator.addClassNames(
            Width.FULL,
            Margin.Vertical.MEDIUM
        )
        loadingIndicator.style.set("display", "none")
    }

    private fun configureSorting() {
        sortSelect.setLabel("Sort by")
        sortSelect.setItems("Name (A-Z)", "Name (Z-A)", "Category (A-Z)", "Category (Z-A)")
        sortSelect.value = "Name (A-Z)"
        sortSelect.addClassNames(
            Margin.Top.MEDIUM,
            Width.AUTO
        )
        sortSelect.addValueChangeListener {
            sortBy = it.value
            updateList()
        }
    }

    private fun configureFilterBar() {
        // Set up category filter listener
        filterBar.setCategoryChangeListener { categories ->
            selectedCategories = categories
            updateList()

            // Show notification about active filters
            if (categories.isNotEmpty()) {
                Notification.show(
                    "Filtering by ${categories.size} categories",
                    3000,
                    Notification.Position.BOTTOM_START
                )
            }
        }

        // Set up visited filter listener
        filterBar.setVisitedChangeListener { filter ->
            visitedFilter = filter
            updateList()

            // Show notification about active filter
            if (filter != "All") {
                Notification.show(
                    "Showing $filter locations",
                    3000,
                    Notification.Position.BOTTOM_START
                )
            }
        }
    }

    private fun configureCardsLayout() {
        cardsLayout.addClassNames(
            Display.FLEX,
            FlexWrap.WRAP,
            Gap.MEDIUM,
            Margin.Top.MEDIUM,
            "cards-container"
        )
        cardsLayout.setWidthFull()
        cardsLayout.setHeight("auto")
    }

    private fun updateList() {
        // Show loading indicator
        loadingIndicator.style.set("display", "block")
        cardsLayout.removeAll()

        // Use UI.getCurrent().access to update the UI after a delay
        // This simulates a network request and shows the loading indicator
        UI.getCurrent().access {
            try {
                // Simulate network delay
                Thread.sleep(300)

                // Get filtered and sorted locations
                val filteredLocations = getFilteredAndSortedLocations()

                // Display filtered locations
                if (filteredLocations.isEmpty()) {
                    // Show a message when no locations match the filters
                    val noResultsMessage = Paragraph("No locations match the selected filters")
                    noResultsMessage.addClassNames(
                        Margin.Top.LARGE,
                        TextColor.SECONDARY,
                        FontSize.LARGE
                    )
                    noResultsMessage.style.set("text-align", "center")
                    noResultsMessage.style.set("width", "100%")
                    cardsLayout.add(noResultsMessage)
                } else {
                    // Display the filtered locations
                    filteredLocations.forEach { location ->
                        val card = LocationCard(location)
                        card.addClassName("card-item")
                        cardsLayout.add(card)
                    }

                    // Show notification about the number of locations
                    Notification.show(
                        "${filteredLocations.size} locations found",
                        3000,
                        Notification.Position.BOTTOM_START
                    )
                }

                // Always update map markers to apply filters to the map
                try {
                    updateMapMarkers()
                } catch (e: Exception) {
                    println("Error updating map markers: ${e.message}")
                    Notification.show(
                        "Error updating map: ${e.message}",
                        3000,
                        Notification.Position.BOTTOM_START
                    )
                }
            } catch (e: Exception) {
                // Handle errors
                Notification.show(
                    "Error loading locations: ${e.message}",
                    5000,
                    Notification.Position.MIDDLE
                )
            } finally {
                // Hide loading indicator
                loadingIndicator.style.set("display", "none")
            }
        }
    }
}
