package nl.tue.s2id90.group41;

import java.util.List;
import nl.tue.s2id90.draughts.DraughtsState;
import nl.tue.s2id90.draughts.player.DraughtsPlayer;
import org10x10.dam.game.Move;

/**
 * A genius draughts player that plays perfect moves
 * @author Group 41
 */
public class GeniusPlayer extends DraughtsPlayer
{    
    public GeniusPlayer()
    {
        super(GeniusPlayer.class.getResource("resources/genius.png"));
    }
    
    private double evaluateBoard(DraughtsState s)
    {
        double totalValue = 0.0;
        for (int tile = 1; tile <= 50; tile++)
        {
            double pieceValue = 0.0;
            int piece = s.getPiece(tile);
            
            //Calculate piece value
            if (piece != 0)
            {
                pieceValue = 1.0;
                
                //White
                if (piece % 2 != 0)
                {
                    //Normal piece
                    if (piece < 3)
                    {
                        if (tile > 45)
                        {
                            pieceValue *= 3.0;
                        }
                        else
                        {
                            pieceValue *= ((((50 - tile) - ((50 - tile) % 5)) * 5) + 75) / 100;
                        }
                    }
                    
                    //King
                    else
                    {
                        pieceValue *= 5.0;
                    }
                }
                
                //Black
                else
                {
                    pieceValue *= -1;
                    
                    //Normal piece
                    if (piece < 3)
                    {
                        if (tile < 6)
                        {
                            pieceValue *= 3.0;
                        }
                        else
                        {
                            pieceValue *= ((((tile - 1) - ((tile - 1) % 5)) * 5) + 75) / 100;
                        }
                    }
                    
                    //King
                    else
                    {
                        pieceValue *= 5.0;
                    }
                }
            }
            totalValue += pieceValue;
        }
        return totalValue;
    }
    
    @Override
    /** @return the best move **/
    public Move getMove(DraughtsState s)
    {
        List<Move> moves = s.getMoves();
        return null;
    }

    @Override
    public Integer getValue()
    {
        return 0;
    }
}