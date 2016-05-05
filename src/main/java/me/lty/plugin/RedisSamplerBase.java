package me.lty.plugin;

import java.util.List;

import me.lty.redis.StringOperator;

import org.apache.jmeter.samplers.AbstractSampler;
import org.apache.jmeter.samplers.Entry;
import org.apache.jmeter.samplers.SampleResult;

import redis.clients.jedis.Jedis;

import com.alibaba.fastjson.JSON;

/**
 */
public class RedisSamplerBase extends AbstractSampler{

	private static final long serialVersionUID = 1L;
	public static final String KEY = "key";
    public static final String VALUE = "value";
    public static final String SECONDS = "seconds";
    public static final String FIELD = "field";
    public static final String INDEX = "index";
    public static final String TYPE = "type";
    public static final String OPERATOR_TYPE = "operator_type";
    public static final String OPERATOR_CODE = "operator_code";

    public static final String IP = "ip";
    public static final String PORT = "port";
    public static final String PASSWORD = "password";

    public static final String OPERATOR_INDEX = "operator_index";

    public void setSeconds(int seconds){
        this.setProperty(SECONDS,seconds);
    }

    public int getSeconds(){
        return this.getPropertyAsInt(SECONDS);
    }

    public void setOperatorIndex(int index){
        this.setProperty(OPERATOR_INDEX,index);
    }

    public int getOperatorIndex(){
        return this.getPropertyAsInt(OPERATOR_INDEX);
    }

    public void setIp(String ip){
        this.setProperty(IP,ip);
    }

    public void setPort(int port){
        this.setProperty(PORT,port);
    }

    public void setPassword(String password){
        this.setProperty(PASSWORD,password);
    }

    public int  getPORT() {
        return this.getPropertyAsInt(PORT);
    }

    public  String getIP() {
        return this.getPropertyAsString(IP);
    }

    public String getPASSWORD() {
        return this.getPropertyAsString(PASSWORD);
    }

    public void setKey(String key){
        this.setProperty(KEY,key);
    }

    public String getKey(){
        return this.getPropertyAsString(KEY);
    }

    public void setValue(String value){
        this.setProperty(VALUE,value);
    }

    public String getValue(){
        return this.getPropertyAsString(VALUE);
    }

    public void setField(String field){
        this.setProperty(FIELD,field);
    }

    public String getField(){
        return this.getPropertyAsString(FIELD);
    }

    public void setIndex(int index){
        this.setProperty(INDEX,index);
    }

    public int getIndex(){
        return this.getPropertyAsInt(INDEX);
    }

    public void setType(String type){
        this.setProperty(TYPE,type);
    }

    public String getType(){
        return this.getPropertyAsString(TYPE);
    }

    public void setOperatorType(String type){
        this.setProperty(OPERATOR_TYPE,type);
    }

    public String getOperatorType(){
        return this.getPropertyAsString(OPERATOR_TYPE);
    }

    public void setOperatorCode(int code){
        this.setProperty(OPERATOR_CODE,code);
    }

    public int getOperatorCode(){
        return this.getPropertyAsInt(OPERATOR_CODE);
    }

    public SampleResult sample(Entry entry) {
    	Jedis jedis =new Jedis(getIP(),getPORT());
        String type = getType();
        SampleResult res = new SampleResult();
        res.sampleStart();
        String result = "";
        try {
        	 if(type!=null && type.equals("String")){
             	result = doStringOperator(jedis);
             }
            res.setResponseOK();
            if(result != null){
                res.setResponseData(result,"UTF-8");
                res.setSamplerData(result);
                res.setResponseMessage(result);
            }else {
                res.setResponseData("null","UTF-8");
                res.setResponseMessage("null");
            }
        }catch (Exception e){
            res.setResponseCode("500");
            res.setSuccessful(false);
            res.setResponseMessage(e.toString());
            res.setSamplerData(e.toString());
        }finally {
        	jedis.close();
            res.sampleEnd();
        }
        return res;
    }

    
    private String doStringOperator(Jedis jedis) {
        int code = getOperatorCode();
        String result = "";
        if(code==0){
        	 result = StringOperator.get(getKey(),jedis);
        }else if(code==1){
        	result = StringOperator.set(jedis,getKey(),getValue());
        }else if(code==2){
        	String[] keys = getKey().split(",");
            String[] values = getValue().split(",");
            String[] key_value = new String[keys.length * 2];
            for(int i = 0;i < keys.length ;i++){
                key_value[i*2] = keys[i];
                key_value[i*2+1] = values[i];
            }
            result = StringOperator.set(jedis,key_value);
        }else if(code==3){
        	 List<String> list = StringOperator.get(jedis,getKey().split(","));
             result = JSON.toJSONString(list);
        }
        return result;
    }

}
