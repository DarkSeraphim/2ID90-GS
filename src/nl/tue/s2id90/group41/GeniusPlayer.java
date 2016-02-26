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
    private final AlphaBeta search;
    
    private int value;
    
    private boolean isWhite;
    
    private int blackPieces;
    
    private int whitePieces;
    
    public GeniusPlayer()
    {
        super(GeniusPlayer.class.getResource("resources/genius.png"));
        this.search = new AlphaBeta();
    }
    
    private void countPieces(DraughtsState ds) {
        this.blackPieces = 0;
        this.whitePieces = 0;
        for (int piece : ds.getPieces()) {
            switch (piece) {
                case DraughtsState.BLACKPIECE:
                case DraughtsState.BLACKKING:
                    blackPieces++;
                    break;
                case DraughtsState.WHITEFIELD:
                case DraughtsState.WHITEKING:
                    whitePieces++;
                    break;
            }
        }
    }
    
    protected double evaluateBoard(DraughtsState s, Move move)
    {   
        double totalValue = 0.0;
        int b = 0, w = 0;
        for (int tile = 1; tile <= 50; tile++)
        {
            double pieceValue;
            int piece = s.getPiece(tile);
            
            //Calculate piece value
            if (piece != 0)
            {
                continue;
            }
            
            if (piece % 2 != 0) 
            {
                w++;
            }
            else
            {
                b++;
            }
            
            pieceValue = 1.0;

            //White
            if ((piece % 2 != 0) == this.isWhite)
            {
                //Normal piece
                if (piece < 3)
                {
                    if ((this.isWhite && tile > 45) || (!this.isWhite && tile < 6))
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
            /*else
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
            }*/
            totalValue += pieceValue;
        }
        double x = 1 - (this.isWhite ? (this.whitePieces - w) / this.whitePieces : (this.blackPieces - b) / this.blackPieces);
        return totalValue * x;
    }
    
    @Override
    /** @return the best move **/
    public Move getMove(DraughtsState s)
    {
        this.isWhite = s.isWhiteToMove();
        List<Move> moves = s.getMoves();
        GameNode node = new GameNode(this, s);
        countPieces(s);
        this.value = this.search.search(node);
        return node.getBestMove();
    }

    @Override
    public Integer getValue()
    {
        return this.value;
    }
}