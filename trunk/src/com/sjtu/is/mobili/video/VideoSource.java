package com.sjtu.is.mobili.video;

public class VideoSource {
	private Integer order;
	private Integer length;
	private String url;
	
	public VideoSource(Integer order, Integer length, String url){
		this.order = order;
		this.length = length;
		this.url = url;
	}

	public Integer getOrder() {
		return order;
	}

	public Integer getLength() {
		return length;
	}

	public String getUrl() {
		return url;
	}
	
	
}
