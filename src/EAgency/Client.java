package EAgency;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

import java.time.LocalDate;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 * The type Client.
 */
@Getter
public class Client extends ObjectPlus {
    private final Set<Sale> saleSet = new HashSet<>();
    private double budget;
    @NonNull
    @Setter
    private Person person;

    /**
     * Instantiates a new Client.
     *
     * @param budget the budget
     * @param person the person
     */
    public Client(double budget, @NonNull Person person) {
        setBudget(budget);
        setPerson(person);
    }

    /**
     * Gets sale set.
     *
     * @return the sale set
     */
    public Set<Sale> getSaleSet() {
        return Collections.unmodifiableSet(saleSet);
    }


    /**
     * Sets budget.
     *
     * @param budget the budget
     */
    public void setBudget(double budget) {
        if (budget < 0) throw new IllegalArgumentException("Budget should be positive");
        this.budget = budget;
    }

    /**
     * Remove sale.
     *
     * @param sale the sale
     */
    public void removeSale(Sale sale) {
        if (sale == null) {
            throw new IllegalArgumentException("Sale cannot be null");
        }
        if (!saleSet.contains(sale)) {
            return;
        }
        sale.removeClient();
        saleSet.remove(sale);
    }

    /**
     * Add sale.
     *
     * @param sale the sale
     */
    public void addSale(Sale sale) {
        if (sale == null) {
            throw new IllegalArgumentException("Sale cannot be null");
        }

        if (!this.saleSet.contains(sale)) {
            this.saleSet.add(sale);
            sale.setClient(this.getPerson());
        }
    }

    /**
     * Buy property.
     *
     * @param property the property
     */
    public void buyProperty(Property property) {
        this.addSale(new Sale(property.getPrice(), LocalDate.now(), property, property.getEstateAgent().getPerson(), this.getPerson()));
        setBudget(getBudget() - property.getPrice());
    }

    /**
     * Return property.
     *
     * @param property the property
     */
    public void returnProperty(Property property) {
        this.removeSale(saleSet.stream().filter(e -> e.getClient() == this && e.getProperty() == property).findFirst().orElse(null));
        setBudget(getBudget() + 0.95 * property.getPrice());
    }

}
