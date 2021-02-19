package com.mb.ext.core.util;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.map.DeserializationContext;
import org.codehaus.jackson.map.JsonDeserializer;

public class JsonDateTimeDeserializer  extends JsonDeserializer<Date> {
	private SimpleDateFormat dateFormat=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");  
	   @Override  
	   public Date deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JsonProcessingException {  
	        try {  
	            return dateFormat.parse(jsonParser.getText());  
	        } catch (ParseException e) {  
	            throw new RuntimeException(e);  
	        }  
	    } 
}
