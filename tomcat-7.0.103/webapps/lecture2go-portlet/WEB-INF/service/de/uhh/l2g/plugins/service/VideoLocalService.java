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

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.search.IndexableType;
import com.liferay.portal.kernel.transaction.Isolation;
import com.liferay.portal.kernel.transaction.Propagation;
import com.liferay.portal.kernel.transaction.Transactional;
import com.liferay.portal.service.BaseLocalService;
import com.liferay.portal.service.InvokableLocalService;
import com.liferay.portal.service.PersistedModelLocalService;

/**
 * Provides the local service interface for Video. Methods of this
 * service will not have security checks based on the propagated JAAS
 * credentials because this service can only be accessed from within the same
 * VM.
 *
 * @author Iavor Sturm
 * @see VideoLocalServiceUtil
 * @see de.uhh.l2g.plugins.service.base.VideoLocalServiceBaseImpl
 * @see de.uhh.l2g.plugins.service.impl.VideoLocalServiceImpl
 * @generated
 */
@Transactional(isolation = Isolation.PORTAL, rollbackFor =  {
	PortalException.class, SystemException.class})
public interface VideoLocalService extends BaseLocalService,
	InvokableLocalService, PersistedModelLocalService {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link VideoLocalServiceUtil} to access the video local service. Add custom service methods to {@link de.uhh.l2g.plugins.service.impl.VideoLocalServiceImpl} and rerun ServiceBuilder to automatically copy the method declarations to this interface.
	 */

	/**
	* Adds the video to the database. Also notifies the appropriate model listeners.
	*
	* @param video the video
	* @return the video that was added
	* @throws SystemException if a system exception occurred
	*/
	@com.liferay.portal.kernel.search.Indexable(type = IndexableType.REINDEX)
	public de.uhh.l2g.plugins.model.Video addVideo(
		de.uhh.l2g.plugins.model.Video video)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Creates a new video with the primary key. Does not add the video to the database.
	*
	* @param videoId the primary key for the new video
	* @return the new video
	*/
	public de.uhh.l2g.plugins.model.Video createVideo(long videoId);

	/**
	* Deletes the video with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param videoId the primary key of the video
	* @return the video that was removed
	* @throws PortalException if a video with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	@com.liferay.portal.kernel.search.Indexable(type = IndexableType.DELETE)
	public de.uhh.l2g.plugins.model.Video deleteVideo(long videoId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException;

	/**
	* Deletes the video from the database. Also notifies the appropriate model listeners.
	*
	* @param video the video
	* @return the video that was removed
	* @throws SystemException if a system exception occurred
	*/
	@com.liferay.portal.kernel.search.Indexable(type = IndexableType.DELETE)
	public de.uhh.l2g.plugins.model.Video deleteVideo(
		de.uhh.l2g.plugins.model.Video video)
		throws com.liferay.portal.kernel.exception.SystemException;

	public com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery();

	/**
	* Performs a dynamic query on the database and returns the matching rows.
	*
	* @param dynamicQuery the dynamic query
	* @return the matching rows
	* @throws SystemException if a system exception occurred
	*/
	@SuppressWarnings("rawtypes")
	public java.util.List dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery)
		throws com.liferay.portal.kernel.exception.SystemException;

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
	@SuppressWarnings("rawtypes")
	public java.util.List dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end) throws com.liferay.portal.kernel.exception.SystemException;

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
	@SuppressWarnings("rawtypes")
	public java.util.List dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the number of rows that match the dynamic query.
	*
	* @param dynamicQuery the dynamic query
	* @return the number of rows that match the dynamic query
	* @throws SystemException if a system exception occurred
	*/
	public long dynamicQueryCount(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the number of rows that match the dynamic query.
	*
	* @param dynamicQuery the dynamic query
	* @param projection the projection to apply to the query
	* @return the number of rows that match the dynamic query
	* @throws SystemException if a system exception occurred
	*/
	public long dynamicQueryCount(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery,
		com.liferay.portal.kernel.dao.orm.Projection projection)
		throws com.liferay.portal.kernel.exception.SystemException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public de.uhh.l2g.plugins.model.Video fetchVideo(long videoId)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the video with the primary key.
	*
	* @param videoId the primary key of the video
	* @return the video
	* @throws PortalException if a video with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public de.uhh.l2g.plugins.model.Video getVideo(long videoId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException;

	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public com.liferay.portal.model.PersistedModel getPersistedModel(
		java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException;

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
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public java.util.List<de.uhh.l2g.plugins.model.Video> getVideos(int start,
		int end) throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the number of videos.
	*
	* @return the number of videos
	* @throws SystemException if a system exception occurred
	*/
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public int getVideosCount()
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Updates the video in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	*
	* @param video the video
	* @return the video that was updated
	* @throws SystemException if a system exception occurred
	*/
	@com.liferay.portal.kernel.search.Indexable(type = IndexableType.REINDEX)
	public de.uhh.l2g.plugins.model.Video updateVideo(
		de.uhh.l2g.plugins.model.Video video)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the Spring bean ID for this bean.
	*
	* @return the Spring bean ID for this bean
	*/
	public java.lang.String getBeanIdentifier();

	/**
	* Sets the Spring bean ID for this bean.
	*
	* @param beanIdentifier the Spring bean ID for this bean
	*/
	public void setBeanIdentifier(java.lang.String beanIdentifier);

	@Override
	public java.lang.Object invokeMethod(java.lang.String name,
		java.lang.String[] parameterTypes, java.lang.Object[] arguments)
		throws java.lang.Throwable;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public java.util.List<de.uhh.l2g.plugins.model.Video> getByOpenAccess(
		int bool) throws com.liferay.portal.kernel.exception.SystemException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public int getByOpenAccessAndUploadedFile(int bool)
		throws com.liferay.portal.kernel.exception.SystemException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public de.uhh.l2g.plugins.model.Video getLatestOpenAccessVideoForLectureseries(
		java.lang.Long lectureseriesId);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public java.util.List<de.uhh.l2g.plugins.model.Video> getByTerm(
		java.lang.Long termId)
		throws com.liferay.portal.kernel.exception.SystemException;

	public int unlinkLectureseriesFromVideos(java.lang.Long lectureseriesId);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public java.util.List<de.uhh.l2g.plugins.model.Video> getByRootInstitution(
		java.lang.Long rootInstitutionId)
		throws com.liferay.portal.kernel.exception.SystemException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public java.util.List<de.uhh.l2g.plugins.model.Video> getByFilename(
		java.lang.String filename)
		throws com.liferay.portal.kernel.exception.SystemException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public java.util.List<de.uhh.l2g.plugins.model.Video> getByProducer(
		java.lang.Long producerId)
		throws com.liferay.portal.kernel.exception.SystemException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public java.util.List<de.uhh.l2g.plugins.model.Video> getByLectureseries(
		java.lang.Long lectureseriesId)
		throws com.liferay.portal.kernel.exception.SystemException;

	public int countByLectureseries(java.lang.Long lectureseriesId)
		throws com.liferay.portal.kernel.exception.SystemException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public java.util.List<de.uhh.l2g.plugins.model.Video> getByProducerAndLectureseries(
		java.lang.Long producerId, java.lang.Long lectureseriesId)
		throws com.liferay.portal.kernel.exception.SystemException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public java.util.List<de.uhh.l2g.plugins.model.Video> getByProducerAndDownloadLink(
		java.lang.Long producerId, int downloadLink)
		throws com.liferay.portal.kernel.exception.SystemException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public java.util.List<de.uhh.l2g.plugins.model.Video> getPopular(int limit);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public java.util.List<de.uhh.l2g.plugins.model.Video> getLatestVideos();

	public void createThumbnailsIfNotExisting(java.lang.Long videoId);

	public void createSymLinkToDownloadableFileIfNotExisting(
		java.lang.Long videoId);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public org.json.JSONArray getJSONVideo(java.lang.Long videoId);

	public void createLastVideoList()
		throws com.liferay.portal.kernel.exception.SystemException;

	public int countByLectureseriesAndOpenaccess(
		java.lang.Long lectureseriesId, int openAccess)
		throws com.liferay.portal.kernel.exception.SystemException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public java.util.List<de.uhh.l2g.plugins.model.Video> getByLectureseriesAndOpenaccess(
		java.lang.Long lectureseriesId, int openAccess)
		throws com.liferay.portal.kernel.exception.SystemException;

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
	public void addPlayerUris2Video(de.uhh.l2g.plugins.model.Video video);

	/**
	* This adds the "tracks" section for the video player json if there are any captions or chapters and sets the label to
	* language of the caption file (translated to the userLocale)
	*/
	public void addTextTracks2VideoWithLanguageLabel(
		de.uhh.l2g.plugins.model.Video video, java.util.Locale userLocale);

	/**
	* This adds the "tracks" section for the video player json if there are any captions or chapters
	*/
	public void addTextTracks2Video(de.uhh.l2g.plugins.model.Video video);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public de.uhh.l2g.plugins.model.Video getBySecureUrl(java.lang.String surl)
		throws com.liferay.portal.kernel.exception.SystemException,
			de.uhh.l2g.plugins.NoSuchVideoException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public java.util.List<de.uhh.l2g.plugins.model.Video> getAll()
		throws com.liferay.portal.kernel.exception.SystemException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public java.util.List<de.uhh.l2g.plugins.model.Video> getBySearchWord(
		java.lang.String word, int limit)
		throws com.liferay.portal.kernel.exception.SystemException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public java.util.List<de.uhh.l2g.plugins.model.Video> getByAllSearchWords()
		throws com.liferay.portal.kernel.exception.SystemException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public java.util.List<de.uhh.l2g.plugins.model.Video> getBySearchWordAndLectureseriesId(
		java.lang.String word, java.lang.Long lectureseriesId)
		throws com.liferay.portal.kernel.exception.SystemException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public java.util.List<de.uhh.l2g.plugins.model.Video> getByHits(
		java.lang.Long hits);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public java.util.List<de.uhh.l2g.plugins.model.Video> getByHitsAndOpenAccess(
		java.lang.Long hits);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public java.util.List<de.uhh.l2g.plugins.model.Video> getByHits();

	public de.uhh.l2g.plugins.model.Video incrementHitCounter(
		de.uhh.l2g.plugins.model.Video video)
		throws com.liferay.portal.kernel.exception.SystemException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public java.lang.Long getLatestClosedAccessVideoId(
		java.lang.Long lectureseriesId);

	/**
	* Checks if the video has a related smil-file in the file system
	*/
	public boolean checkSmilFile(de.uhh.l2g.plugins.model.Video video);

	public boolean fileStringSegmentFoundInArray(java.lang.String file,
		org.json.JSONArray jsonArray);

	/**
	* Creates a symlink for the caption of the video to to captions folder
	*/
	public void createSymLinkForCaptionIfExisting(java.lang.Long videoId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException;

	/**
	* Checks if file is a symoblic link
	*
	* @param file the file to check
	* @return true if file is sym link, false if not
	* @throws IOException
	*/
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public boolean isSymlink(java.io.File file) throws java.io.IOException;

	/**
	* Tries to retrieve the language from the caption file and returns a translated language display name
	*
	* Reads first lines of the file (specs of webvtt define headers must be before first blank line) and looks for a language property
	*
	* @param captionFile the caption file from which the language will be extracted
	* @param userLocale the locale which is used to return the translated language display name
	* @return the language display name in the language of the locale property or "Default" if none found
	*/
	public java.lang.String retrieveLanguageDisplayNameOfCaptionFile(
		java.io.File captionFile, java.util.Locale userLocale);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public boolean hasMissingMetadata(java.lang.Long videoId);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public java.util.List<de.uhh.l2g.plugins.model.Video> getVideosWithMissingMetadata();

	public java.util.List<de.uhh.l2g.plugins.model.Video> stripVideosWithMissingMetadataFromList(
		java.util.List<de.uhh.l2g.plugins.model.Video> videos);

	/**
	* This method is only used to fix missing database entries
	* Uses the lectureseries information for filling the missing data
	*/
	public void fixMissingMetadataForVideosFromRelatedLectureseries();
}