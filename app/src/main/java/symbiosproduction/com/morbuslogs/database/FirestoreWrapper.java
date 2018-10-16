package symbiosproduction.com.morbuslogs.database;

import android.support.annotation.NonNull;
import android.util.Log;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;

import symbiosproduction.com.morbuslogs.database.model.patient.Patient;

public class FirestoreWrapper{

    private final FirebaseFirestore DATABASE = FirebaseFirestore.getInstance();
    private static final String TAG = "FirestoreWrapper";


    //@TODO: Update or create it so that you can send every object that implements DatabaseInterface
    private void updateOrCreatePatient(Patient patient)
    {
        DATABASE.collection(patient.getCollection())
                .document(patient.getUid())
                .set(patient.toMap())
                .addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Log.d(TAG,"Success adding something to the database..");
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.d(TAG, "Failed to add to database");
            }
        });
    }

}
