package se.stonepath.framework.jsonlink.list;

import java.util.Map;

import se.stonepath.framework.jsonlink.link.Link;

public class LinkListEntry implements Map.Entry<String, Link>{

	private String key;
	private Link jsonLink;
	public LinkListEntry(String key,Link jsonLink) {
		this.key = key;
		this.jsonLink = jsonLink;
	}
	
	@Override
	public String getKey() {
		return key;
	}

	@Override
	public Link getValue() {
		return jsonLink;
	}

	@Override
	public Link setValue(Link value) {
		Link old = this.jsonLink;
	    this.jsonLink = value;
	    return old;
	}

}
