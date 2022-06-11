import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class getdistancematrixAPI {

    private static final String API_KEY = "api";
    public static float[][] distances;
    public static float[][] times;
    public static String[] cities = {"", "Ipoh", "KualaLumpur", "Bangsa", "GeorgeTown", "Kuching", "AlorSetar", "Kuantan", "Seremban"};
    public static final int n= cities.length;


    //downloading the data
    public static String getData(String source,String destination) throws Exception{
        var url ="https://maps.googleapis.com/maps/api/distancematrix/json?origins="+source+"&destinations="+destination+"&key="+API_KEY;
        var request = HttpRequest.newBuilder().GET().uri(URI.create(url)).build();
        var client = HttpClient.newBuilder().build();
        var response = client.send(request, HttpResponse.BodyHandlers.ofString()).body();
        return response;

//        long distance = -1l;
//        long time = -1l;

//        System.out.println(response);

        //parsing json data
//        JSONParser jp = new JSONParser();
//        JSONObject jo = (JSONObject) jp.parse(response);
//        JSONArray ja = (JSONArray) jo.get("rows");
//        jo = (JSONObject) ja.get(0);
//        ja = (JSONArray) jo.get("elements");
//        jo = (JSONObject) ja.get(0);
//        JSONObject je = (JSONObject)jo.get("distance");
//        JSONObject jf = (JSONObject)jo.get("duration");
//        distance = (long) je.get("value");
//        time = (long) jf.get("value");

        //1
//        System.out.println(ja.toString())
        //2
//        System.out.println(distance+" mm");
//        System.out.println(time +" ms");

    }
    public static void parse(String response,int i,int j){
        long distance = -1L;
        long time = -1L;
        //parsing json data and updating data
        {
            try {
                JSONParser jp = new JSONParser();
                JSONObject jo = (JSONObject) jp.parse(response);
                JSONArray ja = (JSONArray) jo.get("rows");
                jo = (JSONObject) ja.get(0);
                ja = (JSONArray) jo.get("elements");
                jo = (JSONObject) ja.get(0);
                JSONObject je = (JSONObject) jo.get("distance");
                JSONObject jf = (JSONObject) jo.get("duration");
                distance = (long) je.get("value");
                time = (long) jf.get("value");

                distances[i][j] = distance;
                times[i][j] = time;

            } catch (Exception e) {
                System.out.println(e + " for " + cities[j]);
            }
        }
    }

    public static void main(String[] args) throws Exception {
//        //Coding starting calling API
//        String source ="KualaLumpur";
//        String destination ="SeberangPerai";
//        getData(source,destination);

        distances = new float[n][n];
        times = new float[n][n];
        int count=0;
        for (int i = 1; i < n; i++)
            for (int j = 1; j < n; j++) {
//                System.out.print(++count+"/100 ");
                if (i != j) {
                    String response = getData(cities[i], cities[j]);
                    parse(response,i,j);
                }
                else {
                    times[i][j] = 0;
                    distances[i][j] = 0;
                }
            }
        for (int i = 0;i<n;i++){
            for (int j = 0;j<n;j++){
                System.out.println(cities[i] +" --> "+ cities[j] + " distance =" + distances[i][j] + "time = " + times[i][j]);
            }
        }
    }
}
