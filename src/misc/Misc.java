/*
  Copyright 2019 www.dev5.cn, Inc. dev5@qq.com
 
  This file is part of X-MSG-IM.
 
  X-MSG-IM is free software: you can redistribute it and/or modify
  it under the terms of the GNU General Public License as published by
  the Free Software Foundation, either version 3 of the License, or
  (at your option) any later version.

  X-MSG-IM is distributed in the hope that it will be useful,
  but WITHOUT ANY WARRANTY; without even the implied warranty of
  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
  GNU General Public License for more details.
 
  You should have received a copy of the GNU Affero General Public License
  along with X-MSG-IM.  If not, see <https://www.gnu.org/licenses/>.
 */
package misc;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.io.RandomAccessFile;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLDecoder;
import java.text.DecimalFormat;
import java.util.Comparator;
import java.util.Enumeration;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ThreadLocalRandom;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Function;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonParser;
import com.google.protobuf.Message;
import com.google.protobuf.MessageOrBuilder;
import com.google.protobuf.util.JsonFormat;

public final class Misc
{
	public static final String UTF_8 = "UTF-8";
	public static final String GBK = "GBK";
	public static final String ISO_8859_1 = "ISO-8859-1";
	public static final String COMMA = ",";
	public static final String COMMA2 = ";";
	public static final String SPACE = " ";
	public static final String LINE = "\n";
	public static final String LINE_R_N = "\r\n";
	public static final String _LINE = "_";
	public static final String UNIX_FILE_SEPARATOR = "/";
	public static final long procup = System.currentTimeMillis();
	private static final DecimalFormat df = new DecimalFormat("#0.00");
	private static final PrintStream out = new PrintStream(System.out);
	private static final BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
	private static final Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();

	public static final String readLine()
	{
		try
		{
			return Misc.in.readLine();
		} catch (IOException e)
		{
			return null;
		}
	}

	public static final String trim(String arg)
	{
		return arg == null ? null : ("".equals(arg.trim()) ? null : arg.trim());
	}

	public static final long forceLongO(String arg)
	{
		try
		{
			long value = Long.parseLong(Misc.trim(arg));
			return value < 0 ? 0 : value;
		} catch (Exception e)
		{
			return 0;
		}
	}

	public static final double forceDouble0(String arg)
	{
		try
		{
			double value = Double.parseDouble(Misc.trim(arg));
			return value < 0.00f ? 0.00f : value;
		} catch (Exception e)
		{
			return 0.00f;
		}
	}

	public static final double forceDouble_1(String arg)
	{
		try
		{
			double value = Double.parseDouble(Misc.trim(arg));
			return value < -1f ? -1f : value;
		} catch (Exception e)
		{
			return -1f;
		}
	}

	public static final double double0(String arg)
	{
		try
		{
			return Double.parseDouble(Misc.trim(arg));
		} catch (Exception e)
		{
			return 0D;
		}
	}

	public static final int int_1(String arg)
	{
		try
		{
			return Integer.parseInt(Misc.trim(arg));
		} catch (Exception e)
		{
			return -1;
		}
	}

	public static final int forceInt_1(String arg)
	{
		try
		{
			int value = Integer.parseInt(Misc.trim(arg));
			return value < -1 ? -1 : value;
		} catch (Exception e)
		{
			return -1;
		}
	}

	public static final int int0(String arg)
	{
		try
		{
			return Integer.parseInt(Misc.trim(arg));
		} catch (Exception e)
		{
			return 0;
		}
	}

	public static final int forceInt0(String arg)
	{
		try
		{
			int value = Integer.parseInt(Misc.trim(arg));
			return value < 0 ? 0 : value;
		} catch (Exception e)
		{
			return 0;
		}
	}

	public static final int int1(String arg)
	{
		try
		{
			return Integer.parseInt(Misc.trim(arg));
		} catch (Exception e)
		{
			return 1;
		}
	}

	public static final int forceInt1(String arg)
	{
		try
		{
			int value = Integer.parseInt(Misc.trim(arg));
			return value < 1 ? 1 : value;
		} catch (Exception e)
		{
			return 1;
		}
	}

	public static final float forceFloat0(String arg)
	{
		try
		{
			float value = Float.parseFloat(Misc.trim(arg));
			return value < 0 ? 0 : value;
		} catch (Exception e)
		{
			return 0;
		}
	}

	public static final float forceFloat1(String arg)
	{
		try
		{
			float value = Float.parseFloat(Misc.trim(arg));
			return value < 1.0f ? 1.0f : value;
		} catch (Exception e)
		{
			return 1.0f;
		}
	}

	public static final float forceFloat_1(String arg)
	{
		try
		{
			float value = Float.parseFloat(Misc.trim(arg));
			return value < -1.00f ? -1.00f : value;
		} catch (Exception e)
		{
			return -1;
		}
	}

	public static final float float0(String arg)
	{
		try
		{
			return Float.parseFloat(Misc.trim(arg));
		} catch (Exception e)
		{
			return 0;
		}
	}

	
	public static final byte[] parseByteArr(String str)
	{
		int arr[] = Misc.parseIntArr(str);
		if (arr == null || arr.length < 1)
			return null;
		byte by[] = new byte[arr.length];
		for (int i = 0; i < arr.length; ++i)
			by[i] = (byte) arr[i];
		return by;
	}

	
	public static final int[] parseIntArr(String str)
	{
		try
		{
			String arr[] = str.split(Misc.COMMA);
			if (arr == null || arr.length < 1)
				return null;
			int ia[] = new int[arr.length];
			for (int i = 0; i < ia.length; ++i)
				ia[i] = Integer.parseInt(Misc.trim(arr[i]));
			return ia;
		} catch (Exception e)
		{
			return null;
		}
	}

	
	public static final String[] parseStrArr(String str)
	{
		try
		{
			String arr[] = str.split(Misc.COMMA);
			if (arr == null || arr.length < 1)
				return null;
			for (int i = 0; i < arr.length; ++i)
			{
				arr[i] = Misc.trim(arr[i]);
				if (arr[i] == null)
					return null;
			}
			return arr;
		} catch (Exception e)
		{
			return null;
		}
	}

	
	public static final List<String> parseStr2list(String str)
	{
		try
		{
			String arr[] = str.split(Misc.COMMA);
			if (arr == null || arr.length < 1)
				return null;
			LinkedList<String> list = new LinkedList<>();
			for (int i = 0; i < arr.length; ++i)
			{
				String s = Misc.trim(arr[i]);
				if (s == null)
					return null;
				list.add(s);
			}
			return list.isEmpty() ? null : list;
		} catch (Exception e)
		{
			return null;
		}
	}

	public static final String intArr2string(int arr[])
	{
		StringBuilder strb = new StringBuilder();
		for (int i = 0; i < arr.length; i++)
			strb.append(arr[i]).append(Misc.COMMA);
		return strb.substring(0, strb.length() - 1);
	}

	public static final String intArr2string(List<Integer> arr)
	{
		StringBuilder strb = new StringBuilder();
		for (Integer str : arr)
			strb.append(str).append(Misc.COMMA);
		return strb.substring(0, strb.length() - 1);
	}

	public static final String strArr2stringWithSQ(String[] arr)
	{
		StringBuilder strb = new StringBuilder();
		for (int i = 0; i < arr.length; i++)
			strb.append("'").append(arr[i]).append("',");
		return strb.substring(0, strb.length() - 1);
	}

	public static final String strArr2stringWithSQ(List<String> arr)
	{
		StringBuilder strb = new StringBuilder();
		for (String str : arr)
			strb.append("'").append(str).append("',");
		return strb.substring(0, strb.length() - 1);
	}

	public static final String strArr2stringWithNoSQ(List<String> arr)
	{
		StringBuilder strb = new StringBuilder();
		for (String str : arr)
			strb.append(str).append(",");
		return strb.substring(0, strb.length() - 1);
	}

	public static final boolean isNull(String arg)
	{
		return Misc.trim(arg) == null;
	}

	public static final Byte parseByte(String arg)
	{
		try
		{
			return Byte.parseByte(trim(arg));
		} catch (Exception e)
		{
			return null;
		}
	}

	public static final boolean isDigital(String arg)
	{
		arg = trim(arg);
		if (arg == null)
			return false;
		char[] array = arg.toCharArray();
		for (char chr : array)
		{
			if (!Character.isDigit(chr))
				return false;
		}
		return true;
	}

	public static final boolean isDigital(byte by)
	{
		return by >= 0x30 && by <= 0x39;
	}

	public static final boolean isLetter(byte by)
	{
		return (by >= 0x61 && by <= 0x7A) || (by >= 0x41 && by <= 0x5A);
	}

	public static final boolean isDigitalOrLetter(byte by[])
	{
		for (int i = 0; i < by.length; ++i)
			if (!Misc.isDigital(by[i]) && !Misc.isLetter(by[i]))
				return false;
		return by.length > 0;
	}

	public static final boolean isDigitalOrLetter(byte by[], int ofst, int len)
	{
		for (int i = ofst; i < len; ++i)
			if (!Misc.isDigital(by[i]) && !Misc.isLetter(by[i]))
				return false;
		return len > 0;
	}

	public static final boolean isReadableAscii(byte by[])
	{
		for (int i = 0; i < by.length; ++i)
			if (by[i] < 0x20 || by[i] > 0x7E)
				return false;
		return by.length > 0;
	}

	public static final void printf(String format, Object... args)
	{
		Misc.out.printf(format, args);
	}

	public static final void printf(byte by[])
	{
		Misc.printf("%s\n", Net.printBytes(by, 0, by.length));
	}

	public static final void printf(byte by[], int offset, int length)
	{
		Misc.printf(Net.printBytes(by, offset, length));
	}

	public static final String printf2str(String format, Object... args)
	{
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		PrintStream ps = new PrintStream(bos);
		ps.printf(format, args);
		return bos.toString();
	}

	public static final String printBytes(byte by[])
	{
		return Net.printBytes(by, 0, by.length);
	}

	public static final String printBytes(byte by[], int offset, int length)
	{
		return Net.printBytes(by, offset, length);
	}

	public static final String decimalformat(double val)
	{
		return Misc.df.format(val);
	}

	public static final boolean isEmail(String email)
	{
		if (email == null)
			return false;
		byte by[] = email.getBytes();
		for (int i = 0; i < by.length; i++)
		{
			if (by[i] != 0x2D  && 
					by[i] != 0x2E  && 
					by[i] != 0x40  && 
					by[i] != 0x5F  && 
					(by[i] < 0x30 || by[i] > 0x39 ) && 
					(by[i] < 0x61 || by[i] > 0x7A ) && 
					(by[i] < 0x41 || by[i] > 0x5A ))
				return false;
		}
		return true;
	}

	public static final String iso2utf8(String str)
	{
		try
		{
			return new String(str.getBytes(Misc.ISO_8859_1), Misc.UTF_8);
		} catch (Exception e)
		{
			return null;
		}
	}

	public static final String iso2gbk(String str)
	{
		try
		{
			return new String(str.getBytes(Misc.ISO_8859_1), Misc.GBK);
		} catch (Exception e)
		{
			return null;
		}
	}

	public static final String iso2gbk(byte by[], int ofst, int len)
	{
		try
		{
			return new String(by, ofst, len, Misc.GBK);
		} catch (Exception e)
		{
			return null;
		}
	}

	public static final double randPercent(double val, float bp, float f)
	{
		if (val < 0.00001)
			return val;
		double bv = val * bp;
		double fv = bv + (val * f);
		return ThreadLocalRandom.current().nextDouble(bv, fv);
	}

	public static final long randLang()
	{
		return ThreadLocalRandom.current().nextLong() & 0x7FFFFFFFFFFFFFFFL;
	}

	public static final int randInt()
	{
		return (int) (ThreadLocalRandom.current().nextLong() & 0x7FFFFFFF);
	}

	public static final boolean bitset(byte by[], int bit)
	{
		return ((by[(bit / 8)] >> (bit % 8)) & 0x01) == 1;
	}

	public static final void sleep(long ms)
	{
		try
		{
			Thread.sleep(ms);
		} catch (Exception e)
		{
			if (Log.isError())
				Log.error("%s", Log.trace(e));
		}
	}

	public static final void hold()
	{
		try
		{
			Thread.currentThread().join();
		} catch (Exception e)
		{
			if (Log.isError())
				Log.error("%s", Log.trace(e));
		}
	}

	public static final <T> List<T> paging(List<T> list, int page, int pagesize)
	{
		int skip = page * pagesize;
		if (skip >= list.size())
			return null;
		LinkedList<T> ret = new LinkedList<>();
		for (int i = skip; i < skip + pagesize && i < list.size(); ++i)
			ret.add(list.get(i));
		return ret;
	}

	public static final List<String> loadFileLines(String path)
	{
		return Misc.loadFileLines(new File(path));
	}

	public static final List<String> loadFileLines(File file)
	{
		RandomAccessFile raf = null;
		try
		{
			LinkedList<String> lines = new LinkedList<>();
			raf = new RandomAccessFile(file, "r");
			String line = raf.readLine();
			while (line != null)
			{
				lines.add(line);
				line = raf.readLine();
			}
			return lines;
		} catch (Exception e)
		{
			if (Log.isError())
				Log.error("%s", Log.trace(e));
			return null;
		} finally
		{
			Misc.closeRaf(raf);
		}
	}

	public static final byte[] loadFile(String path)
	{
		File f = new File(path);
		return Misc.loadFile(f);
	}

	public static final byte[] loadFile(File f)
	{
		FileInputStream fis = null;
		DataInputStream dis = null;
		try
		{
			fis = new FileInputStream(f);
			dis = new DataInputStream(fis);
			byte by[] = new byte[(int) f.length()];
			dis.readFully(by);
			return by;
		} catch (Exception e)
		{
			return null;
		} finally
		{
			Misc.closeInputStream(dis);
			Misc.closeInputStream(fis);
		}
	}

	public static final File[] loadFiles(String dir)
	{
		LinkedList<File> arr = new LinkedList<>();
		Misc.__loadFiles__(new File(dir), arr);
		if (arr.size() < 1)
			return null;
		File fs[] = new File[arr.size()];
		int i = 0;
		for (File f : arr)
			fs[i++] = f;
		return fs;
	}

	private static final void __loadFiles__(File file, LinkedList<File> arr)
	{
		if (file.isDirectory())
		{
			File fs[] = file.listFiles();
			for (int i = 0; fs != null && i < fs.length; ++i)
				Misc.__loadFiles__(fs[i], arr);
		} else
			arr.add(file);
	}

	public static final boolean writeFile(String path, byte by[])
	{
		FileOutputStream fos = null;
		try
		{
			fos = new FileOutputStream(path);
			fos.write(by);
			fos.flush();
			return true;
		} catch (Exception e)
		{
			if (Log.isError())
				Log.error("%s", Log.trace(e));
			return false;
		} finally
		{
			Misc.closeOutputStream(fos);
		}
	}

	public static final boolean writeFileAppend(String path, byte by[])
	{
		RandomAccessFile raf = null;
		try
		{
			raf = new RandomAccessFile(new File(path), "rw");
			raf.seek(raf.length());
			raf.write(by);
			return true;
		} catch (Exception e)
		{
			if (Log.isError())
				Log.error("%s", Log.trace(e));
			return false;
		} finally
		{
			Misc.closeRaf(raf);
		}
	}

	public static final boolean isSuffix(String name, String suffix)
	{
		int i = name.indexOf(suffix);
		return i < 1 ? false : i == name.length() - suffix.length();
	}

	
	public static final String getSuffix(String name)
	{
		int i = name.lastIndexOf(".");
		return (i < 0 || i == name.length() - 1) ? "" : name.substring(i + 1, name.length());
	}

	public static final void closeInputStream(InputStream ins)
	{
		if (ins != null)
		{
			try
			{
				ins.close();
			} catch (IOException e)
			{
				if (Log.isError())
					Log.error("%s", Log.trace(e));
			}
			ins = null;
		}
	}

	public static final void closeOutputStream(OutputStream ops)
	{
		if (ops != null)
		{
			try
			{

				ops.close();
			} catch (Exception e)
			{
				if (Log.isError())
					Log.error("%s", Log.trace(e));
			}
			ops = null;
		}
	}

	public static final void closeRaf(RandomAccessFile raf)
	{
		if (raf != null)
		{
			try
			{
				raf.close();
			} catch (Exception e)
			{
				if (Log.isError())
					Log.error("%s", Log.trace(e));
			}
			raf = null;
		}
	}

	@SuppressWarnings("unchecked")
	public static final <T> Class<T> classForName(String cls, Class<T> sup)
	{
		try
		{
			Class<?> c = Class.forName(cls);
			return (c.getSuperclass().getName().equals(sup.getName())) ? (Class<T>) c : null;
		} catch (Exception e)
		{
			if (Log.isDebug())
				Log.debug("%s", Log.trace(e));
			return null;
		}
	}

	@SuppressWarnings("unchecked")
	public static final <T> Class<T> loadClass(ClassLoader loader, String cls, Class<T> sup)
	{
		try
		{
			Class<?> c = loader.loadClass(cls);
			return (c.getSuperclass().getName().equals(sup.getName())) ? (Class<T>) c : null;
		} catch (Exception e)
		{
			if (Log.isDebug())
				Log.debug("%s", Log.trace(e));
			return null;
		}
	}

	public static final String getMethodPars(Class<?> cls, String funName)
	{
		Method m[] = cls.getDeclaredMethods();
		StringBuilder strb = new StringBuilder();
		for (int i = 0; m != null && i < m.length; i++)
		{
			if (m[i].getName().equals(funName))
			{
				Class<?> x[] = m[i].getParameterTypes();
				for (int j = 0; x != null && j < x.length; j++)
					strb.append("L").append(x[j].getName()).append(";");
			}
		}
		String str = strb.toString();
		if (str.length() < 2)
			return null;
		return strb.toString().replace('.', '/');
	}

	public static final Method findMethodByName(Class<?> cls, String name)
	{
		Method m[] = cls.getDeclaredMethods();
		for (int i = 0; i < m.length; i++)
		{
			if (m[i].getName().equals(name))
				return m[i];
		}
		return null;
	}

	public static final void invoke(Method m, Object... args)
	{
		try
		{
			m.invoke(null, args);
		} catch (Exception e)
		{
			if (Log.isError())
				Log.error("%s", Log.trace(e));
		}
	}

	public static final byte[] serialObject(Object o)
	{
		try
		{
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			ObjectOutputStream oos = new ObjectOutputStream(baos);
			oos.writeObject(o);
			return baos.toByteArray();
		} catch (Exception e)
		{
			if (Log.isError())
				Log.error("%s", Log.trace(e));
			return null;
		}
	}

	@SuppressWarnings("unchecked")
	public static final <T> T unSerialObject(byte by[])
	{
		try
		{
			ByteArrayInputStream bais = new ByteArrayInputStream(by);
			ObjectInputStream ois = new ObjectInputStream(bais);
			return (T) ois.readObject();
		} catch (Exception e)
		{
			if (Log.isError())
				Log.error("%s", Log.trace(e));
			return null;
		}
	}

	public static final String getStackInfo()
	{
		StackTraceElement[] stacks = new Throwable().getStackTrace();
		StringBuilder strb = new StringBuilder();
		for (int i = 1; i < stacks.length; i++)
			strb.append(stacks[i].toString()).append(Misc.LINE);
		return strb.toString();
	}

	public static final String getCurrentMethod()
	{
		return new Throwable().getStackTrace()[1].getMethodName();
	}

	public static final <T extends Message.Builder> T json2pb(String json, T b)
	{
		try
		{
			JsonFormat.parser().merge(json, b);
			return b;
		} catch (Exception e)
		{
			if (Log.isDebug())
				Log.debug("json: %s, e: %s", json, Log.trace(e));
			return null;
		}
	}

	public static final String pb2str(MessageOrBuilder msg)
	{
		try
		{
			return JsonFormat.printer().includingDefaultValueFields().omittingInsignificantWhitespace().print(msg);
		} catch (Exception e)
		{
			if (Log.isDebug())
				Log.debug("msg: %s, e: %s", msg, Log.trace(e));
			return null;
		}
	}

	public static final void printPb(Message msg)
	{
		System.out.println(Misc.pb2str(msg));
	}

	@SuppressWarnings("unchecked")
	public static final <T> T get(Object o)
	{
		return (T) o;
	}

	public static final <T> List<T> json2list(String json, Class<T> cls)
	{
		try
		{
			LinkedList<T> arr = new LinkedList<T>();
			JsonArray array = new JsonParser().parse(json).getAsJsonArray();
			array.forEach(v -> arr.add(Misc.gson.fromJson(v, cls)));
			return arr;
		} catch (Exception e)
		{
			if (Log.isError())
				Log.error("%s", Log.trace(e));
			return null;
		}
	}

	public static final <T> T json2obj(String json, Class<T> t)
	{
		try
		{
			return Misc.gson.fromJson(json, t);
		} catch (Exception e)
		{
			if (Log.isDebug())
				Log.debug("%s", Log.trace(e));
			return null;
		}
	}

	public static final String obj2json(Object o)
	{
		try
		{
			return Misc.gson.toJson(o);
		} catch (Exception e)
		{
			if (Log.isDebug())
				Log.debug("%s", Log.trace(e));
			return null;
		}
	}

	public static final void setEnv(String key, Object val)
	{
		System.setProperty(key, val == null ? "" : val.toString());
	}

	public static final String getEnvStr(String key, String def)
	{
		String str = Misc.trim(System.getProperty(key));
		if (str == null)
		{
			str = Misc.trim(System.getenv(key));
			return str == null ? def : str;
		}
		return str;
	}

	public static final int getEnvInt(String key, int def)
	{
		String val = Misc.getEnvStr(key, null);
		return val == null ? def : Net.hexOrInt(val) == null ? 0 : Net.hexOrInt(val);
	}

	public static final String getSetEnvStr(String key, String def)
	{
		String val = Misc.getEnvStr(key, def);
		Misc.setEnv(key, val);
		return val;
	}

	public static final int getSetEnvInt(String key, int def)
	{
		Integer val = Misc.getEnvInt(key, def);
		Misc.setEnv(key, val);
		return val;
	}

	public static final LinkedList<Map.Entry<Object, Object>> getEnvs()
	{
		LinkedList<Map.Entry<Object, Object>> arr = new LinkedList<Map.Entry<Object, Object>>();
		System.getProperties().entrySet().forEach(o -> arr.add(o));
		arr.sort(new Comparator<Map.Entry<Object, Object>>()
		{
			public int compare(Entry<Object, Object> o1, Entry<Object, Object> o2)
			{
				return o1.getKey().toString().compareTo(o2.getKey().toString());
			}
		});
		return arr;
	}

	public static final String sysEnvs()
	{
		LinkedList<Map.Entry<Object, Object>> arr = Misc.getEnvs();
		StringBuilder strb = new StringBuilder();
		arr.forEach(v -> strb.append(v.getKey()).append("=").append(v.getValue()).append("\n"));
		return strb.toString();
	}

	public static final StringBuilder printEnvs()
	{
		LinkedList<Map.Entry<Object, Object>> envs = Misc.getEnvs();
		StringBuilder strb = new StringBuilder();
		envs.forEach((e) -> strb.append(e.getKey()).append("=").append(e.getValue()).append(Misc.LINE));
		return strb;
	}

	public static final boolean isWindowsPlatform()
	{
		return File.separatorChar == '\\';
	}

	public static final String getPwd()
	{
		return System.getProperty("user.dir");
	}

	public static final void lazySystemExit()
	{
		Misc.sleep(5000);
		if (Log.isInfo())
			Log.error("we will exit, stack: %s", Misc.getStackInfo());
		System.exit(1);
	}

	public static final long elap(long sts)
	{
		return (System.nanoTime() - sts) / 1000_000;
	}

	public static final long elap(long sts0, long sts1)
	{
		return (sts1 - sts0) / 1000_000;
	}

	public static final String getPwd(Class<?> webcls )
	{
		try
		{
			String pkg = webcls.getPackage().getName().replace('.', '/');
			Enumeration<URL> dirs = Thread.currentThread().getContextClassLoader().getResources(pkg);
			if (!dirs.hasMoreElements())
			{
				Log.fault("can not got web-service pwd.");
				Misc.lazySystemExit();
				return null;
			}
			String path = URLDecoder.decode(dirs.nextElement().getFile(), "UTF-8");
			return path.substring(0, path.indexOf("classes/" + pkg));
		} catch (Exception e)
		{
			if (Log.isError())
				Log.error("can not got web-service pwd.\n%s", Log.trace(e));
			Misc.lazySystemExit();
			return null;
		}
	}

	public static final <T> boolean exeConsumer(Consumer<T> c, T t)
	{
		try
		{
			c.accept(t);
			return true;
		} catch (Exception e)
		{
			if (Log.isWarn())
				Log.warn("%s", Log.trace(new Throwable()));
			if (Log.isWarn())
				Log.warn("t: %s, e: %s", t, Log.trace(e));
			return false;
		}
	}

	public static final <K, V> boolean exeBiConsumer(BiConsumer<? super K, ? super V> c, K k, V v)
	{
		try
		{
			c.accept(k, v);
			return true;
		} catch (Exception e)
		{
			if (Log.isWarn())
				Log.warn("%s", Log.trace(new Throwable()));
			if (Log.isWarn())
				Log.warn("k: %s, v: %s, e: %s", k, v, Log.trace(e));
			return false;
		}
	}

	public static final <A, B, C> boolean exeTripletConsumer(TripletConsumer<A, B, C> cb, A a, B b, C c)
	{
		try
		{
			cb.accept(a, b, c);
			return true;
		} catch (Exception e)
		{
			if (Log.isWarn())
				Log.warn("%s", Log.trace(new Throwable()));
			if (Log.isWarn())
				Log.warn("a: %s, b: %s, c: %s, e: %s", a, b, c, Log.trace(e));
			return false;
		}
	}

	public static final <T, R> R exeFunction(Function<T, R> f, T t)
	{
		try
		{
			return f.apply(t);
		} catch (Exception e)
		{
			if (Log.isWarn())
				Log.warn("%s", Log.trace(e));
			return null;
		}
	}

	public static final void donothing()
	{

	}

	public static interface TripletConsumer<A, B, C>
	{
		void accept(A a, B b, C c);
	}

	public static final void future(Consumer<Void> cb)
	{
		new Thread(() ->
		{
			cb.accept(null);
		}).start();
	}
}
