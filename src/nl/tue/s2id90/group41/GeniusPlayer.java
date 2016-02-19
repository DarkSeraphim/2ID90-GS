package nl.tue.s2id90.group41;

import nl.tue.s2id90.draughts.DraughtsState;
import nl.tue.s2id90.draughts.player.DraughtsPlayer;
import org10x10.dam.game.Move;

/**
 * A simple draughts player that plays perfect moves
 * @author Group 41
 */
public class GeniusPlayer extends DraughtsPlayer
{
    public GeniusPlayer()
    {
        super(GeniusPlayer.class.getResource("resources/smiley.png"));
    }
    
    @Override
    /** @return an illegal move **/
    public Move getMove(DraughtsState s)
    {
       return null;
    }

    @Override
    public Integer getValue()
    {
        return 0;
    }
}