import java.util.*;
import java.util.stream.Collectors;
import java.lang.reflect.Array;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
//import org.json.JSONArray;
//import org.json.JSONException;
//import org.json.JSONObject;



public class DistanceCalculator {

    private static final double EARTH_RADIUS_KM = 6371.01; // Earth's radius in kilometers

    // HashMap to store city names and their corresponding postal codes
    public static HashMap<String, String> cityPostalCodeMap;

    public DistanceCalculator() {
    	Scanner input = new Scanner(System.in);
        cityPostalCodeMap = new HashMap<String, String>();
        System.out.print("Geben Sie die Postleitzahl an: ");
        String plz = input.nextLine();
        System.out.print("Geben Sie die Stadt an: ");
        String stadt = input.nextLine();
        
        // Add some sample city names and their postal codes
        while (cityPostalCodeMap.isEmpty()) {
        cityPostalCodeMap.put(stadt,plz);
    }
    }

    public String getPostalCode(String city) {
        return cityPostalCodeMap.get(city);
    }

    public double getDistance(double lat1, double lon1, double lat2, double lon2) {
        double dLat = Math.toRadians(lat2 - lat1);
        double dLon = Math.toRadians(lon2 - lon1);
        double a = Math.sin(dLat / 2) * Math.sin(dLat / 2) +
                   Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2)) *
                   Math.sin(dLon / 2) * Math.sin(dLon / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        return EARTH_RADIUS_KM * c;
    }

    public static void main(String[] args) {
        DistanceCalculator cityPostalCode = new DistanceCalculator();
        
        for (String key : cityPostalCodeMap.keySet()) {
            String value = cityPostalCodeMap.get(key);
            if (value instanceof String) {
                System.out.println(key + ": " + value);
            }
        }
        // Get the postal code for a city
//        String newYorkPostalCode = cityPostalCode.getPostalCode("New York");
//        System.out.println("Postal code for New York: " + newYorkPostalCode);
//
//        // Calculate the distance between two cities
//        double newYorkLat = 40.7128;
//        double newYorkLon = -74.0060;
//        double losAngelesLat = 34.0522;
//        double losAngelesLon = -118.2437;
//        double distance = cityPostalCode.getDistance(newYorkLat, newYorkLon, losAngelesLat, losAngelesLon);
//        System.out.printf("Distance between New York and Los Angeles: %.2f km", distance);
    }
}


//public class DistanceCalculator {
//
//    private static final String API_KEY = "your-api-key";
//    private static final String ENDPOINT = "https://api.openrouteservice.org/v2/directions/driving-car";
//
//    public static void main(String[] args) {
//
//        // Define the two cities you want to calculate the distance between
//        String city1 = "Berlin";
//        String city2 = "Munich";
//
//        // Use the API to get the latitude and longitude coordinates of the two cities
//        double[] city1Coords = geocodeCity(city1);
//        double[] city2Coords = geocodeCity(city2);
//
//        // Use the API to calculate the driving distance between the two cities
//        double distance = calculateDistance(city1Coords, city2Coords);
//
//        // Print the distance in kilometers
//        System.out.printf("The driving distance between %s and %s is %.2f km.", city1, city2, distance / 1000);
//    }
//
//    public static double[] geocodeCity(String city) {
//        try {
//            // Set up the API endpoint
//            String endpoint = "https://api.openrouteservice.org/geocode/search";
//            String urlString = String.format("%s?api_key=%s&text=%s,Germany", endpoint, API_KEY, city);
//
//            // Send a HTTP request to the API and retrieve the response
//            URL url = new URL(urlString);
//            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
//            conn.setRequestMethod("GET");
//            conn.setRequestProperty("Accept", "application/json");
//            Scanner scanner = new Scanner(conn.getInputStream());
//            String responseString = scanner.useDelimiter("\\A").next();
//            scanner.close();
//            conn.disconnect();
//
//            // Parse the JSON response and extract the latitude and longitude coordinates
//            JSONObject responseJson = new JSONObject(responseString);
//            JSONArray features = responseJson.getJSONArray("features");
//            JSONObject firstFeature = features.getJSONObject(0);
//            JSONArray coordinates = firstFeature.getJSONObject("geometry").getJSONArray("coordinates");
//            double[] coords = new double[2];
//            coords[0] = coordinates.getDouble(0);
//            coords[1] = coordinates.getDouble(1);
//            return coords;
//        } catch (IOException e) {
//            e.printStackTrace();
//            return null;
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//
//    }
//
//    private static double calculateDistance(double[] coords1, double[] coords2) {
//        try {
//            // Set up the API parameters
//            String urlString = String.format("%s?api_key=%s&start=%s,%s&end=%s,%s", ENDPOINT, API_KEY,
//                    coords1[1], coords1[0], coords2[1], coords2[0]);
//
//            // Send a HTTP request to the API and retrieve the response
//            URL url = new URL(urlString);
//            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
//            conn.setRequestMethod("GET");
//            conn.setRequestProperty("Accept", "application/json");
//            Scanner scanner = new Scanner(conn.getInputStream());
//            String responseString = scanner.useDelimiter("\\A").next();
//            scanner.close();
//            conn.disconnect();
//
//            // Parse the JSON response and extract the distance
//            JSONObject responseJson = new JSONObject(responseString);
//            double distance = responseJson.getJSONArray("features").getJSONObject(0)
//                    .getJSONObject("properties").getDouble("segments");
//            return distance;
//
//        } catch (IOException e) {
//            e.printStackTrace();
//            return -1;
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//        return 0;
//    }
//}

