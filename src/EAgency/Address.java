package EAgency;

import lombok.Getter;

import java.util.regex.Pattern;

/**
 * The type Address.
 */
@Getter
public class Address extends ObjectPlus {

    private String street, city, postalCode;
    private int number;

    /**
     * Instantiates a new Address.
     *
     * @param street     the street
     * @param city       the city
     * @param postalCode the postal code
     * @param number     the number
     */
    public Address(String street, String city, String postalCode, int number) {
        setCity(city);
        setStreet(street);
        setNumber(number);
        setPostalCode(postalCode);
    }

    /**
     * Sets street.
     *
     * @param street the street
     */
    public void setStreet(String street) {
        Utils.checkIfStringIsNotNullAndNotBlank(street);
        this.street = street;
    }

    /**
     * Sets city.
     *
     * @param city the city
     */
    public void setCity(String city) {
        Utils.checkIfStringIsNotNullAndNotBlank(city);
        this.city = city;
    }

    /**
     * Sets postal code.
     *
     * @param postalCode the postal code
     */
    public void setPostalCode(String postalCode) {
        Utils.checkIfStringIsNotNullAndNotBlank(postalCode);
        if (!Pattern.matches("\\d{2}-\\d{3}", postalCode))
            throw new IllegalArgumentException("Postal code should be in **-*** format.");
        this.postalCode = postalCode;
    }

    /**
     * Sets number.
     *
     * @param number the number
     */
    public void setNumber(int number) {
        if (number <= 0) throw new IllegalArgumentException("Number must be positive");
        this.number = number;
    }

    @Override
    public String toString() {
        return postalCode + " " + city + ", " + street + " " + number;
    }
}
