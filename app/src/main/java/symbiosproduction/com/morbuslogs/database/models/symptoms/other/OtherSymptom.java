package symbiosproduction.com.morbuslogs.database.models.symptoms.other;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.HashMap;
import java.util.Map;

import symbiosproduction.com.morbuslogs.database.models.symptoms.AbstractSymptom;

public final class OtherSymptom extends AbstractSymptom {



    private String nameOfUser;

    public OtherSymptom(String dateOfOccurrence,
                        String timeUnit,
                        Long duration,
                        String description,
                        String symptomName,
                        String photoPath,
                        String photoDbPath,
                        String nameOfUser) {
        super(dateOfOccurrence, timeUnit, duration, description, symptomName, photoPath, photoDbPath);
        this.nameOfUser = nameOfUser;
    }

    public OtherSymptom(Map<String, Object> symptomInMap) {
        super(symptomInMap);
        this.nameOfUser = (String) symptomInMap.get("nameOfUser");
    }

    protected OtherSymptom(Parcel in) {
        super(in);
        in.writeString(nameOfUser);
    }

    public static final Parcelable.Creator<OtherSymptom> CREATOR
            = new Parcelable.Creator<OtherSymptom>() {
        public OtherSymptom createFromParcel(Parcel in) {
            return new OtherSymptom(in);
        }

        public OtherSymptom[] newArray(int size) {
            return new OtherSymptom[size];
        }
    };

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        dest.writeString(nameOfUser);
    }


    @Override
    public Map<String, Object> toMap() {
        Map<String, Object> dataMap = new HashMap<>();
        dataMap.put("symptomName", symptomName);
        dataMap.put("dateOfOccurrence", dateOfOccurrence);
        dataMap.put("duration", duration);
        dataMap.put("timeUnit", timeUnit);
        dataMap.put("description", description);
        dataMap.put("photoPath", photoPath);
        dataMap.put("photoDbPath", photoDbPath);
        dataMap.put("nameOfUser", nameOfUser);
        return dataMap;
    }

    public String getNameOfUser() {
        return nameOfUser;
    }

    public void setNameOfUser(String nameOfUser) {
        this.nameOfUser = nameOfUser;
    }
}
