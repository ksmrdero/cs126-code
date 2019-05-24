EXENAME = main

CXX = clang++
# CXXFLAGS = -std=c++0x -g -O0 -Wall -Wextra

all : $(EXENAME)

$(EXENAME): main.cpp ll.hpp
	$(CXX) main.cpp -o $(EXENAME)

test: ../test/ll_test.cpp ll.hpp
	$(CXX) $(CXXFLAGS) ../test/ll_test.cpp -o test

.PHONY: clean
clean:
	rm -f $(EXENAME)  test
