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

import java.util.Date;
import java.util.Enumeration;

import org.apache.log4j.Layout;
import org.apache.log4j.Level;
import org.apache.log4j.spi.LoggingEvent;

public class Log4jAdapter extends Layout
{
	public void activateOptions()
	{

	}

	public String format(LoggingEvent event)
	{
		String cls = event.getLocationInformation().getClassName();
		Enumeration<String> keys = Log.ignores.keys();
		while (keys.hasMoreElements())
		{
			String key = keys.nextElement();
			if (cls.startsWith(key))
				return null;
		}
		byte lev = 0;
		StringBuilder strb = new StringBuilder();
		strb.append(DateMisc.to_hh_mm_ss_ms(new Date(event.getTimeStamp())));
		if (Level.ALL.toString().equals(event.getLevel().toString()))
		{
			lev = Log.RECORD;
			strb.append("[RECO]");
		}
		if (Level.TRACE.toString().equals(event.getLevel().toString()))
		{
			lev = Log.TRACE;
			strb.append("[TRAC]");
		}
		if (Level.DEBUG.toString().equals(event.getLevel().toString()))
		{
			lev = Log.DEBUG;
			strb.append("[DEBU]");
		}
		if (Level.INFO.toString().equals(event.getLevel().toString()))
		{
			lev = Log.INFO;
			strb.append("[INFO]");
		}
		if (Level.WARN.toString().equals(event.getLevel().toString()))
		{
			lev = Log.WARN;
			strb.append("[WARN]");
		}
		if (Level.ERROR.toString().equals(event.getLevel().toString()))
		{
			lev = Log.ERROR;
			strb.append("[ERRO]");
		}
		strb.append(Misc.forceInt0(event.getThreadName().substring(event.getThreadName().lastIndexOf("-") + 1, event.getThreadName().length())));
		strb.append("(").append(cls).append(" ").
				append(event.getLocationInformation().getMethodName()).append(" ").
				append(event.getLocationInformation().getLineNumber()).
				append(") ");
		strb.append(event.getMessage());
		Log.log4log4j(lev, strb.toString());
		return null;
	}

	public boolean ignoresThrowable()
	{
		return false;
	}
}
