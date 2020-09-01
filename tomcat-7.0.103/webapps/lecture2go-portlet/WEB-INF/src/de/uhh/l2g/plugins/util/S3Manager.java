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

import java.net.URL;
import java.util.Date;
import java.util.List;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Hex;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.HttpMethod;
import com.amazonaws.SdkClientException;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.DeleteObjectRequest;
import com.amazonaws.services.s3.model.GeneratePresignedUrlRequest;
import com.amazonaws.services.s3.model.ListObjectsRequest;
import com.amazonaws.services.s3.model.ObjectListing;
import com.amazonaws.services.s3.model.S3ObjectSummary;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.util.PropsUtil;
import com.liferay.portal.kernel.util.StringUtil;

public class S3Manager {
	private String accessKey;
	private String secretKey;
	private String region;
	private String serviceName;
	private String endpoint;
	private String bucket;
	private String httpProtocol = "https://";
	
	// the default exipration time for presigned URLs
	private final long defaultExpirationMillis = 1000*60*60;
	
	private AmazonS3 s3Client;
	
	/**
	 * initialize the s3 connection
	 */
	public void initS3Client() {
		this.s3Client = AmazonS3ClientBuilder
				.standard()
				.withCredentials(new AWSStaticCredentialsProvider(new BasicAWSCredentials(this.accessKey, this.secretKey)))
				.withEndpointConfiguration(new AwsClientBuilder.EndpointConfiguration(httpProtocol + this.endpoint, this.region))
				.withRegion(this.region)
				.build();
	}
	
	
	/**
	 * Load the S3Manager with default values from properties file
	 * @return the S3Manager instance with default values set
	 */
	public S3Manager withDefaults() {
		return
			withDefaultCredentials()
			.withDefaultBucket()
			.withDefaultEndpoint();
	}
	
	/**
	 * Load the S3Manager with the given credentials
	 * @param accessKey
	 * @param secretKey
	 * @param region
	 * @param serviceName
	 * @return the S3Manager instance with the given credentials
	 */
	public S3Manager withCredentials(String accessKey, String secretKey, String region, String serviceName) {
		this.accessKey = accessKey;
		this.secretKey = secretKey;
		this.region = region;
		this.serviceName = serviceName;
		return this;
	}

	/**
	 * Load the S3Manager with default credentials values from properties file
	 * @return the S3Manager instance with default credentials set
	 */
	public S3Manager withDefaultCredentials() {
		return withCredentials(PropsUtil.get("lecture2go.s3.accesskey"),PropsUtil.get("lecture2go.s3.secretkey"),PropsUtil.get("lecture2go.s3.region"),PropsUtil.get("lecture2go.s3.servicename"));
	}
	
	/**
	 * Load the S3Manager with the given bucket
	 * @param bucket
	 * @return the S3Manager instance with the given bucket
	 */
	public S3Manager withBucket(String bucket) {
		this.bucket = bucket;
		return this;
	}
	
	/**
	 * Load the S3Manager with the default bucket
	 * @return the S3Manager instance with the default bucket
	 */
	public S3Manager withDefaultBucket() {
		return withBucket(PropsUtil.get("lecture2go.s3.bucket"));
	}
	
	/**
	 * Load the S3Manager with the given endpoint
	 * @param endpoint
	 * @return the S3Manager instance with the given endpoint
	 */
	public S3Manager withEndpoint(String endpoint) {
		this.endpoint = endpoint;
		return this;
	}
	
	/**
	 * Load the S3Manager with the default endpoint
	 * @return the S3Manager instance with the default endpoint
	 */
	public S3Manager withDefaultEndpoint() {
		return withEndpoint(PropsUtil.get("lecture2go.s3.endpoint"));
	}
	
	/**
	 * Get all items from the S3 bucket with a given prefix / "folder"
	 * @param prefix the prefix ("folder") to filter for
	 * @return a list of S3ObjectSummary instances
	 */
	public List<S3ObjectSummary> getItemsFromBucketWithPrefix(String prefix) throws AmazonServiceException, SdkClientException {
		ListObjectsRequest listObjectsRequest = new ListObjectsRequest().withBucketName(this.bucket).withPrefix(prefix);

		ObjectListing result = s3Client.listObjects(listObjectsRequest);
		List<S3ObjectSummary> objects = result.getObjectSummaries();
		return objects;
	}
	
	/**
	 * Get all items from the S3 bucket with a given prefix / "folder" in JSON format with some information and a presignedURL
	 * @param prefix the prefix ("folder") to filter for
	 * @return a jsonArray with informations about the objects and presignedURLs
	 */
	public JSONArray getItemsFromBucketWithPrefixAsJson(String prefix) {	
		try {
			List<S3ObjectSummary> objects = getItemsFromBucketWithPrefix(prefix);
			JSONArray jsonArray =  JSONFactoryUtil.createJSONArray();

			for (S3ObjectSummary os : objects) {
				JSONObject jo = JSONFactoryUtil.createJSONObject();
				jo.put("key", os.getKey());
				jo.put("filename", StringUtil.extractLast(os.getKey(), "/"));
				jo.put("presignedUrl", getPresignedUrlGet(os.getKey(), getExpirationDateFromMillis(this.defaultExpirationMillis)).toString());
				jo.put("fileSize", (os.getSize() / 1024 / 1024) + " MB");

				jsonArray.put(jo);
	       	}
			return jsonArray;
		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * Get a presinged url for the object
	 * @param key the object key
	 * @param expiration the expiration date
	 * @return the presigned URL
	 */
	public URL getPresignedUrlGet(String key, Date expiration) {
		GeneratePresignedUrlRequest generatePresignedUrlRequest =
                new GeneratePresignedUrlRequest(this.bucket, key)
                        .withMethod(HttpMethod.GET)
                        .withExpiration(expiration);
        URL url = s3Client.generatePresignedUrl(generatePresignedUrlRequest);
        return url;
	}
	
	/**
	 * Delete an object with the given key
	 * @param key the object key of the object, which will be deleted
	 */
	public void deleteObject(String key) {
		this.s3Client.deleteObject(new DeleteObjectRequest(this.bucket, key));
	}
	
	/**
	 * AWS specific signature calculating used for manually signing the uploads via evaporateJS
	 * (without aws sdk, maybe this can be replaced by the sdk)
	 * @param data
	 * @param key
	 * @return
	 * @throws Exception
	 */
	public byte[] HmacSHA256(String data, byte[] key) throws Exception {
	    String algorithm="HmacSHA256";
	    Mac mac = Mac.getInstance(algorithm);
	    mac.init(new SecretKeySpec(key, algorithm));
	    return mac.doFinal(data.getBytes("UTF-8"));
	}
	
	/**
	 * AWS specific signature calculating used for manually signing the uploads via evaporateJS
	 * (without aws sdk, maybe this can be replaced by the sdk)
	 * @param signer
	 * @return
	 */
	public String getSignatureKey(byte[] signer) {
		return new String(Hex.encodeHex(signer));
	}

	/**
	 * AWS specific signature calculating used for manually signing the uploads via evaporateJS
	 * (without aws sdk, maybe this can be replaced by the sdk)
	 * @param dateStamp
	 * @param toSign
	 * @return
	 * @throws Exception
	 */
	public byte[] getSignatureKey(String dateStamp, String toSign) throws Exception {
	    byte[] kSecret = ("AWS4" + this.secretKey).getBytes("UTF-8");
	    byte[] kDate = HmacSHA256(dateStamp, kSecret);
	    byte[] kRegion = HmacSHA256(this.region, kDate);
	    byte[] kService = HmacSHA256(this.serviceName, kRegion);
	    byte[] kSigning = HmacSHA256("aws4_request", kService);
	    byte[] dataSigning = HmacSHA256(toSign, kSigning);
	    return dataSigning;
	}
	
	/**
	 * Get the expiration date which will be calculated from the given milliseconds
	 * @param millis the milliseconds from which the expiration date will be calculated
	 * @return the expiration date 
	 */
	private Date getExpirationDateFromMillis(long millis) {
        Date expiration = new Date();
        long expTimeMillis = expiration.getTime();
        expTimeMillis += millis;
        //expTimeMillis += 1000 * 60 * 60;
        expiration.setTime(expTimeMillis);
        return expiration;
	}
}
