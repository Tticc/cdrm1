package com.spc.cdrm1.util;

import java.text.SimpleDateFormat;
import java.util.Date;

public class CommonUtil {

	public static String getDateString() {
		SimpleDateFormat df = new SimpleDateFormat("yyyy-mm-dd hh:mm:ss E");
		return df.format(new Date());
	}
}
