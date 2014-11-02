package se.stonepath.framework.jsonlink.link;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;


public class Link{

	private String jsonPath;
	private String classType;
	public Link(String jsonPath,Class<?> classType){
		this.jsonPath = jsonPath;
		this.classType = classType.getName();
	}
	public Link(Class<?> classType){
		this.jsonPath = "";
		this.classType = classType.getName();
	}
	
	public static JsonElement createJsonElement(Link jsonLink){
		JsonObject jsonObject = new JsonObject();
		jsonObject.addProperty("jsonPath", jsonLink.getJsonPath());
		jsonObject.addProperty("classType", jsonLink.getClassType());
		return jsonObject;
	}
	
	public String getJsonPath(){
		return jsonPath;
	}
	public String getClassType(){
		return classType;
	}
}
