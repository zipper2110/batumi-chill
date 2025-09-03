package com.litvin.batumichill.ui

import com.litvin.batumichill.model.Location
import com.litvin.batumichill.service.LocationService
import com.vaadin.flow.component.button.Button
import com.vaadin.flow.component.button.ButtonVariant
import com.vaadin.flow.component.checkbox.Checkbox
import com.vaadin.flow.component.html.Div
import com.vaadin.flow.component.html.H2
import com.vaadin.flow.component.html.Paragraph
import com.vaadin.flow.component.html.Span
import com.vaadin.flow.component.icon.Icon
import com.vaadin.flow.component.icon.VaadinIcon
import com.vaadin.flow.component.notification.Notification
import com.vaadin.flow.component.orderedlayout.HorizontalLayout
import com.vaadin.flow.component.orderedlayout.VerticalLayout
import com.vaadin.flow.router.BeforeEvent
import com.vaadin.flow.router.HasUrlParameter
import com.vaadin.flow.router.PageTitle
import com.vaadin.flow.router.Route
import com.vaadin.flow.theme.lumo.LumoUtility.*

@Route(value = "location", layout = MainLayout::class)
@PageTitle("Location Details | Batumi Chill Guide")
class LocationDetailView(private val locationService: LocationService) : VerticalLayout(), HasUrlParameter<Long> {

    private var location: Location? = null
    private val visitedCheckbox = Checkbox("Visited")

    init {
        addClassName(Padding.LARGE)
        setWidthFull()
    }

    override fun setParameter(event: BeforeEvent, locationId: Long) {
        location = locationService.getLocationById(locationId)
        location?.let { displayLocation(it) } ?: showNotFound()
    }

    private fun displayLocation(location: Location) {
        removeAll()

        // Back button
        val backButton = Button("Back to List", Icon(VaadinIcon.ARROW_LEFT))
        backButton.addThemeVariants(ButtonVariant.LUMO_TERTIARY)
        backButton.addClickListener { e -> e.source.ui.ifPresent { ui -> ui.navigate(MainView::class.java) } }

        // Location name
        val nameElement = H2(location.name)
        nameElement.addClassNames(
            Margin.Top.MEDIUM,
            Margin.Bottom.SMALL,
            FontSize.XXXLARGE,
            TextColor.PRIMARY
        )

        // Category badge
        val categoryBadge = createCategoryBadge(location)

        // Header layout with name and category
        val headerLayout = HorizontalLayout(nameElement, categoryBadge)
        headerLayout.setDefaultVerticalComponentAlignment(com.vaadin.flow.component.orderedlayout.FlexComponent.Alignment.CENTER)
        headerLayout.addClassName(Gap.MEDIUM)

        // Description
        val descriptionElement = Paragraph(location.description)
        descriptionElement.addClassNames(
            Margin.Top.MEDIUM,
            Margin.Bottom.LARGE,
            TextColor.SECONDARY,
            MaxWidth.SCREEN_MEDIUM
        )

        // Visited checkbox
        visitedCheckbox.value = location.visited
        visitedCheckbox.addValueChangeListener { event ->
            val updatedLocation = locationService.updateVisitedStatus(location.id, event.value)
            if (updatedLocation != null) {
                Notification.show(
                    if (event.value) "Marked as visited!" else "Marked as not visited!",
                    3000,
                    Notification.Position.BOTTOM_START
                )
            }
        }

        // Add components to layout
        add(backButton, headerLayout, descriptionElement, visitedCheckbox)
    }

    private fun showNotFound() {
        removeAll()
        
        val backButton = Button("Back to List", Icon(VaadinIcon.ARROW_LEFT))
        backButton.addThemeVariants(ButtonVariant.LUMO_TERTIARY)
        backButton.addClickListener { e -> e.source.ui.ifPresent { ui -> ui.navigate(MainView::class.java) } }
        
        val notFoundMessage = H2("Location not found")
        notFoundMessage.addClassNames(
            Margin.Top.XLARGE,
            TextColor.ERROR
        )
        
        add(backButton, notFoundMessage)
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
            FontWeight.MEDIUM
        )
        return badge
    }
}