#pragma once
#include <string>
#include <vector>
#include <fstream>
#include <sstream>
#include <iostream>
#include <math.h>

using std::string;
using std::vector;
using std::stringstream;
using std::ifstream;
using std::istream;
using std::getline;
using std::stold;
using std::cout;
using std::endl;

#define ROUNDF(f, c) (((float)((int)((f) * (c))) / (c)))

/**
 * Reads in a model created by ImageTrainer and classifies the data.
 */
class ImageClassifier {
	public:
		/**
		 * Constructor for the image classifier class.
		 * @param label_count The number of test labels.
		 * @param line_count  The number of lines each test case contains.
		 */
		ImageClassifier(int label_count, int line_count);

		/**
		 * Determines the label for each image.
		 * @return An unsigned integer representing the label for the image.
		 */
		unsigned DetermineLabel();

		/**
		 * Calculates the performance of the algorithm and prints out 
		 * a confusion matrix for the algorithm.
		 * @param  test_filename       The filename containing the correct 
		 *                             labels.
		 * @param  classifier_filename The filename containing the labels 
		 *                             found from classifier.
		 * @return                     The percent performance of the algorithm.
		 */
		double FindPerformance(string test_filename, 
			string classifier_filename);

		/**
		 * Calculates the probability of each number given the training data and 
		 * an input test.
		 * @param  training_data     The training data vector.
		 * @param  class_probability The probability each class.
		 * @param  input             The image to test.
		 * @return                   The probability that the test image is a 
		 *                           certain label. 
		 */
		double CalculateProbability(vector<double> training_data, 
			double class_probability, string input);

		/**
		 * Prints the confusion matrix to terminal.
		 * @param confusion_matrix The confusion matrix as a 2D vector.
		 * @param test_sums        The sums of each row from the confusion 
		 *                         matrix.
		 */
		void PrintConfusionMatrix(vector<vector<double>> confusion_matrix, 
			vector<double> test_sums);

		/**
		 * Reads in the model written by ImageTrainer.
		 * @param filename The filename that contains the data.
		 */
		void ReadModel(string filename);

		/**
		 * Clears the input string.
		 */
		void ClearInput();

		/**
		 * Splits a string given a delimiter and converts it to a vector.
		 * @param line 		The string to be split.
		 * @param delimiter	The delimiter that splits each string.
		 * @return 			A vector containing the split string.
		 */
		vector<double> SplitString(string line, char delimiter);

		/**
		 * Gets the class probabilities vector.
		 * @return 	The class probabilities vector.
		 */
		vector<double> GetClassProbabilities();

		/**
		 * Gets the training data vector.
		 * @return 	The training data vector.
		 */
		vector<vector<double>> GetTrainingData();
		friend istream &operator>>(istream &is, ImageClassifier &i);

	private:
		string input;
		int num_labels;
		int num_read_lines;
		vector<vector<double>> training_data_;
		vector<double> class_probabilities_;
};