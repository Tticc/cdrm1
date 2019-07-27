package com.spc.cdrm1.config.mutidatasource;

import java.util.ArrayList;
import java.util.List;

public class DynamicDataSourceContextHolder {
	private static final ThreadLocal<String> contextHolder = new ThreadLocal();
	public static List<Object> dataSourceKeys = new ArrayList<>();
	/**
	 * 转换数据源
	 * @author Wen, Changying
	 * @param key
	 * @date 2019年7月23日
	 */
	public static void setDataSourceKey(String key) {
        contextHolder.set(key);
    }
	/**
	 * 获取当前数据源
	 * @author Wen, Changying
	 * @return
	 * @date 2019年7月23日
	 */
	public static String getDataSourceKey() {
        return contextHolder.get();
    }
	/**
	 * 将数据源设置为默认数据源
	 * @author Wen, Changying
	 * @date 2019年7月23日
	 */
	public static void clearDataSourceKey() {
        contextHolder.remove();
    }
	/**
	 * 检查数据源是否在当前数据源列表
	 * @author Wen, Changying
	 * @param key
	 * @return
	 * @date 2019年7月23日
	 */
	public static boolean containDataSourceKey(String key) {
        return dataSourceKeys.contains(key);
    }

}
