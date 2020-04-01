
public class Platform
{
	private Vector pos;
	private Vector size;
	
	public Platform(Vector pos, Vector size)
	{
		this.setPos(pos.clone());
		this.setSize(size.clone());
	}

	public Vector getPos() {
		return pos;
	}

	public void setPos(Vector pos) {
		this.pos = pos;
	}

	public Vector getSize() {
		return size;
	}

	public void setSize(Vector size) {
		this.size = size;
	}

}
