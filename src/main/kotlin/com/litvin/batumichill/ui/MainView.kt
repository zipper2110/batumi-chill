package com.litvin.batumichill.ui

import com.litvin.batumichill.model.Category
import com.litvin.batumichill.service.LocationService
import com.litvin.batumichill.ui.components.FilterBar
import com.litvin.batumichill.ui.components.LocationCard
import com.vaadin.flow.component.html.H2
import com.vaadin.flow.component.html.Paragraph
import com.vaadin.flow.component.notification.Notification
import com.vaadin.flow.component.orderedlayout.FlexLayout
import com.vaadin.flow.component.orderedlayout.VerticalLayout
import com.vaadin.flow.router.PageTitle
import com.vaadin.flow.router.Route
import com.vaadin.flow.router.RouteAlias
import com.vaadin.flow.theme.lumo.LumoUtility.*

@Route(value = "locations", layout = MainLayout::class)
@RouteAlias(value = "", layout = MainLayout::class)
@PageTitle("Locations | Batumi Chill Guide")
class MainView(private val locationService: LocationService) : VerticalLayout() {

    private val cardsLayout = FlexLayout()
    private val filterBar = FilterBar()

    // Filter state
    private var selectedCategories: Set<Category> = emptySet()
    private var visitedFilter: String = "All"

    init {
        addClassName(Padding.LARGE)
        setWidthFull()

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

        // Configure filter bar
        configureFilterBar()

        // Configure cards layout
        configureCardsLayout()

        // Add components to layout
        add(pageTitle, description, filterBar, cardsLayout)

        // Load data
        updateList()
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

        // Set up clear filters listener
        filterBar.setClearFiltersListener {
            selectedCategories = emptySet()
            visitedFilter = "All"
            updateList()
            Notification.show(
                "Filters cleared",
                3000,
                Notification.Position.BOTTOM_START
            )
        }
    }

    private fun configureCardsLayout() {
        cardsLayout.addClassNames(
            Display.FLEX,
            FlexWrap.WRAP,
            Gap.MEDIUM,
            Margin.Top.MEDIUM
        )
        cardsLayout.setWidthFull()
    }

    private fun updateList() {
        cardsLayout.removeAll()

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

                // Make cards responsive
                card.addClassName("card-item")

                // Set card width based on screen size
                card.style["width"] = "100%"
                card.style["max-width"] = "300px"

                cardsLayout.add(card)
            }
        }
    }
}
