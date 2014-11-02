package se.stonepath.framework.jsonlink.link;

import java.lang.reflect.Type;

import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import com.jayway.jsonpath.JsonPath;

public class LinkSerializer implements JsonSerializer<Link>{

	
	private String jsonData;
	public LinkSerializer(String jsonData){
		this.jsonData = jsonData;
	}
	

	
	public JsonElement serialize(Link jsonLink, Type type,
			JsonSerializationContext context) {
		
		JsonElement jsonElement = new JsonPrimitive("");
		
		if(type.equals(Link.class)){
			Class<?> classType;
			
			try {
				classType = Class.forName(jsonLink.getClassType());
				Object sourceObject = JsonPath.read(jsonData, jsonLink.getJsonPath());
				if(sourceObject != null){
					if(classType.equals(Integer.class)){
						if(sourceObject instanceof String)
							jsonElement = new JsonPrimitive(Integer.parseInt((String)sourceObject));
						else 
							jsonElement = new JsonPrimitive((Integer)sourceObject);
					}else if(classType.equals(String.class)){
						jsonElement = new JsonPrimitive((String)sourceObject);
					}
				}
				
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
			
			
		}
		
		
		return jsonElement;
	}

}
