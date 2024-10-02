public class ChessBoard {
    private Piece[][] board;

    public ChessBoard() {
        board = new Piece[8][8];
        initialiseGame();
    }

    public void initialiseGame() {
        // Initialize pieces for both players (White on rows 1-2, Black on rows 7-8)
        
        // White pieces
        board[0][0] = new Rook("A1", true);
        board[0][1] = new Night("B1", true);
        board[0][2] = new Bishop("C1", true);
        board[0][3] = new Queen("D1", true);
        board[0][4] = new King("E1", true);
        board[0][5] = new Bishop("F1", true);
        board[0][6] = new Night("G1", true);
        board[0][7] = new Rook("H1", true);

        // White pawns
        for (int i = 0; i < 8; i++) {
            board[1][i] = new Pawn((char) ('A' + i) + "2", true);
        }

        // Black pieces
        board[7][0] = new Rook("A8", false);
        board[7][1] = new Night("B8", false);
        board[7][2] = new Bishop("C8", false);
        board[7][3] = new Queen("D8", false);
        board[7][4] = new King("E8", false);
        board[7][5] = new Bishop("F8", false);
        board[7][6] = new Night("G8", false);
        board[7][7] = new Rook("H8", false);

        // Black pawns
        for (int i = 0; i < 8; i++) {
            board[6][i] = new Pawn((char) ('A' + i) + "7", false);
        }
    }

    public String movePiece(String currentPosition, String newPosition) {
        // Convert positions like "A2" to board indices
        int currentRow = Character.getNumericValue(currentPosition.charAt(1)) - 1;
        int currentCol = currentPosition.charAt(0) - 'A';
        int newRow = Character.getNumericValue(newPosition.charAt(1)) - 1;
        int newCol = newPosition.charAt(0) - 'A';

        Piece piece = board[currentRow][currentCol];
        if (piece != null && piece.isValidMove(newPosition, board)) {
            // Move piece to new position
            Piece tempPiece = board[newRow][newCol];
            board[newRow][newCol] = piece;
            piece.setPosition(newPosition);
            board[currentRow][currentCol] = null;

            //Update the positionOfKing for all the pieces currently on the board 
            if (piece.getClass().getSimpleName().equals("King")) {
                for (int i = 0; i < 8; i++) {
                    for (int j = 0; j < 8; j++) {
                        if (board[i][j] != null && board[i][j].isWhite == piece.isWhite) {
                            board[i][j].positionOfKing = newPosition;
                        }
                    }
                }
            }

            String gameEndStatus = hasGameEnded(piece);
            if (gameEndStatus.equals("true")) {
                return "White Wins";
            } else if (gameEndStatus.equals("false")) {
                return "Black Wins";
            } else if (gameEndStatus.equals("Stalemate")) {
                return "Stalemate";
            }

            if(!isKingSafe(piece, newPosition)) {
                board[newRow][newCol] = tempPiece;
                piece.setPosition(currentPosition);
                board[currentRow][currentCol] = piece;
                
                if (piece.getClass().getSimpleName().equals("King")) {
                    for (int i = 0; i < 8; i++) {
                        for (int j = 0; j < 8; j++) {
                            if (board[i][j] != null && board[i][j].isWhite == piece.isWhite) {
                                board[i][j].positionOfKing = currentPosition;
                            }
                        }
                    }
                }
                return "Your King is in Check";
            }
            return "Valid Move";
        } else {
            return "Invalid Move";
        }
    }

    public boolean isKingSafe(Piece piece, String blockingPiece) {
        String currentlyPlayingKing = piece.positionOfKing;
        boolean isOpponentWhite = !piece.isWhite();
        int rowOfKing = Character.getNumericValue(currentlyPlayingKing.charAt(1)) - 1;
        int colOfKing = Character.toUpperCase(currentlyPlayingKing.charAt(0)) - 'A';
        int blockRow = Character.getNumericValue(blockingPiece.charAt(1)) - 1;
        int blockCol = Character.toUpperCase(blockingPiece.charAt(0)) - 'A';
        
        //Check for safety from each piece here
        return 
        isSafeFromPawn(isOpponentWhite, rowOfKing, colOfKing)
        && isSafeFromKnight(isOpponentWhite, rowOfKing, colOfKing)
        && isSafeInDiagonals(isOpponentWhite, rowOfKing, colOfKing, blockRow, blockCol)
        && isSafeAlongAxes(isOpponentWhite, rowOfKing, colOfKing, blockRow, blockCol);
    }

    private String hasGameEnded(Piece piece) {
        boolean isCurrentPlayerWhite = piece.isWhite;
        Piece opponentKing = null;
        boolean isOpponentInCheck = false;

        // Find the king of Opponent
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (board[i][j] != null && board[i][j].isWhite != isCurrentPlayerWhite && board[i][j].getClass().getSimpleName() == "King") {
                    opponentKing = board[i][j];
                    break;
                }
            }
            if (opponentKing != null) {
                break;
            }
        }

        isOpponentInCheck = !isKingSafe(opponentKing, opponentKing.getPosition());
        
        // Check for all opponent's pieces to see if any can make a valid move to such that they aren't in check
        for (int k = 0; k < 8; k++) {
            for (int l = 0; l < 8; l++) {
                Piece currentPiece = board[k][l];
                if (currentPiece != null && currentPiece.isWhite != isCurrentPlayerWhite) {
                    String currentPosition = currentPiece.getPosition();
                    // Try all possible moves for this piece
                    for (int newRow = 0; newRow < 8; newRow++) {
                        for (int newCol = 0; newCol < 8; newCol++) {
                            String newPosition = "" + (char) ('A' + newCol) + (newRow + 1);
                            if (currentPiece != null && currentPiece.isValidMove(newPosition, board)) {
                                // Move currentPiece to new position
                                Piece tempPiece = board[newRow][newCol];
                                board[newRow][newCol] = currentPiece;
                                currentPiece.setPosition(newPosition);
                                board[k][l] = null;
                    
                                //Update the positionOfKing for all the pieces currently on the board 
                                if (currentPiece.getClass().getSimpleName().equals("King")) {
                                    for (int i = 0; i < 8; i++) {
                                        for (int j = 0; j < 8; j++) {
                                            if (board[i][j] != null && board[i][j].isWhite == currentPiece.isWhite) {
                                                board[i][j].positionOfKing = newPosition;
                                            }
                                        }
                                    }
                                }
                    
                                boolean stillInCheck = !isKingSafe(currentPiece, newPosition);
                                board[newRow][newCol] = tempPiece;
                                currentPiece.setPosition(currentPosition);
                                board[k][l] = currentPiece;
            
                                if (currentPiece.getClass().getSimpleName().equals("King")) {
                                    for (int i = 0; i < 8; i++) {
                                        for (int j = 0; j < 8; j++) {
                                            if (board[i][j] != null && board[i][j].isWhite == currentPiece.isWhite) {
                                                board[i][j].positionOfKing = currentPosition;
                                            }
                                        }
                                    }
                                }
                                if (!stillInCheck) {
                                    return "No";
                                }
                            }
                        }
                    }
                }
            }
        }

        // If no moves can escape check, Game has Ended.
        return (isOpponentInCheck)? "" + (piece.isWhite) : "Stalemate";
    }
    
    
    private boolean isSafeFromPawn(boolean isOpponentWhite, int rowOfKing, int colOfKing) {
        if (isOpponentWhite) {
            try {
                if (board[rowOfKing - 1][colOfKing - 1] != null) {
                    if (board[rowOfKing - 1][colOfKing - 1].isWhite) {
                        String checkingPiece = board[rowOfKing - 1][colOfKing - 1].getClass().getSimpleName();
                        if (checkingPiece.equals("Pawn")) {
                            return false;
                        }
                    }
                }
            } catch (ArrayIndexOutOfBoundsException e) {}
            try {
                if (board[rowOfKing - 1][colOfKing + 1] != null) {
                    if (board[rowOfKing - 1][colOfKing + 1].isWhite) {
                        String checkingPiece = board[rowOfKing - 1][colOfKing + 1].getClass().getSimpleName();
                        if (checkingPiece.equals("Pawn")) {
                            return false;
                        }
                    }
                }
            } catch (ArrayIndexOutOfBoundsException e) {}
        } else {
            try {
                if (board[rowOfKing + 1][colOfKing - 1] != null) {
                    if (!board[rowOfKing + 1][colOfKing - 1].isWhite) {
                        String checkingPiece = board[rowOfKing + 1][colOfKing - 1].getClass().getSimpleName();
                        if (checkingPiece.equals("Pawn")) {
                            return false;
                        }
                    }
                }
            } catch (ArrayIndexOutOfBoundsException e) {}
            try {
                if (board[rowOfKing + 1][colOfKing + 1] != null) {
                    if (!board[rowOfKing + 1][colOfKing + 1].isWhite) {
                        String checkingPiece = board[rowOfKing + 1][colOfKing + 1].getClass().getSimpleName();
                        if (checkingPiece.equals("Pawn")) {
                            return false;
                        }
                    }
                }
            } catch (ArrayIndexOutOfBoundsException e) {}
        }
        return true;
    }
    
    private boolean isSafeFromKnight(boolean isOpponentWhite, int rowOfKing, int colOfKing) {
        int[] diffs1 = {-1, 1};
        int[] diffs2 = {-2, 2};
        for (int diff : diffs1) {
            for (int diff2 : diffs2) {
                try {
                    if (board[rowOfKing + diff][colOfKing + diff2] != null) {
                        if (board[rowOfKing + diff][colOfKing + diff2].isWhite == isOpponentWhite) {
                            String checkingPiece = board[rowOfKing + diff][colOfKing + diff2].getClass().getSimpleName();
                            if (checkingPiece.equals("Night")) {
                                return false;
                            }
                        }
                    }
                } catch (ArrayIndexOutOfBoundsException e) {
                    continue;
                }
            }
        }
        for (int diff : diffs1) {
            for (int diff2 : diffs2) {
                try {
                    if (board[rowOfKing + diff2][colOfKing + diff] != null) {
                        if (board[rowOfKing + diff2][colOfKing + diff].isWhite == isOpponentWhite) {
                            String checkingPiece = board[rowOfKing + diff2][colOfKing + diff].getClass().getSimpleName();
                            if (checkingPiece.equals("Night")) {
                                return false;
                            }
                        }
                    }
                } catch (ArrayIndexOutOfBoundsException e) {
                    continue;
                }
            }
        }
        return true;
    }

    private boolean isSafeInDiagonals(boolean isOpponentWhite, int rowOfKing, int colOfKing, int blockRow, int blockCol) {
        int row = rowOfKing;
        int col = colOfKing;
        while (row <= 7 && col >= 0) {
            if (row == rowOfKing) {
                row++;
                col--;
                continue;
            }
            if (blockRow == row && blockCol == col) {
                break;
            }
            if (board[row][col] != null) {
                if (board[row][col].isWhite == isOpponentWhite) {
                    String obstructingPiece = board[row][col].getClass().getSimpleName();
                    if (obstructingPiece.equals("Queen") || obstructingPiece.equals("Bishop")) {
                        return false;
                    }
                }
                break;
            }
            row++;
            col--;
        }
        
        row = rowOfKing;
        col = colOfKing;
        while (row <= 7 && col <= 7) {
            if (row == rowOfKing) {
                row++;
                col++;
                continue;
            }
            if (blockRow == row && blockCol == col) {
                break;
            }
            if (board[row][col] != null) {
                if (board[row][col].isWhite == isOpponentWhite) {
                    String obstructingPiece = board[row][col].getClass().getSimpleName();
                    if (obstructingPiece.equals("Queen") || obstructingPiece.equals("Bishop")) {
                        return false;
                    }
                }
                break;
            }
            row++;
            col++;
        }

        row = rowOfKing;
        col = colOfKing;
        while (row >= 0 && col <= 7) {
            if (row == rowOfKing) {
                row--;
                col++;
                continue;
            }
            if (blockRow == row && blockCol == col) {
                break;
            }
            if (board[row][col] != null) {
                if (board[row][col].isWhite == isOpponentWhite) {
                    String obstructingPiece = board[row][col].getClass().getSimpleName();
                    if (obstructingPiece.equals("Queen") || obstructingPiece.equals("Bishop")) {
                        return false;
                    }
                }
                break;
            }
            row--;
            col++;
        }

        row = rowOfKing;
        col = colOfKing;
        while (row >= 0 && col >= 0) {
            if (row == rowOfKing) {
                row--;
                col--;
                continue;
            }
            if (blockRow == row && blockCol == col) {
                break;
            }
            if (board[row][col] != null) {
                if (board[row][col].isWhite == isOpponentWhite) {
                    String obstructingPiece = board[row][col].getClass().getSimpleName();
                    if (obstructingPiece.equals("Queen") || obstructingPiece.equals("Bishop")) {
                        return false;
                    }
                }
                break;
            }
            row--;
            col--;
        }
        return true;
    }
    private boolean isSafeAlongAxes(boolean isOpponentWhite, int rowOfKing, int colOfKing, int blockRow, int blockCol) {
        int row = rowOfKing;
        int col = colOfKing;
        while (col >= 0) {
            if (col == colOfKing) {
                col--;
                continue;
            }
            if (blockRow == row && blockCol == col) {
                break;
            }
            if (board[row][col] != null) {
                if (board[row][col].isWhite == isOpponentWhite) {
                    String obstructingPiece = board[row][col].getClass().getSimpleName();
                    if (obstructingPiece.equals("Queen") || obstructingPiece.equals("Rook")) {
                        return false;
                    }
                }
                break;
            }
            col--;
        }
        
        col = colOfKing;
        while (col <= 7) {
            if (col == colOfKing) {
                col++;
                continue;
            }
            if (blockRow == row && blockCol == col) {
                break;
            }
            if (board[row][col] != null) {
                if (board[row][col].isWhite == isOpponentWhite) {
                    String obstructingPiece = board[row][col].getClass().getSimpleName();
                    if (obstructingPiece.equals("Queen") || obstructingPiece.equals("Rook")) {
                        return false;
                    }
                }
                break;
            }
            col++;
        }

        col = colOfKing;
        while (row >= 0) {
            if (row == rowOfKing) {
                row--;
                continue;
            }
            if (blockRow == row && blockCol == col) {
                break;
            }
            if (board[row][col] != null) {
                if (board[row][col].isWhite == isOpponentWhite) {
                    String obstructingPiece = board[row][col].getClass().getSimpleName();
                    if (obstructingPiece.equals("Queen") || obstructingPiece.equals("Rook")) {
                        return false;
                    }
                }
                break;
            }
            row--;
        }

        row = rowOfKing;
        while (row <= 7) {
            if (row == rowOfKing) {
                row++;
                continue;
            }
            if (blockRow == row && blockCol == col) {
                break;
            }
            if (board[row][col] != null) {
                if (board[row][col].isWhite == isOpponentWhite) {
                    String obstructingPiece = board[row][col].getClass().getSimpleName();
                    if (obstructingPiece.equals("Queen") || obstructingPiece.equals("Rook")) {
                        return false;
                    }
                }
                break;
            }
            row++;
        }
        return true;
    }

    public Piece getPieceAt(String position) {
        int row = Character.getNumericValue(position.charAt(1)) - 1;
        int col = position.charAt(0) - 'A';
        return board[row][col];
    }
}