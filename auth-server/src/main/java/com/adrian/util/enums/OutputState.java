package com.adrian.util.enums;

import java.util.HashMap;
import java.util.Map;

/**
 * @author asus
 */

public enum OutputState {
	SUCCESS(1000),//成功
	FAIL(-1000),//失败
	PARAM_NOT_INCOMPLETE(-1001),//参数不全
	PARAM_NAME_ERROR(-1002),//参数名错误
	PARAM_VALUE_ERROR(-1003),//参数值错误
	DATA_NOT_EXISTS(-1004);//未找到
	
	
	private static final Map<Integer, OutputState> integerToEnum = new HashMap<Integer, OutputState>();
    static {
        // Initialize map from constant name to enum constant
        for(OutputState state : values()) {
        	integerToEnum.put(state.value, state);
        }
    } 
    public static OutputState integerString(Integer symbol) {
        return integerToEnum.get(symbol);
    }
	
	private int value; 
	OutputState(int v) {
	   value = v;
    }  
    public int getValue() {
      return value;
    }  
    
    public static String valuesOf(int v){
		   
		   for (OutputState e : OutputState.values()) {
			   	if (e.getValue() == v){
			   		return e.getTxt();
			   	} 
		   }
		
		   return null; 
	   }
	   
	public String getTxt(){
		   switch(value){ 
			   case 1000:
				   return "成功"; 
			   case -1000:
				   return "失败"; 
			   case -1001:
				   return "参数不全";
			   case -1002:
				   return "参数名错误";
			   case -1003:
				   return "参数值错误";
			   case -1004:
				   return "未找到";
			   default:
				   return "";
		   }
	   }
}
