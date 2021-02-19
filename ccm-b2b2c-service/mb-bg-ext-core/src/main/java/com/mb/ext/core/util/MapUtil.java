package com.mb.ext.core.util;

import java.util.HashMap;
import java.util.Map;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.mb.ext.core.constant.MapConstants;
import com.mb.ext.core.service.spec.MapDTO;
import com.mb.ext.msg.HttpClientHelper;

public class MapUtil {

	/**
	 * @param ak 高德地图AK
	 * @param srcLatitude 起点纬度
	 * @param srcLongitude 起点经度
	 * @param destLatitude 终点纬度
	 * @param destLongitude 终点经度
	 * @return 获取两点之间直线距离
	 */
	public static MapDTO getLineDistance(String ak, double srcLatitude,
			double srcLongitude, double destLatitude, double destLongitude) {
		MapDTO mapDTO = new MapDTO();
		Map<String, Object> params = new HashMap<String, Object>();
		String srcLatitudeStr = String.valueOf(srcLatitude);
		String srcLongitudeStr = String.valueOf(srcLongitude);
		String destLatitudeStr = String.valueOf(destLatitude);
		String destLongitudeStr = String.valueOf(destLongitude);
		String origin = srcLatitudeStr + "," + srcLongitudeStr;
		String destination = destLatitudeStr + "," + destLongitudeStr;
		params.put("key", ak);
		params.put("origins", origin);
		params.put("destination", destination);
		params.put("type", "0");	//0-直线距离, 1-驾车导航距离, 3-步行规划距离
		params.put("output", "json");
		String result = HttpClientHelper.sendGet(
				MapConstants.GAODE_MAP_DISTANCE_URL, params, "UTF-8");
		JSONObject resultJson = com.alibaba.fastjson.JSONObject
				.parseObject(result);
		int status = resultJson.getIntValue("status");
		if (status == 1) {
			JSONArray jsonArray = resultJson.getJSONArray("results");
			double distance = jsonArray.getJSONObject(0).getDoubleValue("distance");
			mapDTO.setDistance(distance);
		}
		return mapDTO;
	}


	public static void main(String[] args) {
		MapDTO mapDTO = MapUtil.getLineDistance("1ebb224fcb43af0e4826cb2a5b3869da",40.156878, 116.30815,
				40.256878, 116.30815);
		System.out.print(mapDTO);
	}

}
