package tictactoe

import kotlin.math.abs


fun createBoard(gameBoard: MutableList<MutableList<Char>>) {
    println("---------")
    for (i in 0..2) {
        println("| ${gameBoard[i][0]} ${gameBoard[i][1]} ${gameBoard[i][2]} |")
    }
    println("---------")
}


fun toGameBoard(board: String):MutableList<MutableList<Char>> {
    return mutableListOf(
            mutableListOf(board[0], board[1], board[2]),
            mutableListOf(board[3], board[4], board[5]),
            mutableListOf(board[6], board[7], board[8])
    )
}


fun countWins(gameField:MutableList<MutableList<Char>>):MutableList<Int> {
    var counterColumns = 0
    var counterRows = 0
    var counterDiagonal = 0
    var counterDiagonalRev = 0
    var winX = 0
    var winO = 0
    for (column in 0..2) {
        for (row in 0..2) {
            if (gameField[row][column] == 'X') counterColumns += 1
            else if (gameField[row][column] == 'O') counterColumns -= 1

            if (gameField[column][row] == 'X') counterRows += 1
            else if (gameField[column][row] == 'O') counterRows -= 1
        }
        if (counterColumns == -3) winO += 1
        else if (counterColumns == 3) winX += 1
        counterColumns = 0

        if (counterRows == -3) winO += 1
        else if (counterRows == 3) winX += 1
        counterRows = 0

        if (gameField[column][column] == 'X') counterDiagonal +=1
        else if (gameField[column][column] == 'O') counterDiagonal -=1

        if (gameField[column][2 - column] == 'X') counterDiagonalRev +=1
        else if (gameField[column][2 - column] == 'O') counterDiagonalRev -=1
    }
    if (counterDiagonal == -3) winO += 1
    else if (counterDiagonal == 3) winX += 1

    if (counterDiagonalRev == -3) winO += 1
    else if (counterDiagonalRev == 3) winX += 1

    return mutableListOf(winO, winX)
}


fun validate(gameBoard: MutableList<MutableList<Char>>, result: MutableList<Int>):Boolean {
    var continueGame = false
    var countO = 0
    var countX = 0
    for (row in gameBoard.indices) {
        for (character in gameBoard[row]) {
            if (character == 'X') countX += 1
            else if (character == 'O') countO += 1
        }
    }

    if ((result[0] > 0 && result[1] > 0) || abs(countX - countO) > 1) {
        println("Impossible")
        continueGame = false
    }
    else if (result.sum() == 0 &&
        (" " in gameBoard.joinToString().replace("[", "").replace("]", "").replace(",", "") ||
                "_" in gameBoard.joinToString().replace("[", "").replace("]", "").replace(",", ""))) {
//        println("Game not finished")
        continueGame = true
    }
    else if (result.sum() == 0) {
        println("Draw")
        continueGame = false
    }
    else if (result[0] == 1) {
        println("O wins")
        continueGame = false
    }
    else if (result[1] == 1) {
        println("X wins")
        continueGame = false
    }
    return continueGame
}


fun makeAMove(gameBoard:MutableList<MutableList<Char>>, player:Boolean): MutableList<MutableList<Char>> {
    var validMove = false
    while (!validMove) {
        println("Enter the coordinates: ")
        val coordinates = readln().split(" ").toMutableList()
        if (coordinates[0].toIntOrNull() == null || coordinates[1].toIntOrNull() == null) {
            println("You should enter numbers!")
        } else if (coordinates[0].toInt() in 1..3 && coordinates[1].toInt() in 1..3){
            if (gameBoard[coordinates[0].toInt() - 1][coordinates[1].toInt() - 1] == ' ' ||
                gameBoard[coordinates[0].toInt() - 1][coordinates[1].toInt() - 1] == '_') {
                when (player) {
                    true -> gameBoard[coordinates[0].toInt() - 1][coordinates[1].toInt() - 1] = 'X'
                    false -> gameBoard[coordinates[0].toInt() - 1][coordinates[1].toInt() - 1] = 'O'
                }
                validMove = true
            } else {
                println("This cell is occupied! Choose another one!")
            }
        } else {
            println("Coordinates should be from 1 to 3!")
        }
    }
    return gameBoard
}


fun main() {
    var moves = 0
    var running = true
    var player = true
    var result:MutableList<Int>
    var gameBoard = toGameBoard("_________")
    createBoard(gameBoard)

    while (running) {
        gameBoard = makeAMove(gameBoard, player)
        createBoard(gameBoard)

        result = countWins(gameBoard)

        running = validate(gameBoard, result)
        player = !player
        moves += 1

        if (moves == 9) {
            println("Draw")
            break
        }
    }

}
