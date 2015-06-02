package com.isotopeneo.it_ebooks_beta.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.isotopeneo.it_ebooks_beta.R;
import com.isotopeneo.it_ebooks_beta.activity.SearchResultsActivity;
import com.isotopeneo.it_ebooks_beta.application.IT_Ebooks_Beta_Application;
import com.isotopeneo.it_ebooks_beta.requestexecutor.BitmapRequestExecutor;

public class SearchResultsAdapter extends BaseAdapter {

	private LayoutInflater layoutInflater;
	private IT_Ebooks_Beta_Application application;
	private Activity activity;

	public SearchResultsAdapter(Activity activity) {
		this.activity = activity;
		layoutInflater = LayoutInflater.from(activity);
		application = (IT_Ebooks_Beta_Application) activity.getApplication();
	}

	@Override
	public int getCount() {
		return application.getSearchResults().size();
	}

	@Override
	public Object getItem(int position) {
		return application.getSearchResults().get(position);
	}

	@Override
	public long getItemId(int position) {
		return 0;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		ViewHolder viewholder;
		if (null == convertView) {
			// Maybe will have to use the second type of inflate() method
			convertView = layoutInflater.inflate(R.layout.row_item_search_result, parent, false);
			viewholder = new ViewHolder();
			viewholder.setBookCoverImage((ImageView) convertView.findViewById(R.id.bookCover));
			viewholder.setBookTitle((TextView) convertView.findViewById(R.id.bookTitle));
			viewholder.setBookSubTitle((TextView) convertView.findViewById(R.id.bookSubTitle));
			convertView.setTag(viewholder);
		} else {
			viewholder = (ViewHolder) convertView.getTag();
		}
		viewholder.getBookTitle().setText(application.getSearchResults().get(position).getTitle());
		viewholder.getBookSubTitle().setText(application.getSearchResults().get(position).getSubTitle());
		//viewholder.getBookCoverImage().setImageBitmap(application.getImageCache().get(application.getSearchResults().get(position).getId()));
		viewholder.getBookCoverImage().setTag(application.getSearchResults().get(position).getId());
		new BitmapRequestExecutor().downloadAsync(viewholder.getBookCoverImage(), application.getSearchResults().get(position).getImageURL(), application, application.getSearchResults().get(position).getId());
		convertView.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				application.setSelectedBookID(application.getSearchResults().get(position).getId());
				((SearchResultsActivity) activity).invokeSelectedBookActivity();
			}
		});
		return convertView;
	}
	
	/**
	 * 
	 * @author Jayesh
	 * Inner class to hold the Views
	 */
	public static class ViewHolder {
		private ImageView bookCoverImage;
		private TextView bookTitle;
		private TextView bookSubTitle;

		public ImageView getBookCoverImage() {
			return bookCoverImage;
		}
		public void setBookCoverImage(ImageView bookCoverImage) {
			this.bookCoverImage = bookCoverImage;
		}
		public TextView getBookTitle() {
			return bookTitle;
		}
		public void setBookTitle(TextView bookTitle) {
			this.bookTitle = bookTitle;
		}
		public TextView getBookSubTitle() {
			return bookSubTitle;
		}
		public void setBookSubTitle(TextView bookSubTitle) {
			this.bookSubTitle = bookSubTitle;
		}
	}

}
