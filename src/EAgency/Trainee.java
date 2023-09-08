package EAgency;

import lombok.Getter;
import lombok.NonNull;

import java.time.LocalDate;

/**
 * The type Trainee.
 */
@Getter
public class Trainee extends Person {
    @NonNull
    private final LocalDate endOfInternship;

    private Company company;

    /**
     * Instantiates a new Trainee.
     *
     * @param name            the name
     * @param surname         the surname
     * @param pesel           the pesel
     * @param dateOfBirth     the date of birth
     * @param endOfInternship the end of internship
     */
    public Trainee(String name, String surname, String pesel, @NonNull LocalDate dateOfBirth, @NonNull LocalDate endOfInternship) {
        super(name, surname, pesel, dateOfBirth);
        this.endOfInternship = endOfInternship;
    }

    /**
     * Become agent person.
     *
     * @return the person
     */
    public Person becomeAgent() {
        return new Person(this);
    }


    /**
     * Sets company.
     *
     * @param company the company
     */
    public void setCompany(Company company) {
        if (this.company == null && company != null) {
            this.company = company;
            company.addTrainee(this);
        } else if (this.company != null && company == null) {
            Company tmp = this.company;
            this.company = null;
            tmp.removeTrainee(this);
        } else if (this.company != null) {
            Company tmp = this.company;
            this.company = null;
            tmp.removeTrainee(this);

            this.company = company;
            company.addTrainee(this);
        }
    }
}
