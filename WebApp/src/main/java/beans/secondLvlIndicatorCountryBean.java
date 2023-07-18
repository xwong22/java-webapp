package beans;

import java.util.ArrayList;
import java.util.List;

public class secondLvlIndicatorCountryBean {
	
	private String[] queryCountry;
	private List<secondLvlIndicatorBean> resultByCountry = new ArrayList<>();
	
	public String[] getQueryCountry() {
		return queryCountry;
	}
	
	public void setQueryCountry(String[] queryCountry) {
		this.queryCountry = queryCountry;
	}
	
	public int getSize() {
		return this.resultByCountry.size();
	}
	
	public secondLvlIndicatorBean get(int i) {
		return this.resultByCountry.get(i);
	}
	
	public void add(secondLvlIndicatorBean record) {
		this.resultByCountry.add(record);
	}
	
	public void clear() {
		this.resultByCountry.clear();
	}
}
