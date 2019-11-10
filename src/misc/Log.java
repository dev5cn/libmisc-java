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

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.PrintStream;
import java.io.RandomAccessFile;
import java.util.Date;
import java.util.Enumeration;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.BiConsumer;

import org.apache.log4j.PropertyConfigurator;

public class Log
{
	public static final byte OUTPUT_STDOUT = 1 << 0;
	public static final byte OUTPUT_FILE = 1 << 1;
	public static final byte RECORD = 0x00, TRACE = 0x01, DEBUG = 0x02, INFO = 0x03, WARN = 0x04, ERROR = 0x05, FAULT = 0x06, OPER = 0x07;
	private static byte level = DEBUG;
	private static boolean single = false;
	private static boolean enableSlf4j = false;
	private static byte output = Log.OUTPUT_STDOUT;
	private static String path = "./";
	private static BiConsumer<Byte, String> cb = null;
	public static final ConcurrentHashMap<String, String> ignores = new ConcurrentHashMap<String, String>();
	private static Properties pro4log4j = new Properties();

	static
	{
		try
		{
			Log.setLog4j();
		} catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	public static final void init()
	{
		Log.init(null, Log.OUTPUT_STDOUT);
	}

	public static final void init(String path, int o)
	{
		Log.path = path == null ? Log.path : path;
		Log.output = (byte) o;
	}

	public static final void setLevel(String lev)
	{
		if ("RECORD".equals(lev))
			Log.setRecord();
		if ("TRACE".equals(lev))
			Log.setTrace();
		if ("DEBUG".equals(lev))
			Log.setDebug();
		if ("INFO".equals(lev))
			Log.setInfo();
		if ("WARN".equals(lev))
			Log.setWarn();
		if ("ERROR".equals(lev))
			Log.setError();
		if ("FAULT".equals(lev))
			Log.setFault();
		if ("OPER".equals(lev))
			Log.setOper();
	}

	public static final String getLevel()
	{
		switch (Log.level)
		{
		case Log.RECORD:
			return "RECORD";
		case Log.TRACE:
			return "TRACE";
		case Log.DEBUG:
			return "DEBUG";
		case Log.INFO:
			return "INFO";
		case Log.WARN:
			return "WARN";
		case Log.ERROR:
			return "ERROR";
		case Log.FAULT:
			return "FAULT";
		case Log.OPER:
			return "OPER";
		default:
			return "NONE";
		}
	}

	public static final void single(boolean s)
	{
		Log.single = s;
	}

	public static final void slf4j(boolean enable)
	{
		Log.enableSlf4j = enable;
	}

	public static final void setRecord()
	{
		Log.level = Log.RECORD;
		Log.setLog4j();
	}

	public static final void setTrace()
	{
		Log.level = Log.TRACE;
		Log.setLog4j();
	}

	public static final void setDebug()
	{
		Log.level = Log.DEBUG;
		Log.setLog4j();
	}

	public static final void setInfo()
	{
		Log.level = Log.INFO;
		Log.setLog4j();
	}

	public static final void setWarn()
	{
		Log.level = Log.WARN;
		Log.setLog4j();
	}

	public static final void setError()
	{
		Log.level = Log.ERROR;
		Log.setLog4j();
	}

	public static final void setFault()
	{
		Log.level = Log.FAULT;
		Log.setLog4j();
	}

	public static final void setOper()
	{
		Log.level = Log.OPER;
		Log.setLog4j();
	}

	public static final boolean isRecord()
	{
		return Log.single ? (Log.level == Log.RECORD) : Log.level <= Log.RECORD;
	}

	public static final boolean isTrace()
	{
		return Log.single ? (Log.level == Log.TRACE) : Log.level <= Log.TRACE;
	}

	public static final boolean isDebug()
	{
		return Log.single ? (Log.level == Log.DEBUG) : Log.level <= Log.DEBUG;
	}

	public static final boolean isInfo()
	{
		return Log.single ? (Log.level == Log.INFO) : Log.level <= Log.INFO;
	}

	public static final boolean isWarn()
	{
		return Log.single ? (Log.level == Log.WARN) : Log.level <= Log.WARN;
	}

	public static final boolean isError()
	{
		return Log.single ? (Log.level == Log.ERROR) : Log.level <= Log.ERROR;
	}

	public static final boolean isFault()
	{
		return Log.single ? (Log.level == Log.FAULT) : Log.level <= Log.FAULT;
	}

	public static final boolean isOper()
	{
		return Log.single ? (Log.level == Log.OPER) : Log.level <= Log.OPER;
	}

	public static final void record(String format, Object... args)
	{
		if (Log.single)
		{
			if (Log.level == Log.RECORD)
				Log.log3("RECO", Log.RECORD, format, args);
			return;
		}
		if (Log.level <= Log.RECORD)
			Log.log3("RECO", Log.RECORD, format, args);
	}

	public static final void trace(String format, Object... args)
	{
		if (Log.single)
		{
			if (Log.level == Log.TRACE)
				Log.log3("TRAC", Log.TRACE, format, args);
			return;
		}
		if (Log.level <= Log.TRACE)
			Log.log3("TRAC", Log.TRACE, format, args);
	}

	public static final void traceSlf4j(String format, Object... args)
	{
		if (Log.single)
		{
			if (Log.level == Log.TRACE)
				Log.log5("TRAC", Log.TRACE, format, args);
			return;
		}
		if (Log.level <= Log.TRACE)
			Log.log5("TRAC", Log.TRACE, format, args);
	}

	public static final void debug(String format, Object... args)
	{
		if (Log.single)
		{
			if (Log.level == Log.DEBUG)
				Log.log3("DEBU", Log.DEBUG, format, args);
			return;
		}
		if (Log.level <= Log.DEBUG)
			Log.log3("DEBU", Log.DEBUG, format, args);
	}

	public static final void debugSlf4j(String format, Object... args)
	{
		if (Log.single)
		{
			if (Log.level == Log.DEBUG)
				Log.log5("DEBU", Log.DEBUG, format, args);
			return;
		}
		if (Log.level <= Log.DEBUG)
			Log.log5("DEBU", Log.DEBUG, format, args);
	}

	public static final void info(String format, Object... args)
	{
		if (Log.single)
		{
			if (Log.level == Log.INFO)
				Log.log3("INFO", Log.INFO, format, args);
			return;
		}
		if (Log.level <= Log.INFO)
			Log.log3("INFO", Log.INFO, format, args);
	}

	public static final void infoSlf4j(String format, Object... args)
	{
		if (Log.single)
		{
			if (Log.level == Log.INFO)
				Log.log5("INFO", Log.INFO, format, args);
			return;
		}
		if (Log.level <= Log.INFO)
			Log.log5("INFO", Log.INFO, format, args);
	}

	public static final void warn(String format, Object... args)
	{
		if (Log.single)
		{
			if (Log.level == Log.WARN)
				Log.log3("WARN", Log.WARN, format, args);
			return;
		}
		if (Log.level <= Log.WARN)
			Log.log3("WARN", Log.WARN, format, args);
	}

	public static final void warnSlf4j(String format, Object... args)
	{
		if (Log.single)
		{
			if (Log.level == Log.WARN)
				Log.log5("WARN", Log.WARN, format, args);
			return;
		}
		if (Log.level <= Log.WARN)
			Log.log5("WARN", Log.WARN, format, args);
	}

	public static final void error(String format, Object... args)
	{
		if (Log.single)
		{
			if (Log.level == Log.ERROR)
				Log.log3("ERRO", Log.ERROR, format, args);
			return;
		}
		if (Log.level <= Log.ERROR)
			Log.log3("ERRO", Log.ERROR, format, args);
	}

	public static final void errorSlf4j(String format, Object... args)
	{
		if (Log.single)
		{
			if (Log.level == Log.ERROR)
				Log.log5("ERRO", Log.ERROR, format, args);
			return;
		}
		if (Log.level <= Log.ERROR)
			Log.log5("ERRO", Log.ERROR, format, args);
	}

	public static final void fault(String format, Object... args)
	{
		if (Log.single)
		{
			if (Log.level == Log.FAULT)
				Log.log3("FAUL", Log.FAULT, format, args);
			return;
		}
		if (Log.level <= Log.FAULT)
			Log.log3("FAUL", Log.FAULT, format, args);
	}

	public static final void oper(String format, Object... args)
	{
		if (Log.single)
		{
			if (Log.level == Log.OPER)
				Log.log3("OPER", Log.OPER, format, args);
			return;
		}
		Log.log3("OPER", Log.OPER, format, args);
	}

	public static final void setOutput(int output)
	{
		Log.output = (byte) output;
	}

	public static final void stream(BiConsumer<Byte, String> cb)
	{
		Log.cb = cb;
	}

	public static final void pub2stream(byte lev, String str)
	{
		if (Log.cb != null)
			Misc.exeBiConsumer(Log.cb, lev, str);
	}

	public static final void ignoreClass(String cls)
	{
		Log.ignores.put(cls, "");
	}

	public static final void log4log4j(byte lev, String str)
	{
		if (Log.cb != null)
			Misc.exeBiConsumer(Log.cb, lev, str);
		if (Log.isOutPutFile())
			Log.write2File(str);
		if (Log.isOutPutStdout())
		{
			if (lev < Log.WARN)
				System.out.print(str);
			else
				System.err.print(str);
		}
	}

	private static final void setLog4j()
	{
		String tmp = null;
		if (Log.level == Log.RECORD)
			tmp = "ALL";
		else if (Log.level == Log.TRACE)
			tmp = "TRACE";
		else if (Log.level == Log.DEBUG)
			tmp = "INFO";
		else if (Log.level == Log.INFO)
			tmp = "INFO";
		else if (Log.level == Log.WARN)
			tmp = "WARN";
		else
			tmp = "ERROR";
		Log.pro4log4j.setProperty("log4j.rootLogger", Misc.printf2str("%s,D", tmp));
		Log.pro4log4j.setProperty("log4j.appender.D", "org.apache.log4j.ConsoleAppender");
		Log.pro4log4j.setProperty("log4j.appender.D.layout", "misc.Log4jAdapter  ");
		PropertyConfigurator.configure(Log.pro4log4j);
	}

	private static final void log3(String level, byte lev, String format, Object... args)
	{
		Log.log(3, level, lev, format, args);
	}

	private static final void log5(String level, byte lev, String format, Object... args)
	{
		if (Log.enableSlf4j)
			Log.log(5, level, lev, format, args);
	}

	private static final void log(int stack, String level, byte lev, String format, Object... args)
	{
		StackTraceElement[] stacks = new Throwable().getStackTrace();
		String cls = stacks[stack].getClassName();
		Enumeration<String> keys = Log.ignores.keys();
		while (keys.hasMoreElements())
		{
			String key = keys.nextElement();
			if (cls.startsWith(key))
				return;
		}
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		Date now = new Date();
		StringBuilder prefix = new StringBuilder();
		prefix.append(DateMisc.to_hh_mm_ss_ms(now))
				.append("[").append(level).append("]")
				.append(Thread.currentThread().getId())
				.append("(").append(cls).append(" ")
				.append(stacks[stack].getMethodName())
				.append(" ")
				.append(stacks[stack].getLineNumber())
				.append(") ");
		PrintStream ps = new PrintStream(bos);
		ps.printf("%s", prefix);
		ps.printf(format, args);
		ps.printf("\n");
		String str = bos.toString();
		if (Log.cb != null)
			Misc.exeBiConsumer(Log.cb, lev, str);
		if (Log.isOutPutFile())
			Log.write2File(str);
		if (Log.isOutPutStdout())
		{
			if (lev < Log.WARN)
				System.out.print(str);
			else
				System.err.print(str);
		}
	}

	private static final void write2File(String str)
	{
		RandomAccessFile raf = null;
		try
		{
			raf = new RandomAccessFile(new File(Log.path + DateMisc.to_yyyy_mm_dd(new Date()) + ".log"), "rw");
			raf.seek(raf.length());
			raf.write(str.getBytes());
		} catch (Exception e)
		{
			e.printStackTrace();
		} finally
		{
			Misc.closeRaf(raf);
		}
	}

	public static final String trace(Throwable e)
	{
		StringBuilder strb = new StringBuilder(0x200);
		StackTraceElement ste[] = e.getStackTrace();
		strb.append(e).append(Misc.LINE);
		for (int i = 0; i < ste.length; i++)
			strb.append(ste[i].toString()).append(Misc.LINE);
		return strb.toString();
	}

	private static final boolean isOutPutStdout()
	{
		return (Log.output & Log.OUTPUT_STDOUT) != 0;
	}

	private static final boolean isOutPutFile()
	{
		return (Log.output & Log.OUTPUT_FILE) != 0;
	}
}
