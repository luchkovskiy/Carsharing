package com.luchkovskiy.util;

import com.google.maps.DirectionsApi;
import com.google.maps.DistanceMatrixApi;
import com.google.maps.DistanceMatrixApiRequest;
import com.google.maps.GeoApiContext;
import com.google.maps.GeocodingApi;
import com.google.maps.model.DistanceMatrix;
import com.google.maps.model.DistanceMatrixElement;
import com.google.maps.model.DistanceMatrixRow;
import com.google.maps.model.GeocodingResult;
import com.google.maps.model.LatLng;
import com.google.maps.model.TravelMode;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.util.HashMap;
import java.util.Map;

@RequiredArgsConstructor
public class LocationManager {

    private final GeoApiContext context;

    public DistanceMatrix getRouteTime(String firstLocation, String secondLocation, TravelMode travelMode) {
        LatLng source = null;
        LatLng destination = null;
        try {
            source = getLatLng(firstLocation);
            destination = getLatLng(secondLocation);
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

    public Map<String, Float> getSessionInfo(String source, String destination) {
        Map<String, Float> map = new HashMap<>();
        DistanceMatrix route = getRouteTime(source, destination, TravelMode.DRIVING);
        DistanceMatrixRow[] rows = route.rows;
        for (DistanceMatrixRow row : rows) {
            DistanceMatrixElement[] elements = row.elements;
            for (DistanceMatrixElement element : elements) {
                float distance = element.distance.inMeters;
                map.put("distance", distance / 1000f);
                long duration = element.duration.inSeconds;
                map.put("duration", duration / 3600f);
            }
        }
        return map;
    }

    private LatLng getLatLng(String address) throws Exception {
        GeocodingResult[] results = GeocodingApi.geocode(context, address).await();
        return results[0].geometry.location;
    }

}
