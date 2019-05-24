#include "image_trainer.h"

ImageTrainer::ImageTrainer(int label_count, int line_count) {
	num_read_lines = line_count;
	int total_pixel_count = num_read_lines * num_read_lines;
	vector<double> temp(total_pixel_count, 0);
	for (int i = 0; i < label_count; i++) {
		training_data_.push_back(temp);
		class_count_.push_back(0);
	}
	total_count_ = 0;
}

void ImageTrainer::WriteModel(string filename) {
	std::ofstream output_file(filename);

	// Adds the class count to the file first.
	for (unsigned i = 0; i < class_count_.size(); i++) {
		output_file << class_count_[i] << std::endl;
	}

	// Adds training data to the file. Each label is separated by a new line,
	// and every value in each label is separated by a space.
	for (unsigned i = 0; i < training_data_.size(); i++) {
		for (unsigned j = 0; j < training_data_[0].size(); j++) {
			output_file << training_data_[i][j] << " ";
		}
		output_file << std::endl;
	}
}

void ImageTrainer::AddData(int num, string input) {
	double edge_impact = 0.75;
	for (unsigned i = 0; i < input.size(); i++) {
		if (input[i] == '#') {
			training_data_[num][i] += 1;
		} else if (input[i] == '+') {
			training_data_[num][i] += edge_impact;
		}
	}
	class_count_[num] += 1;
	total_count_++;
}

void ImageTrainer::CalculateClassProbability() {
	for (unsigned i = 0; i < class_count_.size(); i++) {
		class_count_[i] /= total_count_;
	}
}

void ImageTrainer::CalculatePixelProbability(double smooth_const) {
	for (unsigned i = 0; i < training_data_.size(); i++) {
		for (unsigned j = 0; j < training_data_[0].size(); j++) {
			training_data_[i][j] = (training_data_[i][j] + smooth_const) / 
				((2 * smooth_const) + class_count_[i]);
		}
	}
}

void ImageTrainer::ClearInput() {
	input.clear();
}

string ImageTrainer::GetInput() {
	return input;
}

vector<double> ImageTrainer::GetClassCount() {
	return class_count_;
}

vector<vector<double>> ImageTrainer::GetTrainingData() {
	return training_data_;
}

istream &operator>>(istream &is, ImageTrainer &image) {
	string line;
	for (int i = 0; i < image.num_read_lines; i++) {
		std::getline(is, line);
		image.input.append(line);
	}
	return is;
}