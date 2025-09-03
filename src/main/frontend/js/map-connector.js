// Leaflet map connector for Vaadin
// This file handles the integration between Leaflet.js and Vaadin

// Import Leaflet
import L from 'leaflet';
import 'leaflet/dist/leaflet.css';

// Fix Leaflet's default icon paths
delete L.Icon.Default.prototype._getIconUrl;
L.Icon.Default.mergeOptions({
    iconRetinaUrl: 'https://unpkg.com/leaflet@1.9.4/dist/images/marker-icon-2x.png',
    iconUrl: 'https://unpkg.com/leaflet@1.9.4/dist/images/marker-icon.png',
    shadowUrl: 'https://unpkg.com/leaflet@1.9.4/dist/images/marker-shadow.png'
});

// Create a registry to store map and marker references
// This avoids circular references when the element is serialized
const mapRegistry = new Map();

// Log that the connector is loaded
console.log('Map connector loaded, Leaflet version:', L.version);

// Initialize the map
window.initMap = function(element, lat, lng) {
    console.log('Initializing map with element:', element, 'lat:', lat, 'lng:', lng);

    try {
        // Check if Leaflet is loaded
        if (!L) {
            console.error('Leaflet is not loaded!');
            return false;
        }

        console.log('Leaflet is loaded, creating map...');

        // Verify the element exists and has an ID
        if (!element || !element.id) {
            console.error('Map container element is invalid:', element);
            return false;
        }

        console.log('Map container element ID:', element.id);

        // Get the actual DOM element by ID to ensure we're targeting the right element
        const container = document.getElementById(element.id);
        if (!container) {
            console.error('Could not find map container element with ID:', element.id);
            return false;
        }

        console.log('Found map container DOM element:', container);

        // Ensure the container has dimensions
        const containerStyle = window.getComputedStyle(container);
        console.log('Container dimensions:', {
            width: containerStyle.width,
            height: containerStyle.height,
            position: containerStyle.position
        });

        // Create a map instance centered on Batumi
        console.log('Creating map with L.map()...');
        const map = L.map(element.id, {
            center: [lat, lng],
            zoom: 13,
            zoomControl: true
        });

        console.log('Map created successfully');

        // Add OpenStreetMap tile layer
        console.log('Adding tile layer...');
        L.tileLayer('https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png', {
            attribution: '&copy; <a href="https://www.openstreetmap.org/copyright">OpenStreetMap</a> contributors'
        }).addTo(map);

        console.log('Tile layer added to map');

        // Force a resize to ensure the map renders correctly
        setTimeout(() => {
            console.log('Forcing map invalidateSize()');
            map.invalidateSize(true);
        }, 100);

        // Store the map instance and markers array in the registry
        mapRegistry.set(element.id, {
            map: map,
            markers: []
        });

        console.log('Map initialization complete');

        // Return a simple boolean instead of the map object to avoid circular references
        return true;
    } catch (error) {
        console.error('Error initializing map:', error);
        console.error('Error stack:', error.stack);
        return false;
    }
};

// Update markers on the map
window.updateMarkers = function(element, markersData) {
    console.log('Updating markers with element ID:', element.id, 'markersData:', markersData);

    try {
        // Get the map and markers from the registry
        const mapData = mapRegistry.get(element.id);

        if (!mapData || !mapData.map) {
            console.error('Map not initialized for element ID:', element.id);
            return false;
        }

        const map = mapData.map;

        console.log('Map found, clearing existing markers');

        // Clear existing markers
        clearMarkers(element);

        console.log('Adding new markers, count:', markersData.length);

        // Add new markers
        for (let i = 0; i < markersData.length; i++) {
            const markerData = markersData[i];

            console.log('Creating marker for:', markerData.name, 'at position:', markerData.lat, markerData.lng);

            // Create marker
            const marker = L.marker([markerData.lat, markerData.lng]).addTo(map);

            // Add popup with location information
            marker.bindPopup(`
                <div class="marker-popup">
                    <h3>${markerData.name}</h3>
                    <p>Category: ${markerData.category}</p>
                    <p>Status: ${markerData.visited ? 'Visited' : 'Not visited'}</p>
                    <a href="/location/${markerData.id}">View details</a>
                </div>
            `);

            // Store marker reference in the registry
            mapData.markers.push(marker);
        }

        console.log('Markers update complete, total markers:', mapData.markers.length);

        // Return a simple boolean instead of any complex objects to avoid circular references
        return true;
    } catch (error) {
        console.error('Error updating markers:', error);
        return false;
    }
};

// Clear all markers from the map
window.clearMarkers = function(element) {
    console.log('Clearing markers for element ID:', element.id);

    try {
        // Get the map and markers from the registry
        const mapData = mapRegistry.get(element.id);

        if (!mapData || !mapData.map) {
            console.error('Map not initialized for element ID:', element.id);
            return false;
        }

        const map = mapData.map;
        const markers = mapData.markers;

        console.log('Map found, removing markers, count:', markers ? markers.length : 0);

        // Remove all markers
        if (markers) {
            for (let i = 0; i < markers.length; i++) {
                map.removeLayer(markers[i]);
            }
        }

        // Reset markers array
        mapData.markers = [];

        console.log('All markers cleared');

        // Return a simple boolean instead of any complex objects to avoid circular references
        return true;
    } catch (error) {
        console.error('Error clearing markers:', error);
        return false;
    }
};
