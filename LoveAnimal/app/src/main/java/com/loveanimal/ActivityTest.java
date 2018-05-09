package com.loveanimal;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.loveanimal.R;

import uibezierpath.OnSetup;
import uibezierpath.UIBezierPath;


public class ActivityTest extends Activity {

    private RelativeLayout mContainer;
    private ImageView mPlaneImageView;
    private Button mAgain;
    private UIBezierPath mBezierPath;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        mContainer = findViewById(R.id.container);
        mPlaneImageView = findViewById(R.id.plane_iv);
        mAgain = findViewById(R.id.again);


        mBezierPath = new UIBezierPath(mContainer, mPlaneImageView, R.mipmap.plane, new OnSetup() {
            @Override
            public void onSetupDone() {

                // Starting the animation
                mBezierPath.startAnimation();
            }
        });


        mAgain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
if(mBezierPath !=null)
                // Starting the animation
                mBezierPath.startAnimation();
            }
        });
    }
}
