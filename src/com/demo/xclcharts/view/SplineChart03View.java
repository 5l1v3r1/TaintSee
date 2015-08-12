package com.demo.xclcharts.view;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import org.xclcharts.chart.CustomLineData;
import org.xclcharts.chart.PointD;
import org.xclcharts.chart.SplineChart;
import org.xclcharts.chart.SplineData;
import org.xclcharts.common.IFormatterDoubleCallBack;
import org.xclcharts.common.IFormatterTextCallBack;
import org.xclcharts.event.click.PointPosition;
import org.xclcharts.renderer.XChart;
import org.xclcharts.renderer.XEnum;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;

import com.demo.taintsee.MainActivity;
import com.taintflow.TaintFlowDataBaseHelper.TaintInfo;

@SuppressLint("ClickableViewAccessibility") public class SplineChart03View extends DemoView {

	private String TAG = "SplineChart03View";
	private SplineChart chart = new SplineChart();
	// �������ǩ����
	private LinkedList<String> labels = new LinkedList<String>();
	private LinkedList<SplineData> chartData = new LinkedList<SplineData>();

	private Paint mPaintTooltips = new Paint(Paint.ANTI_ALIAS_FLAG);

	private List<CustomLineData> mXCustomLineDataset = new ArrayList<CustomLineData>();
	private List<CustomLineData> mYCustomLineDataset = new ArrayList<CustomLineData>();
	
	private  int MAX = 0;
	Context context = null;
	
	public SplineChart03View(Context context) {	
		super(context);
		this.context = context;
		initView();
	}

	public SplineChart03View(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.context = context;
		initView();
	}

	public SplineChart03View(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		this.context = context;
		initView();
	}

	private void initView() {
		//chartLabels();
		//chartCustomeLines();
		chartDataSet();
		chartRender();
	}

	@Override
	protected void onSizeChanged(int w, int h, int oldw, int oldh) {
		super.onSizeChanged(w, h, oldw, oldh);
		// ͼ��ռ��Χ��С
		chart.setChartRange(w, h);
	}

	private void chartRender() {
		try {

			// ���û�ͼ��Ĭ������pxֵ,���ÿռ���ʾAxis,Axistitle....
			int[] ltrb = getBarLnDefaultSpadding();
			chart.setPadding(ltrb[0], ltrb[1], ltrb[2], ltrb[3]);

			// ��ʾ�߿�
			chart.showRoundBorder();

			// ����Դ
			chart.setCategories(labels);
			chart.setDataSource(chartData);

			// ����ϵ
			// ���������ֵ
			int step = MAX /10 +1;
			chart.getDataAxis().setLabelFormatter(new IFormatterTextCallBack() {

				@Override
				public String textFormatter(String value) {

					DecimalFormat df = new DecimalFormat("#0");
					Double tmp = Double.parseDouble(value);
					String label = df.format(tmp).toString();
					return label;
				}

			});
			chart.getDataAxis().setAxisMax(step * 10);
			 chart.getDataAxis().setAxisMin(0);
			// ������̶ȼ��
			chart.getDataAxis().setAxisSteps(step);
			chart.setCustomLines(mYCustomLineDataset); // y��

			// ��ǩ�����ֵ
			chart.setCategoryAxisMax(8);
			// ��ǩ����Сֵ
			chart.setCategoryAxisMin(1);
			// chart.setCustomLines(mXCustomLineDataset); //y��
			chart.setCategoryAxisCustomLines(mXCustomLineDataset); // x��

			// ����ͼ�ı���ɫ
			chart.setApplyBackgroundColor(true);
			chart.setBackgroundColor(Color.rgb(255,255,255));
			chart.getBorder().setBorderLineColor(Color.rgb(179, 147, 197));

			// �������������߷��
			chart.getCategoryAxis().hideTickMarks();
			chart.getDataAxis().hideAxisLine();
			chart.getDataAxis().hideTickMarks();
			chart.getPlotGrid().showHorizontalLines();
			// chart.hideTopAxis();
			// chart.hideRightAxis();

			chart.getPlotGrid().getHorizontalLinePaint()
					.setColor(Color.rgb(179, 147, 197));
			chart.getCategoryAxis()
					.getAxisPaint()
					.setColor(
							chart.getPlotGrid().getHorizontalLinePaint()
									.getColor());
			chart.getCategoryAxis()
					.getAxisPaint()
					.setStrokeWidth(
							chart.getPlotGrid().getHorizontalLinePaint()
									.getStrokeWidth());

			// ���彻����ǩ��ʾ��ʽ,�ر�ע,������ͼ�������ԣ����Է��ظ�ʽΪ: xֵ,yֵ
			// �����з�������
			chart.setDotLabelFormatter(new IFormatterTextCallBack() {

				@Override
				public String textFormatter(String value) {
					String label = "[" + value + "]";
					return (label);
				}

			});
			
			chart.setItemLabelFormatter(new IFormatterDoubleCallBack() {
				@Override
				public String doubleFormatter(Double value) {
					DecimalFormat df=new DecimalFormat("#0");					 
					String label = df.format(value).toString();
					return label;
				}});
			
			
			// ����
			chart.setTitle("һ������ͼ");
			chart.addSubtitle("��˽й©ͳ��");

			// ����������
			chart.ActiveListenItemClick();
			// Ϊ���ô�������������������5px�ĵ��������Χ
			chart.extPointClickRange(5);
			chart.showClikedFocus();

			// ��ʾƽ������
			chart.setCrurveLineStyle(XEnum.CrurveLineStyle.BEZIERCURVE);

			// ͼ����ʾ�����·�
			chart.getPlotLegend().setVerticalAlign(XEnum.VerticalAlign.BOTTOM);
			chart.getPlotLegend().setHorizontalAlign(
					XEnum.HorizontalAlign.CENTER);

		} catch (Exception e) {
			e.printStackTrace();
			Log.e(TAG, e.toString());
		}
	}

	@SuppressLint("SimpleDateFormat") private void chartDataSet() {
		Date day;
		String dayString;
		int number;
		// ��1�����ݼ�
		List<PointD> linePoint1 = new ArrayList<PointD>();
		for (int i = 7; i >=0; i--) {
			day = getDateBeforeOrAfter(i * -1);
			SimpleDateFormat ss = new SimpleDateFormat("MM-dd");
			dayString = ss.format(day);
			
			labels.add(dayString);
			
			number = getNumberday(context,dayString);
			if (MAX < number) {
				MAX = number;
			}
			linePoint1.add(new PointD(8-i, number));
		}

		SplineData dataSeries1 = new SplineData("����ͼ", linePoint1, Color.rgb(54,
				141, 238));
		// ����Ūϸ��
		dataSeries1.getLinePaint().setStrokeWidth(2);
		chartData.add(dataSeries1);

	}

	public static  int getNumberday(Context context,String day) {
		int number = 0;
		Cursor cursor = MainActivity.taintInfo.select_time(day);
		try {
			number = cursor.getCount();
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			if (cursor != null) {
				cursor.close();
			}
		}
		
		return number;
	}

	@SuppressLint("SimpleDateFormat") public static Date getFormatDateTime(String currDate, String format) {
		if (currDate == null) {
			return null;
		}
		SimpleDateFormat dtFormatdB = null;
		try {
			dtFormatdB = new SimpleDateFormat(format);
			return dtFormatdB.parse(currDate);
		} catch (Exception e) {
			try {
				return dtFormatdB.parse(currDate);
			} catch (Exception ex) {
			}
		}
		return null;
	}

	public static Date getDateBeforeOrAfter(int iDate) {
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DAY_OF_MONTH, iDate);
		return cal.getTime();
	}

	public static String getFormatDateV2(String date) {
		if (date == null) {
			return null;
		}

		String[] datearr = date.split("-");
		if (datearr == null || datearr.length != 3) {
			return date;
		}

		StringBuffer ret = new StringBuffer();
		ret.append(Integer.parseInt(datearr[1]) < 10 ? "0"
				+ Integer.parseInt(datearr[1]) : datearr[1]);
		ret.append("-");
		ret.append(Integer.parseInt(datearr[2]) < 10 ? "0"
				+ Integer.parseInt(datearr[2]) : datearr[2]);
		return ret.toString();
	}



	@Override
	public void render(Canvas canvas) {
		try {
			chart.render(canvas);
		} catch (Exception e) {
			Log.e(TAG, e.toString());
		}
	}

	@Override
	public List<XChart> bindChart() {
		List<XChart> lst = new ArrayList<XChart>();
		lst.add(chart);
		return lst;
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {

		super.onTouchEvent(event);

		if (event.getAction() == MotionEvent.ACTION_UP) {
			triggerClick(event.getX(), event.getY());
		}
		return true;
	}

	// ��������
	private void triggerClick(float x, float y) {
		if (!chart.getListenItemClickStatus())
			return;

		PointPosition record = chart.getPositionRecord(x, y);
		if (null == record)
			return;

		if (record.getDataID() >= chartData.size())
			return;
		SplineData lData = chartData.get(record.getDataID());
		List<PointD> linePoint = lData.getLineDataSet();
		int pos = record.getDataChildID();
		int i = 0;
		Iterator<PointD> it = linePoint.iterator();
		while (it.hasNext()) {
			PointD entry = (PointD) it.next();

			if (pos == i) {
				Double xValue = entry.x;
				Double yValue = entry.y;

				float r = record.getRadius();
				chart.showFocusPointF(record.getPosition(), r + r * 0.8f);
				chart.getFocusPaint().setStyle(Style.FILL);
				chart.getFocusPaint().setStrokeWidth(3);
				if (record.getDataID() >= 2) {
					chart.getFocusPaint().setColor(Color.BLUE);
				} else {
					chart.getFocusPaint().setColor(Color.RED);
				}
				// �ڵ������ʾtooltip
				mPaintTooltips.setColor(Color.RED);
				chart.getToolTip().setCurrentXY(x, y);
				chart.getToolTip().addToolTip(" Key:" + lData.getLineKey(),
						mPaintTooltips);
				chart.getToolTip().addToolTip(
						" Current Value:" + Double.toString(xValue) + ","
								+ Double.toString(yValue), mPaintTooltips);
				chart.getToolTip().getBackgroundPaint().setAlpha(100);
				this.invalidate();

				break;
			}
			i++;
		}// end while

	}

}
