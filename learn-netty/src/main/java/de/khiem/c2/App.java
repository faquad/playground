package de.khiem.c2;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import java.net.InetSocketAddress;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args ) throws Exception
    {
       new App().start();
        
    }
    
    void start() throws Exception {
        EventLoopGroup group = new NioEventLoopGroup();
        try {
        final PingServerHandler h =new PingServerHandler();
        final int port = 8888;
        
        ServerBootstrap bs=new ServerBootstrap();
        final ChannelInitializer<SocketChannel> chi=new ChannelInitializer<SocketChannel>() {
            @Override
            protected void initChannel(SocketChannel ch) throws Exception {
                ch.pipeline().addLast(h);
            }
        };
        bs.group(group)
                .channel(NioServerSocketChannel.class)
                .localAddress(new InetSocketAddress(port))
                .childHandler(chi)
                ;
            ChannelFuture cf = bs.bind().sync();
            System.out.println("bootstrapt bind & sync");
            cf.channel().closeFuture().sync();
            System.out.println("closeFuture & sync");
        }finally {
            group.shutdownGracefully().sync();
        }
    }
}
