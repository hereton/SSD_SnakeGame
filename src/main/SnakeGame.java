package main;

import lib.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class SnakeGame extends AbstractGame {

	private Random random = new Random();
	private Map map = new Map();

	private Snake snake = new Snake(10, 10);
	private Food food = new Food(15, 15);

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

	@Override
	protected void gameLogic() {
		snake.move();
		// Check if snake eat food
		for (Block b : snake.getBody()) {
			if (b.overlapped(food)) {
				Block newBlock = snake.expand();
				map.addBlock(newBlock);
				food.setX(random.nextInt(map.getSize()));
				food.setY(random.nextInt(map.getSize()));
				break;
			}
		}
		// Check if snake hit itself
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

	public void load(Memento m) {
		this.map.getBlocks().clear();
		this.snake.getBody().clear();
		for (Block b : m.snakeBlocks) {
			this.snake.getBody().add(new Block(b.getX(), b.getY()));
		}
		this.snake.setDx(m.dx);
		this.snake.setDy(m.dy);
		for (Block b : this.snake.getBody()) {
			this.map.addBlock(b);
		}
		this.food.setX(m.food.getX());
		this.food.setY(m.food.getY());
		this.map.addBlock(food);
	}

	public Memento save() {
		return new Memento(this.snake, this.food);
	}

	static class Memento {

		List<Block> snakeBlocks = new ArrayList<>();
		Food food;
		int dx;
		int dy;

		public Memento(Snake snake, Food food) {
			for (Block b : snake.getBody()) {
				snakeBlocks.add(new Block(b.getX(), b.getY()));
			}
			this.dx = snake.getDx();
			this.dy = snake.getDy();
			this.food = new Food(food.getX(), food.getY());
		}

	}

}
