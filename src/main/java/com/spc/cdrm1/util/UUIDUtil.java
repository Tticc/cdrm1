package com.spc.cdrm1.util;

import java.util.UUID;

public class UUIDUtil {

	public static String getUUID() {
		return UUID.randomUUID().toString().replaceAll("-", "");
	}
}
