package fr.iut.rgmm.traitementimages;

import android.widget.ImageView;

/**
 * Représente un traitement qui rend les pixels de l'image
 * plus jaune que la normale.
 * @author Maxime MALGORN, Robin GEHAN.
 * @version 1.0.0
 */
public class TraitementJaune extends Traitement {

	TraitementJaune(ImageView view, MainActivity activity) {
		super(view, activity);
	}

	@Override
	public int modifyPixel(int pixel, int line, int col) {
		return pixel ^ 0x00FFFF00;
	}

}
