package com.isotopeneo.it_ebooks_beta.fragment;

import android.app.Fragment;
import android.app.LoaderManager;
import android.content.Intent;
import android.content.Loader;
import android.net.Uri;
import android.net.Uri.Builder;
import android.os.Bundle;
import android.text.Html;
import android.text.SpannableStringBuilder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.isotopeneo.it_ebooks_beta.R;
import com.isotopeneo.it_ebooks_beta.application.IT_Ebooks_Beta_Application;
import com.isotopeneo.it_ebooks_beta.bean.Book;
import com.isotopeneo.it_ebooks_beta.loader.SelectedBookLoader;
import com.isotopeneo.it_ebooks_beta.util.LoggerClass;

public class SelectedBookFragment extends Fragment implements LoaderManager.LoaderCallbacks<Book>{
	
	private IT_Ebooks_Beta_Application application;
	private View view;
	private ImageView bookCoverImage;
	private TextView bookDetails;
	private Button bookDownload;
	private String downloadURL;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		this.application = (IT_Ebooks_Beta_Application) getActivity().getApplication();
		view = inflater.inflate(R.layout.selected_book_details, container);
		initView();
		return view;
	}
	
	public void initView() {
		bookCoverImage = (ImageView) view.findViewById(R.id.bookCoverImage);
		bookDetails = (TextView) view.findViewById(R.id.bookDetails);
		bookDownload = (Button) view.findViewById(R.id.bookDownload);
		bookDownload.setOnClickListener(getOnClickListener());
		// Start the loader
		inititateLoader();
	}

	public OnClickListener getOnClickListener() {
		return new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				openWebPage();
			}
		};
	}
	
	public void openWebPage() {
		Uri webpage = Uri.parse(downloadURL);
		Intent intent = new Intent(Intent.ACTION_VIEW, webpage);
		if (intent.resolveActivity(getActivity().getPackageManager()) != null) {
			startActivity(intent);
		}
	}

	public void inititateLoader() {
		if (application.isDeviceConnectedToNetwork()) {
			getLoaderManager().initLoader(0, null, this).forceLoad();
		} else {
			application.dismissProgressBar();
			application.showAlertDialog("Please check your internet connection", getActivity());
			//getActivity().finish();
		}
	}
	
	@Override
	public Loader<Book> onCreateLoader(int id, Bundle args) {
		application.showProgressBar("Fetching data", getActivity());
		return new SelectedBookLoader(getActivity().getApplication());
	}

	@Override
	public void onLoadFinished(Loader<Book> loader, Book data) {
		application.dismissProgressBar();
		updateView(data);
		LoggerClass.log("Download URL: " + new Builder().query(data.getDownloadURL()).build().toString());
		downloadURL = data.getDownloadURL();
	}

	@Override
	public void onLoaderReset(Loader<Book> loader) {
	}

	public void updateView(Book selectedBook) {
		getActivity().getActionBar().setTitle(selectedBook.getTitle());
		bookCoverImage.setImageBitmap(application.getImageCache().get(selectedBook.getId()));
		bookDetails.setText(computeText(selectedBook));
	}
	
	public SpannableStringBuilder computeText(Book selectedBook) {
		SpannableStringBuilder output = new SpannableStringBuilder();
		output.append(Html.fromHtml("<b>Title: </b>"));
		output.append(selectedBook.getTitle());
		output.append("\n\n");
		output.append(Html.fromHtml("<b>Description: </b>"));
		output.append(selectedBook.getDescription());
		output.append("\n\n");
		output.append(Html.fromHtml("<b>Author(s): </b>"));
		output.append(selectedBook.getAuthors());
		output.append("\n\n");
		output.append(Html.fromHtml("<b>ISBN: </b>"));
		output.append(selectedBook.getIsbn());
		output.append("\n\n");
		output.append(Html.fromHtml("<b>Number of Pages: </b>"));
		output.append(selectedBook.getNumberOfPages());
		output.append("\n\n");
		output.append(Html.fromHtml("<b>Publication year: </b>"));
		output.append(selectedBook.getPublicationDate());
		output.append("\n\n");
		output.append(Html.fromHtml("<b>Publisher: </b>"));
		output.append(selectedBook.getPublisher());
		output.append("\n");
		return output;
	}
}
