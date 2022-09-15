package tests

import game.enemyai.{AIGameState, AIPlayer, PlayerLocation}
import game.lo4_data_structures.linkedlist.LinkedListNode
import game.maps.GridLocation
import org.scalatest._

class ApplicationObjective extends FunSuite {
  def makeGameState(): AIGameState = {
    val gameState: AIGameState = new AIGameState
    gameState.levelWidth = 10
    gameState.levelHeight = 8

    gameState.wallLocations = List(
      new GridLocation(3, 1),
      new GridLocation(3, 2),
      new GridLocation(3, 3),
      new GridLocation(3, 4),
      new GridLocation(3, 5),
      new GridLocation(4, 1),
      new GridLocation(5, 1),
      new GridLocation(6, 1),
      new GridLocation(6, 2),
      new GridLocation(6, 3),
      new GridLocation(6, 4),
      new GridLocation(6, 5)
    )

    gameState.playerLocations = new LinkedListNode[PlayerLocation](new PlayerLocation(5.6, 3.4, "1"), null)
    gameState.playerLocations = new LinkedListNode[PlayerLocation](new PlayerLocation(7,5,"2"), gameState.playerLocations)
    gameState.playerLocations = new LinkedListNode[PlayerLocation](new PlayerLocation(7,8,"3"), gameState.playerLocations)
    gameState.playerLocations = new LinkedListNode[PlayerLocation](new PlayerLocation(2,3,"4"), gameState.playerLocations)
    gameState
  }

  test("test1") {
    val gameState = makeGameState()
    val player: AIPlayer = new AIPlayer("1")
    val result: PlayerLocation = player.closestPlayerAvoidWalls(
      gameState
    )
    assert(result.playerId == "3")
  }

  test("test2") {
    val gameState = makeGameState()
    val player: AIPlayer = new AIPlayer("2")
    val result: PlayerLocation = player.closestPlayerAvoidWalls(
      gameState
    )
    assert(result.playerId == "3")
  }

  test("test3") {
    val gameState = makeGameState()
    val player: AIPlayer = new AIPlayer("3")
    val result: PlayerLocation = player.closestPlayerAvoidWalls(
      gameState
    )
    assert(result.playerId == "2")
  }

  test("test4") {
    val gameState = makeGameState()
    val player: AIPlayer = new AIPlayer("4")
    val result: PlayerLocation = player.closestPlayerAvoidWalls(
      gameState
    )
    assert(result.playerId == "3")
  }
}
