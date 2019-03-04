package com.test.utill;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

public class Utill {

	public static String generateUniqueFileName() {
		String filename = "";

		long millis = System.currentTimeMillis();
		String DATE_FORMAT = "yyyyMMdd_HHmmss_SSS";
		SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
		String date = sdf.format(new Date());
		System.out.println("date " + date);
		String num = String.valueOf(new Random().nextInt(9999));
		filename = date + "_" + millis + "_" + num;
		return filename;

	}
}
