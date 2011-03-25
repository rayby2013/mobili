package com.sjtu.is.mobili.video;

import com.sjtu.is.mobili.user.UserData;
import com.sjtu.is.mobili.utils.DogaInfoParser;

public class VideoData {
	private String aid;
	private String vid;
	private String url;
	private String imgUrl;
	private UserData uper;
	private String description;
	private String title;
	
	public VideoData(String url, String imgUrl){
		this.url = url;
		this.imgUrl = imgUrl;
		DogaInfoParser dip = new DogaInfoParser(url);
		aid = dip.getAid();
		vid = dip.getVid();
		uper = dip.getAuthor();
		title = dip.getTitle();
		description = dip.getDescription();
	}
	
	
	
	public String getTitle() {
		return title;
	}

	public String getImgUrl() {
		return imgUrl;
	}

	public String getAid() {
		return aid;
	}

	public String getVid() {
		return vid;
	}

	public String getUrl() {
		return url;
	}

	public UserData getUper() {
		return uper;
	}

	public String getDescription() {
		return description;
	}

}
