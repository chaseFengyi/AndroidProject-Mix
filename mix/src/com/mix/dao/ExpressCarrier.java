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
				android.os.Environment.MEDIA_MOUNTED);// �ж�sd���Ƿ����
		if (sdCardExist) {
			sdDir = Environment.getExternalStorageDirectory();
		}

		return sdDir.toString();
	}
	
	// ����ָ��Ŀ¼
		public static File createDir() {
			String path = getSDPath();

			if (path.equals(null)) {
				//Toast.makeText(context, "����Ƿ����sd��", Toast.LENGTH_SHORT).show();
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
					//Toast.makeText(context, "����ʧ�ܣ�", Toast.LENGTH_SHORT).show();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					//Toast.makeText(context, "����ʧ�ܣ�", Toast.LENGTH_SHORT).show();
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
				//Toast.makeText(context, "����Ŀ¼ʧ�ܣ�", Toast.LENGTH_SHORT).show();
				return null;
			} else {
				File targetFile = null;
				try {
					targetFile = new File(file.getCanonicalPath() + "/" + EXPRESSCARRIER);
					if (!targetFile.exists()) {
						/*Toast.makeText(context, "�ļ������ڣ����ܶ�ȡ��", Toast.LENGTH_SHORT)
								.show();*/
					} else {
						// ��ȡָ���ļ���Ӧ��������
						FileInputStream fileInputStream = new FileInputStream(
								targetFile.toString());
						// ��ָ����������װ��BufferedReader
						BufferedReader bufferedReader = new BufferedReader(
								new InputStreamReader(fileInputStream));
						List<String> list = new ArrayList<String>();
						String line = null;
						// ѭ����ȡ�ļ�����
						while ((line = bufferedReader.readLine()) != null) {
							list.add(line);
						}
						bufferedReader.close();

						return list;
					}

				} catch (FileNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
//					Toast.makeText(context, "��"+targetFile.toString()+"��ȡʧ��", Toast.LENGTH_SHORT).show();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
//					Toast.makeText(context, "��"+targetFile.toString()+"��ȡʧ��", Toast.LENGTH_SHORT).show();
				}
			}

			return null;
		}
		
		// д������ֱ�Ӹ���
		public static void writeCover(String content) {

			if (content.equals(null) || content.equals("")) {
//				Toast.makeText(context, "Ҫ���������Ϊ��", Toast.LENGTH_SHORT).show();
				return;
			}

			File file = createDir();
			if (file == null) {
//				Toast.makeText(context, "����"+file.toString()+"Ŀ¼ʧ�ܣ�", Toast.LENGTH_SHORT).show();
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
