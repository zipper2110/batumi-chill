package com.litvin.batumichill.ui.components

import com.litvin.batumichill.model.Category
import com.litvin.batumichill.service.TranslationService
import com.vaadin.flow.component.combobox.MultiSelectComboBox
import com.vaadin.flow.component.dependency.CssImport
import com.vaadin.flow.component.orderedlayout.FlexLayout
import com.vaadin.flow.component.orderedlayout.VerticalLayout
import com.vaadin.flow.component.radiobutton.RadioButtonGroup
import com.vaadin.flow.component.radiobutton.RadioGroupVariant
import com.vaadin.flow.spring.annotation.SpringComponent
import com.vaadin.flow.spring.annotation.UIScope
import com.vaadin.flow.theme.lumo.LumoUtility.*
import org.springframework.beans.factory.annotation.Autowired

/**
 * A component that provides filtering options for locations.
 * Responsive design that adapts to different screen sizes.
 */
@SpringComponent
@UIScope
@CssImport("./styles/filter-bar.css")
class FilterBar @Autowired constructor(
    private val translationService: TranslationService
) : VerticalLayout() {

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
        add(filtersContainer)
    }

    private fun configureCategoryFilter() {
        categoryFilter.label = translationService.getMessage("filter.category.label")
        categoryFilter.placeholder = translationService.getMessage("filter.category.placeholder")
        categoryFilter.setItems(Category.entries)
        categoryFilter.setItemLabelGenerator { it.name.replace("_", " ").lowercase().capitalize() }
        categoryFilter.isAllowCustomValue = false
        categoryFilter.addValueChangeListener { event ->
            categoryChangeListener?.invoke(event.value)
        }
    }

    private fun configureVisitedFilter() {
        visitedFilter.label = translationService.getMessage("filter.visited.label")

        // Get translated items
        val allText = translationService.getMessage("filter.visited.all")
        val visitedText = translationService.getMessage("filter.visited.visited")
        val notVisitedText = translationService.getMessage("filter.visited.notVisited")

        visitedFilter.setItems(allText, visitedText, notVisitedText)
        visitedFilter.value = allText
        visitedFilter.addThemeVariants(RadioGroupVariant.LUMO_VERTICAL)
        visitedFilter.addValueChangeListener { event ->
            // Map the translated text back to the expected values in the MainView
            val value = when (event.value) {
                visitedText -> "Visited"
                notVisitedText -> "Not Visited"
                else -> "All"
            }
            visitedChangeListener?.invoke(value)
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
}
