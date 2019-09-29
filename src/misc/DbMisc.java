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

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Blob;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

public class DbMisc
{
	public static final boolean simpleIUD(Connection conn, String sql) throws Exception
	{
		if (Log.isTrace())
			Log.trace("sql: %s", sql);
		PreparedStatement pst = null;
		try
		{
			pst = conn.prepareStatement(sql);
			pst.executeUpdate();
			return true;
		} catch (Exception e)
		{
			if (Log.isWarn())
				Log.warn("execute sql failed: %s", sql);
			throw e;
		} finally
		{
			DbMisc.closeConn(null, pst, null, null);
		}
	}

	public static final boolean simpleIUD(Connection conn, String sql, LinkedList<Object> field) throws Exception
	{
		return DbMisc.simpleIUD(conn, sql, field.toArray());
	}

	public static final boolean simpleIUD(Connection conn, String sql, Object... fv) throws Exception
	{
		if (Log.isTrace())
			Log.trace("sql: %s", DbMisc.printSql(sql, fv));
		PreparedStatement pst = null;
		try
		{
			pst = conn.prepareStatement(sql);
			DbMisc.dbFieldBind(pst, fv);
			pst.executeUpdate();
			return true;
		} catch (Exception e)
		{
			if (Log.isWarn())
				Log.warn("execute sql failed: %s", DbMisc.printSql(sql, fv));
			throw e;
		} finally
		{
			DbMisc.closeConn(null, pst, null, null);
		}
	}

	public static final boolean batchInsert(Connection conn, String sql, List<Object[]> fvList) throws Exception
	{
		if (Log.isTrace())
			Log.trace("sql: %s, fv: %s", sql, Misc.obj2json(fvList));
		PreparedStatement pst = null;
		try
		{
			conn.setAutoCommit(false);
			pst = conn.prepareStatement(sql);
			for (Object[] fv : fvList)
			{
				DbMisc.dbFieldBind(pst, fv);
				pst.addBatch();
			}
			pst.executeBatch();
			conn.commit();
			return true;
		} catch (Exception e)
		{
			if (Log.isWarn())
				Log.warn("execute sql failed: : %s, fv: %s", sql, Misc.obj2json(fvList));
			throw e;
		} finally
		{
			DbMisc.closeConn(null, pst, null, null);
		}
	}

	public static final int intVal(Connection conn, String sql) throws Exception
	{
		if (Log.isTrace())
			Log.trace("sql: %s", sql);
		PreparedStatement pst = null;
		ResultSet rst = null;
		try
		{
			pst = conn.prepareStatement(sql);
			rst = pst.executeQuery();
			return rst.next() ? rst.getInt(1) : 0;
		} catch (Exception e)
		{
			if (Log.isWarn())
				Log.warn("execute sql failed: %s", sql);
			throw e;
		} finally
		{
			DbMisc.closeConn(null, pst, rst, null);
		}
	}

	public static final int intVal(Connection conn, String sql, Object... fv) throws Exception
	{
		if (Log.isTrace())
			Log.trace("sql: %s", DbMisc.printSql(sql, fv));
		PreparedStatement pst = null;
		ResultSet rst = null;
		try
		{
			pst = conn.prepareStatement(sql);
			DbMisc.dbFieldBind(pst, fv);
			rst = pst.executeQuery();
			return rst.next() ? rst.getInt(1) : 0;
		} catch (Exception e)
		{
			if (Log.isWarn())
				Log.warn("execute sql failed: %s", DbMisc.printSql(sql, fv));
			throw e;
		} finally
		{
			DbMisc.closeConn(null, pst, rst, null);
		}
	}

	public static final Integer integerVal(Connection conn, String sql) throws Exception
	{
		if (Log.isTrace())
			Log.trace("sql: %s", sql);
		PreparedStatement pst = null;
		ResultSet rst = null;
		try
		{
			pst = conn.prepareStatement(sql);
			rst = pst.executeQuery();
			return rst.next() ? rst.getInt(1) : null;
		} catch (Exception e)
		{
			if (Log.isWarn())
				Log.warn("execute sql failed: %s", sql);
			throw e;
		} finally
		{
			DbMisc.closeConn(null, pst, rst, null);
		}
	}

	public static final Integer integerVal(Connection conn, String sql, Object... fv) throws Exception
	{
		if (Log.isTrace())
			Log.trace("sql: %s", DbMisc.printSql(sql, fv));
		PreparedStatement pst = null;
		ResultSet rst = null;
		try
		{
			pst = conn.prepareStatement(sql);
			DbMisc.dbFieldBind(pst, fv);
			rst = pst.executeQuery();
			return rst.next() ? rst.getInt(1) : null;
		} catch (Exception e)
		{
			if (Log.isWarn())
				Log.warn("execute sql failed: %s", DbMisc.printSql(sql, fv));
			throw e;
		} finally
		{
			DbMisc.closeConn(null, pst, rst, null);
		}
	}

	public static final String strVal(Connection conn, String sql) throws Exception
	{
		if (Log.isTrace())
			Log.trace("sql: %s", sql);
		PreparedStatement pst = null;
		ResultSet rst = null;
		try
		{
			pst = conn.prepareStatement(sql);
			rst = pst.executeQuery();
			return rst.next() ? rst.getString(1) : null;
		} catch (Exception e)
		{
			if (Log.isWarn())
				Log.warn("execute sql failed: %s", sql);
			throw e;
		} finally
		{
			DbMisc.closeConn(null, pst, rst, null);
		}
	}

	public static final String strVal(Connection conn, String sql, Object... fv) throws Exception
	{
		if (Log.isTrace())
			Log.trace("sql: %s", DbMisc.printSql(sql, fv));
		PreparedStatement pst = null;
		ResultSet rst = null;
		try
		{
			pst = conn.prepareStatement(sql);
			DbMisc.dbFieldBind(pst, fv);
			rst = pst.executeQuery();
			return rst.next() ? rst.getString(1) : null;
		} catch (Exception e)
		{
			if (Log.isWarn())
				Log.warn("execute sql failed: %s", DbMisc.printSql(sql, fv));
			throw e;
		} finally
		{
			DbMisc.closeConn(null, pst, rst, null);
		}
	}

	public static final Date dateVal(Connection conn, String sql) throws Exception
	{
		if (Log.isTrace())
			Log.trace("sql: %s", sql);
		PreparedStatement pst = null;
		ResultSet rst = null;
		try
		{
			pst = conn.prepareStatement(sql);
			rst = pst.executeQuery();
			return rst.next() ? rst.getTimestamp(1) : null;
		} catch (Exception e)
		{
			if (Log.isWarn())
				Log.warn("execute sql failed: %s", sql);
			throw e;
		} finally
		{
			DbMisc.closeConn(null, pst, rst, null);
		}
	}

	public static final Date dateVal(Connection conn, String sql, Object... fv) throws Exception
	{
		if (Log.isTrace())
			Log.trace("sql: %s", DbMisc.printSql(sql, fv));
		PreparedStatement pst = null;
		ResultSet rst = null;
		try
		{
			pst = conn.prepareStatement(sql);
			DbMisc.dbFieldBind(pst, fv);
			rst = pst.executeQuery();
			return rst.next() ? rst.getTimestamp(1) : null;
		} catch (Exception e)
		{
			if (Log.isWarn())
				Log.warn("execute sql failed: %s", DbMisc.printSql(sql, fv));
			throw e;
		} finally
		{
			DbMisc.closeConn(null, pst, rst, null);
		}
	}

	public static final byte[] bytesVal(Connection conn, String sql) throws Exception
	{
		if (Log.isTrace())
			Log.trace("sql: %s", sql);
		PreparedStatement pst = null;
		ResultSet rst = null;
		try
		{
			pst = conn.prepareStatement(sql);
			rst = pst.executeQuery();
			return rst.next() ? rst.getBytes(1) : null;
		} catch (Exception e)
		{
			if (Log.isWarn())
				Log.warn("execute sql failed: %s", sql);
			throw e;
		} finally
		{
			DbMisc.closeConn(null, pst, rst, null);
		}
	}

	public static final byte[] bytesVal(Connection conn, String sql, Object... fv) throws Exception
	{
		if (Log.isTrace())
			Log.trace("sql: %s", DbMisc.printSql(sql, fv));
		PreparedStatement pst = null;
		ResultSet rst = null;
		try
		{
			pst = conn.prepareStatement(sql);
			DbMisc.dbFieldBind(pst, fv);
			rst = pst.executeQuery();
			return rst.next() ? rst.getBytes(1) : null;
		} catch (Exception e)
		{
			if (Log.isWarn())
				Log.warn("execute sql failed: %s", DbMisc.printSql(sql, fv));
			throw e;
		} finally
		{
			DbMisc.closeConn(null, pst, rst, null);
		}
	}

	public static final LinkedList<byte[]> bytesArr(Connection conn, String sql) throws Exception
	{
		if (Log.isTrace())
			Log.trace("sql: %s", sql);
		PreparedStatement pst = null;
		ResultSet rst = null;
		try
		{
			pst = conn.prepareStatement(sql);
			rst = pst.executeQuery();
			LinkedList<byte[]> arr = new LinkedList<>();
			while (rst.next())
				arr.add(rst.getBytes(1));
			return arr;
		} catch (Exception e)
		{
			if (Log.isWarn())
				Log.warn("execute sql failed: %s", sql);
			throw e;
		} finally
		{
			DbMisc.closeConn(null, pst, rst, null);
		}
	}

	public static final LinkedList<byte[]> bytesArr(Connection conn, String sql, Object... fv) throws Exception
	{
		if (Log.isTrace())
			Log.trace("sql: %s", DbMisc.printSql(sql, fv));
		PreparedStatement pst = null;
		ResultSet rst = null;
		try
		{
			pst = conn.prepareStatement(sql);
			DbMisc.dbFieldBind(pst, fv);
			rst = pst.executeQuery();
			LinkedList<byte[]> arr = new LinkedList<>();
			while (rst.next())
				arr.add(rst.getBytes(1));
			return arr;
		} catch (Exception e)
		{
			if (Log.isWarn())
				Log.warn("execute sql failed: %s", DbMisc.printSql(sql, fv));
			throw e;
		} finally
		{
			DbMisc.closeConn(null, pst, rst, null);
		}
	}

	public static final LinkedList<String> strArr(Connection conn, String sql) throws Exception
	{
		if (Log.isTrace())
			Log.trace("sql: %s", sql);
		PreparedStatement pst = null;
		ResultSet rst = null;
		try
		{
			pst = conn.prepareStatement(sql);
			rst = pst.executeQuery();
			LinkedList<String> arr = new LinkedList<>();
			while (rst.next())
				arr.add(rst.getString(1));
			return arr.size() < 1 ? null : arr;
		} catch (Exception e)
		{
			if (Log.isWarn())
				Log.warn("execute sql failed: %s", sql);
			throw e;
		} finally
		{
			DbMisc.closeConn(null, pst, rst, null);
		}
	}

	public static final LinkedList<String> strArr(Connection conn, String sql, Object... fv) throws Exception
	{
		if (Log.isTrace())
			Log.trace("sql: %s", DbMisc.printSql(sql, fv));
		PreparedStatement pst = null;
		ResultSet rst = null;
		try
		{
			pst = conn.prepareStatement(sql);
			DbMisc.dbFieldBind(pst, fv);
			rst = pst.executeQuery();
			LinkedList<String> arr = new LinkedList<>();
			while (rst.next())
				arr.add(rst.getString(1));
			return arr.size() < 1 ? null : arr;
		} catch (Exception e)
		{
			if (Log.isWarn())
				Log.warn("execute sql failed: %s", DbMisc.printSql(sql, fv));
			throw e;
		} finally
		{
			DbMisc.closeConn(null, pst, rst, null);
		}
	}

	public static final LinkedList<Integer> intArr(Connection conn, String sql) throws Exception
	{
		if (Log.isTrace())
			Log.trace("sql: %s", sql);
		PreparedStatement pst = null;
		ResultSet rst = null;
		try
		{
			pst = conn.prepareStatement(sql);
			rst = pst.executeQuery();
			LinkedList<Integer> arr = new LinkedList<>();
			while (rst.next())
				arr.add(rst.getInt(1));
			return arr.size() < 1 ? null : arr;
		} catch (Exception e)
		{
			if (Log.isWarn())
				Log.warn("execute sql failed: %s", sql);
			throw e;
		} finally
		{
			DbMisc.closeConn(null, pst, rst, null);
		}
	}

	public static final LinkedList<Integer> intArr(Connection conn, String sql, Object... fv) throws Exception
	{
		if (Log.isTrace())
			Log.trace("sql: %s", DbMisc.printSql(sql, fv));
		PreparedStatement pst = null;
		ResultSet rst = null;
		try
		{
			pst = conn.prepareStatement(sql);
			DbMisc.dbFieldBind(pst, fv);
			rst = pst.executeQuery();
			LinkedList<Integer> arr = new LinkedList<>();
			while (rst.next())
				arr.add(rst.getInt(1));
			return arr.size() < 1 ? null : arr;
		} catch (Exception e)
		{
			if (Log.isWarn())
				Log.warn("execute sql failed: %s", DbMisc.printSql(sql, fv));
			throw e;
		} finally
		{
			DbMisc.closeConn(null, pst, rst, null);
		}
	}

	public static final <T> T obj(Connection conn, Class<T> cls, String sql) throws Exception
	{
		if (Log.isTrace())
			Log.trace("sql: %s", sql);
		PreparedStatement pst = null;
		ResultSet rst = null;
		try
		{
			pst = conn.prepareStatement(sql);
			rst = pst.executeQuery();
			return rst.next() ? DbMisc.takeRow(rst, cls) : null;
		} catch (Exception e)
		{
			if (Log.isWarn())
				Log.warn("execute sql failed: %s", sql);
			throw e;
		} finally
		{
			DbMisc.closeConn(null, pst, rst, null);
		}
	}

	public static final <T> T obj(Connection conn, Class<T> cls, String sql, Object... fv) throws Exception
	{
		if (Log.isTrace())
			Log.trace("sql: %s", DbMisc.printSql(sql, fv));
		PreparedStatement pst = null;
		ResultSet rst = null;
		try
		{
			pst = conn.prepareStatement(sql);
			DbMisc.dbFieldBind(pst, fv);
			rst = pst.executeQuery();
			return rst.next() ? DbMisc.takeRow(rst, cls) : null;
		} catch (Exception e)
		{
			if (Log.isWarn())
				Log.warn("execute sql failed: %s", DbMisc.printSql(sql, fv));
			throw e;
		} finally
		{
			DbMisc.closeConn(null, pst, rst, null);
		}
	}

	public static final <T> LinkedList<T> objArr(Connection conn, Class<T> cls, String sql) throws Exception
	{
		if (Log.isTrace())
			Log.trace("sql: %s", sql);
		PreparedStatement pst = null;
		ResultSet rst = null;
		try
		{
			pst = conn.prepareStatement(sql);
			rst = pst.executeQuery();
			LinkedList<T> arr = new LinkedList<>();
			while (rst.next())
				arr.add(DbMisc.takeRow(rst, cls));
			return arr.size() < 1 ? null : arr;
		} catch (Exception e)
		{
			if (Log.isWarn())
				Log.warn("execute sql failed: %s", sql);
			throw e;
		} finally
		{
			DbMisc.closeConn(null, pst, rst, null);
		}
	}

	public static final <T> LinkedList<T> objArr(Connection conn, Class<T> cls, String sql, Object... fv) throws Exception
	{
		if (Log.isTrace())
			Log.trace("sql: %s", DbMisc.printSql(sql, fv));
		PreparedStatement pst = null;
		ResultSet rst = null;
		try
		{
			pst = conn.prepareStatement(sql);
			DbMisc.dbFieldBind(pst, fv);
			rst = pst.executeQuery();
			LinkedList<T> arr = new LinkedList<>();
			while (rst.next())
				arr.add(DbMisc.takeRow(rst, cls));
			return arr.size() < 1 ? null : arr;
		} catch (Exception e)
		{
			if (Log.isWarn())
				Log.warn("execute sql failed: %s", DbMisc.printSql(sql, fv));
			throw e;
		} finally
		{
			DbMisc.closeConn(null, pst, rst, null);
		}
	}

	public static final String printSql(String sql, Object... fv)
	{
		String arr[] = sql.split("[?]");
		int k = 0;
		StringBuilder strb = new StringBuilder(arr[k]);
		for (int i = 1; i < fv.length + 1; ++i)
		{
			++k;
			Object v = fv[i - 1];
			if (v == null)
				strb.append("null");
			else if ((v instanceof Integer) || 
					(v instanceof Long) || 
					(v instanceof Short) || 
					(v instanceof Byte) || 
					(v instanceof BigDecimal) || 
					(v instanceof Float) || (v instanceof Double))
				strb.append(v.toString());
			else if (v instanceof String)
				strb.append("'").append((String) v).append("'");
			else if (v instanceof Date)
				strb.append("'").append(DateMisc.to_yyyy_mm_dd_hh_mm_ss((Date) v)).append("'");
			else if (v instanceof byte[])
				strb.append("(").append(Net.byte2HexStrSpace((byte[]) v)).append(")");
			else
				Log.fault("it`s a bug, type: %s, stack: %s", v.getClass().getName(), Misc.getStackInfo());
			if (k < arr.length)
				strb.append(arr[k]);
		}
		return strb.toString();
	}

	public static final void dbFieldBind(PreparedStatement pst, Object... fv) throws Exception
	{
		for (int i = 1; i < fv.length + 1; ++i)
		{
			Object v = fv[i - 1];
			if (v == null)
				pst.setObject(i, null);
			else if (v instanceof Integer)
				pst.setInt(i, (Integer) v);
			else if (v instanceof String)
				pst.setString(i, (String) v);
			else if (v instanceof Long)
				pst.setLong(i, (Long) v);
			else if ((v instanceof Short))
				pst.setShort(i, (Short) v);
			else if ((v instanceof Byte))
				pst.setByte(i, (Byte) v);
			else if (v instanceof Float)
				pst.setFloat(i, (Float) v);
			else if (v instanceof BigDecimal)
				pst.setBigDecimal(i, (BigDecimal) v);
			else if (v instanceof Double)
				pst.setDouble(i, (Double) v);
			else if (v instanceof Date)
				pst.setTimestamp(i, new Timestamp(((Date) v).getTime()));
			else if (v instanceof byte[])
				pst.setBlob(i, new ByteArrayInputStream(((byte[]) v)));
			else
				Log.fault("it`s a bug, type: %s, stack: %s", v.getClass().getName(), Misc.getStackInfo());
		}
	}

	private static final <T> T takeRow(ResultSet rst, Class<T> cls) throws Exception
	{
		T o = cls.getDeclaredConstructor().newInstance();
		Field field[] = cls.getDeclaredFields();
		for (Field f : field)
		{
			if (Modifier.isStatic(f.getModifiers())) 
				continue;
			Object ret = rst.getObject(f.getName());
			if (ret == null)
				continue;
			if (ret instanceof byte[])
			{
				if ("java.lang.String".equals(f.getType().getName()))
					f.set(o, new String((byte[]) ret));
				else
					f.set(o, ret); 
				continue;
			}
			if (ret instanceof BigDecimal) 
				f.set(o, ((BigDecimal) ret).longValue());
			else if (ret instanceof BigInteger)
				f.set(o, ((BigInteger) ret).longValue());
			else
				f.set(o, ret);
		}
		return o;
	}

	public static final <T> LinkedList<T> page(LinkedList<T> arr, int page, int pagesize)
	{
		LinkedList<T> tmp = new LinkedList<>();
		for (int i = page * pagesize; i < arr.size() && i < (page + 1) * pagesize; ++i)
			tmp.add(arr.get(i));
		return tmp.size() < 1 ? null : tmp;
	}

	public static final byte[] getBlob(Blob b) throws Exception
	{
		DataInputStream dis = new DataInputStream(b.getBinaryStream());
		byte by[] = new byte[(int) b.length()];
		dis.readFully(by);
		dis.close();
		return by;
	}

	public static final void closeConn(Connection conn, PreparedStatement pst, ResultSet rst, CallableStatement call)
	{
		if (rst != null)
		{
			try
			{
				rst.close();
			} catch (Exception e)
			{
				if (Log.isDebug())
					Log.debug("%s,", Log.trace(e));
			}
		}
		if (pst != null)
		{
			try
			{
				pst.close();
			} catch (Exception e)
			{
				if (Log.isDebug())
					Log.debug("%s,", Log.trace(e));
			}
		}
		if (call != null)
		{
			try
			{
				call.close();
			} catch (Exception e)
			{
				if (Log.isDebug())
					Log.debug("%s,", Log.trace(e));
			}
		}
		if (conn != null)
		{
			try
			{
				conn.close();
			} catch (Exception e)
			{
				if (Log.isDebug())
					Log.debug("%s,", Log.trace(e));
			}
		}
	}

	public static final void closeConn(Connection conn, PreparedStatement pst, ResultSet rst)
	{
		if (rst != null)
		{
			try
			{
				rst.close();
			} catch (Exception e)
			{
				if (Log.isDebug())
					Log.debug("%s,", Log.trace(e));
			}
		}
		if (pst != null)
		{
			try
			{
				pst.close();
			} catch (Exception e)
			{
				if (Log.isDebug())
					Log.debug("%s,", Log.trace(e));
			}
		}
		if (conn != null)
		{
			try
			{
				conn.close();
			} catch (Exception e)
			{
				if (Log.isDebug())
					Log.debug("%s,", Log.trace(e));
			}
		}
	}

	public static final void closeConn(Connection conn)
	{
		if (conn != null)
		{
			try
			{
				conn.close();
			} catch (Exception e)
			{
				if (Log.isDebug())
					Log.debug("%s,", Log.trace(e));
			}
		}
	}

	public static final void closeConn(Connection conn, PreparedStatement pst)
	{
		DbMisc.closeConn(conn, pst, null, null);
	}

	public static final void closeConn(Connection conn, Statement st, ResultSet rst, CallableStatement call)
	{
		if (rst != null)
		{
			try
			{
				rst.close();
			} catch (Exception e)
			{
				if (Log.isDebug())
					Log.debug("%s,", Log.trace(e));
			}
		}
		if (st != null)
		{
			try
			{
				st.close();
			} catch (Exception e)
			{
				if (Log.isDebug())
					Log.debug("%s,", Log.trace(e));
			}
		}
		if (call != null)
		{
			try
			{
				call.close();
			} catch (Exception e)
			{
				if (Log.isDebug())
					Log.debug("%s,", Log.trace(e));
			}
		}
		if (conn != null)
		{
			try
			{
				conn.close();
			} catch (Exception e)
			{
				if (Log.isDebug())
					Log.debug("%s,", Log.trace(e));
			}
		}
	}

	public static final void closeConn(PreparedStatement pst, ResultSet rst)
	{
		if (rst != null)
		{
			try
			{
				rst.close();
			} catch (Exception e)
			{
				if (Log.isDebug())
					Log.debug("%s,", Log.trace(e));
			}
		}
		if (pst != null)
		{
			try
			{
				pst.close();
			} catch (Exception e)
			{
				if (Log.isDebug())
					Log.debug("%s,", Log.trace(e));
			}
		}
	}

	public static final void begin(Connection conn)
	{
		try
		{
			if (conn.getAutoCommit())
				conn.setAutoCommit(false);
		} catch (Exception e)
		{
			if (Log.isError())
				Log.error("%s", Log.trace(e));
		}
	}

	public static final void commit(Connection conn)
	{
		try
		{
			if (!conn.getAutoCommit())
				conn.commit();
		} catch (Exception e)
		{
			if (Log.isError())
				Log.error("%s", Log.trace(e));
		}
	}

	public static final void rollback(Connection conn)
	{
		try
		{
			if (!conn.getAutoCommit())
				conn.rollback();
		} catch (Exception e)
		{
			if (Log.isError())
				Log.error("%s", Log.trace(e));
		}
	}

	public static final String trimSql(String sql)
	{
		sql = Misc.trim(sql);
		if (sql == null)
			return null;
		try
		{
			sql = sql.replace(" ", "");
			sql = sql.replaceAll("\\(", "");
			sql = sql.replaceAll("\\)", "");
			sql = sql.replace("'", "");
			sql = sql.replace(";", "");
			sql = sql.replace("--", "");
			sql = sql.replace("/**/", "");
			return sql;
		} catch (Exception e)
		{
			if (Log.isDebug())
				Log.debug("%s,", Log.trace(e));
			return null;
		}
	}
}
