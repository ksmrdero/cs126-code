#include "sudoku.h"
#include <iostream>

const unsigned kBoardLength = 9;
const unsigned kSubSquareLength = 3;
const unsigned kStartNumber = 1;
const unsigned kEndNumber = 9;
const char kEmptyValue = '_';

Sudoku::Sudoku() {
  // Does nothing.
}

Sudoku::Sudoku(string input) {
  str_board_ = input;
  MakeMatrix(input);
}

Matrix Sudoku::GetBoard() {
  return board_;
}

void Sudoku::MakeMatrix(string input) {
  for(unsigned row = 0; row < kBoardLength; row++) {

    // Creates a temporary vector first, and then appends
    // it the board matrix to create the board.
    vector<char> temp_row_nums;
    for(unsigned col = 0; col < kBoardLength; col++) {
      temp_row_nums.push_back(input[row * kBoardLength + col]);
    }
    board_.push_back(temp_row_nums);
  }
}

bool Sudoku::FindSolution() {
  unsigned row = 0;
  unsigned col = 0;
  if (!FindNextLocation(row, col)) {
    return true;
  }

  // Tries to iterate through all 9 numbers, to see if
  // a number can fit, and continues this loop until
  // no more empty spaces are found.
  // Inspired by Mohan Das, Nikash, and the recommended algorithm.
  for (unsigned i = kStartNumber; i <= kEndNumber; i++) {
    char new_num = '0' + i;
    if (CountRowConflicts(new_num, row) == 0
    && CountColumnConflicts(new_num, col) == 0
    && CountSubSquareConflicts(new_num, row, col) == 0) {
      board_[row][col] = new_num;
      if (FindSolution()) {
        return true;
      }
      board_[row][col] = kEmptyValue;
    }
  }
  return false;
}

bool Sudoku::IsValidInput() {
  int num_bad_conflict = 2;
  for (unsigned row = 0; row < board_.size(); row++) {
    for (unsigned col = 0; col < board_[0].size(); col++) {

      // If the square contains a numeric value, continue.
      if (board_[row][col] != kEmptyValue) {
        char value = board_[row][col];

        // Determines if there are multiple conflicts within the
        // row, column, or sub-square, then the inputted board
        // will not be valid.
        if (CountColumnConflicts(value, col) >= num_bad_conflict
            || CountRowConflicts(value, row) >= num_bad_conflict
            || CountSubSquareConflicts(value, row, col) >= num_bad_conflict) {
          return false;
        }
      }
    }
  }
  return true;
}

bool Sudoku::FindNextLocation(unsigned &row, unsigned &col) {
  for (row = 0; row < board_.size(); row++) {
    for (col = 0; col < board_[0].size(); col++) {
      if (board_[row][col] == kEmptyValue) {
        return true;
      }
    }
  }
  return false;
}

int Sudoku::CountRowConflicts(char value, int row) {
  int conflict_count = 0;
  for (unsigned col = 0; col < board_[row].size(); col++) {
    if (board_[row][col] == value) {
      conflict_count++;
    }
  }
  return conflict_count;
}

int Sudoku::CountColumnConflicts(char value, int col) {
  int conflict_count = 0;
  for (unsigned row = 0; row < board_.size(); row++) {
    if (board_[row][col] == value) {
      conflict_count++;
    }
  }
  return conflict_count;
}

int Sudoku::CountSubSquareConflicts(char value, int row, int col) {
  int conflict_count = 0;

  // The start rows and columns for each sub-square
  // given the position of the inputted value.
  unsigned start_row_num = (row / kSubSquareLength) * kSubSquareLength;
  unsigned start_col_num = (col / kSubSquareLength) * kSubSquareLength;

  // Iterates through the next 3 spaces from the start number
  // and checks if a value is found.
  for (unsigned row = start_row_num; row < start_row_num + kSubSquareLength; row++) {
    for (unsigned col = start_col_num; col < start_col_num + kSubSquareLength; col++) {
      if (board_[row][col] == value) {
        conflict_count++;
      }
    }
  }
  return conflict_count;
}

std::ostream &operator<<(std::ostream &output, const Sudoku &puzzle) {
  output << " -------------------------------------" << std::endl;
  for (unsigned row = 0; row < puzzle.board_.size(); row++) {
    for (unsigned col = 0; col < puzzle.board_[0].size(); col++) {
      output << " | " << puzzle.board_[row][col];
    }
    output << " | " << std::endl <<" -------------------------------------" <<
    std::endl;
  }
  return output;
}

std::istream &operator>>(std::istream &input, Sudoku &puzzle) {
  input >> puzzle.str_board_;
  puzzle.MakeMatrix(puzzle.str_board_);
  return input;
}
