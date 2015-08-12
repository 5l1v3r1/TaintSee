package com.taintflow;

import com.demo.xclcharts.view.BarChart08View;

import android.app.Activity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;

public class app_detail_chartActivity extends Activity{

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		 FrameLayout content = new FrameLayout(this);    
	       
		   FrameLayout.LayoutParams frameParm = new FrameLayout.LayoutParams(
		   LayoutParams.WRAP_CONTENT,LayoutParams.WRAP_CONTENT);  
		   frameParm.gravity = Gravity.BOTTOM|Gravity.RIGHT;  
		   DisplayMetrics dm = getResources().getDisplayMetrics();		   
		   int scrWidth = (int) (dm.widthPixels ); 	
		   int scrHeight = (int) (dm.heightPixels); 			   		
	       RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(
	    		   													scrWidth, scrHeight);
	       
	       //������ʾ
         layoutParams.addRule(RelativeLayout.CENTER_IN_PARENT);   
         //ͼ��view���벼���У�Ҳ��ֱ�ӽ�ͼ��view����Activity��Ӧ��xml�ļ���
         final RelativeLayout chartLayout = new RelativeLayout(this);  
    
         chartLayout.addView( new BarChart08View(this, getIntent()), layoutParams);

	        //���ӿؼ�
		   ((ViewGroup) content).addView(chartLayout);		   
		   //((ViewGroup) content).addView(mZoomControls);
		    setContentView(content);		   	       
	}
}
