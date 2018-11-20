package symbiosproduction.com.morbuslogs.database.model.patient;

import com.google.firebase.auth.FirebaseUser;

import java.util.HashMap;
import java.util.Map;

import symbiosproduction.com.morbuslogs.database.DBCollection;
import symbiosproduction.com.morbuslogs.database.ToMap;

public class Patient implements DBCollection,ToMap {

    private String name;
    private String email;
    private String Uid;
    private Integer age;
    private Gender gender;

    private static final String DB_COLLECTION_PATIENTS = "Patients";


    public Patient(FirebaseUser user, Integer age, Gender gender)
    {
        this.name = user.getDisplayName();
        this.email = user.getEmail();
        this.Uid = user.getUid();
        this.age = age;
        this.gender = gender;
    }

    public Patient(FirebaseUser user)
    {
        this.name = user.getDisplayName();
        this.email = user.getEmail();
        this.Uid = user.getUid();
    }


    @Override
    public Map<String, Object> toMap() {

        Map<String, Object> patientData = new HashMap<>();

        patientData.put("name",name);
        patientData.put("email",email);

        if(age != null)
            patientData.put("age", age);
        if(gender != null)
            patientData.put("gender",gender.toString());


        return patientData;
    }

    public String getUid() {
        return Uid;
    }

    @Override
    public String getCollection() {
        return DB_COLLECTION_PATIENTS;
    }
}
