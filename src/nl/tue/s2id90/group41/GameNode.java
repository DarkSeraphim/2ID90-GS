package nl.tue.s2id90.group41;

import nl.tue.s2id90.game.GameState;
import org10x10.dam.game.Move;

/**
 *
 * @author s129977
 */
public class GameNode {
    
    private Move bestMove;
    
    private final GameState<Move> gameState;
    
    public GameNode(GameState<Move> gameState) {
        this.gameState = gameState;
    }
    
    public int getRating(boolean isMe) {
        // TODO: apply Koen's evaluation
        return 0;
    }
    
    public GameState<Move> getGameState() {
        return this.gameState;
    }
    
    public void setBestMove(Move move) {
        this.bestMove = move;
    }
    
    public Move getBestMove() {
        return this.bestMove;
    }
}
