package symbiosproduction.com.morbuslogs.dialogs;

import android.app.DialogFragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;


import symbiosproduction.com.morbuslogs.R;

public class TermsOfServiceDialog extends DialogFragment {

    private static final String TAG = "TermsOfServiceDialog";

    private TextView mDescriptionTextView;
    private Button mCloseButton;
    private String[] mDescriptionStringArray;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.dialog_terms_of_service,container, false);

        mCloseButton = (Button) view.findViewById(R.id.closeButton);
        mCloseButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view)
            {
                Log.d(TAG,"onClick: closing dialog");
                getDialog().dismiss();
            }
        });

        mDescriptionTextView = (TextView) view.findViewById(R.id.descriptionTextView);
        mDescriptionStringArray = getResources().getStringArray(R.array.description_textView_tos);

        StringBuilder stringBuilder = new StringBuilder();
        for(int i = 0; i< mDescriptionStringArray.length; ++i)
        {
            stringBuilder.append(mDescriptionStringArray[i]);
        }

        mDescriptionTextView.setText(stringBuilder);

        return view;
    }

}
