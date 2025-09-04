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

// Function to create custom marker icons based on category and visited status
function createCustomMarkerIcon(category, visited) {
    // Define colors for different categories
    const categoryColors = {
        'RESTAURANT': '#FF5722',    // Orange-red
        'HISTORICAL_SITE': '#8D6E63', // Brown
        'PARK': '#4CAF50',          // Green
        'BEACH': '#03A9F4',         // Light blue
        'CAFE': '#795548',          // Brown
        'MUSEUM': '#9C27B0',        // Purple
        'VIEWPOINT': '#3F51B5',     // Indigo
        'SHOPPING': '#E91E63',      // Pink
        'ENTERTAINMENT': '#FFC107'  // Amber
    };

    // Default color if category not found
    const color = categoryColors[category] || '#2196F3'; // Default blue

    // Adjust opacity based on visited status
    const opacity = visited ? 0.7 : 1.0;

    // Create custom icon
    return L.divIcon({
        className: 'custom-map-marker',
        html: `<div style="
            background-color: ${color}; 
            opacity: ${opacity};
            width: 24px; 
            height: 24px; 
            border-radius: 50%; 
            border: 2px solid white;
            box-shadow: 0 0 4px rgba(0,0,0,0.3);
            display: flex;
            align-items: center;
            justify-content: center;
            color: white;
            font-size: 12px;
            font-weight: bold;
        ">
            ${getIconForCategory(category)}
        </div>`,
        iconSize: [28, 28],
        iconAnchor: [14, 14],
        popupAnchor: [0, -14]
    });
}

// Helper function to get icon character for category
function getIconForCategory(category) {
    const categoryIcons = {
        'RESTAURANT': '🍽️',
        'HISTORICAL_SITE': '🏛️',
        'PARK': '🌳',
        'BEACH': '🏖️',
        'CAFE': '☕',
        'MUSEUM': '🏛️',
        'VIEWPOINT': '🌄',
        'SHOPPING': '🛍️',
        'ENTERTAINMENT': '🎭'
    };

    return categoryIcons[category] || '';
}

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

        // Create a map instance centered on Batumi with enhanced options
        console.log('Creating map with L.map()...');
        const map = L.map(element.id, {
            center: [lat, lng],
            zoom: 13,
            zoomControl: false,  // We'll add zoom control manually for better positioning
            scrollWheelZoom: true,
            doubleClickZoom: true,
            dragging: true,
            zoomAnimation: true,
            fadeAnimation: true,
            markerZoomAnimation: true
        });

        // Add zoom control to the top-right corner
        L.control.zoom({
            position: 'topright'
        }).addTo(map);

        // We no longer add the legend as a Leaflet control
        // It will be added below the map instead

        console.log('Map created successfully');

        // Add a more visually appealing map tile layer
        console.log('Adding tile layer...');
        L.tileLayer('https://{s}.tile.thunderforest.com/atlas/{z}/{x}/{y}.png?apikey=6170aad10dfd42a38d4d8c709a536f38', {
            attribution: '&copy; <a href="https://www.thunderforest.com/">Thunderforest</a>, &copy; <a href="https://www.openstreetmap.org/copyright">OpenStreetMap</a> contributors',
            maxZoom: 22
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

            // Create custom marker based on category
            const markerIcon = createCustomMarkerIcon(markerData.category, markerData.visited);
            const marker = L.marker([markerData.lat, markerData.lng], { icon: markerIcon }).addTo(map);

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

// Center the map on Batumi
window.centerMap = function(element) {
    console.log('Centering map for element ID:', element.id);

    try {
        // Get the map from the registry
        const mapData = mapRegistry.get(element.id);

        if (!mapData || !mapData.map) {
            console.error('Map not initialized for element ID:', element.id);
            return false;
        }

        const map = mapData.map;

        // Center the map on Batumi
        map.setView([41.6168, 41.6367], 13);
        console.log('Map centered on Batumi');

        return true;
    } catch (error) {
        console.error('Error centering map:', error);
        return false;
    }
};
