package zzw.httpclient_kanjia;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Random;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class Test {

	public static void main(String[] args) throws ClientProtocolException, IOException {
		int i = 20;
		int id = 14059;
		int kid = 23;
		while (i > 0) {
			HttpClient client = HttpClients.createDefault();
			HttpGet httpGet = new HttpGet("http://www.yiheweixin.com/akj/userd/plugin.php?id=yihe_kanjia&mod=index&kid=" + kid + "&uid=" + id);
			HttpResponse httpResponse = client.execute(httpGet);
			Document document = Jsoup.parse(EntityUtils.toString(httpResponse.getEntity(), "utf8"));
			Elements elements = document.select("input[name=\"formhash\"]");
			String valueString = elements.get(0).attr("value");
			HttpGet httpGet2 = new HttpGet("http://www.yiheweixin.com/akj/userd/plugin.php?id=yihe_kanjia&mod=ajax&act=kanjia&kid=" + kid + "&uid=" + id + "&name=" + getRandomJianHan(1)
					+ "&num_sun=&formhash=" + valueString + "&openid=&num_a=5&num_b=4&num_count=9");
			HttpResponse httpResponse1 = client.execute(httpGet2);
			System.out.println(EntityUtils.toString(httpResponse1.getEntity()));
			i--;

		}

	}

	public static String getRandomJianHan(int len) {
		String ret = "";
		for (int i = 0; i < len; i++) {
			String str = null;
			int hightPos, lowPos; // 定义高低位
			Random random = new Random();
			hightPos = (176 + Math.abs(random.nextInt(39))); // 获取高位值
			lowPos = (161 + Math.abs(random.nextInt(93))); // 获取低位值
			byte[] b = new byte[2];
			b[0] = (new Integer(hightPos).byteValue());
			b[1] = (new Integer(lowPos).byteValue());
			try {
				str = new String(b, "GBk"); // 转成中文
			} catch (UnsupportedEncodingException ex) {
				ex.printStackTrace();
			}
			ret += str;
		}
		return ret;
	}
}
