package test.eben.cn.testtouchevent;

import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;


/**
 * 1. 显示触笔事件的坐标位置，用于对比笔位置的精细程度
 *
 */
public class DrawPointsAtPosition extends Activity {

    private static final String TAG = "DrawPointsAtPosition";

    private Paint mPaint;
    private Paint mPaintCoordinate;

    private String mEventType="无触摸事件";
    private float[] mPoint;
    private boolean isRefreshing = true;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(new DrawPointsView(this));

        mPaint = new Paint();
        mPaint.setColor(Color.RED);
        mPaint.setTextSize(28f);

        mPaintCoordinate = new Paint();
        mPaintCoordinate.setStrokeWidth(2f);
        mPaintCoordinate.setColor(Color.BLUE);
        mPaintCoordinate.setTextSize(24f);

        mPoint = new float[2];
    }





    class DrawPointsView extends View{

        public DrawPointsView(Context context) {
            super(context);
        }

        @Override
        protected void onDraw(Canvas canvas){
            if (isRefreshing){
                canvas.drawText("中心点坐标：（500,500）", 100,160,mPaint);
                canvas.drawLine(100,500, 800, 500, mPaint);
                canvas.drawLine(500,100, 500, 800, mPaint);
                drawStandardLines(canvas);
            }
            canvas.drawText("本程序测试触笔或手指与目测定位的偏移像素", 100,50,mPaint);
            canvas.drawText(mEventType, 100,100,mPaint);

            canvas.drawText("触摸事件坐标：(" + mPoint[0] + "," + mPoint[1] + ")", 100, 230, mPaintCoordinate);
            canvas.drawText("偏移像素: \u0394x=" + (int)(mPoint[0] - 500) + " \u0394y=" + (int)(mPoint[1] -500), 100, 260, mPaintCoordinate);
        }

        @Override
        public  boolean onTouchEvent(MotionEvent motionEvent){

//            if (motionEvent.)
            switch (motionEvent.getToolType(0)){
                case MotionEvent.TOOL_TYPE_STYLUS:
                    mEventType = "笔尖事件";
                    break;
                case MotionEvent.TOOL_TYPE_FINGER:
                    mEventType = "手指事件";
                    break;
                case MotionEvent.TOOL_TYPE_ERASER:
                    mEventType = "笔帽事件";
                    break;
                default:
                    mEventType = "未知触摸类型";
                    break;
            }

//            if("手指事件".equals(mEventType)||"未知触摸类型".equals(mEventType)){
//
//            }

            Log.d(TAG, motionEvent.getX() +":" + motionEvent.getY());
            //Toast.makeText(DrawPointsAtPosition.this, "(" + motionEvent.getX() + ", " + motionEvent.getY() + ")", Toast.LENGTH_SHORT).show();

            mPoint[0] = motionEvent.getX();
            mPoint[1] = motionEvent.getY();
            isRefreshing = true;
            invalidate();

            return  true;
        }

        private void drawStandardLines(Canvas canvas){

            Paint paint = new Paint();
            int nSteps = 0;
            paint.setColor(Color.RED);
            for (nSteps=0;nSteps<10; nSteps++) {
                paint.setStrokeWidth(1.0f + 1 * nSteps);
                canvas.drawLine(100f + nSteps * 40, 700f, 100f + nSteps * 40, 800f, paint);
                canvas.drawText((int)paint.getStrokeWidth()+"px",100f + nSteps * 40, 600f+90, paint);
            }
        }
    }


}

