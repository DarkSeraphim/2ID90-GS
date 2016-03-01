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
    
    protected double evaluateBoard(DraughtsState s)
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
                if ((isWhite && piece % 2 == 0) || (!isWhite && piece % 2 == 1))
                {
                    pieceValue *= -1.0;
                }
                
                //Normal piece
                if (piece < 3)
                {
                    if ((piece % 2 == 1 && tile > 45) || (piece % 2 == 0 && tile < 6))
                    {
                        pieceValue *= 5.0;
                    }
                    else
                    {
                        if (piece % 2 == 1)
                        {
                            pieceValue *= ((((50 - tile) - ((50 - tile) % 5)) * 5) + 75) / 100;
                        }
                        else
                        {
                            pieceValue *= ((((tile - 1) - ((tile - 1) % 5)) * 5) + 75) / 100;
                        }
                    }
                }

                //King
                else
                {
                    pieceValue *= 25.0;
                }
                
                //Defensive
                int row = (tile - 1) / 5;
                int column = -1;
                if (row % 2 == 0)
                {
                    column = 2 * (tile % 10) - 1;
                }
                else
                {
                    column = 2 * ((tile - 6) % 10);
                }
                boolean[] defended = new boolean[4];
                for (int i = 0; i < 4; i++)
                {
                    defended[i] = true;
                }
                if (row > 0 && column > 0)
                {
                    int otherPiece = s.getPiece(row - 1, column - 1);
                    if (otherPiece == 0 || (otherPiece != 0 && otherPiece % 2 != piece % 2))
                    {
                        defended[0] = false;
                    }
                }
                if (row > 0 && column < 9)
                {
                    int otherPiece = s.getPiece(row - 1, column + 1);
                    if (otherPiece == 0 || (otherPiece != 0 && otherPiece % 2 != piece % 2))
                    {
                        defended[1] = false;
                    }
                }
                if (row < 9 && column > 0)
                {
                    int otherPiece = s.getPiece(row + 1, column - 1);
                    if (otherPiece == 0 || (otherPiece != 0 && otherPiece % 2 != piece % 2))
                    {
                        defended[2] = false;
                    }
                }
                if (row < 9 && column < 9)
                {
                    int otherPiece = s.getPiece(row + 1, column + 1);
                    if (otherPiece == 0 || (otherPiece != 0 && otherPiece % 2 != piece % 2))
                    {
                        defended[3] = false;
                    }
                }
                if (defended[0] || defended[3])
                {
                    pieceValue *= 2.0;
                }
                if (defended[1] || defended[2])
                {
                    pieceValue *= 2.0;
                }
                
                //return
                totalValue += pieceValue;
            }
        }
        return totalValue;
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