#pragma once

#include <string>
#include <vector>

enum TicTacToeState {UnreachableState, Xwins, Owins, NoWinner, InvalidInput};

/**
 * Evaluates the given board position.
 * @param boardState the current board position
 * @return the state of the board position
 */
TicTacToeState CheckTicTacToeBoard(std::string board);
