import com.diaby.ChessBoard;

public class Main {
    public static void main(String[] args) {
        ChessBoard chess = new ChessBoard();
        chess.printBoard();

        System.out.println(chess.movePiece(1, 0, 2, 0));
        chess.printBoard();
        chess.removePieceAt(2, 0);
        chess.printBoard();
    }
}