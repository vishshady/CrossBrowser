package common;

import static org.rendersnake.HtmlAttributesFactory.border;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.rendersnake.HtmlCanvas;
import org.rendersnake.Renderable;

public class WriteDataToHtml {

	public void writeHtml(Map<Integer, Object[]> data2) throws IOException {
		Map<Integer, Object[]> data = new HashMap<Integer, Object[]>();
		data = data2;
		Set<Integer> keyset = data.keySet();
		HtmlCanvas html = new HtmlCanvas();
		html.html().body().h1().content("My HTML").table(border("1"));
		for (Integer key : keyset) {
			Object[] objArr = data.get(key);
			html.tr();
			for (Object ob : objArr) {
				html.td().content(ob.toString());
			}
			html._tr();
		}
		html._table()._body()._html();

		System.out.println(html.toHtml());
		Writer write = new BufferedWriter(new OutputStreamWriter(
				new FileOutputStream("filename.html"), "utf-8"));
		write.write(html.toHtml());
		write.close();
	}

}
