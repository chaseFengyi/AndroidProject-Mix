package com.mix.dao;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import android.os.Environment;

public class ExpressCarrier {
	public final static String STOREPOSITION = "SearchWizard"; 
	public final static String EXPRESSCARRIER = "expressCarrier";
	
	public static String getSDPath() {

		File sdDir = null;
		boolean sdCardExist = Environment.getExternalStorageState().equals(
				android.os.Environment.MEDIA_MOUNTED);// 判断sd卡是否存在
		if (sdCardExist) {
			sdDir = Environment.getExternalStorageDirectory();
		}

		return sdDir.toString();
	}
	
	// 创建指定目录
		public static File createDir() {
			String path = getSDPath();

			if (path.equals(null)) {
				//Toast.makeText(context, "检查是否插入sd卡", Toast.LENGTH_SHORT).show();
			} else {
				File file = new File(path);
				try {
					File files = new File(file.getCanonicalPath() + "/"
							+ STOREPOSITION);
					if (files.exists()) {
						return files;
					} else {
						if (files.mkdirs()) {
							return files;
						}
					}
				} catch (FileNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					//Toast.makeText(context, "保存失败！", Toast.LENGTH_SHORT).show();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					//Toast.makeText(context, "保存失败！", Toast.LENGTH_SHORT).show();
				}
			}

			return null;
		}
	
		/**
		 * @param context
		 * @return 
		 */
		public static List<String> read() {
			File file = createDir();
			if (file == null) {
				//Toast.makeText(context, "创建目录失败！", Toast.LENGTH_SHORT).show();
				return null;
			} else {
				File targetFile = null;
				try {
					targetFile = new File(file.getCanonicalPath() + "/" + EXPRESSCARRIER);
					if (!targetFile.exists()) {
						/*Toast.makeText(context, "文件不存在，不能读取！", Toast.LENGTH_SHORT)
								.show();*/
					} else {
						// 获取指定文件对应的输入流
						FileInputStream fileInputStream = new FileInputStream(
								targetFile.toString());
						// 将指定输入流包装成BufferedReader
						BufferedReader bufferedReader = new BufferedReader(
								new InputStreamReader(fileInputStream));
						List<String> list = new ArrayList<String>();
						String line = null;
						// 循环读取文件内容
						while ((line = bufferedReader.readLine()) != null) {
							list.add(line);
						}
						bufferedReader.close();

						return list;
					}

				} catch (FileNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
//					Toast.makeText(context, "从"+targetFile.toString()+"读取失败", Toast.LENGTH_SHORT).show();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
//					Toast.makeText(context, "从"+targetFile.toString()+"读取失败", Toast.LENGTH_SHORT).show();
				}
			}

			return null;
		}
		
		// 写的内容直接覆盖
		public static void writeCover(String content) {

			if (content.equals(null) || content.equals("")) {
//				Toast.makeText(context, "要保存的内容为空", Toast.LENGTH_SHORT).show();
				return;
			}

			File file = createDir();
			if (file == null) {
//				Toast.makeText(context, "创建"+file.toString()+"目录失败！", Toast.LENGTH_SHORT).show();
				return;
			} else {
				final File targetFile;
				try {
					targetFile = new File(file.getCanonicalPath() + "/" + EXPRESSCARRIER);
					try {
						FileOutputStream fileOutputStream = new FileOutputStream(
								targetFile);
						byte[] bytes = content.getBytes();
						fileOutputStream.write(bytes);
						fileOutputStream.close();
					} catch (IOException e) {
						// TODO Auto-generated catch
						e.printStackTrace();
					}
				} catch (IOException e2) {
					// TODO Auto-generated catch block
					e2.printStackTrace();
				}

			}
		}

}
