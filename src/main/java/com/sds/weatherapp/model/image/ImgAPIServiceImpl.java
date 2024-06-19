package com.sds.weatherapp.model.image;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.sds.weatherapp.domain.Urls;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class ImgAPIServiceImpl implements ImgAPIService {

	public Urls getImage(String keyword) {
	    String Access_Key = "mx-7qOLvKRp4MbSea2bNNAPR0JWw_zu4zaIQvMDwkrM"; // 액세스 키 값

	    Urls urls = null; // api 데이터 파싱 결과를 담을 변수
	    BufferedReader rd;
	    StringBuilder sb = new StringBuilder();

	    StringBuilder urlBuilder = new StringBuilder("https://api.unsplash.com/search/photos"); /*URL*/
	    try {
			urlBuilder.append("?" + URLEncoder.encode("page","UTF-8") + "=1");
			urlBuilder.append("&" + URLEncoder.encode("client_id","UTF-8") + "=" + URLEncoder.encode(Access_Key, "UTF-8"));
		    urlBuilder.append("&" + URLEncoder.encode("query","UTF-8") + "=" + URLEncoder.encode(keyword, "UTF-8"));
		    urlBuilder.append("&" + URLEncoder.encode("lang","UTF-8") + "=" + URLEncoder.encode("ko", "UTF-8"));
		    URL url = new URL(urlBuilder.toString());

		    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		    conn.setRequestMethod("GET");
		    conn.setRequestProperty("Content-type", "application/json");
		    log.debug("Response code: " + conn.getResponseCode());

		    

		    if(conn.getResponseCode() >= 200 && conn.getResponseCode() <= 300) {
		       rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
		    } else {
		       rd = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
		    }

		    
		    String line;
		    while ((line = rd.readLine()) != null) {
		       sb.append(line);
		    }
		    rd.close();
		    conn.disconnect();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} /*Service Key*/

	    

	    String result = sb.toString();
	    log.debug("result: " + result);

	    JSONParser parser = new JSONParser();
	    try{
	       JSONObject json = (JSONObject)parser.parse(result);
	       JSONArray array = (JSONArray)json.get("results"); // 안쪽 배열(= 키인 results에 대응되는 값) 반환

	       JSONObject json2= (JSONObject)array.get(0);
	       json2 = (JSONObject)json2.get("urls"); // 키 urls에 대응되는 값 반환

	       Gson gson = new Gson();

	       Type resultType = new TypeToken<Urls>() {}.getType();
	       urls = gson.fromJson(json2.toJSONString(), resultType);

	    }catch (Exception e) {
	       e.printStackTrace();
	    }

	    return urls;
	}

}
