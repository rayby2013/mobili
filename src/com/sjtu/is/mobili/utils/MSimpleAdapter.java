package com.sjtu.is.mobili.utils;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.Map;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
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
		public void setViewImage(ImageView v, String value) {   
		    new ImageDownloadTask().execute(value, v);   
		}   
		private class ImageDownloadTask extends AsyncTask<Object, Object, Bitmap>{   
		        private ImageView imageView = null;   
		        protected Bitmap doInBackground(Object... params) {   
		            // TODO Auto-generated method stub   
		            Bitmap bmp = null;   
		            imageView = (ImageView) params[1];   
		            try {   
		                bmp = BitmapFactory.decodeStream(new URL((String)params[0]).openStream());   
		            } catch (MalformedURLException e) {   
		                // TODO Auto-generated catch block   
		                e.printStackTrace();   
		            } catch (IOException e) {   
		                // TODO Auto-generated catch block   
		                e.printStackTrace();   
		            }   
		            return bmp;   
		        }   
		           
		        protected void onPostExecute(Bitmap result){   
		            imageView.setImageBitmap(result);   
		        }   
		    } 
	}
