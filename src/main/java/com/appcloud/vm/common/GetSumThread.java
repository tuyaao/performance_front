package com.appcloud.vm.common;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import com.appcloud.vm.action.entity.SumRankingOperaFactory;
import com.appcloud.vm.utils.GetSumResult;

//用于前端固定从hbase里面获取数据，存在内存里，本来应该从Mysql里面获取的，但我懒得写了
public class GetSumThread extends Thread {
	
	public void run() {
		while (true) {
			try {
				Thread.sleep(1000 * 60 * 60);
				SumRankingOperaFactory lists = InitializeListener
						.getCloudPlatformRankingList();
				;
				String testUpToTime = "testUpToTime";
				String testUpToTimeEnd = "testUpToTimeEnd";
				GetSumResult it = new GetSumResult();
				it.execute(lists, testUpToTime, testUpToTimeEnd);
				Calendar startCal = Calendar.getInstance();
				Calendar endCal = Calendar.getInstance();
				startCal.add(Calendar.DATE, -7);
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
				testUpToTime = sdf.format(startCal.getTime());
				testUpToTimeEnd = sdf.format(endCal.getTime());
				InitializeListener
						.synchro(lists, testUpToTime, testUpToTimeEnd);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}
