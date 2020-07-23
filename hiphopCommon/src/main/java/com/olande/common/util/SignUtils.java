package com.olande.common.util;

import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

/**
 * 签名工具
 * 
 * @author chengfr
 * 
 */
public class SignUtils {

	public SignUtils() {
		super();
	}

	/**
	 * 获得签名
	 * 
	 * @param paramMap
	 *            :请求参数map集合
	 * @return
	 */
	public String getSign(Map<String, String> paramMap) {
		if (null == paramMap || 0 == paramMap.size()) {
			throw new IllegalArgumentException("没有参与签名的参数");
		}
		Set<String> keySet = new TreeSet<String>(paramMap.keySet());
		Iterator<String> keyIter = keySet.iterator();
		StringBuffer strABuf = new StringBuffer();
		while (keyIter.hasNext()) {
			String key = keyIter.next();
			strABuf.append(key);
			strABuf.append('=');
			String value = paramMap.get(key);
			strABuf.append(value);
			strABuf.append('&');
		}
		String stringA = strABuf.toString();
		String stringSignTemp = stringA + "key=hiphop";
		String sign = EncryptUtils.getMD5(stringSignTemp).toUpperCase();
		return sign;
	}

	/**
	 * 获取随机字符串
	 * 
	 * @return
	 */
	public String getNonceStr() {
		String randomStr = RandomCodeUtils.bulidRepeatCodes(512)
				+ String.valueOf(System.currentTimeMillis());
		return EncryptUtils.getMD5(randomStr).toUpperCase();
	}

	/**
	 * 标准北京时间,时区为东八区,自1970年1月1日 0点0分0秒以来的秒数(10位数字) 对于毫秒进行四舍五入
	 * 
	 * @return
	 */
	public long getNowSeconds() {
		long time = System.currentTimeMillis();
		return Math.round(time / 1000.0);
	}

	/**
	 * 失效时间
	 * 
	 * @param min
	 *            :交易失效时间(单位分钟,必须大于5分钟)
	 * @return
	 */
	public long getTimeExpire(int min) {
		if (min < 5) {
			throw new IllegalArgumentException("最短失效时间间隔必须大于5分钟");
		}
		return getNowSeconds() + min * 60;
	}

}
