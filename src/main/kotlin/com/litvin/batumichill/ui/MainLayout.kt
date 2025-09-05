package com.litvin.batumichill.ui

import com.vaadin.flow.component.applayout.AppLayout
import com.vaadin.flow.component.dependency.CssImport
import com.vaadin.flow.component.html.H1
import com.vaadin.flow.component.orderedlayout.FlexComponent
import com.vaadin.flow.component.orderedlayout.HorizontalLayout
import com.vaadin.flow.theme.lumo.LumoUtility.*

/**
 * The main layout of the application.
 * Side menu has been removed.
 */
@CssImport("./styles/main-layout.css")
class MainLayout : AppLayout() {

    init {
        // Create the header
        val logo = H1("Batumi Chill Guide")
        logo.addClassNames(
            FontSize.XLARGE,
            Margin.NONE,
            TextColor.PRIMARY
        )

        val header = HorizontalLayout(logo)
        header.setDefaultVerticalComponentAlignment(FlexComponent.Alignment.CENTER)
        header.setWidthFull()
        header.addClassNames(
            Padding.MEDIUM,
            BoxSizing.BORDER
        )

        addToNavbar(header)
    }
}
