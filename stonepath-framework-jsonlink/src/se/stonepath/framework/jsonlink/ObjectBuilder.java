package se.stonepath.framework.jsonlink;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;

import se.stonepath.framework.jsonlink.link.Link;
import se.stonepath.framework.jsonlink.link.LinkDeserializer;
import se.stonepath.framework.jsonlink.link.LinkSerializer;
import se.stonepath.framework.jsonlink.list.LinkList;
import se.stonepath.framework.jsonlink.list.LinkListSerializer;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;


public class ObjectBuilder {
	
	
	public static String objectToJson(Object object){
		return new Gson().toJson(object);
	}
	
	private static Gson createGsonSource(String jsonSource){
		GsonBuilder gsonSourceBuilder = new GsonBuilder();
		gsonSourceBuilder.registerTypeAdapter(Link.class, new LinkSerializer(jsonSource));
		gsonSourceBuilder.registerTypeAdapter(LinkList.class, new LinkListSerializer(jsonSource));
		return gsonSourceBuilder.create();
	}
	private static Gson createGsonModel(){
		return new GsonBuilder().registerTypeAdapter(Link.class, new LinkDeserializer()).create();
	}
	
	
	public static <T> T createObject(String jsonSource,File modelFile,Class<?> modelClassType,Class<T> targetClassType) throws IOException, ObjectBuildException{
		String jsonModel = new String(Files.readAllBytes(modelFile.toPath()), StandardCharsets.UTF_8);		
		return createObject(jsonSource, jsonModel, modelClassType, targetClassType);
	}
	public static <T> T createObject(Object objectSource,File modelFile,Class<?> modelClassType,Class<T> targetClassType) throws IOException, ObjectBuildException{
			
		return createObject(objectToJson(objectSource), modelFile, modelClassType, targetClassType);
	}
	
	public static <T> T createObject(Object objectSource,Class<?> modelClassType,Class<T> targetClassType) throws ObjectBuildException, InstantiationException, IllegalAccessException{
		return createObject(objectToJson(objectSource), objectToJson(modelClassType.newInstance()), modelClassType,targetClassType);	
	}
	
	
	public static <T> T createObject(String jsonSource,String jsonModel,Class<?> modelClassType,Class<T> targetClassType) throws ObjectBuildException{
				
		Gson gsonSource = createGsonSource(jsonSource);
		Gson gsonModel = createGsonModel();
		
		try{
			return new Gson().fromJson(gsonSource.toJson(gsonModel.fromJson(jsonModel,modelClassType)), targetClassType);
		}catch(Exception e){
			e.printStackTrace();
			throw new ObjectBuildException(e);
		}
	}
	

}
