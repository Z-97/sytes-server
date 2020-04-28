package org.sytes.data.server;
import java.io.FileReader;
import java.util.Properties;
import java.util.Set;
import org.apache.ibatis.io.ResolverUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.sytes.core.actor.RemoteActorName;
import org.sytes.core.db.XmlDBModule;
import org.sytes.data.server.fengyun.place.AddSportPlaceActor;
import org.sytes.data.server.fengyun.place.CanelMatchActor;
import org.sytes.data.server.fengyun.place.CitySportPlaceActor;
import org.sytes.data.server.fengyun.place.EnrollActor;
import org.sytes.data.server.fengyun.place.RechargeCallBackActor;
import org.sytes.data.server.fengyun.place.SponsorMatchActor;
import org.sytes.data.server.fengyun.place.SportPlaceManager;
import org.sytes.data.server.fengyun.user.MyPayLogActor;
import org.sytes.data.server.fengyun.user.UpdateUserInfoActor;
import org.sytes.data.server.fengyun.user.UserInfoActor;
import org.sytes.data.server.fengyun.user.UserManager;
import com.google.inject.AbstractModule;
import com.google.inject.Binder;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Key;
import com.google.inject.Singleton;
import com.google.inject.Stage;
import com.google.inject.name.Names;
import com.typesafe.config.ConfigFactory;

import akka.actor.ActorSystem;
import akka.actor.Props;


/**
 * 程序运行的环境，单列，类似spring的ApplicationContext
 *
 * @author wang
 */
public class ApplicationContext {

    private static final Logger LOG = LoggerFactory.getLogger(ApplicationContext.class);

    // server配置文件
    private static final String SERVER_CFG_FILE = "config/application.properties";
    
    // user-data数据库jdbc路径
    private static final String DATADB_JDBC_PATH = "config/datadb.properties";
    // user-data数据库environment
    private static final String DATADB_ENVIRONMENT_ID = "datadb_product";
    // user-data数据库xml配置文件
    private static final String DATADB_XML_CFG_PATH = "datadb-config.xml";

    // game-dic数据库jdbc路径
    //private static final String DICDB_JDBC_PATH = "config/jdbc/dicdb.properties";
    // game-dic数据库mapper包
    //private static final String DICDB_MAPPER_PKG = "com.alex.game.dbdic.mapper";

    // server组件扫描的包
    private static final String SERVER_PACKAGE_PATH = "org.sytes";

    private static ApplicationContext instance;

    private final Injector injector;

    private ApplicationContext() {
		this.injector = Guice.createInjector(Stage.PRODUCTION,
				// 用户数据库模块
				new XmlDBModule(DATADB_JDBC_PATH, DATADB_ENVIRONMENT_ID, DATADB_XML_CFG_PATH),
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
		initActor();
	}

    /**
     * 注册actor
     */
    private void initActor() {
    	ActorSystem system=ActorSystem.create("sys",ConfigFactory.load("data_server_actor.conf"));
    	//system.actorOf(Props.create(GateServerRemoteAcrtor.class), "remoteAcrtor");
    	 /**************个人信息**************/
    	system.actorOf(Props.create(UserInfoActor.class,injector.getInstance(UserManager.class)), RemoteActorName.userInfoActor);
    	system.actorOf(Props.create(UpdateUserInfoActor.class,injector.getInstance(UserManager.class)), RemoteActorName.updateUserInfoActor);
    	system.actorOf(Props.create(MyPayLogActor.class,injector.getInstance(UserManager.class)), RemoteActorName.myPayLogActor);
	    /**************场地相关**************/
    	system.actorOf(Props.create(AddSportPlaceActor.class,injector.getInstance(SportPlaceManager.class)), RemoteActorName.addSportPlaceActor);
    	system.actorOf(Props.create(CitySportPlaceActor.class,injector.getInstance(SportPlaceManager.class)), RemoteActorName.citySportPlaceActor);
    	system.actorOf(Props.create(SponsorMatchActor.class,injector.getInstance(SportPlaceManager.class)), RemoteActorName.sponsorMatchActor);
    	system.actorOf(Props.create(EnrollActor.class,injector.getInstance(SportPlaceManager.class)), RemoteActorName.enrollActor);
    	system.actorOf(Props.create(CanelMatchActor.class,injector.getInstance(SportPlaceManager.class)), RemoteActorName.canelMatchActor);
    	 /**************充值相关**************/
    	system.actorOf(Props.create(RechargeCallBackActor.class,injector.getInstance(SportPlaceManager.class)), RemoteActorName.rechargeCallBackActor);
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

}
