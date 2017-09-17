package br.livroaws.s3.demo;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

import com.amazonaws.auth.PropertiesCredentials;
import com.amazonaws.regions.Region;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.Bucket;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.GetObjectRequest;
import com.amazonaws.services.s3.model.ListObjectsRequest;
import com.amazonaws.services.s3.model.ObjectListing;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.model.S3ObjectInputStream;
import com.amazonaws.services.s3.model.S3ObjectSummary;

/**
 * @author Ricardo Lecheta
 * 
 */
public class S3Helper {

	private AmazonS3Client s3;

	public S3Helper() {

	}

	public void connect() throws IOException {
		s3 = new AmazonS3Client(new PropertiesCredentials(S3Helper.class.getResourceAsStream("AwsCredentials.properties")));
		Region r = Region.getRegion(Regions.SA_EAST_1);
		s3.setRegion(r);
	}

	public void listBuckets() {
		System.out.println("Listando buckets");
		for (Bucket bucket : s3.listBuckets()) {
			System.out.println(" - " + bucket.getName());
		}
		System.out.println();
	}

	public void listObjects(String bucketName) {
		System.out.println("Listing objects: " + bucketName);
		ObjectListing objectListing = s3.listObjects(new ListObjectsRequest().withBucketName(bucketName));
		for (S3ObjectSummary objectSummary : objectListing.getObjectSummaries()) {
			System.out.println(" - " + objectSummary.getKey() + "  " + "(size = " + objectSummary.getSize() + ")");
		}
		System.out.println();
	}

	public void createBucket(String bucketName) {
		s3.createBucket(bucketName);
	}

	public void deleteBucket(String bucketName) {
		s3.deleteBucket(bucketName);
	}

	public void deleteObject(String bucketName, String key) {
		s3.deleteObject(bucketName, key);
	}

	public void putFile(String bucketName, String dir, String fileName, String contentType, byte[] bytes) throws IOException {
		String path = dir + "/" + fileName;

		// Salva o objeto no S3
		ObjectMetadata metadata = new ObjectMetadata();
		metadata.setContentType(contentType);
		metadata.setContentLength(bytes.length);
		s3.putObject(bucketName, path, new ByteArrayInputStream(bytes), metadata);

		// Deixa p�blico
		s3.setObjectAcl(bucketName, path, CannedAccessControlList.PublicRead);
	}

	public String getFile(String bucketName, String key) throws IOException {
		StringBuffer sb = new StringBuffer();
		S3Object object = s3.getObject(new GetObjectRequest(bucketName, key));
		// Metadata (content-type, content-length, permiss�es, expira��o)
		// ObjectMetadata objectMetadata = object.getObjectMetadata();
		S3ObjectInputStream input = object.getObjectContent();
		BufferedReader reader = new BufferedReader(new InputStreamReader(input));
		while (true) {
			String line = reader.readLine();
			if (line == null)
				break;
			sb.append(line);
		}
		return sb.toString();
	}

	public String getFileURL(String bucketName, String dir, String file) {
		String url = "https://s3-sa-east-1.amazonaws.com/" + bucketName + "/" + dir + "/" + file;
		return url;
	}
}
