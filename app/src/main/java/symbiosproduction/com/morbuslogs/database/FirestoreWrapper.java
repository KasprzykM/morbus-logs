package symbiosproduction.com.morbuslogs.database;


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;


import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

import symbiosproduction.com.morbuslogs.database.models.log.SymptomsLog;
import symbiosproduction.com.morbuslogs.database.models.patient.Patient;
import symbiosproduction.com.morbuslogs.database.models.symptoms.AbstractSymptom;

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


    public void getSymptomsLog(String mainCollection, String subCollection, OnCompleteListener onCompleteListener) {
        FirebaseUser loggedUser = FirebaseAuth.getInstance().getCurrentUser();
        if (loggedUser != null) {
            DATABASE.collection(mainCollection)
                    .document(loggedUser.getUid())
                    .collection(subCollection).get().addOnCompleteListener(onCompleteListener);

        }
    }

    public void deleteSymptomsLog(SymptomsLog symptomsLog, OnSuccessListener<Void> onSuccessListener, OnFailureListener onFailureListener)
    {
        FirebaseUser loggedUser = FirebaseAuth.getInstance().getCurrentUser();
        if(loggedUser != null)
        {
            DATABASE.collection(symptomsLog.DB_MAIN_COLLECTION)
                    .document(loggedUser.getUid())
                    .collection(SymptomsLog.DB_SUB_COLLECTION)
                    .document(symptomsLog.getDateOfSubmission())
                    .delete().addOnSuccessListener(onSuccessListener).addOnFailureListener(onFailureListener);
        }
    }


    public void updateLogDB(SymptomsLog symptomsLog, OnSuccessListener<Void> onSuccessListener, OnFailureListener onFailureListener)
    {
        FirebaseUser loggedUser = FirebaseAuth.getInstance().getCurrentUser();
        if (loggedUser != null) {
            DATABASE.collection(SymptomsLog.DB_MAIN_COLLECTION)
                    .document(loggedUser.getUid())
                    .collection(SymptomsLog.DB_SUB_COLLECTION)
                    .document(symptomsLog.getDateOfSubmission())
                    .update(symptomsLog.toMap())
                    .addOnSuccessListener(onSuccessListener).addOnFailureListener(onFailureListener);
        }
    }

    public void deletePhotos(SymptomsLog symptomsLog)
    {
        FirebaseStorage storage = FirebaseStorage.getInstance();
        List<String> photoDBPath = symptomsLog.getPhotoReferences();
        List<String> photoFilePath = symptomsLog.getPhotoFileReferences();
        for(int i = 0; i < photoFilePath.size() ; i++)
        {
            StorageReference storageReference = storage.getReference();
            StorageReference imageReference = storageReference.child(photoDBPath.get(i));
            imageReference.delete();
        }
    }

    public void deletePhotos(AbstractSymptom abstractSymptom)
    {
        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference storageReference = storage.getReference();
        StorageReference imageReference = storageReference.child(abstractSymptom.getPhotoDbPath());
        imageReference.delete();
    }


    public void uploadPhotos(SymptomsLog symptomsLog, OnSuccessListener<UploadTask.TaskSnapshot> onSuccessListener, OnFailureListener onFailureListener, Context context)
    {
        FirebaseStorage storage = FirebaseStorage.getInstance();
        Bitmap bitmapOfPhoto;
        List<String> photoDBPath = symptomsLog.getPhotoReferences();
        List<String> photoFilePath = symptomsLog.getPhotoFileReferences();


        for(int i = 0; i < photoFilePath.size(); i++)
        {

            // Storage references
            StorageReference storageReference = storage.getReference();
            StorageReference imageReference = storageReference.child(photoDBPath.get(i));

            // File references
            Uri imageUri = Uri.parse(photoFilePath.get(i));
            try {
                // Fetch file from memory
                //TODO: Fix so files larger than 4096x4096 size don't get uploaded.. no point doing it anyway
                bitmapOfPhoto = MediaStore.Images.Media.getBitmap(context.getContentResolver(), imageUri);

                // Turn it into byte array
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                bitmapOfPhoto.compress(Bitmap.CompressFormat.JPEG, 100, baos);
                byte[] data = baos.toByteArray();

                //Upload it
                UploadTask uploadTask = imageReference.putBytes(data);
                uploadTask.addOnSuccessListener(onSuccessListener)
                        .addOnFailureListener(onFailureListener);

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void fetchPhoto(String dbPath, OnSuccessListener<byte[]> onSuccessListener)
    {
        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference storageRef = storage.getReference();
        StorageReference pathReference = storageRef.child(dbPath);
        final long FIVE_MEGABYTES = 1024 * 1024 * 5;
        pathReference.getBytes(FIVE_MEGABYTES).addOnSuccessListener(onSuccessListener);
    }
}
