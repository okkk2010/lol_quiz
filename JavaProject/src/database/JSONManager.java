package database;

import java.awt.List;
import java.util.ArrayList;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import dataSet.user.User;

public class JSONManager {
	public final static ObjectMapper mapper = new ObjectMapper();
	
	public static <T>T getUserJsonData(String json, JavaType inputClass) {
		
		try {
			return mapper.readValue(json, inputClass);
		} catch (JsonMappingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}
	
	public static <T> ArrayList<T> getUsersJsonData(String json, JavaType inputClass) {
		
		try {
			return mapper.readValue(json, mapper.getTypeFactory().constructCollectionType(ArrayList.class, inputClass)); 
		} catch (JsonMappingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}
}
