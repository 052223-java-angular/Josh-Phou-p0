package com.revature.app.utils;

import static java.lang.System.out;

public class FormatUtil {


    /* Convert a String to Integer
     *
     * @param value the string to convert
     * @return an int
     * */
    public static int toInt(String value) {
        try {
            return Integer.parseInt(value);
        } catch (NumberFormatException e) {
            out.println(e.getLocalizedMessage());
        }
        return 0;
    }

    /* Converts String value to double
     * @param value the string to convert
     * @return a double
     * */
    public static Double toDouble(String value) {
        try {
            return Double.parseDouble(value);
        } catch (NumberFormatException e) {
            out.println(e.getLocalizedMessage());
        }
        return 0.00;
    }

    /* Converts int value to string
     * @param value the int to convert
     * @return a string representation of the int value
     * */
    public static String intToStr(int value) {
        try {
            return String.valueOf(value);
        } catch (NumberFormatException e) {
            out.println(e.getLocalizedMessage());
        }
        return "0";
    }

    /* Converts double value to string
     * @param value the int to convert
     * @return a string representation of the double value
     * */
    public static String doubleToStr(double value) {
        try {
            return String.valueOf(value);
        } catch (NumberFormatException e) {
            out.println(e.getLocalizedMessage());
        }
        return "0.00";
    }


}
