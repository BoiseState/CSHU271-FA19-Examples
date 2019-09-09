package cshu271.tictactoe;

import org.glassfish.grizzly.http.server.HttpServer;

import javax.ws.rs.core.UriBuilder;
import java.io.IOException;
import java.net.URI;
import org.glassfish.grizzly.http.server.CLStaticHttpHandler;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.server.ResourceConfig;

public class Main
{

	private static final int PORT = 9996;
	private static int getPort(int defaultPort)
	{
		//grab port from environment, otherwise fall back to default port 9998
		String httpPort = System.getProperty("jersey.test.port");
		if (null != httpPort)
		{
			try
			{
				return Integer.parseInt(httpPort);
			} catch (NumberFormatException e)
			{
			}
		}
		return defaultPort;
	}

	private static URI getBaseURI()
	{
		return UriBuilder.fromUri("http://0.0.0.0/").port(getPort(PORT)).build();
	}

	public static final URI BASE_URI = getBaseURI();

	protected static HttpServer startServer() throws IOException
	{
		ResourceConfig resourceConfig = new ResourceConfig().packages("cshu271.tictactoe");

        System.out.println("Starting grizzly2...");
        return GrizzlyHttpServerFactory.createHttpServer(BASE_URI, resourceConfig);
	}

	public static void main(String[] args) throws IOException
	{
		// Grizzly 2 initialization
		HttpServer httpServer = startServer();
		CLStaticHttpHandler staticHttpHandler = new CLStaticHttpHandler(Main.class.getClassLoader());
		httpServer.getServerConfiguration().addHttpHandler(staticHttpHandler, "/web");
		System.out.println(String.format("TicTacToe started at http://localhost:" + PORT + "/web/index.html"
			+ "\nHit enter to stop it...",
			BASE_URI));
		System.in.read();
		httpServer.stop();
	}
}
