package com.tianyl.agent.demo;

import java.lang.reflect.Method;
import java.util.concurrent.Callable;

import net.bytebuddy.implementation.bind.annotation.Origin;
import net.bytebuddy.implementation.bind.annotation.RuntimeType;
import net.bytebuddy.implementation.bind.annotation.SuperCall;

public class TimingInterceptor {

	@RuntimeType
	public static Object intercept(@Origin Method method, @SuperCall Callable<?> callable) {
		System.out.println("in intercept");
		long start = System.currentTimeMillis();
		try {
			return callable.call();
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		} finally {
			System.out.println(method + " took " + (System.currentTimeMillis() - start));
		}
	}
}
