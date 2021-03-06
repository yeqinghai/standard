package com.junit.platform.tool.security;

import static org.junit.Assert.assertNotNull;

import java.io.DataInputStream;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

import org.junit.Test;

import com.platform.tools.security.ToolHTTPS2;

/**
 * HTTPS测试
 */
public class TestHTTPS2 {

	/**
	 * 密钥库/信任库密码
	 */
	private String password = "678789";

	/**
	 * 密钥库文件路径
	 */
	private String keyStorePath = System.getProperty("user.dir") + "/doc/ssl/bothway/server.p12";

	/**
	 * 信任库文件路径
	 */
	private String trustStorePath = System.getProperty("user.dir") + "/doc/ssl/bothway/ca.p12";

	/**
	 * 访问地址
	 */
	private String httpsUrl = "https://www.cas.org:8443";

	/**
	 * HTTPS验证
	 * 
	 * @throws Exception
	 */
	@Test
	public void test() throws Exception {
		// 建立HTTPS链接
		URL url = new URL(httpsUrl);
		HttpsURLConnection conn = (HttpsURLConnection) url.openConnection();

		// conn.setRequestMethod(method);

		// 打开输入输出流
		conn.setDoInput(true);
		// conn.setDoOutput(true);

		// 为HttpsURLConnection配置SSLSocketFactory
		ToolHTTPS2.configSSLSocketFactory(conn, password, keyStorePath, trustStorePath);

		// 鉴别内容长度
		int length = conn.getContentLength();

		byte[] data = null;

		// 如果内容长度为-1，则放弃解析
		if (length != -1) {

			DataInputStream dis = new DataInputStream(conn.getInputStream());

			data = new byte[length];

			dis.readFully(data);

			dis.close();

			System.err.println(new String(data));
		}

		conn.disconnect();

		// 验证
		assertNotNull(data);
	}

}
