package se.jensen.yuki.onlinestorebackend.user_order.user.domain.vo;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

import java.util.Objects;
import java.util.regex.Pattern;

@Embeddable
public class Address {
    @Column(name = "street", nullable = false, length = 100)
    private String street;
    @Column(name = "postal_code")
    private String postalCode;
    @Column(name = "city", nullable = false, length = 50)
    private String city;
    @Column(name = "country", nullable = false, length = 50)
    private String country;
    // Simple regex pattern for basic Address validation
    private static final Pattern STREET_PATTERN = Pattern.compile("^[A-Za-zÅÄÖåäö0-9\\s.,\\-]{1,100}$");
    private static final Pattern POSTALCODE_PATTERN = Pattern.compile("^\\d{3}\\s?\\d{2}$");
    private static final Pattern CITY_PATTERN = Pattern.compile("^[A-Za-zÅÄÖåäö\\s\\-]{1,50}$");
    // Maximum length for an Address address
    private static final int MAX_LENGTH = 100;

    protected Address() {
        // for JPA
    }

    private Address(String street, String postalCode, String city, String country) {
        this.street = validator(street, "street", MAX_LENGTH, STREET_PATTERN);
        this.postalCode = validator(postalCode, "postalCode", MAX_LENGTH, POSTALCODE_PATTERN);
        this.city = validator(city, "city", MAX_LENGTH, CITY_PATTERN);
        // Temporary it is only in Sweden
        this.country = "Sverige";
    }

    public static Address of(String street, String postalCode, String city, String country) {
        return new Address(street, postalCode, city, country);
    }

    public String getStreet() {
        return street;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public String getCity() {
        return city;
    }

    public String getCountry() {
        return country;
    }

    private String validator(String value, String fieldName, int maxLength, Pattern regexPattern) {
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException(fieldName + " cannot be null or blank");
        }

        if (value.length() > maxLength) {
            throw new IllegalArgumentException(fieldName + " cannot be longer than " + maxLength + " characters");
        }

        if (!regexPattern.matcher(value).matches()) {
            throw new IllegalArgumentException("Invalid " + fieldName + " format: " + value);
        }

        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Address address)) return false;

        return Objects.equals(this.street, address.street) &&
                Objects.equals(this.postalCode, address.postalCode) &&
                Objects.equals(this.city, address.city) &&
                Objects.equals(this.country, address.country);
    }

    @Override
    public int hashCode() { return Objects.hash(street, postalCode, city, country); }

    @Override
    public String toString() { return street + ", " + postalCode + " " + city + ", " + country; }
}
