#define CATCH_CONFIG_MAIN
#include "../catch/catch.hpp"
#include "../src/ll.h"

using cs126linkedlist::LinkedList;
using std::vector;

TEST_CASE("Constructor can intialize a linked list from a vector", "[Constructor]") {
  vector<int> v = {5,2,5,7,2,8};
  LinkedList<int> l(v);
  REQUIRE(l.front() == 5);
  REQUIRE(l.back() == 8);
  REQUIRE(l.size() == 6);
}

TEST_CASE("Copy constructor can copy a linked list with previous elements in it", "[Copy][Constructor]") {
  vector<int> v = {1,2,3,4,5};
  LinkedList<int> l(v);
  LinkedList<int> l2(l);
  REQUIRE(l == l2);
}

TEST_CASE("Copy assignment operator can copy a linked list", "[Copy][Operator]") {
  vector<int> v = {1,2,3,4,5};
  LinkedList<int> l(v);
  LinkedList<int> l2;
  l2 = l;
  REQUIRE(l == l2);
}

TEST_CASE("Copy assignment operator can copy an empty linked list", "[Copy][Operator]") {
  LinkedList<int> l;
  LinkedList<int> l2(vector<int> {1,2,3,4,5});
  l2 = l;
  REQUIRE(l == l2);
}

TEST_CASE("Copy assignment operator can copy to another list where elements already exist", "[Copy][Operator]") {
  LinkedList<int> l(vector<int> {1,2,3,4,5,6});
  LinkedList<int> l2(vector<int> {1,2,3,4,5});
  l2 = l;
  REQUIRE(l == l2);
}

TEST_CASE("Move constructor transfers linked list to one with original elements", "[Move][Constructor]") {
  LinkedList<int> l(vector<int> {1,2,3,4,5});
  LinkedList<int> l2(std::move(l));
  REQUIRE(l2.front() == 1);
  REQUIRE(l2.back() == 5);
  REQUIRE(l2.size() == 5);
}

TEST_CASE("Move constructor removes the list with original elements", "[Move][Constructor]") {
  LinkedList<int> l(vector<int> {1,2,3,4,5});
  LinkedList<int> l2(std::move(l));
  REQUIRE(l.size() == 0);
}

TEST_CASE("Move operator transfers linked list to one with original elements", "[Move][Operator]") {
  LinkedList<int> l(vector<int> {1,2,3,4,5});
  LinkedList<int> l2;
  l2 = std::move(l);
  REQUIRE(l2.front() == 1);
  REQUIRE(l2.back() == 5);
  REQUIRE(l2.size() == 5);
}

TEST_CASE("Move operator removes the list with original elements", "[Move][Operator]") {
  LinkedList<int> l(vector<int> {1,2,3,4,5});
  LinkedList<int> l2;
  l2 = std::move(l);
  REQUIRE(l.size() == 0);
}

TEST_CASE("Single element can be inserted to the front in empty list", "[Insert]") {
  LinkedList<int> l;
  l.push_front(1);
  REQUIRE(l.front() == 1);
  REQUIRE(l.back() == 1);
  REQUIRE(l.size() == 1);
}

TEST_CASE("Multiple elements can be inserted to the front", "[Insert]") {
  LinkedList<int> l;
  l.push_front(5);
  l.push_front(4);
  l.push_front(3);
  l.push_front(2);
  l.push_front(1);
  REQUIRE(l.front() == 1);
  REQUIRE(l.back() == 5);
  REQUIRE(l.size() == 5);
}

TEST_CASE("Single element can be inserted to the back in empty list", "[Insert]") {
  LinkedList<int> l;
  l.push_back(1);
  REQUIRE(l.front() == 1);
  REQUIRE(l.back() == 1);
  REQUIRE(l.size() == 1);
}

TEST_CASE("Elements can be inserted to the back", "[Insert]") {
  LinkedList<int> l;
  l.push_back(5);
  l.push_back(4);
  l.push_back(3);
  l.push_back(2);
  l.push_back(1);
  REQUIRE(l.front() == 5);
  REQUIRE(l.back() == 1);
  REQUIRE(l.size() == 5);
}

TEST_CASE("Multiple push backs and push fronts work", "[Insert]") {
  LinkedList<int> l;
  l.push_back(4);
  l.push_back(3);
  l.push_front(2);
  l.push_front(1);
  REQUIRE(l.front() == 1);
  REQUIRE(l.back() == 3);
  REQUIRE(l.size() == 4);
}

TEST_CASE("Pop front removes first element", "[Insert]") {
  vector<int> v = {2,4,8,9,2,3};
  LinkedList<int> l(v);
  l.pop_front();
  REQUIRE(l.front() == 4);
  REQUIRE(l.size() == 5);
}

TEST_CASE("Pop back removes last element", "[Remove]") {
  vector<int> v = {2,4,8,9,2,3};
  LinkedList<int> l(v);
  l.pop_back();
  REQUIRE(l.back() == 2);
  REQUIRE(l.size() == 5);
}

TEST_CASE("Multiple pop back and pop front removes multiple elements", "[Remove]") {
  vector<int> v = {2,4,8,9,2,3};
  LinkedList<int> l(v);
  l.pop_back();
  l.pop_back();
  l.pop_back();
  l.pop_front();
  l.pop_front();
  REQUIRE(l.front() == 8);
  REQUIRE(l.back() == 8);
  REQUIRE(l.size() == 1);
}

TEST_CASE("Linked list is deleted when cleared", "[Clear]") {
  vector<int> v = {2,4,8,9,2,3};
  LinkedList<int> l(v);
  l.clear();
  REQUIRE(l.size() == 0);
}

TEST_CASE("Linked list is empty when cleared", "[Empty][Clear]") {
  vector<int> v = {2,4,8,9,2,3};
  LinkedList<int> l(v);
  l.clear();
  REQUIRE(l.empty() == true);
}

TEST_CASE("Linked list is not empty when intialized to random values", "[Empty]") {
  vector<int> v = {2,4,8,9,2,3};
  LinkedList<int> l(v);
  REQUIRE(l.empty() == false);
}

TEST_CASE("Odd indicies are removed from linked list with odd size", "[RemoveOdd]") {
  vector<int> v = {0,1,2,3,4,5,6};
  vector<int> v2 = {0,2,4,6};
  LinkedList<int> l(v);
  LinkedList<int> l_even(v2);
  l.RemoveOdd();
  REQUIRE(l == l_even);
}

TEST_CASE("Odd indicies are removed from linked list with even size", "[RemoveOdd]") {
  vector<int> v = {1,3,5,8,3,4};
  vector<int> v2 = {1,5,3};
  LinkedList<int> l(v);
  LinkedList<int> l_even(v2);
  l.RemoveOdd();
  REQUIRE(l == l_even);
}

TEST_CASE("No indicies are removed from linked list with 1 node", "[RemoveOdd]") {
  vector<int> v = {1};
  vector<int> v2 = {1};
  LinkedList<int> l(v);
  LinkedList<int> l_even(v2);
  l.RemoveOdd();
  REQUIRE(l == l_even);
}

TEST_CASE("Two linked lists with same data are equal", "[Operator]") {
  vector<int> v = {1,2,3,4,5};
  vector<int> v2 = {1,2,3,4,5};
  LinkedList<int> l(v);
  LinkedList<int> l2(v2);
  bool is_equal = (l == l2);
  REQUIRE(is_equal == true);
}

TEST_CASE("Two linked lists with different data are not equal", "[Operator]") {
  vector<int> v = {1,2,3,4,5};
  vector<int> v2 = {1,2,3,4,6};
  LinkedList<int> l(v);
  LinkedList<int> l2(v2);
  bool is_equal = (l == l2);
  REQUIRE(is_equal == false);
}

TEST_CASE("Two linked lists with different sizes are not equal", "[Operator]") {
  vector<int> v = {1,2,3,4,5};
  vector<int> v2 = {1,2,2,3,4,5};
  LinkedList<int> l(v);
  LinkedList<int> l2(v2);
  bool is_equal = (l == l2);
  REQUIRE(is_equal == false);
}

TEST_CASE("Linked list is copied when copy method is called", "[Copy]") {
  vector<int> v = {1,2,3,4,5};
  LinkedList<int> l(v);
  LinkedList<int> l2;
  l2.copy(l);
  REQUIRE(l2 == l);
}

TEST_CASE("Iterator starts at the head of the linked list", "[Iterator]") {
  vector<int> v = {1,2,3,4,5};
  LinkedList<int> l(v);
  LinkedList<int>::iterator it = l.begin();
  REQUIRE(*it == 1);
}

TEST_CASE("Iterator can increment through the linked list", "[Iterator]") {
  vector<int> v = {1,2,3,4,5};
  LinkedList<int> l(v);
  LinkedList<int>::iterator it = l.begin();
  ++it;
  ++it;
  REQUIRE(*it == 3);
}

TEST_CASE("Iterator can iterate through entire linked list", "[Iterator]") {
  vector<int> v = {1,2,3,4,5};
  LinkedList<int> l(v);
  LinkedList<int>::iterator it;
  vector<int> v2;
  for(it = l.begin(); it != l.end(); ++it) {
    v2.push_back(*it);
  }
  REQUIRE(v == v2);
}

TEST_CASE("Const iterator starts at the head of the linked list", "[Iterator]") {
  vector<int> v = {1,2,3,4,5};
  const LinkedList<int> l(v);
  LinkedList<int>::const_iterator it = l.begin();
  REQUIRE(*it == 1);
}

TEST_CASE("Const iterator can increment through the linked list", "[Iterator]") {
  vector<int> v = {1,2,3,4,5};
  const LinkedList<int> l(v);
  LinkedList<int>::const_iterator it = l.begin();
  ++it;
  ++it;
  REQUIRE(*it == 3);
}

TEST_CASE("Const iterator can iterate through entire linked list", "[Iterator]") {
  vector<int> v = {1,2,3,4,5};
  const LinkedList<int> l(v);
  LinkedList<int>::const_iterator it = l.begin();
  vector<int> v2;
  for(it = l.begin(); it != l.end(); ++it) {
    v2.push_back(*it);
  }
  REQUIRE(v == v2);
}

TEST_CASE("Operator<< can correctly output linked list data", "[Operator]") {
  vector<int> v = {1,2,3,4,5};
  LinkedList<int> l(v);
  std::stringstream ss;
  ss << l;
  REQUIRE(ss.str() == "1, 2, 3, 4, 5");
}