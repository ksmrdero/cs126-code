#define CATCH_CONFIG_MAIN
#include "../catch/catch.hpp"
#include "../sudoku.h"

TEST_CASE("Solution is correct to first puzzle", "[SolveBoard]") {
	Sudoku s("3_65_84__52________87____31__3_1__8_9__863__5_5__9_6__13____25________74__52_63__");
	bool found_answer = s.FindSolution();
	string solution_string;
	Matrix solution_board = s.GetBoard();
	for (unsigned row = 0; row < solution_board.size(); row++) {
    for (unsigned col = 0; col < solution_board[0].size(); col++) {
			solution_string.push_back(solution_board[row][col]);
    }
  }
	REQUIRE(found_answer == true);
	REQUIRE(solution_string ==
		"316578492529134768487629531263415987974863125851792643138947256692351874745286319");
}

TEST_CASE("Solution is correct to second puzzle", "[SolveBoard]") {
	Sudoku s("___8_5____3__6___7_9___38___4795_3______71_9____2__5__1____248___9____5______6___");
	bool found_answer = s.FindSolution();
	string solution_string;
	Matrix solution_board = s.GetBoard();
	for (unsigned row = 0; row < solution_board.size(); row++) {
    for (unsigned col = 0; col < solution_board[0].size(); col++) {
			solution_string.push_back(solution_board[row][col]);
    }
  }
	REQUIRE(found_answer == true);
	REQUIRE(solution_string ==
		"714825936538469127692713845247958361853671294961234578176592483389147652425386719");
}

TEST_CASE("Solution is correct to puzzle needing one space", "[SolveBoard]") {
	Sudoku s("714825936538469127692713845247958_61853671294961234578176592483389147652425386719");
	bool found_answer = s.FindSolution();
	string solution_string;
	Matrix solution_board = s.GetBoard();
	for (unsigned row = 0; row < solution_board.size(); row++) {
    for (unsigned col = 0; col < solution_board[0].size(); col++) {
			solution_string.push_back(solution_board[row][col]);
    }
  }
	REQUIRE(found_answer == true);
	REQUIRE(solution_string ==
		"714825936538469127692713845247958361853671294961234578176592483389147652425386719");
}

TEST_CASE("Already solved solution shouldn't need to be changed", "[SolveBoard]") {
	Sudoku s("316578492529134768487629531263415987974863125851792643138947256692351874745286319");
	bool found_answer = s.FindSolution();
	string solution_string;
	Matrix solution_board = s.GetBoard();
	for (unsigned row = 0; row < solution_board.size(); row++) {
    for (unsigned col = 0; col < solution_board[0].size(); col++) {
			solution_string.push_back(solution_board[row][col]);
    }
  }
	REQUIRE(found_answer == true);
	REQUIRE(solution_string ==
		"316578492529134768487629531263415987974863125851792643138947256692351874745286319");
}

TEST_CASE("Solvable puzzle is a valid input to test", "[SolveBoard]") {
	Sudoku s("3_65_84__52________87____31__3_1__8_9__863__5_5__9_6__13____25________74__52_63__");
	REQUIRE(s.IsValidInput() == true);
}

TEST_CASE("Puzzle with single value is a valid input to test", "[SolveBoard]") {
	Sudoku s("_____9___________________________________________________________________________");
	REQUIRE(s.IsValidInput() == true);
}

TEST_CASE("Empty puzzle is a valid input to test", "[SolveBoard]") {
	Sudoku s("_________________________________________________________________________________");
	REQUIRE(s.IsValidInput() == true);
}

TEST_CASE("Unsolvable full puzzle is an invalid input to test", "[SolveBoard]") {
	Sudoku s("333333333333333333333333333333333333333333333333333333333333333333333333333333333");
	REQUIRE(s.IsValidInput() == false);
}

TEST_CASE("Unsolvable puzzle with one more input is an invalid input to test",
"[SolveBoard]") {
	Sudoku s("33333333333333333333333333333333333_333333333333333333333333333333333333333333333");
	REQUIRE(s.IsValidInput() == false);
}

TEST_CASE("Unsolvable partially full puzzle is an invalid input to test",
"[SolveBoard]") {
	Sudoku s("666666666______6666666666666_666666_66666666_66666666_66666666____666666666666666");
	REQUIRE(s.IsValidInput() == false);
}

TEST_CASE("Unsolvable puzzle returns false when finding solution", "[SolveBoard]") {
	Sudoku s("___8_5____3__6___7_9___38___4795_33_____71_9____2__5__1____248___9____5______6___");
	bool found_answer = s.FindSolution();
	REQUIRE(found_answer == false);
}

TEST_CASE("Already solved puzzle returns true when finding solution", "[SolveBoard]") {
	Sudoku s("714825936538469127692713845247958361853671294961234578176592483389147652425386719");
	bool found_answer = s.FindSolution();
	REQUIRE(found_answer == true);
}

TEST_CASE("Empty location exists in an incomplete board", "[CheckBoard]") {
	Sudoku s("___8_5____3__6___7_9___38___4795_3______71_9____2__5__1____248___9____5______6__9");
	unsigned row = 5;
	unsigned col = 4;
	REQUIRE(s.FindNextLocation(row, col) == true);
}

TEST_CASE("Complete board cannot find an empty location", "[CheckBoard]") {
	Sudoku s("316578492529134768487629531263415987974863125851792643138947256692351874745286319");
	unsigned row = 2;
	unsigned col = 3;
	REQUIRE(s.FindNextLocation(row, col) == false);
}

TEST_CASE("Column are assigned to the next empty location", "[CheckBoard]") {
	Sudoku s("___8_5____3__6___7_9___38___4795_3______71_9____2__5__1____248___9____5______6__9");
	unsigned row = 6;
	unsigned col = 7;
	s.FindNextLocation(row, col);
	REQUIRE(col == 0);
}

TEST_CASE("Row is assigned to the next empty location", "[CheckBoard]") {
	Sudoku s("___8_5____3__6___7_9___38___4795_3______71_9____2__5__1____248___9____5______6__9");
	unsigned row = 6;
	unsigned col = 7;
	s.FindNextLocation(row, col);
	REQUIRE(row == 0);
}

TEST_CASE("Column is assigned to the last empty location",
"[CheckBoard][EdgeCase]") {
	Sudoku s("316578492529134768487629531263415987974863125851792643138947256692351874_45286319");
	unsigned row = 5;
	unsigned col = 4;
	s.FindNextLocation(row, col);
	REQUIRE(col == 0);
}

TEST_CASE("Row is correctly assigned to the last empty location",
"[CheckBoard][EdgeCase]") {
	Sudoku s("316578492529134768487629531263415987974863125851792643138947256692351874_45286319");
	unsigned row = 5;
	unsigned col = 4;
	s.FindNextLocation(row, col);
	REQUIRE(row == 8);
}

TEST_CASE("Column are assigned to 0 in empty board", "[CheckBoard]") {
	Sudoku s("_________________________________________________________________________________");
	unsigned row = 1;
	unsigned col = 1;
	s.FindNextLocation(row, col);
	REQUIRE(col == 0);
}

TEST_CASE("Row is assigned to 0 in empty board", "[CheckBoard]") {
	Sudoku s("_________________________________________________________________________________");
	unsigned row = 1;
	unsigned col = 1;
	s.FindNextLocation(row, col);
	REQUIRE(row == 0);
}

TEST_CASE("Column is assigned to middle location in a partially filled board",
"[CheckBoard]") {
	Sudoku s("3165784925291347684876295312639530______71_9____2__5__1____248___9____5______6__9");
	unsigned row = 1;
	unsigned col = 1;
	s.FindNextLocation(row, col);
	REQUIRE(col == 7);
}

TEST_CASE("Row is assigned to middle location in a partially filled board", "[CheckBoard]") {
	Sudoku s("3165784925291347684876295312639530______71_9____2__5__1____248___9____5______6__9");
	unsigned row = 1;
	unsigned col = 1;
	s.FindNextLocation(row, col);
	REQUIRE(row == 3);
}

TEST_CASE("Input number in a row with existing number returns false",
"[PlaceNumber]") {
	Sudoku s("3_65_84__52________87____31__3_1__8_9__863__5_5__9_6__13____25________74__52_63__");
	REQUIRE(s.CountRowConflicts('8', 0) == 1);
}

TEST_CASE("Input number in a row without existing number returns true",
"[PlaceNumber]") {
	Sudoku s("___8_5____3__6___7_9___38___4795_3______71_9____2__5__1____248___9____5______6__9");
	REQUIRE(s.CountRowConflicts('1', 3) == 0);
}

TEST_CASE("Input number in a column with existing number returns false",
"[PlaceNumber]") {
	Sudoku s("___8_5____3__6___7_9___38___4795_3______71_9____2__5__1____248___9____5______6__9");
	REQUIRE(s.CountColumnConflicts('7', 8) == 1);
}

TEST_CASE("Input number in a column without existing number returns true",
"[PlaceNumber]") {
	Sudoku s("3_65_84__52________87____31__3_1__8_9__863__5_5__9_6__13____25________74__52_63__");
	REQUIRE(s.CountColumnConflicts('6', 3) == 0);
}

TEST_CASE("Input number in a sub-square with existing number returns false",
"[PlaceNumber]") {
	Sudoku s("___8_5____3__6___7_9___38___4795_3______71_9____2__5__1____248___9____5______6__9");
	REQUIRE(s.CountSubSquareConflicts('3', 4, 7) == 1);
}

TEST_CASE("Input number in a sub-square without existing number returns true",
"[PlaceNumber]") {
	Sudoku s("___8_5____3__6___7_9___38___4795_3______71_9____2__5__1____248___9____5______6__9");
	REQUIRE(s.CountSubSquareConflicts('6', 5, 5) == 0);
}
