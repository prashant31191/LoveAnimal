package com.loveanimal;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.app.Activity;
import android.graphics.Path;
import android.graphics.PathMeasure;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.loveanimal.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ActivityTestRandom extends Activity {

    private RelativeLayout mContainer;
    private RelativeLayout mParent;
    private ImageView imageView;
    private static TextView tvPoint;

    private final int MIN_PLANES_ON_SCREEN = 1;
    private final int MAX_PLANES_ON_SCREEN = 1;

    private final int MIN_EDGE_DELTA = -200;
    private final int MAX_EDGE_DELTA = 200;

    private static final int MIN_ANIMATION_TIME = 3800;
    //private static final int MAX_ANIMATION_TIME = 3800;
    private static final int[] COLORS = new int[]{

            0xff560027,
            0xff12005e,
            0xff7f0000,
            0xff002f6c,
            0xff870000,
            0xffc43e00,
            0xff1c313a,
            0xff1b0000,
            0xff8e0038,
            0xffba2d65
            //Color.RED, Color.YELLOW, Color.GREEN, Color.BLUE, Color.MAGENTA, Color.CYAN

    };

    private Handler h;
    private int timeInterval = 3000;
    Runnable r = new Runnable() {
        @Override
        public void run() {
            if(isStop == false) {
                createAnimation();
                h.postDelayed(r, timeInterval);
            }
        }
    };

    boolean isStop = false;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        mContainer = (RelativeLayout) findViewById(R.id.container);
        mParent = (RelativeLayout) findViewById(R.id.parent);
        imageView = (ImageView) findViewById(R.id.plane_iv);
        tvPoint = (TextView) findViewById(R.id.tvPoint);
        findViewById(R.id.again).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isStop = false;
                r.run();
                //r.run();
                v.setEnabled(false);
            }
        });
        tvPoint.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {

                isStop = true;
                findViewById(R.id.again).setEnabled(true);
                return false;
            }
        });


        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                i = i+1;
                Log.i("==Clicked=","==imageView==main---counter=="+i);
                tvPoint.setText("Point Main :" + i);

            }
        });

        h = new Handler();
    }

    @Override
    protected void onResume() {
        super.onResume();

        mParent.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                createAnimation();
                mParent.getViewTreeObserver().removeOnGlobalLayoutListener(this);
            }
        });
    }

    private void createAnimation() {
        List<AnimatedPlaneView> planes = new ArrayList<>();
        //int planesOnScreen = new Random().nextInt(MAX_PLANES_ON_SCREEN - MIN_PLANES_ON_SCREEN) + MIN_PLANES_ON_SCREEN;
        //for (int i = 0; i < planesOnScreen; i++)
        {
            planes.add(new AnimatedPlaneView(mContainer, getRandomPath()));
        }

        for (AnimatedPlaneView plane : planes) {
            plane.startAnimtion();
        }
    }

    public PathWithVariables getRandomPath() {

        float width = mContainer.getWidth();
        float x1 = width / 3;
        float x2 = x1 * 2;
        float singleImageWidth = 2 * imageView.getWidth();

        final Path path = new Path();
        PathWithVariables randomPath = new PathWithVariables();
        randomPath.path = path;
        //randomPath.startX = 0 - singleImageWidth;
        //randomPath.startX = getRandomEdgeWidth()+500;
        //randomPath.startY = getRandomEdgeHeight()+500;
        //path.moveTo(randomPath.startX, randomPath.startY);

       /* path.moveTo(randomPath.startX, randomPath.startY);
        path.cubicTo(x1, getRandomHeight(), x2, getRandomHeight(), width + singleImageWidth, getRandomEdgeHeight());
*/




       /* path.lineTo(105f, randomPath.startY);
        path.lineTo(105f, 500f);
        path.lineTo(randomPath.startX, 500f);*/

/*

        path.cubicTo(0.0f, 0.0f, 300.0f, 350.0f, 100.0f, 200.0f);
       	path.cubicTo(100.0f, 200.0f, 70.0f, 800.0f, -150.0f, 400.0f);
       	path.cubicTo(-150.0f, 300.0f, 400.0f, 600.0f, 500.0f, -180.0f);

*/

        setAnimationPath(path);

        return randomPath;
    }


    public void bottomToTop(Path path) {
        try {

            path.moveTo(getRandomEdgeWidth() + 500, getRandomEdgeHeight() + 500);


            float f_x1 = getRandomEdgeWidth();
            float f_y1 = getRandomEdgeHeight();

            float f_x2 = getRandomEdgeWidth();
            float f_y2 = getRandomEdgeHeight();

            // path.moveTo(0.0f, 0.0f);
            path.cubicTo(0.0f, 0.0f, f_x1, f_y1, (f_x2 - 800), f_x1);
            path.cubicTo((f_x2 - 800), f_x1, f_x2, f_y2, (f_x2 - 50), (f_y2 + 100));
            path.cubicTo((f_x2 - 50), (f_y2 + 50), f_x1, f_y1, (f_x1 - 100), -180.0f);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void setAnimationPath(Path path) {
        try {

            int iCase = (int) getRandomMinMax(1, 8);
            Log.i("==sss=", "=iCase=" + iCase);

            float f_x1 = getRandomEdgeWidth();
            float f_y1 = getRandomEdgeHeight();

            float f_x2 = getRandomEdgeWidth();
            float f_y2 = getRandomEdgeHeight();

            switch (iCase) {
                case 1:
                    //Bottom To Top (Right)
                    path.moveTo(getRandomMinMax(-100, (f_x1 / 1.15f)), getRandomMinMax(f_y1 + 500, f_y1 + 800));
                    path.cubicTo(getRandomMinMax(100, 450), getRandomMinMax(45, 250),
                            getRandomMinMax(70, 420), getRandomMinMax(200, 400),
                            getRandomMinMax(-300, (f_x2 / 1.2f)), getRandomMinMax(-200, -180)
                    );
                    break;

                case 2:
                    //Bottom To Top (Left)
                    path.moveTo(getRandomMinMax(-100, (f_x1 / 1.12f)), getRandomMinMax(f_y1 + 500, f_y1 + 800));
                    path.cubicTo(getRandomMinMax(200, 450), getRandomMinMax(245, 450),
                            getRandomMinMax(100, 420), getRandomMinMax(200, 450),
                            getRandomMinMax(-300, (f_x2 / 1.12f)), getRandomMinMax(-200, -180)
                    );
                    break;

                case 3:
                    //Top To Bottom (Right)
                    path.moveTo(getRandomMinMax(-100, (f_x1 / 1.12f)), getRandomMinMax(-200, -180));
                    path.cubicTo(getRandomMinMax(100, 450), getRandomMinMax(45, 250),
                            getRandomMinMax(70, 420), getRandomMinMax(200, 400),
                            f_x2+ 400, getRandomMinMax(f_y1 + 500, f_y1 + 800)
                    );
                    break;

                case 4:
                    //Top To Bottom (Left)
                    path.moveTo(getRandomMinMax(10, f_x1), getRandomMinMax(-200, -180));
                    path.cubicTo(getRandomMinMax(200, 450), getRandomMinMax(45, 250),
                            getRandomMinMax(70, 420), getRandomMinMax(200, 400),
                            getRandomMinMax(-300, f_x2 / 1.15f), getRandomMinMax(f_y1 + 500, f_y1 + 800)
                    );
                    break;



                case 5:
                    //Bottom To Top (Right)
                    path.moveTo(getRandomMinMax(-100, (f_x1 / 1.15f)), getRandomMinMax(f_y1 + 500, f_y1 + 800));
                    path.cubicTo(getRandomMinMax(100, 450), getRandomMinMax(45, 250),
                            getRandomMinMax(70, 420), getRandomMinMax(200, 400),
                            getRandomMinMax(-300, (f_x2 / 1.2f)), getRandomMinMax(-200, -180)
                    );
                    break;

                case 6:
                    //Bottom To Top (Left)
                    path.moveTo(getRandomMinMax(-100, (f_x1 / 1.12f)), getRandomMinMax(f_y1 + 500, f_y1 + 800));
                    path.cubicTo(getRandomMinMax(200, 450), getRandomMinMax(245, 450),
                            getRandomMinMax(100, 420), getRandomMinMax(200, 450),
                            getRandomMinMax(-300, (f_x2 / 1.12f)), getRandomMinMax(-200, -180)
                    );
                    break;

                case 7:
                    //Top To Bottom (Right)
                    path.moveTo(getRandomMinMax(-100, (f_x1 / 1.12f)), getRandomMinMax(-200, -180));
                    path.cubicTo(getRandomMinMax(100, 450), getRandomMinMax(45, 250),
                            getRandomMinMax(70, 420), getRandomMinMax(200, 400),
                            f_x2+ 400, getRandomMinMax(f_y1 + 500, f_y1 + 800)
                    );
                    break;

                case 8:
                    //Top To Bottom (Left)
                    path.moveTo(getRandomMinMax(10, f_x1), getRandomMinMax(-200, -180));
                    path.cubicTo(getRandomMinMax(200, 450), getRandomMinMax(45, 250),
                            getRandomMinMax(70, 420), getRandomMinMax(200, 400),
                            getRandomMinMax(-300, f_x2 / 1.15f), getRandomMinMax(f_y1 + 500, f_y1 + 800)
                    );
                    break;

                default:
                    //Top To Bottom (Left)
                    path.moveTo(getRandomMinMax(10, f_x1), getRandomMinMax(-200, -180));
                    path.cubicTo(getRandomMinMax(200, 450), getRandomMinMax(45, 250),
                            getRandomMinMax(70, 420), getRandomMinMax(200, 400),
                            getRandomMinMax(-300, f_x2 / 1.15f), getRandomMinMax(f_y1 + 500, f_y1 + 800)
                    );
                    break;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private float getRandomMinMax(float min, float max) {
        //r.nextInt(High-Low) + Low;
        float fteamp = 0f;
        return fteamp + (new Random().nextInt(((int) max - (int) min)) + ((int) min));
    }


    public float getRandomHeight() {
        float low = mContainer.getTop();
        float high = mContainer.getBottom();
        int d = (int) (high - low);
        return new Random().nextInt(d);
    }

    public float getRandomEdgeHeight() {
        float height = mContainer.getHeight();// / 2;
        return height + (new Random().nextInt(MAX_EDGE_DELTA - MIN_EDGE_DELTA) + MIN_EDGE_DELTA);
    }

    public float getRandomEdgeWidth() {
        float width = mContainer.getWidth();// / 2;
        return width + (new Random().nextInt(MAX_EDGE_DELTA - MIN_EDGE_DELTA) + MIN_EDGE_DELTA);
    }

    public static class PathWithVariables {
        Path path;
        float startX;
        float startY;
    }

    public static class AnimatedPlaneView {

        float[] last;
        ImageView iv;
        ValueAnimator pathAnimator;

        public AnimatedPlaneView(final ViewGroup parentView, PathWithVariables path) {
            last = new float[]{parentView.getX() + parentView.getHeight() / 2, parentView.getWidth() / 2};
            iv = new ImageView(parentView.getContext());
            iv.setImageResource(R.mipmap.pet);
            iv.setEnabled(true);

            iv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    i = i+1;
                    Log.i("==Clicked=","==-sub-counter=="+i);

                    //Toast.makeText(parentView.getContext(),"=Point=" + i, Toast.LENGTH_SHORT).show();
                    tvPoint.setText("Point :" + i);
                    //iv.setVisibility(View.GONE);
                    iv.setImageResource(R.mipmap.sad_pet);
                    iv.setEnabled(false);
                    //iv.setLayerType(View.LAYER_TYPE_NONE, null);
                    //iv.setDrawingCacheEnabled(false);
                    //parentView.removeView(iv);
                }
            });

            RelativeLayout.LayoutParams rlParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
            iv.setLayoutParams(rlParams);
            iv.setX(path.startX);
            iv.setY(path.startY);
            int color = COLORS[new Random().nextInt(COLORS.length)];
            iv.setColorFilter(color);

            parentView.addView(iv);

            final PathMeasure pm = new PathMeasure(path.path, false);

            pathAnimator = ValueAnimator.ofFloat(0.0f, pm.getLength());
            //pathAnimator.setDuration(new Random().nextInt(MAX_ANIMATION_TIME - MIN_ANIMATION_TIME) + MIN_ANIMATION_TIME);
            pathAnimator.setDuration(MIN_ANIMATION_TIME);
            pathAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    float val = animation.getAnimatedFraction();

                    float distance = pm.getLength();
                    if (val != 0) {
                        distance = pm.getLength() * val;
                    }
                    float[] tan = new float[2];
                    iv.setX(last[0]);
                    iv.setY(last[1]);

                    pm.getPosTan(distance, last, tan);
                    float deg = (float) (Math.atan2(tan[1], tan[0]) * 180.0 / Math.PI);
                    iv.setRotation(deg);
                }
            });
            pathAnimator.addListener(new AnimatorListenerAdapter() {

                @Override
                public void onAnimationStart(Animator animation) {
                    super.onAnimationStart(animation);
                    iv.setLayerType(View.LAYER_TYPE_HARDWARE, null);
                }

                @Override
                public void onAnimationEnd(Animator animation) {
                    super.onAnimationEnd(animation);
                    iv.setVisibility(View.GONE);
                    iv.setLayerType(View.LAYER_TYPE_NONE, null);
                    iv.setDrawingCacheEnabled(false);
                    parentView.removeView(iv);
                }

            });



        }

        public void startAnimtion() {
            pathAnimator.start();
        }

    }
    static int i=0;

    //Project: Material-Motion   File: BaseFragment.java  -- https://www.programcreek.com/java-api-examples/?code=vpaliyX/Material-Motion/Material-Motion-master/app/src/main/java/com/vpaliy/fabexploration/BaseFragment.java
    protected Path createArcPath(View view, float endX, float endY, float radius) {
        Path arcPath = new Path();
        float startX = view.getTranslationX();
        float startY = view.getTranslationY();
        float midX = startX + ((endX - startX) / 2);
        float midY = startY + ((endY - startY) / 2);
        float xDiff = midX - startX;
        float yDiff = midY - startY;

        double angle = (Math.atan2(yDiff, xDiff) * (180 / Math.PI)) - 90;
        double angleRadians = Math.toRadians(angle);

        float pointX = (float) (midX + radius * Math.cos(angleRadians));
        float pointY = (float) (midY + radius * Math.sin(angleRadians));

        arcPath.moveTo(startX, startY);
        arcPath.cubicTo(startX, startY, pointX, pointY, endX, endY);
        return arcPath;
    }


}
