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
    
    private int blackKings;
    
    private int blackPieces;
    
    private int whiteKings;
    
    private int whitePieces;
    
    public GeniusPlayer()
    {
        super(GeniusPlayer.class.getResource("resources/genius.png"));
        this.search = new AlphaBeta();
    }
    
    private void countPieces(DraughtsState ds) {
        this.blackKings = 0;
        this.blackPieces = 0;
        this.whiteKings = 0;
        this.whitePieces = 0;
        for (int piece : ds.getPieces()) {
            switch (piece) {
                case DraughtsState.BLACKKING:
                    this.blackKings++;
                case DraughtsState.BLACKPIECE:
                    blackPieces++;
                    break;
                case DraughtsState.WHITEKING:
                    this.whiteKings++;
                case DraughtsState.WHITEFIELD:
                    whitePieces++;
                    break;
            }
        }
    }
    
    protected double evaluateBoard(DraughtsState s)
    {   
        double totalValue = 0.0;
        int black = 0, white = 0;
        for (int tile = 1; tile <= 50; tile++)
        {
            int piece = s.getPiece(tile);
            if (piece == 0)
            {
                // If there was no piece, skip it
                continue;
            }
            
            if (piece % 2 == 0) 
            {
                black++;
            } 
            else 
            {
                white++;
            }
            
            double pieceValue = 1.0;
            // If this was our enemy (we're white, they're black, or the other way around)
            // Negate the value
            if ((isWhite && piece % 2 == 0) || (!isWhite && piece % 2 == 1))
            {
                pieceValue *= -1.0;
            }

            //Normal piece
            if (piece < 3)
            {
                if ((piece % 2 == 1 && tile > 45) || (piece % 2 == 0 && tile < 6))
                {
                    // Double the value if it's on the last row
                    pieceValue *= 2.0;
                }
                else
                {
                    if (piece % 2 == 1)
                    {
                        // pieceValue *= ((5 - (tile / 10)) * 5 + 75) / 100;
                        //pieceValue *= ((((50 - tile) - ((50 - tile) % 5)) * 5) + 75) / 100;
                    }
                    else
                    {
                        // pieceValue *= (((tile / 10)) * 5 + 75) / 100;
                        // pieceValue *= ((((tile - 1) - ((tile - 1) % 5)) * 5) + 75) / 100;
                    }
                }
            }
            else
            {
                // If the piece was a king
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
            // Check if any of the pieces defends the other
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
        
        // Check whether we / the opponent lost pieces
        double x = 0;
        double y = 0;
        double piecesBefore = this.isWhite ? this.whitePieces : this.blackPieces;
        double enemyPiecesBefore = !this.isWhite ? this.whitePieces : this.blackPieces;
        if (piecesBefore > 0) 
        {
            int piecesNow = this.isWhite ? white : black;
            x = ((piecesBefore - piecesNow) / piecesBefore);
        }
        
        // We like this!
        if (enemyPiecesBefore > 0)
        {
            int enemyPiecesNow = !this.isWhite ? white : black;
            y = ((enemyPiecesBefore - enemyPiecesNow) / enemyPiecesBefore) * 2;
        }
        return (1 - x) * (y + 1) * totalValue;
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