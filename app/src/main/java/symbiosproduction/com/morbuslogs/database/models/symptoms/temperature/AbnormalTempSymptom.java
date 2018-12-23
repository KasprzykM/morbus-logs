package symbiosproduction.com.morbuslogs.database.models.symptoms.temperature;

import android.os.Parcel;
import android.os.Parcelable;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import symbiosproduction.com.morbuslogs.database.models.symptoms.AbstractSymptom;

public final class AbnormalTempSymptom extends AbstractSymptom {


    private Integer tempInCelsius;



    public AbnormalTempSymptom(String dateOfOccurrence,
                               Long duration,
                               String timeUnit,
                               String description,
                               Integer tempInCelsius,
                               String symptomName,
                               String photoPath,
                               String photoDbPath)
    {
        super(dateOfOccurrence,timeUnit,duration,description, symptomName, photoPath, photoDbPath);
        this.tempInCelsius = tempInCelsius;
    }

    public AbnormalTempSymptom(Map<String, Object> symptomInMap)
    {
        super(symptomInMap);
        Long tempLong = (Long) symptomInMap.get("tempInCelsius");
        this.tempInCelsius = new BigDecimal(tempLong).intValueExact();
    }

    public static final Parcelable.Creator<AbnormalTempSymptom> CREATOR
            = new Parcelable.Creator<AbnormalTempSymptom>() {
        public AbnormalTempSymptom createFromParcel(Parcel in) {
            return new AbnormalTempSymptom(in);
        }

        public AbnormalTempSymptom[] newArray(int size) {
            return new AbnormalTempSymptom[size];
        }
    };

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        dest.writeLong(tempInCelsius);
    }

    private AbnormalTempSymptom(Parcel in)
    {
        super(in);
        tempInCelsius = in.readInt();
    }

    public Integer getTempInCelsius() {
        return tempInCelsius;
    }


    @Override
    public Map<String, Object> toMap() {

        Map<String, Object> dataMap = new HashMap<>();

        dataMap.put("symptomName", symptomName);
        dataMap.put("dateOfOccurrence", dateOfOccurrence);
        dataMap.put("duration", duration);
        dataMap.put("timeUnit", timeUnit);
        dataMap.put("description", description);
        dataMap.put("tempInCelsius", tempInCelsius);
        dataMap.put("photoPath", photoPath);
        dataMap.put("photoDbPath", photoDbPath);

        return dataMap;
    }
}
