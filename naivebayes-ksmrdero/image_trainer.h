#pragma once
#include <string>
#include <vector>
#include <fstream>
#include <sstream>

using std::string;
using std::vector;
using std::istream;

/**
 * Outputs a model given training images and labels.
 */
class ImageTrainer {
	public:
		/**
		 * Constructor for training images.
		 * @param label_count The number of test labels.
		 * @param line_count  The number of lines each test case contains.
		 */
		ImageTrainer(int label_count, int line_count);

		/**
		 * Writes the model to a file to be classified.
		 * @param filename The filename of the model.
		 */
		void WriteModel(string filename);

		/**
		 * Adds data to the training images and class count vector.
		 * @param num   The label of the image.
		 * @param input The training data as a string.
		 */
		void AddData(int num, string input);

		/**
		 * Calculates the class probability.
		 */
		void CalculateClassProbability();

		/**
		 * Calculates the probability of each pixel.
		 * @param smooth_const A constant used for smoothing.
		 */
		void CalculatePixelProbability(double smooth_const);

		/**
		 * Clears the input string.
		 */
		void ClearInput();

		/**
		 * Gets the input string.
		 * @return The input string.
		 */
		string GetInput();

		/**
		 * Gets the class count vector.
		 * @return The class count vector.
		 */
		vector<double> GetClassCount();

		/**
		 * Gets the training data vector.
		 * @return The training data vector.
		 */
		vector<vector<double>> GetTrainingData();
		friend istream &operator>>(istream &is, ImageTrainer &i);

	private:
		string input;
		int num_read_lines;
		unsigned total_count_;
		vector<double> class_count_;
		vector<vector<double>> training_data_;
};