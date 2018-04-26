package main;

import lib.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class SnakeGame extends AbstractGame {

	private Random random = new Random();
	public Map map = new Map();

	private Snake snake = new Snake(10, 10, map);
	private Food food = new Food(random.nextInt(map.getSize() - 1) + 0, random.nextInt(map.getSize() - 1) + 0);

	public SnakeGame() {
		for (Block b : snake.getBody()) {
			map.addBlock(b);
		}
		map.addBlock(food);
	}

	public int getMapSize() {
		return map.getSize();
	}

	public List<Block> getBlocks() {
		return map.getBlocks();
	}

	public Memento saveGame() {
		return new Memento(snake.getBody(), food, snake.getDx(), snake.getDy());
	}

	public void loadGame(Memento m) {
		this.snake.getBody().clear();
		this.snake.getBody().addAll(m.body);
		this.food = m.food;
		this.snake.setDx(m.dx);
		this.snake.setDy(m.dy);
		this.map.getBlocks().clear();
		this.map.getBlocks().addAll(snake.getBody());
		this.map.addBlock(food);
	}

	@Override
	protected void gameLogic() {
		snake.move();
		for (Block b : snake.getBody()) {
			if (b.overlapped(food)) {
				Block newBlock = snake.expand();
				map.addBlock(newBlock);

				food.setX(random.nextInt(map.getSize() - 1) + 0);
				food.setY(random.nextInt(map.getSize() - 1) + 0);
				break;
			}
		}
		if (snake.hitItself()) {
			end();
		}
	}

	@Override
	protected void handleLeftKey() {
		snake.setDx(-1);
		snake.setDy(0);
	}

	@Override
	protected void handleRightKey() {
		snake.setDx(1);
		snake.setDy(0);
	}

	@Override
	protected void handleUpKey() {
		snake.setDx(0);
		snake.setDy(-1);
	}

	@Override
	protected void handleDownKey() {
		snake.setDx(0);
		snake.setDy(1);
	}

	class Memento {
		private List<Block> body;
		private Food food;
		private int dx, dy;

		public Memento(List<Block> body, Food food, int dx, int dy) {
			this.body = new ArrayList<Block>();
			for (Block block : body) {
				this.body.add(new Block(block.getX(), block.getY(), block.getColor()));
			}
			this.food = new Food(food.getX(), food.getY());
			this.dx = dx;
			this.dy = dy;
		}
	}

}