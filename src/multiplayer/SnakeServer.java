package multiplayer;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.kryonet.Server;

import lib.Block;
import main.Snake;
import main.SnakeGame;

public class SnakeServer extends SnakeGame {
	private Server server;
	private Map<Connection, Snake> connections;

	public SnakeServer() throws IOException {
		connections = new HashMap<>();
		server = new Server();
		server.getKryo().register(ActionData.class);
		server.getKryo().register(GameData.class);
		server.getKryo().register(BlockData.class);
		server.getKryo().register(ArrayList.class);
		server.addListener(new ServerListener());
		server.start();
		super.start();
		server.bind(54333);
		System.out.println("server Started");
	}

	class ServerListener extends Listener {
		@Override
		public void connected(Connection connection) {
			super.connected(connection);
			Snake newSnake = new Snake(10, 10, map);
			connections.put(connection, newSnake);
			System.out.println("Client connected");
		}

		@Override
		public void disconnected(Connection connection) {
			super.disconnected(connection);
			connections.remove(connection);
			System.out.println(connection.toString() + " : disconnected");
		}

		@Override
		public void received(Connection connection, Object o) {
			super.received(connection, o);
			if (o instanceof ActionData) {
				ActionData ad = (ActionData) o;
				Snake snake = connections.get(connection);
				snake.setDx(ad.dx);
				snake.setDy(ad.dy);
			}
		}

	}

	@Override
	protected void gameLogic() {
		System.out.println("Running");
		System.out.println("Number of snake : " + connections.size());
		for (Snake s : connections.values()) {
			s.move();
		}
		sendDataToClients();
	}

	public void sendDataToClients() {
		GameData gameData = new GameData();
		for (Snake s : connections.values()) {
			for (Block b : s.getBody()) {
				BlockData data = new BlockData();
				data.x = b.getX();
				data.y = b.getY();
				data.r = b.getColor().getRed();
				data.g = b.getColor().getGreen();
				data.b = b.getColor().getBlue();
				gameData.blockdata.add(data);
			}
		}
		for (Connection c : connections.keySet()) {
			c.sendTCP(gameData);
		}

	}

	public static void main(String[] args) {
		SnakeServer server;
		try {
			server = new SnakeServer();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
