package com.example.tubesp3b_2.view;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.TimePicker;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.example.tubesp3b_2.MainActivity;
import com.example.tubesp3b_2.databinding.BookTicketFragmentBinding;
import com.example.tubesp3b_2.model.Routes;
import com.example.tubesp3b_2.model.RoutesResult;
import com.example.tubesp3b_2.model.User;
import com.example.tubesp3b_2.presenter.GetRoutesTask;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class BookTicketFragment extends Fragment implements View.OnClickListener {
    private BookTicketFragmentBinding binding;
    private MainActivity activity;
    private FragmentManager fragmentManager;
    private User user;

    //dropdown cities needs
    private Spinner spinnerArrival;
    private Spinner spinnerDeparting;
    ArrayList<String> arrivalCities;
    ArrayList<String> departingCities;

    //date picker needs
    private Calendar calendar;
    private DatePickerDialog.OnDateSetListener date;
    private String formatedDate;

    //time picker needs
    private String formatedHour;

    //must-have empty constructor
    public BookTicketFragment(){}


    //singleton
    public static BookTicketFragment newInstance(User user, FragmentManager fragmentManager, MainActivity activity){
        BookTicketFragment fragment = new BookTicketFragment();
        fragment.user = user;
        fragment.fragmentManager = fragmentManager;
        fragment.activity = activity;

        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // inflating layout
        this.binding = BookTicketFragmentBinding.inflate(inflater, container, false);
        View view = this.binding.getRoot();

        //get routes data from API
        new GetRoutesTask(this.getContext(), this.activity, this.user.getToken()).executeRoutes();

        //setup popup calendar
        this.setupCalendar();
        this.binding.datePicker.setOnClickListener(this::onClick);

        //setup time picker
        this.setupTimePicker();

        //set find-button listener
        this.binding.btnFind.setOnClickListener(this::onClick);

        return view;
    }


    //adding routes array & setup spinner --> diasumsikan kota source & kota tujuan berbeda
    public void addRoutesArray(RoutesResult res){
        this.arrivalCities = new ArrayList<>();
        this.departingCities = new ArrayList<>();
        Routes[] arrRoutes = res.getArrRoutes();
        int size = res.getArrRoutes().length;

        //cek duplikat
        Map<String, Boolean> checkDuplicateArrival = new HashMap<>();
        Map<String, Boolean> checkDuplicateDeparting = new HashMap<>();

        //masukin ke arraylist
        for(int i = 0; i<size; i++){
            String city = arrRoutes[i].getSource();
            if(!checkDuplicateArrival.containsKey(city)){
                checkDuplicateArrival.put(city,true);
                this.arrivalCities.add(city);
            }

            city = arrRoutes[i].getDestination();
            if(!checkDuplicateDeparting.containsKey(city)){
                checkDuplicateDeparting.put(city, true);
                this.departingCities.add(city);
            }
        }

        //setup spinner
        this.setupSpinner();
    }


    //setup spinner
    public void setupSpinner(){
        //inflate arrival spinner
        this.spinnerArrival = (Spinner) this.binding.spinnerArrival;
        ArrayAdapter<String> adp = new ArrayAdapter<String> (this.getContext(), android.R.layout.simple_spinner_dropdown_item, this.arrivalCities);
        spinnerArrival.setAdapter(adp);
        spinnerArrival.setSelected(true);

        //inflate departing spinner
        this.spinnerDeparting = (Spinner) this.binding.spinnerDeparting;
        ArrayAdapter<String> adp2 = new ArrayAdapter<String> (this.getContext(), android.R.layout.simple_spinner_dropdown_item, this.departingCities);
        spinnerDeparting.setAdapter(adp2);
        spinnerDeparting.setSelected(true);
    }


    //setup date
    public void setupCalendar(){
        //set current initial date
        this.updateViewDate();

        //set date picker listener
        this.date = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int date) {
                calendar.set(Calendar.YEAR, year);
                calendar.set(Calendar.MONTH, month);
                calendar.set(Calendar.DATE, date);

                //langsung update ke view
                updateViewDate();
            }
        };
    }


    //updating date layout
    public void updateViewDate(){
        String formated = "dd-MM-yyyy";
        SimpleDateFormat sdf = new SimpleDateFormat(formated, Locale.US);

        //set current initial date
        if(this.calendar == null){
            this.formatedDate = sdf.format(java.util.Calendar.getInstance().getTime());
        //update date on change
        }else{
            this.formatedDate = sdf.format(calendar.getTime());
        }
        this.binding.datePicker.setText(this.formatedDate);
    }


    //setup time
    public void setupTimePicker(){
        //set current initial date
        this.formatedHour = ""+this.binding.timePicker.getCurrentHour();
        this.formatTime();

        //set time picker listener
        this.binding.timePicker.setOnTimeChangedListener(new TimePicker.OnTimeChangedListener() {
            @Override
            public void onTimeChanged(TimePicker timePicker, int hour, int minute) {
                    formatedHour = hour+"";
                    formatTime();
            }
        });
    }


    //format time
    public void formatTime(){
        if(this.formatedHour.length() < 2){
            this.formatedHour = "0"+this.formatedHour;
        }
    }


    @Override
    public void onClick(View view) {
        //show popup calendar
        if(view == this.binding.datePicker){
            this.calendar = Calendar.getInstance();
            new DatePickerDialog(this.getContext(), date, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DATE)).show();

            String dt = this.calendar.getTime().toString();

        //go to next page (pick a seat)
        }else if(view == this.binding.btnFind){
            //move page
            Bundle nextPage = new Bundle();
            nextPage.putInt("page", 2);
            this.fragmentManager.setFragmentResult("changePage", nextPage);

            //send order details to SeatFragment
            Bundle orderDetails = new Bundle();
            orderDetails.putString("source", this.binding.spinnerDeparting.getSelectedItem().toString());
            orderDetails.putString("destination", this.binding.spinnerArrival.getSelectedItem().toString());
            orderDetails.putString("vehicle", "Small");
            orderDetails.putString("date", this.formatedDate);
            orderDetails.putString("hour", this.formatedHour);
            this.fragmentManager.setFragmentResult("getOrderDetails", orderDetails);
        }
    }
}
