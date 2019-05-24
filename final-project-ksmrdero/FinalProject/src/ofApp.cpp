#include "ofApp.h"

//--------------------------------------------------------------
void ofApp::setup() {
	ofBackground(0, 0, 0);
	video_grabber.setVerbose(true);
	video_grabber.setup(kVideoWidth, kVideoWidth); //1024,768 320, 240

	face_detect.setup("haarcascade_frontalface.xml");
	eye_detect.setup("haarcascade_eyes.xml");
	nose_detect.setup("nose.xml");

	phone.load("phone.png");
	camera.load("camera.png");
	camera.resize(kCameraWidth, kCameraWidth);

	feature_img_data.insert({ "h1", Hat("hat.png") });
	feature_img_data.insert({ "h2", Hat("hat2.png") });
	feature_img_data.insert({ "h3", Hat("hat3.png") });
	feature_img_data.insert({ "g1", Glasses("glasses.png") });
	feature_img_data.insert({ "g2", Glasses("glasses2.png") });
	feature_img_data.insert({ "m1", Mustache("mustache.png") });
	feature_img_data.insert({ "m2", Mustache("mustache2.png") });

	options.Setup();
}

//--------------------------------------------------------------
void ofApp::update() {
	// If a screen shot is taken, there is no need to update the image.
	if (show_screen_shot) {
		return;
	}

	video_grabber.update();
	main_img.setFromPixels(video_grabber.getPixels());
	gray_img = main_img;

	// Finding the haar cascade for the current image.
	face_detect.findHaarObjects(gray_img);
	eye_detect.findHaarObjects(gray_img);
	nose_detect.findHaarObjects(gray_img);

	// If a face is detected and the current feature is a hat, then update that image.
	if (face_detect.blobs.size() > 0 && curr_feature[0] == 'h') {
		ofRectangle face_rect = face_detect.blobs[0].boundingRect;
		Hat *hat = (Hat*)&feature_img_data[curr_feature];
		hat->UpdateHat(face_rect);
	}

	// If 2 eyes are detected and the current feature are glasses, then update that image.
	if (eye_detect.blobs.size() > 1 && curr_feature[0] == 'g') {
		ofRectangle eye1 = eye_detect.blobs[0].boundingRect;
		ofRectangle eye2 = eye_detect.blobs[1].boundingRect;
		Glasses *glasses = (Glasses*)&feature_img_data[curr_feature];
		glasses->UpdateGlasses(eye1, eye2);
	}
	
	// If a nose is detected and the current feature is a mustache, then update that image.
	if (nose_detect.blobs.size() > 0 && curr_feature[0] == 'm') {
		ofRectangle nose_rect = nose_detect.blobs[0].boundingRect;
		Mustache *mustache = (Mustache*)&feature_img_data[curr_feature];
		mustache->UpdateMustache(nose_rect);
	}
}

//--------------------------------------------------------------
void ofApp::draw() {
	if (show_screen_shot) {
		screen_shot.draw(0, 0);
		return;
	}

	phone.draw(0, 0);
	main_img.draw(kStartVideoXCoord, kStartVideoYCoord);
	camera.draw(185, 280);

	if (is_animating) {
		options.DrawAnimation(change_x);
	} else {
		options.Draw();
	}
	
	// If a possible face is detected, show the most prominent face, located
	// at index 0 of the blob rectangle array. 
	if (face_detect.blobs.size() > 0) {
		
		// If the current feature is a hat, then draw that image. 
		if (curr_feature[0] == 'h') {
			feature_img_data[curr_feature].DrawImage();
		}
		if (show_outline) {
			face_detect.blobs[0].draw(kStartVideoXCoord, kStartVideoYCoord);
		}	
	}

	// If there are 2 possible eyes, the 2 most prominent outlines can be shown. 
	if (eye_detect.blobs.size() > 1) {
		if (curr_feature[0] == 'g') {
			feature_img_data[curr_feature].DrawImage();
		}
		if (show_outline) {
			eye_detect.blobs[0].draw(kStartVideoXCoord, kStartVideoYCoord);
			eye_detect.blobs[1].draw(kStartVideoXCoord, kStartVideoYCoord);
		}
	}

	// If there is a nose detected, then an outline for a nose can be shown.
	if (nose_detect.blobs.size() > 0) {
		if (curr_feature[0] == 'm') {
			feature_img_data[curr_feature].DrawImage();
		}
		if (show_outline) {
			nose_detect.blobs[0].draw(kStartVideoXCoord, kStartVideoYCoord);
		}
	}
}

//--------------------------------------------------------------
void ofApp::keyPressed(int key) {
	if (key == 's') {
		show_outline = !show_outline;
	}

	// A space takes a screenshot for the current image.
	if (key == ' ') {
		show_screen_shot = !show_screen_shot;
		if (show_screen_shot) {
			screen_shot.grabScreen(0, 0, kPhoneImgWidth, kPhoneImgLength);
		}
	}
}

//--------------------------------------------------------------
void ofApp::keyReleased(int key) {

}

//--------------------------------------------------------------
void ofApp::mouseMoved(int x, int y) {

}

//--------------------------------------------------------------
void ofApp::mouseDragged(int x, int y, int button) {
	if (button == 0) {
		int move_left_threshold = 30;
		int move_right_threshold = 15;
		change_x = x - start_mouse;

		// If mouse drags to the right, then move the options to the left. 
		// Otherwise, do the opposite.
		if ((change_x % kUserOptionButtonLength) > move_left_threshold && x > start_mouse) {
			options.MoveLeft();
		} else if ((change_x % kUserOptionButtonLength) < move_right_threshold 
			&& x < start_mouse) {
			options.MoveRight();
		}
		is_animating = true;
	}
}

//--------------------------------------------------------------
void ofApp::mousePressed(int x, int y, int button) {
	if (button == 0) {
		start_mouse = x;

		// Coordinates correspond to the 's' key on the Blackberry.
		if (x >= kSKeyLeftXCoordinate && x <= kSKeyRightXCoordinate 
			&& y >= kSKeyLeftYCoordinate && y <= kSKeyRightYCoordinate) {
			show_outline = !show_outline;
		}

		// Coordinates correspond to the space key on the Blackberry.
		if (x >= kSpaceKeyLeftXCoordinate && x <= kSpaceKeyRightXCoordinate 
			&& y >= kSpaceKeyLeftYCoordinate && y <= kSpaceKeyRightYCoordinate) {
			show_screen_shot = !show_screen_shot;
			if (show_screen_shot) {
				screen_shot.grabScreen(0, 0, kPhoneImgWidth, kPhoneImgLength);
			}

		// Location of the camera in the screen.
		} else if (x >= kCameraButtonLeftXCoordinate && x <= kCameraButtonRightXCoordinate 
			&& y >= kCameraButtonLeftYCoordinate && y <= kCameraButtonRightYCoordinate) {
			show_screen_shot = true;
			screen_shot.grabScreen(0, 0, kPhoneImgWidth, kPhoneImgLength);
		}
	}
}

//--------------------------------------------------------------
void ofApp::mouseReleased(int x, int y, int button) {
	is_animating = false;
	curr_feature = options.GetCurrentFeature();
	feature_img_data[curr_feature].ShowFeature();
}

//--------------------------------------------------------------
void ofApp::mouseEntered(int x, int y) {

}

//--------------------------------------------------------------
void ofApp::mouseExited(int x, int y) {

}

//--------------------------------------------------------------
void ofApp::windowResized(int w, int h) {

}

//--------------------------------------------------------------
void ofApp::gotMessage(ofMessage msg) {

}

//--------------------------------------------------------------
void ofApp::dragEvent(ofDragInfo dragInfo) {

}
