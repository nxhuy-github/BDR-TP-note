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
public class ComputeScoreBolt implements IRichBolt{
    private OutputCollector collector;

    /* (non-Javadoc)
	 * @see backtype.storm.topology.IRichBolt#execute(backtype.storm.tuple.Tuple)
     */
    public void execute(Tuple t) {
        String n = t.getValueByField("json").toString();
        
        JsonReader jReader = Json.createReader(new StringReader(n));
        JsonObject tortoises = jReader.readObject();
        JsonArray arr = tortoises.getJsonArray("tortoises");
        //JsonArrayBuilder arr2Builder = Json.createArrayBuilder();
        ArrayList<String> aStrings = new ArrayList<String>();
        for(int i=0; i<arr.size();i++){
            JsonObject tmp = arr.getJsonObject(i);
            int score = 0;
            score += tmp.getInt("points");
            //JsonObject tmp2 = Json.createObjectBuilder()
            //        .add("id", tmp.getInt("id"))
            //        .add("top", tmp.getInt("top"))
            //        .add("score", score).build();
            //arr2Builder.add(tmp2);
            String str = String.valueOf(tmp.getInt("id")) 
                    + "-" 
                    + String.valueOf(tmp.getInt("top")) 
                    + "-"
                    + String.valueOf(score);
            aStrings.add(str);
        }
        //JsonArray arr2 = arr2Builder.build();
        //JsonObject newTor = Json.createObjectBuilder()
        //        .add("tortoises", arr2).build();
        
        //n = newTor.toString();
        String result = String.join(",", aStrings);
        collector.emit(t, new Values(result));

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
