package com.tianyl.agent.demo;

import java.lang.reflect.Method;

public class ReflectionUtil {

	public static Object executeMethod(Object obj, String methodName) {
		try {
			Method method = obj.getClass().getDeclaredMethod(methodName);
			return method.invoke(obj);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

}
