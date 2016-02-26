package nl.tue.s2id90.group41.players;


import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import nl.tue.s2id90.draughts.DraughtsState;
import nl.tue.s2id90.draughts.player.DraughtsPlayer;
import org10x10.dam.game.Move;

/**
 * A simple draughts player that plays random moves
 * and values all moves with value 0.
 * @author huub
 */
public class UninformedPlayer extends DraughtsPlayer {

    public UninformedPlayer() {
        super(UninformedPlayer.class.getResource("resources/smiley.png"));
    }
    @Override
    /** @return a random move **/
    public Move getMove(DraughtsState s) {
        List<Move> moves = s.getMoves();
        return moves.get(ThreadLocalRandom.current().nextInt(moves.size()));
    }

    @Override
    public Integer getValue() {
        return 0;
    }
}
