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
import java.util.List;

import symbiosproduction.com.morbuslogs.R;
import symbiosproduction.com.morbuslogs.database.models.symptoms.AbstractSymptom;
import symbiosproduction.com.morbuslogs.fragments.logs.commInterfaces.EditLogCallbacksAdapter;

public class LogAdapter extends RecyclerView.Adapter<LogAdapter.SymptomViewHolder> {

    private ArrayList<AbstractSymptom> dataSet;
    private Context mContext;
    private EditLogCallbacksAdapter mEditLogCallbacksAdapter;
    private static int REQUEST_CODE_CONSTANT = 1243;
//    private ArrayList<String> photosPathToDelete;

    private int lastPosition = -1;


    public LogAdapter(ArrayList<AbstractSymptom> data, Context context, EditLogCallbacksAdapter editLogCallbacksAdapter) {
        this.mContext = context;
        this.dataSet = data;
        this.mEditLogCallbacksAdapter = editLogCallbacksAdapter;
       // photosPathToDelete = new ArrayList<>();
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
                    mEditLogCallbacksAdapter.onSymptomEdit(symptomToEdit);
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
                                //photosPathToDelete.add(dataSet.get(position).getPhotoDbPath());
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

//    public List<String> getPhotoDBList()
//    {
//        return photosPathToDelete;
//    }

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
            case "Other":
                holder.image_view.setImageResource(R.drawable.ic_medical_history);
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
