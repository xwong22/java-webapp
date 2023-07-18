package beans;

import java.util.ArrayList;
import java.util.List;

public class firstLvlIndicatorBean {
	
	List<firstLvlIndicator> firstLvlIndicatorList = new ArrayList<>();
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
		return this.firstLvlIndicatorList.size();
	}
	
	public firstLvlIndicator get(int i) {
		return this.firstLvlIndicatorList.get(i);
	}
	
	public void add(firstLvlIndicator record) {
		this.firstLvlIndicatorList.add(record);
	}
	
	public void clear() {
		this.firstLvlIndicatorList.clear();
	}
	
	public List<firstLvlIndicator> getFirstLvlIndicatorList() {
		return firstLvlIndicatorList;
	}

}
