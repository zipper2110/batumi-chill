package com.litvin.batumichill.ui.util

import com.litvin.batumichill.model.CoolnessRating

/**
 * Utility class for handling coolness rating display properties.
 */
object CoolnessRatingUtil {
    /**
     * Get the background color style for a coolness rating.
     */
    fun getBackgroundColorStyle(rating: CoolnessRating?): String {
        return when (rating) {
            CoolnessRating.MUST_SEE -> "var(--lumo-success-color-10pct)"
            CoolnessRating.COOL -> "var(--lumo-primary-color-10pct)"
            CoolnessRating.JUST_OK -> "var(--lumo-contrast-10pct)"
            null -> "var(--lumo-contrast-5pct)"
        }
    }

    /**
     * Get the text color style for a coolness rating.
     */
    fun getTextColorStyle(rating: CoolnessRating?): String {
        return when (rating) {
            CoolnessRating.MUST_SEE -> "var(--lumo-success-text-color)"
            CoolnessRating.COOL -> "var(--lumo-primary-text-color)"
            CoolnessRating.JUST_OK -> "var(--lumo-contrast)"
            null -> "var(--lumo-contrast-60pct)"
        }
    }

    /**
     * Get a human-readable display name for a coolness rating.
     */
    fun getDisplayName(rating: CoolnessRating?): String {
        return when (rating) {
            CoolnessRating.MUST_SEE -> "Must See"
            CoolnessRating.COOL -> "Cool"
            CoolnessRating.JUST_OK -> "Just OK"
            null -> "Not Rated"
        }
    }

    /**
     * Get an icon name for a coolness rating.
     */
    fun getIcon(rating: CoolnessRating?): String {
        return when (rating) {
            CoolnessRating.MUST_SEE -> "vaadin:star"
            CoolnessRating.COOL -> "vaadin:thumbs-up"
            CoolnessRating.JUST_OK -> "vaadin:hand"
            null -> "vaadin:question"
        }
    }
}