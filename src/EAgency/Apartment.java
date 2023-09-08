package EAgency;

import lombok.Getter;
import lombok.NonNull;

/**
 * The type Apartment.
 */
@Getter
public class Apartment extends Property {
    private int apartmentNumber;
    private double rent;

    private Apartment(@NonNull Neighbourhood neighbourhood, @NonNull Address address, double area, int roomNumber, double price, int constructionYear, int apartmentNumber, double rent) {
        super(neighbourhood, address, area, roomNumber, price, constructionYear);
        setApartmentNumber(apartmentNumber);
        setRent(rent);
        addToExtent();
    }

    /**
     * Create apartment property.
     *
     * @param neighbourhood    the neighbourhood
     * @param address          the address
     * @param area             the area
     * @param numberOfRooms    the number of rooms
     * @param price            the price
     * @param constructionYear the construction year
     * @param apartmentNumber  the apartment number
     * @param rent             the rent
     * @return the property
     */
    public static Property createApartment(Neighbourhood neighbourhood, Address address, double area, int numberOfRooms, double price, int constructionYear, int apartmentNumber, double rent) {
        if (neighbourhood == null || address == null)
            throw new IllegalArgumentException("Neighbourhood or address cannot be null");
        Property apartment = new Apartment(neighbourhood, address, area, numberOfRooms, price, constructionYear, apartmentNumber, rent);
        neighbourhood.addProperty(apartment);
        return apartment;
    }

    /**
     * Sets apartment number.
     *
     * @param apartmentNumber the apartment number
     */
    public void setApartmentNumber(int apartmentNumber) {
        if (apartmentNumber <= 0) throw new IllegalArgumentException("Apartment number should be positive");
        this.apartmentNumber = apartmentNumber;
    }

    /**
     * Sets rent.
     *
     * @param rent the rent
     */
    public void setRent(double rent) {
        if (rent < 0) throw new IllegalArgumentException("Rent should be non negative");
        this.rent = rent;
    }
}
