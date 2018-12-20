package Entity;

/**
 * 第一种设计方式
 * 菱形相关信息 默认坐标是屏幕的左上角为坐标原点，向右X递增，向下Y递增
 * 
 * @author Administrator
 *
 */
public class Crumbus {
	public Point point;// 菱形的四个点
	public int width;// 菱形的宽度
	public int height;// 菱形的高度，默认为10
	public double Ctan;// 菱形内角的Tan的值

	
	
	
	/** 有参构造方法1 */
	public Crumbus(int x, int y) {// x,y代表的是菱形的顶点坐标
		if(x*2>y||y>2*x) {//确保有刚好的行数和列数展示
			y = 2*x;
		}
		this.width = 2*x;
		this.height = y;
		point = new Point(x, y);// 顶点
		//求菱形内角的tan的值；
		this.Ctan = 
				Math.round((((double) this.height / (double) this.width)));

	}



	/** 打印菱形 */
	public void printlnCrumbus() {
		for (int i = 0; i < this.height+1; i++) {//行
			for (int j = 0; j < this.width+1; j++) {//列
				
				if (j <= this.width/2) {// 左半边菱形界
					if (i >= (double)this.height/2 - j*this.Ctan 
										&& 
						i <= (double)this.height/2 + j*this.Ctan) {
						System.out.print("*");
						continue;
					}

				}

				if (j >= this.width / 2) {// 左半边菱形界
					if (i>=(double)this.height/2 - (double)(this.width-j)*this.Ctan
														&& 
						i<=(double)this.height/2 + (double)(this.width-j)*this.Ctan) {
						System.out.print("*");
						continue;
					}

				}
				System.out.print(" ");
			}
			System.out.println();
		}
	}

}
