package Entity;

/**
 * ��һ����Ʒ�ʽ
 * ���������Ϣ Ĭ����������Ļ�����Ͻ�Ϊ����ԭ�㣬����X����������Y����
 * 
 * @author Administrator
 *
 */
public class Crumbus3 {
	public Point point;// ���ε��ĸ���
	public int width;// ���εĿ��
	public int height;// ���εĸ߶ȣ�Ĭ��Ϊ10
	public double Ctan;// �����ڽǵ�Tan��ֵ

	
	
	
	/** �вι��췽��1 */
	public Crumbus3(int x, int y) {// x,y����������εĶ�������
		point = new Point(x,y);//��������
		this.width = 2*x+1;
		this.height = this.width;//��������������֮��Ĺ�ϵ��֪x=2y+1
		//�������ڽǵ�tan��ֵ��
		this.Ctan = 
				Math.round((((double) this.height / (double) this.width)));

	}



	/** ��ӡ���� */
	public void printlnCrumbus() {
		int min_x = point.x;
		int max_x = point.x;
		int min_y = point.y;
		int max_y = point.y;
		for(int i=0;i<this.height;i++) {
			
			for(int j=0;j<this.width;j++) {
				if(i>=min_y&&i<=max_y&&j>=min_x&&j<=max_x) {
					System.out.print("*");
					continue;
				}
				
				System.out.print(" ");
			}
			if(i==(this.height+1)/2) {
				min_x +=1;
				max_x -=1;
				min_y +=1;
				max_y -=1;
			}
			if(i!=(this.height+1)/2) {
				min_x -=1;
				max_x +=1;
				min_y -=1;
				max_y +=1;
			}
			
			System.out.println();
		}	
	}

}
