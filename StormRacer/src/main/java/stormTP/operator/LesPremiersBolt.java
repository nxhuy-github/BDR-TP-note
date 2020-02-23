/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package stormTP.operator;

import java.io.StringReader;
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
public class LesPremiersBolt implements IRichBolt{
    private OutputCollector collector;
    
    public void execute(Tuple t) {
        String n = t.getValueByField("json").toString();
        JsonReader jReader = Json.createReader(new StringReader(n));
        JsonObject tortoises = jReader.readObject();
        JsonArray arr = tortoises.getJsonArray("tortoises");
        JsonArrayBuilder arr2Builder = Json.createArrayBuilder();
        
        for(int i=0; i<arr.size();i++){
            JsonObject tmp = arr.getJsonObject(i);
            if (tmp.getInt("nbDevant") == 0) {
                n = String.valueOf(tmp.getInt("id"));
            }
        }
        collector.emit(t, new Values(n));
        return;
    }

    /* (non-Javadoc)
	 * @see backtype.storm.topology.IComponent#declareOutputFields(backtype.storm.topology.OutputFieldsDeclarer)
     */
    public void declareOutputFields(OutputFieldsDeclarer arg0) {
        arg0.declare(new Fields("json"));
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
