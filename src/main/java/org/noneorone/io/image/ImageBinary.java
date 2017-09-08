package org.noneorone.io.image;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
public class ImageBinary {
	public static void main(String[] args) throws IOException {
		@SuppressWarnings("unused")
		Image imagesoure = new Image();
		System.out.println("This is the end");
	}
}
class Image {
	int width;
	int height;
	int area = width * height;
	// BufferedImage outBinary=img;
	public Image() throws IOException {
		File file = new File("F:\\validate_code\\img\\cmf.bmp");
		BufferedImage img = ImageIO.read(file);
		BufferedImage outBinary = img;
		width = img.getWidth();
		height = img.getHeight();
		int area = width * height;
		int gray[][] = new int[width][height];
		int u = 0;// �Ҷ�ƽ��ֵ
		@SuppressWarnings("unused")
		int graybinary;
		int graysum = 0;
		int graymean = 0;
		int grayfrontmean = 0;
		int graybackmean = 0;
		Color color;
		int pixl[][] = new int[width][height];
		int pixelsR;
		int pixelsG;
		int pixelsB;
		int pixelGray;
		@SuppressWarnings("unused")
		int T = 0;
		int front = 0;
		int back = 0;
		for (int i = 1; i < width; i++) { // ����߽��к��У�Ϊ����Խ��
			for (int j = 1; j < height; j++) {
				pixl[i][j] = img.getRGB(i, j);
				color = new Color(pixl[i][j]);
				pixelsR = color.getRed();// R�ռ�
				pixelsB = color.getBlue();// G�ռ�
				pixelsG = color.getGreen();// B�ռ�
				pixelGray = (int) (0.3 * pixelsR + 0.59 * pixelsG + 0.11 * pixelsB);// ����ÿ�������ĻҶ�
				gray[i][j] = (pixelGray << 16) + (pixelGray << 8) + (pixelGray);
				graysum += pixelGray;
			}
		}
		graymean = (int) (graysum / area);// ����ͼ�ĻҶ�ƽ��ֵ
		u = graymean;
		System.out.println(u);
		for (int i = 0; i < width; i++) // ��������ͼ�Ķ�ֵ����ֵ
		{
			for (int j = 0; j < height; j++) {
				if (((gray[i][j]) & (0x0000ff)) < graymean) {
					graybackmean += ((gray[i][j]) & (0x0000ff));
					back++;
				} else {
					grayfrontmean += ((gray[i][j]) & (0x0000ff));
					front++;
				}
			}
		}
		int frontvalue = (int) (grayfrontmean / front);// ǰ������
		int backvalue = (int) (graybackmean / back);// ��������
		float G[] = new float[frontvalue - backvalue + 1];// ��������
		int s = 0;
		System.out.println(front);
		System.out.println(frontvalue);
		System.out.println(backvalue);
		for (int i1 = backvalue; i1 < frontvalue + 1; i1++)// ��ǰ�����ĺͱ�������Ϊ������ô���㷨
		{
			back = 0;
			front = 0;
			grayfrontmean = 0;
			graybackmean = 0;
			for (int i = 0; i < width; i++) {
				for (int j = 0; j < height; j++) {
					if (((gray[i][j]) & (0x0000ff)) < (i1 + 1)) {
						graybackmean += ((gray[i][j]) & (0x0000ff));
						back++;
					} else {
						grayfrontmean += ((gray[i][j]) & (0x0000ff));
						front++;
					}
				}
			}
			grayfrontmean = (int) (grayfrontmean / front);
			graybackmean = (int) (graybackmean / back);
			G[s] = (((float) back / area) * (graybackmean - u)
					* (graybackmean - u) + ((float) front / area)
					* (grayfrontmean - u) * (grayfrontmean - u));
			s++;
		}
		float max = G[0];
		int index = 0;
		for (int i = 1; i < frontvalue - backvalue + 1; i++) {
			if (max < G[i]) {
				max = G[i];
				index = i;
			}
		}
		// System.out.println(G[index]);
		// System.out.println(index);
		for (int i = 0; i < width; i++) {
			for (int j = 0; j < height; j++) {
				if (((gray[i][j]) & (0x0000ff)) < (index + backvalue)) {
					outBinary.setRGB(i, j, 0x000000);
				} else {
					outBinary.setRGB(i, j, 0xffffff);
				}
			}
		}
		File f = new File("F:\\validate_code\\img\\cmf.png");
		ImageIO.write(outBinary, "jpg", f);
	}
}
