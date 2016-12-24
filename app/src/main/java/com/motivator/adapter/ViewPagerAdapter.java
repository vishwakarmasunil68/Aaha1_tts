package com.motivator.adapter;

import com.motivator.wecareyou.R;
import com.motivator.wecareyou.R.id;
import com.motivator.wecareyou.R.layout;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.RelativeLayout;


public class ViewPagerAdapter extends PagerAdapter {
	Context context;
	LayoutInflater inflater;
	int[] background;


	public ViewPagerAdapter(Context context,int[] background) {
		this.context = context;
		this.background = background;
	}


	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return background.length;
	}

	@Override
	public boolean isViewFromObject(View view, Object object) {
        return view == ((RelativeLayout) object);

    }

	@Override
	public Object instantiateItem(ViewGroup container, final int position) {
		// Declare Variables
		ImageView imgNewsImage;

		inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View itemView = inflater.inflate(R.layout.viewpager_adapter, container,
				false);

		imgNewsImage = (ImageView) itemView.findViewById(R.id.imgNewsImage);
		imgNewsImage.setScaleType(ScaleType.FIT_XY);
		
		itemView.setBackgroundResource(background[position]);
		
		/*imgNewsImage.setTag(bannerNews.get(position));

		TextView txtTitleBannerNews = (TextView) itemView
				.findViewById(R.id.txtTitleBannerNews);
		txtTitleBannerNews.setText(bannerNews.get(position));*/

		
		// Add viewpager_item.xml to ViewPager
		((ViewPager) container).addView(itemView);

		return itemView;
	}

	@Override
	public void destroyItem(ViewGroup container, int position, Object object) {
		// Remove viewpager_item.xml from ViewPager
		((ViewPager) container).removeView((RelativeLayout) object);

	}

}
