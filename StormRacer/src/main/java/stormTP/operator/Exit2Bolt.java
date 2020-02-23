package stormTP.operator;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
//import java.util.logging.Logger;

import org.apache.storm.task.OutputCollector;
import org.apache.storm.task.TopologyContext;
import org.apache.storm.topology.IRichBolt;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.tuple.Fields;
import org.apache.storm.tuple.Tuple;
import stormTP.stream.StreamEmiter;


public class Exit2Bolt implements IRichBolt {

	private static final long serialVersionUID = 4262369370788107342L;
	//private static Logger logger = Logger.getLogger("ExitBolt");
	private OutputCollector collector;
	String ipM = "";
	int port = -1;
	StreamEmiter semit = null;
	
	public Exit2Bolt (int port, String ip) {
		this.port = port;
		this.ipM = ip; 
		this.semit = new StreamEmiter(this.port,this.ipM);
		
	}
	
	/* (non-Javadoc)
	 * @see backtype.storm.topology.IRichBolt#execute(backtype.storm.tuple.Tuple)
	 */
	public void execute(Tuple t) {
	
//		String n = t.getValueByField("json").toString();
//                List<String> r = new ArrayList<String>(Arrays.asList(n.split(",")));
//                JsonObject obj = Json.createObjectBuilder()
//                .add("id",r.get(0))
//                .add("top",r.get(1))
//                .add("nom",r.get(2))
//                .add("position",r.get(3))
//                .add("nbDevant",r.get(4))
//                .add("nbDerriere",r.get(5))
//                .add("total",r.get(6)).build();
                JsonObject obj = Json.createObjectBuilder()
                        .add("id", t.getInteger(0))
                        .add("top", t.getInteger(1))
                        .add("nom", t.getString(2))
                        .add("position", t.getInteger(3))
                        .add("nbDevant", t.getInteger(4))
                        .add("nbDerriere", t.getInteger(5))
                        .add("total", t.getInteger(6))
                        .build();
               
		this.semit.send(obj.toString());
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