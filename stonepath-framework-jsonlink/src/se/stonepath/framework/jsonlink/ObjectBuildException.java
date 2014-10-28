package se.stonepath.framework.jsonlink;

public class ObjectBuildException extends Exception{
	
	private static final long serialVersionUID = 1L;

	public ObjectBuildException(Exception parentException){
		super(parentException.getMessage());
	}
}
