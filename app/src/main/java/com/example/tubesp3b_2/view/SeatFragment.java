package com.example.tubesp3b_2.view;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentResultListener;

import com.example.tubesp3b_2.MainActivity;
import com.example.tubesp3b_2.R;
import com.example.tubesp3b_2.databinding.SeatFragmentBinding;
import com.example.tubesp3b_2.model.Course;
import com.example.tubesp3b_2.model.TicketOrder;
import com.example.tubesp3b_2.model.User;
import com.example.tubesp3b_2.presenter.GetCoursesTask;

public class SeatFragment extends Fragment implements View.OnClickListener {
    private SeatFragmentBinding binding;
    private MainActivity activity;
    private FragmentManager fragmentManager;
    private User user;
    private Course course;

    //seats canvas need
    private Bitmap bitmap;
    private Canvas canvas;
    private Paint strokePaint;

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

        //listener get ticket order details
        this.fragmentManager.setFragmentResultListener(
            "getOrderDetails", this, new FragmentResultListener() {
                @Override
                public void onFragmentResult(@NonNull String requestKey, @NonNull Bundle result) {
                    TicketOrder order = new TicketOrder(result.getString("source"), result.getString("destination"),
                                        result.getString("vehicle"), result.getString("date"), result.getString("hour"));

                    //get courses data from API
                    new GetCoursesTask(getContext(), activity, user.getToken()).executeCourses(order);
                }
            }
        );

        return view;
    }


    //get course detailed information
    public void getCourseInfo(Course course){
        this.course = course;
        this.setupSeatsCanvas();
    }


    //setup seats canvas
    //TODO: GANTI WIDTH & HEIGHT DARI HASIL AMBIL DI CANVAS LAYOUT
    public void setupSeatsCanvas(){
        //Small
        if(this.course.getVehicle().equals("Small")){
            this.bitmap = Bitmap.createBitmap(900, 1000, Bitmap.Config.ARGB_8888);
        //Large
        }else{
            this.bitmap = Bitmap.createBitmap(1000, 1000, Bitmap.Config.ARGB_8888);
        }
        //this.binding.seatsCanvas.setImageBitmap(bitmap);
        this.canvas = new Canvas(bitmap);

        //canvas bg color
        int mColorBackground = ResourcesCompat.getColor(getResources(), R.color.white, null);
        canvas.drawColor(mColorBackground);

        //add seats
        this.addSeats();

        //update to canvas view layout
        //this.binding.seatsCanvas.invalidate();
    }


    //add seats into canvas
    public void addSeats(){
        //stroke color
        strokePaint = new Paint();
        int mColorTest = ResourcesCompat.getColor(getResources(), R.color.orange, null);
        strokePaint.setColor(mColorTest);

        //6 seats
        if(this.course.getSeats().equals("Small")){
            
        //10 seats
        }else{

        }
    }


    @Override
    public void onClick(View view) {

    }
}
