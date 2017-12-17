# DefinedViewTest
自定义View练习
## 1. MyCircleImageView--自定义圆形ImageView  
闲来无事自定义一个圆形控件玩玩儿：  
### 1.1 先看看有哪些属性  
```  
<!--自定义圆形ImageView-->
    <declare-styleable name="MyCircleImageView">
        <attr name="border_width" format="dimension"/>
        <attr name="border_color" format="color" />
        <attr name="circle_radius" format="dimension"/>
    </declare-styleable>
```  
第一个属性是圆形控件的外围的描边的宽度，第二个属性是描边的颜色，第三个就是圆的半径；  
### 1.2 得到背景图  
绘制一个Bitmap首先要得到他，但是，如何通过一个Drawable得到Bitma呢？  
```  
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
```  
这是我的做法，再看看网上大神的做法：  
```  
private Bitmap getBitmapFromDrawable(Drawable drawable) {
        if (drawable == null) {
            return null;
        }

        if (drawable instanceof BitmapDrawable) {
            return ((BitmapDrawable) drawable).getBitmap();
        }

        try {
            Bitmap bitmap;

            if (drawable instanceof ColorDrawable) {
                bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
            } else {
                bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
            }

            Canvas canvas = new Canvas(bitmap);
            drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
            drawable.draw(canvas);
            Matrix matrix=new Matrix();
            matrix.setScale(width/drawable.getIntrinsicWidth(),height/drawable.getIntrinsicHeight());
            bitmap=Bitmap.createBitmap(bitmap,0,0,(int)width,(int)height,matrix,true);
            return bitmap;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
```  
实际上无非是对各种的Drawable进行判断和处理，从中解析出Bitmap。  
### 1.3 绘制出圆形  
#### 1.3.1 通过设置BitmapShader+Canvas.drawCircle绘制出来  
```  
@Override
    protected void onDraw(Canvas canvas) {
        //super.onDraw(canvas);
        Log.d("", "onDraw: ++++++++++++++");

        drawableToBitmap();
        BitmapShader shader=new BitmapShader(content, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);
        circlePaint.setShader(shader);
        canvas.drawCircle(width/2,height/2,radius,circlePaint);
        
        canvas.drawCircle(width/2,height/2,width/2-borderWidth/2,widthPaint);
    }
```  
#### 1.3.2 通过Canvas.clipPath(Path)+Canvas.drawBitmap  
```  
@Override
    protected void onDraw(Canvas canvas) {
        //super.onDraw(canvas);
        Log.d("", "onDraw: ++++++++++++++");

        drawableToBitmap();

        canvas.save();
        Path path=new Path();
        path.addCircle(width/2,height/2,radius+borderWidth/2, Path.Direction.CCW);
        canvas.clipPath(path);
        canvas.drawBitmap(content,new Matrix(),circlePaint);
        canvas.restore();
        canvas.drawCircle(width/2,height/2,width/2-borderWidth/2,widthPaint);
    }
```  
注意：第二种方法一定要有Canvas.save和Canvas.restore两句  
眼见的你或许会发现，我把super.onDraw()给注释掉了，这是因为，我在之前绘制完了之后，原来那个方形的背景不会消失；因为那个是系统的ImageView绘制出来的，所以我选择将那两句注释掉问题就解决了。  
