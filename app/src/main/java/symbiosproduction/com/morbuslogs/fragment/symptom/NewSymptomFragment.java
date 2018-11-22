package symbiosproduction.com.morbuslogs.fragment.symptom;


import java.util.Calendar;
import java.util.TimeZone;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Spinner;

import symbiosproduction.com.morbuslogs.R;
import symbiosproduction.com.morbuslogs.fragment.commonFragments.SelectDateFragment;

public class NewSymptomFragment extends Fragment implements AdapterView.OnItemSelectedListener{



    private Button datePickerButton;
    private Spinner symptomSpinner;
    private Spinner durationSpinner;
    private Calendar calendar;
    private SelectDateFragment selectDateFragment;

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

        initSpinners(view);
        initDatePicker(view);

    }

    public void initDatePicker(View view)
    {
        datePickerButton = (Button) view.findViewById(R.id.dateChooserButton);
        datePickerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar cal = Calendar.getInstance();
                selectDateFragment = new SelectDateFragment();
                selectDateFragment.setButton(datePickerButton);
                DatePickerDialog datePicker = new DatePickerDialog(getActivity(),
                        selectDateFragment,
                        cal.get(Calendar.YEAR),
                        cal.get(Calendar.MONTH),
                        cal.get(Calendar.DAY_OF_MONTH));
                datePicker.show();
            }
        });
    }

    public void initSpinners(View view)
    {
        symptomSpinner = (Spinner) view.findViewById(R.id.symptomNameSpinner);
        ArrayAdapter<CharSequence> symptomAdapter = ArrayAdapter.createFromResource(getActivity(),
                R.array.symptom_array, android.R.layout.simple_spinner_item);
        symptomAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        symptomSpinner.setAdapter(symptomAdapter);
        symptomSpinner.setOnItemSelectedListener(this);


        durationSpinner = (Spinner) view.findViewById(R.id.durationSpinner);
        ArrayAdapter<CharSequence> durationAdapter = ArrayAdapter.createFromResource(getActivity(),
                R.array.duration_array_symptom, android.R.layout.simple_spinner_item);
        durationAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        durationSpinner.setAdapter(durationAdapter);
        durationSpinner.setOnItemSelectedListener( new DurationOnItemSelectedListener());
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
