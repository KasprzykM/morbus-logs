package symbiosproduction.com.morbuslogs.fragment.logs;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

import symbiosproduction.com.morbuslogs.R;
import symbiosproduction.com.morbuslogs.database.model.symptoms.AbstractSymptom;
import symbiosproduction.com.morbuslogs.fragment.logs.adapter.LogAdapter;
import symbiosproduction.com.morbuslogs.fragment.logs.interfaces.EditCallbacksAdapter;
import symbiosproduction.com.morbuslogs.fragment.symptom.NewSymptomFragment;

import static android.app.Activity.RESULT_OK;


public class NewLogFragment extends Fragment implements EditCallbacksAdapter {


    private static final String TAG = "NewLogFragment";
    private static int ADD_REQUEST_CODE_CONSTANT = 1243;
    private static int EDIT_REQUEST_CODE_CONSTANT = 1196;
    private static final String SYMPTOM_BUNDLE_CONSTANT = "symptom";

    private Button newSymptomButton;
    private ArrayList<AbstractSymptom> arrayOfSymptoms;
    private RecyclerView logRecView;
    private LogAdapter logAdapter;
    private TextView emptyView;
    private int indexOfSymptomToEdit;

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
            AbstractSymptom userInputSymptom = (AbstractSymptom) data.getExtras().get(SYMPTOM_BUNDLE_CONSTANT);
            if(requestCode == ADD_REQUEST_CODE_CONSTANT)
            {
                arrayOfSymptoms.add(userInputSymptom);
            }

            else if(requestCode == EDIT_REQUEST_CODE_CONSTANT)
            {
                arrayOfSymptoms.remove(indexOfSymptomToEdit);
                arrayOfSymptoms.add(indexOfSymptomToEdit,userInputSymptom);
            }

            logAdapter.notifyDataSetChanged();
        }
        checkForEmptyList();
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
                newSymptomFragment.setTargetFragment(NewLogFragment.this, ADD_REQUEST_CODE_CONSTANT);
                getActivity()
                        .getSupportFragmentManager()
                        .beginTransaction()
                        .add(R.id.const_layout_newlog_fragment, newSymptomFragment)
                        .addToBackStack(null)
                        .commit();
            }
        });
    }

    @Override
    public void onSymptomEdit(AbstractSymptom symptomToEdit) {
        Bundle bundleToSave = new Bundle();

        indexOfSymptomToEdit = arrayOfSymptoms.indexOf(symptomToEdit);

        bundleToSave.putParcelable("symptomToEdit", symptomToEdit);
        NewSymptomFragment editSymptomFragment = new NewSymptomFragment();
        editSymptomFragment.setArguments(bundleToSave);
        editSymptomFragment.setTargetFragment(this, EDIT_REQUEST_CODE_CONSTANT);
        getActivity().getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.const_layout_newlog_fragment, editSymptomFragment)
                .addToBackStack(null)
                .commit();
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
        emptyView = (TextView) view.findViewById(R.id.empty_log_view);
        checkForEmptyList();

        // create adapter
        logAdapter = new LogAdapter(arrayOfSymptoms, getContext(), this);
        logRecView.setAdapter(logAdapter);

        // create layout manager
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
        logRecView.setLayoutManager(mLayoutManager);

        // set additional animations
        logRecView.setItemAnimator(new DefaultItemAnimator());
        logRecView.addItemDecoration(new DividerItemDecoration(getContext(), LinearLayoutManager.VERTICAL));
    }


    private void checkForEmptyList()
    {
        if(arrayOfSymptoms.isEmpty())
        {
            logRecView.setVisibility(View.GONE);
            emptyView.setVisibility(View.VISIBLE);
        }
        else {
            logRecView.setVisibility(View.VISIBLE);
            emptyView.setVisibility(View.GONE);
        }
    }
}

