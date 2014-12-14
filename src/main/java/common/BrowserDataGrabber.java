package common;

import java.util.HashMap;

import org.json.JSONObject;

public interface BrowserDataGrabber {
	HashMap<Integer, JSONObject> dataCollection = new HashMap<Integer, JSONObject>();
}
