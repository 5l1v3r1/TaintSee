package com.demo.xclcharts.view;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.xclcharts.chart.BarChart;
import org.xclcharts.chart.BarData;
import org.xclcharts.chart.CustomLineData;
import org.xclcharts.common.DrawHelper;
import org.xclcharts.common.IFormatterDoubleCallBack;
import org.xclcharts.common.IFormatterTextCallBack;
import org.xclcharts.renderer.XChart;
import org.xclcharts.renderer.XEnum;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Align;
import android.util.Log;

import com.demo.taintsee.MainActivity;
import com.taintflow.Service.App_detailService;
import com.taintflow.Service.PowerService;
import com.taintflow.TaintFlowDataBaseHelper.TaintInfo;
import com.taintflow.infos.App_infos;
import com.taintflow.infos.Infos;

public class BarChart08View extends DemoView {

	private static final int ACCESSIBILITY_LIVE_REGION_ASSERTIVE = 0;
	private String TAG = "BarChart08View";
	private BarChart chart = new BarChart();
	// ������Դ
	private List<String> chartLabels;
	private List<BarData> chartData;
	private List<CustomLineData> mCustomLineDataset;

	Intent intent;
	private String subtitle = "";
	private Context context;
	static String package_name = null;
	static String app_name;
	static ArrayList<Infos> charts;

	public BarChart08View(Context context, Intent intent) {

		super(context);
		this.context = context;
		this.intent = intent;

		init();

	}

	private void init() {

		app_name = intent.getExtras().getString("app_Name");
		package_name = App_infos.getPackageName_app(context, app_name);
		/*charts = App_detailService.get_Chart_Info_List_App(context, app_name);*/
		charts = App_detailService.get_Chart_Info_List_App(context, app_name);

		chartLabels = BarChartService.getLabels(intent, context);
		chartData = BarChartService.getDate(intent, context);
		mCustomLineDataset = BarChartService.getCustomLineDatas(intent);
		subtitle = intent.getExtras().getString("app_Name");

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

			// ����
			chart.setTitle("��˽й©Ƶ��ͳ��ͼ");
			chart.addSubtitle(subtitle);
			// ����Դ
			chart.setDataSource(chartData);
			chart.setCategories(chartLabels);
			chart.setCustomLines(mCustomLineDataset);
			int max = 0;
			for (int i = 0; i < charts.size(); i++) {
				if (max < charts.get(i).getNumber()) {
					max = charts.get(i).getNumber();
				}
			}
			max = (int)(max*1.2);
			int step = max/10 +1;
					

			// ������
			chart.getDataAxis().setAxisMax(step * 10);
			chart.getDataAxis().setAxisMin(0);
			chart.getDataAxis().setAxisSteps(step);
			// ָ�����ٸ���̶�(��ϸ�̶�)��Ϊ���̶�
			chart.getDataAxis().setDetailModeSteps(2);

			chart.getDataAxis().enabledAxisStd();
			chart.getDataAxis().setAxisStd(0);
			chart.getCategoryAxis().setAxisBuildStd(true);

			// ��������
			chart.getPlotGrid().showHorizontalLines();

			// �����������ǩ��ʾ��ʽ
			chart.getDataAxis().setLabelFormatter(new IFormatterTextCallBack() {

				@Override
				public String textFormatter(String value) {
					Double tmp = Double.parseDouble(value);
					DecimalFormat df = new DecimalFormat("#0");
					String label = df.format(tmp).toString();
					return (label);
				}

			});

			// ��ǩ��ת45��
			 chart.getCategoryAxis().setTickLabelRotateAngle(45f);
			chart.getCategoryAxis().getTickLabelPaint().setTextSize(15);
			
			chart.getCategoryAxis().setTickLabelMargin(ACCESSIBILITY_LIVE_REGION_ASSERTIVE);

			// �����ζ�����ʾֵ
			chart.getBar().setItemLabelVisible(true);
			// �趨��ʽ
			chart.setItemLabelFormatter(new IFormatterDoubleCallBack() {
				@Override
				public String doubleFormatter(Double value) {
					DecimalFormat df = new DecimalFormat("#0");
					String label = df.format(value).toString();
					return label;
				}
			});

			// ����Key
			chart.getPlotLegend().hide();

			// �����Ӽ�û�հ�
			chart.getBar().setBarInnerMargin(0.5f); // �ɳ���0.1��0.5����ɶЧ����

			// ����ƽ��ģʽ
			// chart.disablePanMode();

			chart.disableHighPrecision();

			// ����ֻ�����һ���
			chart.setPlotPanMode(XEnum.PanMode.HORIZONTAL);

			chart.setBarCenterStyle(XEnum.BarCenterStyle.TICKMARKS);

			// chart.showRoundBorder();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);

	@Override
	public void render(Canvas canvas) {
		try {
			chart.render(canvas);

			paint.setTextSize(22);
			paint.setColor(Color.RED);

			float textHeight = DrawHelper.getInstance().getPaintFontHeight(
					paint);
			paint.setTextAlign(Align.LEFT);
			canvas.drawText("Ƶ��", chart.getPlotArea().getLeft(), chart
					.getPlotArea().getTop() - textHeight, paint);

			paint.setTextAlign(Align.RIGHT);
			canvas.drawText("��˽��������", chart.getPlotArea().getRight(), chart
					.getPlotArea().getBottom() + textHeight, paint);

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

	private static class BarChartService {

		public static List<String> getLabels(Intent intent, Context context) {

			LinkedList<String> chartLabels = new LinkedList<String>();
			for (int i = 0; i < charts.size(); i++) {
				chartLabels.add(charts.get(i).getName());
			}
			return (LinkedList<String>) chartLabels;
		}

		public static List<BarData> getDate(Intent intent, Context context) {

			LinkedList<BarData> chartData = new LinkedList<BarData>();
			// ��ǩ��Ӧ���������ݼ�
			List<Double> dataSeriesA = new LinkedList<Double>();
			// ������ֵȷ����Ӧ��������ɫ.
			List<Integer> dataColorA = new LinkedList<Integer>();

			Cursor cursor;
			int v;

			for (int i = 0; i < charts.size(); i++) {
				v = 0;
				cursor = MainActivity.taintInfo.select_package_name_Taint(
						package_name, PowerService.taint[PowerService
								.get_index_title(charts.get(i).getName())]);
				try {
					while (cursor.moveToNext()) {
						v++;
					}
				} catch (Exception e) {
					e.printStackTrace();
				}finally{
					if (cursor != null) {
						cursor.close();
					}
				}
				dataSeriesA.add((double) v);
				if (v <= 25d) // ����
				{
					dataColorA.add(Color.rgb(77, 184, 73));
				} else if (v <= 50d) { // ����
					dataColorA.add(Color.rgb(252, 210, 9));
				} else if (v <= 100d) { // ƫ��
					dataColorA.add(Color.rgb(171, 42, 96));
				} else { // ����
					dataColorA.add(Color.RED);
				}
			}
			// �˵ص���ɫΪKeyֵ��ɫ�����ε�Ĭ����ɫ
			BarData BarDataA = new BarData("", dataSeriesA, dataColorA,
					Color.rgb(53, 169, 239));

			chartData.add(BarDataA);
			return chartData;

		}

		public static List<CustomLineData> getCustomLineDatas(Intent intent) {
			
			LinkedList<CustomLineData> mCustomLineDataset = new LinkedList<CustomLineData>();
			
			return mCustomLineDataset;

		}
	}

}
