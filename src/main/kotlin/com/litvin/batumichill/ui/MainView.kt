package com.litvin.batumichill.ui

import com.litvin.batumichill.model.Location
import com.litvin.batumichill.service.LocationService
import com.litvin.batumichill.ui.components.LocationCard
import com.vaadin.flow.component.html.H2
import com.vaadin.flow.component.html.Paragraph
import com.vaadin.flow.component.orderedlayout.FlexComponent
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
            Margin.Bottom.LARGE,
            TextColor.SECONDARY,
            MaxWidth.SCREEN_MEDIUM
        )

        add(pageTitle, description)

        // Configure cards layout
        configureCardsLayout()
        add(cardsLayout)

        // Load data
        updateList()
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

        locationService.getAllLocations().forEach { location ->
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
