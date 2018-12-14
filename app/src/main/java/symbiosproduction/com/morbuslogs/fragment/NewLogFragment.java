package symbiosproduction.com.morbuslogs.fragment;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import symbiosproduction.com.morbuslogs.R;
import symbiosproduction.com.morbuslogs.fragment.symptom.NewSymptomFragment;


public class NewLogFragment extends Fragment {


    private static final String TAG = "NewLogFragment";
    private FloatingActionButton fab;

    public NewLogFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.new_log_fragment, container, false);
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        fab = (FloatingActionButton) view.findViewById(R.id.fl_btn_new_symptom);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG,"Inside floating button trying to start new fragment..");
                //TODO: Potential bug in here getActivity() <-> getChildFragmentManager()
                getActivity()
                        .getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.const_layout_newlog_fragment, new NewSymptomFragment())
                        .addToBackStack(null)
                        .commit();
            }
        });
    }

}
