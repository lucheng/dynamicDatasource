package com.cheng.dynamic.config;

public abstract class DynamicDataSourceContextHolder {
	
	private static final ThreadLocal<String> CONTEXT_HOLDER = new ThreadLocal<String>();

    public static void set(String dataSourceType) {
        CONTEXT_HOLDER.set(dataSourceType);
    }

    public static String get() {
        return CONTEXT_HOLDER.get();
    }

    public static void remove() {
        CONTEXT_HOLDER.remove();
    }
}
