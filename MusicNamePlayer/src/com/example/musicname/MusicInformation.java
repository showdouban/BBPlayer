/**
 * 
 */
package com.example.musicname;

/**
 * Title: MusicInformation.java
 * Description: 
 * Copyright:Copyright(c)2015
 * Company: 四川久聚科技股份有限公司
 * CreateTime:2015-8-31 下午1:44:20 
 * @author liuwenxing
 * @version 1.0
 */
public class MusicInformation {
	private String musicName;
	private String musicPath;
	private String musicPlayer;
	private int MusicTime;
	/**
	 * @return the musicPlayer
	 */
	public String getMusicPlayer() {
		return musicPlayer;
	}
	/**
	 * @param musicPlayer the musicPlayer to set
	 */
	public void setMusicPlayer(String musicPlayer) {
		this.musicPlayer = musicPlayer;
	}
	/**
	 * @return the musicTime
	 */
	public int getMusicTime() {
		return MusicTime;
	}
	/**
	 * @param musicTime the musicTime to set
	 */
	public void setMusicTime(int musicTime) {
		MusicTime = musicTime;
	}
	/**
	 * @return the musicName
	 */
	public String getMusicName() {
		return musicName;
	}
	/**
	 * @param musicName the musicName to set
	 */
	public void setMusicName(String musicName) {
		this.musicName = musicName;
	}
	/**
	 * @return the musicPath
	 */
	public String getMusicPath() {
		return musicPath;
	}
	/**
	 * @param musicPath the musicPath to set
	 */
	public void setMusicPath(String musicPath) {
		this.musicPath = musicPath;
	}
	
}
