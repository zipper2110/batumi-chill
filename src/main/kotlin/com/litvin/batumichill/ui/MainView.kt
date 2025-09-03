package com.litvin.batumichill.ui

import com.vaadin.flow.component.html.H1
import com.vaadin.flow.component.html.Paragraph
import com.vaadin.flow.component.orderedlayout.FlexComponent
import com.vaadin.flow.component.orderedlayout.VerticalLayout
import com.vaadin.flow.router.Route
import com.vaadin.flow.theme.lumo.LumoUtility.*

@Route("") 
class MainView : VerticalLayout() {

    init {
        addClassName(Padding.LARGE)
        setWidthFull()
        setJustifyContentMode(FlexComponent.JustifyContentMode.CENTER)
        setAlignItems(FlexComponent.Alignment.CENTER)

        val header = H1("Hello Batumi!")
        header.addClassNames(TextColor.PRIMARY, FontSize.XXXLARGE, Margin.NONE)

        val description = Paragraph("Your personal guide to exploring the beautiful coastal city of Batumi, Georgia")
        description.addClassNames(Margin.Top.MEDIUM, TextColor.SECONDARY, MaxWidth.SCREEN_MEDIUM)

        add(header, description)
    }
}
