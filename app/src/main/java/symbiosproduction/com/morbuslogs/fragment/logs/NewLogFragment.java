package symbiosproduction.com.morbuslogs.fragment.logs;


import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;

import java.util.ArrayList;

import symbiosproduction.com.morbuslogs.R;
import symbiosproduction.com.morbuslogs.database.model.symptoms.AbstractSymptom;
import symbiosproduction.com.morbuslogs.database.model.symptoms.pain.PainSymptom;
import symbiosproduction.com.morbuslogs.fragment.logs.adapter.LogAdapter;
import symbiosproduction.com.morbuslogs.fragment.symptom.NewSymptomFragment;

import static android.app.Activity.RESULT_OK;


public class NewLogFragment extends Fragment {


    private static final String TAG = "NewLogFragment";
    private static int REQUEST_CODE_CONSTANT = 1243;
    private static final String SYMPTOM_BUNDLE_CONSTANT = "symptom";

    private Button newSymptomButton;
    private ArrayList<AbstractSymptom> arrayOfSymptoms;
    private RecyclerView logRecView;
    private LogAdapter logAdapter;

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
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == RESULT_OK)
        {
            if(requestCode == REQUEST_CODE_CONSTANT)
            {
                AbstractSymptom userInputSymptom = (AbstractSymptom) data.getExtras().get(SYMPTOM_BUNDLE_CONSTANT);

                //@TODO: Start to create list view with logAdapter out of it..
                if(userInputSymptom instanceof PainSymptom)
                {
                    arrayOfSymptoms.add(userInputSymptom);
                    logAdapter.notifyDataSetChanged();
                    System.out.println("dzialaj pls");
                }
            }
        }
    }


    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList("arrayOfSymptoms", arrayOfSymptoms);
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initArrayOfSymptoms(savedInstanceState);
        initRecyclerView(view);


        newSymptomButton = (Button) view.findViewById(R.id.btn_new_symptom);
        newSymptomButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO: Potential bug in here getActivity() <-> getChildFragmentManager()
                NewSymptomFragment newSymptomFragment = new NewSymptomFragment();
                newSymptomFragment.setTargetFragment(NewLogFragment.this, REQUEST_CODE_CONSTANT);
                getActivity()
                        .getSupportFragmentManager()
                        .beginTransaction()
                        .add(R.id.const_layout_newlog_fragment, newSymptomFragment)
                        .addToBackStack(null)
                        .commit();
            }
        });
    }

    private void initArrayOfSymptoms(Bundle savedInstanceState)
    {
        // empty list
        if(savedInstanceState == null || !savedInstanceState.containsKey("arrayOfSymptoms"))
        {
            arrayOfSymptoms = new ArrayList<>();
        }
        // restoring list
        else
        {
            arrayOfSymptoms = savedInstanceState.getParcelableArrayList("arrayOfSymptoms");
        }
    }

    private void initRecyclerView(View view)
    {
        // find id
        logRecView = (RecyclerView) view.findViewById(R.id.log_recycler_view);

        // create adapter
        logAdapter = new LogAdapter(arrayOfSymptoms);
        logRecView.setAdapter(logAdapter);

        // create layout manager
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
        logRecView.setLayoutManager(mLayoutManager);

        // set additional animations
        logRecView.setItemAnimator(new DefaultItemAnimator());
        logRecView.addItemDecoration(new DividerItemDecoration(getContext(), LinearLayoutManager.VERTICAL));
    }
}
