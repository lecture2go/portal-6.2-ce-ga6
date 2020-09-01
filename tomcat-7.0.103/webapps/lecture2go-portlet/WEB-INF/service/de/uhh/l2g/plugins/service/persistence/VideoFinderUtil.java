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

import com.liferay.portal.kernel.bean.PortletBeanLocatorUtil;
import com.liferay.portal.kernel.util.ReferenceRegistry;

/**
 * @author Iavor Sturm
 */
public class VideoFinderUtil {
	public static java.util.List<de.uhh.l2g.plugins.model.Video> findPopular(
		int limit) {
		return getFinder().findPopular(limit);
	}

	public static java.util.List<de.uhh.l2g.plugins.model.Video> findVideosByHits(
		long hits) {
		return getFinder().findVideosByHits(hits);
	}

	public static java.util.List<de.uhh.l2g.plugins.model.Video> findVideosByHitsAndOpenAccess(
		long hits) {
		return getFinder().findVideosByHitsAndOpenAccess(hits);
	}

	public static java.util.List<de.uhh.l2g.plugins.model.Video> findVideosByAllSearchWords() {
		return getFinder().findVideosByAllSearchWords();
	}

	public static java.util.List<de.uhh.l2g.plugins.model.Video> findVideosBySearchWord(
		java.lang.String word, int limit) {
		return getFinder().findVideosBySearchWord(word, limit);
	}

	public static java.util.List<de.uhh.l2g.plugins.model.Video> findVideosBySearchWordAndLectureseriesId(
		java.lang.String word, long lectureseriesId) {
		return getFinder()
				   .findVideosBySearchWordAndLectureseriesId(word,
			lectureseriesId);
	}

	public static int unlinkLectureseriesFromVideos(
		java.lang.Long lectureseriesId) {
		return getFinder().unlinkLectureseriesFromVideos(lectureseriesId);
	}

	public static de.uhh.l2g.plugins.model.Video findVideoBySerureUrl(
		java.lang.String surl) {
		return getFinder().findVideoBySerureUrl(surl);
	}

	public static de.uhh.l2g.plugins.model.Video findLatestOpenAccessVideoForLectureseries(
		java.lang.Long lectureseriesId) {
		return getFinder()
				   .findLatestOpenAccessVideoForLectureseries(lectureseriesId);
	}

	public static java.util.List<de.uhh.l2g.plugins.model.Video> findLatestVideos() {
		return getFinder().findLatestVideos();
	}

	public static boolean checkVideoHasMissingMetadata(java.lang.Long videoId) {
		return getFinder().checkVideoHasMissingMetadata(videoId);
	}

	public static java.util.List<de.uhh.l2g.plugins.model.Video> findVideosWithMissingMetadata() {
		return getFinder().findVideosWithMissingMetadata();
	}

	public static VideoFinder getFinder() {
		if (_finder == null) {
			_finder = (VideoFinder)PortletBeanLocatorUtil.locate(de.uhh.l2g.plugins.service.ClpSerializer.getServletContextName(),
					VideoFinder.class.getName());

			ReferenceRegistry.registerReference(VideoFinderUtil.class, "_finder");
		}

		return _finder;
	}

	public void setFinder(VideoFinder finder) {
		_finder = finder;

		ReferenceRegistry.registerReference(VideoFinderUtil.class, "_finder");
	}

	private static VideoFinder _finder;
}