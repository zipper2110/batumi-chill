package com.litvin.batumichill.ui

import com.litvin.batumichill.service.TranslationService
import com.litvin.batumichill.ui.components.LanguageSelector
import com.vaadin.flow.component.applayout.AppLayout
import com.vaadin.flow.component.dependency.CssImport
import com.vaadin.flow.component.html.H1
import com.vaadin.flow.component.orderedlayout.FlexComponent
import com.vaadin.flow.component.orderedlayout.HorizontalLayout
import com.vaadin.flow.spring.annotation.SpringComponent
import com.vaadin.flow.spring.annotation.UIScope
import com.vaadin.flow.theme.lumo.LumoUtility.*
import org.springframework.beans.factory.annotation.Autowired

/**
 * The main layout of the application.
 * Side menu has been removed.
 */
@SpringComponent
@UIScope
@CssImport("./styles/main-layout.css")
class MainLayout @Autowired constructor(
    private val translationService: TranslationService
) : AppLayout() {

    init {
        // Create the header
        val logo = H1(translationService.getMessage("main.title"))
        logo.addClassNames(
            FontSize.XLARGE,
            Margin.NONE,
            TextColor.PRIMARY
        )

        // Create language selector
        val languageSelector = LanguageSelector()

        // Create header layout with logo and language selector
        val header = HorizontalLayout(logo, languageSelector)
        header.setDefaultVerticalComponentAlignment(FlexComponent.Alignment.CENTER)
        header.expand(logo) // Make logo take available space, pushing language selector to the right
        header.setWidthFull()
        header.addClassNames(
            Padding.MEDIUM,
            BoxSizing.BORDER
        )

        addToNavbar(header)
    }
}
