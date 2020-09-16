package co.salpa.bookery.find.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import co.salpa.bookery.model.TocDao;
import lombok.Setter;

@Controller
public class FindController {

	@Autowired
	TocDao tocDao;

	@RequestMapping("/find") // �˻��������� �̵�
	public String find() {
		return "/find/find"; // find�����Ʒ� find.jsp
	}

	@RequestMapping("/find/result") // �˻��������� �˻���� ����
	public ModelAndView searchBooks(HttpServletRequest request, HttpServletResponse response) throws Exception {
		// TODO Auto-generated method stub
		String clientId = "INyNwc8RvDNjUoCD9lHg"; // HyeongJin naver api key
		String clientSecret = "e4hlkduAe3";
		request.setCharacterEncoding("utf-8");
		response.setContentType("application/json;charset=utf-8");
		String text = request.getParameter("search");// ����ó��
		String start = "&start=" + request.getParameter("start");// �˻���� ������ �д� ���ۼ���.
		System.out.println(text + start);

		try {
			text = URLEncoder.encode(text, "UTF-8");
			System.out.println(text);
		} catch (UnsupportedEncodingException e) {
			throw new RuntimeException("encoding error", e);
		} // catch

		String apiURL = "https://openapi.naver.com/v1/search/book.json?query=" + text + start; // book search json
		System.out.println(apiURL);

		Map<String, String> requestHeaders = new HashMap<>();
		requestHeaders.put("X-Naver-Client-Id", clientId);
		requestHeaders.put("X-Naver-Client-Secret", clientSecret);
		String responseBody = get(apiURL, requestHeaders);// ���̹��� �˻���� ������ ������ responseBody�� ����

		System.out.println(responseBody);

		PrintWriter out = response.getWriter(); // bookery�˻��������� ���̹�å ������������ ������
		out.print(responseBody);
		out.close();

		return null;// ajax����̶� view�� ����
	}// searchBooks

	private static String get(String apiUrl, Map<String, String> requestHeaders) {
		HttpURLConnection con = connect(apiUrl);
		try {
			con.setRequestMethod("GET");
			for (Map.Entry<String, String> header : requestHeaders.entrySet()) {
				con.setRequestProperty(header.getKey(), header.getValue());
			}

			int responseCode = con.getResponseCode();
			if (responseCode == HttpURLConnection.HTTP_OK) { // success ���Ἲ��
				return readBody(con.getInputStream());
			} else { // error
				return readBody(con.getErrorStream());
			}
		} catch (IOException e) {
			throw new RuntimeException("API request, response fail", e);
		} finally {
			con.disconnect();
		}
	}// get

	private static HttpURLConnection connect(String apiUrl) {
		try {
			URL url = new URL(apiUrl);
			return (HttpURLConnection) url.openConnection();
		} catch (MalformedURLException e) {
			throw new RuntimeException("API URL is wrong : " + apiUrl, e);
		} catch (IOException e) {
			throw new RuntimeException("connection fail : " + apiUrl, e);
		}
	}// HttpURLConnection

	private static String readBody(InputStream body) throws UnsupportedEncodingException {
		InputStreamReader streamReader = new InputStreamReader(body, "utf-8");
		// �˻��� å �������� ���������� responseBody�� ��Ƽ� ��ȯ�Ѵ�.
		try (BufferedReader lineReader = new BufferedReader(streamReader)) {
			StringBuilder responseBody = new StringBuilder();

			String line;
			while ((line = lineReader.readLine()) != null) {
				responseBody.append(line);
			}
			String tmp = new String(responseBody.toString());
			// System.out.println("tmp = " + tmp);
			return tmp;
		} catch (IOException e) {
			throw new RuntimeException("API Reading response fail", e);
		}
	}// readBody

	/***************************
	 * å�󼼺��� �������� �ε��� �� �񵿱�� å���� ũ�Ѹ��ؼ� ������
	 **********************************/
	@RequestMapping("/find/crawling")
	public ModelAndView crawlingBook(int bid, HttpServletResponse response) throws IOException {
//		int bid = Integer.parseInt(request.getParameter("bid"));
		String url = "https://book.naver.com/bookdb/book_detail.nhn?bid=" + bid;
		Document doc = null;
		System.out.println("ũ�Ѹ�!! url = " + url);
		try {
			doc = Jsoup.connect(url).get();
			// System.out.println(doc);
		} catch (IOException e) {
			e.printStackTrace();
		}
		//System.out.println(doc);
		response.setContentType("text/html;charset=utf-8");
		PrintWriter out = response.getWriter();
		out.print(doc);
		out.close();
		return null;
	}// crawlingBook

}