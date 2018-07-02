package com.chinasoft.it.wecode.event;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;

import com.chinasoft.it.wecode.common.util.ApplicationUtils;
import com.chinasoft.it.wecode.common.util.LogUtils;

@SuppressWarnings({ "rawtypes", "unchecked" })
public class EventUtils {

	private static final Logger log = LogUtils.getLogger();

	// https://blog.csdn.net/qq_25806863/article/details/71126867
	private static final ThreadPoolExecutor executor = new ThreadPoolExecutor(1, 10, 60, TimeUnit.SECONDS,
			new ArrayBlockingQueue<>(1000));

	/**
	 * 触发事件
	 * 
	 * @param eventClass
	 * @param arg
	 */
	public static void fire(Class<Event> eventClass, EventArg arg) {
		fire(eventClass, null, arg);
	}

	/**
	 * 触发事件
	 * 
	 * @param eventClass
	 * @param sender
	 * @param arg
	 */
	public static void fire(Class<Event> eventClass, Object sender, EventArg arg) {
		fire(eventClass, sender, arg, false, true);
	}

	/**
	 * 触发事件
	 * 
	 * @param eventClass
	 *            触发事件类
	 * @param sender
	 *            触发对象
	 * @param arg
	 *            事件参数
	 * @param throwable
	 *            抛出异常，默认 false
	 * @param parallel
	 *            并行执行，默认 true
	 */
	public static void fire(Class<Event> eventClass, Object sender, EventArg arg, boolean throwable, boolean parallel) {
		Map<String, Event> beansOfType = ApplicationUtils.getApplicationContext().getBeansOfType(eventClass);
		if (parallel) {
			List<Future<?>> futures = new ArrayList<>();
			for (Event event : beansOfType.values()) {
				Future<?> future = executor.submit(() -> invork(event, sender, arg, throwable));
				futures.add(future);
			}

			for (Future<?> future : futures) {
				try {
					future.get();
				} catch (InterruptedException | ExecutionException e) {
					log.error("future get error", e);
				}
			}
		} else {
			for (Event event : beansOfType.values()) {
				invork(event, sender, arg, throwable);
			}
		}
	}

	private static void invork(Event event, Object sender, EventArg arg, boolean throwable) {
		if (throwable) {
			event.action(sender, arg);
		} else {
			try {
				event.action(sender, arg);
			} catch (Exception e) {
				log.error("fire event error for " + event.getClass() + " and " + arg, e);
			}
		}
	}
}
