package me.lty.redis;

import java.util.*;

public class Data  {
   public static final Map<String,List<Operator>> TYPE_OPERATOR ;

    static {
        TYPE_OPERATOR = new HashMap<>();
        TYPE_OPERATOR.put("String",createStringList());
    }
   
    public static List<Operator> createStringList(){
        List<Operator> string = new ArrayList<Operator>();
        String type = "String";
        string.add(new Operator(type,"get",0));
        string.add(new Operator(type,"set",1));
        string.add(new Operator(type,"mset",2));
        string.add(new Operator(type,"mget",3));
        return string;
    }
  
}
