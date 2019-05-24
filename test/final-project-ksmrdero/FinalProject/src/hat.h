#pragma once
#include "features.h"

class Hat : public Features {
	public:
		Hat();
		Hat(string filename);

		/**
		 * Updates the coordinates of the hat image.
		 * @param bounds The rectangle determined by the haar cascade.
		 */
		void UpdateHat(ofRectangle bounds);
};