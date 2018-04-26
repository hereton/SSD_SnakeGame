package multiplayer;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Observable;

import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;

import multiplayer.SnakeServer.ServerListener;

public class SnakeClient extends Observable {
	private Client client;

	public SnakeClient() throws IOException {

		client = new Client();
		client.getKryo().register(ActionData.class);
		client.getKryo().register(GameData.class);
		client.getKryo().register(BlockData.class);
		client.getKryo().register(ArrayList.class);
		client.addListener(new ClientListener());
		client.start();
		client.connect(5000, "127.0.0.1", 54333);
	}

	public void handleLeftKey() {
		ActionData ad = new ActionData();
		ad.dx = -1;
		ad.dy = 0;
		client.sendTCP(ad);
	}

	public void handleRightKey() {
		ActionData ad = new ActionData();
		ad.dx = 1;
		ad.dy = 0;
		client.sendTCP(ad);
	}

	public void handleUpKey() {
		ActionData ad = new ActionData();
		ad.dx = 0;
		ad.dy = -1;
		client.sendTCP(ad);
	}

	public void handleDownKey() {
		ActionData ad = new ActionData();
		ad.dx = 0;
		ad.dy = 1;
		client.sendTCP(ad);
	}

	class ClientListener extends Listener {
		@Override
		public void connected(Connection arg0) {
			super.connected(arg0);
		}

		@Override
		public void received(Connection connection, Object o) {
			super.received(connection, o);
			if (o instanceof GameData) {
				GameData gameData = (GameData) o;
				System.out.println("Recieve " + gameData);
				setChanged();
				notifyObservers(gameData);
			}

		}
	}

	public static void main(String[] args) {
		try {
			SnakeClient client = new SnakeClient();
			SnakeGui gui = new SnakeGui(client);
			gui.setVisible(true);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			Thread.sleep(60 * 1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
