package tests

import game.enemyai.{AIPlayer, PlayerLocation}
import game.lo4_data_structures.linkedlist.LinkedListNode
import game.maps.GridLocation
import org.scalatest._

class Task1 extends FunSuite {


  test("test locatePlayer") {
    val player : AIPlayer = new AIPlayer("player1")
    var list : LinkedListNode[PlayerLocation] = new LinkedListNode[PlayerLocation](
      new PlayerLocation(3.0,4.0,"player2"),null
    )
    list = new LinkedListNode[PlayerLocation](
      new PlayerLocation(1.0,8.0,"player1"),list
    )

    //TODO : compare doubles to check for 3.0 and 4.0
    val actual : PlayerLocation = player.locatePlayer("player2",list)
    assert(actual.playerId == "player2")
    compareDoubles(actual.x,3.0)
    compareDoubles(actual.y,4.0)

    //TODO: Do the same thing for player1.
    val player1 : PlayerLocation = player.locatePlayer("player1",list)
    assert(player1.playerId == "player1")
    compareDoubles(player1.x,1.0)
    compareDoubles(player1.y,8.0)
  }

  test ("test ComputePath") {
    val player : AIPlayer = new AIPlayer("player1")

    var list : LinkedListNode[GridLocation] = player.computePath(
      new GridLocation(2,2),
      new GridLocation(4,4)
    )

    assert(list.value == new GridLocation(2,2))
    assert(list.size == 5)


    //TODO: Check if path starts with 2,2
    //TODO: Check if path ends with 4,4
    //TODO: check if length of the path is minimal
    //TODO: check if all moves are valid (up,down,left,right).
  }

  test ("test closestPlayer") {
    val player : AIPlayer = new AIPlayer("player1")
    var list : LinkedListNode[PlayerLocation] = new LinkedListNode[PlayerLocation](
      new PlayerLocation(1.0,7.0,"player2"),null
    )
    list = new LinkedListNode[PlayerLocation](
      new PlayerLocation(1.0,8.0,"player1"),list
    )
    list = new LinkedListNode[PlayerLocation](
      new PlayerLocation(3.0,4.0,"player3"),list
    )


    val actual : PlayerLocation = player.closestPlayer(list)
    compareDoubles(actual.x,1.0)
    compareDoubles(actual.y,7.0)
    assert(actual.playerId == "player2")
  }

  test ("negative path") {
    val player: AIPlayer = new AIPlayer("player1")

    var list: LinkedListNode[GridLocation] = player.computePath(
      new GridLocation(4, 4),
      new GridLocation(2, 2)
    )


    assert(list.value == new GridLocation(4, 4))
    assert(list.size == 5)
  }

  def compareDoubles(val1 : Double, val2 : Double) : Unit ={
    assert(Math.abs(val1-val2)<0.001)
  }


}
