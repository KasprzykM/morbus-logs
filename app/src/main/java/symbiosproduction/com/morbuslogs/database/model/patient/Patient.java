package symbiosproduction.com.morbuslogs.database.model.patient;

import com.google.firebase.auth.FirebaseUser;

import java.util.HashMap;
import java.util.Map;

import symbiosproduction.com.morbuslogs.database.DatabaseInterface;

public class Patient implements DatabaseInterface {

    private String name;
    private String email;
    private String Uid;
    private Integer age;
    private Gender gender;

    private static final String DB_COLLECTION = "Patients";


    public Patient(FirebaseUser user, Integer age, Gender gender)
    {
        this.name = user.getDisplayName();
        this.email = user.getEmail();
        this.Uid = user.getUid();
        this.age = age;
        this.gender = gender;
    }


    @Override
    public Map<String, Object> toMap() {

        Map<String, Object> mapOfPatientFields = new HashMap<>();

        mapOfPatientFields.put("name",name);
        mapOfPatientFields.put("email",email);
        mapOfPatientFields.put("age",age);
        //@TODO:Fix gender issue.
       // mapOfPatientFields.put("gender",gender);


        return mapOfPatientFields;
    }

    public String getUid() {
        return Uid;
    }

    @Override
    public String getCollection() {
        return DB_COLLECTION;
    }
}
