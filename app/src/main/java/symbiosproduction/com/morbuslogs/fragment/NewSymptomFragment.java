package symbiosproduction.com.morbuslogs.fragment;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import symbiosproduction.com.morbuslogs.R;

public class NewSymptomFragment extends Fragment implements AdapterView.OnItemSelectedListener{


    private static final String TAG = "NewSymptomFragment";

    public NewSymptomFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.new_symptom_fragment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Spinner spinner = (Spinner) view.findViewById(R.id.symptomNameSpinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getActivity(),
                R.array.symptom_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        switch(position)
        {
            //Pain
            case 0: {
                getChildFragmentManager()
                        .beginTransaction()
                        .replace(R.id.symptomOptionsLayout, new PainSymptomFragment())
                        .commit();
                break;
            }
            //Abnormal Temp
            case 1:
            {
                getChildFragmentManager()
                        .beginTransaction()
                        .replace(R.id.symptomOptionsLayout, new AbnormalTempSymptomFragment())
                        .commit();
            }
            default:
                break;
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
