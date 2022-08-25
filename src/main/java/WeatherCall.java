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

    public String sendAnimationById() {
        ArrayList<String> suitableAnimations = new ArrayList<>();
        Random random = new Random();

        String[] hotWeather = {
                "CgACAgIAAxkBAAIERGMFMgHDY07wnEbQSRPAAbHMSnunAALvGwACZXMxSPd4mScuWSrOKQQ",
                "CgACAgIAAxkBAAIESGMFMkWu4MNCmQ1Z2YLYKpx2yGdrAALxGwACZXMxSEpyHNZaYxrOKQQ",
                "CgACAgIAAxkBAAIETGMFMtLqxYRfgp5mBm5RL-nYKZ8jAAL0GwACZXMxSHzXjtyDyehRKQQ",
                "CgACAgIAAxkBAAIEVGMFNFL68vlcFV8Gt0Bt_flR28pAAAL5GwACZXMxSAEVrUoB833zKQQ",
                "CgACAgIAAxkBAAIEXmMFNKOk78nUKK4lPzDKOrKP9jEzAAL7GwACZXMxSGAO-Br8pz0qKQQ",
                "CgACAgIAAxkBAAIEZGMFNMFa0jCL1_IUQpZH0HwTuzVvAAL8GwACZXMxSGbaLnXdKJxoKQQ"
        };
        if (Double.parseDouble(temp_info) > 30) {
            suitableAnimations.add(hotWeather[random.nextInt(hotWeather.length)]);
        }

        String[] rainy = {
                "CgACAgQAAxkBAAIEOmMFMGVlOmHGF4AFeNUW4Q2UyaMYAAJlAwACuIEEU7U6KSbUeMzdKQQ",
                "CgACAgIAAxkBAAIEPGMFMNteLQltCE-spGEqRDg5Yba3AAI_AwAC779BSfrIFjUvo7nSKQQ",
                "CgACAgQAAxkBAAIETmMFNCdlgImuBvRR0ObXiBGLzNNTAAIPAwACH5odU6Y5VH7x6g3QKQQ",
                "CgACAgQAAxkBAAIEUGMFNC9PCp13m3Q5sN72Y44CCgtSAALGAgACTq0NUw6tiXTS9ExRKQQ",
                "CgACAgIAAxkBAAIEaGMFNNR5Y8lWOXqfZoJGup-XpneVAAL-GwACZXMxSCHEYjLOp-XyKQQ"
        };
        if (main.equals("Rain")) {
            suitableAnimations.add(rainy[random.nextInt(rainy.length)]);
        }

        String[] clearSkyGoodWeather = {
                "CgACAgIAAxkBAAIEbGMFNOqTDzNjvYmXKIVGEql6wKWyAAMcAAJlczFIpYg4yqwYfgABKQQ",
                "CgACAgIAAxkBAAIEcGMFNPn1kisfZXEgjNo4iSVrxd07AAIBHAACZXMxSBsoPHU1qzdNKQQ"
        };
        if (!main.equals("Rain") && Double.parseDouble(temp_info) < 30 && Double.parseDouble(temp_info) > 15) {
            suitableAnimations.add(clearSkyGoodWeather[random.nextInt(clearSkyGoodWeather.length)]);
        }

        String[] snow = {
                "CgACAgIAAxkBAAIEdGMFNQ3uRzpvg21rD2Kf8alhUQqRAAICHAACZXMxSLrAoTg8E4H5KQQ",
                "CgACAgIAAxkBAAIEhGMFNwaQJTo2ZoveYMLz_ktYcj91AAIMHAACZXMxSLXNoSy75yqsKQQ",
                "CgACAgIAAxkBAAIEiGMFOo6HjHjKxjgToDlErM9IqIIFAAIdHAACZXMxSESZ2ZG2CJDnKQQ",
                "CgACAgIAAxkBAAIEkGMFO44nK4lHFeRvNyQhZFDXjFLbAAIhHAACZXMxSFHf0BpQuCoyKQQ",
                "CgACAgIAAxkBAAIEkmMFO5FPCOVY5A0L3FHNri1xzow_AAIjHAACZXMxSHOJlJ5oxSNIKQQ"
        };
        if(main.equals("Snow")) {
            suitableAnimations.add(snow[random.nextInt(snow.length)]);
        }

        String[] mist = {
                "CgACAgQAAxkBAAIEdmMFNZknDb9JNTEQGBR5KmlU77xUAAL_AgACjgcMU66qzMbhlQxWKQQ",
                "CgACAgIAAxkBAAIEfmMFNj99auRONwpekjduFuDCuQRDAAILHAACZXMxSDaUhrUQ-eLTKQQ"
        };
        if(main.equals("Mist")) {
            suitableAnimations.add(mist[random.nextInt(mist.length)]);
        }

        if (suitableAnimations.isEmpty()) {
            System.out.println("Try to return empty");
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
