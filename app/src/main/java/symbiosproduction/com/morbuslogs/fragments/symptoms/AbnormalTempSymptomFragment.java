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

public class AbnormalTempSymptomFragment extends Fragment {


    private EditText mTempEditText;
    private int initTempValue = 0;
    private final int TEMP_ERROR_CODE = 0;

    public AbnormalTempSymptomFragment() {
        // Required empty public constructor
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mTempEditText = (EditText) view.findViewById(R.id.temperatureInput);
        mTempEditText.setText(Integer.toString(initTempValue));
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.abnormal_temp_symptom_fragment, container, false);
    }

    public int getTemperatureInput()
    {
        if (mTempEditText.getText().length() != 0)
            return Integer.parseInt(mTempEditText.getText().toString());
        else
            return TEMP_ERROR_CODE;
    }

    public void setTemperatureInput(Integer temperatureInput)
    {
        initTempValue = temperatureInput;
    }
}
