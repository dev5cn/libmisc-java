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
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

public final class Net
{
	public static final short byte2short(byte by[], int ofst)
	{
		short value = 0;
		value |= (by[ofst + 0] << 0x08) & 0xFF00;
		value |= by[ofst + 1] & 0x00FF;
		return value;
	}

	public static final int byte2int(byte by[], int ofst)
	{
		int value = 0;
		value |= (by[ofst + 0] << 0x18) & 0xFF000000;
		value |= (by[ofst + 1] << 0x10) & 0x00FF0000;
		value |= (by[ofst + 2] << 0x08) & 0x0000FF00;
		value |= by[ofst + 3] & 0x000000FF;
		return value;
	}

	public static final int byte32int(byte by[], int ofst)
	{
		int value = 0;
		value |= (by[ofst + 0] << 0x10) & 0x00FF0000;
		value |= (by[ofst + 1] << 0x08) & 0x0000FF00;
		value |= by[ofst + 2] & 0x000000FF;
		return value;
	}

	public static final int byte2int(byte by)
	{
		return ((int) by) & 0x00FF;
	}

	public static final long byte2long(byte by[], int ofst)
	{
		long value = 0L;
		value |= ((long) by[ofst + 0] << 0x38) & 0xFF00000000000000L;
		value |= ((long) by[ofst + 1] << 0x30) & 0x00FF000000000000L;
		value |= ((long) by[ofst + 2] << 0x28) & 0x0000FF0000000000L;
		value |= ((long) by[ofst + 3] << 0x20) & 0x000000FF00000000L;
		value |= ((long) by[ofst + 4] << 0x18) & 0x00000000FF000000L;
		value |= ((long) by[ofst + 5] << 0x10) & 0x0000000000FF0000L;
		value |= ((long) by[ofst + 6] << 0x08) & 0x000000000000FF00L;
		value |= ((long) by[ofst + 7]) & 0x00000000000000FFL;
		return value;
	}

	public static final byte[] short2byte(short arg)
	{
		return new byte[] { (byte) ((arg >> 8) & 0xFF), (byte) (arg & 0xFF) };
	}

	public static final byte[] shortArr2byte(short arr[])
	{
		byte by[] = new byte[arr.length * 2];
		int c = 0;
		for (int i = 0; i < arr.length; ++i)
		{
			c = i * 2;
			by[c + 0] = (byte) ((arr[i] >> 8) & 0xFF);
			by[c + 1] = (byte) (arr[i] & 0xFF);
		}
		return by;
	}

	public static final void shortArr2byte(short arr[], byte[] by, int ofst)
	{
		int c = 0;
		for (int i = 0; i < arr.length; ++i)
		{
			c = i * 2;
			by[ofst + c + 0] = (byte) ((arr[i] >> 8) & 0xFF);
			by[ofst + c + 1] = (byte) (arr[i] & 0xFF);
		}
	}

	public static final short[] byteArr2Short(byte[] by)
	{
		return Net.byteArr2Short(by, 0, by.length);
	}

	public static final short[] byteArr2Short(byte[] by, int ofst, int len)
	{
		short arr[] = new short[by.length / 2];
		int c = 0;
		for (int i = ofst; i < len; i += 2)
			arr[c++] = Net.byte2short(by, i);
		return arr;
	}

	public static final int short2int(short h , short l )
	{
		int x = ((int) h) & 0x0000FFFF;
		x = (x << 16) & 0xFFFF0000;
		return x | (((int) l) & 0x0000FFFF);
	}

	public static final int short2int(short s)
	{
		return ((int) s) & 0x0000FFFF;
	}

	public static final byte[] int2byte(int arg)
	{
		byte by[] = new byte[4];
		by[0] = (byte) (arg >> 0x18);
		by[1] = (byte) (arg >> 0x10);
		by[2] = (byte) (arg >> 0x08);
		by[3] = (byte) (arg >> 0x00);
		return by;
	}

	public static final long int2long(int arg)
	{
		return ((long) arg) & 0x00000000FFFFFFFFL;
	}

	public static final byte[] long2byte(long arg)
	{
		byte by[] = new byte[8];
		by[0] = (byte) (arg >> 0x38);
		by[1] = (byte) (arg >> 0x30);
		by[2] = (byte) (arg >> 0x28);
		by[3] = (byte) (arg >> 0x20);
		by[4] = (byte) (arg >> 0x18);
		by[5] = (byte) (arg >> 0x10);
		by[6] = (byte) (arg >> 0x08);
		by[7] = (byte) (arg >> 0x00);
		return by;
	}

	public static final short byte2short(byte by)
	{
		return (short) (by & 0x00FF);
	}

	public static final long h2l_long(long h)
	{
		long n = 0L;
		n |= ((h << 56) & 0xFF00000000000000L);
		n |= ((h << 40) & 0x00FF000000000000L);
		n |= ((h << 24) & 0x0000FF0000000000L);
		n |= ((h << 8) & 0x000000FF00000000L);
		n |= ((h >> 8) & 0x00000000FF000000L);
		n |= ((h >> 24) & 0x0000000000FF0000L);
		n |= ((h >> 40) & 0x000000000000FF00L);
		n |= ((h >> 56) & 0x00000000000000FFL);
		return n;
	}

	public static final int h2l_int(int h)
	{
		return ((h >> 24) & 0xFF) + ((((h >> 16) & 0xFF) << 8) & 0xFF00) + ((((h >> 8) & 0xFF) << 16) & 0xFF0000) + (((h & 0xFF) << 24) & 0xFF000000);
	}

	public static final short h2l_short(short h)
	{
		return (short) (((h >> 8) & 0xFF) + (((h & 0xFF) << 8) & 0xFF00));
	}

	public static final byte[] charArr2Byte(char[] arr)
	{
		byte by[] = new byte[arr.length];
		for (int i = 0; i < arr.length; ++i)
			by[i] = (byte) arr[i];
		return by;
	}

	public static final byte[] hex2bytes(String hex)
	{
		try
		{
			if (hex == null || hex.length() < 2)
				return null;
			byte by[] = new byte[hex.length() / 2];
			char hx[] = hex.toCharArray();
			int c = 0;
			for (int i = 0; i < hx.length; ++i)
			{
				by[c] = (byte) (((hx[i] < 65 ? hx[i] - 0x30 : (hx[i] < 97 ? hx[i] - 55 : hx[i] - 87)) << 4) & 0xF0);
				++i;
				by[c] |= (byte) ((hx[i] < 65 ? hx[i] - 0x30 : (hx[i] < 97 ? hx[i] - 55 : hx[i] - 87)));
				++c;
			}
			return by;
		} catch (Exception e)
		{
			return null;
		}
	}

	public static final byte[] hex2bytesSpace(String hex)
	{
		try
		{
			return hex == null ? null : Net.hex2bytes(hex.replaceAll(" ", ""));
		} catch (Exception e)
		{
			if (Log.isTrace())
				Log.trace("%s", e);
			return null;
		}
	}

	public static final byte[] hex2bytesSpaceFormatCheck(String hex)
	{
		if (hex == null)
			return null;
		hex = Misc.trim(hex.replaceAll(" ", ""));
		if (hex == null)
			return null;
		if (hex.length() % 2 != 0)
			return null;
		hex = hex.toUpperCase();
		byte by[] = new byte[hex.length() / 2];
		char hx[] = hex.toCharArray();
		int c = 0;
		for (int i = 0; i < hx.length; ++i)
		{
			if (hx[i] < 0x30)
				return null;
			if (hx[i] > 0x39 && hx[i] < 0x41)
				return null;
			if (hx[i] > 0x46)
				return null;
			by[c] = (byte) (((hx[i] < 0x41 ? hx[i] - 0x30 : hx[i] - 0x37) << 4) & 0xF0);
			++i;
			if (hx[i] < 0x30)
				return null;
			if (hx[i] > 0x39 && hx[i] < 0x41)
				return null;
			if (hx[i] > 0x46)
				return null;
			by[c] |= (byte) ((hx[i] < 0x41 ? hx[i] - 0x30 : hx[i] - 0x37));
			++c;
		}
		return by;
	}

	
	public static final short hex2short(String hex)
	{
		char hx[] = hex.toCharArray();
		int ret = 0x00;
		for (int i = 0; i < 4; ++i)
			ret += (hx[i] < 65 ? hx[i] - 0x30 : (hx[i] < 97 ? hx[i] - 55 : hx[i] - 87)) << (3 - i) * 4;
		return (short) (ret & 0xFFFF);
	}

	
	public static final int hex2int(String hex)
	{
		char hx[] = hex.toCharArray();
		long ret = 0x00;
		for (int i = 0; i < 8; ++i)
			ret += (hx[i] < 65 ? hx[i] - 0x30 : (hx[i] < 97 ? hx[i] - 55 : hx[i] - 87)) << (7 - i) * 4;
		return (int) (ret & 0xFFFFFFFF);
	}

	public static final Integer hexOrInt(String val)
	{
		if (val == null)
			return null;
		if (val.indexOf("0x") == 0)
		{
			int len = val.length();
			if (len > 10 || len < 3) 
				return null;
			String x = val.substring(2, val.length());
			len -= 2;
			if (len == 1)
				x = "0000000" + x;
			else if (len == 2)
				x = "000000" + x;
			else if (len == 3)
				x = "00000" + x;
			else if (len == 4)
				x = "0000" + x;
			else if (len == 5)
				x = "000" + x;
			else if (len == 6)
				x = "00" + x;
			else if (len == 7)
				x = "0" + x;
			return Net.hex2int(x);
		}
		try
		{
			return Integer.parseInt(val);
		} catch (Exception e)
		{
			return null;
		}
	}

	public static final String byte2HexStr(byte by[])
	{
		return Net.byte2HexStr(by, 0, by.length);
	}

	public static final String byte2HexStr(byte by[], int ofst, int len)
	{
		if (len < 1)
			return "";
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		PrintStream ps = new PrintStream(bos);
		for (int i = ofst; i < ofst + len; i++)
			ps.printf("%02X", by[i]);
		return bos.toString();
	}

	public static final String byte2HexStrSpace(byte by[])
	{
		return Net.byte2HexStrSpace(by, 0, by.length);
	}

	public static final String byte2HexStrSpace(byte by[], int ofst, int len)
	{
		if (len < 1)
			return "";
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		PrintStream ps = new PrintStream(bos);
		for (int i = 0; i < len - 1; ++i)
			ps.printf("%02X ", by[ofst + i]);
		ps.printf("%02X", by[ofst + (len - 1)]);
		return bos.toString();
	}

	public static final String printBytes(byte by[])
	{
		return Net.printBytes(by, 0, by.length);
	}

	public static final String printBytes(byte by[], int ofst, int length)
	{
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		PrintStream ps = new PrintStream(bos);
		int rows = length / 16;
		int ac = length % 16;
		for (int i = 0; i < rows; ++i)
			ps.printf("%02X %02X %02X %02X %02X %02X %02X %02X %02X %02X %02X %02X %02X %02X %02X %02X %c%c%c%c%c%c%c%c%c%c%c%c%c%c%c%c\n", 
					by[ofst + (16 * i) + 0x00], 
					by[ofst + (16 * i) + 0x01], 
					by[ofst + (16 * i) + 0x02], 
					by[ofst + (16 * i) + 0x03], 
					by[ofst + (16 * i) + 0x04], 
					by[ofst + (16 * i) + 0x05], 
					by[ofst + (16 * i) + 0x06], 
					by[ofst + (16 * i) + 0x07], 
					by[ofst + (16 * i) + 0x08], 
					by[ofst + (16 * i) + 0x09], 
					by[ofst + (16 * i) + 0x0A], 
					by[ofst + (16 * i) + 0x0B], 
					by[ofst + (16 * i) + 0x0C], 
					by[ofst + (16 * i) + 0x0D], 
					by[ofst + (16 * i) + 0x0E], 
					by[ofst + (16 * i) + 0x0F], 
					Net.toc(by[ofst + (16 * i) + 0x00]), 
					Net.toc(by[ofst + (16 * i) + 0x01]), 
					Net.toc(by[ofst + (16 * i) + 0x02]), 
					Net.toc(by[ofst + (16 * i) + 0x03]), 
					Net.toc(by[ofst + (16 * i) + 0x04]), 
					Net.toc(by[ofst + (16 * i) + 0x05]), 
					Net.toc(by[ofst + (16 * i) + 0x06]), 
					Net.toc(by[ofst + (16 * i) + 0x07]), 
					Net.toc(by[ofst + (16 * i) + 0x08]), 
					Net.toc(by[ofst + (16 * i) + 0x09]), 
					Net.toc(by[ofst + (16 * i) + 0x0A]), 
					Net.toc(by[ofst + (16 * i) + 0x0B]), 
					Net.toc(by[ofst + (16 * i) + 0x0C]), 
					Net.toc(by[ofst + (16 * i) + 0x0D]), 
					Net.toc(by[ofst + (16 * i) + 0x0E]), 
					Net.toc(by[ofst + (16 * i) + 0x0F]));
		for (int i = 0; i < ac; i++)
			ps.printf("%02X ", by[ofst + rows * 16 + i]);
		for (int i = 0; ac > 0 && i < 16 - ac; i++)
			ps.printf("%s", "   ");
		for (int i = 0; i < ac; i++)
			ps.printf("%c", Net.toc(by[ofst + rows * 16 + i]));
		return bos.toString();
	}

	public static final String short2HexStrSpace(short arr[])
	{
		return Net.short2HexStrSpace(arr, 0, arr.length);
	}

	public static final String short2HexStrSpace(short arr[], int ofst, int len)
	{
		if (len < 1)
			return "";
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		PrintStream ps = new PrintStream(bos);
		for (int i = 0; i < len - 1; ++i)
			ps.printf("%04X ", arr[ofst + i]);
		ps.printf("%04X", arr[ofst + (len - 1)]);
		return bos.toString();
	}

	public static final byte[] reversal(byte by[])
	{
		for (int i = 0; i < by.length / 2; ++i)
		{
			byte tmp = by[i];
			by[i] = by[by.length - 1 - i];
			by[by.length - 1 - i] = tmp;
		}
		return by;
	}

	public static final String byte2BinStr(byte val)
	{
		String str = Integer.toBinaryString(val);
		int len = str.length();
		if (len >= 8)
			return str;
		char chr[] = new char[8 - len];
		for (int i = 0; i < chr.length; ++i)
			chr[i] = '0';
		return new String(chr) + str;
	}

	public static final String short2BinStr(short val)
	{
		String str = Integer.toBinaryString(val);
		int len = str.length();
		if (len >= 0x10)
			return str;
		char chr[] = new char[0x10 - len];
		for (int i = 0; i < chr.length; ++i)
			chr[i] = '0';
		return new String(chr) + str;
	}

	public static final String int2BinStr(int val)
	{
		String str = Integer.toBinaryString(val);
		int len = str.length();
		if (len >= 0x20)
			return str;
		char chr[] = new char[0x20 - len];
		for (int i = 0; i < chr.length; ++i)
			chr[i] = '0';
		return new String(chr) + str;
	}

	public static final String long2BinStr(long val)
	{
		String str = Long.toBinaryString(val);
		int len = str.length();
		if (len >= 0x40)
			return str;
		char chr[] = new char[0x40 - len];
		for (int i = 0; i < chr.length; ++i)
			chr[i] = '0';
		return new String(chr) + str;
	}

	public static final long byte2ip(byte addr[])
	{
		if (addr == null || addr.length != 0x04)
		{
			Log.error("ipv6?");
			return 0;
		}
		return ((addr[0] << 24) & 0xFF000000L) | ((addr[1] << 16) & 0x00FF0000L) | ((addr[2] << 8) & 0x0000FF00L) | (addr[3] & 0x000000FFL);
	}

	public static final String ip2str(long ip)
	{
		return ((ip >> 24) & 0xFF) + "." + ((ip >> 16) & 0xFF) + "." + ((ip >> 8) & 0xFF) + "." + (ip & 0xFF);
	}

	public static final long str2ip(String ip)
	{
		String arr[] = ip.split("\\.");
		return ((Integer.parseInt(arr[0]) << 24) & 0xFF000000L) | ((Integer.parseInt(arr[1]) << 16) & 0x00FF0000L) | ((Integer.parseInt(arr[2]) << 8) & 0x0000FF00L) | (Integer.parseInt(arr[3]) & 0x000000FFL);
	}

	public static final byte[] str2bcd(String str)
	{
		try
		{
			if (!Misc.isDigital(str))
				return null;
			byte by[] = str.getBytes("ISO-8859-1");
			int k = by.length / 2;
			byte ret[] = new byte[(by.length % 2) + k];
			for (int i = 0; i < k; ++i)
				ret[i] = (byte) ((by[i * 2] & 0x0F) + (((by[i * 2 + 1] & 0x0F) << 4) & 0xF0));
			if (by.length % 2 == 1)
				ret[ret.length - 1] = (byte) ((by[by.length - 1] & 0x0F) + 0xF0);
			return ret;
		} catch (Exception e)
		{
			return null;
		}
	}

	public static final long bcd2long(byte by[])
	{
		if (by.length > 9) 
			return 0L;
		return Misc.forceLongO(Net.byte2HexStr(Net.reversal(by)));
	}

	public static final void readAndDiscard(SocketChannel sc, ByteBuffer bb)
	{
		try
		{
			while (true)
			{
				bb.position(0);
				if (sc.read(bb) < 1)
					break;
			}
		} catch (Exception e)
		{
			if (Log.isDebug())
				Log.debug("%s,", Log.trace(e));
		}
	}

	public static final String getAddr(InetSocketAddress addr)
	{
		return addr.getAddress().getHostAddress() + ":" + addr.getPort();
	}

	public static final String getRemoteAddr(SocketChannel sc)
	{
		try
		{
			return Net.getAddr((InetSocketAddress) sc.getRemoteAddress());
		} catch (Exception e)
		{
			return "exception";
		}
	}

	public static final InetSocketAddress getAddr(String host, int port)
	{
		return new InetSocketAddress(host, port);
	}

	public static final InetSocketAddress getAddr(String addr)
	{
		try
		{
			return new InetSocketAddress(addr.split(":")[0], Misc.forceInt0(addr.split(":")[1]));
		} catch (Exception e)
		{
			return null;
		}
	}

	public static final boolean send(DataOutputStream dos, byte by[])
	{
		try
		{
			dos.write(by);
			return true;
		} catch (IOException e)
		{
			if (Log.isDebug())
				Log.debug("%s,", Log.trace(e));
			return false;
		}
	}

	public static final void closeSocketChannel(SocketChannel sc)
	{
		try
		{
			if (sc != null)
				sc.close();
		} catch (Exception e)
		{
			if (Log.isError())
				Log.error("%s", Log.trace(e));
		}
	}

	public static final void closeSocket(Socket s)
	{
		if (s != null)
		{
			try
			{
				s.close();
			} catch (Exception e)
			{
				if (Log.isError())
					Log.error("%s", Log.trace(e));
			}
			s = null;
		}
	}

	public static final void closeSocket(DataInputStream dis, DataOutputStream dos, Socket sock)
	{
		Misc.closeInputStream(dis);
		Misc.closeOutputStream(dos);
		Net.closeSocket(sock);
	}

	public static final String urlEncode(String str)
	{
		try
		{
			return URLEncoder.encode(Misc.trim(str), Misc.UTF_8);
		} catch (Exception e)
		{
			return str;
		}
	}

	public static final String urlDecode(String str)
	{
		try
		{
			return URLDecoder.decode(Misc.trim(str), Misc.UTF_8);
		} catch (Exception e)
		{
			return str;
		}
	}

	public static byte[] readAll(InputStream inputStream) throws IOException
	{
		try
		{
			ByteArrayOutputStream bos = new ByteArrayOutputStream();
			byte by[] = new byte[0x2000];
			int len = -1;
			while ((len = inputStream.read(by)) != -1)
				bos.write(by, 0, len);
			return bos.toByteArray();
		} catch (Exception e)
		{
			if (Log.isDebug())
				Log.debug("%s", Log.trace(e));
			return null;
		}
	}

	private static final char toc(byte chr)
	{
		return (chr > 0x20 && chr < 0x7F) ? (char) chr : '.';
	}
}
