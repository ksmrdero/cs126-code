#pragma once

#include <algorithm>
#include <vector>
#include <iostream>
#include <cmath> 
#include <map>
#include "ofMain.h"
#include "features.h"
#include "hat.h"
#include "glasses.h"
#include "mustache.h"
#include "user_options.h"
#include "ofxOpenCv.h"
#include "ofGraphics.h"
#include "constants.h"

using std::min;
using std::max;
using std::abs;
using std::map;

class ofApp : public ofBaseApp{

	public:
		void setup();
		void update();
		void draw();
		void keyPressed(int key);
		void keyReleased(int key);
		void mouseMoved(int x, int y );
		void mouseDragged(int x, int y, int button);
		void mousePressed(int x, int y, int button);
		void mouseReleased(int x, int y, int button);
		void mouseEntered(int x, int y);
		void mouseExited(int x, int y);
		void windowResized(int w, int h);
		void dragEvent(ofDragInfo dragInfo);
		void gotMessage(ofMessage msg);

	private:
		ofImage phone;
		ofImage camera;
		ofVideoGrabber video_grabber;
		ofxCvHaarFinder face_detect;
		ofxCvHaarFinder eye_detect;
		ofxCvHaarFinder nose_detect;
		ofxCvColorImage main_img;
		ofxCvGrayscaleImage gray_img;
		bool show_outline;

		// Used for taking a screen shot
		ofImage screen_shot;
		bool show_screen_shot;

		// User option variables
		UserOptions options;
		int start_mouse;
		bool is_animating;
		int change_x;

		// Map containing all the filters
		map<string, Features> feature_img_data;
		string curr_feature;
};


