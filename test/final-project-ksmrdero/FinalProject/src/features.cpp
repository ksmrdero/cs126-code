#include "features.h"

void Features::LoadImage(string filename) {
	img.load(filename);
}

void Features::DrawImage() {
	if (is_visible) {
		img.draw(x_coord, y_coord);
	}
}

void Features::ChangeVisibility() {
	is_visible = !is_visible;
}

bool Features::GetVisibility() {
	return is_visible;
}

void Features::ShowFeature() {
	is_visible = true;
}

void Features::HideFeature() {
	is_visible = false;
}

int Features::GetXCoord() {
	return x_coord;
}

int Features::GetYCoord() {
	return y_coord;
}