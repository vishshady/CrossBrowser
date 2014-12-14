package common;

public class ElementProperties {
	private String id;

	private String name;

	private String className;

	private String enabled;

	private String displayed;

	private String coordinate;

	private String childelementcount;

	private String type;

	private String width;

	private String height;

	private String xpath;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	public String getEnabled() {
		return enabled;
	}

	public void setEnabled(String enabled) {
		this.enabled = enabled;
	}

	public String getDisplayed() {
		return displayed;
	}

	public void setDisplayed(String displayed) {
		this.displayed = displayed;
	}

	public String getCoordinate() {
		return coordinate;
	}

	public void setCoordinate(String coordinate) {
		this.coordinate = coordinate;
	}

	public String getChildelementcount() {
		return childelementcount;
	}

	public void setChildelementcount(String childelementcount) {
		this.childelementcount = childelementcount;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getWidth() {
		return width;
	}

	public void setWidth(String width) {
		this.width = width;
	}

	public String getHeight() {
		return height;
	}

	public void setHeight(String height) {
		this.height = height;
	}

	public String getXpath() {
		return xpath;
	}

	public void setXpath(String xpath) {
		this.xpath = xpath;
	}

	@Override
	public String toString() {
		return "ElementProperties [id=" + id + ",name=" + name + ",className="
				+ className + ", enabled=" + enabled + ", " + "displayed="
				+ displayed + ", coordinate=" + coordinate
				+ ", childelementcount=" + childelementcount + ", type=" + type
				+ ", width=" + width + ", height=" + height + ", xpath="
				+ xpath + "]";
	}
}
