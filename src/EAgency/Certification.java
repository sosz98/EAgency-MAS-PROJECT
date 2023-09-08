package EAgency;

import lombok.Getter;

import java.time.LocalDate;
import java.util.UUID;

/**
 * The type Certification.
 */
@Getter
public class Certification extends ObjectPlus {

    private final UUID id;
    private final LocalDate dateFrom, dateTo;
    private EstateAgent agent;
    private Training training;
    private String reasonOfChange;
    private double score;

    /**
     * Instantiates a new Certification.
     * @param reasonOfChange the reason of change
     * @param score          the score
     * @param agent          the agent
     * @param training       the training
     */
    public Certification(String reasonOfChange, double score, Person agent, Training training) {
        super();
        this.dateFrom = LocalDate.now();
        this.dateTo = LocalDate.now().plusYears(5);
        setReasonOfChange(reasonOfChange);
        setScore(score);
        setEstateAgent(agent);
        setTraining(training);
        this.id = UUID.randomUUID();
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
     * Sets score.
     *
     * @param score the score
     */
    public void setScore(double score) {
        if (score < 80 || score > 100) throw new IllegalArgumentException("Score should be between 80 and 100");
        this.score = score;
    }

    /**
     * Sets estate agent.
     *
     * @param person the person
     */
    public void setEstateAgent(Person person) {
        if (person == null) {
            throw new IllegalArgumentException("Agent cannot cannot be null");
        }
        person.getEstateAgent().addCertification(this);
        this.agent = person.getEstateAgent();
    }

    /**
     * Sets training.
     *
     * @param training the training
     */
    public void setTraining(Training training) {
        if (training == null) {
            throw new IllegalArgumentException("training cannot cannot be null");
        }

        if (this.training != null) {
            if (this.training.equals(training)) {
                return;
            }
            throw new IllegalArgumentException("This certification has already defined training.");
        }
        training.addCertification(this);
        this.training = training;
    }

    /**
     * Remove training.
     */
    public void removeTraining() {
        if (training != null) {
            Training temp = training;
            training = null;
            temp.removeCertification(this);
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
            temp.removeCertification(this);
        }
        if (training != null) {
            removeTraining();
        }
        removeFromExtent();
    }


}
