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

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class JoinCounter
{
	private final Lock lock = new ReentrantLock();
	private final Condition condition = lock.newCondition();
	private AtomicInteger counter = new AtomicInteger(0x00);

	public JoinCounter(int counter)
	{
		this.counter.set(counter < 1 ? 1 : counter);
	}

	public final void set()
	{
		if (this.counter.decrementAndGet() != 0)
			return;
		this.lock.lock();
		this.condition.signalAll();
		this.lock.unlock();
	}

	public final void waitFor()
	{
		this.lock.lock();
		try
		{
			this.condition.await();
		} catch (Exception e)
		{
			Log.error("%s", Log.trace(e));
		}
		this.lock.unlock();
	}
}
