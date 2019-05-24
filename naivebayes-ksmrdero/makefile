EXENAME = main

CXX = clang++
CXXFLAGS = -std=c++0x -g -O0 -Wall -Wextra

all : $(EXENAME)

$(EXENAME): main.cpp image_trainer.cpp image_classifier.cpp
	$(CXX) $(CXXFLAGS) main.cpp image_classifier.cpp image_trainer.cpp -o $(EXENAME)

test_classifier: tests/image_classifier_test.cpp image_classifier.cpp
	$(CXX) $(CXXFLAGS) tests/image_classifier_test.cpp image_classifier.cpp image_trainer.cpp -o classifier

test_trainer: tests/image_trainer_test.cpp image_trainer.cpp 
	$(CXX) $(CXXFLAGS) tests/image_trainer_test.cpp image_classifier.cpp image_trainer.cpp -o trainer

.PHONY: clean
clean:
	rm -f $(EXENAME) classifier trainer
