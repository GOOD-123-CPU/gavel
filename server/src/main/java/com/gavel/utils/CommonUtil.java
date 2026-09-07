package com.gavel.utils;

import java.security.SecureRandom;

public class CommonUtil {
	private static final SecureRandom RANDOM = new SecureRandom();
	private static final String BASE = "abcdefghijklmnopqrstuvwxyz0123456789";

	/**
     * 获取安全随机字符串（使用 SecureRandom，用于生成 token）
     *
     * @param num 长度
     * @return 随机字符串
     */
    public static String getRandomString(Integer num) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < num; i++) {
            sb.append(BASE.charAt(RANDOM.nextInt(BASE.length())));
        }
        return sb.toString();
    }
}
