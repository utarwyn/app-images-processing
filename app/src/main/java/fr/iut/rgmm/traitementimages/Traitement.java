package fr.iut.rgmm.traitementimages;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Matrix;
import android.os.AsyncTask;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Représente un traitement d'image. Chaque traitement est lancé en
 * asynchrone et peut effectuer une tâche différente en parralèle.
 * C'est la classe la plus importante de l'application.
 *
 * @author Maxime MALGORN, Robin GEHAN.
 * @version 1.0.0
 */
public abstract class Traitement extends AsyncTask<Void, Bitmap, Bitmap> {

	private ImageView view;

	private MainActivity activity;

	private Bitmap bitmap;

	Traitement(ImageView view, MainActivity activity) {
		this.view = view;
		this.activity = activity;
	}

	@Override
	protected void onPreExecute() {
		if (this.bitmap != null) {
			this.bitmap.recycle();
			this.bitmap = null;
		}

		this.activity.getStatus().setText(R.string.traitement_cours);

		Bitmap orig = BitmapFactory.decodeResource(activity.getResources(), activity.getRessourceCourrante());
		this.bitmap = getResizedBitmap(orig.copy(orig.getConfig(), true), this.view);
	}

	@Override
	protected Bitmap doInBackground(Void... voids) {
		int imgWidth = this.bitmap.getWidth();
		int imgHeight = this.bitmap.getHeight();
		int pixels[] = new int[imgWidth * imgHeight];

		this.bitmap.getPixels(pixels, 0, imgWidth, 0, 0, imgWidth, imgHeight);
		this.modifyImage(pixels, imgWidth, imgHeight);
		this.bitmap.setPixels(pixels, 0, imgWidth, 0, 0, imgWidth, imgHeight);
		return this.bitmap;
	}

	@Override
	protected void onProgressUpdate(Bitmap... values) {
		this.view.setImageBitmap(values[0]);
	}

	@Override
	protected void onPostExecute(Bitmap bitmap) {
		this.activity.getStatus().setText(R.string.traitement_termine);
		this.view.setImageBitmap(bitmap);
	}

	public abstract int modifyPixel(int pixel, int line, int col);

	private void modifyImage(int[] pixels, int width, int height) {
		for (int line = 0; line < height; line++) {
			for (int col = 0; col < width; col++) {
				int index = col + width * line;

				pixels[index] = this.modifyPixel(pixels[index], line, col);
			}

			if (line % 3 == 0) {
				this.bitmap.setPixels(pixels, 0, width, 0, 0, width, height);
				this.publishProgress(this.bitmap);
			}
		}
	}

	private Bitmap getResizedBitmap(Bitmap bm, View view) {
		final int actualHeight, actualWidth;
		final int imageViewHeight = view.getHeight(), imageViewWidth = view.getWidth();
		final int bitmapHeight = bm.getHeight(), bitmapWidth = bm.getWidth();

		if (imageViewHeight * bitmapWidth <= imageViewWidth * bitmapHeight) {
			actualWidth = bitmapWidth * imageViewHeight / bitmapHeight;
			actualHeight = imageViewHeight;
		} else {
			actualHeight = bitmapHeight * imageViewWidth / bitmapWidth;
			actualWidth = imageViewWidth;
		}

		Bitmap resizedBitmap = Bitmap.createScaledBitmap(bm, actualWidth, actualHeight, true);
		bm.recycle();

		return resizedBitmap;
	}

}
