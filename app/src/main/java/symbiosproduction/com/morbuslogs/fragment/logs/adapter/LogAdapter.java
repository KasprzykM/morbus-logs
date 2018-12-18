package symbiosproduction.com.morbuslogs.fragment.logs.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import symbiosproduction.com.morbuslogs.R;
import symbiosproduction.com.morbuslogs.database.model.symptoms.AbstractSymptom;

public class LogAdapter extends RecyclerView.Adapter<LogAdapter.SymptomViewHolder> implements View.OnClickListener {

    private ArrayList<AbstractSymptom> dataSet;
    private Context mContext;

    private int lastPosition = -1;


    @Override
    public void onClick(View v) {

    }

    public LogAdapter(ArrayList<AbstractSymptom> data) {
        this.dataSet = data;
    }

    class SymptomViewHolder extends RecyclerView.ViewHolder{
        ImageView image_view;
        TextView symptomNameTextView;
        TextView dateTextView;

        SymptomViewHolder(View view) {
            super(view);
            symptomNameTextView = (TextView) view.findViewById(R.id.symptom_name_text_item);
            dateTextView= (TextView) view.findViewById(R.id.symptom_date_text_item);
            image_view = (ImageView) view.findViewById(R.id.symptom_icon_item);
        }

    }

    @NonNull
    @Override
    public SymptomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.log_item, parent, false);

        return new SymptomViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull SymptomViewHolder holder, int position) {
        AbstractSymptom abstractSymptom = dataSet.get(position);
        holder.symptomNameTextView.setText(abstractSymptom.getSymptomName());
        holder.dateTextView.setText(abstractSymptom.getDateOfOccurrence());

        chooseIcon(holder, abstractSymptom.getSymptomName());

    }

    private void chooseIcon(SymptomViewHolder holder, String symptomName)
    {
        switch(symptomName)
        {
            case "Pain":
                holder.image_view.setImageResource(R.drawable.ic_stomach_ache);
                break;
            default:
                break;
        }
    }

    @Override
    public int getItemCount() {
        return dataSet.size();
    }

}
