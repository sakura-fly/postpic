

import java.io.ByteArrayInputStream;
import java.util.Base64;

public class Run {
	
	private static String url = "http://192.168.1.111:8090/webtest/upload";
//	private static String url = "http://192.168.1.104:8080/esae/face/put";

	public static void main(String[] args) {
		String content = "find";
		System.out.println(
				Util.postImgByBaseIS(url, new ByteArrayInputStream(Base64.getDecoder().decode(Contants.picBase64)), content));
	}
}
