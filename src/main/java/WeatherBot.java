import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendAnimation;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.games.Animation;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class WeatherBot extends TelegramLongPollingBot {
    private final static String BOT_NAME = "weather_telegram_antp_bot";
    private final static String BOT_TOKEN = "5749054194:AAE3Dzid--FHkwvW8GV6fcTxLbQINq-8hK8";
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

                    InlineKeyboardMarkup markup = new StartInlineMarkup().getCountryInlineMarkup();
                    message.setReplyMarkup(markup);

                    try {
                        execute(message);
                    } catch (TelegramApiException e) {
                        e.printStackTrace();
                    }
                } else {
                    WeatherCall weather = new WeatherCall(message_text);
                    SendMessage message = new SendMessage();
                    message.setChatId(chat_id);
                    message.setText(createWeatherString(weather));

                    SendAnimation sendAnimation = null;
                    if (weather.getMessage() == null) {
                        sendAnimation = animationById(weather);
                        sendAnimation.setChatId(chat_id);
                    }

                    try {
                        execute(message);
                        if (sendAnimation != null) execute(sendAnimation);
                    } catch (TelegramApiException e) {
                        e.printStackTrace();
                    }
                }
            } else if (update.getMessage().hasAnimation()) {
                takeAnimationId(update);
            }
        } else if (update.hasCallbackQuery()) {
            String call_data = update.getCallbackQuery().getData();
            long chat_id = update.getCallbackQuery().getMessage().getChatId();
            long message_id = update.getCallbackQuery().getMessage().getMessageId();
            SendAnimation animation = null;


            EditMessageText message;
            switch (call_data) {
                case "Ukraine", "Poland", "France", "Great Britain", "USA", "Germany" ->
                    message = chooseOfTheCity(message_id, chat_id, call_data);
                default -> {
                    WeatherCall weather = new WeatherCall(call_data);
                    message = new EditMessageText();
                    message.setText(createWeatherString(weather));
                    message.setMessageId(Math.toIntExact(message_id));
                    message.setChatId(chat_id);
                    animation = animationById(weather);
                    animation.setChatId(chat_id);
                }
            }
            try {
                if (animation != null) execute(animation);
                execute(message);
            } catch (TelegramApiException e) {
                e.printStackTrace();
            }
        }
    }

    private SendAnimation animationById(WeatherCall weatherCall) {

        SendAnimation send_animation = new SendAnimation();
        send_animation.setAnimation(takeFileById(weatherCall.sendAnimationById()));
        return send_animation;
    }

    private void takeAnimationId(Update update) {
        long chat_id = update.getMessage().getChatId();

        Animation anim = update.getMessage().getAnimation();
        String anim_id = anim.getFileId();

        SendAnimation msg = new SendAnimation();
        msg.setChatId(chat_id);
        msg.setAnimation(takeFileById(anim_id));
        msg.setCaption(anim_id);

        try {
            execute(msg);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    private InputFile takeFileById(String id) {
        InputFile file = new InputFile();
        file.setMedia(id);
        return file;
    }

    private EditMessageText chooseOfTheCity(long message_id, long chat_id, String country) {
        EditMessageText message = new EditMessageText();
        message.setText("Choose the city: ");
        message.setMessageId(Math.toIntExact(message_id));
        message.setChatId(chat_id);
        message.setReplyMarkup(new StartInlineMarkup().getCityInlineMarkup(country));
        return message;
    }

    private String createWeatherString(WeatherCall weather) {
        if (weather.getMessage() != null) {
            System.out.println("message not null");
            return weather.getMessage();
        }
        return "Weather in " + weather.getLocation() + ":\n\n" +
                "Temp: " + weather.getTemp() + "\n" +
                "Temp filling: " + weather.getTempFilling() + "\n" +
                "Today max temp: " + weather.getTempMax() + "\n" +
                "Today min temp: " + weather.getTempMin() + "\n" +
                "Clouds: " + weather.getDescription() + "\n" +
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
