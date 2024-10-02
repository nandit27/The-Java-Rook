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

    public boolean movePiece(String currentPosition, String newPosition) {
        // Convert positions like "A2" to board indices
        int currentRow = Character.getNumericValue(currentPosition.charAt(1)) - 1;
        int currentCol = currentPosition.charAt(0) - 'A';
        int newRow = Character.getNumericValue(newPosition.charAt(1)) - 1;
        int newCol = newPosition.charAt(0) - 'A';

        Piece piece = board[currentRow][currentCol];
        if (piece != null && piece.isValidMove(newPosition, board)) {
            // Move piece to new position
            Piece temPiece = board[newRow][newCol];
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

            if(!isKingSafe(piece, newPosition)) {
                board[newRow][newCol] = temPiece;
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
                return false;
            }
            return true;
        } else {
            return false;
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
        isSafeInDiagonals(isOpponentWhite, rowOfKing, colOfKing, blockRow, blockCol)
        && isSafeAlongAxes(isOpponentWhite, rowOfKing, colOfKing, blockRow, blockCol);
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
                return true;
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
                return true;
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
                return true;
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
                return true;
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
                return true;
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
                return true;
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
                return true;
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
                return true;
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