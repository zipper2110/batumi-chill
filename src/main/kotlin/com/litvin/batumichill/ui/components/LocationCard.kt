package com.litvin.batumichill.ui.components

import com.litvin.batumichill.model.Location
import com.litvin.batumichill.ui.LocationDetailView
import com.vaadin.flow.component.UI
import com.vaadin.flow.component.html.Div
import com.vaadin.flow.component.html.H3
import com.vaadin.flow.component.html.Paragraph
import com.vaadin.flow.component.html.Span
import com.vaadin.flow.component.icon.Icon
import com.vaadin.flow.component.icon.VaadinIcon
import com.vaadin.flow.component.orderedlayout.FlexComponent
import com.vaadin.flow.component.orderedlayout.HorizontalLayout
import com.vaadin.flow.component.orderedlayout.VerticalLayout
import com.vaadin.flow.theme.lumo.LumoUtility.*

/**
 * A component that displays location information in a card format.
 */
class LocationCard(private val location: Location) : VerticalLayout() {

    init {
        addClassNames(
            Background.CONTRAST_5,
            BorderRadius.LARGE,
            BoxShadow.SMALL,
            Display.FLEX,
            FlexDirection.COLUMN,
            AlignItems.START,
            Padding.MEDIUM,
            Height.AUTO,
            Width.AUTO
        )
        setSpacing(false)
        setWidthFull()
        style["transition"] = "transform 0.2s ease-in-out, box-shadow 0.2s ease-in-out"
        style["cursor"] = "pointer"
        style["hover"] = "transform: translateY(-5px); box-shadow: var(--lumo-box-shadow-m)"

        // Add click listener to navigate to detail view
        addClickListener { 
            UI.getCurrent().navigate(LocationDetailView::class.java, location.id)
        }

        // Header with name and visited status
        val headerLayout = HorizontalLayout()
        headerLayout.setWidthFull()
        headerLayout.setDefaultVerticalComponentAlignment(FlexComponent.Alignment.CENTER)
        headerLayout.justifyContentMode = FlexComponent.JustifyContentMode.BETWEEN

        // Location name
        val nameElement = H3(location.name)
        nameElement.addClassNames(
            Margin.NONE,
            FontSize.XLARGE,
            TextColor.PRIMARY
        )

        // Visited status indicator
        val visitedStatus = if (location.visited) {
            val icon = Icon(VaadinIcon.CHECK_CIRCLE)
            icon.addClassNames(TextColor.SUCCESS)
            val badge = Span(icon, Span("Visited"))
            badge.addClassNames(
                Background.SUCCESS_10,
                TextColor.SUCCESS,
                BorderRadius.MEDIUM,
                Padding.Horizontal.SMALL,
                Padding.Vertical.XSMALL,
                FontSize.XSMALL,
                FontWeight.MEDIUM
            )
            badge
        } else {
            null
        }

        headerLayout.add(nameElement)
        visitedStatus?.let { headerLayout.add(it) }

        // Category badge
        val categoryBadge = createCategoryBadge(location)

        // Description
        val descriptionElement = Paragraph(getShortDescription(location.description))
        descriptionElement.addClassNames(
            Margin.Top.SMALL,
            Margin.Bottom.NONE,
            TextColor.SECONDARY
        )

        add(headerLayout, categoryBadge, descriptionElement)
    }

    private fun createCategoryBadge(location: Location): Span {
        val badge = Span(location.category.name.replace("_", " ").lowercase().capitalize())
        badge.addClassNames(
            Background.PRIMARY_10,
            TextColor.PRIMARY,
            BorderRadius.MEDIUM,
            Padding.Horizontal.SMALL,
            Padding.Vertical.XSMALL,
            FontSize.SMALL,
            FontWeight.MEDIUM,
            Margin.Top.XSMALL
        )
        return badge
    }

    private fun getShortDescription(description: String): String {
        // Limit description to 100 characters
        return if (description.length > 100) {
            description.substring(0, 97) + "..."
        } else {
            description
        }
    }
}
