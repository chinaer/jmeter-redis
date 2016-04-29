package me.lty.redis;

import java.util.*;

public class Data  {
   public static final Map<String,List<Operator>> TYPE_OPERATOR ;

    static {
        TYPE_OPERATOR = new HashMap<>();

        TYPE_OPERATOR.put("Key",createKeyList() );

        TYPE_OPERATOR.put("String",createStringList());

        TYPE_OPERATOR.put("Hash",createHashList());

        TYPE_OPERATOR.put("List",createList());

        TYPE_OPERATOR.put("Set",createSetList());
    }



    public static List<Operator> createKeyList(){
        List<Operator> key = new ArrayList<Operator>();
        String type = "Key";
        key.add(new Operator(type,"clear all Keys",0));
        key.add(new Operator(type,"set key  expire time",1));
        key.add(new Operator(type," key exit",2));
        key.add(new Operator(type,"get key type",3));
        key.add(new Operator(type,"delete key",4));
        key.add(new Operator(type,"get all key",5));
        return key;
    }

    public static List<Operator> createStringList(){
        List<Operator> string = new ArrayList<Operator>();
        String type = "String";
        string.add(new Operator(type,"get record by key",0));
        string.add(new Operator(type,"set",1));
        string.add(new Operator(type,"mset",2));
        string.add(new Operator(type,"mget",3));
        return string;
    }

    public static List<Operator> createHashList(){
        List<Operator> Hash = new ArrayList<Operator>();
        String type = "Hash";
        Hash.add(new Operator(type,"hexists",0));
        Hash.add(new Operator(type,"hmget",1));
        Hash.add(new Operator(type,"hgetAll",2));
        Hash.add(new Operator(type,"hset",3));
        Hash.add(new Operator(type,"hmset",4));
        Hash.add(new Operator(type,"hlen",5));
        Hash.add(new Operator(type,"hdel",6));
        return Hash;
    }

    public static List<Operator> createList(){
        List<Operator> list = new ArrayList<Operator>();
        String type = "List";
        list.add(new Operator(type,"llen",0));
        list.add(new Operator(type,"lset",1));
        list.add(new Operator(type,"linsert",2));
        list.add(new Operator(type,"lindex",3));
        list.add(new Operator(type,"lpop",4));
        list.add(new Operator(type,"rpop",5));
        list.add(new Operator(type,"lpush",6));
        list.add(new Operator(type,"rpush",7));
        list.add(new Operator(type,"lrange",9));
        list.add(new Operator(type,"lpop",8));
        return list;
    }

    private static List<Operator> createSetList() {
        List<Operator> list = new ArrayList<>();
        String type = "Set";
        list.add(new Operator(type,"sadd",0));
        list.add(new Operator(type,"scard",1));
        list.add(new Operator(type,"sismember",2));
        list.add(new Operator(type,"smembers",3));
        list.add(new Operator(type,"srem",4));
        return list;
    }
}
