package Entity;
/**
 * 代表一个点
 * @author Administrator
 *
 */
public class Point {
	public int x;//点x的坐标
	public int y;//点y的坐标
	/**构造函数*/
	public Point(int x,int y) {
		this.x = x;
		this.y = y;
	}
	public int getX() {
		return x;
	}
	public void setX(int x) {
		this.x = x;
	}
	public int getY() {
		return y;
	}
	public void setY(int y) {
		this.y = y;
	}
	
}
