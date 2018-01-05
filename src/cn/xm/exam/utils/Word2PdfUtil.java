package cn.xm.exam.utils;

import java.io.File;
import java.io.IOException;

import com.jacob.activeX.ActiveXComponent;
import com.jacob.com.Dispatch;

/**
 * word转pdf的工具
 * 
 * @author QiaoLiQiang
 * @time 2018年1月4日下午2:18:33
 */
public class Word2PdfUtil {

	static final int wdDoNotSaveChanges = 0;// 不保存待定的更改。
	static final int wdFormatPDF = 17;// word转PDF 格式

	public static void main(String[] args) throws IOException {
		String source1 = "C:\\Users\\liqiang\\Desktop\\考试试卷.doc";
		String target1 = "C:\\Users\\liqiang\\Desktop\\考试试卷.pdf";
		Word2PdfUtil.word2pdf(source1, target1);
		System.out.println("22222");
	}

	/**
	 * 
	 * @param source
	 *            word路径
	 * @param target
	 *            生成的pdf路径
	 * @return
	 */
	public static boolean word2pdf(String source, String target) {
		System.out.println("Word转PDF开始启动...");
		long start = System.currentTimeMillis();
		ActiveXComponent app = null;
		try {
			app = new ActiveXComponent("Word.Application");
			app.setProperty("Visible", false);
			Dispatch docs = app.getProperty("Documents").toDispatch();
			System.out.println("打开文档：" + source);
			Dispatch doc = Dispatch.call(docs, "Open", source, false, true).toDispatch();
			System.out.println("转换文档到PDF：" + target);
			File tofile = new File(target);
			if (tofile.exists()) {
				tofile.delete();
			}
			Dispatch.call(doc, "SaveAs", target, wdFormatPDF);
			Dispatch.call(doc, "Close", false);
			long end = System.currentTimeMillis();
			System.out.println("转换完成，用时：" + (end - start) + "ms");
			return true;
		} catch (Exception e) {
			System.out.println("Word转PDF出错：" + e.getMessage());
			return false;
		} finally {
			if (app != null) {
				app.invoke("Quit", wdDoNotSaveChanges);
			}
		}
	}

}