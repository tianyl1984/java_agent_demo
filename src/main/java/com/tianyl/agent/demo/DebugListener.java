package com.tianyl.agent.demo;

import net.bytebuddy.agent.builder.AgentBuilder;
import net.bytebuddy.description.type.TypeDescription;
import net.bytebuddy.dynamic.DynamicType;
import net.bytebuddy.utility.JavaModule;

public class DebugListener implements AgentBuilder.Listener {

	@Override
	public void onDiscovery(String typeName, ClassLoader classLoader, JavaModule module, boolean loaded) {
		// System.out.println("onDiscovery:" + typeName);
	}

	@Override
	public void onTransformation(TypeDescription typeDescription, ClassLoader classLoader, JavaModule module, boolean loaded, DynamicType dynamicType) {
		// System.out.println("onTransformation:" + typeDescription);
	}

	@Override
	public void onIgnored(TypeDescription typeDescription, ClassLoader classLoader, JavaModule module, boolean loaded) {
		// System.out.println("onIgnored:" + typeDescription);
	}

	@Override
	public void onError(String typeName, ClassLoader classLoader, JavaModule module, boolean loaded, Throwable throwable) {
		System.out.println("onError:" + typeName);
		throwable.printStackTrace();
	}

	@Override
	public void onComplete(String typeName, ClassLoader classLoader, JavaModule module, boolean loaded) {
		// System.out.println("onComplete:" + typeName);
	}

}
