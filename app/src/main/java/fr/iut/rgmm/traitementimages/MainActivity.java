package fr.iut.rgmm.traitementimages;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Random;

/**
 * Activité principale de l'application.
 * @author Maxime MALGORN, Robin GEHAN.
 * @version 1.0.0
 */
public class MainActivity extends AppCompatActivity {

	private final Random RND = new Random();

	private TextView status;

	private ImageView image1, image2;

	private int imgCourrante;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		this.status = this.findViewById(R.id.status);
		this.image1 = this.findViewById(R.id.image1);
		this.image2 = this.findViewById(R.id.image2);

		// Initialisation des deux images
		this.changerImage(null);
		this.setImageCourrante(this.image1);
	}

	@Override
	protected void onSaveInstanceState(Bundle bundle) {
		super.onSaveInstanceState(bundle);

		bundle.putInt("img_courrante", this.imgCourrante);
		bundle.putString("status", this.status.getText().toString());

		bundle.putParcelable("image1", ((BitmapDrawable) this.image1.getDrawable()).getBitmap());
		bundle.putParcelable("image2", ((BitmapDrawable) this.image2.getDrawable()).getBitmap());
	}

	@Override
	protected void onRestoreInstanceState(Bundle bundle) {
		super.onSaveInstanceState(bundle);

		this.imgCourrante = bundle.getInt("img_courrante");
		this.status.setText(bundle.getString("status"));

		// Le traitement de l'image est systématiquement terminé.
		if (status.getText().equals(getString(R.string.traitement_cours)))
			this.status.setText(R.string.traitement_termine);

		this.image1.setImageBitmap((Bitmap) bundle.getParcelable("image1"));
		this.image2.setImageBitmap((Bitmap) bundle.getParcelable("image2"));

	}

	public TextView getStatus() {
		return this.status;
	}

	public int getRessourceCourrante() {
		return getResources().getIdentifier("image_" + this.imgCourrante, "drawable", getPackageName());
	}

	public void traiterImage(View view) {
		this.setImageCourrante(this.image1);
		new TraitementNegatif(this.image1, this).execute();
	}

	public void traiterImage2(View view) {
		this.setImageCourrante(this.image2);
		new TraitementJaune(this.image2, this).execute();
	}

	public void changerImage(View view) {
		int id;

		do {

			id = RND.nextInt(10) + 1;

		} while (id == this.imgCourrante);


		this.imgCourrante = id;
		this.setImageCourrante(this.image2);
	}

	private void setImageCourrante(ImageView imageView) {
		imageView.setImageResource(this.getRessourceCourrante());
	}

}
