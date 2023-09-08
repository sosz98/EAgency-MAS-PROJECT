package EAgency;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

/**
 * The type Estate agent.
 */
@Getter
public class EstateAgent extends ObjectPlus {
    private static float MAX_PROVISION = 0.05f;
    private final Set<Sale> saleSet = new HashSet<>();
    private final Map<UUID, Property> agentProperties = new HashMap<>();
    private float provision;
    @NonNull
    @Setter
    private Person person;
    private Set<Certification> certificationSet = new HashSet<>();
    private Set<Employment> employmentSet = new HashSet<>();

    /**
     * Instantiates a new Estate agent.
     *
     * @param provision the provision
     * @param person    the person
     */
    public EstateAgent(float provision, @NonNull Person person) {
        setProvision(provision);
        setPerson(person);
    }

    /**
     * Gets max provision.
     *
     * @return the max provision
     */
    public static float getMaxProvision() {
        return MAX_PROVISION;
    }

    /**
     * Sets max provision.
     *
     * @param provision the provision
     */
    public static void setMaxProvision(float provision) {
        if (provision < 0 || provision > 1)
            throw new IllegalArgumentException("Provision cannot be less than 0 or more than 1");
        MAX_PROVISION = provision;
    }

    /**
     * Gets certification set.
     *
     * @return the certification set
     */
    public Set<Certification> getCertificationSet() {
        return Collections.unmodifiableSet(certificationSet);
    }

    /**
     * Sets certification set.
     *
     * @param certificationSet the certification set
     */
    public void setCertificationSet(Set<Certification> certificationSet) {
        if (certificationSet == null) throw new IllegalArgumentException("Certification set cannot be null");
        this.certificationSet = certificationSet;
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
     * Sets employment set.
     *
     * @param employmentSet the employment set
     */
    public void setEmploymentSet(Set<Employment> employmentSet) {
        if (employmentSet == null) throw new IllegalArgumentException("Employment set cannot be null");
        this.employmentSet = employmentSet;
    }

    /**
     * Sets provision.
     *
     * @param provision the provision
     */
    public void setProvision(float provision) {
        if (provision < 0 || provision > MAX_PROVISION)
            throw new IllegalArgumentException("Provision should be between 0 and 0.05");
        this.provision = provision;
    }

    /**
     * Add property.
     *
     * @param property the property
     */
    public void addProperty(Property property) {

        if (!agentProperties.containsKey(property.getId())) {
            agentProperties.put(property.getId(), property);
        }
        if (property.getEstateAgent() == null) property.setEstateAgent(this.getPerson());
    }

    /**
     * Remove property.
     *
     * @param property the property
     */
    public void removeProperty(Property property) {
        if (property == null) {
            throw new IllegalArgumentException("Property cannot be null");
        }

        if (!agentProperties.containsKey(property.getId())) return;
        agentProperties.remove(property.getId());
        property.setEstateAgent(null);
    }

    /**
     * Remove certification.
     *
     * @param certification the certification
     */
    public void removeCertification(Certification certification) {
        if (certification == null) {
            throw new IllegalArgumentException("Certification cannot be null");
        }
        if (!certificationSet.contains(certification)) {
            return;
        }
        certification.removeAgent();
        certificationSet.remove(certification);
    }

    /**
     * Add certification.
     *
     * @param certification the certification
     */
    protected void addCertification(Certification certification) {
        if (certification == null) {
            throw new IllegalArgumentException("certification cannot be null");
        }

        if (!certificationSet.contains(certification)) {
            certificationSet.add(certification);
            certification.setEstateAgent(this.getPerson());
        }
    }

    /**
     * Gets properties.
     *
     * @return the properties
     */
    public Set<Property> getProperties() {
        return Set.copyOf(this.agentProperties.values());
    }

    /**
     * Add employment.
     *
     * @param employment the employment
     */
    protected void addEmployment(Employment employment) {
        if (employment == null) {
            throw new IllegalArgumentException("employment cannot be null");
        }

        if (!this.employmentSet.contains(employment)) {
            this.employmentSet.add(employment);
            employment.setEstateAgent(this.getPerson());
        }
    }

    /**
     * Sell property.
     *
     * @param property the property
     */
    public void sellProperty(Property property) {
        Sale newSale = new Sale(property.getPrice(), LocalDate.now(), property, property.getEstateAgent().getPerson(), this.getPerson());
        this.addSale(newSale);
        newSale.getClient().setBudget(newSale.getClient().getBudget() - property.getPrice());
    }

    /**
     * Filter properties set.
     *
     * @param type     the type
     * @param minPrice the min price
     * @param maxPrice the max price
     * @param minArea  the min area
     * @param maxArea  the max area
     * @return the set
     */
    public Set<Property> filterProperties(String type, int minPrice, int maxPrice, int minArea, int maxArea) {
        if (type == null) throw new IllegalArgumentException("Type cannot be null");
        if (!type.equals("House") && !type.equals("Apartment"))
            throw new IllegalArgumentException("Type must be House or Apartment");
        return getProperties().stream().filter(e -> e.getPrice() > minPrice && e.getPrice() < maxPrice && e.getArea() > minArea && e.getArea() < maxArea && e.getClass().getSimpleName().equals(type)).collect(Collectors.toSet());
    }

    /**
     * Remove employment.
     *
     * @param employment the employment
     */
    public void removeEmployment(Employment employment) {
        if (employment == null) {
            throw new IllegalArgumentException("Employment cannot be null");
        }
        if (!this.employmentSet.contains(employment)) {
            return;
        }
        employment.removeAgent();
        this.employmentSet.remove(employment);
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
        sale.removeAgent();
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
            sale.setEstateAgent(this.getPerson());
        }
    }


}
