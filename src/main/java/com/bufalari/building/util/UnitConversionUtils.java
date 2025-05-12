package com.bufalari.building.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UnitConversionUtils {

    private static final Logger log = LoggerFactory.getLogger(UnitConversionUtils.class);
    private static final double INCHES_PER_FOOT = 12.0;

    // Pattern to match feet and inches: "X' Y Z/N\"" or "X' Y\"" or "Y Z/N\"" or "Y\""
    // Examples: 5' 6 1/2", 5' 6", 6 1/2", 6"
    // This is a simplified pattern. A more robust imperial measurement parser might be needed for complex inputs.
    private static final Pattern FEET_INCHES_PATTERN = Pattern.compile(
            "(?:(\\d+)(?:\\s* pieds|\\s*p|\\s*'))?" +  // Optional feet part (X')
            "\\s*" +
            "(?:(\\d+)(?:\\s*(\\d+)/(\\d+))?(\\s* pouces|\\s*po|\\s*\")?)?" // Optional inches part (Y Z/N")
    );


    public static double convertFeetToInches(double feet) {
        return feet * INCHES_PER_FOOT;
    }

    public static double convertInchesToFeet(double inches) {
        if (INCHES_PER_FOOT == 0) return 0; // Avoid division by zero, though constant is 12.0
        return inches / INCHES_PER_FOOT;
    }

    /**
     * Converts a string representation of feet and inches (e.g., "5' 6 1/2\"") to total feet.
     * @param value String like "X' Y Z/N\""
     * @return total feet as double, or 0.0 if parsing fails.
     */
    public static double convertStringToFeet(String value) {
        if (value == null || value.trim().isEmpty()) {
            log.warn("Input string for feet conversion is null or empty. Returning 0.0.");
            return 0.0;
        }
        value = value.trim().replace(',', '.'); // Normalize comma to dot for decimals

        double totalFeet = 0.0;
        double totalInches = 0.0;

        // Attempt to parse common formats like "10.5" (for 10.5 feet) or "10 1/2" (for 10.5 feet)
        try {
            if (value.matches("^-?\\d+(\\.\\d+)?$")) { // Is it a simple decimal number?
                return Double.parseDouble(value);
            }
            if (value.matches("^\\d+\\s+\\d+[/\\\\]\\d+$")) { // e.g. "10 1/2"
                 String[] parts = value.split("\\s+");
                 totalFeet = Double.parseDouble(parts[0]);
                 String[] fractionParts = parts[1].split("[/\\\\]");
                 if (fractionParts.length == 2) {
                     double num = Double.parseDouble(fractionParts[0]);
                     double den = Double.parseDouble(fractionParts[1]);
                     if (den != 0) {
                         totalFeet += num / den;
                     }
                 }
                 return totalFeet;
            }
        } catch (NumberFormatException e) {
            // Fall through to more complex parsing
        }


        // More complex parsing for "X' Y Z/N\""
        // This part needs a robust imperial measurement parser.
        // The provided regex and logic in original `convertStringToFeet` was very basic.
        // For now, a simplified approach for "X" or "X Y/Z" (assuming X is feet, Y/Z is fraction of foot)
        String[] parts = value.split("\\s+"); // Split by space
        try {
            if (parts.length >= 1 && !parts[0].isEmpty()) {
                totalFeet = Double.parseDouble(parts[0].replace("'", "").replace("p", ""));
            }
            if (parts.length > 1 && !parts[1].isEmpty()) {
                // Assuming second part is a fraction of a foot
                if (parts[1].contains("/")) {
                    String[] fraction = parts[1].split("/");
                    if (fraction.length == 2) {
                        double numerator = Double.parseDouble(fraction[0].replace("\"", "").replace("po", ""));
                        double denominator = Double.parseDouble(fraction[1].replace("\"", "").replace("po", ""));
                        if (denominator != 0) {
                            totalFeet += numerator / denominator;
                        } else {
                            log.warn("Denominator zero in fraction part '{}' of value '{}'", parts[1], value);
                        }
                    }
                } else {
                     // If not a fraction, it might be inches that need conversion to feet
                     // This logic becomes ambiguous without clear markers like ' and "
                     log.warn("Ambiguous second part '{}' in value '{}' for feet conversion. Interpreting as inches.", parts[1], value);
                     totalInches = Double.parseDouble(parts[1].replace("\"", "").replace("po", ""));
                     totalFeet += convertInchesToFeet(totalInches);
                }
            }
        } catch (NumberFormatException e) {
            log.error("Error parsing string '{}' to feet. Returning 0.0. Error: {}", value, e.getMessage());
            return 0.0;
        }
        return totalFeet;
    }

    /**
     * Converts a string representation of inches (e.g., "6", "6 1/2") to total inches.
     * @param value String like "Y" or "Y Z/N"
     * @return total inches as double, or 0.0 if parsing fails.
     */
    public static double convertStringToInches(String value) {
        if (value == null || value.trim().isEmpty()) {
            log.warn("Input string for inches conversion is null or empty. Returning 0.0.");
            return 0.0;
        }
        value = value.trim().replace(',', '.').replace("\"", "").replace("po", ""); // Normalize and remove units

        double totalInches = 0.0;
        String[] parts = value.split("\\s+"); // Split by space

        try {
            if (parts.length == 1) { // Format: "INCHES" or "N/D"
                if (parts[0].contains("/")) {
                    totalInches = parseFraction(parts[0]);
                } else {
                    totalInches = Double.parseDouble(parts[0]);
                }
            } else if (parts.length == 2) { // Format: "WHOLE_INCHES N/D"
                totalInches = Double.parseDouble(parts[0]);
                totalInches += parseFraction(parts[1]);
            } else {
                log.warn("Unsupported format for inches string: '{}'. Attempting to parse first part.", value);
                if (!parts[0].isEmpty()) totalInches = Double.parseDouble(parts[0]);
            }
        } catch (NumberFormatException e) {
            log.error("Error parsing string '{}' to inches. Returning 0.0. Error: {}", value, e.getMessage());
            return 0.0;
        }
        return totalInches;
    }

    private static double parseFraction(String fractionStr) {
        if (fractionStr == null || !fractionStr.contains("/")) {
            log.warn("Invalid fraction format: '{}'. Expected 'N/D'. Attempting to parse as double.", fractionStr);
            try {
                return Double.parseDouble(fractionStr);
            } catch (NumberFormatException e) {
                log.error("Could not parse '{}' as double after failing fraction parse.", fractionStr);
                return 0.0;
            }
        }
        String[] fractionParts = fractionStr.split("/");
        if (fractionParts.length != 2) {
            log.warn("Invalid fraction format: '{}'. Expected 'N/D'.", fractionStr);
            return 0.0;
        }
        try {
            double numerator = Double.parseDouble(fractionParts[0]);
            double denominator = Double.parseDouble(fractionParts[1]);
            if (denominator == 0) {
                log.warn("Denominator is zero in fraction: '{}'", fractionStr);
                return 0.0;
            }
            return numerator / denominator;
        } catch (NumberFormatException e) {
            log.error("Error parsing numerator/denominator in fraction: '{}'. Error: {}", fractionStr, e.getMessage());
            return 0.0;
        }
    }
}