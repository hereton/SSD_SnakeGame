package lib;

public class Block {

	protected int x;
	protected int y;

	public Block(int x, int y) {
		this.x = x;
		this.y = y;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public void setX(int x) {
		this.x = x;
	}

	public void setY(int y) {
		this.y = y;
	}

	public boolean overlapped(Block b) {
		return x == b.x && y == b.y;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null)
			return false;
		if (obj.getClass() != this.getClass())
			return false;
		Block other = (Block) obj;
		return this.x == other.x && this.y == other.y;
	}
}
