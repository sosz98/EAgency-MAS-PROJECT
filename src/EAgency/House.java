package EAgency;

import lombok.Getter;
import lombok.NonNull;

/**
 * The type House.
 */
@Getter
public class House extends Property {

    private double houseArea;

    private House(@NonNull Neighbourhood neighbourhood, @NonNull Address address, double area, int numberOfRooms, double price, int constructionYear, double houseArea) {
        super(neighbourhood, address, area, numberOfRooms, price, constructionYear);
        setHouseArea(houseArea);
        addToExtent();
    }

    /**
     * Create house property.
     *
     * @param neighbourhood    the neighbourhood
     * @param address          the address
     * @param area             the area
     * @param numberOfRooms    the number of rooms
     * @param price            the price
     * @param constructionYear the construction year
     * @param houseArea        the house area
     * @return the property
     */
    public static Property createHouse(Neighbourhood neighbourhood, Address address, double area, int numberOfRooms, double price, int constructionYear, double houseArea) {
        if (neighbourhood == null || address == null)
            throw new IllegalArgumentException("Neighbourhood or address cannot be null");
        Property house = new House(neighbourhood, address, area, numberOfRooms, price, constructionYear, houseArea);
        neighbourhood.addProperty(house);
        return house;
    }

    /**
     * Sets house area.
     *
     * @param houseArea the house area
     */
    public void setHouseArea(double houseArea) {
        if (houseArea < 0) throw new IllegalArgumentException("Area must be positive");
        if (houseArea > this.getArea())
            throw new IllegalArgumentException("House area must be lower than property area");
        this.houseArea = houseArea;
    }

    /**
     * Gets garden area.
     *
     * @return the garden area
     */
    public double getGardenArea() {
        return this.getArea() - houseArea;
    }

}
