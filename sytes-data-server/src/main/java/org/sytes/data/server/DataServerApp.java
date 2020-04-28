package org.sytes.data.server;
import java.text.ParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 *
 */
public class DataServerApp {
	private static final Logger LOG = LoggerFactory.getLogger(DataServerApp.class);
	

	public static void main(String[] args) throws ParseException {
		
		
		
		try {
			// 创建程序上下文
			ApplicationContext.createInstance();
			// 注册关闭Hook，所有的关闭操作都注册到onShutDown中
			Runtime.getRuntime().addShutdownHook(new Thread(() -> {
				LOG.info("数据服务器关闭中...");
				long now = System.currentTimeMillis();

				// 处理关服逻辑，如把保存玩家数据等

				LOG.info("数据服务器关闭成功,耗时[{}]毫秒", System.currentTimeMillis() - now);
			}, "CloseDataServer "));
		} catch (Exception e) {
			LOG.error("数据服务器启动异常", e);
			System.exit(1);
		}
		
	
		
	}
}
