package com.olande.common.util;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.font.FontRenderContext;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;

/***
 * 
 * 
 * @author chengfr
 *
 */
public final class ImgUtils {
	/**
	 * 向左旋转90
	 * 
	 * @param srcImg
	 * @param turnImage
	 */
	private final static void trunLeft90(BufferedImage srcImg,
			BufferedImage turnImage) {
		int width = srcImg.getWidth();
		int height = srcImg.getHeight();
		for (int x = 0; x < width; x++) {
			for (int y = 0; y < height; y++) {
				int rgb = srcImg.getRGB(x, y);
				int pX = y;
				int pY = width - x - 1;
				turnImage.setRGB(pX, pY, rgb);
			}
		}
	}

	/**
	 * 向右旋转90
	 * 
	 * @param srcImg
	 * @param turnImage
	 */
	private final static void trunRight90(BufferedImage srcImg,
			BufferedImage turnImage) {
		int width = srcImg.getWidth();
		int height = srcImg.getHeight();
		for (int x = 0; x < width; x++) {
			for (int y = 0; y < height; y++) {
				int rgb = srcImg.getRGB(x, y);
				int pX = height - y - 1;
				int pY = x;
				turnImage.setRGB(pX, pY, rgb);
			}
		}
	}

	/**
	 * 左右颠倒
	 * 
	 * @param srcImg
	 * @param turnImage
	 */
	private final static void trunLR(BufferedImage srcImg,
			BufferedImage turnImage) {
		int width = srcImg.getWidth();
		int height = srcImg.getHeight();
		for (int x = 0; x < width; x++) {
			for (int y = 0; y < height; y++) {
				int rgb = srcImg.getRGB(x, y);
				int pX = width - x - 1;
				int pY = y;
				turnImage.setRGB(pX, pY, rgb);
			}
		}
	}

	/**
	 * 上下颠倒
	 * 
	 * @param srcImg
	 * @param turnImage
	 */
	public final static void trunTB(BufferedImage srcImg,
			BufferedImage turnImage) {
		int width = srcImg.getWidth();
		int height = srcImg.getHeight();
		for (int x = 0; x < width; x++) {
			for (int y = 0; y < height; y++) {
				int rgb = srcImg.getRGB(x, y);
				int pX = width - x - 1;
				int pY = height - y - 1;
				turnImage.setRGB(pX, pY, rgb);
			}
		}
	}

	public final static void trunImg(int type, BufferedImage srcImg,
			BufferedImage turnImage) {
		switch (type) {
		case 1:
			trunLeft90(srcImg, turnImage);
			break;
		case 2:
			trunRight90(srcImg, turnImage);
			break;
		case 3:
			trunLR(srcImg, turnImage);
			break;
		case 4:
			break;
		default:
			break;
		}
	}

	public final static BufferedImage createFontImg(String name,
			String fontName, int width, int height) {
		BufferedImage artFontImg = new BufferedImage(width, height,
				BufferedImage.TYPE_INT_RGB);
		Graphics2D g2 = (Graphics2D) artFontImg.getGraphics();
		g2.setBackground(Color.WHITE);
		g2.clearRect(0, 0, width, height);
		Font font = new Font(fontName, Font.ITALIC, 108);
		g2.setFont(font);
		g2.setPaint(new Color(0, 0, 0));
		g2.draw3DRect(0, 0, width - 1, height - 1, true);
		g2.draw3DRect(1, 1, width - 3, height - 3, true);
		g2.draw3DRect(2, 2, width - 5, height - 5, true);
		g2.draw3DRect(3, 3, width - 7, height - 7, true);
		FontRenderContext context = g2.getFontRenderContext();
		Rectangle2D bounds = font.getStringBounds(name, context);
		double x = (width - bounds.getWidth()) / 2;
		double y = (height - bounds.getHeight()) / 2;
		double ascent = -bounds.getY();
		double baseY = y + ascent;
		g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,
				RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
		g2.drawString(name, (int) x, (int) baseY);
		g2.dispose();
		return artFontImg;
	}

}
