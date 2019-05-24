#include "image_classifier.h"
#include "image_trainer.h"

using std::ofstream;
using std::cin;

int main() {
	int num_labels = 10;
	int test_line_count = 28;
	ifstream input_data("digitdata/trainingimages");
	ifstream input_values("digitdata/traininglabels");
	ImageTrainer trainer(num_labels, test_line_count);

	// Reads in the training images and adds them to the training data vectors.
	while (!input_values.eof()) {
		int num;
		input_data >> trainer;
		input_values >> num;
		trainer.AddData(num, trainer.GetInput());
		trainer.ClearInput();
	}

	// Calculates values for the training data. 
	double smooth_const = 0.2;
	trainer.CalculatePixelProbability(smooth_const);
	trainer.CalculateClassProbability();
	trainer.WriteModel("model");

	string test_data;
	string test_labels;
	cout << "Please enter a file name containing testing data: ";
	cin >> test_data;
	cout << "Please enter a file name containing test labels: ";
	cin >> test_labels;

	// Sets up a new classfier to read in the model outputted by ImageTrainer
	// and outputs the labels to a new file.
	ifstream input_test(test_data);
	ofstream test_output("answers");
	ImageClassifier classifier(num_labels, test_line_count);
	classifier.ReadModel("model");

	// Goes through the classifier to determine the label of each test image.
	while (!input_test.eof()) {
		input_test >> classifier;
		unsigned max_num = classifier.DetermineLabel();
		test_output << max_num << endl;
		classifier.ClearInput();
	}

	// Finds the performance of the classifer and outputs it to terminal.
	double performance = classifier.FindPerformance(test_labels, "answers");
	cout << "Total performance: " << performance << endl;

	return 0;
}
