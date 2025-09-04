package com.litvin.batumichill.ui.components

import com.litvin.batumichill.model.Location
import com.litvin.batumichill.ui.LocationDetailView
import com.litvin.batumichill.ui.util.CategoryColorUtil
import com.litvin.batumichill.ui.util.CoolnessRatingUtil
import com.vaadin.flow.component.UI
import com.vaadin.flow.component.dependency.CssImport
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
 * Enhanced with better styling and hover effects.
 */
@CssImport("./styles/location-card.css")
class LocationCard(private val location: Location) : VerticalLayout() {

    init {
        addClassNames(
            Background.CONTRAST_10,
            BorderRadius.LARGE,
            BoxShadow.SMALL,
            Display.FLEX,
            FlexDirection.COLUMN,
            AlignItems.START,
            Padding.MEDIUM,
            Height.AUTO,
            Width.AUTO,
            "location-card"
        )
        setSpacing(false)
        setWidthFull()
        setHeight("auto")
        setMinHeight("200px")

        // Add click listener to navigate to detail view
        addClickListener { 
            UI.getCurrent().navigate(LocationDetailView::class.java, location.id)
        }

        // Header with name and visited status
        val headerLayout = HorizontalLayout()
        headerLayout.setWidthFull()
        headerLayout.setDefaultVerticalComponentAlignment(FlexComponent.Alignment.CENTER)
        headerLayout.justifyContentMode = FlexComponent.JustifyContentMode.BETWEEN
        headerLayout.addClassName("card-header")

        // Location name
        val nameElement = H3(location.name)
        nameElement.addClassNames(
            Margin.NONE,
            FontSize.XLARGE,
            TextColor.PRIMARY,
            "card-title"
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
                FontWeight.MEDIUM,
                "visited-badge"
            )
            badge
        } else {
            null
        }

        headerLayout.add(nameElement)
        visitedStatus?.let { headerLayout.add(it) }

        // Category badge
        val categoryBadge = createCategoryBadge(location)

        // Coolness rating badge
        val coolnessRatingBadge = createCoolnessRatingBadge(location)

        // Badges layout
        val badgesLayout = HorizontalLayout(categoryBadge, coolnessRatingBadge)
        badgesLayout.setSpacing(true)
        badgesLayout.addClassName(Gap.SMALL)

        // Description
        val descriptionElement = Paragraph(getShortDescription(location.description))
        descriptionElement.addClassNames(
            Margin.Top.SMALL,
            Margin.Bottom.NONE,
            TextColor.SECONDARY,
            "card-description"
        )

        add(headerLayout, badgesLayout, descriptionElement)
    }

    private fun createCategoryBadge(location: Location): Span {
        val badge = Span(location.category.name.replace("_", " ").lowercase().capitalize())
        badge.addClassNames(
            BorderRadius.MEDIUM,
            Padding.Horizontal.SMALL,
            Padding.Vertical.XSMALL,
            FontSize.SMALL,
            FontWeight.MEDIUM,
            Margin.Top.XSMALL,
            "category-badge"
        )
        badge.style.setBackgroundColor(CategoryColorUtil.getBackgroundColorStyle(location.category))
        badge.style.setColor(CategoryColorUtil.getTextColorStyle(location.category))
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

    private fun createCoolnessRatingBadge(location: Location): Span {
        val rating = location.coolnessRating
        val badge = Span(CoolnessRatingUtil.getDisplayName(rating))

        badge.addClassNames(
            BorderRadius.MEDIUM,
            Padding.Horizontal.SMALL,
            Padding.Vertical.XSMALL,
            FontSize.SMALL,
            FontWeight.MEDIUM,
            "coolness-badge"
        )

        badge.style.setBackgroundColor(CoolnessRatingUtil.getBackgroundColorStyle(rating))
        badge.style.setColor(CoolnessRatingUtil.getTextColorStyle(rating))

        // Add icon
        val icon = Icon(CoolnessRatingUtil.getIcon(rating))
        icon.addClassNames(
            Margin.Right.XSMALL
        )

        badge.element.insertChild(0, icon.element)

        return badge
    }
}
