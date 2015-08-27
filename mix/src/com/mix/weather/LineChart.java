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

	// ��������
	public int getMiddle(int[] buffer, int low, int high) {
		int tmp = buffer[low]; // ����ĵ�һ����Ϊ����
		while (low < high) {
			while (low < high && buffer[high] >= tmp) {
				high--;
			}
			buffer[low] = buffer[high]; // ������С�ļ�¼�Ƶ��Ͷ�
			while (low < high && buffer[low] < tmp) {
				low++;
			}
			buffer[high] = buffer[low]; // �������ļ�¼�Ƶ��߶�
		}
		buffer[low] = tmp; // �����¼��β
		return low; // ���������λ��
	}

	// �ݹ���ʽ�ķ��������㷨
	public void _quickSort(int[] buffer, int low, int high) {
		if (low < high) {
			int middle = getMiddle(buffer, low, high); // ��buffer�������һ��Ϊ��
			_quickSort(buffer, low, middle - 1); // �Ե��ֱ���еݹ�����
			_quickSort(buffer, middle + 1, high); // �Ը��ֱ���еݹ�����
		}
	}

	/**
	 * @param buffer
	 * @return true����ɹ����� false������Ϊ��
	 */
	public boolean quick(int[] buffer) {
		if (buffer.length > 0) { // �鿴�����Ƿ�Ϊ��
			_quickSort(buffer, 0, buffer.length - 1);
			return true;
		}
		return false;
	}

	//������
	private boolean paintLine(Canvas canvas, int YPoint, int[] buffer){
		Paint paint = new Paint();
		// �û�����ͼ���ǿ��ĵ�
		paint.setStyle(Paint.Style.FILL);
		paint.setAntiAlias(true);// ȥ���
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
			Toast.makeText(context, "��������¶�����", Toast.LENGTH_SHORT).show();
		}
		
		flag = paintLine(canvas, minY, minTemp);
		if(!flag){
			Toast.makeText(context, "��������¶�����", Toast.LENGTH_SHORT).show();
		}
	}

}
