package beans;

import java.util.ArrayList;
import java.util.List;

public class secondLvlIndicatorBean {
	List<secondLvlIndicator> secondLvlIndicatorList = new ArrayList<>();
	String searchCondition;
	String indicatorName;
	
	public String getIndicatorName() {
		return indicatorName;
	}
	
	public void setIndicatorName(String indicatorName) {
		this.indicatorName = indicatorName;
	}
	
	public String getSearchCondition() {
		return searchCondition;
	}
	
	public void setSearchCondition(String searchCondition) {
		this.searchCondition = searchCondition;
	}
	
	public int getSize() {
		return this.secondLvlIndicatorList.size();
	}
	
	public secondLvlIndicator get(int i) {
		return this.secondLvlIndicatorList.get(i);
	}
	
	public void add(secondLvlIndicator record) {
		this.secondLvlIndicatorList.add(record);
	}
	
	public void clear() {
		this.secondLvlIndicatorList.clear();
	}
	
	public List<secondLvlIndicator> getSecondLvlIndicatorList() {
		return secondLvlIndicatorList;
	}

}
