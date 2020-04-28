package org.sytes.gate.server;

import java.io.FileReader;
import java.util.Properties;
import java.util.Set;

import org.apache.ibatis.io.ResolverUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.google.inject.AbstractModule;
import com.google.inject.Binder;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Key;
import com.google.inject.Singleton;
import com.google.inject.Stage;
import com.google.inject.name.Names;

public class ApplicationContext {
	    private static final Logger LOG = LoggerFactory.getLogger(ApplicationContext.class);
	  // server配置文件
	    private static final String SERVER_CFG_FILE = "config/application.properties";
	    // server组件扫描的包
	    private static final String SERVER_PACKAGE_PATH = "org.sytes";
	    private static ApplicationContext instance;

	    private final Injector injector;
	    private ApplicationContext() {
			this.injector = Guice.createInjector(Stage.PRODUCTION,
					// 配置数据数据库模块
					//new DBModule(DICDB_JDBC_PATH, DICDB_MAPPER_PKG),
					// server模块(扫描单列类注册)
					new AbstractModule() {
						@Override
						protected void configure() {
							Properties props = new Properties();
							try (FileReader serverPropsReader = new FileReader(SERVER_CFG_FILE)) {
								props.load(serverPropsReader);
							} catch (Exception e) {
								throw new RuntimeException("加载服务器配置文件[" + SERVER_CFG_FILE + "]错误", e);
							}
							Binder binder = binder();
							Names.bindProperties(binder, props);
							// 扫描包注册所有单列类
							Set<Class<? extends Object>> singletonTypes = new ResolverUtil<Object>()
									.find(new ResolverUtil.AnnotatedWith(Singleton.class), SERVER_PACKAGE_PATH)
									.getClasses();
							for (Class<? extends Object> singletonType : singletonTypes) {
								binder.bind(singletonType);
							}
							
							
						}
					});
		}

	    /**
	     * 同步方法创建实例
	     *
	     * @return
	     */
	    public static synchronized ApplicationContext createInstance() {
	        if (instance == null) {
	            LOG.info("创建ApplicationContext...");
	            instance = new ApplicationContext();
	            LOG.info("创建ApplicationContext成功");
	        } else {
	            LOG.warn("ApplicationContext实例已经创建，请勿重复创建");
	        }

	        return instance;
	    }
	    
		/**
		 * 获取实例
		 * @return
		 */
		public static ApplicationContext getInstance() {
			
			if (instance == null) {
				createInstance();
			}
			
			return instance;
		}

	    /**
	     * 获取bean实例
	     *
	     * @param type
	     * @param <T>
	     * @return
	     */
	    public <T> T getBean(Class<T> type) {
	        return instance.injector.getInstance(type);
	    }

	    /**
	     * 获取bean实例
	     *
	     * @param key
	     * @param <T>
	     * @return
	     */
	    public <T> T getBean(Key<T> key) {

	        return instance.injector.getInstance(key);
	    }

	    /**
	     * 手动注入
	     *
	     * @param obj
	     */
	    public void injectMembers(Object obj) {
	        instance.injector.injectMembers(obj);
	    }

		public ActorFactoy getActorFactoy() {
			
			return getBean(ActorFactoy.class);
		}
}
