package com.luchkovskiy.util;

import com.google.maps.DirectionsApi;
import com.google.maps.DistanceMatrixApi;
import com.google.maps.DistanceMatrixApiRequest;
import com.google.maps.GeoApiContext;
import com.google.maps.GeocodingApi;
import com.google.maps.model.DistanceMatrix;
import com.google.maps.model.GeocodingResult;
import com.google.maps.model.LatLng;
import com.google.maps.model.TravelMode;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;

public class LocationManager {

    public DistanceMatrix getRouteTime(String firstLocation, String secondLocation, GeoApiContext context, TravelMode travelMode) {
        LatLng source = null;
        LatLng destination = null;
        try {
            source = getLatLng(firstLocation, context);
            destination = getLatLng(secondLocation, context);
        } catch (Exception e) {
            e.printStackTrace();
        }
        LocalDateTime time = LocalDateTime.now();
        ZoneId zone = ZoneId.of("Europe/Minsk");
        ZoneOffset zoneOffSet = zone.getRules().getOffset(time);
        try {
            DistanceMatrixApiRequest req = DistanceMatrixApi.newRequest(context);
            req.departureTime(time.toInstant(zoneOffSet));
            DirectionsApi.RouteRestriction tolls = DirectionsApi.RouteRestriction.TOLLS;
            return req.origins(source)
                    .destinations(destination)
                    .mode(travelMode)
                    .avoid(tolls)
                    .language("en-Us")
                    .await();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    private LatLng getLatLng(String address, GeoApiContext context) throws Exception {
        GeocodingResult[] results = GeocodingApi.geocode(context, address).await();
        return results[0].geometry.location;
    }

}
