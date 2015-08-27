package com.mix.util;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.mix.bean.AllExpressageBean;
import com.mix.bean.AllMovieBean;
import com.mix.bean.ExpressageBean;
import com.mix.bean.HotFilmBean;
import com.mix.bean.ISBNBean;
import com.mix.bean.PublicBean;
import com.mix.bean.TranslateBean;
import com.mix.bean.Weather_CityBean;
import com.mix.bean.Weather_CurBean;
import com.mix.bean.WeekWeatherBean;

public class JsonResolveUtil {
	// 翻译
	/*
	 * 返回结果JSON格式示例 { "status": 200, "message": "OK", "data": [ { "dst":
	 * "I'm Chinese, I love my country", "src": "我是中国人，我爱我的国家" } ] }
	 */

	public static List<PublicBean> getJsonResultByTranslate(String jsonString) {
		List<PublicBean> list = new ArrayList<PublicBean>();
		try {
			JSONObject jsonObject = new JSONObject(jsonString);
			PublicBean publicBean = new PublicBean();
			publicBean.setStatus(jsonObject.getInt("status"));
			publicBean.setMessage(jsonObject.getString("message"));
			List<Object> lists = new ArrayList<Object>();
			if (jsonObject.get("data").equals(null)/*
													 * || jsonObject.get("data")
													 * == null
													 */) {
				publicBean.setList(null);
			} else {
				JSONArray jsonArray = jsonObject.getJSONArray("data");
				for (int i = 0; i < jsonArray.length(); i++) {
					TranslateBean translateBean = new TranslateBean();
					translateBean.setDst(jsonArray.getJSONObject(i).getString(
							"dst"));
					System.out.println("dst=" + translateBean.getDst());
					translateBean.setSrc(jsonArray.getJSONObject(i).getString(
							"src"));
					lists.add(translateBean);
				}
				publicBean.setList(lists);
			}
			list.add(publicBean);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return list;
	}

	// 智能快递
	/*
	 * { "status": 200, "message": "OK", "data": [ { "acceptTime":
	 * "2014-03-02 15:59:59", "remark": "签收人是：已签收" }, {"acceptTime":
	 * "2014-03-02 15:59:12", "remark": "派件已签收" }, {"acceptTime":
	 * "2014-03-02 15:01:08", "remark": "正在派件..(派件人:王耀东,电话:13279266187)" }
	 */
	public static List<PublicBean> getJsonResultByExpressage(String jsonString) {
		List<PublicBean> list = new ArrayList<PublicBean>();
		try {
			JSONObject jsonObject = new JSONObject(jsonString);
			PublicBean publicBean = new PublicBean();
			publicBean.setStatus(jsonObject.getInt("status"));
			publicBean.setMessage(jsonObject.getString("message"));
			JSONArray jsonArray = null;
			if(jsonObject.length() != 3){
				publicBean.setList(null);
			}else{
				jsonArray = jsonObject.getJSONArray("data");
			}
			System.out.println("2222222");
			List<Object> lists = new ArrayList<Object>();
			System.out.println("array="+jsonArray);
			if (jsonArray.equals(null)) {
				System.out.println("kaa");
				publicBean.setList(null);
			} else {
				System.out.println("kbb="+jsonArray.length());
				for (int i = 0; i < jsonArray.length(); i++) {
					ExpressageBean expressageBean = new ExpressageBean();
					expressageBean.setAcceptTime(jsonArray.getJSONObject(i)
							.getString("acceptTime"));
					expressageBean.setRemark(jsonArray.getJSONObject(i)
							.getString("remark"));
					lists.add(expressageBean);
				}
				publicBean.setList(lists);
			}
			list.add(publicBean);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return list;
	}
	//所有快递商
	public static List<PublicBean> getJsonResultByAllExpressage(String jsonString) {
		List<PublicBean> list = new ArrayList<PublicBean>();
		try {
			JSONObject jsonObject = new JSONObject(jsonString);
			PublicBean publicBean = new PublicBean();
			publicBean.setStatus(jsonObject.getInt("status"));
			publicBean.setMessage(jsonObject.getString("message"));
			JSONArray jsonArray = jsonObject.getJSONArray("data");
			List<Object> lists = new ArrayList<Object>();
			if (jsonArray.equals(null)) {
				publicBean.setList(null);
			} else {
				for (int i = 0; i < jsonArray.length(); i++) {
					AllExpressageBean allExpressageBean = new AllExpressageBean();
					allExpressageBean.setName(jsonArray.getJSONObject(i)
							.getString("name"));
					allExpressageBean.setCom(jsonArray.getJSONObject(i)
							.getString("com"));
					lists.add(allExpressageBean);
				}
				publicBean.setList(lists);
			}
			list.add(publicBean);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return list;
	}
	//天气查询，支持城市列表
	public static List<PublicBean> getAllCity_Weather(String jsonString) {
		List<PublicBean> list = new ArrayList<PublicBean>();
		try {
			JSONObject jsonObject = new JSONObject(jsonString);
			PublicBean publicBean = new PublicBean();
			publicBean.setStatus(jsonObject.getInt("status"));
			publicBean.setMessage(jsonObject.getString("message"));
			JSONArray jsonArray = jsonObject.getJSONArray("data");
			List<Object> lists = new ArrayList<Object>();
			if (jsonArray.equals(null)) {
				publicBean.setList(null);
			} else {
				for (int i = 0; i < jsonArray.length(); i++) {
					Weather_CityBean weatherBean = new Weather_CityBean();
					weatherBean.setId(jsonArray.getJSONObject(i)
							.getInt("areaid"));
					weatherBean.setProvince(jsonArray.getJSONObject(i)
							.getString("prov"));
					weatherBean.setCity(jsonArray.getJSONObject(i)
							.getString("city"));
					weatherBean.setDistrict(jsonArray.getJSONObject(i)
							.getString("district"));
					lists.add(weatherBean);
				}
				publicBean.setList(lists);
			}
			list.add(publicBean);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return list;
	}
	//实时天气
	/*
	 * {
    "status": 200, 
    "message": "OK", 
    "data": {
        "dateTime": "2014年3月23日", 
        "city": "北京", 
        "temp": "17℃", 
        "minTemp": "7℃", 
        "maxTemp": "20℃", 
        "weather": "晴转阴", 
        "windDirection": "南风", 
        "windForce": "2级", 
        "humidity": "26%", 
        "img_1": "0", 
        "img_2": "2", 
        "refreshTime": "13:18"
    }}
	 * */
	public static List<PublicBean> getCur_Weather(String jsonString) {
		List<PublicBean> list = new ArrayList<PublicBean>();
		try {
			JSONObject jsonObject = new JSONObject(jsonString);
			PublicBean publicBean = new PublicBean();
			publicBean.setStatus(jsonObject.getInt("status"));
			publicBean.setMessage(jsonObject.getString("message"));
			JSONObject json = jsonObject.getJSONObject("data");
			List<Object> lists = new ArrayList<Object>();
			if (json.equals(null)) {
				publicBean.setList(null);
			} else {
					Weather_CurBean weather_CurBean = new Weather_CurBean();
					weather_CurBean.setProv(json.getString("prov"));
					weather_CurBean.setDistrict(json.getString("district"));
					weather_CurBean.setDateTime(json.getString("dateTime"));
					weather_CurBean.setCity(json.getString("city"));
					weather_CurBean.setTemp(json.getString("temp"));
					weather_CurBean.setMinTemp(json.getString("minTemp"));
					weather_CurBean.setMaxTemp(json.getString("maxTemp"));
					weather_CurBean.setWeather(json.getString("weather"));
					weather_CurBean.setWindDirection(json.getString("windDirection"));
					weather_CurBean.setWindForce(json.getString("windForce"));
					weather_CurBean.setHumidity(json.getString("humidity"));
					weather_CurBean.setImg_1(json.getString("img_1"));
					weather_CurBean.setImg_2(json.getString("img_2"));
					weather_CurBean.setRefreshTime(json.getString("refreshTime"));
					lists.add(weather_CurBean);
 				publicBean.setList(lists);
			}
			list.add(publicBean);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return list;
	}
	
	//未来一周天气
	public static List<PublicBean> getFur_Weather(String jsonString) {
		List<PublicBean> list = new ArrayList<PublicBean>();
		try {
			JSONObject jsonObject = new JSONObject(jsonString);
			PublicBean publicBean = new PublicBean();
			publicBean.setStatus(jsonObject.getInt("status"));
			publicBean.setMessage(jsonObject.getString("message"));
			JSONObject json = jsonObject.getJSONObject("data");
			List<Object> lists = new ArrayList<Object>();
			if (json.equals(null)) {
				publicBean.setList(null);
			} else {
					WeekWeatherBean weekWeatherBean = new WeekWeatherBean();
					weekWeatherBean.setCity(json.getString("city"));
					String date_1[] = new String[6];
					date_1[0] = json.getString("date_1");
					date_1[1] = json.getString("date_2");
					date_1[2] = json.getString("date_3");
					date_1[3] = json.getString("date_4");
					date_1[4] = json.getString("date_5");
					date_1[5] = json.getString("date_6");
					String temp_1[] = new String[6];
					temp_1[0] = json.getString("temp_1");
					temp_1[1] = json.getString("temp_2");
					temp_1[2] = json.getString("temp_3");
					temp_1[3] = json.getString("temp_4");
					temp_1[4] = json.getString("temp_5");
					temp_1[5] = json.getString("temp_6");
					String weather_1[] = new String[6];
					weather_1[0] = json.getString("weather_1");
					weather_1[1] = json.getString("weather_2");
					weather_1[2] = json.getString("weather_3");
					weather_1[3] = json.getString("weather_4");
					weather_1[4] = json.getString("weather_5");
					weather_1[5] = json.getString("weather_6");
					String wind_1[] = new String[6];
					wind_1[0] = json.getString("wind_1");
					wind_1[1] = json.getString("wind_2");
					wind_1[2] = json.getString("wind_3");
					wind_1[3] = json.getString("wind_4");
					wind_1[4] = json.getString("wind_5");
					wind_1[5] = json.getString("wind_6");
					String fl_1[] = new String[6];
					fl_1[0] = json.getString("fl_1");
					fl_1[1] = json.getString("fl_2");
					fl_1[2] = json.getString("fl_3");
					fl_1[3] = json.getString("fl_4");
					fl_1[4] = json.getString("fl_5");
					fl_1[5] = json.getString("fl_6");
					int img_1[] = new int[6];
					img_1[0] = json.getInt("img_1");
					img_1[1] = json.getInt("img_3");
					img_1[2] = json.getInt("img_5");
					img_1[3] = json.getInt("img_7");
					img_1[4] = json.getInt("img_9");
					img_1[5] = json.getInt("img_11");
					int img_2[] = new int[6];
					img_2[0] = json.getInt("img_2");
					img_2[1] = json.getInt("img_4");
					img_2[2] = json.getInt("img_6");
					img_2[3] = json.getInt("img_8");
					img_2[4] = json.getInt("img_10");
					img_2[5] = json.getInt("img_12");
					weekWeatherBean.setDate_1(date_1);
					weekWeatherBean.setTemp_1(temp_1);
					weekWeatherBean.setWeather_1(weather_1);
					weekWeatherBean.setWind_1(wind_1);
					weekWeatherBean.setFl_1(fl_1);
					weekWeatherBean.setImg_1(img_1);
					weekWeatherBean.setImg_2(img_2);
					lists.add(weekWeatherBean);
 				publicBean.setList(lists);
			}
			list.add(publicBean);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return list;
	}
	
	//热映影片
	/*
	 * {"status": 200,     "message": "OK",     
	 *  "data": {"cityname": "北京市",         
	 *     		 "location": {            
	 *     						"lng": 116.3975,             
	 *     						"lat": 39.9085464
	 *             },
	 *             "movie": [{  
	 *              	  "movie_id": "7104",
	 *                    "movie_name": "放手爱", 
	 *                    "movie_type": "普通",
	 *                    "movie_release_date": "2014-04-30",
	 *                    "movie_nation": "中国大陆",      
	 *                "movie_starring": "杜汶泽/蔡卓妍/洛诗/念贤儿/黄雅莉",
	 *                   "movie_length": "93分钟", 
	 *                   "movie_picture": "http://Img.wangpiao.com/NewsImage/20144/f9a4cb49-2d30-4c7f-9585-d08d903e802d.jpg", 
	 *                   "movie_score": 8.3, 
	 *                   "movie_director": "张敏", 
	 *                   "movie_tags": "喜剧", 
	 *                    "movie_message": "用音乐讲述爱情故事，"放手去爱"OR"放爱自由"？",
	 *                    "is_imax": 0,                 "is_new": 1,
	 *                    "movies_wd": "放手爱 热门电影"            }]} 
	 * */
	public static List<PublicBean> getHotFilm(String jsonString) {
		System.out.println("resoveString="+jsonString);
		List<PublicBean> list = new ArrayList<PublicBean>();
		try {
			System.out.println("aassaa");
			JSONObject jsonObject = new JSONObject(jsonString);
			System.out.println("mmm");
			System.out.println("jsnooo="+jsonObject);
			PublicBean publicBean = new PublicBean();
			publicBean.setStatus(jsonObject.getInt("status"));
			publicBean.setMessage(jsonObject.getString("message"));
			System.out.println("ccsscc");
			JSONObject json = jsonObject.getJSONObject("data");
			System.out.println("json="+json);
			List<Object> lists = new ArrayList<Object>();
			if (json.equals(null)) {
				publicBean.setList(null);
			} else {
					HotFilmBean hotFilmBean = new HotFilmBean();
					hotFilmBean.setCityName(json.getString("cityname"));
					JSONObject locaObject = json.getJSONObject("location");
					float[] loca = new float[2];
					loca[0] = (float)locaObject.getDouble("lng");
					loca[1] = (float)locaObject.getDouble("lat");
					hotFilmBean.setLocation(loca);
					List<Object> movieList = new ArrayList<Object>();
					JSONArray movieArray = json.getJSONArray("movie");
					System.out.println("jsonArray="+movieArray);
					for(int i = 0; i < movieArray.length(); i++){
						AllMovieBean allMovieBean = new AllMovieBean();
						JSONObject movieObject = movieArray.getJSONObject(i);
						allMovieBean.setMovie_id(movieObject.getString("movie_id"));
						allMovieBean.setMovie_name(movieObject.getString("movie_name"));
						allMovieBean.setMovie_type(movieObject.getString("movie_type"));
						allMovieBean.setMovie_release_date(movieObject.getString("movie_release_date"));
						allMovieBean.setMovie_nation(movieObject.getString("movie_nation"));
						allMovieBean.setMovie_staring(movieObject.getString("movie_starring"));
						allMovieBean.setMovie_length(movieObject.getString("movie_length"));
						allMovieBean.setMovie_picture(movieObject.getString("movie_picture"));
						allMovieBean.setMovie_score((float)movieObject.getDouble("movie_score"));
						allMovieBean.setMovie_direction(movieObject.getString("movie_director"));
						allMovieBean.setMovie_tags(movieObject.getString("movie_tags"));
						allMovieBean.setMovie_message(movieObject.getString("movie_message"));
						allMovieBean.setIs_imax(movieObject.getInt("is_imax"));
						allMovieBean.setIs_new(movieObject.getInt("is_new"));
						allMovieBean.setMovies_wd(movieObject.getString("movies_wd"));
						movieList.add(allMovieBean);
					}
					hotFilmBean.setMovie(movieList);
					lists.add(hotFilmBean);
 				publicBean.setList(lists);
			}
			list.add(publicBean);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return list;
	}
	
	//ISBN查询
	public static List<PublicBean> getIsbnInfo(String jsonString) {
		List<PublicBean> list = new ArrayList<PublicBean>();
		try {
			System.out.println(";';';");
			JSONObject jsonObject = new JSONObject(jsonString);
			System.out.println(".........");
			PublicBean publicBean = new PublicBean();
			publicBean.setStatus(jsonObject.getInt("status"));
			publicBean.setMessage(jsonObject.getString("message"));
			JSONObject json = jsonObject.getJSONObject("data");
			List<Object> lists = new ArrayList<Object>();
			if (json.equals(null)) {
				publicBean.setList(null);
			} else {
					ISBNBean isbnBean = new ISBNBean();
					isbnBean.setTitle(json.getString("title"));
//					isbnBean.setSubTitle(json.getString("subtitle"));
					isbnBean.setIsbn10(json.getString("isbn10"));
					isbnBean.setIsbn13(json.getString("isbn13"));
					isbnBean.setAuthor_info(json.getString("author_info"));
					isbnBean.setPages(json.getString("pages"));
					isbnBean.setAuthor(json.getString("author"));
//					isbnBean.setTranslator(json.getString("translator"));
					isbnBean.setPrice(json.getString("price"));
					isbnBean.setBinding(json.getString("binding"));
					isbnBean.setPublisher(json.getString("publisher"));
					isbnBean.setPubDate(json.getString("pubdate"));
					lists.add(isbnBean);
 				publicBean.setList(lists);
			}
			list.add(publicBean);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		
		return list;
	}
	
}
