package symbiosproduction.com.morbuslogs.database;


import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

import symbiosproduction.com.morbuslogs.database.models.log.SymptomsLog;
import symbiosproduction.com.morbuslogs.database.models.patient.Patient;

public class FirestoreWrapper{

    private final FirebaseFirestore DATABASE = FirebaseFirestore.getInstance();
    private static final String TAG = "FirestoreWrapper";


    public void addPatientToDB(Patient patient, OnSuccessListener<Void> onSuccessListener, OnFailureListener onFailureListener)
    {
        DocumentReference userDocReference = DATABASE.collection(patient.getMainCollection())
                .document(patient.getUid());

        userDocReference.set(patient.toMap())
                .addOnSuccessListener(onSuccessListener)
                .addOnFailureListener(onFailureListener);
    }



    public void addLogToDB(SymptomsLog symptomsLog, OnSuccessListener<Void> onSuccessListener, OnFailureListener onFailureListener)
    {
        FirebaseUser loggedUser = FirebaseAuth.getInstance().getCurrentUser();
        if(loggedUser != null) {
            DocumentReference symptomsLogDocReference = DATABASE.collection(symptomsLog.getMainCollection())
                    .document(loggedUser.getUid())
                    .collection(symptomsLog.getSubCollection())
                    .document(symptomsLog.getDateOfSubmission());

            symptomsLogDocReference.set(symptomsLog.toMap())
                    .addOnSuccessListener(onSuccessListener)
                    .addOnFailureListener(onFailureListener);
        }
    }


    public void fetchSymptomsLog(String mainCollection, String subCollection, OnCompleteListener onCompleteListener) {
        FirebaseUser loggedUser = FirebaseAuth.getInstance().getCurrentUser();
        if (loggedUser != null) {
            DATABASE.collection(mainCollection)
                    .document(loggedUser.getUid())
                    .collection(subCollection).get().addOnCompleteListener(onCompleteListener);

        }
    }
}
