package fr.iut.rgmm.traitementimages;

import android.widget.ImageView;

/**
 * Représente un traitement qui inverse la couleur de chaque pixel
 * afin de rendre une image négative à l'originale.
 * @author Maxime MALGORN, Robin GEHAN.
 * @version 1.0.0
 */
public class TraitementNegatif extends Traitement {

	TraitementNegatif(ImageView view, MainActivity activity) {
		super(view, activity);
	}

	@Override
	public int modifyPixel(int pixel, int line, int col) {
		return pixel ^ 0x00FFFFFF;
	}

}
