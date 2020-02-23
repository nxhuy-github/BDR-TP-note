/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package stormTP.operator;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
import org.apache.storm.task.OutputCollector;
import org.apache.storm.task.TopologyContext;
import org.apache.storm.topology.IRichBolt;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.tuple.Fields;
import org.apache.storm.tuple.Tuple;
import stormTP.stream.StreamEmiter;

/**
 *
 * @author admin
 */
public class Exit7Bolt implements IRichBolt {

    private static final long serialVersionUID = 4262369370788107342L;
    //private static Logger logger = Logger.getLogger("ExitBolt");
    private OutputCollector collector;
    String ipM = "";
    int port = -1;
    StreamEmiter semit = null;

    public Exit7Bolt(int port, String ip) {
        this.port = port;
        this.ipM = ip;
        this.semit = new StreamEmiter(this.port, this.ipM);

    }

    /* (non-Javadoc)
	 * @see backtype.storm.topology.IRichBolt#execute(backtype.storm.tuple.Tuple)
     */
    public void execute(Tuple t) {

        String n = t.getValueByField("json").toString();
        
        List<String> r = new ArrayList<String>(Arrays.asList(n.split(",")));
        
        JsonArrayBuilder arrBuilder = Json.createArrayBuilder();
        for(int i=0;i<r.size();i=i+3){
            JsonObject obj = Json.createObjectBuilder()
                    .add("id", r.get(i))
                    .add("cpt", r.get(i+1))
                    .add("evolution",r.get(i+2)).build();
            arrBuilder.add(obj);
        }
        
        JsonArray arr2 = arrBuilder.build();
        JsonObject newTor = Json.createObjectBuilder()
                .add("tortoises", arr2).build();
        
        
        this.semit.send(newTor.toString());
        collector.ack(t);

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
