package EAgency;

import lombok.Getter;

import java.util.Collections;
import java.util.Set;

/**
 * The type Training.
 */
@Getter
public class Training extends ObjectPlus {

    private String name, subject;
    private int difficulty;
    private Set<Certification> certificationSet;

    /**
     * Instantiates a new Training.
     *
     * @param name       the name
     * @param subject    the subject
     * @param difficulty the difficulty
     */
    public Training(String name, String subject, int difficulty) {
        super();
        setName(name);
        setSubject(subject);
        setDifficulty(difficulty);
        addToExtent();
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
     * Sets name.
     *
     * @param name the name
     */
    public void setName(String name) {
        Utils.checkIfStringIsNotNullAndNotBlank(name);
        this.name = name;
    }

    /**
     * Sets subject.
     *
     * @param subject the subject
     */
    public void setSubject(String subject) {
        Utils.checkIfStringIsNotNullAndNotBlank(subject);
        this.subject = subject;
    }

    /**
     * Sets difficulty.
     *
     * @param difficulty the difficulty
     */
    public void setDifficulty(int difficulty) {
        if (difficulty < 0 || difficulty > 10)
            throw new IllegalArgumentException("Difficulty should be between 0 and 10");
        this.difficulty = difficulty;
    }


    /**
     * Remove certification.
     *
     * @param certification the certification
     */
    protected void removeCertification(Certification certification) {
        if (certification == null) {
            throw new IllegalArgumentException("Certification cannot be null");
        }


        if (!certificationSet.contains(certification)) {
            return;
        }

        certification.removeTraining();
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
            certification.setTraining(this);
        }
    }
}
