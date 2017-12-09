

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.UUID;

public class Util {
	public static String postImgByBaseIS(String urlStr, InputStream is, String parameter) {
		String res = "";
		HttpURLConnection conn = null;
		String BOUNDARY = "---------------------------";
		try {
			URL url = new URL(urlStr);
			conn = (HttpURLConnection) url.openConnection();
			conn.setConnectTimeout(5000);
			conn.setReadTimeout(30000);
			conn.setDoOutput(true);
			conn.setDoInput(true);
			conn.setUseCaches(false);
			conn.setRequestMethod("POST");
			conn.setRequestProperty("Connection", "Keep-Alive");
			conn.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows; U; Windows NT 6.1; zh-CN; rv:1.9.2.6)");

			conn.setRequestProperty("Content-Type", "multipart/form-data; boundary=" + BOUNDARY);
			OutputStream out = new DataOutputStream(conn.getOutputStream());
			String content = "--" + BOUNDARY + "\r\n";
			content += "Content-Disposition: form-data; name=\"cmd\"" + "\r\n\r\n";
			content += parameter;
			content += "\r\n--" + BOUNDARY + "\r\n";
			content += "Content-Disposition: form-data; name=\"file\"; filename=\"avatar.jpg\"\r\n";
			content += "Content-Type: image/jpeg\r\n\r\n";
			out.write(content.getBytes());
			// String contentType = "image/png";
			// StringBuffer strBuf = new StringBuffer();
			// strBuf.append("\r\n").append("--").append(BOUNDARY).append("\r\n");
			// String uuid = UUID.randomUUID().toString().replace("-",
			// "").toUpperCase();
			// strBuf.append("Content-Disposition: form-data; name=\"" + "file"
			// + "\"; filename=\"" + uuid + "\"\r\n");
			// strBuf.append("Content-Type:" + contentType + "\r\n\r\n");
			// out.write(strBuf.toString().getBytes());
			DataInputStream in = new DataInputStream(is);
			int bytes = 0;
			byte[] bufferOut = new byte[1024];
			while ((bytes = in.read(bufferOut)) != -1) {
				out.write(bufferOut, 0, bytes);
			}
			in.close();
			byte[] endData = ("\r\n--" + BOUNDARY + (int) (Math.random() * 15) + "--\r\n").getBytes();
			out.write(endData);

			StringBuffer buff = new StringBuffer();
			BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			String line = null;
			while ((line = reader.readLine()) != null) {
				buff.append(line).append("\n");
			}
			res = buff.toString();
			reader.close();
			reader = null;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (conn != null) {
				conn.disconnect();
				conn = null;
			}
		}
		return res;
	}
}
