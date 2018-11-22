package symbiosproduction.com.morbuslogs.fragment.commonFragments;

import android.app.Dialog;
import android.os.Bundle;
import android.app.DatePickerDialog;
import android.support.v4.app.DialogFragment;
import android.widget.Button;
import android.widget.DatePicker;

import java.util.Calendar;

import symbiosproduction.com.morbuslogs.R;

public class SelectDateFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener {


    private Integer year;
    private Integer month;
    private Integer dayOfMonth;
    private Button button;

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
        button.setText(dayOfMonth+"/"+month+"/"+year);
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

}
