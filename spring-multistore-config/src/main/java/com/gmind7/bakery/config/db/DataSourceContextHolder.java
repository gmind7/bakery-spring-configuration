package com.gmind7.bakery.config.db;

import com.gmind7.bakery.config.db.DataSourceType;

public class DataSourceContextHolder {
	
	private static final ThreadLocal<DataSourceType> DATASOURCE_CONTEXT_HOLDER = new ThreadLocal<DataSourceType>();

	public static void setDataSourceType(DataSourceType dataSourceType)	{
		DATASOURCE_CONTEXT_HOLDER.set(dataSourceType);
	}

	public static DataSourceType getDataSourceType() {
		return (DataSourceType)DATASOURCE_CONTEXT_HOLDER.get();
	}

	public static void clearDataSourceType() {
		DATASOURCE_CONTEXT_HOLDER.remove();
	}
	
}
