package cn.zjingsh.common.utils;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Arrays;
import java.util.List;

/**
 * Created by zjs on 2018/10/28.
 */
public class IRRCalUtil {

	// 牛顿法
	public static BigDecimal newtonCal(List<BigDecimal> cList, BigDecimal accuracy) {
		BigDecimal x = new BigDecimal("1.09");
		while (true) {
			BigDecimal x1 = x.subtract(newtonF(cList, x).divide(newtonDerF(cList, x), 20, RoundingMode.HALF_UP));
			if (x1.subtract(x).abs().compareTo(accuracy) < 0) break;
			x = x1;
		}

		return x;
	}

	private static BigDecimal newtonF(List<BigDecimal> cList, BigDecimal x) {
		BigDecimal sum = BigDecimal.ZERO;
		for (int i = 0; i < cList.size(); i++) {
			sum = sum.add(cList.get(i).divide(x.pow(i), 20, RoundingMode.HALF_UP));
		}
//		System.out.println("this f(" + x.toPlainString() + ") is: " + sum.toPlainString());
		return sum;
	}

	private static BigDecimal newtonDerF(List<BigDecimal> cList, BigDecimal x) {
		BigDecimal sum = BigDecimal.ZERO;
		for (int i = 0; i< cList.size(); i++) {
			sum = sum.subtract(cList.get(i).multiply(new BigDecimal(i)).divide(x.pow(i+1), 20, RoundingMode.HALF_UP));
		}
//		System.out.println("this f'(" + x.toPlainString() + ") is: " + sum.toPlainString());
		return sum;
	}

	// 二分法

	// 割线法,excel算法.gitignore

	// 试位法，线性插值法
	private static BigDecimal test(List<BigDecimal> cList, BigDecimal x) {
		BigDecimal sum = BigDecimal.ZERO;
		for (int i = 0; i< cList.size(); i++) {
			sum = sum.add(cList.get(i).divide(x.pow(i), 10, RoundingMode.HALF_UP));
		}
		return sum;
	}

	public static BigDecimal calYear(BigDecimal x) {
		return x.pow(12).subtract(BigDecimal.ONE);
	}

	public static final void main(String... args) {
		List<BigDecimal> cList = Arrays.asList(new BigDecimal("-1000"),
				new BigDecimal("220"), new BigDecimal("220"), new BigDecimal("220"),
				new BigDecimal("220"), new BigDecimal("220"));
		Long start = System.currentTimeMillis();
//		System.out.println(test(cList, new BigDecimal("1.03263495764132586764")));
		System.out.println(newtonCal(cList, new BigDecimal("0.000001")));
		Long end = System.currentTimeMillis();
		System.out.println("cost : " + (end - start));
//		System.out.println(calYear(new BigDecimal("1.03263495764132586764")));
	}
}
