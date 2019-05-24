#pragma once
#include "features.h"

class Glasses : public Features {
	public:
		Glasses();
		Glasses(string filename);

		/**
		 * Updates the coordinates of the glasses.
		 * @param first_bound  The first rectangle determined by the haar cascade.
		 * @param second_bound The second rectangle determined by the haar cascade.
		 */
		void UpdateGlasses(ofRectangle first_bound, ofRectangle second_bound);
}; 
