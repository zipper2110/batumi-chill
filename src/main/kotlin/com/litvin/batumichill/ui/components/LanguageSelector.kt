package com.litvin.batumichill.ui.components

import com.vaadin.flow.component.UI
import com.vaadin.flow.component.button.Button
import com.vaadin.flow.component.button.ButtonVariant
import com.vaadin.flow.component.orderedlayout.HorizontalLayout
import com.vaadin.flow.server.VaadinServletRequest
import org.springframework.web.servlet.LocaleResolver
import org.springframework.web.servlet.support.RequestContextUtils
import java.util.*

/**
 * A component for selecting the application language.
 */
class LanguageSelector : HorizontalLayout() {

    private val englishButton: Button
    private val russianButton: Button

    init {
        setSpacing(true)
        setMargin(false)
        setPadding(false)
        
        // Create language buttons
        englishButton = createLanguageButton("EN", Locale.ENGLISH)
        russianButton = createLanguageButton("RU", Locale("ru"))
        
        // Add buttons to layout
        add(englishButton, russianButton)
        
        // Update button states based on current locale
        updateButtonStates()
    }
    
    /**
     * Create a button for a specific language.
     */
    private fun createLanguageButton(label: String, locale: Locale): Button {
        val button = Button(label)
        button.addThemeVariants(ButtonVariant.LUMO_SMALL)
        
        button.addClickListener {
            // Change the locale
            changeLocale(locale)
            
            // Update button states
            updateButtonStates()
            
            // Reload the page to apply the language change
            UI.getCurrent().page.reload()
        }
        
        return button
    }
    
    /**
     * Change the application locale.
     */
    private fun changeLocale(locale: Locale) {
        val request = VaadinServletRequest.getCurrent()
        val localeResolver: LocaleResolver = RequestContextUtils.getLocaleResolver(request)!!
        localeResolver.setLocale(request, null, locale)
    }
    
    /**
     * Update the visual state of the language buttons based on the current locale.
     */
    private fun updateButtonStates() {
        val currentLocale = getCurrentLocale()
        
        // Highlight the active language button
        englishButton.removeThemeVariants(ButtonVariant.LUMO_PRIMARY)
        russianButton.removeThemeVariants(ButtonVariant.LUMO_PRIMARY)
        
        if (currentLocale.language == "ru") {
            russianButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY)
        } else {
            englishButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY)
        }
    }
    
    /**
     * Get the current locale from the request.
     */
    private fun getCurrentLocale(): Locale {
        val request = VaadinServletRequest.getCurrent()
        return RequestContextUtils.getLocale(request) ?: Locale.ENGLISH
    }
}