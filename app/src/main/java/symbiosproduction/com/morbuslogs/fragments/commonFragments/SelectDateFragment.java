package symbiosproduction.com.morbuslogs.fragments.commonFragments;

import android.app.Dialog;
import android.os.Bundle;
import android.app.DatePickerDialog;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.widget.Button;
import android.widget.DatePicker;

import java.util.Calendar;


public class SelectDateFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener {


    private Integer year;
    private Integer month;
    private Integer dayOfMonth;
    private Button button;

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final Calendar calendar = Calendar.getInstance();
        int yy = calendar.get(Calendar.YEAR);
        int mm = calendar.get(Calendar.MONTH);
        int dd = calendar.get(Calendar.DAY_OF_MONTH);
        return new DatePickerDialog(getActivity(), this, yy, mm, dd);
    }

    public void onDateSet(DatePicker view, int yy, int mm, int dd) {
        year = yy;
        month = mm+1;
        dayOfMonth = dd;
        populateSetDate();
    }

    private void populateSetDate()
    {
        button.setText(getStringDate());
    }

    public void setButton(Button button)
    {
        this.button = button;
    }


    public Integer getYear() {
        return year;
    }

    public Integer getMonth() {
        return month;
    }

    public Integer getDayOfMonth() {
        return dayOfMonth;
    }

    public String getStringDate(){
        return dayOfMonth+"/"+month+"/"+year;
    }

    public void stringToDate(String date){
        String splitDate[] = date.split("/");
        dayOfMonth = Integer.valueOf(splitDate[0]);
        month = Integer.valueOf(splitDate[1]);
        year = Integer.valueOf(splitDate[2]);
        populateSetDate();
    }

}
