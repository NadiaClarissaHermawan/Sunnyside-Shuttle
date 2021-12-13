package com.example.tubesp3b_2.view;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PointF;
import android.graphics.Rect;
import android.os.Bundle;
import android.util.Log;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentResultListener;

import com.example.tubesp3b_2.MainActivity;
import com.example.tubesp3b_2.R;
import com.example.tubesp3b_2.databinding.SeatFragmentBinding;
import com.example.tubesp3b_2.model.Course;
import com.example.tubesp3b_2.model.Seat;
import com.example.tubesp3b_2.model.TicketOrder;
import com.example.tubesp3b_2.model.User;
import com.example.tubesp3b_2.presenter.GetCoursesTask;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class SeatFragment extends Fragment implements View.OnClickListener, View.OnTouchListener {
    private SeatFragmentBinding binding;
    private MainActivity activity;
    private FragmentManager fragmentManager;
    private User user;
    private TicketOrder order;
    private ArrayList<Course> course;

    //dropdown vehicle needs
    private Spinner spinnerVehicle;
    ArrayList<String> vehicleTypes;

    //seats canvas needs
    private Bitmap bitmap;
    private Canvas canvas;
    private Paint availableSeatPaint, selectedSeatPaint, notAvailableSeatPaint;
    private Paint whiteTextPaint, orangeTextPaint;
    private Seat[] seats;
    private GestureDetector gestureDetector;

    //must-have empty constructor
    public SeatFragment(){}


    //singleton
    public static SeatFragment newInstance(User user, FragmentManager fragmentManager, MainActivity activity){
        SeatFragment fragment = new SeatFragment();
        fragment.user = user;
        fragment.fragmentManager = fragmentManager;
        fragment.activity = activity;

        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // inflating layout
        this.binding = SeatFragmentBinding.inflate(inflater, container, false);
        View view = this.binding.getRoot();

        //initialize attr
        this.gestureDetector = new GestureDetector(this.getContext(), new MyCustomGestureListener());

        //setup vehicle type spinner
        this.setupVehicleSpinner();

        //set touch listener
        this.binding.seatsCanvas.setOnTouchListener(this::onTouch);

        //listener get ticket order details
        this.fragmentManager.setFragmentResultListener(
            "getOrderDetails", this, new FragmentResultListener() {
                @Override
                public void onFragmentResult(@NonNull String requestKey, @NonNull Bundle result) {
                    order = new TicketOrder(result.getString("source"), result.getString("destination"),
                                        result.getString("vehicle"), result.getString("date"), result.getString("hour"));

                    requestCourseInfo();
                }
            }
        );

        return view;
    }


    //request course info to API
    public void requestCourseInfo(){
        new GetCoursesTask(getContext(), activity, user.getToken()).executeCourses(order);
    }


    //get course detailed information
    public void getCourseInfo(ArrayList<Course> res){
        this.course = res;
        this.initializeOccupiedSeat();
        this.setupSeatsCanvas();
    }


    //setup vehicle spinner & listener
    public void setupVehicleSpinner(){
        this.vehicleTypes = new ArrayList<>();
        this.vehicleTypes.add("Small");
        this.vehicleTypes.add("Large");

        //inflate vehicle type spinner
        this.spinnerVehicle = this.binding.spinnerVehicle;
        ArrayAdapter<String> adp = new ArrayAdapter<String> (this.getContext(), android.R.layout.simple_spinner_dropdown_item, this.vehicleTypes);
        spinnerVehicle.setAdapter(adp);
        spinnerVehicle.setSelected(true);

        //set onselect listener
        this.spinnerVehicle.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String currentVehicle = binding.spinnerVehicle.getSelectedItem().toString();
                //change vehicle
                if(!currentVehicle.equals(order.getVehicle())){
                    order.setVehicle(currentVehicle);
                    requestCourseInfo();
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                //do nothing
            }
        });
    }


    //initialize occupied seats object
    public void initializeOccupiedSeat(){
        if(this.course.get(0).getVehicle().equals("Small")){
            this.seats = new Seat[6];
        }else{
            this.seats = new Seat[10];
        }

        ArrayList<Integer> occupiedSeats = this.course.get(0).getSeats();
        int tempSize = occupiedSeats.size();
        for(int i = 0; i< tempSize; i++){
            this.seats[occupiedSeats.get(i)-1] = new Seat(occupiedSeats.get(i), -1, 0, 0, 0, 0);
        }
    }


    //setup seats canvas
    public void setupSeatsCanvas(){
        if(this.canvas == null){
            this.bitmap = Bitmap.createBitmap(this.binding.seatsCanvas.getWidth(), this.binding.seatsCanvas.getHeight(), Bitmap.Config.ARGB_8888);
            this.binding.seatsCanvas.setImageBitmap(bitmap);
            this.canvas = new Canvas(bitmap);
        }

        //set canvas bg color
        int mColorBackground = ResourcesCompat.getColor(getResources(), R.color.white, null);
        canvas.drawColor(mColorBackground);

        //setup various seats colors
        this.setupSeatsColors();

        //add seats rectangle to canvas
        this.setupSeatsRectangle();

        //update to canvas view layout
        this.binding.seatsCanvas.invalidate();
    }


    //setup various seats colors
    public void setupSeatsColors(){
        //available seat paint
        this.availableSeatPaint = new Paint();
        this.availableSeatPaint.setStyle(Paint.Style.STROKE);
        this.availableSeatPaint.setColor(ResourcesCompat.getColor(getResources(), R.color.orange, null));
        this.availableSeatPaint.setStrokeWidth(3);

        //selected seat paint
        this.selectedSeatPaint = new Paint();
        this.selectedSeatPaint.setStyle(Paint.Style.FILL);
        this.selectedSeatPaint.setColor(ResourcesCompat.getColor(getResources(), R.color.yellow, null));
        this.selectedSeatPaint.setStrokeWidth(0);

        //not available seat paint
        this.notAvailableSeatPaint = new Paint();
        this.notAvailableSeatPaint.setStyle(Paint.Style.FILL);
        this.notAvailableSeatPaint.setColor(ResourcesCompat.getColor(getResources(), R.color.red, null));
        this.notAvailableSeatPaint.setStrokeWidth(0);

        //white text color
        this.whiteTextPaint = new Paint();
        this.whiteTextPaint.setColor(ResourcesCompat.getColor(getResources(), R.color.white, null));
        this.whiteTextPaint.setTextSize(50);
        this.whiteTextPaint.setTextAlign(Paint.Align.CENTER);

        //orange text color
        this.orangeTextPaint = new Paint();
        this.orangeTextPaint.setColor(ResourcesCompat.getColor(getResources(), R.color.orange, null));
        this.orangeTextPaint.setTextSize(50);
        this.orangeTextPaint.setTextAlign(Paint.Align.CENTER);
    }


    //setup each seats' rectangle position
    public void setupSeatsRectangle(){
        int top = 100;
        int left = 100;
        int bottom = 250;
        int right = 250;
        int textLeft = left + 80;
        int textTop = top + 100;

        //6 seats
        if(this.course.get(0).getVehicle().equals("Small")){
            for(int i = 0; i<6; i++){
                if(i == 3){
                    top = 100;
                    bottom = 250;
                    left = 550;
                    right = 700;
                    textLeft = left + 80;
                    textTop = top + 100;
                }
                //draw seats according to the setup
                this.drawSeats(i, left, top, right, bottom, textLeft, textTop);
                top += 300;
                bottom += 300;
                textTop += 300;
            }

        //10 seats
        }else{
            //TODO:LENGKAPIN TITIK SEATSNYA
        }
    }


    //generate (draw) seats in canvas
    public void drawSeats(int i, int left, int top, int right, int bottom, int textLeft, int textTop){
        Rect rect = new Rect(left, top, right, bottom);
        //available (status 0)
        if(this.seats[i] == null || this.seats[i].getStatus() == 0){
            this.seats[i] = new Seat(i+1, 0, top, bottom, right, left);
            this.canvas.drawRect(rect, this.availableSeatPaint);
            this.canvas.drawText((i+1)+"", textLeft, textTop, this.orangeTextPaint);

        }else{
            this.seats[i].setTop(top);
            this.seats[i].setBottom(bottom);
            this.seats[i].setLeft(left);
            this.seats[i].setRight(right);

            //occupied (status -1)
            if(this.seats[i].getStatus() == -1){
                this.canvas.drawRect(rect, this.notAvailableSeatPaint);
                this.canvas.drawText((i+1)+"", textLeft, textTop, this.whiteTextPaint);
            //selected (status 1)
            }else{
                this.canvas.drawRect(rect, this.selectedSeatPaint);
                this.canvas.drawText((i+1)+"", textLeft, textTop, this.orangeTextPaint);
            }
        }
        //rect border
        this.canvas.drawRect(rect, this.availableSeatPaint);
    }


    @Override
    public void onClick(View view) {

    }


    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        return gestureDetector.onTouchEvent(motionEvent);
    }



    private class MyCustomGestureListener extends GestureDetector.SimpleOnGestureListener{
        private PointF touchCoordinate;

        @Override
        public boolean onDown(MotionEvent e) {
            //new start point with position if null, else set start position
            if(touchCoordinate == null){
                touchCoordinate = new PointF(e.getX(), e.getY());
            }else{
                touchCoordinate.set(e.getX(), e.getY());
            }
            this.checkClickedSeats();
            return true;
        }


        //check if onDown position == any of seat' position
        public void checkClickedSeats(){
            int size = seats.length;
            for(int i = 0; i<size; i++){
                if(this.touchCoordinate.x >= seats[i].getLeft()*1.0 && this.touchCoordinate.x <= seats[i].getRight()*1.0
                    && this.touchCoordinate.y >= seats[i].getTop()*1.0 && this.touchCoordinate.y <= seats[i].getBottom()*1.0){
                    //update view
                    if(seats[i].getStatus() == 0){
                        seats[i].setStatus(1);
                    }else if(seats[i].getStatus() == 1){
                        seats[i].setStatus(0);
                    }
                    setupSeatsCanvas();
                    break;
                }
            }
        }


        @Override
        public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
            return false;
        }


        @Override
        public void onLongPress(MotionEvent e) { }
    }
}
