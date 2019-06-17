package sim.netty;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import org.springframework.stereotype.Component;

/**
 * The type Ws server.
 */
@Component
public class WSServer {

	private static class SingletionWSServer {
        /**
         * The Instance.
         */
        static final WSServer INSTANCE = new WSServer();
	}

    /**
     * Gets instance.
     *
     * @return the instance
     */
    public static WSServer getInstance() {
		return SingletionWSServer.INSTANCE;
	}
	
	private EventLoopGroup mainGroup;
	private EventLoopGroup subGroup;
	private ServerBootstrap server;
	private ChannelFuture future;

    /**
     * Instantiates a new Ws server.
     */
    public WSServer() {
		mainGroup = new NioEventLoopGroup();
		subGroup = new NioEventLoopGroup();
		server = new ServerBootstrap();
		server.group(mainGroup, subGroup)
			.channel(NioServerSocketChannel.class)
			.childHandler(new WSServerInitialzer());
	}

    /**
     * Start.
     */
    public void start() {
		this.future = server.bind(8088);
		System.out.println("netty websocket server 启动完毕...");
	}
}
