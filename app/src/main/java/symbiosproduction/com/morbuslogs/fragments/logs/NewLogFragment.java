package symbiosproduction.com.morbuslogs.fragments.logs;


import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.UploadTask;

import java.util.ArrayList;

import symbiosproduction.com.morbuslogs.R;
import symbiosproduction.com.morbuslogs.database.FirestoreWrapper;
import symbiosproduction.com.morbuslogs.database.models.log.SymptomsLog;
import symbiosproduction.com.morbuslogs.database.models.symptoms.AbstractSymptom;
import symbiosproduction.com.morbuslogs.fragments.logs.adapters.LogAdapter;
import symbiosproduction.com.morbuslogs.fragments.logs.commInterfaces.EditLogCallbacksAdapter;
import symbiosproduction.com.morbuslogs.fragments.symptoms.NewSymptomFragment;

import static android.app.Activity.RESULT_OK;


public class NewLogFragment extends Fragment implements EditLogCallbacksAdapter {


    private static final String TAG = "NewLogFragment";
    private static int ADD_REQUEST_CODE_CONSTANT = 1243;
    private static int EDIT_REQUEST_CODE_CONSTANT = 1196;
    private static final String SYMPTOM_BUNDLE_CONSTANT = "symptom";

    private Button newSymptomButton;
    private Button mLogToDbButton;
    private ArrayList<AbstractSymptom> arrayOfSymptoms;
    private RecyclerView logRecView;
    private LogAdapter logAdapter;
    private TextView emptyView;
    private EditText mEditTitle;
    private int indexOfSymptomToEdit;
    private ProgressBar mSpinnerProgress;
    private String dateOfSubmissionToEdit;
    private Boolean mEditMode = false;

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
        mEditTitle = (EditText) view.findViewById(R.id.log_title_fragment);
        initArrayOfSymptoms(savedInstanceState);
        initRecyclerView(view);
        initProgressBar(view);


        initDebugData();

        // Add new symptom
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


        // Add log to db
        mLogToDbButton = (Button) view.findViewById(R.id.save_to_db_btn);
        mLogToDbButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getContext(), android.R.style.Theme_Material_Dialog_Alert);
                alertDialogBuilder.setCancelable(true);

                String message = getResources().getString(R.string.message_add_log_dialog);
                if(mEditTitle.length() == 0)
                    message += getResources().getString(R.string.blank_title_message_log_dialog);


                alertDialogBuilder.setMessage(message);
                alertDialogBuilder.setTitle(R.string.title_add_log_dialog);

                alertDialogBuilder.setIcon(R.drawable.ic_warning_white_24dp);

                alertDialogBuilder.setPositiveButton(R.string.confirm_opt_add_log_dialog,
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                mSpinnerProgress.setVisibility(View.VISIBLE);
                                symptomsToDb();
                            }
                        });

                alertDialogBuilder.setNegativeButton(R.string.cancel_opt_add_log_dialog,
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });

                AlertDialog alertDialog = alertDialogBuilder.create();
                alertDialog.show();
            }
        });

    }

    private void symptomsToDb()
    {
        if(arrayOfSymptoms.size() == 0)
        {
            Toast.makeText(getContext(), R.string.add_atleast_one_log_toast, Toast.LENGTH_LONG).show();
            mSpinnerProgress.setVisibility(View.GONE);
            return;
        }
        FirestoreWrapper firestoreWrapper = new FirestoreWrapper();
        SymptomsLog symptomsLog = new SymptomsLog(mEditTitle.getText().toString());
        symptomsLog.addSymptomList(arrayOfSymptoms);

        OnSuccessListener<Void> onSuccessListenerDB = new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Log.d(TAG, "Success adding symptom log to database");
                Toast.makeText(getContext(), R.string.success_adding_toast, Toast.LENGTH_SHORT).show();
                if(mEditMode)
                    getTargetFragment().onActivityResult(getTargetRequestCode(), RESULT_OK, null);
                getActivity().getSupportFragmentManager().popBackStack();
                // To refresh when we do the data swap again
                /*if(mEditMode)
                    getTargetFragment().onActivityResult(getTargetRequestCode(), RESULT_OK, null);
                getActivity().getSupportFragmentManager().popBackStack();*/
            }
        };

        OnSuccessListener<UploadTask.TaskSnapshot> onSuccessListenerPhoto = new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                mSpinnerProgress.setVisibility(View.GONE);
            }
        };

        OnFailureListener onFailureListenerDB = new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.e(TAG, "Failure adding symptom log to database or uploading photo");
                mSpinnerProgress.setVisibility(View.GONE);
            }
        };
        if(mEditMode)
        {
            symptomsLog.setDateOfSubmission(dateOfSubmissionToEdit);
            firestoreWrapper.updateLogDB(symptomsLog, onSuccessListenerDB, onFailureListenerDB);
            firestoreWrapper.uploadPhotos(symptomsLog, onSuccessListenerPhoto, onFailureListenerDB, getContext());
        }
        else {
            firestoreWrapper.addLogToDB(symptomsLog, onSuccessListenerDB, onFailureListenerDB);
            firestoreWrapper.uploadPhotos(symptomsLog, onSuccessListenerPhoto, onFailureListenerDB, getContext());
        }
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


    private void initDebugData()
    {
//        AbnormalTempSymptom tempSymptom1 = new AbnormalTempSymptom("04/04/1996", 15L, "Minutes",
//                "Pointless Description", 44, "Temperature");
//
//        AbnormalTempSymptom tempSymptom2 = new AbnormalTempSymptom("26/04/1996", 45L, "Seconds",
//                "Rafal Urodziny", 35, "Temperature");
//
//        PainSymptom painSymptom1 = new PainSymptom("Mild", PainType.BREAKTHROUGH, "Pain description",
//                3L, "Hours", "20/12/2018", "Pain");
//
//        PainSymptom painSymptom2 = new PainSymptom("Maximum", PainType.BONE, "Long fulfilling poem",
//                90L, "Seconds", "01/01/2018", "Pain");
//
//        arrayOfSymptoms.add(tempSymptom1);
//        arrayOfSymptoms.add(painSymptom1);
//        arrayOfSymptoms.add(tempSymptom2);
//        arrayOfSymptoms.add(painSymptom2);
//        logAdapter.notifyDataSetChanged();
//        checkForEmptyList();

    }

    private void initProgressBar(View view)
    {
        mSpinnerProgress = (ProgressBar) view.findViewById(R.id.progressBarNewLog);
        mSpinnerProgress.setVisibility(View.GONE);
    }

    private void initArrayOfSymptoms(Bundle savedInstanceState)
    {
        // list to edit
        Bundle editArguments = getArguments();
        if(editArguments != null) {
            arrayOfSymptoms = editArguments.getParcelableArrayList("arrayOfSymptoms");
            mEditTitle.setText(editArguments.getString("title"));
            dateOfSubmissionToEdit = editArguments.getString("date");
            mEditMode = true;
        }
        // empty list
        else if(savedInstanceState == null || !savedInstanceState.containsKey("arrayOfSymptoms"))
        {
            arrayOfSymptoms = new ArrayList<>();
        }
        // restoring list from rotation
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

