package com.chinasoft.mybatis_redis.util;

public class GeetestConfig {
    private  static  final String geetest_id="6cb5f8c10ac107363f7d8f8da5d90a72";
    private static final  String geetest_key="0f41fdf48d2fefbf17c1b0af79247b8c";
  private  static final boolean newfailback=true;

    public static String getGeetest_id() {
        return geetest_id;
    }

    public static String getGeetest_key() {
        return geetest_key;
    }

    public static boolean isNewfailback() {
        return newfailback;
    }
}
