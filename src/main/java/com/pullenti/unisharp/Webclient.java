package com.pullenti.unisharp;

public class Webclient {

	public String downloadString(java.net.URI uri) throws Exception {
		return downloadString(uri.toString());
	}
	
	public String downloadString(String uri) throws Exception {
		byte[] dat = uploadData(uri, null);
		if(dat == null) return null;
		return Utils.decodeCharset(java.nio.charset.Charset.forName("UTF-8"), dat, 0, dat.length);
	}
	
	public byte[] downloadData(java.net.URI uri) throws Exception {
		return uploadData(uri.toString(), null);
	}

	public byte[] downloadData(String uri) throws Exception {
		return uploadData(uri, null);
	}
	
	public byte[] uploadData(java.net.URI uri, byte[] data) throws Exception {
		return uploadData(uri.toString(), data);
	}
	
	public byte[] uploadData(String uri, byte[] data) throws Exception {
		java.net.HttpURLConnection conn = null;
		try {

			java.net.URL url = new java.net.URL(uri);
			conn = (java.net.HttpURLConnection) url.openConnection();
			if(data != null && data.length > 0) {
				conn.setRequestMethod("POST");
				conn.setDoOutput(true);
				java.io.OutputStream os = conn.getOutputStream();
				os.write(data);
				os.close();				
			}
			else 
				conn.setRequestMethod("GET");
			// conn.setRequestProperty("Accept", "application/json");

			if (conn.getResponseCode() != 200) {
				throw new Exception("Failed : HTTP error code : " + conn.getResponseCode());
			}

			byte[] result = Utils.readAllBytes(conn.getInputStream());
			return result;

		} catch (Exception e) {
			throw new Exception(e.getMessage(), e);
		} finally {
			if (conn != null)
				conn.disconnect();
		}
	}	
}