package EAgency;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

/**
 * The type Property.
 */
@Getter
public abstract class Property extends ObjectPlus {

    private final UUID id;
    private final Set<Sale> saleSet = new HashSet<>();
    @NonNull
    @Setter
    private Address address;
    private double area;
    private int roomNumber;
    private double price;
    private EstateAgent estateAgent;
    private Neighbourhood neighbourhood;
    private Company company;
    private int constructionYear;


    /**
     * Instantiates a new Property.
     *
     * @param neighbourhood    the neighbourhood
     * @param address          the address
     * @param area             the area
     * @param roomNumber       the room number
     * @param price            the price
     * @param constructionYear the construction year
     */
    public Property(@NonNull Neighbourhood neighbourhood, @NonNull Address address, double area, int roomNumber, double price, int constructionYear) {
        super();
        this.neighbourhood = neighbourhood;
        this.address = address;
        setConstructionYear(constructionYear);
        setArea(area);
        setPrice(price);
        setRoomNumber(roomNumber);
        this.id = UUID.randomUUID();
    }

    /**
     * Sets area.
     *
     * @param area the area
     */
    public void setArea(double area) {
        if (area < 0) throw new IllegalArgumentException("Area must be positive");
        this.area = area;
    }

    /**
     * Sets room number.
     *
     * @param roomNumber the room number
     */
    public void setRoomNumber(int roomNumber) {
        if (roomNumber < 0) throw new IllegalArgumentException("Room number must be positive");
        this.roomNumber = roomNumber;
    }

    /**
     * Sets price.
     *
     * @param price the price
     */
    public void setPrice(double price) {
        if (price < 0) throw new IllegalArgumentException("Price must be positive");
        this.price = price;
    }

    /**
     * Sets estate agent.
     *
     * @param person the person
     */
    public void setEstateAgent(Person person) {
        EstateAgent estateAgent = person.getEstateAgent();
        if (this.estateAgent == null && estateAgent != null) {
            this.estateAgent = estateAgent;
            estateAgent.addProperty(this);
        } else if (this.estateAgent != null && estateAgent == null) {
            EstateAgent tmp = this.estateAgent;
            this.estateAgent = null;
            tmp.removeProperty(this);
        } else if (this.estateAgent != null) {
            EstateAgent tmp = this.estateAgent;
            this.estateAgent = null;
            tmp.removeProperty(this);

            this.estateAgent = estateAgent;
            estateAgent.addProperty(this);
        }
    }

    /**
     * Sets company.
     *
     * @param company the company
     */
    public void setCompany(Company company) {
        if (this.company == null && company != null) {
            this.company = company;
            company.addProperty(this);
        } else if (this.company != null && company == null) {
            Company tmp = this.company;
            this.company = null;
            tmp.removeProperty(this);
        } else if (this.company != null) {
            Company tmp = this.company;
            this.company = null;
            tmp.removeProperty(this);

            this.company = company;
            company.addProperty(this);
        }
    }

    /**
     * Sets neighbourhood.
     *
     * @param neighbourhood the neighbourhood
     */
    public void setNeighbourhood(Neighbourhood neighbourhood) {
        if (neighbourhood == null) throw new IllegalArgumentException("Building plot cannot be null");
        this.neighbourhood = neighbourhood;
    }

    /**
     * Remove sale.
     *
     * @param sale the sale
     */
    public void removeSale(Sale sale) {
        if (sale == null) {
            throw new IllegalArgumentException("Certification cannot be null");
        }
        if (!saleSet.contains(sale)) {
            return;
        }
        sale.removeProperty();
        saleSet.remove(sale);
    }

    /**
     * Add sale.
     *
     * @param sale the sale
     */
    protected void addSale(Sale sale) {
        if (sale == null) {
            throw new IllegalArgumentException("sale cannot be null");
        }

        if (!this.saleSet.contains(sale)) {
            this.saleSet.add(sale);
            sale.setProperty(this);
        }
    }

    @Override
    public String toString() {
        if (getClass().getSimpleName().equals("Apartment"))
            return getClass().getSimpleName() + ": " + getAddress() + "/" + this.getRoomNumber();
        return getClass().getSimpleName() + ": " + getAddress();
    }

    /**
     * Gets construction year.
     *
     * @return the construction year
     */
    public int getConstructionYear() {
        return constructionYear;
    }

    /**
     * Sets construction year.
     *
     * @param constructionYear the construction year
     */
    public void setConstructionYear(int constructionYear) {
        if (constructionYear > LocalDate.now().getYear() + 1)
            throw new IllegalArgumentException("Construction year cannot be in future.");
        this.constructionYear = constructionYear;
    }
}
