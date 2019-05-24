#define CATCH_CONFIG_MAIN
#include "stdafx.h"
#include "../catch/catch.hpp"
#include "../src/ofApp.h"

TEST_CASE("Visibility of feature is changed when called", "[Features]") {
	Features f;
	bool original = f.GetVisibility();
	f.ChangeVisibility();
	REQUIRE(original != f.GetVisibility());
}

TEST_CASE("Visibility is true when show feature is called", "[Features]") {
	Features f;
	f.ShowFeature();
	REQUIRE(true == f.GetVisibility());
}

TEST_CASE("Hat coordinates are correctly updated", "[Hat]") {
	Hat h("hat.png");
	ofRectangle rect(0, 0, 100, 100);
	h.UpdateHat(rect);

	REQUIRE(91 == h.GetXCoord());
	REQUIRE(-121.5 == h.GetYCoord());
}

TEST_CASE("Glasses coordinates are correctly updated", "[Glasses]") {
	Glasses g("Glasses.png");
	ofRectangle rect1(0, 0, 100, 100);
	ofRectangle rect2(100, 0, 200, 100);
	g.UpdateGlasses(rect1, rect2);

	REQUIRE(91 == h.GetXCoord());
	REQUIRE(92 == h.GetYCoord());
}

TEST_CASE("Mustache coordinates are correctly updated", "[Mustache]") {
	Mustache m("Mustache.png");
	ofRectangle rect(0, 0, 100, 100);
	m.UpdateGlasses(rect);

	REQUIRE(91 == h.GetXCoord());
	REQUIRE(-208 == h.GetYCoord());
}
