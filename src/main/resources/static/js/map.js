/**
 * KGEC Campus Bus — Leaflet Map Initialization
 * Renders bus stops as numbered markers with a route polyline.
 */
document.addEventListener('DOMContentLoaded', function () {
    const mapContainer = document.getElementById('map-container');
    if (!mapContainer || typeof busStops === 'undefined' || !busStops.length) return;

    // Center map on Kalyani area
    const centerLat = busStops.reduce((s, st) => s + st.latitude, 0) / busStops.length;
    const centerLng = busStops.reduce((s, st) => s + st.longitude, 0) / busStops.length;

    const map = L.map('map-container', {
        scrollWheelZoom: true,
        zoomControl: true
    }).setView([centerLat, centerLng], 14);

    // Dark-themed tile layer (CartoDB Dark Matter)
    L.tileLayer('https://{s}.basemaps.cartocdn.com/dark_all/{z}/{x}/{y}{r}.png', {
        attribution: '&copy; <a href="https://www.openstreetmap.org/">OpenStreetMap</a> &copy; <a href="https://carto.com/">CARTO</a>',
        subdomains: 'abcd',
        maxZoom: 19
    }).addTo(map);

    // Route coordinates for polyline
    const routeCoords = [];

    // Add markers for each stop
    busStops.forEach(function (stop, index) {
        const coords = [stop.latitude, stop.longitude];
        routeCoords.push(coords);

        // Custom numbered circle marker
        const marker = L.circleMarker(coords, {
            radius: 12,
            fillColor: '#6366f1',
            color: '#a78bfa',
            weight: 2,
            opacity: 1,
            fillOpacity: 0.9
        }).addTo(map);

        // Number label on marker
        const numberIcon = L.divIcon({
            className: 'stop-marker-label',
            html: '<div style="' +
                'width:26px;height:26px;' +
                'background:linear-gradient(135deg,#6366f1,#a78bfa);' +
                'border-radius:50%;' +
                'display:flex;align-items:center;justify-content:center;' +
                'color:white;font-weight:700;font-size:12px;' +
                'font-family:Inter,sans-serif;' +
                'box-shadow:0 0 12px rgba(99,102,241,0.5);' +
                'border:2px solid rgba(255,255,255,0.3);' +
                '">' + (index + 1) + '</div>',
            iconSize: [26, 26],
            iconAnchor: [13, 13]
        });

        L.marker(coords, { icon: numberIcon })
            .addTo(map)
            .bindPopup(
                '<div style="text-align:center;padding:4px;">' +
                '<strong style="font-size:14px;">' + stop.name + '</strong><br/>' +
                '<span style="color:#94a3b8;font-size:12px;">Stop #' + (index + 1) + '</span><br/>' +
                '<span style="color:#64748b;font-size:11px;">' +
                stop.latitude.toFixed(4) + ', ' + stop.longitude.toFixed(4) +
                '</span></div>'
            );
    });

    // Draw route polyline
    if (routeCoords.length > 1) {
        L.polyline(routeCoords, {
            color: '#6366f1',
            weight: 3,
            opacity: 0.7,
            dashArray: '8, 8',
            lineCap: 'round'
        }).addTo(map);
    }

    // Fit map to all markers
    const group = L.featureGroup(
        routeCoords.map(function (c) { return L.marker(c); })
    );
    map.fitBounds(group.getBounds().pad(0.15));
});
