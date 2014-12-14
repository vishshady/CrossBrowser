package crossBrowser;

import static org.rendersnake.HtmlAttributesFactory.border;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;

import org.rendersnake.HtmlCanvas;
import org.rendersnake.Renderable;

public class SampleHTML implements Renderable {

	public static void main(String[] args) throws IOException {
		HtmlCanvas html = new HtmlCanvas();
		html.html().body().h1().content("My HTML").table(border("1")).tr().th()
				.content("UserName").th().content("Password")._tr();
		System.out.println("do something");
		html.tr().td().content("myname").td().content("mypass")._tr()._table()
				._body()._html();

		System.out.println(html.toHtml());
		Writer write = new BufferedWriter(new OutputStreamWriter(
				new FileOutputStream("filename.html"), "utf-8"));
		write.write(html.toHtml());
		write.close();
	}

	public void renderOn(HtmlCanvas arg0) throws IOException {
		// TODO Auto-generated method stub

	}

}
