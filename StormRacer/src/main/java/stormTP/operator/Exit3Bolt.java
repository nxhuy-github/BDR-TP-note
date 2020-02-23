/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package stormTP.operator;

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
 * @author Dell
 */
public class Exit3Bolt implements IRichBolt{
    private OutputCollector collector;
	String ipM = "";
	int port = -1;
	StreamEmiter semit = null;
	
	public Exit3Bolt (int port, String ip) {
		this.port = port;
		this.ipM = ip; 
		this.semit = new StreamEmiter(this.port,this.ipM);
		
	}
	
	/* (non-Javadoc)
	 * @see backtype.storm.topology.IRichBolt#execute(backtype.storm.tuple.Tuple)
	 */
	public void execute(Tuple t) {
                
                JsonObject obj = Json.createObjectBuilder()
                        .add("id", t.getInteger(0))
                        .add("top", t.getInteger(1))
                        .add("nom", t.getString(2))
                        .add("rang", t.getString(3))
                        .add("total", t.getInteger(4))
                        .build();
               
		this.semit.send(obj.toString());
            
//		String n = t.getValueByField("json").toString();
//                List<String> r = new ArrayList<>(Arrays.asList(n.split(";")));
//                JsonArrayBuilder arrBuilder = Json.createArrayBuilder();
//                for (int i = 0; i < r.size(); i++) {
//                    String[] parts = r.get(i).split(",");
//                    JsonObject obj = Json.createObjectBuilder()
//                            .add("id", parts[0])
//                            .add("top", parts[1])
//                            .add("nom", parts[2])
//                            .add("rang", parts[3])
//                            .add("nbTotal", parts[4])
//                            .build();
//                    arrBuilder.add(obj);
//                }
//                JsonArray arr = arrBuilder.build();
//                JsonObject newTor = Json.createObjectBuilder()
//                                    .add("tortoises", arr).build();
//		this.semit.send(newTor.toString());
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
