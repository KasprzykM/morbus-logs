package symbiosproduction.com.morbuslogs.fragment;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import symbiosproduction.com.morbuslogs.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class PainSymptomFragment extends Fragment {


    public PainSymptomFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.pain_symptom_fragment, container, false);
    }

}
