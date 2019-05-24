#include <utility>
#include "ll.h"

namespace cs126linkedlist {

  // ListNode constructors
  template <typename ElementType>
  LinkedList<ElementType>::LinkedListNode::LinkedListNode() { 
    next = nullptr;
    data = ElementType();
  }

  template <typename ElementType>
  LinkedList<ElementType>::LinkedListNode::LinkedListNode(ElementType &new_data) {
    next = nullptr;
    data = new_data;
  }

  // Linked list constructors
  template<typename ElementType>
  LinkedList<ElementType>::LinkedList() {
    head_ = nullptr;
    tail_ = nullptr;
    length_ = 0;
  }

  template<typename ElementType>
  LinkedList<ElementType>::LinkedList(const std::vector<ElementType> &values) {
    ElementType first_value = values[0];
    head_ = new LinkedListNode(first_value);
    LinkedListNode *current = head_;

    // Goes through each element in the values vector, creates a new 
    // node, and links each node together. 
    for (unsigned i = 1; i < values.size(); i++) {
      ElementType element = values[i];
      LinkedListNode *new_node = new LinkedListNode(element);
      current->next = new_node;
      current = current->next;
    }
    tail_ = current;
    length_ = values.size();
  }

  // Copy constructor
  template<typename ElementType>
  LinkedList<ElementType>::LinkedList(const LinkedList<ElementType>& source)
    : head_(nullptr), tail_(nullptr), length_(0) {
    copy(source);
  }

  // Move constructor
  template<typename ElementType>
  LinkedList<ElementType>::LinkedList(LinkedList<ElementType>&& source) noexcept
    : head_(nullptr), tail_(nullptr), length_(0) {
    this->head_ = source.head_;
    this->tail_ = source.tail_;
    this->length_ = source.length_;

    source.head_ = nullptr;
    source.tail_ = nullptr;
    source.length_ = 0;
  }

  // Destructor
  template<typename ElementType>
  LinkedList<ElementType>::~LinkedList() {
    clear();
  }

  // Copy assignment operator
  template<typename ElementType>
  LinkedList<ElementType>& LinkedList<ElementType>::operator= (const LinkedList<ElementType>& source) {
    if (this != &source) {
      clear();
      copy(source);
    }
    return *this;
  }

  template<typename ElementType>
  void LinkedList<ElementType>::copy(const LinkedList<ElementType>& other) {
    if (!other.empty()) {
      LinkedListNode *curr = other.head_;
      LinkedListNode *prev = nullptr;

      // While more nodes in the other linked list exist, create a new
      // node and link that node with the copied linked list
      while (curr != nullptr) {
        LinkedListNode* new_node = new LinkedListNode(curr->data);
        if (head_ == nullptr){
            head_ = new_node;
        }
        if (prev != nullptr) {
            prev->next = new_node;
        }
        prev = new_node;
        curr = curr->next;
      }
      tail_ = prev;
      tail_->next = nullptr;
      length_ = other.length_;
    }
  }

  // Move assignment operator
  template<typename ElementType>
  LinkedList<ElementType>& LinkedList<ElementType>::operator= (LinkedList<ElementType>&& source) noexcept {
    if (this != &source) {

      // Clear the current linked list, and assign the head, tail and 
      // length to that of the source linked list.
      clear();
      head_ = source.head_;
      tail_ = source.tail_;
      length_ = source.length_;

      // Setting the source linked list data to null.
      source.head_ = nullptr;
      source.tail_ = nullptr;
      source.length_ = 0;
    }
    return *this;
  }

  template<typename ElementType>
  void LinkedList<ElementType>::push_front(ElementType value) {
    LinkedListNode * new_node = new LinkedListNode(value);
    new_node->next = head_;
    if (head_ == nullptr) {
      tail_ = new_node;
      head_ = new_node;
    } else {
      head_ = new_node;
    }
    length_++;
  }

  template<typename ElementType>
  void LinkedList<ElementType>::push_back(ElementType value) {
    LinkedListNode * new_node = new LinkedListNode(value);
    if (tail_ == nullptr) {
      tail_ = new_node;
      head_ = new_node;
    } else {
      tail_->next = new_node;
      tail_ = new_node;
    }
    length_++;
  }

  template<typename ElementType>
  ElementType LinkedList<ElementType>::front() const{
    return head_->data;
  }

  template<typename ElementType>
  ElementType LinkedList<ElementType>::back() const {
    return tail_->data;
  }

  template<typename ElementType>
  void LinkedList<ElementType>::pop_front() {
    if (head_ == nullptr) {
      return;
    }
    if (head_ == tail_) {
      clear();
    } else {
      LinkedListNode *temp_head = head_;
      head_ = head_->next;
      delete temp_head;
    }
    length_--;
  }

  template<typename ElementType>
  void LinkedList<ElementType>::pop_back() {
    if (head_ == nullptr) {
      return;
    }

    // if just a single node, clear the linked list
    if (head_ == tail_) {
      clear();
    } else {
      LinkedListNode *temp_tail = tail_;
      LinkedListNode *current = head_;

      // Find the second to last element in the list, and set it 
      // to tail.
      while (current->next != tail_) {
        current = current->next;
      }
      tail_ = current;
      current->next = nullptr;
      delete temp_tail;
    }
    length_--;
  }

  template<typename ElementType>
  int LinkedList<ElementType>::size() const {
    return length_;
  }

  template<typename ElementType>
  bool LinkedList<ElementType>::empty() const {
    return length_ == 0;
  }

  template<typename ElementType>
  void LinkedList<ElementType>::clear() {
    if (head_ != nullptr) {
      LinkedListNode *next_node;
      while (head_ != nullptr) {
        next_node = head_->next;
        delete head_;
        head_ = next_node;
      }
    }
    tail_ = nullptr;
    length_ = 0;
  }

  template<typename ElementType>
  std::ostream &operator<<(std::ostream &os, const LinkedList<ElementType> &list) {
    typename LinkedList<ElementType>::const_iterator it;
    for(it = list.begin(); it != list.end(); ++it) {
      if (*it == list.back()) {
        os << *it;
      } else {
        os << *it << ", ";
      }
    }
    return os;
  }

  template<typename ElementType>
  void LinkedList<ElementType>::RemoveOdd() {
    if (head_ == nullptr) {
      return;
    }
    LinkedListNode *curr = head_->next;
    LinkedListNode *prev = head_;
    int index = 1;
    while(curr != nullptr) {
      LinkedListNode *next_curr = curr->next;
      if (index % 2 == 1) {
        prev->next = next_curr;
        delete curr;
        length_--;
      } else {
        prev = curr; 
      }
      curr = next_curr;
      index++;
    }
    tail_ = prev;
  }

  template<typename ElementType>
  bool LinkedList<ElementType>::operator==(const LinkedList<ElementType> &rhs) const {
    if (length_ != rhs.size()) {
      return false;
    }

    // Iterates through both lhs and rhs linked lists and checks to see 
    // if an element is different. 
    LinkedListNode *curr = head_;
    LinkedListNode *rhs_curr = rhs.head_;
    while (curr != nullptr) {
      if (curr->data != rhs_curr->data) {
        return false;
      }
      curr = curr->next;
      rhs_curr = rhs_curr->next;
    }
    return true;
  }

  template<typename ElementType>
  bool operator!=(const LinkedList<ElementType>& lhs, const LinkedList<ElementType> &rhs) {
    if (lhs.size() != rhs.size()) {
      return true;
    }

    // Iterates through both lhs and rhs linked lists and checks to see 
    // if an element is different. 
    typename LinkedList<ElementType>::const_iterator lhs_it;
    typename LinkedList<ElementType>::const_iterator rhs_it;
    for(lhs_it = lhs.begin(); lhs_it != lhs.end(); ++lhs_it) {
      if (*lhs_it != *rhs_it) {
        return true;
      }
      ++rhs_it;
    }
    return false;
  }

  template<typename ElementType>
  typename LinkedList<ElementType>::iterator& LinkedList<ElementType>::iterator::operator++() {
    current_ = current_->next;
    return *this;
  }

  template<typename ElementType>
  ElementType& LinkedList<ElementType>::iterator::operator*() const {
    return current_->data;
  }

  template<typename ElementType>
  bool LinkedList<ElementType>::iterator::operator!=(const LinkedList<ElementType>::iterator& other) const {
    if (current_ == nullptr && other.current_ != nullptr) {
      return true;
    } else if (current_ != nullptr && other.current_ == nullptr) {
      return true;
    } else if (current_ == nullptr && other.current_ == nullptr) {
      return false;
    }
    return current_ != other.current_;
  }

  template<typename ElementType>
  typename LinkedList<ElementType>::iterator LinkedList<ElementType>::begin() {
    return LinkedList<ElementType>::iterator(head_);
  }

  template<typename ElementType>
  typename LinkedList<ElementType>::iterator LinkedList<ElementType>::end() {
    return LinkedList<ElementType>::iterator(nullptr);
  }

  template<typename ElementType>
  typename LinkedList<ElementType>::const_iterator& LinkedList<ElementType>::const_iterator::operator++() {
    current_ = current_->next;
    return *this;
  }

  template<typename ElementType>
  const ElementType& LinkedList<ElementType>::const_iterator::operator*() const {
    return current_->data;
  }

  template<typename ElementType>
  bool LinkedList<ElementType>::const_iterator::operator!=(const LinkedList<ElementType>::const_iterator& other) const {
    if (current_ == nullptr && other.current_ != nullptr) {
      return true;
    } else if (current_ != nullptr && other.current_ == nullptr) {
      return true;
    } else if (current_ == nullptr && other.current_ == nullptr) {
      return false;
    }
    return current_ != other.current_;
  }

  template<typename ElementType>
  typename LinkedList<ElementType>::const_iterator LinkedList<ElementType>::begin() const {
    return LinkedList<ElementType>::const_iterator(head_);
  }

  template<typename ElementType>
  typename LinkedList<ElementType>::const_iterator LinkedList<ElementType>::end() const {
    return LinkedList<ElementType>::const_iterator(nullptr);
  }
}