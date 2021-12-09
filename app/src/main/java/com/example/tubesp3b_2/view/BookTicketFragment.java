package com.example.tubesp3b_2.view;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.TimePicker;

import androidx.fragment.app.Fragment;

import com.example.tubesp3b_2.R;
import com.example.tubesp3b_2.databinding.BookTicketFragmentBinding;

import java.util.Calendar;

public class BookTicketFragment extends Fragment implements View.OnClickListener {
    private BookTicketFragmentBinding binding;
    private DatePickerDialog datePickerDialog;
    private TimePickerDialog timePickerDialog;

    //must-have empty constructor
    public BookTicketFragment(){}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // inflating layout
        this.binding = BookTicketFragmentBinding.inflate(inflater, container, false);
        View view = this.binding.getRoot();

        //Dropdown source and destination ref = https://developer.android.com/guide/topics/ui/controls/spinner
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this.getActivity(), R.array.city_array, R.layout.book_ticket_fragment);
        adapter.setDropDownViewResource(R.layout.book_ticket_fragment);
        this.binding.spinnerDari.setAdapter(adapter);
        this.binding.spinnerKe.setAdapter(adapter);

        this.binding.btnCari.setOnClickListener(this);
        this.binding.datePicker.setOnClickListener(this);
        this.binding.timePicker.setOnClickListener(this);

        return view;
    }

    @Override
    public void onClick(View view) {
        //Date Picker (ref: datePicker = https://abhiandroid.com/ui/datepicker)
        //Time Picker (ref: timePicker = https://www.tutlane.com/tutorial/android/android-timepicker-with-examples)
        final Calendar c = Calendar.getInstance();
        int mYear = c.get(Calendar.YEAR);
        int mMonth = c.get(Calendar.MONTH);
        int mDay = c.get(Calendar.DAY_OF_MONTH);
        int hour = c.get(Calendar.HOUR_OF_DAY);
        int minute = c.get(Calendar.MINUTE);
        datePickerDialog = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            binding.datePicker.setText(dayOfMonth + '/' +
                    (monthOfYear + 1) + '/' + year);
            }
        }, mYear, mMonth, mDay);

        timePickerDialog = new TimePickerDialog(getActivity(), new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int sHour, int sMinute) {
                binding.timePicker.setText(sHour + ":" + "00");
            }
        }, hour, minute, true);

        datePickerDialog.show();
        timePickerDialog.show();

        //binding.btnCari menuju ke Pemilihan Tempat Duduk
    }
}
