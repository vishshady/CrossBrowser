package crossBrowser;

import java.io.IOException;
import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;
import java.util.SortedSet;

import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.json.JSONException;
import org.json.JSONObject;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import common.BrowserDataGrabber;
import common.Utilities;
import common.WebdriverManager;
import common.WriteDataToHtml;
import common.WriteExcelData;

public class Html {
	RemoteWebDriver d;
	Map<Integer, Object[]> data;
	Map<Integer, Object[]> refBrowser = new LinkedHashMap<Integer, Object[]>();
	Map<Integer, Object[]> matchedBrowser = new LinkedHashMap<Integer, Object[]>();
	Map<Integer, Object[]> noIDMatched = new LinkedHashMap<Integer, Object[]>();
	Utilities u = new Utilities();
	WriteExcelData writeEx = new WriteExcelData();
	int count = 0;
	final String path = "C:\\Users\\vish\\workspace\\Vish_Shady\\Data.xls";

	@DataProvider(name = "test1")
	public Object[][] createData1() {
		return new Object[][] { { "firefox" }, { "googlechrome" } };
	}

	@BeforeMethod()
	public void setUp() {
		System.out.println("Start");
		data = new HashMap<Integer, Object[]>();
	}

	@AfterMethod
	public void tearDown() {
		WebdriverManager.stopDriver();
		if (count == 0) {
			Set<Integer> keyset = data.keySet();

			for (Integer key : keyset) {
				Object[] objArr = data.get(key);
				if (!objArr[0].equals("script"))
					refBrowser.put(key, new Object[] { objArr[0], objArr[1],
							objArr[2], objArr[3], objArr[4], objArr[5],
							objArr[6], objArr[7],objArr[8] });
			}
			writeEx.writeData(path, "MySheet", refBrowser);
		} else {
			Set<Integer> keyset1 = data.keySet();
			Set<Integer> keyset = refBrowser.keySet();
			for (Integer key : keyset) {
				Object[] objArr = refBrowser.get(key);
				for (Integer key1 : keyset1) {
					Object[] objArr1 = data.get(key1);

					if (!objArr1[0].equals("script"))
						if (!objArr[1].toString().isEmpty()) {
							if (Utilities.compare(objArr[1], objArr1[1]))
								matchedBrowser.put(key, new Object[] { objArr[0], objArr[1],
										objArr[2], objArr[3], objArr[4], objArr[5],
										objArr[6], objArr[7],objArr[8] });
						} else {

						}
				}
			}
		}
		count++;
	}

	@Test(dataProvider = "test1")
	public void actual(String browser) throws JSONException,
			JsonParseException, JsonMappingException, IOException {
		WebdriverManager.setupDriver(browser);
		d = WebdriverManager.getDriverInstance();
		data = u.dataCollections(d);
		System.out.println(count);
	}

}
