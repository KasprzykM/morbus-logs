package symbiosproduction.com.morbuslogs.fragments.logs;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

import symbiosproduction.com.morbuslogs.R;
import symbiosproduction.com.morbuslogs.database.FirestoreWrapper;
import symbiosproduction.com.morbuslogs.database.models.log.SymptomsLog;
import symbiosproduction.com.morbuslogs.fragments.logs.adapters.SympLogAdapter;


public class HistoricalLogsFragment extends Fragment {


    private static final String TAG = "HistoricalLogsFragment";

    private ArrayList<SymptomsLog> sympLogArrayList;
    private RecyclerView sympLogRecView;
    private SympLogAdapter sympLogAdapter;



    public HistoricalLogsFragment() {
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.historical_logs_fragment, container, false);
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initSympLogArray(savedInstanceState);
        initRecyclerView(view);
    }

    private void initSympLogArray(Bundle savedInstanceState) {

        sympLogArrayList = new ArrayList<>();
        getLogsFromDB();

    }

    private void getLogsFromDB() {
        OnCompleteListener<QuerySnapshot> onCompleteListener = new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isSuccessful())
                {
                    SymptomsLog tempLog;
                    for(QueryDocumentSnapshot documentSnapshot: task.getResult())
                    {
                        Log.d(TAG, "Data fetched successfully: " + documentSnapshot.getId() + " = > " + documentSnapshot.getData());
                        tempLog = new SymptomsLog(documentSnapshot.getData());
                        sympLogArrayList.add(tempLog);
                        sympLogAdapter.notifyDataSetChanged();
                    }
                }
                else
                {
                    Log.e(TAG, "Error fetching data from database:", task.getException());
                }
            }
        };
        FirestoreWrapper firestoreWrapper = new FirestoreWrapper();
        firestoreWrapper.fetchSymptomsLog(SymptomsLog.DB_MAIN_COLLECTION, SymptomsLog.DB_SUB_COLLECTION, onCompleteListener);
    }

    private void initRecyclerView(View view) {
        // find id
        sympLogRecView = (RecyclerView) view.findViewById(R.id.symptom_log_recview);

        // create adapter
        sympLogAdapter = new SympLogAdapter(sympLogArrayList, getContext());
        sympLogRecView.setAdapter(sympLogAdapter);

        // create layout manager
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
        sympLogRecView.setLayoutManager(mLayoutManager);

        // set additional animations
        sympLogRecView.setItemAnimator(new DefaultItemAnimator());
        sympLogRecView.addItemDecoration(new DividerItemDecoration(getContext(), LinearLayoutManager.VERTICAL));
    }
}
