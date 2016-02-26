package nl.tue.s2id90.group41;

import java.util.concurrent.CancellationException;
import nl.tue.s2id90.draughts.DraughtsState;
import nl.tue.s2id90.game.GameState;
import org10x10.dam.game.Move;

/**
 *
 */
public class AlphaBeta {
    
    private static final int MAX_DEPTH = 5;
    
    private boolean stopped;
    
    public void stop() {
        this.stopped = true;
    }
    
    private void checkStop() {
        if (this.stopped) {
            throw new CancellationException("AI was stopped");
        }
    }
    
    public int search (GameNode node) {
        node.setBestMove(null);
        return max(node, 0, Integer.MIN_VALUE, Integer.MAX_VALUE);
    }
    
    private int max(GameNode node, int depth, int alpha, int beta) {
        checkStop();
        if (++depth == MAX_DEPTH) {
            return node.getRating();
        }
                
        Move bestMove = null;
        int bestAlpha = alpha;
        GameState<Move> state = node.getGameState();
        for (Move move : state.getMoves()) {
            state.doMove(move);
            alpha = Math.max(alpha, min(node, depth, alpha, beta));
            if (alpha > bestAlpha) {
                bestAlpha = alpha;
                bestMove = move;
            }
            state.undoMove(move);
        }
        if (bestMove != null) {
            node.setBestMove(bestMove);
        }
        
//        if (alpha >= beta) {
//            return beta;
//        }
//        return alpha;
        return Math.min(alpha, beta);
    }
    
    private int min(GameNode node, int depth, int alpha, int beta) {
        checkStop();
        if (++depth == MAX_DEPTH) {
            return node.getRating();
        }
                
        GameState<Move> state = node.getGameState();
        for (Move move : state.getMoves()) {
            state.doMove(move);
            beta = Math.min(beta, max(node, depth, alpha, beta));
            state.undoMove(move);
        }
        
//        if( beta <= alpha) {
//            return alpha;
//        }
//        return beta;
        return Math.max(alpha, beta);
    }
}
