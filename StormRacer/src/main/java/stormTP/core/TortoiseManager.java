package stormTP.core;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.json.JsonReader;
import org.json.JSONArray;
import org.json.JSONObject;

public class TortoiseManager {

    public static final String CONST = "C'est mieux!";
    public static final String PROG = "Statu quo";
    public static final String REGR = "ça sera sûrement mieux plus tard!";

    String nomsBinome = "";
    long dossard = -1;

    public TortoiseManager(long dossard, String nomsBinome) {
        this.nomsBinome = nomsBinome;
        this.dossard = dossard;
    }

    /**
     * Permet de filtrer les informations concernant votre tortue
     *
     * @param input
     * @return
     */
    public Runner filter(String input) {

        Runner tortoise = null;
        JSONObject tortoises = new JSONObject(input);
        JSONArray arr = tortoises.getJSONArray("tortoises");
        for(int i=0; i<arr.length();i++){
            JSONObject o = arr.getJSONObject(i);
            if (o.getInt("id") == this.dossard) {
                tortoise = new Runner(o.getLong("id"),
                    this.nomsBinome,
                    o.getInt("nbDevant"),
                    o.getInt("nbDerriere"),
                    o.getInt("total"),
                    o.getInt("position"),
                    o.getLong("top"));
            }
        }
        return tortoise;
    }

    public static String getPodium(String input) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public int giveAverageRank(String[] input) {
        Pattern p = Pattern.compile("([0-9]+)(ex)*");
        int sum = 0;
        for (String s : input) {
            Matcher m = p.matcher(s);
            if (m.find()) {
                int rank = Integer.parseInt(m.group(1));
                sum += rank;
            }
        }
        return sum / input.length;
    }

    public String giveRankEvolution(int i, int i0) {
        String tmp;
        if (i < i0) {
            tmp = new String(CONST);
        } else if (i == i0) {
            tmp = new String(PROG);
        } else {
            tmp = new String(REGR);
        }
        return tmp;
    }

    public double computeSpeed(int i, int i0, int i1, int i2) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public int computePoints(String ex, int i) {
        HashMap<String, Integer> tablePoints = new HashMap<>();
        tablePoints.put("1", 12);
        tablePoints.put("2", 10);
        tablePoints.put("3", 8);
        tablePoints.put("4", 6);
        tablePoints.put("5", 5);
        tablePoints.put("6", 4);
        tablePoints.put("7", 3);
        tablePoints.put("8", 2);
        tablePoints.put("9", 1);
        tablePoints.put("10", 0);
        return tablePoints.get(ex.split("ex")[0]);
    }

    public Runner computeRank(int i, int i0, String nomsBinome, int i1, int i2, int i3) {
        Runner tortoise = null;
        int defaultRang = 2;
        HashMap<Integer, Boolean> map = new HashMap<>();
        map.put(defaultRang, Boolean.TRUE);
        String rang = "";

        if (!map.containsKey(i1 + 1)) {
            map.put(i1 + 1, Boolean.TRUE);
            rang = Integer.toString(i1 + 1);
        } else {
            rang = Integer.toString(i1 + 1) + "ex";
        }
        tortoise = new Runner(i, nomsBinome, i3, rang, i0);
        return tortoise;
    }
}
