package symbiosproduction.com.morbuslogs.database.model.symptoms;


import symbiosproduction.com.morbuslogs.database.ToMap;

public abstract class AbstractSymptom implements ToMap {

        protected String dateOfOccurrence;
        protected Long duration;
        protected String description;
        protected String SYMPTOM_NAME;

    public String getSymptomName()
    {
        return SYMPTOM_NAME;
    }

    public String getDateOfOccurrence() {
        return dateOfOccurrence;
    }

    public void setDateOfOccurrence(String dateOfOccurrence) {
        this.dateOfOccurrence = dateOfOccurrence;
    }

    public Long getDuration() {
        return duration;
    }

    public void setDuration(Long duration) {
        this.duration = duration;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}
