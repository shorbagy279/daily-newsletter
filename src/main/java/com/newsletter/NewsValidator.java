package com.newsletter;

import java.util.Set;

public class NewsValidator {
    private static final Set<String> VALID_CATEGORIES = Set.of(
        "business", "entertainment", "general", 
        "health", "science", "sports", "technology"
    );

    private static final Set<String> VALID_COUNTRIES = Set.of(
       "us"
    );

    public static void validateParameters(String country, String category) {
        if (!VALID_COUNTRIES.contains(country.toLowerCase())) {
            throw new IllegalArgumentException("Invalid country code: " + country);
        }
        
        if (category != null && !VALID_CATEGORIES.contains(category.toLowerCase())) {
            throw new IllegalArgumentException("Invalid category: " + category);
        }
    }
}