package com.litvin.batumichill.ui.util

import com.litvin.batumichill.model.Category

/**
 * Utility class for getting consistent colors for different categories.
 */
object CategoryColorUtil {

    /**
     * Get the background color style for a category.
     */
    fun getBackgroundColorStyle(category: Category): String {
        return when (category) {
            Category.RESTAURANT -> "var(--lumo-success-color-10pct)"
            Category.HISTORICAL_SITE -> "var(--lumo-error-color-10pct)"
            Category.PARK -> "var(--lumo-success-color-50pct)"
            Category.BEACH -> "var(--lumo-primary-color-10pct)"
            Category.CAFE -> "var(--lumo-contrast-20pct)"
            Category.MUSEUM -> "var(--lumo-error-color-50pct)"
            Category.VIEWPOINT -> "var(--lumo-primary-color-50pct)"
            Category.SHOPPING -> "var(--lumo-contrast-5pct)"
            Category.ENTERTAINMENT -> "var(--lumo-success-color-10pct)"
        }
    }

    /**
     * Get the text color style for a category.
     */
    fun getTextColorStyle(category: Category): String {
        return when (category) {
            Category.RESTAURANT -> "var(--lumo-success-text-color)"
            Category.HISTORICAL_SITE -> "var(--lumo-error-text-color)"
            Category.PARK -> "var(--lumo-success-contrast-color)"
            Category.BEACH -> "var(--lumo-primary-text-color)"
            Category.CAFE -> "var(--lumo-body-text-color)"
            Category.MUSEUM -> "var(--lumo-error-contrast-color)"
            Category.VIEWPOINT -> "var(--lumo-primary-contrast-color)"
            Category.SHOPPING -> "var(--lumo-secondary-text-color)"
            Category.ENTERTAINMENT -> "var(--lumo-success-text-color)"
        }
    }

    /**
     * Get a map of category to icon name.
     */
    fun getCategoryIcon(category: Category): String {
        return when (category) {
            Category.RESTAURANT -> "utensils"
            Category.HISTORICAL_SITE -> "landmark"
            Category.PARK -> "tree"
            Category.BEACH -> "umbrella-beach"
            Category.CAFE -> "coffee"
            Category.MUSEUM -> "university"
            Category.VIEWPOINT -> "mountain"
            Category.SHOPPING -> "shopping-bag"
            Category.ENTERTAINMENT -> "ticket"
        }
    }
}