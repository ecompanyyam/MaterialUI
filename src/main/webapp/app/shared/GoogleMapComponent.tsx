import React, { useEffect, useRef } from 'react';

interface GoogleMapComponentProps {
  location: string;
  selectedCoordinates: { latitude: number; longitude: number } | null;
  setSelectedCoordinates: React.Dispatch<React.SetStateAction<{ latitude: number; longitude: number } | null>>;
}

declare global {
  interface Window {
    google: any;
  }
}

const GoogleMapComponent: React.FC<GoogleMapComponentProps> = ({ location, selectedCoordinates }) => {
  const mapRef = useRef<HTMLDivElement | null>(null);
  const markerRef = useRef<any>(null); // Ref to the marker

  useEffect(() => {
    if (mapRef.current && window.google) {
      const [lat, lng] = location.split(',').map(parseFloat);
      const defaultLat = 37.7749; // Default latitude (e.g., San Francisco)
      const defaultLng = -122.4194; // Default longitude (e.g., San Francisco)

      const mapOptions = {
        center: { lat: isNaN(lat) ? defaultLat : lat, lng: isNaN(lng) ? defaultLng : lng },
        zoom: 12,
      };

      const map = new window.google.maps.Map(mapRef.current, mapOptions);

      // Create the marker if selectedCoordinates is available
      if (selectedCoordinates) {
        markerRef.current = new window.google.maps.Marker({
          position: selectedCoordinates,
          map,
        });
      }
    }
  }, [location, selectedCoordinates]);

  useEffect(() => {
    // Update marker position if selectedCoordinates change
    if (markerRef.current && selectedCoordinates) {
      markerRef.current.setPosition(selectedCoordinates);
    }
  }, [selectedCoordinates]);

  return <div style={{ height: '200px' }} ref={mapRef} />;
};

export default GoogleMapComponent;
