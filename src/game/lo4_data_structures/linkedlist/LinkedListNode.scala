package game.lo4_data_structures.linkedlist

import game.maps.GridLocation

import scala.annotation.tailrec

// Simple Linked List implementation (note: Cannot represent empty list)
class LinkedListNode[A](var value: A, var next: LinkedListNode[A]) {


  /**
   * returns the number of nodes in the list starting with this node
   */
  @tailrec
  final def sizeTailRec(accumulatedSize: Int): Int = {
    if (this.next == null) {
      accumulatedSize + 1
    } else {
      this.next.sizeTailRec(accumulatedSize + 1)
    }
  }

  def find(toFind : A): LinkedListNode[A] = {
    if (this.value == toFind){
      this
    } else if (this.next==null){
      null
    }else{
      this.next.find(toFind)
    }

  }

  def foreach(f : A => Unit) : Unit ={
    f(this.value)
    if(this.next != null){
      this.next.foreach(f)
    }
  }


  def size(): Int = {
    sizeTailRec(0)
  }


  override def toString: String = {
    if (this.next == null) {
      this.value.toString
    } else {
      this.value.toString + ", " + this.next.toString
    }
  }

  def apply(i:Int) : LinkedListNode[A] = {
    if (i==0){
      this
    }
    else {
      this.next.apply(i-1)
    }
  }


}
