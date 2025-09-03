package com.litvin.batumichill.ui

import com.vaadin.flow.component.applayout.AppLayout
import com.vaadin.flow.component.applayout.DrawerToggle
import com.vaadin.flow.component.dependency.CssImport
import com.vaadin.flow.component.html.H1
import com.vaadin.flow.component.html.Span
import com.vaadin.flow.component.icon.VaadinIcon
import com.vaadin.flow.component.orderedlayout.FlexComponent
import com.vaadin.flow.component.orderedlayout.HorizontalLayout
import com.vaadin.flow.component.tabs.Tab
import com.vaadin.flow.component.tabs.Tabs
import com.vaadin.flow.router.RouterLink
import com.vaadin.flow.theme.lumo.LumoUtility.*

/**
 * The main layout of the application with a responsive navigation menu.
 * Enhanced with better styling and navigation.
 */
@CssImport("./styles/main-layout.css")
class MainLayout : AppLayout() {

    private val tabs: Tabs

    init {
        // Create the header
        val logo = H1("Batumi Chill Guide")
        logo.addClassNames(
            FontSize.XLARGE,
            Margin.NONE,
            TextColor.PRIMARY
        )

        val toggle = DrawerToggle()

        val header = HorizontalLayout(toggle, logo)
        header.setDefaultVerticalComponentAlignment(FlexComponent.Alignment.CENTER)
        header.setWidthFull()
        header.addClassNames(
            Padding.MEDIUM,
            BoxSizing.BORDER
        )

        addToNavbar(header)

        // Create the navigation tabs
        tabs = createTabs()
        addToDrawer(tabs)
    }

    private fun createTabs(): Tabs {
        val tabs = Tabs()
        tabs.addClassNames("navigation-tabs")
        tabs.add(
            createTab("Locations", VaadinIcon.MAP_MARKER, MainView::class.java, true),
            createTab("About", VaadinIcon.INFO_CIRCLE, MainView::class.java) // Placeholder for future About page
        )
        tabs.orientation = Tabs.Orientation.VERTICAL
        return tabs
    }

    private fun createTab(text: String, icon: VaadinIcon, navigationTarget: Class<out com.vaadin.flow.component.Component>, selected: Boolean = false): Tab {
        val tab = Tab()
        val link = RouterLink()

        // Create icon
        val iconElement = icon.create()
        iconElement.addClassNames(
            IconSize.SMALL,
            Margin.Right.SMALL,
            TextColor.SECONDARY
        )

        // Create text
        val textSpan = Span(text)
        textSpan.addClassNames(
            FontWeight.MEDIUM,
            FontSize.SMALL
        )

        // Add icon and text to link
        link.add(iconElement, textSpan)
        link.setRoute(navigationTarget)
        link.addClassNames(
            Display.FLEX,
            AlignItems.CENTER,
            Padding.Horizontal.MEDIUM,
            Padding.Vertical.SMALL,
            TextColor.BODY,
            "nav-link"
        )

        if (selected) {
            link.addClassNames(TextColor.PRIMARY, Background.PRIMARY_10)
            iconElement.addClassNames(TextColor.PRIMARY)
        }

        tab.add(link)
        return tab
    }
}
