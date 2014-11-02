package se.stonepath.framework.jsonlink.list;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import com.jayway.jsonpath.JsonPath;

public class LinkListSerializer implements JsonSerializer<LinkList>{

	private String jsonData;
	public LinkListSerializer(String jsonData) {
		this.jsonData = jsonData;
	}
	
	
	@Override
	public JsonElement serialize(LinkList jsonLinkList, Type type,
			JsonSerializationContext context) {
		
		Iterator<LinkListEntry> iterator =jsonLinkList.iterator();
		
		JsonArray jsonArray = new JsonArray();
		
		HashMap<String,List<?>> objectMap = new HashMap<String,List<?>>();
		
		int biggestList = 0;
		while(iterator.hasNext()){
			LinkListEntry entry = iterator.next();
			
			
			if(JsonPath.read(jsonData, entry.getValue().getJsonPath()) instanceof List<?>){
				List<?> list = JsonPath.read(jsonData, entry.getValue().getJsonPath());
				objectMap.put(entry.getKey(),list);
				if(list.size() > biggestList){
					biggestList = list.size();
				}
			}
		}
		
		for(int i = 0; i < biggestList; i++){
			JsonObject jsonObject = new JsonObject();
			Iterator<Entry<String, List<?>>> objectIterator = objectMap.entrySet().iterator();
			
			while(objectIterator.hasNext()){
				Entry<String, List<?>> objectEntry = objectIterator.next();
				if(objectEntry.getValue().iterator().hasNext()){
					Class<?> classType = objectEntry.getValue().iterator().next().getClass();
					
					if(objectEntry.getValue().size() > i){
						if(classType.equals(String.class)){
							jsonObject.addProperty(objectEntry.getKey(), (String) objectEntry.getValue().get(i));
						}else if(classType.equals(Integer.class)){
							jsonObject.addProperty(objectEntry.getKey(), (Integer) objectEntry.getValue().get(i));
						}
					}
				}
				
			}
			
			jsonArray.add(jsonObject);
		}
		
		
		return jsonArray;
	}

}
