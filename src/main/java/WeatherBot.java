import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class WeatherBot extends TelegramLongPollingBot {
    private final static String BOT_NAME = "my_test_apavliuk_bot";
    private final static String BOT_TOKEN = "5516603177:AAGNklCwMcYf9-e4TtTvtTnaWxPFeLKI1mI";
    @Override
    public String getBotUsername() {
        return BOT_NAME;
    }

    @Override
    public String getBotToken() {
        return BOT_TOKEN;
    }

    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage()) {
            String user_first_name = update.getMessage().getChat().getFirstName();
            String user_username = update.getMessage().getChat().getUserName();
            long chat_id = update.getMessage().getChatId();
            log(user_first_name, user_username, Long.toString(chat_id));

            if (update.getMessage().hasText()) {
                String message_text = update.getMessage().getText();
                if (message_text.equals("/start")) {
                    SendMessage message = new SendMessage();
                    message.setChatId(chat_id);
                    message.setText("Choose the country");

                    InlineKeyboardMarkup markup = new CountryInlineMarkup().getCountryInlineMarkup();
                    message.setReplyMarkup(markup);

                    try {
                        execute(message);
                    } catch (TelegramApiException e) {
                        e.printStackTrace();
                    }
                } else {
                    SendMessage message = new SendMessage();
                    message.setChatId(chat_id);
                    message.setText(createWeatherString(message_text));

                    try {
                        execute(message);
                    } catch (TelegramApiException e) {
                        e.printStackTrace();
                    }
                }
            }
        } else if (update.hasCallbackQuery()) {
            String call_data = update.getCallbackQuery().getData();
            long chat_id = update.getCallbackQuery().getMessage().getChatId();
            long message_id = update.getCallbackQuery().getMessage().getMessageId();

            EditMessageText message;
            switch (call_data) {
                case "Ukraine" ->
                    message = chooseAfterCountry(message_id, chat_id, call_data);
                case "Poland" ->
                    message = chooseAfterCountry(message_id, chat_id, call_data);
                case "France" ->
                    message = chooseAfterCountry(message_id, chat_id, call_data);
                case "Great Britain" ->
                    message = chooseAfterCountry(message_id, chat_id, call_data);
                case "USA" ->
                    message = chooseAfterCountry(message_id, chat_id, call_data);
                case "Germany" ->
                    message = chooseAfterCountry(message_id, chat_id, call_data);
                default -> {
                    message = new EditMessageText();
                    message.setText(createWeatherString(call_data));
                    message.setMessageId(Math.toIntExact(message_id));
                    message.setChatId(chat_id);
                }
            }
            try {
                execute(message);
            } catch (TelegramApiException e) {
                e.printStackTrace();
            }
        }
    }

    private EditMessageText chooseAfterCountry(long message_id, long chat_id, String country) {
        EditMessageText message = new EditMessageText();
        message.setText("Choose the city: ");
        message.setMessageId(Math.toIntExact(message_id));
        message.setChatId(chat_id);
        message.setReplyMarkup(new CityInlineMarkup().getCityInlineMarkup(country));
        return message;
    }

    private String createWeatherString(String location) {
        WeatherCall weather = new WeatherCall(location);
        if (weather.getMessage() != null) {
            return weather.getMessage();
        }
        return "Weather in " + location + ":\n\n" +
                "Temp: " + weather.getTemp() + "\n" +
                "Temp filling: " + weather.getTempFilling() + "\n" +
                "Clouds: " + weather.getClouds() + "\n" +
                "Wind speed: " + weather.getWind() + "\n" +
                "Air pressure: " + weather.getAirPressure();
    }



    private void log(String first_name, String user_username, String chat_id) {
        System.out.println("\b------------------------------------------------------------");
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd hh:mm:ss");
        Date date = new Date();
        System.out.println(dateFormat.format(date));
        System.out.println("Message from " + first_name + " aka. " + user_username + ".\n " +
                "(chat_id = " + chat_id + ")");
    }
}
