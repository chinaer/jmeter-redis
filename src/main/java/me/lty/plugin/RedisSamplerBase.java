package me.lty.plugin;

import com.alibaba.fastjson.JSON;
import me.lty.redis.*;
import org.apache.jmeter.samplers.AbstractSampler;
import org.apache.jmeter.samplers.Entry;
import org.apache.jmeter.samplers.SampleResult;
import redis.clients.jedis.BinaryClient;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
        RedisExecPool pool = RedisExecPool.getInstance(getIP(),getPORT(),getPASSWORD());
        String type = getType();
        SampleResult res = new SampleResult();
        res.sampleStart();
        String result = "";
        try {
            switch (type){
                case "Key":
                    result = doKeyOperator(pool);
                    break;
                case "String":
                    result = doStringOperator(pool);
                    break;
                case "List":
                    result = doListOperator(pool);
                    break;
                case "Hash":
                    result = doHashOperator(pool);
                    break;
                case "Set":
                    result = doSetOperator(pool);
                    break;
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
            res.sampleEnd();
        }
        return res;
    }

    private String doSetOperator(RedisExecPool pool) {
        int code = getOperatorCode();
        String result = "";
        switch (code){
            case 0:
                result = SetOperator.add(pool,getKey(),getValue())+"";
                break;
            case 1:
                result = SetOperator.length(pool,getKey())+"";
                break;
            case 2:
                result = SetOperator.exists(pool,getKey(),getValue())+"";
                break;
            case 3:
                result = JSON.toJSONString(SetOperator.getMembers(pool,getKey()));
                break;
            case 4:
                result = SetOperator.delete(pool,getKey(),getValue().split(","))+"";
                break;
        }
        return  result;
    }

    private String doHashOperator(RedisExecPool pool) {
        int code = getOperatorCode();
        String result = "";
        switch (code){
            case 0:
                result = HashOperator.exists(pool,getKey(),getField())+"";
                break;
            case 1:
                result = JSON.toJSONString(HashOperator.get(pool,getKey(),getField().split(",")));
                break;
            case 2:
                result = JSON.toJSONString(HashOperator.getAll(pool,getKey()));
                break;
            case 3:
                result = HashOperator.set(pool,getKey(),getField(),getValue())+"";
                break;
            case 4:
                String[] hash_field = getField().split(",");
                String[] hash_value = getValue().split(",");
                Map<String,String> map = new HashMap<>();
                for (int i = 0; i<hash_field.length ;i++){
                    map.put(hash_field[i],hash_value[i]);
                }
                result = HashOperator.setAll(pool,getKey(),map);
                break;
            case 5:
                result = HashOperator.length(pool,getKey())+"";
                break;
            case 6:
                result = HashOperator.delete(pool,getKey(),getField().split(","))+"";
                break;
        }
        return result;
    }

    private String doListOperator(RedisExecPool pool) {
        int code = getOperatorCode();
        String result = "";
        switch (code){
            case 0:
                result = ListOperator.length(pool,getKey())+"";
                break;
            case 1:
                result = ListOperator.set(pool,getKey(),getIndex(),getValue());
                break;
            case 2:
                String pivot = ListOperator.get(pool,getKey(),getIndex());
                result = ListOperator.insert(pool,getKey(), BinaryClient.LIST_POSITION.AFTER,pivot,getValue())+"";
                break;
            case 3:
                result = ListOperator.get(pool,getKey(),getIndex());
                break;
            case 4:
                result = ListOperator.lpop(pool,getKey());
                break;
            case 5:
                result = ListOperator.rpop(pool,getKey());
                break;
            case 6:
                result = ListOperator.lpush(pool,getKey(),getValue().split(","))+"";
                break;
            case 7:
                result = ListOperator.rpush(pool,getKey(),getValue().split(","))+"";
                break;
            case 8:
                result = ListOperator.clear(pool,getKey());
                break;
            case 9:
                result = JSON.toJSONString(ListOperator.lrange(pool,getKey(),0,-1));
                break;
        }
        return result;
    }

    private String doStringOperator(RedisExecPool pool) {
        int code = getOperatorCode();
        String result = "";
        switch (code){
            case 0:
                result = StringOperator.get(getKey(),pool);
                break;
            case 1:
                result = StringOperator.set(pool,getKey(),getValue());
                break;
            case 2:
                String[] keys = getKey().split(",");
                String[] values = getValue().split(",");
                String[] key_value = new String[keys.length * 2];
                for(int i = 0;i < keys.length ;i++){
                    key_value[i*2] = keys[i];
                    key_value[i*2+1] = values[i];
                }
                result = StringOperator.set(pool,key_value);
                break;
            case 3:
                List<String> list = StringOperator.get(pool,getKey().split(","));
                result = JSON.toJSONString(list);
                break;
        }
        return result;
    }

    private String doKeyOperator(RedisExecPool pool) {
        int code = getOperatorCode();
        String result = "";
        switch (code){
            case 0:
                result = KeyOperator.flushAll(pool);
                break;
            case 1:
                result = KeyOperator.expired(pool,getKey(),getSeconds()) + "";
                break;
            case 2:
                result = KeyOperator.exists(pool,getKey())+"";
                break;
            case 3:
                result = KeyOperator.type(pool,getKey());
                break;
            case 4:
                result = KeyOperator.delete(pool,getKey())+"";
                break;
            case 5:
                result = JSON.toJSONString(KeyOperator.keys(pool,"*"));
                break;
        }
        return result;
    }

}
