package symbiosproduction.com.morbuslogs.database.models.symptoms;


import android.os.Parcel;
import android.os.Parcelable;

import java.util.Map;

import symbiosproduction.com.morbuslogs.database.ToMap;

public abstract class AbstractSymptom implements ToMap,Parcelable {

        protected String dateOfOccurrence;
        protected Long duration;
        protected String timeUnit;
        protected String description;
        protected String symptomName;


    public String getTimeUnit() {
        return timeUnit;
    }

    public String getSymptomName()
    {
        return symptomName;
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

    protected Long calculateDuration(Long duration, String timeUnit)
    {
        switch(timeUnit)
        {
            case "Seconds":
                //duration is already in seconds
                break;
            case "Minutes":
                duration *= 60;
                break;
            case "Hours":
                duration *= 60 * 60;
                break;
            case "Days":
                duration *= 24 * 60 * 60;
                break;
            case "Weeks":
                duration *= 7 * 24 * 60 * 60;
                break;
            case "Months":
                duration *= 30 * 7 * 24 * 60 * 60;
                break;
        }

        return duration;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(dateOfOccurrence);
        dest.writeLong(duration);
        dest.writeString(description);
        dest.writeString(symptomName);
        dest.writeString(timeUnit);
    }

    public  AbstractSymptom(String dateOfOccurrence, String timeUnit, Long duration, String description, String symptomName){
        this.dateOfOccurrence = dateOfOccurrence;
        this.duration = duration;
        this.description = description;
        this.symptomName = symptomName;
        this.timeUnit = timeUnit;
    }

    public AbstractSymptom(Map<String, Object> symptomInMap)
    {
       this.dateOfOccurrence = (String) symptomInMap.get("dateOfOccurrence");
       this.duration = (Long) symptomInMap.get("duration");
       this.description = (String) symptomInMap.get("description");
       this.symptomName = (String) symptomInMap.get("symptomName");
       this.timeUnit = (String) symptomInMap.get("timeUnit");

    }

    protected AbstractSymptom(Parcel in)
    {
       dateOfOccurrence = in.readString();
       duration = in.readLong();
       description = in.readString();
       symptomName = in.readString();
       timeUnit = in.readString();
    }
}
