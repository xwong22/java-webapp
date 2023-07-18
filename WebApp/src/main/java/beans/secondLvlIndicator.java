package beans;

import java.math.BigDecimal;

public class secondLvlIndicator {
	
	private int id;
	private String country;
	private int year;
	private long count;
	private BigDecimal standardizedCount;
	
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public int getYear() {
		return year;
	}

	public void setYear(int year) {
		this.year = year;
	}

	public long getCount() {
		return count;
	}

	public void setCount(long count) {
		this.count = count;
	}

	public BigDecimal getStandardizedCount() {
		return standardizedCount;
	}

	public void setStandardizedCount(BigDecimal standardizedCount) {
		this.standardizedCount = standardizedCount;
	}

}
