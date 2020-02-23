/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package stormTP.operator;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
import javax.json.JsonReader;
import org.apache.storm.task.OutputCollector;
import org.apache.storm.task.TopologyContext;
import org.apache.storm.topology.IRichBolt;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.tuple.Fields;
import org.apache.storm.tuple.Tuple;
import org.apache.storm.tuple.Values;

/**
 *
 * @author Dell
 */
public class ComputePointsBolt implements IRichBolt{
    private OutputCollector collector;

    /* (non-Javadoc)
	 * @see backtype.storm.topology.IRichBolt#execute(backtype.storm.tuple.Tuple)
     */
    public void execute(Tuple t) {
        String n = t.getValueByField("json").toString();
        HashMap<Integer, Boolean> map = new HashMap<>();
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
        
        JsonReader jReader = Json.createReader(new StringReader(n));
        JsonObject tortoises = jReader.readObject();
        JsonArray arr = tortoises.getJsonArray("tortoises");
        
        for(int i=0; i<arr.size();i++){
            JsonObject tmp = arr.getJsonObject(i);
            
            String rang = "";
            if (!map.containsKey((tmp.getInt("nbDevant") + 1))) {
                map.put((tmp.getInt("nbDevant") + 1), Boolean.TRUE);
                rang = Integer.toString((tmp.getInt("nbDevant") + 1));
            } else {
                rang = Integer.toString((tmp.getInt("nbDevant") + 1)) + "ex";
            }
            
            int point = tablePoints.get(rang.split("ex")[0]);
            collector.emit(t, new Values(tmp.getInt("id"), tmp.getInt("top"), point));
        }

    }

    /* (non-Javadoc)
	 * @see backtype.storm.topology.IComponent#declareOutputFields(backtype.storm.topology.OutputFieldsDeclarer)
     */
    public void declareOutputFields(OutputFieldsDeclarer arg0) {
        arg0.declare(new Fields("id", "top", "points"));
    }


    /* (non-Javadoc)
	 * @see backtype.storm.topology.IComponent#getComponentConfiguration()
     */
    public Map<String, Object> getComponentConfiguration() {
        return null;
    }

    /* (non-Javadoc)
	 * @see backtype.storm.topology.IBasicBolt#cleanup()
     */
    public void cleanup() {

    }

    /* (non-Javadoc)
	 * @see backtype.storm.topology.IRichBolt#prepare(java.util.Map, backtype.storm.task.TopologyContext, backtype.storm.task.OutputCollector)
     */
    @SuppressWarnings("rawtypes")
    public void prepare(Map arg0, TopologyContext context, OutputCollector collector) {
        this.collector = collector;
    }
}
