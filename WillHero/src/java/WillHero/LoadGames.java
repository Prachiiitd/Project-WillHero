package WillHero;


import Exceptions.SaveNotFoundException;

import java.io.File;
import java.util.ArrayList;

public class LoadGames {
    private final ArrayList<String> loadableGames;
        private final ArrayList<String> loadablePlayers;
    public LoadGames() {
        loadableGames = new ArrayList<>();
        loadablePlayers = new ArrayList<>();
    }
    public ArrayList<String> getLoadableGamesList() {
        File [] directory = new File("Willhero/src/java/SavedGames").listFiles();
        if (directory != null) {
            for(File file : directory) {
                loadableGames.add(file.getName());
            }
            return loadableGames;

        }
        else {
            throw new SaveNotFoundException("No games found");
        }

    }
    public ArrayList<String> getLoadablePlayersList() {
        File[] directory = new File("Willhero/src/java/SavedPlayers").listFiles();
        if (directory != null) {
            for (File file : directory) {
                loadablePlayers.add(file.getName());
            }
            return loadablePlayers;
        } else {
            throw new SaveNotFoundException("No players found");
        }
    }
}