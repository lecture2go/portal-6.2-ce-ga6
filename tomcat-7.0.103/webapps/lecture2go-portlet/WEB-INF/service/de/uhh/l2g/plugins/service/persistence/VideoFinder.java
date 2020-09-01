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

package de.uhh.l2g.plugins.service.persistence;

/**
 * @author Iavor Sturm
 */
public interface VideoFinder {
	public java.util.List<de.uhh.l2g.plugins.model.Video> findPopular(int limit);

	public java.util.List<de.uhh.l2g.plugins.model.Video> findVideosByHits(
		long hits);

	public java.util.List<de.uhh.l2g.plugins.model.Video> findVideosByHitsAndOpenAccess(
		long hits);

	public java.util.List<de.uhh.l2g.plugins.model.Video> findVideosByAllSearchWords();

	public java.util.List<de.uhh.l2g.plugins.model.Video> findVideosBySearchWord(
		java.lang.String word, int limit);

	public java.util.List<de.uhh.l2g.plugins.model.Video> findVideosBySearchWordAndLectureseriesId(
		java.lang.String word, long lectureseriesId);

	public int unlinkLectureseriesFromVideos(java.lang.Long lectureseriesId);

	public de.uhh.l2g.plugins.model.Video findVideoBySerureUrl(
		java.lang.String surl);

	public de.uhh.l2g.plugins.model.Video findLatestOpenAccessVideoForLectureseries(
		java.lang.Long lectureseriesId);

	public java.util.List<de.uhh.l2g.plugins.model.Video> findLatestVideos();

	public boolean checkVideoHasMissingMetadata(java.lang.Long videoId);

	public java.util.List<de.uhh.l2g.plugins.model.Video> findVideosWithMissingMetadata();
}