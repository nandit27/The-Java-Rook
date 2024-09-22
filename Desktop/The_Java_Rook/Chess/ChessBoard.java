package Chess;

public class ChessBoard {
    private Piece[][] board;

    public ChessBoard(){
        board =  new Piece[8][8];
        initialiseGame();
    }

    public void initialiseGame(){
        //Place pieces on the board for both players

        board[0][0]=new Rook("A1",true);
        board[0][1]=new Knight("B1",true);

        //Continue initialise the board for all placesC
    }

    public void printBoard(){
        for(int i=0;i<8;i++){
            for(int j=0;j<8;j++){
                if(board[i][j]!=null){
                    Piece piece = board[i][j];
                    char pieceChar = piece.isWhite() ? Character.toUpperCase(piece.getClass().getSimpleName().charAt(0)) 
                                                     : Character.toLowerCase(piece.getClass().getSimpleName().charAt(0));
                    System.out.print(pieceChar + " ");
                }
                else{
                    System.out.print("* ");
                }
            }
            System.out.println();
        }
    }

    public void movePiece(String CurrentPosition, String newPosition){
        //Implement logic to move a piece on the board 
        //Update the board state after the move
    }
}
