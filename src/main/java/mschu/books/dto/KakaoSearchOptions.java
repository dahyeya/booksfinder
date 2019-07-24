package mschu.books.dto;

public class KakaoSearchOptions {

	private String query;
	private String sort; //accuracy (정확도순), latest (최신순) default accuracy
	private String page; // 1-100 default 1
	private String size; // 1-50 default 10
	private String target; //title (제목에서 검색), isbn (ISBN에서 검색), publisher (출판사에서 검색), person(인명에서 검색) default : none
	
	public String getQuery() {
		return query;
	}
	public void setQuery(String query) {
		this.query = query;
	}
	public String getSort() {
		return sort;
	}
	public void setSort(String sort) {
		this.sort = sort;
	}
	public String getPage() {
		return page;
	}
	public void setPage(String page) {
		this.page = page;
	}
	public String getSize() {
		return size;
	}
	public void setSize(String size) {
		this.size = size;
	}
	public String getTarget() {
		return target;
	}
	public void setTarget(String target) {
		this.target = target;
	}
	
	@Override
	public String toString() {
		StringBuffer strBuffer = new StringBuffer();
		if(query!=null) {
			strBuffer.append("?query=");
			strBuffer.append(query);
		}
		if(sort!=null) {
			strBuffer.append("&sort=");
			strBuffer.append(sort);
		}
		if(page!=null) {
			strBuffer.append("&page=");
			strBuffer.append(page);
		}
		if(size!=null) {
			strBuffer.append("&size=");
			strBuffer.append(size);
		}
		if(target!=null) {
			strBuffer.append("&target=");
			strBuffer.append(target);
		}
		return strBuffer.toString();
	}
}
