package symbiosproduction.com.morbuslogs.database;

import android.support.annotation.NonNull;
import android.util.Log;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import symbiosproduction.com.morbuslogs.database.model.patient.Patient;

public class FirestoreWrapper{

    private final FirebaseFirestore DATABASE = FirebaseFirestore.getInstance();
    private static final String TAG = "FirestoreWrapper";
    private DocumentReference documentReference;
    private Patient patientData;

    //@TODO: Update or create it so that you can send every object that implements DBCollection


    public void addData(Patient patient)
    {
        documentReference = DATABASE.collection(patient.getCollection()).document(patient.getUid());
        patientData = patient;
    }

    public void sendPatientToDatabase(final Patient patient) {

        documentReference = DATABASE.collection(patient.getCollection()).document(patient.getUid());

        documentReference.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot documentSnapshot = task.getResult();
                    if (documentSnapshot.exists()) {
                        updatePatient(patient);
                    }
                    else
                    {
                        createPatient(patient);
                    }
                }
            }
        });
    }


    public void updatePatient(Patient patient) {
        documentReference.update(patient.toMap())
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Log.d(TAG, "Success updating patient to the data base");
                    }
                }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Log.d(TAG, "Failed to update in database..");
            }
        });
    }

    public void createPatient(Patient patient) {

        documentReference.set(patient.toMap())
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Log.d(TAG, "Success creating user in data base");
                    }
                }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Log.d(TAG, "Failed to create user in data base");
                        }
        });
    }


}
