package game.enemyai

import game.enemyai.decisiontree.DecisionTreeValue
import game.lo4_data_structures.graphs.Graph
import game.lo4_data_structures.linkedlist.LinkedListNode
import game.lo4_data_structures.trees.BinaryTreeNode
import game.maps.GridLocation
import game.{AIAction, MovePlayer}
import scala.collection.mutable.Queue

class AIPlayer(val id: String) {

  // TODO: Replace this placeholder code with your own
  def locatePlayer(playerId: String, playerLocations: LinkedListNode[PlayerLocation]): PlayerLocation = {
    if (playerLocations.value.playerId == playerId) {
      playerLocations.value
    }
    else {
      locatePlayer(playerId, playerLocations.next)
    }
  }

  // TODO: Replace this placeholder code with your own
  def closestPlayer(playerLocations: LinkedListNode[PlayerLocation]): PlayerLocation = {
    val thisPlayer: PlayerLocation = locatePlayer(this.id, playerLocations)
    var closestDistance: Double = 100000.0
    var closestPlayer: PlayerLocation = thisPlayer
    for (node <- playerLocations) {
      if (node.playerId != this.id) {
        val distance = Math.sqrt(Math.pow(node.x - thisPlayer.x, 2) + Math.pow(node.y - thisPlayer.y, 2))
        if (distance < closestDistance) {
          closestDistance = distance
          closestPlayer = node
        }
      }
    }
    closestPlayer
  }


  // TODO: Replace this placeholder code with your own
  def computePath(start: GridLocation, end: GridLocation): LinkedListNode[GridLocation] = {
    var myList: LinkedListNode[GridLocation] = new LinkedListNode[GridLocation](end, null)
    val diffx: Int = end.x - start.x
    val diffy: Int = end.y - start.y
    var x: Int = end.x
    var y: Int = end.y
    for (i <- 1 to Math.abs(diffx)) {
      if (diffx < 0) {
        myList = new LinkedListNode[GridLocation](new GridLocation(x + 1, y), myList)
        x = x + 1
      }
      else {
        myList = new LinkedListNode[GridLocation](new GridLocation(x - 1, y), myList)
        x = x - 1
      }
    }
    for (i <- 1 to Math.abs(diffy)) {
      if (diffy < 0) {
        myList = new LinkedListNode[GridLocation](new GridLocation(x, y + 1), myList)
        y = y + 1
      }
      else {
        myList = new LinkedListNode[GridLocation](new GridLocation(x, y - 1), myList)
        y = y - 1
      }
    }
    myList
  }


  // TODO: Replace this placeholder code with your own
  def makeDecision(gameState: AIGameState, decisionTree: BinaryTreeNode[DecisionTreeValue]): AIAction = {
    if (decisionTree.value.check(gameState) < 0) {
      makeDecision(gameState, decisionTree.left)
    }
    else if (decisionTree.value.check(gameState) > 0) {
      makeDecision(gameState, decisionTree.right)
    }
    else {
      decisionTree.value.action(gameState)
    }

  }

  def distanceAvoidWalls(state: AIGameState, location1: GridLocation, location2: GridLocation): Int = {
    //location1 is starting location
    //location2 is ending location for this function
    val graph: Graph[GridLocation] = state.levelAsGraph()
    val location1ID: Int = (location1.y * state.levelWidth) + location1.x
    val location2ID: Int = (location2.y * state.levelWidth) + location2.x

    var exploredMap: Map[Int, Int] = Map(location1ID -> 0)
    val queue = new Queue[Int]()
    var explored: Set[Int] = Set(location1ID)
    queue.enqueue(location1ID)

    while (queue.nonEmpty) {
      val nodeToExplore : Int = queue.dequeue()
      for (node <- graph.adjacencyList(nodeToExplore)) {
        if (!explored.contains(node) && !queue.contains(node)) {
          queue.enqueue(node)
        }
        if (exploredMap.contains(node)) {
          if (!explored.contains(nodeToExplore)) {
            explored += node
            exploredMap += (nodeToExplore -> (exploredMap(node) + 1))
          }
        }
      }

    }
    println(exploredMap)
    exploredMap.getOrElse(location2ID, 0)
  }


  // TODO: Replace this placeholder code with your own
  def closestPlayerAvoidWalls(gameState: AIGameState): PlayerLocation = {
    var playerLocations = gameState.playerLocations
    var thisPlayer = locatePlayer(this.id,playerLocations)
    var distance : Int = 1200
    var closestPlayer : PlayerLocation = new PlayerLocation(0,0,"null")
    for (location <- playerLocations){
      if (location.playerId != this.id){
        val dist = distanceAvoidWalls(gameState,thisPlayer.asGridLocation(),location.asGridLocation())
        if (dist < distance){
          distance = dist
          closestPlayer = location
        }
      }
    }
    closestPlayer
  }

  // TODO: Replace this placeholder code with your own
  def getPath(gameState: AIGameState): LinkedListNode[GridLocation] = {
    computePath(locatePlayer(this.id, gameState.playerLocations).asGridLocation(), closestPlayerAvoidWalls(gameState).asGridLocation())
  }

}

