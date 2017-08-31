package com.tianyl.agent.demo;

import java.lang.reflect.Method;
import java.util.concurrent.Callable;

import net.bytebuddy.implementation.bind.annotation.Argument;
import net.bytebuddy.implementation.bind.annotation.Origin;
import net.bytebuddy.implementation.bind.annotation.RuntimeType;
import net.bytebuddy.implementation.bind.annotation.SuperCall;

public class FilterInterceptor {

	@RuntimeType
	public static Object intercept(@Origin Method method, @SuperCall Callable<?> callable, @Argument(0) Object reqObj, @Argument(1) Object response) {
		System.out.println("in filter intercept");
		long start = System.currentTimeMillis();
		try {
			return callable.call();
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		} finally {
			long end = System.currentTimeMillis();
			System.out.println("filter[]:[" + (end - start) + "]:[" + ReflectionUtil.executeMethod(reqObj, "getRequestURI") + "]");
		}

	}

}
