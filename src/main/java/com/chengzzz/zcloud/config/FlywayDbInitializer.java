//package com.chengzzz.zcloud.config;
//
//import cn.hutool.core.util.StrUtil;
//import org.flywaydb.core.Flyway;
//import org.springframework.context.annotation.Configuration;
//import javax.annotation.PostConstruct;
//import javax.annotation.Resource;
//import javax.sql.DataSource;
//import java.sql.SQLException;
//import java.util.Locale;
//
///**
// * 数据库初始化
// *
// * @author zhaojun
// */
//@Configuration
//public class FlywayDbInitializer {
//
//	public static final String[] SUPPORT_DB_TYPE = new String[]{"sqlite"};
//
//	@Resource
//	private DataSource dataSource;
//
//
//	/**
//	 * 启动时根据当前数据库类型执行数据库初始化
//	 */
//	@PostConstruct
//	public void migrateFlyway() {
//		try {
//			String databaseProductName = dataSource.getConnection().getMetaData().getDatabaseProductName();
//			String dbType = databaseProductName.toLowerCase(Locale.ROOT);
//
//			// 检查当前数据库类型是否支持
//			if (!StrUtil.equalsAnyIgnoreCase(dbType, SUPPORT_DB_TYPE)) {
//				throw new RuntimeException("不支持的数据库类型 [" + dbType + "]");
//			}
//
//			Flyway load = Flyway.configure().dataSource(dataSource).outOfOrder(true).locations("db/migration/" + dbType).load();
//			load.migrate();
//		} catch (SQLException e) {
//			e.printStackTrace();
//		}
//	}
//
//}