package com.litvin.batumichill.ui.components

import com.litvin.batumichill.model.Category
import com.vaadin.flow.component.combobox.MultiSelectComboBox
import com.vaadin.flow.component.dependency.CssImport
import com.vaadin.flow.component.html.H4
import com.vaadin.flow.component.orderedlayout.FlexLayout
import com.vaadin.flow.component.orderedlayout.VerticalLayout
import com.vaadin.flow.component.radiobutton.RadioButtonGroup
import com.vaadin.flow.component.radiobutton.RadioGroupVariant
import com.vaadin.flow.theme.lumo.LumoUtility.*

/**
 * A component that provides filtering options for locations.
 * Responsive design that adapts to different screen sizes.
 */
@CssImport("./styles/filter-bar.css")
class FilterBar : VerticalLayout() {

    private val categoryFilter = MultiSelectComboBox<Category>()
    private val visitedFilter = RadioButtonGroup<String>()
    private val filtersContainer = FlexLayout()

    // Event listeners
    private var categoryChangeListener: ((Set<Category>) -> Unit)? = null
    private var visitedChangeListener: ((String) -> Unit)? = null

    init {
        addClassNames(
            Background.CONTRAST_5,
            BorderRadius.LARGE,
            BoxShadow.SMALL,
            Padding.MEDIUM,
            Margin.Bottom.MEDIUM,
            Width.FULL
        )
        setSpacing(false)
        setPadding(true)
        setWidthFull()
        addClassName("filter-bar")

        // Title
        val title = H4("Filters")
        title.addClassNames(
            Margin.NONE,
            Margin.Bottom.SMALL,
            TextColor.SECONDARY
        )

        // Configure filters container
        filtersContainer.addClassNames(
            Display.FLEX,
            FlexWrap.WRAP,
            Gap.MEDIUM,
            Width.FULL
        )

        // Configure category filter
        configureCategoryFilter()

        // Configure visited filter
        configureVisitedFilter()

        // Add components to filters container
        filtersContainer.add(categoryFilter, visitedFilter)

        // Add components to layout
        add(title, filtersContainer)
    }

    private fun configureCategoryFilter() {
        categoryFilter.setLabel("Filter by Category")
        categoryFilter.setPlaceholder("Select categories")
        categoryFilter.setItems(*Category.values())
        categoryFilter.setItemLabelGenerator { it.name.replace("_", " ").lowercase().capitalize() }
        categoryFilter.addValueChangeListener { event ->
            categoryChangeListener?.invoke(event.value)
        }
    }

    private fun configureVisitedFilter() {
        visitedFilter.setLabel("Show")
        visitedFilter.setItems("All", "Visited", "Not Visited")
        visitedFilter.value = "All"
        visitedFilter.addThemeVariants(RadioGroupVariant.LUMO_VERTICAL)
        visitedFilter.addValueChangeListener { event ->
            visitedChangeListener?.invoke(event.value)
        }
    }

    /**
     * Set a listener for category filter changes.
     */
    fun setCategoryChangeListener(listener: (Set<Category>) -> Unit) {
        categoryChangeListener = listener
    }

    /**
     * Set a listener for visited filter changes.
     */
    fun setVisitedChangeListener(listener: (String) -> Unit) {
        visitedChangeListener = listener
    }

    /**
     * Reset all filters to their default values.
     */
    fun resetFilters() {
        categoryFilter.clear()
        visitedFilter.value = "All"
    }
}