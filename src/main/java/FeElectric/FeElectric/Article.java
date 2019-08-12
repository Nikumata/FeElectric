package FeElectric.FeElectric;

import java.util.ArrayList;
import java.util.List;

public class Article {

	private String category;
	private String index;
	private String monthDay;
	private String year;
	private String title;
	private String summary;
	private List<String[]> triples;

	public Article(String category, String index, String monthDay, String year, String title, String summary) {
		this.category = category;
		this.index = index;
		this.monthDay = monthDay;
		this.year = year;
		this.title = title;
		this.summary = summary;
		this.triples = new ArrayList<>();
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getMonthDay() {
		return monthDay;
	}

	public void setMonthDay(String monthDay) {
		this.monthDay = monthDay;
	}

	public String getYear() {
		return year;
	}

	public void setYear(String year) {
		this.year = year;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getSummary() {
		return summary;
	}

	public void setSummary(String summary) {
		this.summary = summary;
	}

	public String getIndex() {
		return index;
	}

	public void setIndex(String index) {
		this.index = index;
	}

	public List<String[]> getTriples() {
		return triples;
	}

	public void setTriples(List<String[]> triples) {
		this.triples = triples;
	}

}
