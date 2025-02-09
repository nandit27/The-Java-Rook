# The Java Rook ♜

A fully-featured chess game implemented in Java, offering a classic two-player offline experience with complete chess rules and mechanics.

## Features

- Complete chess piece movement validation
- Turn-based gameplay system
- Checkmate detection
- Special moves implementation:
  - Pawn promotion
  - Castling
  - En-passant capture
- Chess.com inspired GUI interface built with Java Swing
- Object-Oriented Programming architecture

## Requirements

- Java Development Kit (JDK) installed on your system
- Java Runtime Environment (JRE)

## Installation

1. Clone this repository or download all files
2. Keep the file structure intact, ensuring the `images` folder and all `.java` files remain in their original locations
3. Open a terminal or command prompt in the project directory

## How to Run

1. Compile the program:
```bash
javac Main.java
```

2. Run the compiled program:
```bash
java Main
```

## Project Structure

- `Main.java` - Entry point of the application
- `ChessBoard.java` - Handles the chess board logic
- `ChessUI.java` - Contains the user interface implementation
- `Piece.java` - Base class for chess pieces
- Individual piece classes:
  - `Bishop.java`
  - `King.java`
  - `Knight.java`
  - `Pawn.java`
  - `Queen.java`
  - `Rook.java`

## How to Play

1. Launch the game
2. White pieces move first
3. Click on a piece to select it
4. Click on a valid destination square to move
5. Game continues until checkmate or stalemate

## Technical Implementation

- Built using Object-Oriented Programming principles
- Java Swing for graphical user interface
- Modular design with separate classes for pieces and game logic
- UI design inspired by Chess.com

## Contributing

Feel free to fork this project and submit pull requests for any improvements you'd like to add. Please make sure to test your changes thoroughly before submitting.

---

Created by Prem Kotadiya & Nandit Kalaria. 

For questions or issues, please open an issue in the GitHub repository.
