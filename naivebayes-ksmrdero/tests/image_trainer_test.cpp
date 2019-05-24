#define CATCH_CONFIG_MAIN
#include "../catch/catch.hpp"
#include "../image_classifier.h"
#include "../image_trainer.h"

TEST_CASE("Image trainer reads in the first input from file correctly", 
	"[ReadFile]") {
	ImageTrainer i(10, 28);
	ifstream input_file("digitdata/trainingimages");
	input_file >> i;
	REQUIRE(i.GetInput() == "                                                                                                                                                            +++++##+            +++++######+###+           +##########+++++             #######+##                  +++###  ++                     +#+                         +#+                          +#+                         +##++                        +###++                       ++##++                        +##+                         ###+                     +++###                    ++#####+                  ++######+                 ++######+                  +######+                 ++######+                   +####++                                                                                                     ");
}

TEST_CASE("Input variable is cleared when ClearInput is called", 
	"[ClearInput]") {
	ImageTrainer image(10, 28);
	ifstream input_file("digitdata/trainingimages");
	input_file >> image;
	image.ClearInput();
	REQUIRE(image.GetInput().size() == 0); 
}

TEST_CASE("Image trainer reads in a middle input from file correctly", 
	"[ReadFile]") {
	ImageTrainer image(10, 28);
	ifstream input_file("digitdata/trainingimages");
	for (int i = 0; i < 340; i++) {
		input_file >> image;
		image.ClearInput();
	}
	input_file >> image;
	REQUIRE(image.GetInput() == "                                                                                                                                                                                     ++##                       +####                       #####                      +#####+                    +### ###                   +++#+ +##                   +##++ +##+                 +##+    +#+++               ###+    +##+               +##+     +##+              +###+      ###              ++++       ###+                       +###+                      +++#+                       +# #                       +##++                       +##                        ++#+                        +#+                         +#                                                                     ");
}

TEST_CASE("Image trainer reads in last input from file correctly", 
	"[ReadFile]") {
	ImageTrainer image(10, 28);
	ifstream input_file("digitdata/trainingimages");
	for (int i = 0; i < 4999; i++) {
		input_file >> image;
		image.ClearInput();
	}
	input_file >> image;
	REQUIRE(image.GetInput() == "                                                                                                                           +#+++                       +#####+                    +#######+                   +#+ +++##+                  ##     ##+                 +#+     +#+                 +##+    +#+                  ++    +##                        +##+                       ++##+                      ++##+                       +##+                  +#++++##++                  +######+                    +#######+                   +########+                   +##+  ####+                        +###++                       +####+                        +###+                                                                                                                     ");
}

TEST_CASE("New data is added to the training data vector correctly", 
	"[ReadFile]") {
	ImageTrainer i(10, 28);
	ifstream input_data("digitdata/trainingimages");
	ifstream input_values("digitdata/traininglabels");
	for (int k = 0; k < 5; k++) {
		int num;
		input_data >> i;
		input_values >> num;
		i.AddData(num, i.GetInput());
		i.ClearInput();
	}
	
	// Converting the vector to a string for easier comparison.
	string s;
	vector<vector<double>> training_data = i.GetTrainingData();
	for (unsigned k = 0; k < training_data[5].size(); k++) {
		std::stringstream ss;
		ss << training_data[5][k];
		string temp = ss.str();
		s.append(temp);
	}
	REQUIRE(s == "0000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000.750.750.750.750.75110.750000000000000.750.750.750.750.751111110.751110.75000000000000.7511111111110.750.750.750.750.75000000000000011111110.75110000000000000000000.750.750.75111000.750.750000000000000000000000.7510.7500000000000000000000000000.7510.75000000000000000000000000000.7510.7500000000000000000000000000.75110.750.750000000000000000000000000.751110.750.75000000000000000000000000.750.75110.750.750000000000000000000000000.75110.7500000000000000000000000001110.750000000000000000000000.750.750.75111000000000000000000000.750.75111110.750000000000000000000.750.751111110.75000000000000000000.750.751111110.750000000000000000000.751111110.75000000000000000000.750.751111110.7500000000000000000000.7511110.750.7500000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000");
}

TEST_CASE("Small test for adding new data to the training vector", "[Data]") {
	ImageTrainer image(10, 2);
	image.AddData(0, "  ++");
	vector<vector<double>> v(10, vector<double>(4, 0));
	v[0][2] = 0.75;
	v[0][3] = 0.75;
	REQUIRE(image.GetTrainingData() == v);
}

TEST_CASE("New data is added to the training vector correctly multiple times", 
	"[Data]") {
	ImageTrainer image(10, 2);
	image.AddData(0, "  ++");
	image.AddData(0, "  #+");
	image.AddData(0, "  ##");
	image.AddData(1, "####");
	image.AddData(2, "+++ ");
	vector<vector<double>> v(10, vector<double>(4, 0));
	v[0][2] = 2.75;
	v[0][3] = 2.5;
	for (int i = 0; i < 4; i++) {
		v[1][i] = 1;
	}
	for (int i = 0; i < 3; i++) {
		v[2][i] = 0.75;
	}
	REQUIRE(image.GetTrainingData() == v);
}

TEST_CASE("Training data probability is calculated correctly", "[Data]") {
	ImageTrainer image(10, 2);
	image.AddData(0, "  ++");
	vector<vector<double>> v(10, vector<double>(4, 0.5));
	v[0][0] = (double) 1 / 3;
	v[0][1] = (double) 1 / 3;
	v[0][2] = (double) 7 / 12;
	v[0][3] = (double) 7 / 12;
	image.CalculatePixelProbability(1);
	REQUIRE(image.GetTrainingData() == v);
}

TEST_CASE("Small test for adding data to the class count vector", "[Data]") {
	ImageTrainer image(10, 2);
	image.AddData(0, "  ++");
	vector<double> v(10, 0);
	v[0] = 1;
	REQUIRE(image.GetClassCount() == v);
}

TEST_CASE("Test for adding data to the class count vector multiple times", 
	"[Data]") {
	ImageTrainer image(10, 2);
	image.AddData(0, "  ++");
	image.AddData(0, "  ++");
	image.AddData(1, "##++");
	image.AddData(3, "  ++");
	image.AddData(5, "+#  ");
	image.AddData(7, "  ++");
	image.AddData(1, "    ");
	image.AddData(2, "++++");
	image.AddData(5, "## +");
	vector<double> v(10, 0);
	v[0] = 2;
	v[1] = 2;
	v[2] = 1;
	v[3] = 1;
	v[5] = 2;
	v[7] = 1; 
	REQUIRE(image.GetClassCount() == v);
}

TEST_CASE("Class data probability is calculated correctly", "[Data]") {
	ImageTrainer image(10, 2);
	image.AddData(0, "  ++");
	image.AddData(0, "  ++");
	image.AddData(1, "##++");
	image.AddData(3, "  ++");
	image.AddData(5, "+#  ");
	image.AddData(7, "  ++");
	image.AddData(1, "    ");
	image.AddData(2, "++++");
	image.AddData(5, "## +");
	image.AddData(5, "## +");
	vector<double> v(10, 0);
	v[0] = 0.2;
	v[1] = 0.2;
	v[2] = 0.1;
	v[3] = 0.1;
	v[5] = 0.3;
	v[7] = 0.1; 
	image.CalculateClassProbability();
	REQUIRE(image.GetClassCount() == v);
}