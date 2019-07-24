package mschu.books.dto;

public class Meta {
	private int totalCount;
	private int pageableCount;
	private boolean isEnd;
	
	public int getTotalCount() {
		return totalCount;
	}
	public void setTotalCount(int totalCount) {
		this.totalCount = totalCount;
	}
	public int getPageableCount() {
		return pageableCount;
	}
	public void setPageableCount(int pageableCount) {
		this.pageableCount = pageableCount;
	}
	public boolean isEnd() {
		return isEnd;
	}
	public void setEnd(boolean isEnd) {
		this.isEnd = isEnd;
	}
}
