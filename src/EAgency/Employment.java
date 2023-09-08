package EAgency;

import lombok.Getter;
import lombok.NonNull;

import java.time.LocalDate;

/**
 * The type Employment.
 */
@Getter
public class Employment extends ObjectPlus {
    @NonNull
    private final LocalDate dateFrom;
    private final LocalDate dateTo;
    private final boolean isActive;
    private String reasonOfChange;
    private Company company;
    private EstateAgent agent;

    /**
     * Instantiates a new Employment.
     *
     * @param dateFrom       the date from
     * @param dateTo         the date to
     * @param reasonOfChange the reason of change
     * @param company        the company
     * @param estateAgent    the estate agent
     */
    public Employment(@NonNull LocalDate dateFrom, LocalDate dateTo, String reasonOfChange, Company company, Person estateAgent) {
        super();
        this.dateFrom = dateFrom;
        this.dateTo = dateTo;
        this.isActive = this.dateTo == null;
        setEstateAgent(estateAgent);
        setCompany(company);
        setReasonOfChange(reasonOfChange);
        addToExtent();
    }

    /**
     * Sets reason of change.
     *
     * @param reasonOfChange the reason of change
     */
    public void setReasonOfChange(String reasonOfChange) {
        Utils.checkIfStringIsNotNullAndNotBlank(reasonOfChange);
        this.reasonOfChange = reasonOfChange;
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
        agent.addEmployment(this);
        this.agent = agent;
    }

    /**
     * Sets company.
     *
     * @param company the company
     */
    public void setCompany(Company company) {
        if (company == null) {
            throw new IllegalArgumentException("training cannot cannot be null");
        }

        company.addEmployment(this);
        this.company = company;
    }

    /**
     * Remove company.
     */
    public void removeCompany() {
        if (company != null) {
            Company temp = company;
            company = null;
            temp.removeEmployment(this);
        }
        if (agent != null) {
            removeAgent();
        }
        removeFromExtent();
    }

    /**
     * Remove agent.
     */
    public void removeAgent() {
        if (agent != null) {
            EstateAgent temp = agent;
            agent = null;
            temp.removeEmployment(this);
        }
        if (company != null) {
            removeCompany();
        }
        removeFromExtent();
    }

    @Override
    public String toString() {
        return agent.getPerson().getName() + " " + agent.getPerson().getSurname() + ", " + company.getName() + ", from: " + dateFrom + ", is active: " + isActive + ", to: " + dateTo;
    }
}
