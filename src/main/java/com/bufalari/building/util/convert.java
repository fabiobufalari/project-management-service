package com.bufalari.building.util;

public class convert {

    public static double convertFeetToInches(double feet) {
        return feet * 12;
    }

    public static double convertStringToFeet(String value) {
        if (value == null || value.isEmpty()) {
            return 0.0;
        }
        String[] parts = value.split(" ");
        double feet = Double.parseDouble(parts[0]);
        if (parts.length > 1) {
            String[] fraction = parts[1].split("/");
            double numerator = Double.parseDouble(fraction[0]);
            double denominator = Double.parseDouble(fraction[1]);
            feet += numerator / denominator;
        }
        return feet;
    }

    public static double convertInchesToFeet(double inches) {
        return inches / 12.0;
    }

    public static double convertStringToInches(String value) {
        if (value == null || value.isEmpty()) {
            return 0.0;
        }
        String[] parts = value.split(" ");
        double inches = 0.0;
        if (parts.length == 1) {
            // Apenas polegadas
            inches = Double.parseDouble(parts[0]);
        } else if (parts.length == 2) {
            // Polegadas e fração
            double wholeInches = Double.parseDouble(parts[0]);
            inches = wholeInches;
            String[] fraction = parts[1].split("/");
            double numerator = Double.parseDouble(fraction[0]);
            double denominator = Double.parseDouble(fraction[1]);
            inches += (numerator / denominator);
        } else if (parts.length == 3) {
            // Pés, polegadas e fração
            double feet = Double.parseDouble(parts[0]);
            inches = feet * 12;
            double wholeInches = Double.parseDouble(parts[1]);
            inches += wholeInches;
            String[] fraction = parts[2].split("/");
            double numerator = Double.parseDouble(fraction[0]);
            double denominator = Double.parseDouble(fraction[1]);
            inches += (numerator / denominator);
            inches = inches/12;
        }
        double feet = convertInchesToFeet(inches);
        return feet;
    }
}
