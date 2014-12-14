package crossBrowser;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import common.Utilities;

public class NewTest {
	Map<Integer, Object[]> data;
	Map<Integer, Object[]> refBrowser = new HashMap<Integer, Object[]>();
	Map<Integer, Object[]> matchedBrowser = new HashMap<Integer, Object[]>();
	Utilities u = new Utilities();
	int count = 0;

	@BeforeMethod
	public void a() {
		System.out.println("Start");
		data = new HashMap<Integer, Object[]>();
	}

	@AfterMethod
	public void b() {
		if (count == 0) {
			Set<Integer> keyset = data.keySet();
			for (Integer key : keyset) {
				Object[] objArr = data.get(key);
				refBrowser.put(key, new Object[] { objArr[0], objArr[1],
						objArr[2], objArr[3] });
			}
			Set<Integer> keyset2 = refBrowser.keySet();
			for (Integer key : keyset2) {
				Object[] objArr = refBrowser.get(key);
				System.out.println("ref browser " + "key, " + key + " value "
						+ objArr[0] + objArr[1] + objArr[2] + objArr[3]);
			}

		} else {
			Set<Integer> keyset1 = data.keySet();
			Set<Integer> keyset = refBrowser.keySet();
			for (Integer key : keyset) {
				Object[] objArr = refBrowser.get(key);
				for (Integer key1 : keyset1) {
					Object[] objArr1 = data.get(key1);
					if (Utilities.compare(objArr[1], objArr1[1]))
						matchedBrowser.put(key, new Object[] { objArr1[0],
								objArr1[1], objArr1[2], objArr1[3] });
				}
			}
			Set<Integer> keyset2 = matchedBrowser.keySet();
			for (Integer key : keyset2) {
				Object[] objArr = matchedBrowser.get(key);
				System.out.println("matchedBrowserr " + "key, " + key
						+ " value " + objArr[0] + objArr[1] + objArr[2]
						+ objArr[3]);
			}
		}

		count++;
	}

	@DataProvider(name = "test1")
	public Object[][] createData1() {
		return new Object[][] { { "firefox" }, { "googlechrome" } };
	}

	@Test(dataProvider = "test1")
	public void f(String a1) {
		System.out.println(a1);
		if (count == 0)
			data = u.check();
		else
			data = u.check1();
	}

	@AfterTest
	public void test() {
		System.out.println(u.getXpathRating(
				"/html/body/div[3]/div[2]/div[2]/div[2]/header/div/div[2]/div[2]/div/div/div/div[1]/h1/a",
				"/html/body/div[3]/div[2]/div[2]/div[2]/header/div/div[2]/div[2]"));	
	}
}
