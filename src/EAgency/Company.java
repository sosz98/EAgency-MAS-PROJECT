package EAgency;

import lombok.Getter;
import lombok.NonNull;
import lombok.ToString;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

//TODO: More than one in sets

/**
 * The type Company.
 */
@ToString
@Getter
public class Company extends ObjectPlus {
    private final Set<Property> properties = new HashSet<>();
    private final Set<Trainee> trainees = new HashSet<>();
    private final Set<EstateAgent> agents = new HashSet<>();
    private final Set<Employment> employmentSet = new HashSet<>();

    private Address address;
    private String name;
    private int constructionYear;

    /**
     * Instantiates a new Company.
     *
     * @param name             the name
     * @param address          the address
     * @param constructionYear the construction year
     */
    public Company(String name, Address address, int constructionYear) {
        super();
        setName(name);
        setAddress(address);
        setConstructionYear(constructionYear);
        addToExtent();
    }

    /**
     * Gets properties.
     *
     * @return the properties
     */
    public Set<Property> getProperties() {
        return Collections.unmodifiableSet(properties);
    }

    /**
     * Gets trainees.
     *
     * @return the trainees
     */
    public Set<Trainee> getTrainees() {
        return Collections.unmodifiableSet(trainees);
    }

    /**
     * Gets agents.
     *
     * @return the agents
     */
    public Set<EstateAgent> getAgents() {
        return Collections.unmodifiableSet(agents);
    }

    /**
     * Gets employment set.
     *
     * @return the employment set
     */
    public Set<Employment> getEmploymentSet() {
        return Collections.unmodifiableSet(employmentSet);
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
     * Sets construction year.
     *
     * @param constructionYear the construction year
     */
    public void setConstructionYear(int constructionYear) {
        if (constructionYear < 0) throw new IllegalArgumentException("Construction year should be positive");
        this.constructionYear = constructionYear;
    }

    /**
     * Remove employment.
     *
     * @param employment the employment
     */
    protected void removeEmployment(Employment employment) {
        if (employment == null) {
            throw new IllegalArgumentException("Certification cannot be null");
        }
        if (!employmentSet.contains(employment)) {
            return;
        }
        employment.removeCompany();
        employmentSet.remove(employment);
    }

    /**
     * Add employment.
     *
     * @param employment the employment
     */
    public void addEmployment(Employment employment) {
        if (employment == null) {
            throw new IllegalArgumentException("employment cannot be null");
        }

        if (!employmentSet.contains(employment)) {
            employmentSet.add(employment);
            employment.setCompany(this);
        }
    }

    /**
     * Add property.
     *
     * @param property the property
     */
    public void addProperty(Property property) {
        if (property == null) throw new IllegalArgumentException("Property cannot be null");
        if (this.properties.contains(property)) return;
        this.properties.add(property);
        if (property.getCompany() == null) property.setCompany(this);
    }

    /**
     * Remove property.
     *
     * @param property the property
     */
    public void removeProperty(Property property) {
        if (property == null) throw new IllegalArgumentException("Cannot remove property which is null");
        if (!this.properties.contains(property)) return;
        this.properties.remove(property);
        property.setCompany(null);
    }

    /**
     * Add trainee.
     *
     * @param trainee the trainee
     */
    public void addTrainee(Trainee trainee) {
        if (trainee == null) throw new IllegalArgumentException("Trainee cannot be null");
        if (this.trainees.contains(trainee)) return;
        this.trainees.add(trainee);
        if (trainee.getCompany() == null) trainee.setCompany(this);
    }

    /**
     * Remove trainee.
     *
     * @param trainee the trainee
     */
    public void removeTrainee(Trainee trainee) {
        if (trainee == null) throw new IllegalArgumentException("Cannot remove trainee which is null");
        if (!this.trainees.contains(trainee)) return;
        this.trainees.remove(trainee);
        trainee.setCompany(null);
    }

    public void setAddress(Address address) {
        if (address == null)
            throw new IllegalArgumentException("Address cannot be null");
        this.address = address;
    }
}
