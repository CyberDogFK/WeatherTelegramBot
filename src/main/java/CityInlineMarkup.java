import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.List;

public class CityInlineMarkup extends InlineKeyboardMarkup{

    public CityInlineMarkup() {
    }

    public InlineKeyboardMarkup getCityInlineMarkup(String country) {
        String[] Ukraine = { "Kyiv", "Odesa", "Lviv", "Chernivtsi", "Zhytomyr", "Kharkiv"};
        String[] Poland = { "Warsaw", "Kraków", "Lublin", "Białystok", "Gdańsk", "Katowice"};
        String[] France = { "Paris", "Montpellier", "Lyon", "Nantes", "Brest", "Marseille"};
        String[] GreatBritain = { "London", "Birmingham", "Glasgow", "Manchester", "Cambridge", "Edinburgh"};
        String[] USA = { "New York", "Washington", "San Francisco", "Los Angeles", "Houston", "Chicago"};
        String[] Germany = { "Berlin", "Dresden", "Hamburg", "Frankfurt", "Nuremberg", "Düsseldorf"};

        InlineKeyboardMarkup cityMarkup = new InlineKeyboardMarkup();
        switch (country) {
            case "Ukraine" -> cityMarkup.setKeyboard(rowsInlineMarkupWithCites(Ukraine));
            case "Poland" -> cityMarkup.setKeyboard(rowsInlineMarkupWithCites(Poland));
            case "France" -> cityMarkup.setKeyboard(rowsInlineMarkupWithCites(France));
            case "Great Britain" -> cityMarkup.setKeyboard(rowsInlineMarkupWithCites(GreatBritain));
            case "USA" -> cityMarkup.setKeyboard(rowsInlineMarkupWithCites(USA));
            case "Germany" -> cityMarkup.setKeyboard(rowsInlineMarkupWithCites(Germany));
        }

        return cityMarkup;
    }


    private List<List<InlineKeyboardButton>> rowsInlineMarkupWithCites(String[] cites) {
         List<List<InlineKeyboardButton>> rowsInLine = new ArrayList<>();

         List<InlineKeyboardButton> rowInLine = new ArrayList<>();
         rowInLine.add(madeButton(cites[0]));
         rowInLine.add(madeButton(cites[1]));
         rowInLine.add(madeButton(cites[2]));

         rowsInLine.add(rowInLine);

         rowInLine = new ArrayList<>();
         rowInLine.add(madeButton(cites[3]));
         rowInLine.add(madeButton(cites[4]));
         rowInLine.add(madeButton(cites[5]));
         rowsInLine.add(rowInLine);

         return rowsInLine;
    }

    private InlineKeyboardButton madeButton(String text) {
        InlineKeyboardButton button = new InlineKeyboardButton();
        button.setText(text);
        button.setCallbackData(text);
        return button;
    }
}
