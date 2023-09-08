package EAgency;

import lombok.Getter;
import lombok.NonNull;

import java.time.LocalDate;
import java.time.Period;
import java.util.UUID;

/**
 * The type Person.
 */
@Getter
public class Person extends ObjectPlus {
    private final UUID ID;
    private String name, surname, pesel;
    @NonNull
    private LocalDate dateOfBirth;
    private Client client;
    private EstateAgent estateAgent;

    /**
     * Instantiates a new Person.
     *
     * @param name        the name
     * @param surname     the surname
     * @param pesel       the pesel
     * @param dateOfBirth the date of birth
     */
    public Person(String name, String surname, String pesel, @NonNull LocalDate dateOfBirth) {
        super();
        setName(name);
        setSurname(surname);
        setPesel(pesel);
        this.dateOfBirth = dateOfBirth;
        this.ID = UUID.randomUUID();
        addToExtent();
    }

    /**
     * Instantiates a new Person.
     *
     * @param name        the name
     * @param surname     the surname
     * @param pesel       the pesel
     * @param dateOfBirth the date of birth
     * @param budget      the budget
     */
    public Person(String name, String surname, String pesel, @NonNull LocalDate dateOfBirth, double budget) {
        super();
        setName(name);
        setSurname(surname);
        setPesel(pesel);
        this.dateOfBirth = dateOfBirth;
        this.ID = UUID.randomUUID();
        this.client = new Client(budget, this);
        addToExtent();
    }

    /**
     * Instantiates a new Person.
     *
     * @param name        the name
     * @param surname     the surname
     * @param pesel       the pesel
     * @param dateOfBirth the date of birth
     * @param provision   the provision
     */
    public Person(String name, String surname, String pesel, @NonNull LocalDate dateOfBirth, float provision) {
        super();
        setName(name);
        setSurname(surname);
        setPesel(pesel);
        this.dateOfBirth = dateOfBirth;
        this.ID = UUID.randomUUID();
        this.estateAgent = new EstateAgent(provision, this);
        addToExtent();
    }

    /**
     * Instantiates a new Person.
     *
     * @param name        the name
     * @param surname     the surname
     * @param pesel       the pesel
     * @param dateOfBirth the date of birth
     * @param provision   the provision
     * @param budget      the budget
     */
    public Person(String name, String surname, String pesel, @NonNull LocalDate dateOfBirth, float provision, double budget) {
        super();
        setName(name);
        setSurname(surname);
        setPesel(pesel);
        this.dateOfBirth = dateOfBirth;
        this.ID = UUID.randomUUID();
        this.estateAgent = new EstateAgent(provision, this);
        this.client = new Client(budget, this);
        addToExtent();
    }

    /**
     * Instantiates a new Person.
     *
     * @param trainee the trainee
     */
    public Person(Trainee trainee) {
        super();
        this.ID = UUID.randomUUID();
        setName(trainee.getName());
        setSurname(trainee.getSurname());
        setPesel(trainee.getPesel());
        setDateOfBirth(trainee.getDateOfBirth());
        this.estateAgent = new EstateAgent(0.01f, this);
        trainee.removeFromExtent();
        addToExtent();
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
     * Sets surname.
     *
     * @param surname the surname
     */
    public void setSurname(String surname) {
        Utils.checkIfStringIsNotNullAndNotBlank(surname);
        this.surname = surname;
    }

    /**
     * Sets pesel.
     *
     * @param pesel the pesel
     */
    public void setPesel(String pesel) {
        Utils.checkIfStringIsNotNullAndNotBlank(pesel);
        if (pesel.length() != 11) throw new IllegalArgumentException("Pesel number must be 11 character long");
        this.pesel = pesel;
    }

    /**
     * Sets date of birth.
     *
     * @param dateOfBirth the date of birth
     */
    public void setDateOfBirth(LocalDate dateOfBirth) {
        if (dateOfBirth.isAfter(LocalDate.now()) || Period.between(dateOfBirth, LocalDate.now()).getYears() < 18) {
            System.out.println(Period.between(LocalDate.now(), dateOfBirth).getYears());
            throw new IllegalArgumentException("Date of birth cannot be in the future and user cannot be under age.");
        }

        this.dateOfBirth = dateOfBirth;
    }

    /**
     * Gets client.
     *
     * @return the client
     */
    public Client getClient() {
        if (client == null) return null;
        return client;
    }

    /**
     * Gets estate agent.
     *
     * @return the estate agent
     */
    public EstateAgent getEstateAgent() {
        if (estateAgent == null) return null;
        return estateAgent;
    }

    /**
     * Is the same client boolean.
     *
     * @return the boolean
     */
    public boolean isTheSameClient() {
        if (getClient() != null && getEstateAgent() != null)
            return getClient().getPerson().getID() == getEstateAgent().getPerson().getID();
        return false;
    }

    @Override
    public String toString() {
        return name + " " + surname;
    }

}
