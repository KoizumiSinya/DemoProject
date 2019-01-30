package jp.sinya.circleseekbar.demo5;



import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.LinearGradient;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PaintFlagsDrawFilter;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.Shader.TileMode;
import android.graphics.drawable.BitmapDrawable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

public class RotatView extends View{

	
	
	/** The context */
	private Context mContext;
	
	/**
     * ��ת��ͼƬ
     */
    Bitmap rotatBitmap;
    

	/**
     * ��ת�ı���ͼƬ
     */
    Bitmap rotatbjBitmap;
    
    /**
     * ����ͼƬ�Ŀ��
     */
    int centerwidth;

    /**
     * ����ͼƬ�ĸ߶�
     */
    int centerheight;
    /**
     * ���ı���ͼƬ�Ŀ��
     */
    int centerbjwidth;
    
    /**
     * ���ı���ͼƬ�ĸ߶�
     */
    int centerbjheight;
    
    /**
     * ԭ������x
     */
    float o_x;

    /**
     * ԭ������y
     */
    float o_y;
    /**
     * ԭ������xƫ��
     */
    float o_x_p;
    
    /**
     * ԭ������yƫ��
     */
    float o_y_p;
    
    /**
     * view����ʵ�����߶�:��Ϊ����ת���������view�������Σ�����ֵ��ͼƬ�ĶԽ��߳���
     */
    double maxwidth;
    
    /**
     * ��ǰ�Ļ���(�Ը� view ������ΪԲ��)
     */
    float current_degree;

    /**
     * ����ʱ�Ļ���(�Ը� view ������ΪԲ��)
     */
    float up_degree;

    /**
     * ��ǰԲ����ת�Ļ���(�Ը� view ������ΪԲ��)
     */
    float deta_degree;
    /**
     * ���ȵ���ʼ�Ƕ�(�Ը� view ������ΪԲ��)
     */
    float start_degree = 135;
    /**
     * ���ȵĽ����������ٽǶ�(�Ը� view ������ΪԲ��)
     */
    float end_degree ;
    public float getEnd_degree() {
		return end_degree;
	}

	public void setEnd_degree(float end_degree) {
		this.end_degree = end_degree;
	}

	public float getCenter_img_degree() {
		return center_img_degree;
	}

	public void setCenter_img_degree(float center_img_degree) {
		this.center_img_degree = center_img_degree;
	}

	/**
     * ����ͼƬ��ת���ٽǶ�(�Ը� view ������ΪԲ��)
     */
    float center_img_degree ;
    
    /**
     * ������½Ƕ�
     */
    float down_degrees ;
    /**
     * �ƶ��Ƕ�
     */
    float move_degress ;
    
    /**
     * �Ƿ�Ϊ˳ʱ����ת
     */
    boolean isClockWise;
    
    /** �����ı��¼� */
	private OnSeekChangeListener mListener;
	
	/**
     * �Ƿ����ֶ�ģʽ
     */
    boolean isHandMove ;
	
	
	
	public boolean isHandMove() {
		return isHandMove;
	}

	public void setHandMove(boolean isHandMove) {
		this.isHandMove = isHandMove;
	}

	public OnSeekChangeListener getmListener() {
		return mListener;
	}

	public void setmListener(OnSeekChangeListener mListener) {
		this.mListener = mListener;
	}

	public interface OnSeekChangeListener {
		public void onProgressChange(RotatView view, float newProgress);
	}
    
	
    
    public RotatView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		// TODO Auto-generated constructor stub
	}

	public RotatView(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
	}

	public RotatView(Context context) {
		super(context);
		mContext = context;
		//initDrawable();
		// TODO Auto-generated constructor stub
	}
	
	{
		//System.out.println("��ʼ��");
		mListener = new OnSeekChangeListener() {

			@Override
			public void onProgressChange(RotatView view, float newProgress) {

			}
		};
	}

    public void setRotatDrawableResource(int id,int bjid) {

        BitmapDrawable drawable = (BitmapDrawable)getContext().getResources().getDrawable(id);
        BitmapDrawable drawablebj = (BitmapDrawable)getContext().getResources().getDrawable(bjid);
        setRotatDrawable(drawable,drawablebj);
    }

    public void setRotatDrawable(BitmapDrawable drawable,BitmapDrawable drawablebj) {
    	rotatBitmap = drawable.getBitmap();
    	rotatbjBitmap = drawablebj.getBitmap();
        initSize();
        postInvalidate();
    }
    
   
	
    private void initSize() {
        if (rotatBitmap == null) {

            // throw new NoBitMapError("Error,No bitmap in RotatView!");
            return;
        }
        centerwidth = rotatBitmap.getWidth();
        centerheight = rotatBitmap.getHeight();
        centerbjwidth = rotatbjBitmap.getWidth();
        centerbjheight = rotatbjBitmap.getHeight();

        //System.out.println("����ͼ�Ŀ�" + centerwidth);
       // System.out.println("����ͼ�ĸ�" + centerheight);
        //maxwidth = Math.sqrt(centerwidth * centerwidth + centerheight * centerheight);
        //System.out.println("�Խ��ߵ�ֵ" + maxwidth);
        
        o_x = (float)(centerbjwidth / 2);//ȷ��Բ������
        o_y = (float)(centerbjheight / 2);//ȷ��Բ������
        
        o_x_p = (float)(centerbjwidth - centerwidth)/2;
        o_y_p = (float)(centerbjheight - centerheight)/2;
        
    }
    
    
	protected void onDraw(Canvas canvas) {

    	Paint p=new Paint();
    	
    	p.setAntiAlias(true); 
    	/**
    	 * LinearGradient shader = new LinearGradient(0, 0, endX, endY, new int[]{startColor, midleColor, endColor},new float[]{0 , 0.5f, 1.0f}, TileMode.MIRROR);
		        ���в���new int[]{startColor, midleColor, endColor}�ǲ��뽥��Ч������ɫ���ϣ�
		        ���в���new float[]{0 , 0.5f, 1.0f}�Ƕ���ÿ����ɫ���ڵĽ������λ�ã�
		        �����������Ϊnull�����Ϊnull��ʾ���е���ɫ��˳����ȵķֲ�
    	 */
		Shader  mShader = new LinearGradient(15, 100, centerbjwidth-10, centerbjheight-10, 
				new int[]{0xFF00BFFF, 0xFF00CD00},new float[]{0,1.0f}, TileMode.MIRROR);
		p.setShader(mShader);
		RectF oval2 = new RectF(10, 10, centerbjwidth-10, centerbjheight-10);// ���ø��µĳ����Σ�ɨ�����  
        
        /**
         *  ��������һ��������RectF�������ǵڶ��������ǽǶȵĿ�ʼ��
         *  �����������Ƕ��ٶȣ����ĸ����������ʱ�����Σ��Ǽٵ�ʱ�򻭻���  
         */
		canvas.drawArc(oval2, start_degree, end_degree, true, p);
    	
		Matrix matrixbj = new Matrix();
		Matrix matrix = new Matrix();
        // ����ѡ��ͼƬƫ��λ��
        matrix.setTranslate(o_x_p, o_y_p);
        
		//System.out.println("Բ��x����" + o_x);
		//System.out.println("Բ��Y����" + o_y);
        // ��ʼת
        matrix.preRotate(center_img_degree,o_x-o_x_p, o_y-o_y_p);
        
        
        
        canvas.setDrawFilter(new PaintFlagsDrawFilter(0, Paint.ANTI_ALIAS_FLAG|Paint.FILTER_BITMAP_FLAG)); 
		canvas.drawBitmap(rotatbjBitmap, matrixbj, p);
		canvas.drawBitmap(rotatBitmap, matrix,p);
		  	
		
		super.onDraw(canvas);
	}
    
    
    @Override
	public boolean onTouchEvent(MotionEvent event) {
		float x = event.getX();
		float y = event.getY();
		boolean up = false;
		if(isHandMove){
			switch (event.getAction()) {
			
			case MotionEvent.ACTION_DOWN:
				up = true;
				moved(x, y, up);
				break;
			case MotionEvent.ACTION_MOVE:
				up = false;
				moved(x, y, up);
				
				break;
			case MotionEvent.ACTION_UP:
				
				moved(x, y, up);
				break;
			}
		}
		
		return true;
	}
    
    private void moved(float x, float y, boolean up) {
    	
    	float distance = (float) Math.sqrt(Math.pow((x - o_x), 2) + Math.pow((y - o_y), 2));
    	//System.out.println("����" + distance);
    	if(distance < centerbjwidth/2){
    		
			float degrees = (float) ((float) ((Math.toDegrees(Math.atan2(x - o_x, o_y - y)) + 360.0)) % 360.0);
			//System.out.println("�����Ƕ�" + degrees);
			degrees = degrees-225;
			if(up == true){
				down_degrees = degrees;
			}else{
				move_degress = degrees;
			}
			//System.out.println("�Ƕ�" + degrees);		
			//computeCurrentAngle(x, y);
			
			if(move_degress-down_degrees >0){
				//System.out.println("˳ʱ��");
				isClockWise = true;
			}else{
				//System.out.println("��ʱ��");
				isClockWise = false;
			}
			if(degrees < 0){
				current_degree = degrees + 360;
			}else{
				current_degree = degrees;
			}
			
			
			/*if(current_degree > 270 ){
				current_degree = 270;
			}
			if(current_degree < 10 ){
				current_degree = 10;
			}*/
			
			//System.out.println("��ǰ�Ƕ�" + current_degree);
			/*if(current_degree>120){
				current_degree = 120;
			}*/
			/*if(current_degree<250){
				current_degree = 250;
			}*/
			
			if(current_degree >260 || current_degree < 10){
				System.out.println("����ת����Χ");
			}else{
				end_degree = current_degree;
				if(end_degree >360 ){
					end_degree = end_degree % 360;
				}
				center_img_degree = current_degree;
				postInvalidate();
			}
    	}
    	mListener.onProgressChange(this, current_degree);
	}
}
