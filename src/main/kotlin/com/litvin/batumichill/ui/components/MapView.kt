package com.litvin.batumichill.ui.components

import com.litvin.batumichill.model.Location
import com.vaadin.flow.component.AttachEvent
import com.vaadin.flow.component.Tag
import com.vaadin.flow.component.UI
import com.vaadin.flow.component.dependency.CssImport
import com.vaadin.flow.component.dependency.JsModule
import com.vaadin.flow.component.dependency.NpmPackage
import com.vaadin.flow.component.html.Div
import com.vaadin.flow.component.orderedlayout.VerticalLayout
import elemental.json.Json

@Tag("div")
@NpmPackage(value = "leaflet", version = "1.9.4")
@JsModule("./js/map-connector.js")
@CssImport("leaflet/dist/leaflet.css")
@CssImport("./styles/map-view.css")
class MapView : VerticalLayout() {

    private var mapInitialized = false
    private val mapContainer = Div()

    init {
        // Remove padding and spacing
        setPadding(false)
        setSpacing(false)

        // Configure map container
        mapContainer.element.setAttribute("id", "map-container")
        mapContainer.style.set("height", "600px")
        mapContainer.style.set("width", "100%")
        mapContainer.style.set("border", "2px solid #ccc")
        mapContainer.style.set("position", "relative")
        mapContainer.style.set("background-color", "#f5f5f5")

        // Add container to layout
        add(mapContainer)

        // Set the component's own display style to ensure it's properly handled in the layout
        style.set("display", "flex")
        style.set("flex-direction", "column")

        println("MapView initialized with map ID: ${mapContainer.element.getAttribute("id")}")
    }

    override fun onAttach(attachEvent: AttachEvent) {
        super.onAttach(attachEvent)
        println("MapView attached to DOM")

        // Schedule initialization after a short delay to ensure the DOM is fully rendered
        attachEvent.ui.access {
            attachEvent.ui.page.executeJs("setTimeout(function() { console.log('Delayed map initialization'); }, 100)")

            // Initialize the map if it's visible
            if (style.get("display") != "none") {
                println("MapView is visible, initializing map")
                initializeMap()
            } else {
                println("MapView is not visible, skipping initialization")
            }
        }
    }

    fun initializeMap() {
        if (!mapInitialized) {
            // Log that we're initializing the map
            println("Initializing map with element ID: ${mapContainer.element.getAttribute("id")}")

            // Initialize the map centered on Batumi
            val page = UI.getCurrent().page

            // Add a callback to log any JavaScript errors
            page.executeJs("try { return initMap($0, $1, $2); } catch(e) { console.error('Error in initMap:', e); return false; }", mapContainer.element, 41.6168, 41.6367)
                .then { result ->
                    println("Map initialization result: $result")
                }

            mapInitialized = true
        } else {
            println("Map already initialized")
        }
    }


    fun updateMarkers(locations: List<Location>) {
        println("Updating markers with ${locations.size} locations")

        val markersArray = Json.createArray()

        locations.forEachIndexed { index, location ->
            println("Adding marker for location: ${location.name} at ${location.latitude}, ${location.longitude}")

            val marker = Json.createObject()
            marker.put("id", location.id.toString())
            marker.put("lat", location.latitude)
            marker.put("lng", location.longitude)
            marker.put("name", location.name)
            marker.put("category", location.category.name)
            marker.put("visited", location.visited)

            markersArray.set(index, marker)
        }

        println("Sending ${markersArray.length()} markers to JavaScript")

        UI.getCurrent().page.executeJs("try { updateMarkers($0, $1); return true; } catch(e) { console.error('Error in updateMarkers:', e); return false; }", mapContainer.element, markersArray)
            .then { result ->
                println("Markers update result: $result")
            }
    }

    fun clearMarkers() {
        println("Clearing markers")

        UI.getCurrent().page.executeJs("try { clearMarkers($0); return true; } catch(e) { console.error('Error in clearMarkers:', e); return false; }", mapContainer.element)
            .then { result ->
                println("Markers clear result: $result")
            }
    }

}
