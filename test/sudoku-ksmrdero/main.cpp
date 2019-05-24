#include "sudoku.h"
#include <iostream>
#include <fstream>

using std::cout;
using std::cin;
using std::endl;

int main() {
  string read_filename;
  string write_filename;
  string format_specifier;
  cout << "Enter a filename to read in the sudoku puzzles: ";
  cin >> read_filename;
  cout << "Enter a filename to write in the sudoku solutions (type '/no' if you don't want to specify a filename): ";
  cin >> write_filename;

  std::ifstream input_file(read_filename);
  input_file >> format_specifier;
  int puzzle_count = 1;

  // Checks if the format of the file is correct, otherwise the class
  // cannot process the information.
  if (format_specifier == "#spf1.0") {
    if (write_filename == "/no") {

      // While more puzzles remain in the file, pretty print the
      // puzzle number and the solution to the puzzle to the console.
      while (!input_file.eof()) {
        cout << "Puzzle " << puzzle_count << ": " << endl;
        Sudoku puzzle;
        input_file >> puzzle;
        puzzle_count++;
        if (!puzzle.IsValidInput()) {
          cout << "No solution found" << endl;
          continue;
        }

        //If no solution exists, no solution will be printed out.
        if (puzzle.FindSolution()) {
          cout << puzzle;
        }
      }
    } else {
      std::ofstream output_file(write_filename);

      // While more puzzles remain in the file, pretty print the puzzle
      // number and the solution to the puzzle to the file the user specified.
      while (!input_file.eof()) {
        output_file << "Puzzle " << puzzle_count << ": " << endl;
        Sudoku puzzle;
        input_file >> puzzle;
        puzzle_count++;
        if (!puzzle.IsValidInput()) {
          output_file << "No solution found" << endl;
          continue;
        }

        if (puzzle.FindSolution()) {
          output_file << puzzle;
        }
      }
    }
  } else {
    cout << "File format not supported";
  }
  return 0;
}
