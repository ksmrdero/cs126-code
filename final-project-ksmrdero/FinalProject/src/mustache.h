#pragma once
#include "features.h"

class Mustache : public Features {
	public:
		Mustache();
		Mustache(string filename);

		/**
		* Updates the coordinates of the hat image.
		* @param bounds The rectangle determined by the haar cascade.
		*/
		void UpdateMustache(ofRectangle bounds);

};