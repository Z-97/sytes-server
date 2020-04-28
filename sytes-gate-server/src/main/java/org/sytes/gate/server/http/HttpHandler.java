package org.sytes.gate.server.http;

import static io.netty.handler.codec.http.HttpMethod.POST;
import static io.netty.handler.codec.http.HttpResponseStatus.OK;
import static io.netty.handler.codec.http.HttpVersion.HTTP_1_1;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.TimeUnit;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.sytes.gate.server.ActorFactoy;
import org.sytes.gate.server.ApplicationContext;
import akka.actor.ActorRef;
import akka.dispatch.OnFailure;
import akka.dispatch.OnSuccess;
import akka.pattern.Patterns;
import akka.util.Timeout;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.ChannelHandler.Sharable;
import io.netty.handler.codec.http.DefaultFullHttpResponse;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.FullHttpResponse;
import io.netty.handler.codec.http.HttpHeaderNames;

import scala.concurrent.Future;
import scala.concurrent.duration.Duration;

@Sharable
public class HttpHandler extends SimpleChannelInboundHandler<FullHttpRequest> {
	private static final Logger HTTP_SERVICE_LOG = LoggerFactory.getLogger("HttpServiceLog");
	private static final Logger LOG = LoggerFactory.getLogger(HttpHandler.class);

	@Override
	protected void channelRead0(ChannelHandlerContext ctx, FullHttpRequest req) throws Exception {

		// String value = req.headers().get(HttpHeaderNames.COOKIE);
		// if (value == null) {
		// System.out.println("no k");
		// DefaultCookie dc = new DefaultCookie("adadad"+Math.round(10000), "adadd222");
		// dc.setMaxAge(0);
		// FullHttpResponse response = new DefaultFullHttpResponse(HTTP_1_1, OK,
		// Unpooled.wrappedBuffer(result.getBytes(StandardCharsets.UTF_8)));
		// response.headers().set(HttpHeaderNames.CONTENT_TYPE, "application/json");
		// response.headers().set(HttpHeaderNames.CONTENT_LENGTH,
		// response.content().readableBytes());
		//
		// response.headers().add(HttpHeaderNames.SET_COOKIE,
		// ClientCookieEncoder.STRICT.encode(dc));
		//
		// ctx.write(response);
		// ctx.flush();
		// } else {
		//
		// Cookie cookies = ClientCookieDecoder.STRICT.decode(value);
		//
		// result = result + cookies.toString();
		// FullHttpResponse response = new DefaultFullHttpResponse(HTTP_1_1, OK,
		// Unpooled.wrappedBuffer(result.getBytes(StandardCharsets.UTF_8)));
		// response.headers().set(HttpHeaderNames.CONTENT_TYPE, "application/json");
		// response.headers().set(HttpHeaderNames.CONTENT_LENGTH,
		// response.content().readableBytes());
		//
		// ctx.write(response);
		// ctx.flush();
		// }

		Channel channel = ctx.channel(); // Handle a bad request. if
		if (!req.decoderResult().isSuccess()) {
			HTTP_SERVICE_LOG.error("http会话[{}]错误的请求", channel);
			channel.close();
			return;
		}

		if (req.method() != POST) {
			HTTP_SERVICE_LOG.warn("http会话[{}]请求的方法不是POST", channel);
			channel.close();
			return;
		}
		String contentText = req.content().toString(StandardCharsets.UTF_8);
		// 请求内容 String contentText = req.content().toString(StandardCharsets.UTF_8);
		long now = System.currentTimeMillis();
		String uri = req.uri();
		ActorFactoy actorFactoy = ApplicationContext.getInstance().getActorFactoy();
		ActorRef handler = actorFactoy.getHandler(uri);
		if (handler == null) {
			sendToHttpClient(ctx, "handler id[{}] 未找到");
			LOG.warn("handler id[{}] 未找到", uri);
		} else {
			Timeout timeOut = new Timeout(Duration.create(2, TimeUnit.SECONDS));
			Future<Object> futrue = Patterns.ask(handler, contentText, timeOut);
			futrue.onSuccess(new OnSuccess<Object>() {

				@Override

				public void onSuccess(Object msg) throws Throwable {

					sendToHttpClient(ctx, (String) msg);

					LOG.info("[{}]handlerid[{}]用时[{}]", handler.path(), uri, System.currentTimeMillis() - now);
				}

			}, actorFactoy.getSystem().dispatcher());
			futrue.onFailure(new OnFailure() {
				@Override
				public void onFailure(Throwable errormsg) throws Throwable {
					sendToHttpClient(ctx, "调用超时");
					LOG.warn("[{}]handler id[{}] [{}]", handler.path(), uri, errormsg);

				}

			}, actorFactoy.getSystem().dispatcher());
		}

	}

	public void sendToHttpClient(ChannelHandlerContext ctx, String result) {
		FullHttpResponse response = new DefaultFullHttpResponse(HTTP_1_1, OK,
				Unpooled.wrappedBuffer(result.getBytes(StandardCharsets.UTF_8)));
		response.headers().set(HttpHeaderNames.CONTENT_TYPE, "application/json");
		response.headers().set(HttpHeaderNames.CONTENT_LENGTH, response.content().readableBytes());
		// if (userId != null) {
		//
		// }
		ctx.write(response);
		ctx.flush();

	}

	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		super.channelActive(ctx);
		HTTP_SERVICE_LOG.info("建立http连接:[{}]", ctx.channel());
	}

	@Override
	public void channelInactive(ChannelHandlerContext ctx) throws Exception {
		super.channelInactive(ctx);
		HTTP_SERVICE_LOG.info("断开http连接:[{}]", ctx.channel());
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		HTTP_SERVICE_LOG.warn("http会话[{}]异常[{}]", ctx.channel(), cause.getMessage());
		ctx.close();
	}
}
