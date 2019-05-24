#pragma once
#include "ofGraphics.h"
#include "ofxOpenCv.h"
#include "constants.h"
#include <string>

using std::string;

class Features {
	public:
		/**
		 * Loads an image to the class.
		 * @param filename The filename contianing the image.
		 */
		void LoadImage(string filename);
		
		/**
		 * Draws the image onto the app.
		 */
		void DrawImage();
		
		/**
		 * Changes the visibility of the image.
		 */
		void ChangeVisibility();
		
		/**
		 * Gets the visibility of the image.
		 * @return Whether the image is visible or not.
		 */
		
		bool GetVisibility();

		/**
		 * Shows the feature on the screen.
		 */
		void ShowFeature();

		/**
		 * Hides the feature on the screen.
		 */
		void HideFeature();
		
		/**
		 * Grabs the x coordinate of the feature.
		 */
		int GetXCoord();

		/**
		 * Grabs the y coordinate of the feature.
		 */
		int GetYCoord();

	protected:
		ofImage img;
		int x_coord;
		int y_coord;
		bool is_visible;
};