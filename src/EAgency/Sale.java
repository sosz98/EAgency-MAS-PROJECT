package EAgency;

import lombok.Getter;
import lombok.NonNull;

import java.time.LocalDate;

/**
 * The type Sale.
 */
//TODO: Property should be in Agent's properties
@Getter
public class Sale extends ObjectPlus {
    @NonNull
    private final LocalDate date;
    private double value;
    private Property property;
    private Client client;
    private EstateAgent estateAgent;

    /**
     * Instantiates a new Sale.
     *
     * @param value       the value
     * @param date        the date
     * @param property    the property
     * @param estateAgent the estate agent
     * @param client      the client
     */
    public Sale(double value, LocalDate date, Property property, Person estateAgent, Person client) {
        super();
        setValue(value);
        this.property = property;
        this.date = date;
        setEstateAgent(estateAgent);
        setClient(client);
        addToExtent();
    }

    /**
     * Sets value.
     *
     * @param value the value
     */
    public void setValue(double value) {
        if (value < 0) throw new IllegalArgumentException("Value should be positive");
        this.value = value;
    }


    /**
     * Sets estate agent.
     *
     * @param person the person
     */
    public void setEstateAgent(Person person) {
        if (person == null) {
            throw new IllegalArgumentException("Person cannot cannot be null");
        }
        EstateAgent agent = person.getEstateAgent();
        agent.addSale(this);
        agent.getAgentProperties().remove(this.getProperty().getId());
        this.estateAgent = agent;
    }

    /**
     * Sets client.
     *
     * @param person the person
     */
    public void setClient(Person person) {
        if (person == null) {
            throw new IllegalArgumentException("Person cannot cannot be null");
        }
        Client client = person.getClient();
        client.addSale(this);
        this.client = client;
    }

    /**
     * Sets property.
     *
     * @param property the property
     */
    public void setProperty(Property property) {
        if (property == null) {
            throw new IllegalArgumentException("Property cannot cannot be null");
        }
        property.addSale(this);
        this.property = property;
    }

    /**
     * Remove agent.
     */
    public void removeAgent() {
        if (estateAgent != null) {
            EstateAgent temp = estateAgent;
            estateAgent = null;
            temp.removeSale(this);
            temp.addProperty(getProperty());
        }
        if (client != null) removeClient();

        if (property != null) removeProperty();
        removeFromExtent();
    }

    /**
     * Remove property.
     */
    public void removeProperty() {
        if (property != null) {
            Property temp = property;
            property = null;
            temp.removeSale(this);
        }
        if (client != null) removeClient();

        if (estateAgent != null) removeAgent();
        removeFromExtent();
    }

    /**
     * Remove client.
     */
    public void removeClient() {
        if (client != null) {
            Client temp = client;
            client = null;

            temp.removeSale(this);
        }
        if (estateAgent != null) removeAgent();

        if (property != null) removeProperty();
        removeFromExtent();
    }

    public String toString() {
        return "Sale (Klient: " + getClient().getPerson().getName() + ", Property: " + getProperty() + ", Estate Agent: " + getEstateAgent().getPerson().getName() + " " + getEstateAgent().getPerson().getSurname();
    }
}
