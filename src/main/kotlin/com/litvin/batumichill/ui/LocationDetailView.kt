package com.litvin.batumichill.ui

import com.litvin.batumichill.model.Location
import com.litvin.batumichill.service.LocationService
import com.litvin.batumichill.ui.util.CategoryColorUtil
import com.vaadin.flow.component.UI
import com.vaadin.flow.component.button.Button
import com.vaadin.flow.component.button.ButtonVariant
import com.vaadin.flow.component.checkbox.Checkbox
import com.vaadin.flow.component.dependency.CssImport
import com.vaadin.flow.component.html.H2
import com.vaadin.flow.component.html.Paragraph
import com.vaadin.flow.component.html.Span
import com.vaadin.flow.component.icon.Icon
import com.vaadin.flow.component.icon.VaadinIcon
import com.vaadin.flow.component.notification.Notification
import com.vaadin.flow.component.orderedlayout.HorizontalLayout
import com.vaadin.flow.component.orderedlayout.VerticalLayout
import com.vaadin.flow.component.progressbar.ProgressBar
import com.vaadin.flow.router.BeforeEvent
import com.vaadin.flow.router.HasUrlParameter
import com.vaadin.flow.router.PageTitle
import com.vaadin.flow.router.Route
import com.vaadin.flow.theme.lumo.LumoUtility.*

@Route(value = "location", layout = MainLayout::class)
@PageTitle("Location Details | Batumi Chill Guide")
@CssImport("./styles/location-detail.css")
class LocationDetailView(private val locationService: LocationService) : VerticalLayout(), HasUrlParameter<Long> {

    private var location: Location? = null
    private val visitedCheckbox = Checkbox("Visited")
    private val loadingIndicator = ProgressBar()
    private val contentContainer = VerticalLayout()

    init {
        addClassName(Padding.LARGE)
        setWidthFull()
        addClassName("location-detail-view")

        // Configure loading indicator
        loadingIndicator.isIndeterminate = true
        loadingIndicator.addClassNames(
            Width.FULL,
            Margin.Vertical.MEDIUM
        )

        // Configure content container
        contentContainer.setPadding(false)
        contentContainer.setSpacing(true)
        contentContainer.setWidthFull()

        // Add loading indicator and content container
        add(loadingIndicator, contentContainer)
    }

    override fun setParameter(event: BeforeEvent, locationId: Long) {
        // Show loading indicator
        loadingIndicator.style.set("display", "block")
        contentContainer.removeAll()

        // Use UI.getCurrent().access to update the UI after a delay
        UI.getCurrent().access {
            try {
                // Simulate network delay
                Thread.sleep(300)

                // Get location by ID
                location = locationService.getLocationById(locationId)
                location?.let { displayLocation(it) } ?: showNotFound()
            } catch (e: Exception) {
                // Handle errors
                showError(e.message ?: "An error occurred")
            } finally {
                // Hide loading indicator
                loadingIndicator.style.set("display", "none")
            }
        }
    }

    private fun displayLocation(location: Location) {
        contentContainer.removeAll()

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
        contentContainer.add(backButton, headerLayout, descriptionElement, visitedCheckbox)
    }

    private fun showNotFound() {
        contentContainer.removeAll()

        val backButton = Button("Back to List", Icon(VaadinIcon.ARROW_LEFT))
        backButton.addThemeVariants(ButtonVariant.LUMO_TERTIARY)
        backButton.addClickListener { e -> e.source.ui.ifPresent { ui -> ui.navigate(MainView::class.java) } }

        val notFoundMessage = H2("Location not found")
        notFoundMessage.addClassNames(
            Margin.Top.XLARGE,
            TextColor.ERROR
        )

        contentContainer.add(backButton, notFoundMessage)
    }

    private fun showError(errorMessage: String) {
        contentContainer.removeAll()

        val backButton = Button("Back to List", Icon(VaadinIcon.ARROW_LEFT))
        backButton.addThemeVariants(ButtonVariant.LUMO_TERTIARY)
        backButton.addClickListener { e -> e.source.ui.ifPresent { ui -> ui.navigate(MainView::class.java) } }

        val errorIcon = Icon(VaadinIcon.WARNING)
        errorIcon.addClassNames(
            TextColor.ERROR,
            FontSize.XXXLARGE
        )

        val errorTitle = H2("Error")
        errorTitle.addClassNames(
            Margin.Top.MEDIUM,
            TextColor.ERROR
        )

        val errorText = Paragraph(errorMessage)
        errorText.addClassNames(
            TextColor.SECONDARY,
            Margin.Top.SMALL
        )

        contentContainer.add(backButton, errorIcon, errorTitle, errorText)

        // Show notification
        Notification.show(
            "Error: $errorMessage",
            5000,
            Notification.Position.MIDDLE
        )
    }

    private fun createCategoryBadge(location: Location): Span {
        val badge = Span(location.category.name.replace("_", " ").lowercase().capitalize())
        badge.addClassNames(
            BorderRadius.MEDIUM,
            Padding.Horizontal.SMALL,
            Padding.Vertical.XSMALL,
            FontSize.SMALL,
            FontWeight.MEDIUM,
            "category-badge"
        )
        badge.style.setBackgroundColor(CategoryColorUtil.getBackgroundColorStyle(location.category))
        badge.style.setColor(CategoryColorUtil.getTextColorStyle(location.category))
        return badge
    }
}
