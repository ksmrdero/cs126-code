#include "image_classifier.h"

ImageClassifier::ImageClassifier(int label_count, int line_count) {
	num_labels = label_count;
	num_read_lines = line_count;
}

double ImageClassifier::FindPerformance(string test_filename, 
	string classifier_filename) {
	ifstream test_model(test_filename);
	ifstream calc_model(classifier_filename);
	double num_total = 0, num_correct = 0;
	vector<vector<double>> confusion_matrix(training_data_.size(), 
		vector<double> (training_data_.size(), 0));
	vector<double> test_sums(training_data_.size(), 0);

	// Compares every value in the test and calculated files to find 
	// the performance of the image classifier.
	while (!test_model.eof()) {
		int realans, calcans;
		test_model >> realans;
		calc_model >> calcans;
		if (realans == calcans) {
			num_correct++;
		}
		confusion_matrix[realans][calcans] += 1;
		test_sums[realans] += 1;
		num_total++;
	}
	PrintConfusionMatrix(confusion_matrix, test_sums);
	return num_correct / num_total;
}

void ImageClassifier::PrintConfusionMatrix(
	vector<vector<double>> confusion_matrix, vector<double> test_sums) {
	int round_two_digits = 100;
	for (unsigned i = 0; i < confusion_matrix.size(); i++) {
		for (unsigned j = 0; j < confusion_matrix[0].size(); j++) {
			float val = ROUNDF(confusion_matrix[i][j] / test_sums[i], 
				round_two_digits);
			cout << val << "\t";
		}
		cout << endl;
	}
}

void ImageClassifier::ReadModel(string filename) {
	ifstream model(filename);
	string class_probability;

	// Reads the first num_labels lines, and adds them to the class 
	// probabilities vector.
	for (int i = 0; i < num_labels; i++) {
		getline(model, class_probability);
		class_probabilities_.push_back(stold(class_probability));
	}

	// Reads the next num_labels lines, and splits the string by space to add 
	// them to the training data vector.
	for (int i = 0; i < num_labels; i++) {
		string training_line_data;
		getline(model, training_line_data);
		vector<double> single_data = SplitString(training_line_data, ' ');
		training_data_.push_back(single_data);
	}
}

unsigned ImageClassifier::DetermineLabel() {
	unsigned max_label = 0;
	double max_num;

	// Goes through every possible label and assigns max_label to the label 
	// with the highest probability.
	for (unsigned i = 0; i < training_data_.size(); i++) {
		double probability = CalculateProbability(training_data_[i], 
			class_probabilities_[i], input);

		// Assigns the maximum probability to the first value at the start of 
		// the loop.
		if (i == 0) {
			max_num = probability;
		}
		if (probability > max_num) {
			max_label = i;
			max_num = probability;
		}
	}
	return max_label;
}

vector<double> ImageClassifier::SplitString(string line, char delimiter) {
	vector<double> vec;
	stringstream ss(line);
	string value;
	while(getline(ss, value, delimiter)) {
		vec.push_back(stold(value));
	}
	return vec;
}

double ImageClassifier::CalculateProbability(vector<double> training_data, 
	double class_probability, string input) {
	double probability = 0;

	// Calculates the probability by adding the log of the values in 
	// the training data. If the input is not a '#' or '+', then add the 
	// log of (1 - val).
	for (unsigned i = 0; i < input.size(); i++) {
		if (input[i] == '#' || input[i] == '+') {
			probability += log(training_data[i]);
		} else {
			probability += log(1 - training_data[i]);
		}
	}
	return probability + log(class_probability);
}

void ImageClassifier::ClearInput() {
	input.clear();
}

vector<double> ImageClassifier::GetClassProbabilities() {
	return class_probabilities_;
}

vector<vector<double>> ImageClassifier::GetTrainingData() {
	return training_data_;
}

istream &operator>>(istream &is, ImageClassifier &image) {
	string line;
	for (int i = 0; i < image.num_read_lines; i++) {
		getline(is, line);
		image.input.append(line);
	}
	return is;
}