package com.sds.weatherapp.model.weather;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.sds.weatherapp.domain.WeatherInfo;

@Service
public class WeatherAPIServiceImpl implements WeatherAPIService{
	//	q={city name}&appid={API key}
	private final static String BASE_URL = "http://api.openweathermap.org/data/2.5/weather?";
	
	@Value("${spring.weather.api.key}")
	private String key;
	
	public WeatherInfo getWeatherData(String city) {
		
		WeatherInfo weatherInfo = new WeatherInfo();
		
		try {
			
			String lon = "126.977948";  //경도
            String lat = "37.566386";   //위도

            //OpenAPI call하는 URL
//            String urlstr = "http://api.openweathermap.org/data/2.5/weather?"
//                        + "lat="+lat+"&lon="+lon + "&appid=" +KEY;
//            String city = "Seoul";
            String urlstr = "https://api.openweathermap.org/data/2.5/weather?q="
            		+city + "&appid=" + key;
			
			URL url = new URL(urlstr);
			String result = "";
			String line;
			
			BufferedReader bf = new BufferedReader(new InputStreamReader(url.openStream()));
			while((line = bf.readLine()) != null) {
				result = result.concat(line);
			}
			
			JSONParser jsonParser = new JSONParser();
			JSONObject jsonObject = (JSONObject) jsonParser.parse(result);
			
			JSONArray weatherArray = (JSONArray) jsonObject.get("weather");
			JSONObject obj = (JSONObject) weatherArray.get(0);
			//description
			/*
			 * 2xx, 3xx, 5xx => 비
			 * 6xx => 눈
			 * 7xx => 안개
			 * 800 => 맑음
			 * 801, 802, 803, 804 => 흐림
			 */
			String id = String.valueOf(obj.get("id"));
			obj = (JSONObject) jsonObject.get("main");
			
			/*
			 * 0도 미만 -> 추움
			 * 
			 * 25 초과 -> 더움
			 */
			double temp = Double.parseDouble(obj.get("temp").toString()) - 273.15;
			
			
			/*
			 * 습도
			 * 30 미만 -> 낮음
			 * 
			 * 70 초과 -> 높음
			 */
			int humidity = Integer.parseInt(String.valueOf(obj.get("humidity")));
			
			weatherInfo.setId(id);
			weatherInfo.setTemp(Math.round(temp*100)/100.0);
			weatherInfo.setHumidity(humidity);
			weatherInfo.setDescription_idx(desIdx(id));
			weatherInfo.setHumidity_idx(humIdx(humidity));
			weatherInfo.setTemp_idx(tempIdx(temp));
			weatherInfo.setDes_name(desName(id));
			weatherInfo.setHum_name(humName(humidity));
			
		}catch(Exception e) {
			e.printStackTrace();
		}
		return weatherInfo;
	}
	
	public int desIdx(String id) {
		if(id.charAt(0) == '8') {
			if(id.equals("800"))
				return 1;
			else
				return 2;
		}else if(id.charAt(0) == '6') {
			return 3;
		}else if(id.charAt(0) == '7') {
			return 5;
		}else {
			return 4;
		}
	}
	
	public int humIdx(int humidity) {
		if(humidity < 30) {
			return 3;
		} else if(humidity > 70) {
			return 1;
		}else {
			return 2;
		}
	}
	
	public int tempIdx(double temp) {
		if(temp > 27.0) {
			return 1;
		} else if(temp < 0) {
			return 3;
		} else {
			return 2;
		}
	}
	
	public String desName(String id) {
		if(id.charAt(0) == '8') {
			if(id.equals("800"))
				return "맑음";
			else
				return "흐림";
		}else if(id.charAt(0) == '6') {
			return "눈";
		}else if(id.charAt(0) == '7') {
			return "안개";
		}else {
			return "비";
		}
	}
	
	public String humName(int humidity) {
		if(humidity < 30) {
			return "낮음";
		} else if(humidity > 70) {
			return "보통";
		}else {
			return "적당";
		}
	}
}
