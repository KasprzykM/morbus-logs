package symbiosproduction.com.morbuslogs.fragments.logs.adapters;

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
import symbiosproduction.com.morbuslogs.database.models.log.SymptomsLog;

public class SympLogAdapter extends RecyclerView.Adapter<SympLogAdapter.SymptomLogViewHolder> {

    private ArrayList<SymptomsLog> dataSet;
    private Context mContext;

    public SympLogAdapter(ArrayList<SymptomsLog> data, Context context) {
        this.mContext = context;
        this.dataSet = data;
    }

    @NonNull
    @Override
    public SymptomLogViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.symptomlog_row, parent, false);

        return new SymptomLogViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull final SymptomLogViewHolder holder, int position) {
        SymptomsLog symptomsLog = dataSet.get(position);
        holder.dateTextView.setText(symptomsLog.getDateOfSubmission());

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

    @Override
    public int getItemCount() {
        return dataSet.size();
    }

    class SymptomLogViewHolder extends RecyclerView.ViewHolder{
        ImageView delete_item_view;
        ImageView edit_item_view;
        TextView dateTextView;

        SymptomLogViewHolder(View view) {
            super(view);
            dateTextView= (TextView) view.findViewById(R.id.date_symptom_log_text_view);
            delete_item_view = (ImageView) view.findViewById(R.id.delete_symptom_log_icon);
            edit_item_view = (ImageView) view.findViewById(R.id.edit_symptom_log_icon);
        }

    }
}
