#include "mustache.h"

Mustache::Mustache() {

}

Mustache::Mustache(string filename) {
	img.load(filename);
}

void Mustache::UpdateMustache(ofRectangle bounds) {
	if (is_visible) {
		float aspect_ratio = img.getHeight() / img.getWidth();

		// Resizes the current image to account for the adjusted width of
		// each different scenario.
		img.resize(bounds.getWidth(), bounds.getWidth() * aspect_ratio);
		x_coord = bounds.getMinX() + kStartVideoXCoord;
		y_coord = bounds.getMaxY() - img.getHeight() + kStartVideoYCoord;
	}
}