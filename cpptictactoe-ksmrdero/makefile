EXENAME = main

CXX = clang++
#CXXFLAGS = -std=c++0x -g -O0 -Wall -Wextra

all : $(EXENAME)

$(EXENAME): tests/TicTacToeTest.cpp TicTacToe.cpp
	$(CXX) tests/TicTacToeTest.cpp TicTacToe.cpp -o $(EXENAME)

.PHONY: clean
clean:
	rm -f $(EXENAME)
