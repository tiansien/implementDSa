import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class single {
    private static final String API_KEY = "api";
    public static float distances;
    public static float times;
    public static String cities = "Kuala Lumpur";


    //downloading the data
    public static String getData(String source, String destination) throws Exception {
        var url = "https://maps.googleapis.com/maps/api/distancematrix/json?origins=" + source + "&destinations=" + destination + "&key=" + API_KEY;
        var request = HttpRequest.newBuilder().GET().uri(URI.create(url)).build();
        var client = HttpClient.newBuilder().build();
        var response = client.send(request, HttpResponse.BodyHandlers.ofString()).body();
//        System.out.println(response);
        return response;

    }

    public static void getdistancentime() throws Exception {
        System.out.println();
        long distance = -1L;
        long time = -1L;

        //parsing json data and updating data
        JSONParser jp = new JSONParser();
        JSONObject jo = (JSONObject) jp.parse(getData("KualaLumpur", "Perak"));
        JSONArray ja = (JSONArray) jo.get("rows");
        jo = (JSONObject) ja.get(0);
        ja = (JSONArray) jo.get("elements");
        jo = (JSONObject) ja.get(0);
        JSONObject je = (JSONObject) jo.get("distance");
        JSONObject jf = (JSONObject) jo.get("duration");
        distance = (long) je.get("value");
        time = (long) jf.get("value");

        distances = distance;
        times = time;

        System.out.println("Distance = " + distance);
        System.out.println("Time = " + time);

    }

    public static long[] getdistancentimesssss() throws Exception {
        System.out.println();
        long distance = 0;
        long time = 0;

        //parsing json data and updating data
        JSONParser jp = new JSONParser();
        JSONObject jo = (JSONObject) jp.parse(getData("KualaLumpur", "Perak"));
        JSONArray ja = (JSONArray) jo.get("rows");
        jo = (JSONObject) ja.get(0);
        ja = (JSONArray) jo.get("elements");
        jo = (JSONObject) ja.get(0);
        JSONObject je = (JSONObject) jo.get("distance");
        JSONObject jf = (JSONObject) jo.get("duration");
        distance = (long) je.get("value");
        time = (long) jf.get("value");

        distances = distance;
        times = time;

        System.out.println("Distance = " + distance + " m");
        System.out.println("Time = " + time + " s");

        return new long[] {distance,time};

    }


    public static void main(String[] args) throws Exception {
        getdistancentime();
        getdistancentimesssss();


    }
}
