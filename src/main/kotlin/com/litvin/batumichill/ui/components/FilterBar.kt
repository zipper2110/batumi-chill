package com.litvin.batumichill.ui.components

import com.litvin.batumichill.model.Category
import com.vaadin.flow.component.button.Button
import com.vaadin.flow.component.button.ButtonVariant
import com.vaadin.flow.component.combobox.MultiSelectComboBox
import com.vaadin.flow.component.icon.Icon
import com.vaadin.flow.component.icon.VaadinIcon
import com.vaadin.flow.component.orderedlayout.FlexComponent
import com.vaadin.flow.component.orderedlayout.HorizontalLayout
import com.vaadin.flow.component.radiobutton.RadioButtonGroup
import com.vaadin.flow.component.radiobutton.RadioGroupVariant
import com.vaadin.flow.theme.lumo.LumoUtility.*

/**
 * A component that provides filtering options for locations.
 */
class FilterBar : HorizontalLayout() {

    private val categoryFilter = MultiSelectComboBox<Category>()
    private val visitedFilter = RadioButtonGroup<String>()
    private val clearFiltersButton = Button("Clear Filters")
    
    // Event listeners
    private var categoryChangeListener: ((Set<Category>) -> Unit)? = null
    private var visitedChangeListener: ((String) -> Unit)? = null
    private var clearFiltersListener: (() -> Unit)? = null

    init {
        addClassNames(
            Background.CONTRAST_5,
            BorderRadius.LARGE,
            BoxShadow.SMALL,
            Padding.MEDIUM,
            Margin.Bottom.MEDIUM,
            Width.FULL
        )
        setDefaultVerticalComponentAlignment(FlexComponent.Alignment.CENTER)
        setJustifyContentMode(FlexComponent.JustifyContentMode.BETWEEN)
        setWidthFull()
        
        // Configure category filter
        configureCategoryFilter()
        
        // Configure visited filter
        configureVisitedFilter()
        
        // Configure clear filters button
        configureClearFiltersButton()
        
        // Add components to layout
        add(categoryFilter, visitedFilter, clearFiltersButton)
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

    private fun configureClearFiltersButton() {
        clearFiltersButton.addThemeVariants(ButtonVariant.LUMO_TERTIARY)
        clearFiltersButton.icon = Icon(VaadinIcon.CLOSE_CIRCLE)
        clearFiltersButton.addClickListener {
            // Clear all filters
            categoryFilter.clear()
            visitedFilter.value = "All"
            
            // Notify listeners
            clearFiltersListener?.invoke()
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
     * Set a listener for clear filters button clicks.
     */
    fun setClearFiltersListener(listener: () -> Unit) {
        clearFiltersListener = listener
    }

    /**
     * Reset all filters to their default values.
     */
    fun resetFilters() {
        categoryFilter.clear()
        visitedFilter.value = "All"
    }
}