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
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.example.tubesp3b_2.MainActivity;
import com.example.tubesp3b_2.databinding.BookTicketFragmentBinding;
import com.example.tubesp3b_2.model.Route;
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
    private String currentDate;

    //time picker needs
    private String formatedHour;
    private String currentHour;

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


    //adding routes array & setup spinner
    public void addRoutesArray(RoutesResult res){
        this.arrivalCities = new ArrayList<>();
        this.departingCities = new ArrayList<>();
        Route[] arrRoutes = res.getArrRoutes();
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
            this.currentDate = sdf.format(java.util.Calendar.getInstance().getTime());
            this.formatedDate = this.currentDate;
        //update date on change
        }else{
            this.formatedDate = sdf.format(calendar.getTime());
        }
        this.binding.datePicker.setText(this.formatedDate);
    }


    //setup time
    public void setupTimePicker(){
        //set current initial date
        this.currentHour = ""+this.binding.timePicker.getCurrentHour();
        this.formatedHour = this.currentHour;
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


    //schedule input validity checker
    public int checkInputValidity(){
        String source = this.binding.spinnerDeparting.getSelectedItem().toString();
        String destination = this.binding.spinnerArrival.getSelectedItem().toString();
        int size = this.arrivalCities.size();

        //source & destination check
        for(int i = 0; i< size; i++){
            if(this.departingCities.get(i).equals(source) && this.arrivalCities.get(i).equals(destination)){
                //year check
                if(Integer.parseInt(this.formatedDate.substring(6)) > Integer.parseInt(this.currentDate.substring(6))){
                    return 1;
                //same year
                }else if(Integer.parseInt(this.formatedDate.substring(6)) == Integer.parseInt(this.currentDate.substring(6))){
                    //month check
                    if(Integer.parseInt(this.formatedDate.substring(3, 5)) > Integer.parseInt(this.currentDate.substring(3, 5))){
                       return 1;
                    //same month
                    }else if(Integer.parseInt(this.formatedDate.substring(3, 5)) == Integer.parseInt(this.currentDate.substring(3, 5))){
                        //date check
                        if(Integer.parseInt(this.formatedDate.substring(0, 2)) > Integer.parseInt(this.currentDate.substring(0, 2))){
                            return 1;
                        //same date
                        }else if(Integer.parseInt(this.formatedDate.substring(0, 2)) == Integer.parseInt(this.currentDate.substring(0, 2))){
                            //hour check
                            if(Integer.parseInt(this.formatedHour) > Integer.parseInt(this.currentHour)){
                                return 1;
                            }else{
                                return 0;
                            }
                        }
                    }
                }
            }
        }
        return -1;
    }


    @Override
    public void onClick(View view) {
        //show popup calendar
        if(view == this.binding.datePicker){
            this.calendar = Calendar.getInstance();
            new DatePickerDialog(this.getContext(), date, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DATE)).show();

        //go to next page (pick a seat)
        }else if(view == this.binding.btnFind){
            int checker = this.checkInputValidity();

            //check input validity
            if(checker == 1){
                //send order details to SeatFragment
                Bundle orderDetails = new Bundle();
                orderDetails.putString("source", this.binding.spinnerDeparting.getSelectedItem().toString());
                orderDetails.putString("destination", this.binding.spinnerArrival.getSelectedItem().toString());
                orderDetails.putString("vehicle", "Small");
                orderDetails.putString("date", this.formatedDate);
                orderDetails.putString("hour", this.formatedHour);
                this.fragmentManager.setFragmentResult("getOrderDetails", orderDetails);

                //move page
                Bundle nextPage = new Bundle();
                nextPage.putInt("page", 2);
                this.fragmentManager.setFragmentResult("changePage", nextPage);

            //invalid schedule
            }else {
                Toast toast = new Toast(this.getContext());
                //date & time invalid
                if (checker == 0) {
                    toast.setText("Please pick later schedule");
                //no route
                } else {
                    toast.setText("There is currently no shuttle for your selected cities");
                }
                toast.show();
            }
        }
    }
}
