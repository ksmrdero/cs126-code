#include "user_options.h"

void UserOptions::Setup() {
	ofImage camera;
	ofImage hat;
	ofImage stache;
	ofImage glasses;

	int num_hat_img = 3;
	int num_glasses_img = 2;
	int num_mustache_img = 2;

	// First loads an empty option holder to the options vector. This 
	// image has no filter to it. 
	camera.load("camera.png");
	camera.resize(kImageResizeWidth, kImageResizeWidth);
	options.push_back(camera);
	option_labels.push_back("n");

	// Loads in the hats, glasses and mustache images afterwards.
	LoadFeatureImages("h", num_hat_img);
	LoadFeatureImages("g", num_glasses_img);
	LoadFeatureImages("m", num_mustache_img);
}

void UserOptions::LoadFeatureImages(string feature, int num) {
	ofImage img; 

	// Loads and resizes each image, and adds them to the 
	// collection of options.
	for (int i = 1; i <= num; i++) {
		img.load(feature + "_circle" + std::to_string(i) + ".png");
		img.resize(kImageResizeWidth, kImageResizeWidth);
		options.push_back(img);
		option_labels.push_back(feature + std::to_string(i));
	}
}

void UserOptions::MoveRight() {
	curr_index = (curr_index + 1) % options.size();
}

void UserOptions::MoveLeft() {
	curr_index = (curr_index - 1) % options.size();
}

void UserOptions::DrawAnimation(int change_x) {
	int updated_x = kXCenterLocation + (change_x % kImageResizeWidth);

	// Drawing the current option.
	options[curr_index].draw(updated_x, kStartYLocation);

	// Drawing the option to the right of the current option.
	options[(curr_index + 1) % options.size()].draw(updated_x + kImageResizeWidth, kStartYLocation);

	// If the option 2 rights to the current option is within the cutoff threshold, then draw that option.
	if (updated_x + 2 * kImageResizeWidth <= kXLocationRightCutoff - kImageResizeWidth) {
		options[(curr_index + 2) % options.size()].draw(updated_x + 2 * kImageResizeWidth, kStartYLocation);
	}
	
	// Drawing the option to the left of the current option.
	options[(curr_index - 1) % options.size()].draw(updated_x - kImageResizeWidth, kStartYLocation);
	
	// If the option 2 lefts to the current option is within the cutoff threshold, then draw that option.
	if (updated_x - 2 * kImageResizeWidth >= kXLocationLeftCutoff) {
		options[(curr_index - 2) % options.size()].draw(updated_x - 2 * kImageResizeWidth, kStartYLocation);
	}
}

void UserOptions::Draw() {
	// Draws 2 features to the left, and 2 features to the right.
	options[curr_index].draw(kXCenterLocation, kStartYLocation);
	options[(curr_index + 1) % options.size()].draw(kXCenterLocation + kImageResizeWidth, kStartYLocation);
	options[(curr_index + 2) % options.size()].draw(kXCenterLocation + 2 * kImageResizeWidth, kStartYLocation);
	options[(curr_index - 1) % options.size()].draw(kXCenterLocation - kImageResizeWidth, kStartYLocation);
	options[(curr_index - 2) % options.size()].draw(kXCenterLocation - 2 * kImageResizeWidth, kStartYLocation);
}

string UserOptions::GetCurrentFeature() {
	return option_labels[curr_index];
}