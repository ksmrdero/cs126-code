#pragma once

#include <string>
#include <vector>

using std::string;
using std::vector;

typedef vector< vector<char> > Matrix;

/*
 * A class to solve sudoku puzzles from a specified string format.
 */
class Sudoku {
  public:
    /*
     * Empty constructor that takes in no parameters.
     */
    Sudoku();
    /*
     * Constructor that takes in input as a string.
     */
    Sudoku(string input);
    /*
     * Getter for the board matrix.
     * @return the board matrix.
     */
    Matrix GetBoard();
    /*
     * Creates a 2d matrix out of a string.
     * @param the input string.
     */
    void MakeMatrix(string input);
    /*
     * Finds the solution to the puzzle and converts the board to the solution.
     * @return whether a solution to the puzzle has been found.
     */
    bool FindSolution();
    /*
     * Finds the next empty location in the board.
     * @param row the row of the next empty location.
     * @param col the column of the next empty location.
     * @return whether an empty location exists in the board.
     */
    bool FindNextLocation(unsigned &row, unsigned &col);

    /*
     * Determines if inputted board is valid.
     * @return whether inputted board is valid.
     */
    bool IsValidInput();
    /*
     * Determines if a value can be placed in the assigned row.
     * @param value the value to be inputted.
     * @param row the row to be assigned.
     * @return number of conflicts in a row.
     */
    int CountRowConflicts(char value, int row);
    /*
     * Determines if a value can be placed in the assigned column.
     * @param value the value to be inputted.
     * @param col the col to be assigned.
     * @return number of conflicts in a column.
     */
    int CountColumnConflicts(char value, int col);
    /*
     * Determines if a value can be placed in the assigned sub-square.
     * @param value the value to be inputted.
     * @param row the row to be assigned.
     * @param col the col to be assigned.
     * @return number of conflicts in a sub-square.
     */
    int CountSubSquareConflicts(char value, int row, int col);
    /**
     * Stream operator that pretty prints the sudoku instance.
     * @param os stream to write to.
     * @param s sudoku to write to the stream.
     */
    friend std::ostream &operator<<(std::ostream &os, const Sudoku &s);
    /**
     * Stream operator that reads in a sudoku instance.
     * @param is stream to write to.
     * @param s sudoku to write to the stream.
     */
    friend std::istream &operator>>(std::istream &is, Sudoku &s);

  private:
    Matrix board_;
    string str_board_;
};
