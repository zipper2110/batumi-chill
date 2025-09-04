package com.litvin.batumichill.ui

import com.litvin.batumichill.model.Location
import com.litvin.batumichill.service.LocationService
import com.litvin.batumichill.ui.util.CategoryColorUtil
import com.litvin.batumichill.ui.util.CoolnessRatingUtil
import com.vaadin.flow.component.UI
import com.vaadin.flow.component.button.Button
import com.vaadin.flow.component.button.ButtonVariant
import com.vaadin.flow.component.checkbox.Checkbox
import com.vaadin.flow.component.dependency.CssImport
import com.vaadin.flow.component.html.*
import com.vaadin.flow.component.icon.Icon
import com.vaadin.flow.component.icon.VaadinIcon
import com.vaadin.flow.component.notification.Notification
import com.vaadin.flow.component.orderedlayout.FlexComponent
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

        // Coolness rating badge
        val coolnessRatingBadge = createCoolnessRatingBadge(location)

        // Header layout with name, category and coolness rating
        val headerLayout = HorizontalLayout(nameElement, categoryBadge, coolnessRatingBadge)
        headerLayout.setDefaultVerticalComponentAlignment(FlexComponent.Alignment.CENTER)
        headerLayout.addClassName(Gap.MEDIUM)

        // Description
        val descriptionElement = Paragraph(location.description)
        descriptionElement.addClassNames(
            Margin.Top.MEDIUM,
            Margin.Bottom.LARGE,
            TextColor.SECONDARY,
            MaxWidth.SCREEN_MEDIUM
        )

        // Photo gallery
        val photoGallery = createPhotoGallery(location)

        // Info section
        val infoSection = createInfoSection(location)

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
        contentContainer.add(
            backButton, 
            headerLayout, 
            descriptionElement,
            photoGallery,
            infoSection,
            visitedCheckbox
        )
    }

    private fun createPhotoGallery(location: Location): Div {
        val galleryContainer = Div()
        galleryContainer.addClassNames(
            Width.FULL,
            Margin.Vertical.MEDIUM
        )

        if (location.photos.isEmpty()) {
            val noPhotosMessage = Paragraph("No photos available")
            noPhotosMessage.addClassNames(
                TextColor.SECONDARY
            )
            noPhotosMessage.style.set("font-style", "italic")
            galleryContainer.add(noPhotosMessage)
            return galleryContainer
        }

        val photosLayout = HorizontalLayout()
        photosLayout.addClassNames(
            Width.FULL,
            Overflow.AUTO,
            Gap.MEDIUM
        )

        location.photos.forEach { photoUrl ->
            val photoContainer = Div()
            photoContainer.addClassNames(
                Height.MEDIUM,
                Width.AUTO
            )
            photoContainer.style.set("box-sizing", "border-box")

            val image = Image(photoUrl, "Photo of ${location.name}")
            image.addClassNames(
                Height.FULL,
                BorderRadius.MEDIUM
            )
            image.style.set("object-fit", "cover")

            photoContainer.add(image)
            photosLayout.add(photoContainer)
        }

        galleryContainer.add(photosLayout)
        return galleryContainer
    }

    private fun createInfoSection(location: Location): Div {
        val infoContainer = Div()
        infoContainer.addClassNames(
            Width.FULL,
            Margin.Vertical.MEDIUM,
            MaxWidth.SCREEN_MEDIUM
        )

        // Section title removed as per requirements

        // Address with map link
        if (!location.address.isNullOrBlank()) {
            // Create a horizontal layout for the map button and address
            val addressContainer = HorizontalLayout()
            addressContainer.setSpacing(true)
            addressContainer.setDefaultVerticalComponentAlignment(FlexComponent.Alignment.START)

            // Add map button if external map URL is available
            if (!location.externalMapUrl.isNullOrBlank()) {
                val mapButton = Button(Icon(VaadinIcon.MAP_MARKER))
                mapButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY)
                mapButton.addClickListener { 
                    UI.getCurrent().page.open(location.externalMapUrl, "_blank")
                }
                mapButton.addClassNames(
                    Margin.Right.SMALL
                )
                addressContainer.add(mapButton)
            }

            // Create and add the address layout without the "Address" label
            val addressLayout = createInfoItem(VaadinIcon.MAP_MARKER, "", location.address!!)
            addressContainer.add(addressLayout)

            infoContainer.add(addressContainer)
        }

        // Opening hours (without title)
        if (!location.openingHours.isNullOrBlank()) {
            val hoursLayout = createInfoItem(VaadinIcon.CLOCK, "", location.openingHours!!)
            infoContainer.add(hoursLayout)
        }

        // Phone
        if (!location.phone.isNullOrBlank()) {
            val phoneLayout = createInfoItem(VaadinIcon.PHONE, "Phone", location.phone!!)
            infoContainer.add(phoneLayout)
        }

        // Website
        if (!location.website.isNullOrBlank()) {
            val websiteLayout = createInfoItem(VaadinIcon.GLOBE, "Website", location.website!!)
            val websiteLink = Anchor(location.website, "Visit website")
            websiteLink.addClassNames(
                Margin.Top.XSMALL,
                TextColor.PRIMARY
            )
            websiteLayout.add(websiteLink)
            infoContainer.add(websiteLayout)
        }

        return infoContainer
    }

    private fun createInfoItem(icon: VaadinIcon, label: String, value: String): VerticalLayout {
        val layout = VerticalLayout()
        layout.setPadding(false)
        layout.setSpacing(false)
        layout.addClassNames(
            Margin.Bottom.MEDIUM
        )

        val labelLayout = HorizontalLayout()
        labelLayout.setSpacing(true)
        labelLayout.setDefaultVerticalComponentAlignment(FlexComponent.Alignment.CENTER)

        val iconElement = Icon(icon)
        iconElement.addClassNames(
            TextColor.SECONDARY,
            IconSize.SMALL
        )

        // Only add label if it's not empty
        if (label.isNotEmpty()) {
            val labelElement = Span(label)
            labelElement.addClassNames(
                FontWeight.SEMIBOLD,
                TextColor.SECONDARY
            )
            labelLayout.add(iconElement, labelElement)
        } else {
            labelLayout.add(iconElement)
        }

        val valueElement = Paragraph(value)
        valueElement.addClassNames(
            Margin.Top.XSMALL,
            Margin.Left.SMALL,
            TextColor.BODY
        )

        layout.add(labelLayout, valueElement)
        return layout
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
            IconSize.SMALL,
            Margin.Right.XSMALL
        )

        badge.element.insertChild(0, icon.element)

        return badge
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
