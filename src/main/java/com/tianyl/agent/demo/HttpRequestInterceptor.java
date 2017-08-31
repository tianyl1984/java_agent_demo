package com.tianyl.agent.demo;

import java.lang.reflect.Method;
import java.util.UUID;
import java.util.concurrent.Callable;

import net.bytebuddy.implementation.bind.annotation.Argument;
import net.bytebuddy.implementation.bind.annotation.Origin;
import net.bytebuddy.implementation.bind.annotation.RuntimeType;
import net.bytebuddy.implementation.bind.annotation.SuperCall;

public class HttpRequestInterceptor {

	@RuntimeType
	public static Object intercept(@Origin Method method, @SuperCall Callable<?> callable, @Argument(0) Object reqObj, @Argument(1) Object response) {
		System.out.println("in request intercept");
		long start = System.currentTimeMillis();
		String requestId = UUID.randomUUID().toString().replaceAll("-", "");
		// System.out.println(reqObj.getClass().getName());
		// System.out.println(reqObj.getClass().getClassLoader());
		// System.out.println(callable.getClass().getClassLoader());
		System.out.println("------------start-------------");
		StackTraceElement[] eles = Thread.currentThread().getStackTrace();
		for (StackTraceElement ste : eles) {
			// System.out.println(ste);
		}
		System.out.println("-------------end--------------");

		// HttpServletRequest request = (HttpServletRequest) reqObj;
		try {
			return callable.call();
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		} finally {
			long end = System.currentTimeMillis();
			System.out.println("request[" + requestId + "]:[" + (end - start) + "]:[" + ReflectionUtil.executeMethod(reqObj, "getRequestURI") + "]");
			// System.out.println("request[" + requestId + "]:[" + (end - start) + "]:[]");
		}
	}

}
