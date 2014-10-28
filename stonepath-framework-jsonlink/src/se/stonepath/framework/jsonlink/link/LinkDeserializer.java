package se.stonepath.framework.jsonlink.link;

import java.lang.reflect.Type;
import java.util.Iterator;
import java.util.Map.Entry;
import java.util.Set;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

public class LinkDeserializer implements JsonDeserializer<Link>{

	@Override
	public Link deserialize(JsonElement jsonElement, Type type,
			JsonDeserializationContext context) throws JsonParseException {
	
			
			JsonObject jsonObject = jsonElement.getAsJsonObject();
			
			Set<Entry<String, JsonElement>> entrySet = jsonObject.entrySet();
			Iterator<Entry<String,JsonElement>> iterator = entrySet.iterator();
			
			boolean jsonPathFound = false;
			boolean classTypeFound = false;
			
			while(iterator.hasNext()){
				Entry<String,JsonElement> entry = iterator.next();
				
				if(entry.getKey().equals("jsonPath"))
					jsonPathFound = true;
				if(entry.getKey().equals("classType"))
					classTypeFound = true;
				
			}
			
			if(jsonPathFound && classTypeFound){
				try {
					
					return new Link(jsonObject.get("jsonPath").getAsString(), Class.forName(jsonObject.get("classType").getAsString()));
				} catch (ClassNotFoundException e) {
					throw new JsonParseException(e.getMessage());
				}
			}
				
		
		return null;
	}

}
