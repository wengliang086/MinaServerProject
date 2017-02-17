package com.mina.test;

import java.net.InetSocketAddress;
import java.util.Date;

import org.apache.mina.core.service.IoAcceptor;
import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.filter.codec.serialization.ObjectSerializationCodecFactory;
import org.apache.mina.filter.logging.LoggingFilter;
import org.apache.mina.transport.socket.nio.NioSocketAcceptor;

public class MinaServer {

	public static void main(String[] args) {
		IoAcceptor acceptor = new NioSocketAcceptor();
		// 添加过滤链
		acceptor.getFilterChain().addLast("logger", new LoggingFilter());
		acceptor.getFilterChain().addLast("codec", new ProtocolCodecFilter(new ObjectSerializationCodecFactory()));
		acceptor.setHandler(new DemoServerHandler());
		acceptor.getSessionConfig().setReadBufferSize(2048);
		acceptor.getSessionConfig().setIdleTime(IdleStatus.BOTH_IDLE, 10);
		try {
			acceptor.bind(new InetSocketAddress(9999));
			System.out.println("服务器启动成功，监听端口：9999");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static class DemoServerHandler extends IoHandlerAdapter {

		@Override
		public void messageReceived(IoSession session, Object message) throws Exception {
			super.messageReceived(session, message);
			System.out.println("messageReceived:" + message.toString());
			session.write(new Date().toString());
		}

		@Override
		public void sessionCreated(IoSession session) throws Exception {
			super.sessionCreated(session);
			System.out.println("sessionCreated:");
		}

		@Override
		public void sessionOpened(IoSession session) throws Exception {
			super.sessionOpened(session);
			System.out.println("sessionOpened:");
		}

		@Override
		public void sessionClosed(IoSession session) throws Exception {
			super.sessionClosed(session);
			System.out.println("sessionClosed:");
		}

		@Override
		public void messageSent(IoSession session, Object message) throws Exception {
			super.messageSent(session, message);
			System.out.println("messageSent:" + message.toString());
		}
	}
}
