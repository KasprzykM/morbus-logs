package symbiosproduction.com.morbuslogs.fragments.symptoms;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import symbiosproduction.com.morbuslogs.R;


public class OtherSymptomFragment extends Fragment {

    private static final String TAG = "PainSymptomFragment";
    private EditText nameEditText;
    private String initName = "";

    public OtherSymptomFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.other_symptom_fragment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        nameEditText = view.findViewById(R.id.chosen_name_edit_text);
        nameEditText.setText(initName);
    }

    public String getName()
    {
        return nameEditText.getText().toString();
    }

    public void setName(String arg)
    {
        initName = arg;
    }
}
