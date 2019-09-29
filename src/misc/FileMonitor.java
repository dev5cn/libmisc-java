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

import java.io.File;
import java.util.HashMap;
import java.util.function.Consumer;

public class FileMonitor
{
	public static final int FND = 0x00;
	public static final int MOD = 0x01;
	public static final int MOV = 0x02;
	private static final long lazy = 5 * DateMisc.SECOND;
	private static long lts = 0L;
	private static long interval = 0L;
	private static final HashMap<String , FileAtt> filelts = new HashMap<>();
	private static final HashMap<String , Consumer<FileChange>> files = new HashMap<>();

	public static final void addFile(String file, Consumer<FileChange> cb)
	{
		File f = new File(file);
		if (!f.exists()) 
			return;
		long now = System.currentTimeMillis();
		if (f.isDirectory())
		{
			File files[] = f.listFiles();
			for (int i = 0; i < files.length; ++i)
			{
				FileAtt att = new FileAtt(files[i].lastModified(), now, files[i].length());
				att.fnd = true;
				FileMonitor.filelts.put(files[i].getPath(), att);
			}
		} else
		{
			FileAtt att = new FileAtt(f.lastModified(), now, f.length());
			att.fnd = true;
			FileMonitor.filelts.put(f.getPath(), att);
		}
		FileMonitor.files.put(file, cb);
	}

	public static final void quartz(long now)
	{
		if (now - FileMonitor.lts <= FileMonitor.interval)
			return;
		FileMonitor.lts = now;
		FileMonitor.files.forEach((k, v) ->
		{
			File file = new File(k);
			if (!file.exists()) 
			{
				FileMonitor.filelts.remove(k);
				Misc.exeConsumer(v, new FileChange(file, FileMonitor.MOV));
				return;
			}
			if (file.isFile())
			{
				FileMonitor.monitorFile(now, k, file, v);
				return;
			}
			if (file.isDirectory())
			{
				FileMonitor.monitorDir(now, file, v);
				return;
			}
		});
	}

	public static final void setInterval(int sec)
	{
		FileMonitor.interval = sec * DateMisc.SECOND;
		FileMonitor.interval = FileMonitor.interval < DateMisc.SECOND ? DateMisc.SECOND : FileMonitor.interval;
	}

	public static final boolean haveFile()
	{
		return FileMonitor.files.size() > 0;
	}

	private static final void monitorFile(long now, String name, File file, Consumer<FileChange> cb)
	{
		FileAtt att = FileMonitor.filelts.get(name);
		if (att == null) 
		{
			att = new FileAtt(file.lastModified(), now, file.length());
			FileMonitor.filelts.put(name, att);
			return; 
		}
		if (att.size != file.length()) 
		{
			att.lcts = now;
			att.size = file.length();
			return;
		}
		if (now - att.lcts <= FileMonitor.lazy) 
			return;
		if (att.lmts != file.lastModified()) 
		{
			att.lmts = file.lastModified();
			Misc.exeConsumer(cb, new FileChange(file, att.fnd ? FileMonitor.MOD : FileMonitor.FND));
			att.fnd = true;
			return;
		}
		if (!att.fnd) 
		{
			att.fnd = true;
			Misc.exeConsumer(cb, new FileChange(file, FileMonitor.FND));
		}
	}

	private static final void monitorDir(long now, File dir, Consumer<FileChange> cb)
	{
		File files[] = dir.listFiles();
		for (int i = 0; i < files.length; ++i)
			FileMonitor.monitorFile(now, files[i].getPath(), files[i], cb);
	}

	public static class FileChange
	{
		public File file;
		public int ret;

		public FileChange(File file, int ret)
		{
			this.file = file;
			this.ret = ret;
		}
	}

	private static class FileAtt
	{
		public long lmts;
		public long lcts;
		public long size;
		public boolean fnd;

		public FileAtt(long lmts, long lcts, long size)
		{
			this.lmts = lmts;
			this.lcts = lcts;
			this.size = size;
			this.fnd = false;
		}
	}
}
