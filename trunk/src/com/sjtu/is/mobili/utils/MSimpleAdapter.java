package com.sjtu.is.mobili.utils;
import java.util.List;
import java.util.Map;

import android.content.Context;
import android.util.Log;
import android.widget.ImageView;
import android.widget.SimpleAdapter;

	public class MSimpleAdapter extends SimpleAdapter
	{

		public MSimpleAdapter(Context context,
				List<? extends Map<String, ?>> data, int resource,
				String[] from, int[] to) 
		{
			super(context, data, resource, from, to);
			// TODO Auto-generated constructor stub
		}
		@Override
		public void setViewImage(ImageView v, String value) {
			Log.d("imageDownd", value);
			DrawableManager dm = new DrawableManager();
			dm.fetchDrawableOnThread(value, v);
		}    
	}
