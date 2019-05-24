#define CATCH_CONFIG_MAIN
#include "../catch/catch.hpp"
#include "../TicTacToe.h"

TEST_CASE("Partially empty board returns no winner", "[NoWinner]") {
	REQUIRE(NoWinner == CheckTicTacToeBoard("O...X.X.."));
}

TEST_CASE("Random character inputs returns no winner", "[NoWinner]") {
	REQUIRE(NoWinner == CheckTicTacToeBoard("13l2;3if9"));
}

TEST_CASE("A full board with no winner returns no winner", "[NoWinner]") {
	REQUIRE(NoWinner == CheckTicTacToeBoard("OOXXXOOXX"));
}

TEST_CASE("Mix of different characters returns no winner", "[NoWinner]") {
	REQUIRE(NoWinner == CheckTicTacToeBoard("ox3fmd3.x"));
}

TEST_CASE("A full board with varying cases and no winner returns no winner",
					"[NoWinner]") {
	REQUIRE(NoWinner == CheckTicTacToeBoard("xXoOxXXoO"));
}

TEST_CASE("Random characters less than board size returns invalid input",
					"[InvalidInput]") {
	REQUIRE(InvalidInput == CheckTicTacToeBoard("asdf"));
}

TEST_CASE("Random characters more than board size returns invalid input",
					"[InvalidInput]") {
	REQUIRE(InvalidInput == CheckTicTacToeBoard("MCID;EIWIDZPIOCVD"));
}

TEST_CASE("Special characters more than board size returns invalid input",
					"[InvalidInput]") {
	REQUIRE(InvalidInput == CheckTicTacToeBoard("⒢GgƓḠĜĝĞğĠġǤǥǦǧǴℊ⅁ǵĢģⒽ"));
}

TEST_CASE("Single 'x' character returns invalid input", "[InvalidInput]") {
	REQUIRE(InvalidInput == CheckTicTacToeBoard("x"));
}

TEST_CASE("Single 'o' character returns invalid input", "[InvalidInput]") {
	REQUIRE(InvalidInput == CheckTicTacToeBoard("o"));
}

TEST_CASE("More 'x' and 'o' characters than board length returns invalid input",
					"[InvalidInput]") {
	REQUIRE(InvalidInput == CheckTicTacToeBoard("xoxoxoxoxoOXXOXO"));;
}

TEST_CASE("8 characters returns invalid input", "[InvalidInput][edgecase]") {
	REQUIRE(InvalidInput == CheckTicTacToeBoard("ldocmeks"));
}

TEST_CASE("10 characters returns invalid input", "[InvalidInput][edgecase]") {
	REQUIRE(InvalidInput == CheckTicTacToeBoard("1234567890"));
}

TEST_CASE("Long character stirng returns invalid input", "[InvalidInput]") {
	REQUIRE(InvalidInput == CheckTicTacToeBoard("NuTgE81HUQJFkyfOvNl6s0ERlqlvK11EWkHxVBoQTavBxM3rgIEayAi1v4dJU6nqaOoWMTMXkYYq5LFTVQFnFlklwtcfblKXrn9kyZ5CrNj3eDWwMFXs9jIMt8T9II8peUKOJhHoMWgOOuwvX66UoNJcsTi4BfMYSFeKZQChGTlXNOqurrhTAtzvZ8iDpM4zvbMrTHhOBmM4Dj4cIAGEBk4KChvovJh3pjtWMe1t43K9lhWY7UC8cr0cVszOS4cA8jvasBBYjWPOfsnqcMVsrv4ORQYC8gt4v6YjicXOlvbJKlMDkyVMwTroPyHKD4mFhuPZSshLAQjF15j5ZzsQkadU2YxJyT3mjTkCtA9lNpZFJxSAgqqfV4JBmirAsAzDZ5lPyAyyadisEAtAvxLdIyvBbQ2HZ44OEVKXDt3SicrY25xjQoPSV41VbWPPkTuUEDOVVsKtzCXd3S9x5CgTgX1fFCEdXANkF9tgeAUY6PMlSYVZILqs"));
}

TEST_CASE("Empty string returns invalid input", "[InvalidInput]") {
	REQUIRE(InvalidInput == CheckTicTacToeBoard(""));
}

TEST_CASE("'x' wins twice returns x wins with row and column",
					"[Xwins][edgecase]") {
	REQUIRE(Xwins == CheckTicTacToeBoard("xxxxooxoo"));
}

TEST_CASE("'x' wins twice returns x wins with row and diagonal",
					"[Xwins][edgecase]") {
	REQUIRE(Xwins == CheckTicTacToeBoard("ooxxxxxoo"));
}

TEST_CASE("'x' wins twice returns x wins with column and diagonal",
					"[Xwins][edgecase]") {
	REQUIRE(Xwins == CheckTicTacToeBoard("xoxoxxoox"));
}

TEST_CASE("'x' wins twice on diagonals returns x wins", "[Xwins][edgecase]") {
	REQUIRE(Xwins == CheckTicTacToeBoard("xoxoxoxox"));
}

TEST_CASE("Top row full of 'x' returns x wins", "[Xwins]") {
	REQUIRE(Xwins == CheckTicTacToeBoard("XXX.OO..."));
}

TEST_CASE("Top row full of 'x' with random casing returns x wins", "[Xwins]") {
	REQUIRE(Xwins == CheckTicTacToeBoard("XxXOo.Ezi"));
}

TEST_CASE("Diagonal full of 'x' returns x wins", "[Xwins]") {
	REQUIRE(Xwins == CheckTicTacToeBoard("xIOOXiklX"));
}

TEST_CASE("Column full of 'x' returns x wins", "[Xwins]") {
	REQUIRE(Xwins == CheckTicTacToeBoard("oixoexifx"));
}

TEST_CASE("Row full of 'o' returns o wins", "[Owins]") {
	REQUIRE(Owins == CheckTicTacToeBoard("OOO.XXXX."));
}

TEST_CASE("Row full of 'o' with random casing returns o wins", "[Owins]"){
	REQUIRE(Owins == CheckTicTacToeBoard("Xx2OoO3XX"));
}

TEST_CASE("Diagonal full of 'o' returns o wins", "[Owins]") {
	REQUIRE(Owins == CheckTicTacToeBoard("x3oXo3oxx"));
}

TEST_CASE("Column full of 'o' returns o wins", "[Owins]") {
	REQUIRE(Owins == CheckTicTacToeBoard("xoxxokeox"));
}

TEST_CASE("More 'o' than 'x' returns unreachable state", "[UnreachableState]") {
	REQUIRE(UnreachableState == CheckTicTacToeBoard("OOOOOOXXX"));
}

TEST_CASE("Single 'o' more than 'x' returns unreachable state",
					"[UnreachableState]") {
	REQUIRE(UnreachableState == CheckTicTacToeBoard("xXOidOksO"));
}

TEST_CASE("Two more 'x' than 'o' returns unreachable state",
					"[UnreachableState]") {
	REQUIRE(UnreachableState == CheckTicTacToeBoard("odkxxilxx"));
}

TEST_CASE("'x' and 'o' both win returns unreachable state",
					"[UnreachableState]") {
	REQUIRE(UnreachableState == CheckTicTacToeBoard("xxxdieooo"));
}

TEST_CASE("Same number of 'x' and 'o', but x won returns unreachable state",
					"[UnreachableState][edgecase]") {
	REQUIRE(UnreachableState == CheckTicTacToeBoard("xxxdokjoo"));
}

TEST_CASE("Board full of 'x' returns unreachable state",
					"[UnreachableState]") {
	REQUIRE(UnreachableState == CheckTicTacToeBoard("xxxXXXxxX"));
}

TEST_CASE("Board full of 'o' returns unreachable state", "[UnreachableState]") {
	REQUIRE(UnreachableState == CheckTicTacToeBoard("OOooOOooO"));
}