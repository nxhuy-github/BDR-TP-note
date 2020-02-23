/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package stormTP.operator;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.Map;
import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.json.JsonReader;
import org.apache.storm.state.KeyValueState;
import org.apache.storm.task.OutputCollector;
import org.apache.storm.task.TopologyContext;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.topology.base.BaseStatefulWindowedBolt;
import org.apache.storm.tuple.Fields;
import org.apache.storm.tuple.Tuple;
import org.apache.storm.tuple.Values;
import org.apache.storm.windowing.TupleWindow;

/**
 *
 * @author admin
 */
public class EvolAvgPointsBolt extends BaseStatefulWindowedBolt<KeyValueState<String, Integer>> {

    private static final long serialVersionUID = 4262379330788107343L;
    private KeyValueState<String, Integer> state;
    private int sum;
    
    public static final String CONST = "C'est mieux!";
    public static final String PROG = "Statu quo";
    public static final String REGR = "ça sera sûrement mieux plus tard!";

    private OutputCollector collector;

    @Override
    public void prepare(Map stormConf, TopologyContext context, OutputCollector collector) {
        this.collector = collector;
    }

    @Override
    public void initState(KeyValueState<String, Integer> state) {
        this.state = state;
        sum = state.get("sum", 0);
        System.out.println("initState with state [" + state + "] current sum [" + sum + "]");
    }

    @Override
    public void execute(TupleWindow inputWindow) {

        int cpt = 0;

        
        ArrayList<String> results = new ArrayList<String>();
        
        int old = -1;
        for (Tuple t : inputWindow.get()) {
            JsonObjectBuilder obj2Builder = Json.createObjectBuilder();
            // START TO READ THE TUPLE 
            String n = t.getValueByField("json").toString();
            JsonReader r = Json.createReader(new StringReader(n));
            JsonObject tortoises = r.readObject();
            JsonArray arr = tortoises.getJsonArray("tortoises");
            
            cpt++;
            
            int avg = arr.getJsonObject(0).getInt("average");
            // TO SKIP THE FIRST TUPLE
            if(old >= 0){
                results.add(arr.getJsonObject(0).get("id").toString());
                results.add(String.valueOf(cpt));
                if(avg >old){
                    results.add(new String(CONST));
                }else if (avg==old){
                    results.add(new String(PROG));
                }else{
                    results.add(new String(REGR));
                }
            }
            old = avg;            
        }
        
        state.put("sum", cpt);
        
        String res = String.join(",", results);
        collector.emit(inputWindow.get(), new Values(res));

    }

    @Override
    public void declareOutputFields(OutputFieldsDeclarer declarer) {
        declarer.declare(new Fields("json"));
    }
    
}
