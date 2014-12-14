package common;

import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.json.JSONException;
import org.json.JSONObject;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebDriver;

public class Utilities {

	public String getElementPosition(WebElement e) {
		return e.getLocation().toString();
	}

	public Dimension getElementSize(WebElement e) {
		return e.getSize();
	}

	public int getWidth(WebElement e) {
		return getElementSize(e).getWidth();
	}

	public int getHeight(WebElement e) {
		return getElementSize(e).getHeight();
	}

	public String[] getElementType(String s, String s1, String s2, WebElement e) {
		String[] properties = new String[1];
		if (!isStringEmpty(s)) {
			properties[0] = "ID : ";
			properties[1] = s;
		} else if (!isStringEmpty(s1)) {
			properties[0] = "Name : ";
			properties[1] = s1;
		} else if (e.getTagName().contentEquals("a"))
			properties[0] = e.getTagName() + " " + e.getText();
		else if (!isStringEmpty(s2))
			properties[0] = "Class : " + s2;
		else
			properties[0] = e.getTagName();
		return properties;
	}

	public String getType(WebElement e) {
		String s = "";
		if (!isStringEmpty(e.getAttribute("type")))
			s = e.getAttribute("type");
		return s;
	}

	public void highlightElement(RemoteWebDriver d, WebElement element) {
		for (int i = 0; i < 2; i++) {
			JavascriptExecutor js = (JavascriptExecutor) d;
			js.executeScript(
					"arguments[0].setAttribute('style', arguments[1]);",
					element, "color: yellow; border: 2px solid yellow;");
			js.executeScript(
					"arguments[0].setAttribute('style', arguments[1]);",
					element, "");
		}
	}

	public Boolean checkElementDisplayed(WebElement element) {
		boolean elementDisplayed = false;
		try {
			if (element.isDisplayed())
				elementDisplayed = true;
		} catch (NoSuchElementException e) {
			elementDisplayed = false;
		}
		return elementDisplayed;
	}

	public Boolean checkElementEnabled(WebElement element) {
		boolean elementEnabled = false;
		try {
			if (element.isEnabled())
				elementEnabled = true;
		} catch (NoSuchElementException e) {
			elementEnabled = false;
		}
		return elementEnabled;
	}

	public String getChildElementCount(WebElement element) {
		return element.getAttribute("childElementCount");
	}

	public String getElementID(WebElement element) {
		String s = null;
		try {
			if (!isStringEmpty(element.getAttribute("id").toString()))
				s = element.getAttribute("id").toString();
		} catch (NullPointerException e) {
			s = null;
		}
		return s;
	}

	public String getElementName(WebElement element) {
		String s = null;
		try {
			if (!isStringEmpty(element.getAttribute("name").toString()))
				s = element.getAttribute("name").toString();
		} catch (NullPointerException e) {
			s = null;
		}
		return s;
	}

	public String getElementClass(WebElement element) {
		String s = null;
		try {
			if (!isStringEmpty(element.getAttribute("class").toString()))
				s = element.getAttribute("class").toString();
		} catch (NullPointerException e) {
			s = null;
		}
		return s;
	}

	public boolean isStringEmpty(String s) {
		boolean status = false;
		try {
			if (s == null || s.isEmpty())
				status = true;
		} catch (NullPointerException e) {
			status = false;
		}
		return status;
	}

	public JSONObject collectElementData(WebElement element, RemoteWebDriver d)
			throws JSONException {
		JSONObject elementData = new JSONObject();
		elementData.put("id", getElementID(element));
		elementData.put("name", getElementName(element));
		elementData.put("className", getElementClass(element));
		elementData.put("xpath", XpathFinder.getAbsoluteXPath(element, d));
		elementData.put("displayed", checkElementDisplayed(element));
		elementData.put("enabled", checkElementEnabled(element));
		elementData.put("childelementcount", getChildElementCount(element));
		elementData.put("coordinate", getElementPosition(element));
		elementData.put("width", getWidth(element));
		elementData.put("height", getHeight(element));
		return elementData;
	}

	public float verifyPossible(String tagName1, String tagName2,
			String eleDisplayed1, String eleDisplayed2, String eleEnabled1,
			String eleEnabled2, String xpath1, String xpath2) {
		float totalRating = (getTagNameRating(tagName1, tagName2)
				+ getDisplayedRating(eleDisplayed1, eleDisplayed2)
				+ getEnabledRating(eleEnabled1, eleEnabled2) + getXpathRating(
				xpath1, xpath2)) / 4;
		return totalRating;
	}

	public int getTagNameRating(String tagName1, String tagName2) {
		if (Utilities.compare(tagName1, tagName2))
			return 1;
		return 0;
	}

	public int getDisplayedRating(String eleDisplayed1, String eleDisplayed2) {
		if (Utilities.compare(eleDisplayed1, eleDisplayed2))
			return 1;
		return 0;
	}

	public int getEnabledRating(String eleEnabled1, String eleEnabled2) {
		if (Utilities.compare(eleEnabled1, eleEnabled2))
			return 1;
		return 0;
	}

	public float getXpathRating(String xpath1, String xpath2) {
		String[] array1 = xpath1.split("/");
		String[] array2 = xpath2.split("/");
		float avg = array2.length + array1.length;
		float count = 0;
		if (array1 != null && array2 != null) {
			for (int i = 0; i < array1.length; i++) {
				outerLoop: for (int j = 0; j < array2.length; j++) {
					if (Utilities.compare(array1[i], array2[j])) {
						count++;
						break outerLoop;
					}
				}
			}
		} else {
			avg = 0;
		}
		return avg = count * 2 / avg;
	}

	public Map<Integer, Object[]> dataCollections(RemoteWebDriver d)
			throws JSONException, JsonParseException, JsonMappingException,
			IOException {
		Map<Integer, Object[]> dataCollection = new LinkedHashMap<Integer, Object[]>();
		List<WebElement> el = d.findElements(By.cssSelector("*"));
		int count = 0;
		try {
			JSONObject ob = null;
			for (WebElement e : el) {
				if (getWidth(e) != 0) {
					ob = collectElementData(e, d);
					ObjectMapper mapper = new ObjectMapper();
					String parse = ob.toString();
					ElementProperties properties = mapper.readValue(parse,
							ElementProperties.class);
					String[] values = getElementType(properties.getId(),
							properties.getName(),
							properties.getClassName(), e);
					dataCollection.put(
							count,
							new Object[] {
									e.getTagName(),
									values[0],
									values[1],
									getType(e),
									properties.getXpath(),
									properties.getDisplayed(),
									properties.getEnabled(),
									properties.getChildelementcount(),
									properties.getCoordinate(),
									properties.getWidth() + "*"
											+ properties.getHeight() });
				}
				count++;
			}
		} catch (StaleElementReferenceException e) {

		}
		System.out.println("count" + count);
		return dataCollection;
	}

	public static boolean compare(Object objArr, Object objArr1) {
		return (objArr == null ? objArr1 == null : objArr.equals(objArr1));
	}

	public Map<Integer, Object[]> check() {
		Map<Integer, Object[]> dataCollection = new HashMap<Integer, Object[]>();
		dataCollection
				.put(1, new Object[] { "div1", "id1", "true1", "xpath1" });
		dataCollection
				.put(2, new Object[] { "div2", "id2", "true2", "xpath2" });
		dataCollection
				.put(3, new Object[] { "div3", "id3", "true3", "xpath3" });
		dataCollection
				.put(4, new Object[] { "div4", "id4", "true4", "xpath4" });
		dataCollection
				.put(5, new Object[] { "div5", "id5", "true5", "xpath5" });
		dataCollection
				.put(6, new Object[] { "div6", "id6", "true6", "xpath6" });
		return dataCollection;
	}

	public Map<Integer, Object[]> check1() {
		Map<Integer, Object[]> dataCollection = new HashMap<Integer, Object[]>();
		dataCollection
				.put(1, new Object[] { "div1", "id9", "true1", "xpath1" });
		dataCollection
				.put(2, new Object[] { "div2", "id3", "true2", "xpath2" });
		dataCollection.put(3, new Object[] { "div3", "id", "true3", "xpath3" });
		dataCollection.put(4,
				new Object[] { "div4", "id11", "true4", "xpath4" });
		dataCollection
				.put(5, new Object[] { "div5", "id6", "true5", "xpath5" });
		dataCollection
				.put(6, new Object[] { "div6", "id7", "true6", "xpath6" });
		return dataCollection;
	}

	public void computeMatchingRating() {

	}

}
