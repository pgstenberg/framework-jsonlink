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

/**
 * Class for building an Object from json-data
 * @author Per-Gustaf Stenberg
 *
 */

public class ObjectBuilder {
	
	/**
	 * Converts an Object to a string containing json-data
	 * @param object Object o be serialized
	 * @return jsonData
	 */
	public static String objectToJson(Object object){
		return new Gson().toJson(object);
	}
	
	/**
	 * Creator Gson-Class for json serialization, taking into 
	 * account the jsonsource in order to create links.
	 * 
	 * @param jsonSource the source as a string
	 * @return GsonSerializer
	 */
	private static Gson createGsonSource(String jsonSource){
		GsonBuilder gsonSourceBuilder = new GsonBuilder();
		gsonSourceBuilder.registerTypeAdapter(Link.class, new LinkSerializer(jsonSource));
		gsonSourceBuilder.registerTypeAdapter(LinkList.class, new LinkListSerializer(jsonSource));
		return gsonSourceBuilder.create();
	}
	
	/**
	 * Creating Gson-Class for json deserialization.
	 * @return GsonDeserializer
	 */
	private static Gson createGsonModel(){
		return new GsonBuilder().registerTypeAdapter(Link.class, new LinkDeserializer()).create();
	}
	
	/**
	 * Main createObject function.
	 * @param jsonSource The source as json
	 * @param modelFile The file that makes the model
	 * @param modelClassType ClassType of the Model
	 * @param targetClassType ClassType of the Object to be created
	 * @return The newly created Object
	 * @throws IOException throws if unable to read modelfile
	 * @throws ObjectBuildException throws if unable to build Object from given params
	 */
	public static <T> T createObject(String jsonSource,File modelFile,Class<?> modelClassType,Class<T> targetClassType) throws IOException, ObjectBuildException{
		String jsonModel = new String(Files.readAllBytes(modelFile.toPath()), StandardCharsets.UTF_8);		
		return createObject(jsonSource, jsonModel, modelClassType, targetClassType);
	}
	/**
	 * Creating Object with givin Object-class
	 * 
	 * @param objectSource The source in form of an Object.
	 * @param modelFile The file that makes the model
	 * @param modelClassType ClassType of the Model
	 * @param targetClassType ClassType of the Object to be created
	 * @return The newly created Object
	 * @throws IOException throws if unable to read modelfile
	 * @throws ObjectBuildException throws if unable to build Object from given params
	 */
	public static <T> T createObject(Object objectSource,File modelFile,Class<?> modelClassType,Class<T> targetClassType) throws IOException, ObjectBuildException{	
		return createObject(objectToJson(objectSource), modelFile, modelClassType, targetClassType);
	}
	/**
	 * Automatically creates new instance of the given Model-Class
	 * @param objectSource The source in form of an Object
	 * @param modelClassType ClassType of the Model
	 * @param targetClassType ClassType of the Object to be created
	 * @return The newly created Object
	 * @throws IOException throws if unable to read modelfile
	 * @throws ObjectBuildException throws if unable to build Object from given params
	 * @throws InstantiationException throws if unable to create new instance of model-class
	 * @throws IllegalAccessException throws if unable to create new instance of model-class
	 */
	public static <T> T createObject(Object objectSource,Class<?> modelClassType,Class<T> targetClassType) throws ObjectBuildException, InstantiationException, IllegalAccessException{
		return createObject(objectToJson(objectSource), objectToJson(modelClassType.newInstance()), modelClassType,targetClassType);	
	}
	/**
	 * Creating object from pure json-data.
	 * 
	 * @param jsonSource source as json-data
	 * @param jsonModel model as json-data
	 * @param modelClassType ClassType of the Model
	 * @param targetClassType ClassType of the Object to be created
	 * @return The newly created Object
	 * @throws ObjectBuildException throws if unable to create new Object
	 */
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
