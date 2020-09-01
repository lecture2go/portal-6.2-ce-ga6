/*******************************************************************************
 * License
 * 
 * +++ +++ +++ +++ +++ +++ +++ +++ +++ +++ +++ +++ +++ +++ +++ +++ +++ +++ +++ +++ +++
 * 
 * Lecture2Go 
 * 
 * The Lecture2Go software is based on the liferay portal 6.2-ga6
 * <http://www.liferay.com> (Copyright notice see below)
 * Lecture2Go <http://lecture2go.uni-hamburg.de> is an open source
 * platform for media management and distribution. Our goal is to
 * support the free access to knowledge because this is a component
 * of each democratic society. The open source software is aimed at
 * academic institutions and has to strengthen the blended learning.
 * All Lecture2Go plugins are continuously being developed and improved.
 * For more details please visit <http://lecture2go-open-source.rrz.uni-hamburg.de>
 * Copyright (c) 2013 - present University of Hamburg / Computer and Data Center (RRZ)
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 * You should have received a copy of the GNU General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 *  
 * +++ +++ +++ +++ +++ +++ +++ +++ +++ +++ +++ +++ +++ +++ +++ +++ +++ +++ +++ +++ +++
 * 
 * The Liferay Plugins SDK:
 * 
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 * 
 * +++ +++ +++ +++ +++ +++ +++ +++ +++ +++ +++ +++ +++ +++ +++ +++ +++ +++ +++ +++ +++
 * 
 * Third Party Software
 * 
 * Lecture2Go uses third-party libraries which may be distributed under different licenses
 * to the above (but are compatible with the used GPL license). Informations about these
 * licenses and copyright informations are mostly detailed in the library source code or jars themselves.
 * You must agree to the terms of these licenses, in addition tothe above Lecture2Go source code license,
 * in order to use this software.
 ******************************************************************************/
package de.uhh.l2g.plugins.util;

/***************************************************************************
 * The Lecture2Go software is based on the liferay portal 6.1.1
 * <http://www.liferay.com>
 *
 * Lecture2Go <http://lecture2go.uni-hamburg.de> is an open source
 * platform for media management and distribution. Our goal is to
 * support the free access to knowledge because this is a component
 * of each democratic society. The open source software is aimed at
 * academic institutions and has to strengthen the blended learning.
 *
 * All Lecture2Go plugins are continuously being developed and improved.
 * For more details please visit <http://lecture2go-open-source.rrz.uni-hamburg.de>
 *
 * @Autor Lecture2Go Team
 * @Version 1.0
 * @Contact lecture2go-open-source@uni-hamburg.de
 *
 * Copyright (c) 2013 University of Hamburg / Computer and Data Center (RRZ)
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 ***************************************************************************/
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.util.PropsUtil;

import de.uhh.l2g.plugins.model.Host;
import de.uhh.l2g.plugins.model.Producer;
import de.uhh.l2g.plugins.model.Video;
import de.uhh.l2g.plugins.service.HostLocalServiceUtil;
import de.uhh.l2g.plugins.service.ProducerLocalServiceUtil;
import de.uhh.l2g.plugins.service.VideoLocalServiceUtil;

/**
 * The Class FFmpegManager.
 */
public class FFmpegManager {
	/**
	 * Instantiates a new f fmpeg manager.
	 *
	 * @param filename
	 *            the filename
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 */
	public FFmpegManager(String filename) throws IOException {
		ProcessBuilder pb = new ProcessBuilder(PropsUtil.get("lecture2go.ffmpeg.bin"), "-i", filename);
		Process p = pb.start();
		InputStream errorStream = p.getErrorStream();
		BufferedReader in = new BufferedReader(new InputStreamReader(errorStream));
		String[] out = new String[40];// array buffer
		String s = "";
		String fileContainer = filename.split("\\.")[filename.split("\\.").length - 1].toLowerCase().trim();
		int i = 0;
		while ((s = in.readLine()) != null) {
			out[i] = s;
			i++;
		}
		videoMetadata = out;
		for (int j = 0; j < out.length; j++) {
			String line = out[j];
			// duration
			try {
				videoDuration = line.split("Duration:")[1].split(",")[0];
			} catch (Exception e) {
			}
			// bitrate
			try {
				videoBitRate = line.split("Video:")[1].split(",")[3].trim().split(" ")[0];
			} catch (Exception e) {
			}
			// resolution
			try {
				videoResolution = line.split("Video:")[1].split(",")[2].trim();
			} catch (Exception e) {
			}
			// video codec
			try {
				videoCodec = line.split("Video:")[1].split(",")[0].trim();
			} catch (Exception e) {
			}
			// audio codec
			try {
				audioCodec = line.split("Audio:")[1].split(",")[0].trim();
			} catch (Exception e) {
			}
			// converting required?
			try {
				int br = new Integer(videoBitRate);
				if (fileContainer.contains("mp4") && br <= 650 && videoCodec.toLowerCase().trim().contains("h264") && audioCodec.toLowerCase().trim().contains("aac")) {
					convertingRequierd = false;
				} else {
					convertingRequierd = true;
				}
			} catch (NumberFormatException nfe) {
				// convertingRequierd = true;
			}
		}
	}

	private boolean convertingRequierd = false;

	public boolean convertingRequierd() {
		return convertingRequierd;
	}

	public void setConvertingRequierd(boolean convertingRequierd) {
		this.convertingRequierd = convertingRequierd;
	}

	/**
	 * Instantiates a new f fmpeg manager.
	 */
	public FFmpegManager() {
	}

	private static boolean createImageRepositoryFolder(){
		boolean ret = false;
		String imgRep = PropsUtil.get("lecture2go.images.system.path");
		File imgRepFolder = new File(imgRep);
		if(!imgRepFolder.exists()){
			if(imgRepFolder.mkdir()){
				ret = true;
			}
		}
		return ret;
	}
	
	private static boolean imageRepositoryFolderExists(){
		boolean ret = false;
		String imgRep = PropsUtil.get("lecture2go.images.system.path");
		File imgRepFolder = new File(imgRep);
		if(imgRepFolder.exists()){
			ret = true;
		}
		return ret;
	}
	/**
	 * Creates the thumbnail.
	 *
	 * @param video
	 *            the video
	 * @param time
	 *            the time
	 * @param thumbnailLocation
	 *            the thumbnail location
	 * @return true, if successful
	 * @throws SystemException 
	 * @throws PortalException 
	 */
	public static boolean createThumbnail(Video video, String time, String thumbnailLocation) throws PortalException, SystemException {
		boolean ret = false;
		if(!imageRepositoryFolderExists()) createImageRepositoryFolder();
		
		if(imageRepositoryFolderExists()){
			Producer producer = ProducerLocalServiceUtil.getProducer(video.getProducerId());
			Host host = HostLocalServiceUtil.getHost(video.getHostId());
			Runtime runCmd = Runtime.getRuntime();
			String command = "";
			int sec = new Integer(time.split(":")[0]) * 60 * 60 + new Integer(time.split(":")[1]) * 60 + new Integer(time.split(":")[2]);
			if (video.getOpenAccess()==1)
				command = PropsUtil.get("lecture2go.ffmpeg.bin") + " -ss " + sec + " -i " + PropsUtil.get("lecture2go.media.repository") + "/" + host.getDirectory() + "/" + producer.getHomeDir() + "/" + video.getFilename() + " -f image2 -vframes 1 -filter:v scale='130:-1' " + thumbnailLocation + "/" + video.getVideoId() + "_"+ sec + ".jpg";
			else
				command = PropsUtil.get("lecture2go.ffmpeg.bin") + " -ss " + sec + " -i " + PropsUtil.get("lecture2go.media.repository") + "/" + host.getDirectory() + "/" + producer.getHomeDir() + "/" + video.getSecureFilename() + " -f image2 -vframes 1 -filter:v scale='130:-1' " + thumbnailLocation + "/" + video.getVideoId()+ "_" + sec + ".jpg";
			try {
				runCmd.exec(command);
				ret = true;
			} catch (IOException e) {
				ret = false;
			}			
		}
		return ret;
	}

	public static boolean createThumbnail(String fileLocation, String thumbnailLocation) {
		return createThumbnail(fileLocation, thumbnailLocation, 13);
	}
	/**
	 * Creates the thumbnail.
	 *
	 * @param fileLocation
	 *            the file location
	 * @param thumbnailLocation
	 *            the thumbnail location
	 * @return true, if successful
	 */
	public static boolean createThumbnail(String fileLocation, String thumbnailLocation, float sec) {
		Runtime runCmd = Runtime.getRuntime();
		String thumbPreffLoc = thumbnailLocation.split(".jpg")[0];
		String command = PropsUtil.get("lecture2go.ffmpeg.bin") + " -noaccurate_seek -ss "+sec+" -i " + fileLocation + " -f image2 -vframes 1 " + thumbnailLocation + " -y";
		String command1 = PropsUtil.get("lecture2go.ffmpeg.bin") + " -noaccurate_seek -ss "+sec+" -i " + fileLocation + " -f image2 -vframes 1 -filter:v scale='130:-1' " + thumbPreffLoc + "_s.jpg -y";
		String command2 = PropsUtil.get("lecture2go.ffmpeg.bin") + " -noaccurate_seek -ss "+sec+" -i " + fileLocation + " -f image2 -vframes 1 -filter:v scale='300:-1' " + thumbPreffLoc + "_m.jpg -y";
		boolean ret = true;
		if(!imageRepositoryFolderExists()) createImageRepositoryFolder();
		
		if(imageRepositoryFolderExists()){
			try {
				File f = new File(thumbnailLocation);
				runCmd.exec(command);
				if (!f.isFile()) ret = false;
			} catch (IOException e) {
				ret = false;
			}
			try {
				File f = new File(thumbPreffLoc + "_s.jpg");
				runCmd.exec(command1);
				if (!f.isFile()) ret = false;
			} catch (IOException e) {
				ret = false;
			}
			try {
				File f = new File(thumbPreffLoc + "_m.jpg");
				runCmd.exec(command2);
				if (!f.isFile()) ret = false;
			} catch (IOException e) {
				ret = false;
			}
		}
		return ret;
	}

	/**
	 * Thumbnails exists.
	 *
	 * @param video
	 *            the video
	 * @return true, if successful
	 */
	public static boolean thumbnailsExists(Video video) {
		boolean ret = true;
		String preffix = "";
		if (video.getOpenAccess()==1)
			preffix = video.getPreffix();
		else
			preffix = video.getSPreffix();
		File fJpg = new File(PropsUtil.get("lecture2go.images.system.path") + "/" + preffix + ".jpg");
		File fJpgm = new File(PropsUtil.get("lecture2go.images.system.path") + "/" + preffix + "_m.jpg");
		File fJpgs = new File(PropsUtil.get("lecture2go.images.system.path") + "/" + preffix + "_s.jpg");
		if (!fJpg.isFile() || !fJpgs.isFile() || !fJpgm.isFile())
			ret = false;
		return ret;
	}

	/** The video duration. */
	private String videoDuration = "";

	/**
	 * Gets the video duration.
	 *
	 * @return the video duration
	 */
	public String getVideoDuration() {
		return videoDuration;
	}

	/** The video resolution. */
	private String videoResolution = "";

	/**
	 * Gets the video resolution.
	 *
	 * @return the video resolution
	 */
	public String getVideoResolution() {
		return videoResolution;
	}

	/**
	 * Sets the video resolution.
	 *
	 * @param videoResolution
	 *            the new video resolution
	 */
	public void setVideoResolution(String videoResolution) {
		this.videoResolution = videoResolution;
	}

	/** The video bit rate. */
	private String videoBitRate = "";

	/**
	 * Gets the video bit rate.
	 *
	 * @return the video bit rate
	 */
	public String getVideoBitRate() {
		return videoBitRate;
	}

	/**
	 * Sets the video bit rate.
	 *
	 * @param videoBitRate
	 *            the new video bit rate
	 */
	public void setVideoBitRate(String videoBitRate) {
		this.videoBitRate = videoBitRate;
	}

	/** The video metadata. */
	private String[] videoMetadata;

	/**
	 * Gets the video metadata.
	 *
	 * @return the video metadata
	 */
	public String[] getVideoMetadata() {
		return videoMetadata;
	}

	/**
	 * Sets the video metadata.
	 *
	 * @param videoMetadata
	 *            the new video metadata
	 */
	public void setVideoMetadata(String[] videoMetadata) {
		this.videoMetadata = videoMetadata;
	}

	/**
	 * Sets the video duration.
	 *
	 * @param videoDuration
	 *            the new video duration
	 */
	public void setVideoDuration(String videoDuration) {
		this.videoDuration = videoDuration;
	}

	/** The video codec. */
	private String audioCodec = "";

	public String getAudioCodec() {
		return audioCodec;
	}

	public void setAudioCodec(String audioCodec) {
		this.audioCodec = audioCodec;
	}

	/** The video codec. */
	private String videoCodec = "";

	/**
	 * Gets the video codec.
	 *
	 * @return the video codec
	 */
	public String getVideoCodec() {
		return videoCodec;
	}

	/**
	 * Sets the video codec.
	 *
	 * @param videoCodec
	 *            the new video codec
	 */
	public void setVideoCodec(String videoCodec) {
		this.videoCodec = videoCodec;
	}

	/**
	 * Update ffmpeg metadata.
	 *
	 * @param video the video
	 * @param model the model
	 * @throws IOException Signals that an I/O exception has occurred.
	 * @throws SystemException 
	 * @throws PortalException 
	 */
	public static void updateFfmpegMetadata(Video video) throws IOException, PortalException, SystemException {
		Host host = HostLocalServiceUtil.getHost(video.getHostId());
		Producer producer = ProducerLocalServiceUtil.getProducer(video.getProducerId());
		// FFMPEG
		try {
			String videopfad = "";
			if (video.getOpenAccess()==1)
				videopfad = PropsUtil.get("lecture2go.media.repository") + "/" + host.getDirectory() + "/" + producer.getHomeDir() + "/" + video.getFilename();
			else
				videopfad = PropsUtil.get("lecture2go.media.repository") + "/" + host.getDirectory() + "/" + producer.getHomeDir() + "/" + video.getSecureFilename();
			FFmpegManager ffmpegp = new FFmpegManager(videopfad);
			video.setDuration(ffmpegp.getVideoDuration());
			video.setResolution(ffmpegp.getVideoResolution());
			
			video.setBitrate(ffmpegp.getVideoBitRate());
			video.setFileSize(new File(videopfad).length()+"");
			
			//update video object
			VideoLocalServiceUtil.updateVideo(video);
		} catch (NullPointerException e) {
			////e.printStackTrace();
		}
	}
}
