package EAgency;

import lombok.Getter;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * The type Neighbourhood.
 */
@Getter
public class Neighbourhood extends ObjectPlus {
    private static final Set<Property> allProperties = new HashSet<>();
    private final Set<Property> properties = new HashSet<>();
    private float area;
    private String name;

    /**
     * Instantiates a new Neighbourhood.
     *
     * @param name the name
     * @param area the area
     */
    public Neighbourhood(String name, float area) {
        super();
        setArea(area);
        setName(name);
        addToExtent();
    }

    /**
     * Sets area.
     *
     * @param area the area
     */
    public void setArea(float area) {
        if (area < 1000) throw new IllegalArgumentException("Area must be at least 1000 square meters");
        this.area = area;
    }

    /**
     * Sets name.
     *
     * @param name the name
     */
    public void setName(String name) {
        Utils.checkIfStringIsNotNullAndNotBlank(name);
        this.name = name;
    }

    /**
     * Remove building plot.
     */
    public void removeNeighbourhood() {

        for (Property property : this.properties) {
            removeProperty(property);
            property.removeFromExtent();
        }
        this.properties.clear();
        this.removeFromExtent();
    }

    /**
     * Add property.
     *
     * @param property the property
     */
    public void addProperty(Property property) {
        if (property == null) throw new IllegalArgumentException("Property cannot be null");
        if (allProperties.contains(property)) throw new IllegalArgumentException("Property is already owned");

        property.setNeighbourhood(this);
        this.properties.add(property);
        allProperties.add(property);
    }

    /**
     * Remove property.
     *
     * @param property the property
     */
    public void removeProperty(Property property) {
        List<Person> agents = getExtentForClass(Person.class).stream().filter(e -> e.getEstateAgent() != null).toList();
        List<Company> companies = getExtentForClass(Company.class);
        List<Sale> sales = getExtentForClass(Sale.class);
        if (property == null) {
            throw new IllegalArgumentException("Property cannot be null");
        }
        if (!this.properties.contains(property)) {
            return;
        }
        agents.forEach(e -> e.getEstateAgent().removeProperty(property));
        sales.stream().filter(e -> e.getProperty() == property).forEach(Sale::removeProperty);
        companies.forEach(e -> e.removeProperty(property));
        allProperties.remove(property);
        property.removeFromExtent();
    }
}
