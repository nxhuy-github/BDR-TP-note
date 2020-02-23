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
import java.util.Collections;

/**
 *
 * @author admin
 */
public class AveragePointsBolt extends BaseStatefulWindowedBolt<KeyValueState<String, Integer>> {

    private static final long serialVersionUID = 4262379330788107343L;
    private KeyValueState<String, Integer> state;
    private int sum;

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
        int maxTop = 0;
        int minTop = 0;
        int sumScore = 0;
        int dossard = 3;
        //JsonArrayBuilder arr2Builder = Json.createArrayBuilder();
        for (Tuple t : inputWindow.get()) {
            
            // START TO READ THE TUPLE 
            String n = t.getValueByField("json").toString();
            JsonReader r = Json.createReader(new StringReader(n));
            JsonObject tortoises = r.readObject();
            JsonArray arr = tortoises.getJsonArray("tortoises");
            
            //maxTop = arr.getJsonObject(0).getInt("top");
            //minTop = maxTop;
            ArrayList<Integer> aInt = new ArrayList<>();
            sumScore = arr.getJsonObject(0).getInt("points");
            cpt++;
            for(int i=1; i<arr.size();i++){
                JsonObject tmp = arr.getJsonObject(i);
                if (dossard == tmp.getInt("id")){
                    //if(tmp.getInt("top")>maxTop){
                    //    maxTop = tmp.getInt("top");
                    //}
                    //if(tmp.getInt("top")<minTop){
                    //    minTop = tmp.getInt("top");
                    //}
                    aInt.add(tmp.getInt("top"));
                    sumScore += tmp.getInt("points");    
                }
            }
            maxTop = Collections.max(aInt);
            minTop = Collections.min(aInt);
        }
        
        state.put("sum", cpt);
        //JsonObjectBuilder r = Json.createObjectBuilder();
        //r.add("id", cpt);
        //r.add("top", minTop+"-"+maxTop);
        //r.add("average", sumScore/cpt);
        //JsonObject response = r.build();
        //arr2Builder.add(response);
        //JsonArray arr2 = arr2Builder.build();
        //JsonObject newTor = Json.createObjectBuilder()
        //        .add("tortoises", arr2).build();
        String str = String.valueOf(dossard) 
                    + "," 
                    + minTop
                    + "-"
                    + maxTop
                    + ","
                    + String.valueOf(sumScore/cpt);
        
        collector.emit(inputWindow.get(), new Values(str));
    }

    @Override
    public void declareOutputFields(OutputFieldsDeclarer declarer) {
        declarer.declare(new Fields("json"));
    }

}
