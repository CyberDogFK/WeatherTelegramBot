import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.List;

public class CountryInlineMarkup {
    public InlineKeyboardMarkup getCountryInlineMarkup() {
        InlineKeyboardMarkup country = new InlineKeyboardMarkup();
        country.setKeyboard(rowsInlineMarkupWithCountries());
        return country;
    }

    private List<List<InlineKeyboardButton>> rowsInlineMarkupWithCountries() {
        List<List<InlineKeyboardButton>> rowsInLine = new ArrayList<>();

        List<InlineKeyboardButton> rowInLine = new ArrayList<>();
        rowInLine.add(madeButton("Ukraine"));
        rowInLine.add(madeButton("Poland"));
        rowInLine.add(madeButton("France"));

        rowsInLine.add(rowInLine);

        rowInLine = new ArrayList<>();
        rowInLine.add(madeButton("Great Britain"));
        rowInLine.add(madeButton("USA"));
        rowInLine.add(madeButton("Germany"));
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
