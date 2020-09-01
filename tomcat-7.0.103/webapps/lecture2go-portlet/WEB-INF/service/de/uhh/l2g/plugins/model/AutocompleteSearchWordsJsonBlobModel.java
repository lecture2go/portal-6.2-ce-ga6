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

package de.uhh.l2g.plugins.model;

import java.sql.Blob;

/**
 * The Blob model class for lazy loading the searchWordsJson column in Autocomplete.
 *
 * @author Iavor Sturm
 * @see Autocomplete
 * @generated
 */
public class AutocompleteSearchWordsJsonBlobModel {
	public AutocompleteSearchWordsJsonBlobModel() {
	}

	public AutocompleteSearchWordsJsonBlobModel(long autocompleteId) {
		_autocompleteId = autocompleteId;
	}

	public AutocompleteSearchWordsJsonBlobModel(long autocompleteId,
		Blob searchWordsJsonBlob) {
		_autocompleteId = autocompleteId;
		_searchWordsJsonBlob = searchWordsJsonBlob;
	}

	public long getAutocompleteId() {
		return _autocompleteId;
	}

	public void setAutocompleteId(long autocompleteId) {
		_autocompleteId = autocompleteId;
	}

	public Blob getSearchWordsJsonBlob() {
		return _searchWordsJsonBlob;
	}

	public void setSearchWordsJsonBlob(Blob searchWordsJsonBlob) {
		_searchWordsJsonBlob = searchWordsJsonBlob;
	}

	private long _autocompleteId;
	private Blob _searchWordsJsonBlob;
}