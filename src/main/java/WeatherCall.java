import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

public class WeatherCall {
    private final String location;

    private String temp_info;
    private String temp_filling;
    private String temp_max;
    private String temp_min;
    private String pressure;
    private String message = null;
    private String clouds;
    private String wind;

    public WeatherCall(String location) {
        this.location = location;
        weatherNowIn();
    }

    private void weatherNowIn() {
        String output = getUrlContent("https://api.openweathermap.org/data/2.5/weather?q="
            + location + "&appid=79ea92d879b88ae61993dfedfc700cb9&units=metric");

        if(!output.isEmpty()) {
            JSONObject obj = new JSONObject(output);
            temp_info = String.valueOf(obj.getJSONObject("main").getDouble("temp"));
            temp_filling = String.valueOf(obj.getJSONObject("main").getDouble("feels_like"));
            temp_max = String.valueOf(obj.getJSONObject("main").getDouble("temp_max"));
            temp_min = String.valueOf(obj.getJSONObject("main").getDouble("temp_min"));
            pressure = String.valueOf(obj.getJSONObject("main").getDouble("pressure"));
            clouds = obj.getJSONArray("weather").getJSONObject(0).getString("description");
            wind = String.valueOf(obj.getJSONObject("wind").getDouble("speed"));
        } else {
            message = "These location not found";
        }
    }

    public String getWind() {
        return wind;
    }

    public String getClouds() {
        return clouds;
    }

    public String getMessage() {
        return message;
    }

    public String getAirPressure() {
        return pressure;
    }

    public String getTempMin() {
        return temp_min;
    }

    public String getTempMax() {
        return temp_max;
    }

    public String getTempFilling() {
        return temp_filling;
    }

    public String getTemp() {
        return temp_info;
    }

    private static String getUrlContent(String urlAdress) {
        StringBuilder content = new StringBuilder();

        try {
            URL url = new URL(urlAdress);
            URLConnection urlConn = url.openConnection();

            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(urlConn.getInputStream()));
            String line;

            while ((line = bufferedReader.readLine()) != null) {
                content.append(line).append("\n");
            }
            bufferedReader.close();
        } catch (Exception e) {
            System.out.println("These city not found");
        }
        return content.toString();
    }

}
