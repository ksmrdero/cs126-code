#define CATCH_CONFIG_MAIN
#include "stdafx.h"
#include "../catch/catch.hpp"
#include "../src/ofApp.h"

TEST_CASE("Current location option index starts with no filter", "[Options]") {
	UserOptions o;
	o.Setup();
	REQUIRE("n" == o.GetCurrentFeature());
}

TEST_CASE("Option to the right is the first hat", "[Options]") {
	UserOptions o;
	o.Setup();
	o.MoveRight();
	REQUIRE("h1" == o.GetCurrentFeature());
}

TEST_CASE("Option to the left is the second mustache", "[Options]") {
	UserOptions o;
	o.Setup();
	o.MoveLeft();
	REQUIRE("m2" == o.GetCurrentFeature());
}

TEST_CASE("Multiple move rights work", "[Options]") {
	UserOptions o;
	o.Setup();
	o.MoveRight();
	o.MoveRight();
	o.MoveRight();
	o.MoveRight();
	REQUIRE("g1" == o.GetCurrentFeature());
}

TEST_CASE("Multiple move left work", "[Options]") {
	UserOptions o;
	o.Setup();
	o.MoveLeft();
	o.MoveLeft();
	o.MoveLeft();
	REQUIRE("g2" == o.GetCurrentFeature());
}

TEST_CASE("Moving left from 0th element and moving right from the last element returns back to 0", "[Options]") {
	UserOptions o;
	o.Setup();
	o.MoveLeft();
	o.MoveRight();
	REQUIRE("n" == o.GetCurrentFeature());
}