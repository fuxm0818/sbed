/**
 * Copyright (c) 2015-2016, Michael Yang 杨福海 (fuhai999@gmail.com).
 *
 * Licensed under the GNU Lesser General Public License (LGPL) ,Version 3.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.gnu.org/licenses/lgpl-3.0.txt
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package io.sbed.common.utils;

import io.sbed.common.Constant;
import io.sbed.common.exception.SbedException;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

/**
 * @author heguoliang
 * @Description: TODO(文件处理)
 * @date 2017-6-23 15:07
 */
public class FileUtils {

	public static String getSuffix(String fileName) {
		if (fileName != null && fileName.contains(".")) {
			return fileName.substring(fileName.lastIndexOf("."));
		}
		return null;
	}

	public static String removePrefix(String src, String prefix) {
		if (src != null && src.startsWith(prefix)) {
			return src.substring(prefix.length());
		}
		return src;
	}

	public static String readString(File file) {
		ByteArrayOutputStream baos = null;
		FileInputStream fis = null;
		try {
			fis = new FileInputStream(file);
			baos = new ByteArrayOutputStream();
			byte[] buffer = new byte[1024];
			for (int len = 0; (len = fis.read(buffer)) > 0;) {
				baos.write(buffer, 0, len);
			}
			return new String(baos.toByteArray(), Constant.ENCODING_UTF_8);
		} catch (Exception e) {
		} finally {
			close(fis, baos);
		}
		return null;
	}

	public static void writeString(File file, String string) {
		FileOutputStream fos = null;
		try {
			fos = new FileOutputStream(file, false);
			fos.write(string.getBytes(Constant.ENCODING_UTF_8));
		} catch (Exception e) {
		} finally {
			close(null, fos);
		}
	}

	private static void close(InputStream is, OutputStream os) {
		if (is != null)
			try {
				is.close();
			} catch (IOException e) {
			}
		if (os != null)
			try {
				os.close();
			} catch (IOException e) {
			}
	}

	public static void unzip(String zipFilePath) throws IOException {
		String targetPath = zipFilePath.substring(0, zipFilePath.lastIndexOf("."));
		unzip(zipFilePath, targetPath);
	}

	public static void unzip(String zipFilePath, String targetPath) throws IOException {
		ZipFile zipFile = new ZipFile(zipFilePath);
		try{
			Enumeration<?> entryEnum = zipFile.entries();
			if (null != entryEnum) {
				while (entryEnum.hasMoreElements()) {
					OutputStream os = null;
					InputStream is = null;
					try {
						ZipEntry zipEntry = (ZipEntry) entryEnum.nextElement();
						if (!zipEntry.isDirectory()) {
							File targetFile = new File(targetPath + File.separator + zipEntry.getName());
							if (!targetFile.getParentFile().exists()) {
								targetFile.getParentFile().mkdirs();
							}
							os = new BufferedOutputStream(new FileOutputStream(targetFile));
							is = zipFile.getInputStream(zipEntry);
							byte[] buffer = new byte[4096];
							int readLen = 0;
							while ((readLen = is.read(buffer, 0, 4096)) > 0) {
								os.write(buffer, 0, readLen);
							}
						}
					} finally {
						if (is != null)
							is.close();
						if (os != null)
							os.close();
					}
				}
			}
		}finally{
			zipFile.close();
		}
	}

	/**
	 * 获取临时目录
	 */
	public static String getTempPath(){
		return System.getProperty("java.io.tmpdir");
	}

	/**
	 * 文件下载
	 * @param path
	 * @param fileName
	 * @param response
	 */
	public static void download(String path, String fileName, HttpServletResponse response){
		File file=new File(path);
		if(!file.exists()){
			throw new SbedException("文件不存在");
		}else{
			response.setContentType("application/force-download");
			response.setHeader("Content-Disposition", "attachment;fileName=" + fileName);

			byte[] buffer = new byte[1024];
			FileInputStream fis = null;
			BufferedInputStream bis = null;

			OutputStream os = null;
			try {
				os = response.getOutputStream();
				fis = new FileInputStream(file);
				bis = new BufferedInputStream(fis);
				int i = bis.read(buffer);
				while(i != -1){
					os.write(buffer);
					i = bis.read(buffer);
				}
			} catch (Exception e) {
				throw new SbedException("系统异常");
			} finally {
				try {
					os.close();
					bis.close();
					fis.close();
				} catch (Exception e) {
					throw new SbedException("系统异常");
				}
			}
		}
	}

}