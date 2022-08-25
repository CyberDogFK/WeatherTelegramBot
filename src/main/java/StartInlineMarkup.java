import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.List;

/**
 * Create inlineMarkup for bot, with names of cities. And similar call-back data in String, for make
 * call of weather
 */
public class StartInlineMarkup extends InlineKeyboardMarkup{

    public StartInlineMarkup() {
    }

    /**
     * Make keyboard with country from array with counties names. County names have similar callback names.
     * You need to create an array with cities of this country in getCityInlineMarkup
     * @return
     */
    public InlineKeyboardMarkup getCountryInlineMarkup() {
        String[] countries = { "Poland", "France", "Great Britain", "USA", "Germany", "Ukraine"};

        InlineKeyboardMarkup country = new InlineKeyboardMarkup();
        country.setKeyboard(rowsInlineMarkupWithValuesOf(countries));
        return country;
    }

    /**
     * Make keyboard with cities of some country from array with cities names. City names need to be
     * similar to real geographic names, because this names uses for make weather call
     * @param country String with name of country.
     * @return inline keyboard object with cities
     */
    public InlineKeyboardMarkup getCityInlineMarkup(String country) {
        String[] Ukraine = { "Odesa", "Lviv", "Chernivtsi", "Zhytomyr", "Kharkiv", "Kyiv"};
        String[] Poland = { "Warsaw", "Kraków", "Lublin", "Białystok", "Gdańsk", "Katowice"};
        String[] France = { "Paris", "Montpellier", "Lyon", "Nantes", "Brest", "Marseille"};
        String[] GreatBritain = { "London", "Birmingham", "Glasgow", "Manchester", "Cambridge", "Edinburgh"};
        String[] USA = { "New York", "Washington", "San Francisco", "Los Angeles", "Houston", "Chicago"};
        String[] Germany = { "Berlin", "Dresden", "Hamburg", "Frankfurt", "Nuremberg", "Düsseldorf"};

        InlineKeyboardMarkup cityMarkup = new InlineKeyboardMarkup();
        switch (country) {
            case "Ukraine" -> cityMarkup.setKeyboard(rowsInlineMarkupWithValuesOf(Ukraine));
            case "Poland" -> cityMarkup.setKeyboard(rowsInlineMarkupWithValuesOf(Poland));
            case "France" -> cityMarkup.setKeyboard(rowsInlineMarkupWithValuesOf(France));
            case "Great Britain" -> cityMarkup.setKeyboard(rowsInlineMarkupWithValuesOf(GreatBritain));
            case "USA" -> cityMarkup.setKeyboard(rowsInlineMarkupWithValuesOf(USA));
            case "Germany" -> cityMarkup.setKeyboard(rowsInlineMarkupWithValuesOf(Germany));
        }

        return cityMarkup;
    }

    /**
     * Create a List for InlineKeyboard. Take a String with cities and make keyboard with
     * max 3 cells in line.
     * @param values String array with all cities what you want to add to InlineKeyboard
     * @return List of lists of keyboard buttons for inlineKeyboardMarkup
     */
    private List<List<InlineKeyboardButton>> rowsInlineMarkupWithValuesOf(String[] values) {
         List<List<InlineKeyboardButton>> rowsInLine = new ArrayList<>();
         List<InlineKeyboardButton> rowInLine = new ArrayList<>();

         for (int i = 0; i < values.length; i++) {
             rowInLine.add(madeButton(values[i]));

             if (values.length == 1 || (i != 0 && i % 2 == 0 || i == values.length - 1) ) {
                 rowsInLine.add(rowInLine);
                 rowInLine = new ArrayList<>();
             }
         }

         return rowsInLine;
    }

    /**
     * Create a one button with text and similar callBackData
     * @param text String with text you want to add to button
     * @return ready InlineKeyboardButton object
     */
    private InlineKeyboardButton madeButton(String text) {
        InlineKeyboardButton button = new InlineKeyboardButton();
        button.setText(text);
        button.setCallbackData(text);
        return button;
    }
}
