package symbiosproduction.com.morbuslogs.fragments.commonFragments;


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.google.android.gms.tasks.OnSuccessListener;

import java.io.IOException;
import symbiosproduction.com.morbuslogs.R;
import symbiosproduction.com.morbuslogs.database.FirestoreWrapper;

public class PhotoPreviewFragment extends Fragment {

    Bitmap bitmap;
    ImageView imageToPreview;
    ProgressBar spinner;


    public PhotoPreviewFragment() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_photo_preview, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        imageToPreview = (ImageView) view.findViewById(R.id.image_to_preview);
        spinner = (ProgressBar) view.findViewById(R.id.spinner_photo);
        spinner.setVisibility(View.VISIBLE);
        if(bitmap != null)
        {
            imageToPreview.setImageBitmap(bitmap);
            spinner.setVisibility(View.GONE);
        }
    }

    public void setFilePath(String filePath, Context context, String optionalDbPath) {
        Uri imageUri = Uri.parse(filePath);
        try {
            bitmap = MediaStore.Images.Media.getBitmap(context.getContentResolver(), imageUri);
        } catch (IOException e) {
            e.printStackTrace();
            FirestoreWrapper firestoreWrapper = new FirestoreWrapper();
            firestoreWrapper.fetchPhoto(optionalDbPath, new OnSuccessListener<byte[]>() {
                @Override
                public void onSuccess(byte[] bytes) {
                    bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                    imageToPreview.setImageBitmap(bitmap);
                    spinner.setVisibility(View.GONE);
                }
            });
        }catch (IllegalStateException e)
        {
            e.printStackTrace();
        }
    }
}
