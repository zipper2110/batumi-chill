package com.litvin.batumichill.ui

import com.vaadin.flow.component.applayout.AppLayout
import com.vaadin.flow.component.applayout.DrawerToggle
import com.vaadin.flow.component.html.H1
import com.vaadin.flow.component.html.Span
import com.vaadin.flow.component.orderedlayout.FlexComponent
import com.vaadin.flow.component.orderedlayout.HorizontalLayout
import com.vaadin.flow.component.tabs.Tab
import com.vaadin.flow.component.tabs.Tabs
import com.vaadin.flow.router.PageTitle
import com.vaadin.flow.router.RouterLink
import com.vaadin.flow.theme.lumo.LumoUtility.*

/**
 * The main layout of the application with a responsive navigation menu.
 */
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
        tabs.add(
            createTab("Locations", MainView::class.java),
            createTab("About", MainView::class.java) // Placeholder for future About page
        )
        tabs.orientation = Tabs.Orientation.VERTICAL
        return tabs
    }

    private fun createTab(text: String, navigationTarget: Class<out com.vaadin.flow.component.Component>): Tab {
        val tab = Tab()
        val link = RouterLink()
        link.add(Span(text))
        link.setRoute(navigationTarget)
        link.addClassNames(
            Display.FLEX,
            AlignItems.CENTER,
            Padding.Horizontal.MEDIUM,
            Padding.Vertical.XSMALL,
            TextColor.BODY
        )
        tab.add(link)
        return tab
    }
}
