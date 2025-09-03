package com.litvin.batumichill.ui.util

import com.litvin.batumichill.model.Category
import com.vaadin.flow.theme.lumo.LumoUtility.Background
import com.vaadin.flow.theme.lumo.LumoUtility.TextColor

/**
 * Utility class for getting consistent colors for different categories.
 */
object CategoryColorUtil {

    /**
     * Get the background color class for a category.
     */
    fun getBackgroundColorClass(category: Category): String {
        return when (category) {
            Category.RESTAURANT -> Background.SUCCESS_10
            Category.HISTORICAL_SITE -> Background.ERROR_10
            Category.PARK -> Background.SUCCESS_50
            Category.BEACH -> Background.PRIMARY_10
            Category.CAFE -> Background.CONTRAST_20
            Category.MUSEUM -> Background.ERROR_50
            Category.VIEWPOINT -> Background.PRIMARY_50
            Category.SHOPPING -> Background.CONTRAST_5
            Category.ENTERTAINMENT -> Background.SUCCESS_10
        }
    }

    /**
     * Get the text color class for a category.
     */
    fun getTextColorClass(category: Category): String {
        return when (category) {
            Category.RESTAURANT -> TextColor.SUCCESS
            Category.HISTORICAL_SITE -> TextColor.ERROR
            Category.PARK -> TextColor.SUCCESS_CONTRAST
            Category.BEACH -> TextColor.PRIMARY
            Category.CAFE -> TextColor.BODY
            Category.MUSEUM -> TextColor.ERROR_CONTRAST
            Category.VIEWPOINT -> TextColor.PRIMARY_CONTRAST
            Category.SHOPPING -> TextColor.SECONDARY
            Category.ENTERTAINMENT -> TextColor.SUCCESS
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