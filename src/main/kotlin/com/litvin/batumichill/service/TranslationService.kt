package com.litvin.batumichill.service

import org.springframework.context.MessageSource
import org.springframework.context.i18n.LocaleContextHolder
import org.springframework.stereotype.Service
import java.util.*

/**
 * Service for handling translations in the application.
 */
@Service
class TranslationService(private val messageSource: MessageSource) {

    /**
     * Get a translated message for the given key using the current locale.
     *
     * @param key The message key
     * @param args Optional arguments for message formatting
     * @return The translated message
     */
    fun getMessage(key: String, vararg args: Any): String {
        return messageSource.getMessage(key, args, LocaleContextHolder.getLocale())
    }

    /**
     * Get a translated message for the given key using the specified locale.
     *
     * @param key The message key
     * @param locale The locale to use
     * @param args Optional arguments for message formatting
     * @return The translated message
     */
    fun getMessage(key: String, locale: Locale, vararg args: Any): String {
        return messageSource.getMessage(key, args, locale)
    }

    /**
     * Get the current locale from the LocaleContextHolder.
     *
     * @return The current locale
     */
    fun getCurrentLocale(): Locale {
        return LocaleContextHolder.getLocale()
    }
}