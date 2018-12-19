package symbiosproduction.com.morbuslogs.fragment.symptom;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import symbiosproduction.com.morbuslogs.R;
import symbiosproduction.com.morbuslogs.fragment.symptom.OnItemSelectedListeners.PainIntensityOnItemSelectedListener;
import symbiosproduction.com.morbuslogs.fragment.symptom.OnItemSelectedListeners.PainTypeOnItemSelectedListener;

public class PainSymptomFragment extends Fragment {


    private Spinner intensitySpinner;
    private Spinner painTypeSpinner;
    private ArrayAdapter<CharSequence> intensityAdapter;
    private ArrayAdapter<CharSequence> painTypeAdapter;
    private String initPainTypePos;
    private String initIntensityPos;

    private static final String TAG = "PainSymptomFragment";

    public PainSymptomFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.pain_symptom_fragment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initSpinners(view);
    }

    public void initSpinners(View view){
        intensitySpinner = (Spinner) view.findViewById(R.id.intensitySpinner);
        intensityAdapter = ArrayAdapter.createFromResource(getActivity(),
                R.array.pain_intensity_array, android.R.layout.simple_spinner_item);
        intensityAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        intensitySpinner.setAdapter(intensityAdapter);
        intensitySpinner.setOnItemSelectedListener( new PainIntensityOnItemSelectedListener());

        painTypeSpinner = (Spinner) view.findViewById(R.id.painTypeSpinner);
        painTypeAdapter = ArrayAdapter.createFromResource(getActivity(),
                R.array.pain_type_array, android.R.layout.simple_spinner_item);
        painTypeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        painTypeSpinner.setAdapter(painTypeAdapter);
        painTypeSpinner.setOnItemSelectedListener( new PainTypeOnItemSelectedListener());

        if(initPainTypePos != null && initIntensityPos != null) {
            intensitySpinner.setSelection(intensityAdapter.getPosition(initIntensityPos));
            painTypeSpinner.setSelection(painTypeAdapter.getPosition(initPainTypePos));
        }

    }

    public String getSelectedIntensity()
    {
        return intensitySpinner.getSelectedItem().toString();
    }

    public String getSelectedPainType()
    {
        return painTypeSpinner.getSelectedItem().toString();
    }


    public void setSelectedIntensity(String intensity)
    {
        initIntensityPos = intensity;
    }

    public void setSelectedPainType(String painType)
    {
        initPainTypePos = painType;
    }

}
