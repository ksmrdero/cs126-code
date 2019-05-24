#include "glasses.h"

Glasses::Glasses() {

}

Glasses::Glasses(string filename) {
	img.load(filename);
}

void Glasses::UpdateGlasses(ofRectangle first_bound, ofRectangle second_bound) {
  if (is_visible) {
		float max_x = max(first_bound.getRight(), second_bound.getRight());
		float min_x = min(first_bound.getLeft(), second_bound.getLeft());
		float new_width = max_x - min_x;

		// Resizes the current image to account for the adjusted width of
		// each different scenario.
		img.resize(new_width, first_bound.getHeight());
		x_coord = min_x + kStartVideoXCoord;
		y_coord = first_bound.getMinY() + kStartVideoYCoord;
	}
}