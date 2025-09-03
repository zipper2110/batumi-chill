package com.litvin.batumichill.ui

import com.litvin.batumichill.model.Location
import com.litvin.batumichill.service.LocationService
import com.vaadin.flow.component.grid.Grid
import com.vaadin.flow.component.html.H1
import com.vaadin.flow.component.html.Paragraph
import com.vaadin.flow.component.orderedlayout.FlexComponent
import com.vaadin.flow.component.orderedlayout.HorizontalLayout
import com.vaadin.flow.component.orderedlayout.VerticalLayout
import com.vaadin.flow.router.Route
import com.vaadin.flow.theme.lumo.LumoUtility.*

@Route("") 
class MainView(private val locationService: LocationService) : VerticalLayout() {

    private val grid = Grid(Location::class.java)

    init {
        addClassName(Padding.LARGE)
        setWidthFull()

        // Create header
        val headerLayout = HorizontalLayout()
        headerLayout.setWidthFull()
        headerLayout.setJustifyContentMode(FlexComponent.JustifyContentMode.CENTER)
        headerLayout.setAlignItems(FlexComponent.Alignment.CENTER)

        val header = H1("Batumi Chill Guide")
        header.addClassNames(TextColor.PRIMARY, FontSize.XXXLARGE, Margin.NONE)

        val description = Paragraph("Your personal guide to exploring the beautiful coastal city of Batumi, Georgia")
        description.addClassNames(Margin.Top.MEDIUM, TextColor.SECONDARY, MaxWidth.SCREEN_MEDIUM)

        headerLayout.add(header)
        add(headerLayout, description)

        // Configure grid
        configureGrid()

        // Add grid to layout
        add(grid)

        // Load data
        updateList()
    }

    private fun configureGrid() {
        grid.addClassNames(Margin.Top.MEDIUM)
        grid.setWidthFull()
        grid.setHeight("500px")

        // Configure columns
        grid.setColumns("name", "category", "visited")
        grid.addColumn(Location::description).setHeader("Description").setAutoWidth(true).setFlexGrow(1)

        // Make columns auto-width
        grid.getColumnByKey("name").setAutoWidth(true).setHeader("Location")
        grid.getColumnByKey("category").setAutoWidth(true).setHeader("Category")
        grid.getColumnByKey("visited").setAutoWidth(true).setHeader("Visited")
    }

    private fun updateList() {
        grid.setItems(locationService.getAllLocations())
    }
}
