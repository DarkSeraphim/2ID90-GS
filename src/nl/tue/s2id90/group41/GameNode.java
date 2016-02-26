package nl.tue.s2id90.group41;

import nl.tue.s2id90.draughts.DraughtsState;
import nl.tue.s2id90.game.GameState;
import org10x10.dam.game.Move;

/**
 *
 * @author s129977
 */
public class GameNode {
    
    private final GeniusPlayer player;
    
    private final GameState<Move> gameState;
    
    private Move bestMove;
    
    public GameNode(GeniusPlayer player, GameState<Move> gameState) {
        this.player = player;
        this.gameState = gameState;
    }
    
    public int getRating() {
        return (int) this.player.evaluateBoard((DraughtsState) this.gameState, null);
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
