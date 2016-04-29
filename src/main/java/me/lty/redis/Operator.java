package me.lty.redis;

public class Operator {
    public String type ; 
    public String operator ;
    public int code; 

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getType() {

        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }

    public Operator(String type,String operator,int code){
        this.type = type;
        this.operator = operator;
        this.code = code;
    }
    @Override
    public String toString() {
        return operator;
    }
}
