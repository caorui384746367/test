package Entity;

/**
 * 第一种设计方式
 * 菱形相关信息 默认坐标是屏幕的左上角为坐标原点，向右X递增，向下Y递增
 * 
 * @author Administrator
 *
 */
public class Crumbus2 {
	public Point point;// 菱形的四个点
	public int width;// 菱形的宽度
	public int height;// 菱形的高度，默认为10
	public double Ctan;// 菱形内角的Tan的值

	
	
	
	/** 有参构造方法1 */
	public Crumbus2(int x, int y) {// x,y代表的是菱形的顶点坐标
		point = new Point(x,y);//创建顶点
		this.width = 2*x+1;
		this.height = (this.width-1)/2+1;//由输出点的行与列之间的关系可知x=2y+1
		

	}



	/** 打印菱形 */
	public void printlnCrumbus() {
		int min_x = point.x;
		int max_x = point.x;
		int min_y = point.y;
		int max_y = point.y;
		for(int i=0;i<this.height/2;i++) {
			
			for(int j=0;j<this.width;j++) {
				if(i>=min_y&&i<=max_y&&j>=min_x&&j<=max_x) {
					System.out.print("*");
					continue;
				}
				System.out.print(" ");
			}
			min_x -=1;
			max_x +=1;
			min_y -=1;
			max_y +=1;
			System.out.println();
		}
		
	}

}
