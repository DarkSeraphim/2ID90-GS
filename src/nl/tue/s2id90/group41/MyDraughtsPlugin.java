package nl.tue.s2id90.group41;

import nl.tue.s2id90.group41.players.OptimisticPlayer;
import nl.tue.s2id90.group41.players.StupidPlayer;
import nl.tue.s2id90.group41.players.UninformedPlayer;
import net.xeoh.plugins.base.annotations.PluginImplementation;
import nl.tue.s2id90.draughts.DraughtsPlayerProvider;
import nl.tue.s2id90.draughts.DraughtsPlugin;

/**
 *
 * @author Huub
 */
@PluginImplementation
public class MyDraughtsPlugin extends DraughtsPlayerProvider implements DraughtsPlugin {
    public MyDraughtsPlugin()
    {
        // make two players available to the AICompetition tool
        // During the final competition you should make only your 
        // best player available. For testing it might be handy
        // to make more than one player available.
        super(new GeniusPlayer(), new UninformedPlayer(), new OptimisticPlayer(), new StupidPlayer());
    }
}