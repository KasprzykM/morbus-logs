package symbiosproduction.com.morbuslogs.database;

import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import symbiosproduction.com.morbuslogs.activity.HomeActivity;
import symbiosproduction.com.morbuslogs.database.model.log.SymptomsLog;
import symbiosproduction.com.morbuslogs.database.model.patient.Patient;

public class FirestoreWrapper{

    private final FirebaseFirestore DATABASE = FirebaseFirestore.getInstance();
    private static final String TAG = "FirestoreWrapper";
    private DocumentReference patientDocumentReference;
    private Patient patientData;

    //@TODO: Update or create it so that you can send every object that implements DBCollection

    //@TODO: This one actually has to be refactored like holy fuck you boosted animal monkey
    public void addPatientData(Patient patient)
    {
        patientDocumentReference = DATABASE.collection(patient.getCollection()).document(patient.getUid());
        patientData = patient;
    }

    public void sendPatientToDatabase(final Patient patient) {

        patientDocumentReference = DATABASE.collection(patient.getCollection()).document(patient.getUid());

        patientDocumentReference.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
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
        patientDocumentReference.update(patient.toMap())
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

        patientDocumentReference.set(patient.toMap())
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



    public void sendLogToDatabase(SymptomsLog symptomsLog, OnSuccessListener<Void> onSuccessListener, OnFailureListener onFailureListener)
    {
        FirebaseUser loggedUser = FirebaseAuth.getInstance().getCurrentUser();
        if(loggedUser != null) {
            DocumentReference symptomsLogReference = DATABASE.collection(symptomsLog.getCollection())
                    .document(loggedUser.getUid())
                    .collection(symptomsLog.getSubCollection())
                    .document(symptomsLog.getDateOfSubmission());

            symptomsLogReference.set(symptomsLog.toMap())
                    .addOnSuccessListener(onSuccessListener)
                    .addOnFailureListener(onFailureListener);
        }
    }
}
