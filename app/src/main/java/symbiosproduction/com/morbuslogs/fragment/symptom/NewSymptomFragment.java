package symbiosproduction.com.morbuslogs.fragment.symptom;


import java.util.Calendar;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import symbiosproduction.com.morbuslogs.R;
import symbiosproduction.com.morbuslogs.database.model.symptoms.AbstractSymptom;
import symbiosproduction.com.morbuslogs.database.model.symptoms.pain.PainSymptom;
import symbiosproduction.com.morbuslogs.database.model.symptoms.pain.PainType;
import symbiosproduction.com.morbuslogs.fragment.commonFragments.SelectDateFragment;
import symbiosproduction.com.morbuslogs.fragment.symptom.OnItemSelectedListeners.DurationOnItemSelectedListener;

import static android.app.Activity.RESULT_OK;

public class NewSymptomFragment extends Fragment implements AdapterView.OnItemSelectedListener{


    private Button datePickerButton;
    private Spinner durationSpinner;
    private Spinner symptomSpinner;
    private SelectDateFragment selectDateFragment;
    private EditText durationInput;
    private EditText descriptionInput;
    private AbstractSymptom symptom;
    private static final String SYMPTOM_BUNDLE_CONSTANT = "symptom";


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
        initEditText(view);
        submitButton(view);
    }

    public void initEditText(View view)
    {
        View.OnFocusChangeListener focusChanger = new View.OnFocusChangeListener(){
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(!hasFocus)
                {
                    hideKeyboard(v);
                }
            }
        };
        descriptionInput = (EditText) view.findViewById(R.id.descriptionInput);
        descriptionInput.setOnFocusChangeListener(focusChanger);
        durationInput = (EditText) view.findViewById(R.id.durationValueInput);
        durationInput.setOnFocusChangeListener(focusChanger);
    }

    public void submitButton(View view)
    {
        Button submitButton = (Button) view.findViewById(R.id.submit_button);
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(durationInput.getText().length() <= 0 || datePickerButton.getText().equals(getString(R.string.date_button_symptom)))
                {
                    Toast.makeText(getContext(), R.string.unfilled_fields_toast, Toast.LENGTH_LONG).show();
                    return;
                }


                // Initial data for abstract symptom
                Long duration = Long.parseLong(durationInput.getText().toString());
                String timeUnit = durationSpinner.getSelectedItem().toString();
                String description = descriptionInput.getText().toString();
                String date = selectDateFragment.getStringDate();
                String symptomName = symptomSpinner.getSelectedItem().toString();


                // Get fragment that has been chosen by user and fetch its data
                Fragment specificSymptomFragment = getChildFragmentManager().getFragments().get(0);

                // Construct PainSymptom Object
                if(specificSymptomFragment instanceof PainSymptomFragment)
                {
                    String painIntensity = ((PainSymptomFragment) specificSymptomFragment).getSelectedIntensity();
                    String stringToPainEnum = ((PainSymptomFragment) specificSymptomFragment).getSelectedPainType()
                            .toUpperCase().replace(" ", "_");
                    PainType painType = PainType.valueOf(stringToPainEnum);
                    symptom = new PainSymptom(painIntensity ,painType
                            ,description, duration
                            ,timeUnit
                            ,date,
                            symptomName);
                }


                //Go back to new log fragment
                if(symptom != null) {
                    Intent intent = new Intent(getContext(), NewSymptomFragment.class);
                    Bundle bundle = new Bundle();
                    bundle.putParcelable(SYMPTOM_BUNDLE_CONSTANT, symptom);
                    //test.putExtra("symptom", symptom);
                    intent.putExtras(bundle);
                    getTargetFragment().onActivityResult(getTargetRequestCode(), RESULT_OK, intent);
                    getActivity().getSupportFragmentManager().popBackStack();
                }

            }

        });
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


        durationSpinner = (Spinner) view.findViewById(R.id.timeUnitSpinner);
        ArrayAdapter<CharSequence> durationAdapter = ArrayAdapter.createFromResource(getActivity(),
                R.array.duration_array_symptom, android.R.layout.simple_spinner_item);
        durationAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        durationSpinner.setAdapter(durationAdapter);
        durationSpinner.setOnItemSelectedListener( new DurationOnItemSelectedListener());
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        Fragment fragment = null;
        switch(position)
        {
            //Pain
            case 0: {
                fragment = new PainSymptomFragment();
                break;
            }
            //Abnormal Temp
            case 1: {
                fragment = new AbnormalTempSymptomFragment();
                break;
            }
            default:
                break;
        }
        if(fragment != null)
            getChildFragmentManager()
                    .beginTransaction()
                    .replace(R.id.symptomOptionsLayout, fragment, fragment.getTag())
                    .commit();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    public void hideKeyboard(View view) {
        InputMethodManager inputMethodManager = (InputMethodManager) getContext().getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

}
