/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package stormTP.operator;

import java.io.StringReader;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Map;
import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.json.JsonReader;
import javax.json.JsonString;
import javax.json.JsonValue;
import org.apache.storm.task.OutputCollector;
import org.apache.storm.task.TopologyContext;
import org.apache.storm.topology.IRichBolt;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.tuple.Fields;
import org.apache.storm.tuple.Tuple;
import org.apache.storm.tuple.Values;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 *
 * @author ntu
 */
public class MyTortoiseBolt implements IRichBolt{
    private OutputCollector collector;

    /* (non-Javadoc)
	 * @see backtype.storm.topology.IRichBolt#execute(backtype.storm.tuple.Tuple)
     */
    public void execute(Tuple t) {

        String n = t.getValueByField("json").toString();
        //System.out.println( n  + " is treated!"); 
        
        JsonReader jReader = Json.createReader(new StringReader(n));
        JsonObject tortoises = jReader.readObject();
        JsonArray arr = tortoises.getJsonArray("tortoises");
        int dossard = 3;
        
        ArrayList<String> results = new ArrayList<String>();
        
        for(int i=0; i<arr.size();i++){
            JsonObject tmp = arr.getJsonObject(i);
            if (dossard == tmp.getInt("id")){
//                results.add(tmp.get("id").toString());
//                results.add(tmp.get("top").toString());
//                results.add("nguyen-nguyen");
//                results.add(tmp.get("position").toString());
//                results.add(tmp.get("nbDevant").toString());
//                results.add(tmp.get("nbDerriere").toString());
//                results.add(tmp.get("total").toString());
                collector.emit(t, new Values(tmp.get("id"),
                                            tmp.get("top"),
                                            "nguyen-nguyen",
                                            tmp.get("position"),
                                            tmp.get("nbDevant"),
                                            tmp.get("nbDerriere"),
                                            tmp.get("total")
                                            ));
            }
        }
        //String res = String.join(",", results);
        //collector.emit(t, new Values(res));
        //return;
    }

    /* (non-Javadoc)
	 * @see backtype.storm.topology.IComponent#declareOutputFields(backtype.storm.topology.OutputFieldsDeclarer)
     */
    public void declareOutputFields(OutputFieldsDeclarer arg0) {
        arg0.declare(new Fields("id", "top", "nom", "position", "nbDevant", "nbDerriere", "total"));
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
