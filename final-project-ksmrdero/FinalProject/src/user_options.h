#pragma once
#include "ofGraphics.h"
#include "ofxOpenCv.h"
#include "constants.h"
#include <vector>
#include <string>

using std::string;
using std::vector;

class UserOptions {
	public:
		/**
		 * Loads the images onto the options vector and their respective 
		 * labels onto the option_label vector.
		 */
		void Setup();

		/**
		 * Helper function for setup that can read in many images in a row.
		 * @param feature The filename of the image.
		 * @param num     The number of files to read.
		 */
		void LoadFeatureImages(string feature, int num);

		/**
		 * Moves the current visible features one spot to the right.
		 */
		void MoveRight();

		/**
		 * Moves the current visible features one spot to the left.
		 */
		void MoveLeft();

		/**
		 * Draws a still image of the 5 closest options from the current 
		 * index (the 2 before, and 2 after the current indexed option).
		 */
		void Draw(); 

		/**
		 * Draws the animation of the 5 current options on the screen.
		 * @param change_x The change to move the option images.
		 */
		void DrawAnimation(int change_x);

		/**
		 * Gets the label current option.
		 * @return The string of the current feature.
		 */
		string GetCurrentFeature();

	private:
		vector<ofImage> options;
		vector<string> option_labels;
		int curr_index;
};