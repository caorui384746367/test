package Entity;

/**
 * ��һ����Ʒ�ʽ
 * ���������Ϣ Ĭ����������Ļ�����Ͻ�Ϊ����ԭ�㣬����X����������Y����
 * 
 * @author Administrator
 *
 */
public class Crumbus {
	public Point point;// ���ε��ĸ���
	public int width;// ���εĿ��
	public int height;// ���εĸ߶ȣ�Ĭ��Ϊ10
	public double Ctan;// �����ڽǵ�Tan��ֵ

	
	
	
	/** �вι��췽��1 */
	public Crumbus(int x, int y) {// x,y����������εĶ�������
		if(x*2>y||y>2*x) {//ȷ���иպõ�����������չʾ
			y = 2*x;
		}
		this.width = 2*x;
		this.height = y;
		point = new Point(x, y);// ����
		//�������ڽǵ�tan��ֵ��
		this.Ctan = 
				Math.round((((double) this.height / (double) this.width)));

	}



	/** ��ӡ���� */
	public void printlnCrumbus() {
		for (int i = 0; i < this.height+1; i++) {//��
			for (int j = 0; j < this.width+1; j++) {//��
				
				if (j <= this.width/2) {// �������ν�
					if (i >= (double)this.height/2 - j*this.Ctan 
										&& 
						i <= (double)this.height/2 + j*this.Ctan) {
						System.out.print("*");
						continue;
					}

				}

				if (j >= this.width / 2) {// �������ν�
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
