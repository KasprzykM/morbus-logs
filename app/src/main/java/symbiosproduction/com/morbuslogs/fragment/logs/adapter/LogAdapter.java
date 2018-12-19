package symbiosproduction.com.morbuslogs.fragment.logs.adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import symbiosproduction.com.morbuslogs.R;
import symbiosproduction.com.morbuslogs.database.model.symptoms.AbstractSymptom;
import symbiosproduction.com.morbuslogs.fragment.logs.interfaces.EditCallbacksAdapter;

public class LogAdapter extends RecyclerView.Adapter<LogAdapter.SymptomViewHolder> implements View.OnClickListener {

    private ArrayList<AbstractSymptom> dataSet;
    private Context mContext;
    private EditCallbacksAdapter mEditCallbacksAdapter;
    private static int REQUEST_CODE_CONSTANT = 1243;

    private int lastPosition = -1;


    @Override
    public void onClick(View v) {

    }

    public LogAdapter(ArrayList<AbstractSymptom> data, Context context, EditCallbacksAdapter editCallbacksAdapter) {
        this.mContext = context;
        this.dataSet = data;
        this.mEditCallbacksAdapter = editCallbacksAdapter;
    }

    class SymptomViewHolder extends RecyclerView.ViewHolder{
        ImageView image_view;
        ImageView delete_item_view;
        ImageView edit_item_view;
        TextView symptomNameTextView;
        TextView dateTextView;

        SymptomViewHolder(View view) {
            super(view);
            symptomNameTextView = (TextView) view.findViewById(R.id.symptom_name_text_item);
            dateTextView= (TextView) view.findViewById(R.id.symptom_date_text_item);
            image_view = (ImageView) view.findViewById(R.id.symptom_icon_item);
            delete_item_view = (ImageView) view.findViewById(R.id.delete_log_item_icon);
            edit_item_view = (ImageView) view.findViewById(R.id.edit_log_item_icon);
        }

    }

    @NonNull
    @Override
    public SymptomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.log_row, parent, false);

        return new SymptomViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull final SymptomViewHolder holder, int position) {
        AbstractSymptom abstractSymptom = dataSet.get(position);
        holder.symptomNameTextView.setText(abstractSymptom.getSymptomName());
        holder.dateTextView.setText(abstractSymptom.getDateOfOccurrence());
        chooseIcon(holder, abstractSymptom.getSymptomName());


        // Edit icon
        holder.edit_item_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = holder.getAdapterPosition();
                if(position != RecyclerView.NO_POSITION)
                {
                    AbstractSymptom symptomToEdit = dataSet.get(position);
                    mEditCallbacksAdapter.onSymptomEdit(symptomToEdit);
                }
            }
        });

        // Delete icon
        holder.delete_item_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = holder.getAdapterPosition();
                if(position != RecyclerView.NO_POSITION)
                {
                    createDeleteConfirmationDialog(position);
                }
            }


            private void createDeleteConfirmationDialog(final int position)
            {
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(mContext, android.R.style.Theme_Material_Dialog_Alert);
                alertDialogBuilder.setMessage(R.string.confirm_log_item_delete_dialog);
                alertDialogBuilder.setCancelable(true);
                alertDialogBuilder.setTitle(R.string.title_log_item_delete_dialog);
                alertDialogBuilder.setIcon(R.drawable.ic_warning_white_24dp);

                alertDialogBuilder.setPositiveButton(R.string.confirm_delete_log_item_dialog,
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dataSet.remove(position);
                                notifyDataSetChanged();
                            }
                        });

                alertDialogBuilder.setNegativeButton(R.string.cancel_delete_log_item_dialog,
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

    private void chooseIcon(SymptomViewHolder holder, String symptomName)
    {
        switch(symptomName)
        {
            case "Pain":
                holder.image_view.setImageResource(R.drawable.ic_stomach_ache);
                break;
            case "Temperature":
                holder.image_view.setImageResource(R.drawable.ic_thermometer);
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
