package com.tianyl.agent.demo;

import java.lang.instrument.Instrumentation;

import net.bytebuddy.agent.builder.AgentBuilder;
import net.bytebuddy.description.type.TypeDescription;
import net.bytebuddy.dynamic.DynamicType;
import net.bytebuddy.dynamic.DynamicType.Builder;
import net.bytebuddy.implementation.MethodDelegation;
import net.bytebuddy.matcher.ElementMatchers;
import net.bytebuddy.utility.JavaModule;

public class TraceAgent {

	public static void premain(String arg, Instrumentation inst) {

		System.out.println("in agent 5");

		DebugListener listener = new DebugListener();
		new AgentBuilder.Default().with(listener).type(ElementMatchers.nameEndsWith("Demo")).transform(new AgentBuilder.Transformer() {
			@Override
			public DynamicType.Builder<?> transform(Builder<?> builder, TypeDescription typeDescription, ClassLoader classLoader, JavaModule module) {
				System.out.println("in transform");
				return builder.method(ElementMatchers.any()).intercept(MethodDelegation.to(TimingInterceptor.class));
			}
		}).installOn(inst);

		new AgentBuilder.Default().with(listener).type(ElementMatchers.named("javax.servlet.http.HttpServlet")).transform(new AgentBuilder.Transformer() {
			@Override
			public DynamicType.Builder<?> transform(Builder<?> builder, TypeDescription typeDescription, ClassLoader classLoader, JavaModule module) {
				System.out.println("in transform HttpServlet");
				return builder.method(ElementMatchers.named("service").and(ElementMatchers.isProtected())).intercept(MethodDelegation.to(HttpRequestInterceptor.class));
			}
		}).installOn(inst);

		new AgentBuilder.Default().with(listener).type(ElementMatchers.hasSuperType(ElementMatchers.named("javax.servlet.Filter"))).transform(new AgentBuilder.Transformer() {
			@Override
			public DynamicType.Builder<?> transform(Builder<?> builder, TypeDescription typeDescription, ClassLoader classLoader, JavaModule module) {
				if (typeDescription.isAbstract()) {
					return builder;
				}
				if (typeDescription.isInterface()) {
					return builder;
				}
				System.out.println("in transform Filter");
				return builder.method(ElementMatchers.named("doFilter")).intercept(MethodDelegation.to(FilterInterceptor.class));
			}
		}).installOn(inst);

	}

}
