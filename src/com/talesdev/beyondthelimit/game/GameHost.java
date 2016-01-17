package com.talesdev.beyondthelimit.game;

import com.google.gson.*;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import com.talesdev.beyondthelimit.BeyondTheLimit;
import com.talesdev.beyondthelimit.game.scoreboard.GameComparator;
import com.talesdev.beyondthelimit.game.scoreboard.ScoreComparator;
import com.talesdev.beyondthelimit.game.scoreboard.ScoreEntry;
import org.pmw.tinylog.Logger;

import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Game host
 *
 * @author MoKunz
 */
public class GameHost {
    private SortedSet<ScoreEntry> previousGame;
    private Game currentGame;
    private BeyondTheLimit app;
    private File save = new File("data.json");
    public static final Comparator<ScoreEntry> COMPARATOR = new ScoreComparator();

    public GameHost(BeyondTheLimit app) {
        this.app = app;
        this.previousGame = new TreeSet<>(COMPARATOR);
    }

    public BeyondTheLimit app() {
        return app;
    }

    public void loadData() {
        SortedSet<ScoreEntry> gs = new TreeSet<>(COMPARATOR);
        JsonArray o = convertFileToJSON(save);
        for (Iterator<JsonElement> it = o.getAsJsonArray().iterator(); it.hasNext(); ) {
            JsonElement e = it.next();
            if (e.isJsonObject()) {
                JsonObject jo = e.getAsJsonObject();
                gs.add(new ScoreEntry(jo.get("teamName").getAsString(), jo.get("score").getAsInt(), jo.get("timeLeft").getAsInt()));
            }
        }
        previousGame.addAll(gs);
    }

    public void add(String teamName,int timeLeft,int score){
        previousGame.add(new ScoreEntry(teamName,score,timeLeft));
    }

    public void add(ScoreEntry scoreEntry){
        previousGame.add(scoreEntry);
    }

    public boolean has(Game game){
        for(ScoreEntry entry : previousGame){
            if(entry.teamName().equals(game.teamName())) return true;
        }
        return false;
    }

    public void saveData() {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        try {
            FileWriter fw = new FileWriter(save);
            fw.write(gson.toJson(asList()));
            fw.flush();
            fw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private JsonArray convertFileToJSON(File file) {
        // Read from File to String
        JsonArray jsonObject = new JsonArray();
        try {
            JsonParser parser = new JsonParser();
            JsonElement jsonElement = parser.parse(new FileReader(file));
            jsonObject = jsonElement.getAsJsonArray();
        } catch (FileNotFoundException ignored) {

        }
        return jsonObject;
    }


    public Game currentRunning() {
        return currentGame;
    }

    public void hostGame(Game game) {
        if (this.currentGame != null) {
            previousGame.add(new ScoreEntry(this.currentGame));
            if(!this.currentGame.currentStatus().equals(GameStatus.END)) this.currentGame.end();
        }
        this.currentGame = game;
    }

    public void clear() {
        this.currentGame = null;
    }

    public void startGame() {
        currentGame.start();
    }

    public GameStatus currentStatus() {
        return currentGame.currentStatus();
    }

    public void stopGame() {
        if (currentGame != null) {
            this.currentGame.end();
        }
    }

    public void recordGame(){
        //previousGame.add(new ScoreEntry(currentGame));
    }

    public SortedSet<ScoreEntry> previousGame() {
        return new TreeSet<>(previousGame);
    }

    public List<ScoreEntry> asList() {
        return new ArrayList<>(previousGame);
    }
}
