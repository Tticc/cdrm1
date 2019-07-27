package com.spc.cdrm1;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.junit.Test;

import com.fasterxml.jackson.core.format.DataFormatDetector;

import cn.hutool.cron.CronUtil;
import cn.hutool.cron.task.Task;

public class ScheduleTest {

	@Test
	public void testSchedule() {
		// 分 时 日 月 周
		// 每月28号23:00执行任务
		CronUtil.schedule("0 23 28 * ?",new Task() {
			@Override
			public void execute() {
				DateFormat df = new SimpleDateFormat("yyyy-mm-dd hh:mm:ss");
				System.out.println(df.format(new Date())+" start task");
			}
		});
	}
	@Test
	public void test_dateformat() {
		System.out.println("close");
	}
}
