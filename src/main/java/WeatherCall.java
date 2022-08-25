import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Random;

public class WeatherCall {
    private final String location;

    private String temp_info;
    private String temp_filling;
    private String temp_max;
    private String temp_min;
    private String pressure;
    private String message = null;
    private String main;
    private String description;
    private String wind;

    public String setAnimationById() {
        ArrayList<String> suitableAnimations = new ArrayList<>();
        Random random = new Random();

        String[] hotWeather = {
                "CgACAgIAAxkBAAMKYwesB3C2PrPzhlmLnm1-te0Knz8AAl8dAAIQVkFIYX_y8w1DYo8pBA",
                "CgACAgIAAxkBAAMQYwesEnBLmWL8rwrQkM8diNfR2AQAAmIdAAIQVkFIDL6jarI-ERYpBA",
                "CgACAgIAAxkBAAMSYwesFrNBEEkBnaMGVJi8IpzDJ3oAAmMdAAIQVkFIusIAAabo3YLkKQQ",
                "CgACAgIAAxkBAAMYYwesH9D-jgQkXDkfdkHXE-de8GEAAmYdAAIQVkFITsDGrWwOXqIpBA",
                "CgACAgIAAxkBAAMaYwesJgfFUqaM7Wvz0XJFstltc14AAmcdAAIQVkFIhOaXLcKNamUpBA",
                "CgACAgIAAxkBAAMkYwesN4wfiqh0LJytWVnr36yoBb8AAmwdAAIQVkFILsLozcJC7mwpBA",
                "CgACAgIAAxkBAAM3YweusY33YlrfwE04GYtRnBfh0wkAAnUdAAIQVkFIrMTTRilsFBMpBA"
        };
        if (Double.parseDouble(temp_info) > 30) {
            suitableAnimations.add(hotWeather[random.nextInt(hotWeather.length)]);
        }

        String[] rainy = {
                "CgACAgIAAxkBAAMIYwesAjsNTsIMhthGr-Ez6uylViYAAl0dAAIQVkFIJ5LUEObIIygpBA",
                "CgACAgQAAxkBAAMmYwetO4edrhOMS62fM3pRRkYBwF0AAmUDAAK4gQRTH2KvnXnTq7spBA",
                "CgACAgIAAxkBAAMoYwetTRTD3TRCd-u2vLpcFBpA2tMAAj8DAALvv0FJOb01dKMEchUpBA",
                "CgACAgQAAxkBAAMqYwetazZI36njGGzzL8M3eEvsmXwAAsYCAAJOrQ1TyGJU0AdT9-YpBA"
        };
        if (main.equals("Rain")) {
            suitableAnimations.add(rainy[random.nextInt(rainy.length)]);
        }

        String[] clearSkyGoodWeather = {
                "CgACAgIAAxkBAAMcYwesKfXvatUlF9jnsVcsCrXi-lQAAmgdAAIQVkFI437pZtZkV-opBA",
                "CgACAgIAAxkBAAMiYwesNfZt62BTaLzBSPgT1EJlwVgAAmsdAAIQVkFIB-MmJaUF9N0pBA",
                "CgACAgIAAxkBAAMzYweucI3nN1_KoHTD4v0Hf0uss2oAAnQdAAIQVkFI1n8EMK-fA3UpBA",
                "CgACAgIAAxkBAANAYwevlep6ENYdGjIFdP2-ZfDW_gUAAngdAAIQVkFIWZvJT8adbvIpBA",
                "CgACAgIAAxkBAANDYwewFkFsweQXqrliRqB8gQYcu80AAn0dAAIQVkFItx-mOHhInxMpBA",
                "CgACAgIAAxkBAANFYwewWAABEM0YIab8WDbIJ8V_FoXRAAJ-HQACEFZBSLwjTd0zxA4mKQQ",
                "CgACAgIAAxkBAANHYwewmfE6KNOqf-11Egm2wcoyZUsAAn8dAAIQVkFI7xqR2qAsth0pBA",
                "CgACAgIAAxkBAANJYwexM_FT5olBGYZVIK_j-InsUYcAAoMdAAIQVkFIf02Muflgui8pBA",
                "CgACAgIAAxkBAANLYwex31PwQhdr1PXhbPuINtjCMyAAAogdAAIQVkFIAo4oPA3lOB0pBA"
        };
        if (!main.equals("Rain") && Double.parseDouble(temp_info) < 30 && Double.parseDouble(temp_info) > 15) {
            suitableAnimations.add(clearSkyGoodWeather[random.nextInt(clearSkyGoodWeather.length)]);
        }

        String[] snow = {
                "CgACAgIAAxkBAAMGYwer_gx48ZO_Ynua48E4mcwcWH4AAlwdAAIQVkFIFpXP6SvwzYcpBA",
                "CgACAgIAAxkBAAMMYwesCmsehw4JwuBTdAHMTYV2EcMAAmAdAAIQVkFInFroLjDWhagpBA",
                "CgACAgIAAxkBAAMWYwesHC8NjDbwxLzOa7cGL0gsW1YAAmUdAAIQVkFI9tfBHL7P9j8pBA",
                "CgACAgIAAxkBAAMeYwesL6uFb63fKuci-bnACCzAYbkAAmkdAAIQVkFIAYA_ytbEahopBA",
                "CgACAgIAAxkBAAMgYwesM87_yftF-YTonJWw-JuEAAGCAAJqHQACEFZBSJOnSLLGcN82KQQ"
        };
        if(main.equals("Snow")) {
            suitableAnimations.add(snow[random.nextInt(snow.length)]);
        }

        String[] mist = {
                "CgACAgIAAxkBAAMOYwesDz4kjSz_TpmdkTBlHcWraH4AAmEdAAIQVkFIjm-1BVtnc5wpBA",
                "CgACAgIAAxkBAAMUYwesGdMtthlzBIufuPLjuUd86xAAAmQdAAIQVkFIGTrvuy-jPrIpBA"
        };
        if(main.equals("Mist")) {
            suitableAnimations.add(mist[random.nextInt(mist.length)]);
        }

        if (suitableAnimations.isEmpty()) {
            return "CgACAgQAAxkBAAIEOmMFMGVlOmHGF4AFeNUW4Q2UyaMYAAJlAwACuIEEU7U6KSbUeMzdKQQ";
        }
        return suitableAnimations.get(random.nextInt(suitableAnimations.size()));
    }

    public WeatherCall(String location) {
        this.location = location;
        weatherNow();
    }

    private void weatherNow() {
        String output = getUrlContent("https://api.openweathermap.org/data/2.5/weather?q="
            + location + "&appid=79ea92d879b88ae61993dfedfc700cb9&units=metric");

        if(!output.isEmpty()) {
            JSONObject obj = new JSONObject(output);
            temp_info = String.valueOf(obj.getJSONObject("main").getDouble("temp"));
            temp_filling = String.valueOf(obj.getJSONObject("main").getDouble("feels_like"));
            temp_max = String.valueOf(obj.getJSONObject("main").getDouble("temp_max"));
            temp_min = String.valueOf(obj.getJSONObject("main").getDouble("temp_min"));
            pressure = String.valueOf(obj.getJSONObject("main").getDouble("pressure"));
            main = obj.getJSONArray("weather").getJSONObject(0).getString("main");
            description = obj.getJSONArray("weather").getJSONObject(0).getString("description");
            wind = String.valueOf(obj.getJSONObject("wind").getDouble("speed"));
        } else {
            message = "These location not found";
        }
    }

    public String getLocation() {
        return location;
    }

    public String getMain() {
        return main;
    }

    public String getWind() {
        return wind;
    }

    public String getDescription() {
        return description;
    }

    public String getMessage() {
        if (message == null) {
            return null;
        }
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
