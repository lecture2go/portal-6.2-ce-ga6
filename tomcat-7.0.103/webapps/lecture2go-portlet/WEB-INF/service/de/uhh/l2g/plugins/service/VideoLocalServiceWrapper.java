/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package de.uhh.l2g.plugins.service;

import com.liferay.portal.service.ServiceWrapper;

/**
 * Provides a wrapper for {@link VideoLocalService}.
 *
 * @author Iavor Sturm
 * @see VideoLocalService
 * @generated
 */
public class VideoLocalServiceWrapper implements VideoLocalService,
	ServiceWrapper<VideoLocalService> {
	public VideoLocalServiceWrapper(VideoLocalService videoLocalService) {
		_videoLocalService = videoLocalService;
	}

	/**
	* Adds the video to the database. Also notifies the appropriate model listeners.
	*
	* @param video the video
	* @return the video that was added
	* @throws SystemException if a system exception occurred
	*/
	@Override
	public de.uhh.l2g.plugins.model.Video addVideo(
		de.uhh.l2g.plugins.model.Video video)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _videoLocalService.addVideo(video);
	}

	/**
	* Creates a new video with the primary key. Does not add the video to the database.
	*
	* @param videoId the primary key for the new video
	* @return the new video
	*/
	@Override
	public de.uhh.l2g.plugins.model.Video createVideo(long videoId) {
		return _videoLocalService.createVideo(videoId);
	}

	/**
	* Deletes the video with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param videoId the primary key of the video
	* @return the video that was removed
	* @throws PortalException if a video with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	@Override
	public de.uhh.l2g.plugins.model.Video deleteVideo(long videoId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _videoLocalService.deleteVideo(videoId);
	}

	/**
	* Deletes the video from the database. Also notifies the appropriate model listeners.
	*
	* @param video the video
	* @return the video that was removed
	* @throws SystemException if a system exception occurred
	*/
	@Override
	public de.uhh.l2g.plugins.model.Video deleteVideo(
		de.uhh.l2g.plugins.model.Video video)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _videoLocalService.deleteVideo(video);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery() {
		return _videoLocalService.dynamicQuery();
	}

	/**
	* Performs a dynamic query on the database and returns the matching rows.
	*
	* @param dynamicQuery the dynamic query
	* @return the matching rows
	* @throws SystemException if a system exception occurred
	*/
	@Override
	@SuppressWarnings("rawtypes")
	public java.util.List dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _videoLocalService.dynamicQuery(dynamicQuery);
	}

	/**
	* Performs a dynamic query on the database and returns a range of the matching rows.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link de.uhh.l2g.plugins.model.impl.VideoModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param dynamicQuery the dynamic query
	* @param start the lower bound of the range of model instances
	* @param end the upper bound of the range of model instances (not inclusive)
	* @return the range of matching rows
	* @throws SystemException if a system exception occurred
	*/
	@Override
	@SuppressWarnings("rawtypes")
	public java.util.List dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end) throws com.liferay.portal.kernel.exception.SystemException {
		return _videoLocalService.dynamicQuery(dynamicQuery, start, end);
	}

	/**
	* Performs a dynamic query on the database and returns an ordered range of the matching rows.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link de.uhh.l2g.plugins.model.impl.VideoModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param dynamicQuery the dynamic query
	* @param start the lower bound of the range of model instances
	* @param end the upper bound of the range of model instances (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching rows
	* @throws SystemException if a system exception occurred
	*/
	@Override
	@SuppressWarnings("rawtypes")
	public java.util.List dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _videoLocalService.dynamicQuery(dynamicQuery, start, end,
			orderByComparator);
	}

	/**
	* Returns the number of rows that match the dynamic query.
	*
	* @param dynamicQuery the dynamic query
	* @return the number of rows that match the dynamic query
	* @throws SystemException if a system exception occurred
	*/
	@Override
	public long dynamicQueryCount(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _videoLocalService.dynamicQueryCount(dynamicQuery);
	}

	/**
	* Returns the number of rows that match the dynamic query.
	*
	* @param dynamicQuery the dynamic query
	* @param projection the projection to apply to the query
	* @return the number of rows that match the dynamic query
	* @throws SystemException if a system exception occurred
	*/
	@Override
	public long dynamicQueryCount(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery,
		com.liferay.portal.kernel.dao.orm.Projection projection)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _videoLocalService.dynamicQueryCount(dynamicQuery, projection);
	}

	@Override
	public de.uhh.l2g.plugins.model.Video fetchVideo(long videoId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _videoLocalService.fetchVideo(videoId);
	}

	/**
	* Returns the video with the primary key.
	*
	* @param videoId the primary key of the video
	* @return the video
	* @throws PortalException if a video with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	@Override
	public de.uhh.l2g.plugins.model.Video getVideo(long videoId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _videoLocalService.getVideo(videoId);
	}

	@Override
	public com.liferay.portal.model.PersistedModel getPersistedModel(
		java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _videoLocalService.getPersistedModel(primaryKeyObj);
	}

	/**
	* Returns a range of all the videos.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link de.uhh.l2g.plugins.model.impl.VideoModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of videos
	* @param end the upper bound of the range of videos (not inclusive)
	* @return the range of videos
	* @throws SystemException if a system exception occurred
	*/
	@Override
	public java.util.List<de.uhh.l2g.plugins.model.Video> getVideos(int start,
		int end) throws com.liferay.portal.kernel.exception.SystemException {
		return _videoLocalService.getVideos(start, end);
	}

	/**
	* Returns the number of videos.
	*
	* @return the number of videos
	* @throws SystemException if a system exception occurred
	*/
	@Override
	public int getVideosCount()
		throws com.liferay.portal.kernel.exception.SystemException {
		return _videoLocalService.getVideosCount();
	}

	/**
	* Updates the video in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	*
	* @param video the video
	* @return the video that was updated
	* @throws SystemException if a system exception occurred
	*/
	@Override
	public de.uhh.l2g.plugins.model.Video updateVideo(
		de.uhh.l2g.plugins.model.Video video)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _videoLocalService.updateVideo(video);
	}

	/**
	* Returns the Spring bean ID for this bean.
	*
	* @return the Spring bean ID for this bean
	*/
	@Override
	public java.lang.String getBeanIdentifier() {
		return _videoLocalService.getBeanIdentifier();
	}

	/**
	* Sets the Spring bean ID for this bean.
	*
	* @param beanIdentifier the Spring bean ID for this bean
	*/
	@Override
	public void setBeanIdentifier(java.lang.String beanIdentifier) {
		_videoLocalService.setBeanIdentifier(beanIdentifier);
	}

	@Override
	public java.lang.Object invokeMethod(java.lang.String name,
		java.lang.String[] parameterTypes, java.lang.Object[] arguments)
		throws java.lang.Throwable {
		return _videoLocalService.invokeMethod(name, parameterTypes, arguments);
	}

	@Override
	public java.util.List<de.uhh.l2g.plugins.model.Video> getByOpenAccess(
		int bool) throws com.liferay.portal.kernel.exception.SystemException {
		return _videoLocalService.getByOpenAccess(bool);
	}

	@Override
	public int getByOpenAccessAndUploadedFile(int bool)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _videoLocalService.getByOpenAccessAndUploadedFile(bool);
	}

	@Override
	public de.uhh.l2g.plugins.model.Video getLatestOpenAccessVideoForLectureseries(
		java.lang.Long lectureseriesId) {
		return _videoLocalService.getLatestOpenAccessVideoForLectureseries(lectureseriesId);
	}

	@Override
	public java.util.List<de.uhh.l2g.plugins.model.Video> getByTerm(
		java.lang.Long termId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _videoLocalService.getByTerm(termId);
	}

	@Override
	public int unlinkLectureseriesFromVideos(java.lang.Long lectureseriesId) {
		return _videoLocalService.unlinkLectureseriesFromVideos(lectureseriesId);
	}

	@Override
	public java.util.List<de.uhh.l2g.plugins.model.Video> getByRootInstitution(
		java.lang.Long rootInstitutionId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _videoLocalService.getByRootInstitution(rootInstitutionId);
	}

	@Override
	public java.util.List<de.uhh.l2g.plugins.model.Video> getByFilename(
		java.lang.String filename)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _videoLocalService.getByFilename(filename);
	}

	@Override
	public java.util.List<de.uhh.l2g.plugins.model.Video> getByProducer(
		java.lang.Long producerId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _videoLocalService.getByProducer(producerId);
	}

	@Override
	public java.util.List<de.uhh.l2g.plugins.model.Video> getByLectureseries(
		java.lang.Long lectureseriesId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _videoLocalService.getByLectureseries(lectureseriesId);
	}

	@Override
	public int countByLectureseries(java.lang.Long lectureseriesId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _videoLocalService.countByLectureseries(lectureseriesId);
	}

	@Override
	public java.util.List<de.uhh.l2g.plugins.model.Video> getByProducerAndLectureseries(
		java.lang.Long producerId, java.lang.Long lectureseriesId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _videoLocalService.getByProducerAndLectureseries(producerId,
			lectureseriesId);
	}

	@Override
	public java.util.List<de.uhh.l2g.plugins.model.Video> getByProducerAndDownloadLink(
		java.lang.Long producerId, int downloadLink)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _videoLocalService.getByProducerAndDownloadLink(producerId,
			downloadLink);
	}

	@Override
	public java.util.List<de.uhh.l2g.plugins.model.Video> getPopular(int limit) {
		return _videoLocalService.getPopular(limit);
	}

	@Override
	public java.util.List<de.uhh.l2g.plugins.model.Video> getLatestVideos() {
		return _videoLocalService.getLatestVideos();
	}

	@Override
	public void createThumbnailsIfNotExisting(java.lang.Long videoId) {
		_videoLocalService.createThumbnailsIfNotExisting(videoId);
	}

	@Override
	public void createSymLinkToDownloadableFileIfNotExisting(
		java.lang.Long videoId) {
		_videoLocalService.createSymLinkToDownloadableFileIfNotExisting(videoId);
	}

	@Override
	public org.json.JSONArray getJSONVideo(java.lang.Long videoId) {
		return _videoLocalService.getJSONVideo(videoId);
	}

	@Override
	public void createLastVideoList()
		throws com.liferay.portal.kernel.exception.SystemException {
		_videoLocalService.createLastVideoList();
	}

	@Override
	public int countByLectureseriesAndOpenaccess(
		java.lang.Long lectureseriesId, int openAccess)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _videoLocalService.countByLectureseriesAndOpenaccess(lectureseriesId,
			openAccess);
	}

	@Override
	public java.util.List<de.uhh.l2g.plugins.model.Video> getByLectureseriesAndOpenaccess(
		java.lang.Long lectureseriesId, int openAccess)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _videoLocalService.getByLectureseriesAndOpenaccess(lectureseriesId,
			openAccess);
	}

	/**
	* required properties for jwplayer in portal-ext.properties file
	*
	* [host]=configured host in database (automatically e.g. streaming.server.com)
	* [ext]=file extension (automatically e.g mp3)
	* [l2go_path]=generated lecture2go file path (automatically e.g. 3l2gproducer1)
	* [filename]=video file name (automatically e.g 00.000_video_2015-06-08_08-06.mp4)
	* [protocol]=host protocol (automatically e.g rtmpt)
	* [port]=host port (automatically e.g 80)
	* [smilfile]=adaptive streaming file
	*
	* example for lecture2go configuration
	* lecture2go.uri1.player.template=https://[host]/vod/_definst/smil:[l2go_path]/[smilfile]/playlist.m3u8
	* lecture2go.uri2.player.template=https://[host]/vod/_definst/[ext]:[l2go_path]/[filename]/playlist.m3u8
	* lecture2go.uri3.player.template=rtmpt://[host]/vod/_definst/[ext]:[l2go_path]/[filename]
	* lecture2go.uri4.player.template=${lecture2go.downloadserver.web.root}/abo/[filename]
	* lecture2go.uri5.player.template=rtsp://[host]:[port]/vod/_definst/[ext]:[l2go_path]/[filename]
	*/
	@Override
	public void addPlayerUris2Video(de.uhh.l2g.plugins.model.Video video) {
		_videoLocalService.addPlayerUris2Video(video);
	}

	/**
	* This adds the "tracks" section for the video player json if there are any captions or chapters and sets the label to
	* language of the caption file (translated to the userLocale)
	*/
	@Override
	public void addTextTracks2VideoWithLanguageLabel(
		de.uhh.l2g.plugins.model.Video video, java.util.Locale userLocale) {
		_videoLocalService.addTextTracks2VideoWithLanguageLabel(video,
			userLocale);
	}

	/**
	* This adds the "tracks" section for the video player json if there are any captions or chapters
	*/
	@Override
	public void addTextTracks2Video(de.uhh.l2g.plugins.model.Video video) {
		_videoLocalService.addTextTracks2Video(video);
	}

	@Override
	public de.uhh.l2g.plugins.model.Video getBySecureUrl(java.lang.String surl)
		throws com.liferay.portal.kernel.exception.SystemException,
			de.uhh.l2g.plugins.NoSuchVideoException {
		return _videoLocalService.getBySecureUrl(surl);
	}

	@Override
	public java.util.List<de.uhh.l2g.plugins.model.Video> getAll()
		throws com.liferay.portal.kernel.exception.SystemException {
		return _videoLocalService.getAll();
	}

	@Override
	public java.util.List<de.uhh.l2g.plugins.model.Video> getBySearchWord(
		java.lang.String word, int limit)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _videoLocalService.getBySearchWord(word, limit);
	}

	@Override
	public java.util.List<de.uhh.l2g.plugins.model.Video> getByAllSearchWords()
		throws com.liferay.portal.kernel.exception.SystemException {
		return _videoLocalService.getByAllSearchWords();
	}

	@Override
	public java.util.List<de.uhh.l2g.plugins.model.Video> getBySearchWordAndLectureseriesId(
		java.lang.String word, java.lang.Long lectureseriesId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _videoLocalService.getBySearchWordAndLectureseriesId(word,
			lectureseriesId);
	}

	@Override
	public java.util.List<de.uhh.l2g.plugins.model.Video> getByHits(
		java.lang.Long hits) {
		return _videoLocalService.getByHits(hits);
	}

	@Override
	public java.util.List<de.uhh.l2g.plugins.model.Video> getByHitsAndOpenAccess(
		java.lang.Long hits) {
		return _videoLocalService.getByHitsAndOpenAccess(hits);
	}

	@Override
	public java.util.List<de.uhh.l2g.plugins.model.Video> getByHits() {
		return _videoLocalService.getByHits();
	}

	@Override
	public de.uhh.l2g.plugins.model.Video incrementHitCounter(
		de.uhh.l2g.plugins.model.Video video)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _videoLocalService.incrementHitCounter(video);
	}

	@Override
	public java.lang.Long getLatestClosedAccessVideoId(
		java.lang.Long lectureseriesId) {
		return _videoLocalService.getLatestClosedAccessVideoId(lectureseriesId);
	}

	/**
	* Checks if the video has a related smil-file in the file system
	*/
	@Override
	public boolean checkSmilFile(de.uhh.l2g.plugins.model.Video video) {
		return _videoLocalService.checkSmilFile(video);
	}

	@Override
	public boolean fileStringSegmentFoundInArray(java.lang.String file,
		org.json.JSONArray jsonArray) {
		return _videoLocalService.fileStringSegmentFoundInArray(file, jsonArray);
	}

	/**
	* Creates a symlink for the caption of the video to to captions folder
	*/
	@Override
	public void createSymLinkForCaptionIfExisting(java.lang.Long videoId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_videoLocalService.createSymLinkForCaptionIfExisting(videoId);
	}

	/**
	* Checks if file is a symoblic link
	*
	* @param file the file to check
	* @return true if file is sym link, false if not
	* @throws IOException
	*/
	@Override
	public boolean isSymlink(java.io.File file) throws java.io.IOException {
		return _videoLocalService.isSymlink(file);
	}

	/**
	* Tries to retrieve the language from the caption file and returns a translated language display name
	*
	* Reads first lines of the file (specs of webvtt define headers must be before first blank line) and looks for a language property
	*
	* @param captionFile the caption file from which the language will be extracted
	* @param userLocale the locale which is used to return the translated language display name
	* @return the language display name in the language of the locale property or "Default" if none found
	*/
	@Override
	public java.lang.String retrieveLanguageDisplayNameOfCaptionFile(
		java.io.File captionFile, java.util.Locale userLocale) {
		return _videoLocalService.retrieveLanguageDisplayNameOfCaptionFile(captionFile,
			userLocale);
	}

	@Override
	public boolean hasMissingMetadata(java.lang.Long videoId) {
		return _videoLocalService.hasMissingMetadata(videoId);
	}

	@Override
	public java.util.List<de.uhh.l2g.plugins.model.Video> getVideosWithMissingMetadata() {
		return _videoLocalService.getVideosWithMissingMetadata();
	}

	@Override
	public java.util.List<de.uhh.l2g.plugins.model.Video> stripVideosWithMissingMetadataFromList(
		java.util.List<de.uhh.l2g.plugins.model.Video> videos) {
		return _videoLocalService.stripVideosWithMissingMetadataFromList(videos);
	}

	/**
	* This method is only used to fix missing database entries
	* Uses the lectureseries information for filling the missing data
	*/
	@Override
	public void fixMissingMetadataForVideosFromRelatedLectureseries() {
		_videoLocalService.fixMissingMetadataForVideosFromRelatedLectureseries();
	}

	/**
	 * @deprecated As of 6.1.0, replaced by {@link #getWrappedService}
	 */
	public VideoLocalService getWrappedVideoLocalService() {
		return _videoLocalService;
	}

	/**
	 * @deprecated As of 6.1.0, replaced by {@link #setWrappedService}
	 */
	public void setWrappedVideoLocalService(VideoLocalService videoLocalService) {
		_videoLocalService = videoLocalService;
	}

	@Override
	public VideoLocalService getWrappedService() {
		return _videoLocalService;
	}

	@Override
	public void setWrappedService(VideoLocalService videoLocalService) {
		_videoLocalService = videoLocalService;
	}

	private VideoLocalService _videoLocalService;
}