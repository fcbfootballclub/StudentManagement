package vd1;

import java.text.DateFormat;
import java.text.NumberFormat;
import java.util.Date;
import java.util.Locale;
import java.util.ResourceBundle;

public class App {
    public static void main(String[] args) {
        String language = "vi";
        String country = "VN";

        Locale locale = new Locale(language, country);
        ResourceBundle resourceBundle = ResourceBundle.getBundle("MsgBundle_vi_VN", locale);
        System.out.println(resourceBundle.getString("greeting"));

        int x = 123456789;
        float y = 1233213.42432f;

        NumberFormat numberFormat = NumberFormat.getNumberInstance(locale);
        System.out.println("x = " + numberFormat.format(x));
        System.out.println("y = " + numberFormat.format(y));

        numberFormat = NumberFormat.getCurrencyInstance(locale);
        System.out.println(numberFormat.format(x));
        numberFormat = NumberFormat.getPercentInstance(locale);
        System.out.println(numberFormat.format(x));

        DateFormat dateFormat = DateFormat.getDateInstance(DateFormat.FULL, locale);
        Date date = new Date();

        String value = dateFormat.format(date);
        System.out.println(value);

    }
}
