EXENAME = main

CXX = clang++
CXXFLAGS = -std=c++0x -g -O0 -Wall -Wextra

all : $(EXENAME)

$(EXENAME): main.cpp sudoku.cpp
	$(CXX) $(CXXFLAGS) main.cpp sudoku.cpp -o $(EXENAME)

test: tests/sudoku_test.cpp sudoku.cpp
	$(CXX) $(CXXFLAGS) tests/sudoku_test.cpp sudoku.cpp -o test

.PHONY: clean
clean:
	rm -f $(EXENAME) test
