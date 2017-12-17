package com.example.as.definedviewtest;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Shader;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.ViewGroup;

/**
 * Created by as on 2017/12/15.
 */

public class MyCircleImageView extends android.support.v7.widget.AppCompatImageView {

    private static final float DEF_WIDTH=100;       //layout_width为wrap_content下的宽
    private static final float DEF_HEIGHT=100;     //layout_height为wrap_content下的高
    private static final int DEF_BORDER_COLOR= Color.parseColor("#ff996357");   //默认的边框的颜色
    private static final float DEF_BORDER_WIDTH=18;     //默认的边框的宽度
    private static final float DEF_RADIUS=80;

    private float borderWidth;     //边框的宽度所占半经的比例
    private int borderColor;      //边框的颜色

    private float width;      //控件的宽
    private float height;     //控件的高

    private float radius;      //半径
    private Bitmap content;     //控件的背景

    private Paint widthPaint;    //负责绘制边框的一切
    private Paint circlePaint;    //负责绘制图片

    public MyCircleImageView(Context context) {
        this(context,null,0);
    }

    public MyCircleImageView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public MyCircleImageView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, 0);

        //初始化
        initParams(context,attrs);
        initPaints();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        Log.d(radius+"", "onMeasure: ++++++++++++"+radius);

        //父容器的宽，以及测量规则
        float parentWidth=MeasureSpec.getSize(widthMeasureSpec);
        int widthMode=MeasureSpec.getMode(widthMeasureSpec);
        
        //父容器的高，以及测量规则
        float parentHeight=MeasureSpec.getSize(heightMeasureSpec);
        int heightMode=MeasureSpec.getMode(heightMeasureSpec);


        ViewGroup.LayoutParams params=getLayoutParams();
        switch (widthMode)
        {
            case MeasureSpec.AT_MOST:
                Log.d(radius+"", "onMeasure: ++++++++++++"+1);
                if(params.width== ViewGroup.LayoutParams.MATCH_PARENT)
                    width=Math.min(parentWidth,2*(radius+borderWidth));
                else if(params.width== ViewGroup.LayoutParams.WRAP_CONTENT)
                {
                    //如果是wrap_content，则按照默认的参数来
                    width=DEF_WIDTH;
                    radius=DEF_RADIUS;
                    borderWidth=DEF_BORDER_WIDTH;
                }
                else
                    width=Math.min(params.width,2*(radius+borderWidth));
                break;
            case MeasureSpec.EXACTLY:
                Log.d(radius+"", "onMeasure: ++++++++++++"+2);
                if(params.width== ViewGroup.LayoutParams.MATCH_PARENT)
                    width=Math.min(parentHeight,2*(radius+borderWidth));
                else if(params.width== ViewGroup.LayoutParams.WRAP_CONTENT)
                {
                    //如果是wrap_content，则按照默认的参数来
                    width=DEF_WIDTH;
                    radius=DEF_RADIUS;
                    borderWidth=DEF_BORDER_WIDTH;
                }
                else
                    width=Math.min(params.width,2*(radius+borderWidth));
                break;
            default:
                Log.d(radius+"", "onMeasure: ++++++++++++"+3);
                if(params.width== ViewGroup.LayoutParams.MATCH_PARENT)
                    width=Math.min(parentHeight,2*(radius+borderWidth));
                else if(params.width== ViewGroup.LayoutParams.WRAP_CONTENT)
                {
                    //如果是wrap_content，则按照默认的参数来
                    width=DEF_WIDTH;
                    radius=DEF_RADIUS;
                    borderWidth=DEF_BORDER_WIDTH;
                }
                else
                    width=Math.min(params.width,2*(radius+borderWidth));
                break;

        }
        
        switch (heightMode)
        {
            case MeasureSpec.AT_MOST:
                if(params.height== ViewGroup.LayoutParams.MATCH_PARENT)
                    height=Math.min(parentHeight,2*(radius+borderWidth));
                else if(params.height== ViewGroup.LayoutParams.WRAP_CONTENT)
                {
                    height=DEF_HEIGHT;
                    radius=DEF_RADIUS;
                    borderWidth=DEF_BORDER_WIDTH;
                }
                else
                    height=Math.min(params.height,2*(radius+borderWidth));
                break;
            case MeasureSpec.EXACTLY:
                if(params.height== ViewGroup.LayoutParams.MATCH_PARENT)
                    height=Math.min(parentHeight,2*(radius+borderWidth));
                else if(params.height== ViewGroup.LayoutParams.WRAP_CONTENT)
                {
                    height=DEF_HEIGHT;
                    radius=DEF_RADIUS;
                    borderWidth=DEF_BORDER_WIDTH;
                }
                else
                    height=Math.min(params.height,2*(radius+borderWidth));
                break;
            default:
                if(params.height== ViewGroup.LayoutParams.MATCH_PARENT)
                    height=Math.min(parentHeight,2*(radius+borderWidth));
                else if(params.height== ViewGroup.LayoutParams.WRAP_CONTENT)
                {
                    height=DEF_HEIGHT;
                    radius=DEF_RADIUS;
                    borderWidth=DEF_BORDER_WIDTH;
                }
                else
                    height=Math.min(params.height,2*(2*(radius+borderWidth)));
                break;

        }

        setMeasuredDimension(Math.min((int)width,(int)height),Math.min((int)width,(int)height));
    }

    @Override
    protected void onDraw(Canvas canvas) {
        //super.onDraw(canvas);
        Log.d("", "onDraw: ++++++++++++++");

        drawableToBitmap();
        BitmapShader shader=new BitmapShader(content, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);
        circlePaint.setShader(shader);
        canvas.drawCircle(width/2,height/2,width/2-borderWidth/2,circlePaint);

        //canvas.save();
        //Path path=new Path();
        //path.addCircle(width/2,height/2,radius+borderWidth/2, Path.Direction.CCW);
        //canvas.clipPath(path);
        //canvas.drawBitmap(content,new Matrix(),circlePaint);
        //canvas.restore();
        canvas.drawCircle(width/2,height/2,width/2-borderWidth/2,widthPaint);
    }

    /**
     * 初始化自定义的属性的值
     * @param context 上下文对象
     * @param attrs 属性的集合
     */
    private void initParams(Context context,@Nullable AttributeSet attrs)
    {
        TypedArray array=context.obtainStyledAttributes(attrs,R.styleable.MyCircleImageView);
        borderWidth=array.getDimension(R.styleable.MyCircleImageView_border_width,DEF_BORDER_WIDTH);
        borderColor=array.getColor(R.styleable.MyCircleImageView_border_color,DEF_BORDER_COLOR);
        radius=array.getDimension(R.styleable.MyCircleImageView_circle_radius,DEF_RADIUS);

        //最后回收资源
        array.recycle();
    }

    /**
     * 初始化画笔
     */
    private void initPaints()
    {
        //实例化画笔并且指定抗锯齿
        widthPaint=new Paint(Paint.ANTI_ALIAS_FLAG);
        widthPaint.setColor(borderColor);
        widthPaint.setStyle(Paint.Style.STROKE);
        widthPaint.setStrokeWidth(borderWidth);

        circlePaint=new Paint(Paint.ANTI_ALIAS_FLAG);
    }


    /**
     * 将控件的背景转化成Bitmap并放缩成正确的大小
     */
    private void drawableToBitmap()
    {
        Drawable background=getDrawable();

        if(background==null)
            return;
        if(getDrawable() instanceof BitmapDrawable)
            content=((BitmapDrawable)background).getBitmap();
        else
            content= Bitmap.createBitmap(background.getIntrinsicWidth(),background.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        Matrix matrix=new Matrix();
        matrix.setScale(width/background.getIntrinsicWidth(),height/background.getIntrinsicHeight());
        content=Bitmap.createBitmap(content,0,0,content.getWidth(),content.getHeight(),matrix,true);
    }


}
