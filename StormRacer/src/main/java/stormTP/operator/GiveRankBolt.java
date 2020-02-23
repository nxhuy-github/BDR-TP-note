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
public class GiveRankBolt implements IRichBolt{
    private OutputCollector collector;
    private HashMap<Integer, Boolean> map = new HashMap<>();

    /* (non-Javadoc)
	 * @see backtype.storm.topology.IRichBolt#execute(backtype.storm.tuple.Tuple)
     */
    public void execute(Tuple t) {
        
        int id = Integer.parseInt(t.getValueByField("id").toString());
        int top = Integer.parseInt(t.getValueByField("top").toString());
        int total = Integer.parseInt(t.getValueByField("total").toString());
        String nom = t.getValueByField("nom").toString();
        int nbDevant = Integer.parseInt(t.getValueByField("nbDevant").toString());
        
        String rang = "";
        if (!map.containsKey(nbDevant + 1)) {
            map.put(nbDevant + 1, Boolean.TRUE);
            rang = Integer.toString(nbDevant + 1);
        } else {
            rang = Integer.toString(nbDevant + 1) + "ex";
        }
        
        collector.emit(t, new Values(id, top, nom, rang, total));

        return;

    }

    /* (non-Javadoc)
	 * @see backtype.storm.topology.IComponent#declareOutputFields(backtype.storm.topology.OutputFieldsDeclarer)
     */
    public void declareOutputFields(OutputFieldsDeclarer arg0) {
        arg0.declare(new Fields("id", "top", "nom", "rang", "total"));
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
