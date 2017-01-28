package org.fostash.atomic.mysql;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;

import static java.util.stream.Collectors.joining;

/**
 * SQLUtils class for sql transformation.
 */
public interface SQLUtils {

    /**
     * SQLField utility class.
     */
    interface SQLField {

        /**
         * TODO: aggiungere spiegazione.
         * @param field field
         * @param value value
         * @return field formatted for value instance type
         */
        static String toSQL(final String field, final Object value) {

            if (value instanceof String && SQLValue.isDateFormat(value)) {
                return "DATE(FROM_UNIXTIME(" + field + " / 1000))";
            } else if (value instanceof Date) {
                return "DATE(FROM_UNIXTIME(" + field + " / 1000))";
            } else {
                return field;
            }
        }

        /**
         * @param value value
         * @return true if value is a '@' false otherwise
         */
        static boolean isFieldValue(final Object value) {
            return value instanceof String && String.valueOf(value).charAt(0) == '@';
        }
    }

    /**
     * SQLValue utility class.
     */
    interface SQLValue {

        /**
         * Se value inizia con @ viene interpretato come campo di tabella.
         *
         * @param value value
         * @return value formatted base on type
         */
        static String toSQL(final Object value) {

            if (value instanceof String && isDateFormat(value)) {
                return "DATE_FORMAT('" + value + "', 'dd-MM-yyyy')";
            } else if (value instanceof Date) {
                return "DATE_FORMAT('" + new SimpleDateFormat("dd-MM-yyyy").format(value) + "', 'dd-MM-yyyy')";
            } else if (value instanceof String && String.valueOf(value).charAt(0) == '@') {
                return String.valueOf(value).replace("@", "");
            } else if (value instanceof String && isNumberFormat(value, ',')) {
                return "'" + String.valueOf(value).replace(',', '.').replace("'", "''") + "'";
            } else if (value instanceof String || value instanceof Character) {
                return "'" + String.valueOf(value).replace("'", "''") + "'";
            } else if (value instanceof Object[]) {
                return Arrays.asList((Object[]) value)
                        .stream()
                        .map(SQLValue::toSQL)
                        .collect(joining(", "));
            } else {
                return String.valueOf(value);
            }
        }

        /**
         * @param value value
         * @return true if value is a number false otherwise
         */
        static boolean isDateFormat(final Object value) {

            final SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
            try {
                sdf.parse(String.valueOf(value));
            } catch (ParseException e) {
                return false;
            }
            return true;
        }

        /**
         * @param value value
         * @param separator decimal separator
         * @return true if value is a number false otherwise
         */
        static boolean isNumberFormat(final Object value, final char separator) {

            DecimalFormatSymbols.getInstance().setDecimalSeparator(separator);
            final DecimalFormat formatter = new DecimalFormat("#0" + separator + "000");
            try {
                formatter.parse(String.valueOf(value));
            } catch (ParseException e) {
                return false;
            }
            return true;
        }

        /**
         * @param value value
         * @return true if value is numeric false otherwise
         */
        static boolean isNumeric(final Object value) {

            if (value instanceof Integer || value instanceof BigDecimal || value instanceof Double) {
                return true;
            } else if (value instanceof String || value != null) {
                try {
                    Integer.parseInt(String.valueOf(value));
                    return true;
                } catch (NumberFormatException e) {
                    return false;
                }
            } else {
                return false;
            }
        }
    }

}
