#include "hat.h"

Hat::Hat() {

}

Hat::Hat(string filename) {
	img.load(filename);
}

void Hat::UpdateHat(ofRectangle bounds) {
	if (is_visible) {
		float aspect_ratio = img.getHeight() / img.getWidth();

		// Resizes the current image to account for the adjusted width of
		// each different scenario.
		img.resize(bounds.getWidth(), bounds.getWidth() * aspect_ratio);
		x_coord = bounds.getMinX() + kStartVideoXCoord;
		y_coord = bounds.getMinY() - bounds.getWidth() * aspect_ratio + kStartVideoYCoord;
	}
}