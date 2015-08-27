package com.mix.weather;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PaintFlagsDrawFilter;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Toast;

public class LineChart extends View {

	public int[] maxTemp;
	public int[] minTemp;
	Context context;
	public int maxY = 30;
	public int minY = 60;
	public int startX = 0;

	public LineChart(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	public LineChart(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public LineChart(Context context) {
		super(context);
		this.context = context;
	}

	// 快速排序
	public int getMiddle(int[] buffer, int low, int high) {
		int tmp = buffer[low]; // 数组的第一个作为中轴
		while (low < high) {
			while (low < high && buffer[high] >= tmp) {
				high--;
			}
			buffer[low] = buffer[high]; // 比中轴小的记录移到低端
			while (low < high && buffer[low] < tmp) {
				low++;
			}
			buffer[high] = buffer[low]; // 比中轴大的记录移到高端
		}
		buffer[low] = tmp; // 中轴记录到尾
		return low; // 返回中轴的位置
	}

	// 递归形式的分治排序算法
	public void _quickSort(int[] buffer, int low, int high) {
		if (low < high) {
			int middle = getMiddle(buffer, low, high); // 将buffer数组进行一分为二
			_quickSort(buffer, low, middle - 1); // 对低字表进行递归排序
			_quickSort(buffer, middle + 1, high); // 对高字表进行递归排序
		}
	}

	/**
	 * @param buffer
	 * @return true数组成功排序 false：数组为空
	 */
	public boolean quick(int[] buffer) {
		if (buffer.length > 0) { // 查看数组是否为空
			_quickSort(buffer, 0, buffer.length - 1);
			return true;
		}
		return false;
	}

	//画折线
	private boolean paintLine(Canvas canvas, int YPoint, int[] buffer){
		Paint paint = new Paint();
		// 让画出的图形是空心的
		paint.setStyle(Paint.Style.FILL);
		paint.setAntiAlias(true);// 去锯齿
		paint.setColor(Color.rgb(128, 128, 0));
		paint.setTextSize(25);
		
		if(buffer == null || buffer.length <= 0){
			return false;
		}
		int[] temp = new int[buffer.length];
		for (int i = 0; i < temp.length; i++) {
			temp[i] = buffer[i];
		}
		if (!quick(temp)) {
			return false;
		}
		
		for (int i = 0; i < buffer.length - 1; i++) {
			canvas.drawLine(startX * (i+1), temp[temp.length - 1] - buffer[i] + YPoint,
					startX * (i + 2), temp[temp.length - 1] - buffer[i + 1] + YPoint,
					paint);
			canvas.drawCircle(startX * (i+1), temp[temp.length - 1] + YPoint - buffer[i],
					5, paint);
		}
		canvas.setDrawFilter(new PaintFlagsDrawFilter(0, Paint.ANTI_ALIAS_FLAG|Paint.FILTER_BITMAP_FLAG));
		canvas.drawCircle(startX * buffer.length, temp[temp.length - 1] + YPoint
				- buffer[buffer.length - 1], 5, paint);

		Paint paint2 = new Paint();
		paint2.setStyle(Paint.Style.FILL);
		paint2.setAntiAlias(true);
		paint2.setColor(Color.rgb(255, 0, 0));
		paint2.setTextSize(25);
		for (int i = 0; i < buffer.length; i++) {
			canvas.drawText(buffer[i]/10 + "", startX * (i+1), temp[temp.length - 1]
					- buffer[i] + YPoint - 4, paint2);
		}
		return true;
	}
	
	
	@Override
	protected void onDraw(Canvas canvas) {	
		super.onDraw(canvas);
		
		boolean flag = paintLine(canvas, maxY, maxTemp);
		if(!flag){
			Toast.makeText(context, "暂无最高温度数据", Toast.LENGTH_SHORT).show();
		}
		
		flag = paintLine(canvas, minY, minTemp);
		if(!flag){
			Toast.makeText(context, "暂无最低温度数据", Toast.LENGTH_SHORT).show();
		}
	}

}
