package Entity;

/**
 * ��һ����Ʒ�ʽ
 * ���������Ϣ Ĭ����������Ļ�����Ͻ�Ϊ����ԭ�㣬����X����������Y����
 * 
 * @author Administrator
 *
 */
public class Crumbus2 {
	public Point point;// ���ε��ĸ���
	public int width;// ���εĿ��
	public int height;// ���εĸ߶ȣ�Ĭ��Ϊ10
	public double Ctan;// �����ڽǵ�Tan��ֵ

	
	
	
	/** �вι��췽��1 */
	public Crumbus2(int x, int y) {// x,y����������εĶ�������
		point = new Point(x,y);//��������
		this.width = 2*x+1;
		this.height = (this.width-1)/2+1;//��������������֮��Ĺ�ϵ��֪x=2y+1
		

	}



	/** ��ӡ���� */
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
