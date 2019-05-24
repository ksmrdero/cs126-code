#include "TicTacToe.h"
#include <string>
#include <vector>
#include <algorithm>

typedef std::vector< std::vector<int> > Matrix;

const Matrix kRowWinComb = {{3, 4, 5}, {6, 7, 8}, {0, 1, 2}};
const Matrix kColWinComb = {{0, 3, 6}, {1, 4, 7}, {2, 5, 8}};
const Matrix kDiagWinComb = {{0, 4, 8}, {2, 4, 6}};

/**
 * Grabs the indices of given character in string.
 * @param board Inputted board position
 * @param c character you want to look for
 * @return an array list of indices of character
 */
std::vector<int> AddIndicies(std::string board, char c) {
  std::vector<int> indices;
  for (int i = 0; i < board.size(); i++) {
    if (board[i] == c) {
      indices.push_back(i);
    }
  }
  return indices;
}

/**
 * Counts the number of times x wins, o wins, or neither
 * @param index An array list of indices of x or o in the given board position
 * @return number of possible ways a x or o has won.
 */
int FindWinCount(std::vector<int> index, Matrix win_position) {
  int win_count = 0;
  for (int row = 0; row < win_position.size(); row++) {
    int row_count = 0;
    for (int col = 0; col < win_position[0].size(); col++) {
      if (std::find(index.begin(), index.end(),
          win_position[row][col]) != index.end()) {
          row_count += 1;
      }
    }

    //if a winning combination from the win_position matrix is found
    if (row_count == 3) {
      win_count += 1;
    }
  }
  return win_count;
}

/**
 * Determines if a given character has won.
 * @param player the given player to determine if they won
 * @return If the given character has won
 */
bool DetermineWins(std::string board, char player) {
  std::vector<int> player_index = AddIndicies(board, player);
  int row_wins = FindWinCount(player_index, kRowWinComb);
  int col_wins = FindWinCount(player_index, kColWinComb);
  int diag_wins = FindWinCount(player_index, kDiagWinComb);

  if (row_wins + col_wins + diag_wins == 1) {
    return true;
  }

  if (row_wins == 1 && col_wins == 1 && diag_wins == 0) {
    return true;
  } else if (row_wins == 1 && col_wins == 0 && diag_wins == 1) {
    return true;
  } else if (row_wins == 0 && col_wins == 1 && diag_wins == 1) {
    return true;
  } else if (row_wins == 0 && col_wins == 0 && diag_wins == 2) {
    return true;
  }
  return false;
}

TicTacToeState CheckTicTacToeBoard(std::string board) {
  std::transform(board.begin(), board.end(), board.begin(), ::tolower);
  unsigned board_length = 9;
  if (board.size() != board_length) {
    return InvalidInput;
  }

  std::vector<int> o_index = AddIndicies(board, 'o');
  std::vector<int> x_index = AddIndicies(board, 'x');
  int stateCheck = x_index.size() - o_index.size();
  if (stateCheck != 0 && stateCheck != 1) {
    return UnreachableState;
  }

  bool o_wins = DetermineWins(board, 'o');
  bool x_wins = DetermineWins(board, 'x');
  if (o_wins == true && x_wins == false) {
    return Owins;
  } else if (o_wins == false && x_wins == true) {

    //checks if x won but an equal number of x's and o's are on the board
    if (stateCheck == 0) {
      return UnreachableState;
    }
    return Xwins;
  } else if (o_wins == false && x_wins == false) {
    return NoWinner;
  } else {
    return UnreachableState;
  }
}
