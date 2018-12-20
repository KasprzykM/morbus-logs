package symbiosproduction.com.morbuslogs.fragments.logs;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import symbiosproduction.com.morbuslogs.R;
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


    }
}
