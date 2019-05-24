#include "ll.h"
#include <iostream>

using std::cout;
using std::endl;

using namespace cs126linkedlist;

int main() {
  std::vector<int> v = {1,2,3,4,5,6, 7};
  LinkedList<int> l(v);
  l.RemoveOdd();
  cout << l << endl;
  return 0;
}