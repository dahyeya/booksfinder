package mschu.books.dto;

public class NaverSearchOptions {

	private String query;
	private String display; //1 - 100 default 10;
	private String start;  // 1 - 1000 default 1;
	private String sort; //sim (유사도), date (출간), count (판매량) default sim
	private String dTitl;
	private String dAuth;
	private String dCont;
	private String dIsbn;
	private String dPubl;
	private String dDafr;
	private String dDato;
	private String dCatg;
	
	public NaverSearchOptions() {}
	public NaverSearchOptions(KakaoSearchOptions options) {
		this.query = options.getQuery();
		this.display = options.getSize();
		this.start = String.valueOf((Integer.parseInt(options.getPage()) -1) * 10 + 1);
		
	}
	public String getQuery() {
		return query;
	}
	public void setQuery(String query) {
		this.query = query;
	}
	public String getDisplay() {
		return display;
	}
	public void setDisplay(String display) {
		this.display = display;
	}
	public String getStart() {
		return start;
	}
	public void setStart(String start) {
		this.start = start;
	}
	public String getSort() {
		return sort;
	}
	public void setSort(String sort) {
		this.sort = sort;
	}
	public String getdTitl() {
		return dTitl;
	}
	public void setdTitl(String dTitl) {
		this.dTitl = dTitl;
	}
	public String getdAuth() {
		return dAuth;
	}
	public void setdAuth(String dAuth) {
		this.dAuth = dAuth;
	}
	public String getdCont() {
		return dCont;
	}
	public void setdCont(String dCont) {
		this.dCont = dCont;
	}
	public String getdIsbn() {
		return dIsbn;
	}
	public void setdIsbn(String dIsbn) {
		this.dIsbn = dIsbn;
	}
	public String getdPubl() {
		return dPubl;
	}
	public void setdPubl(String dPubl) {
		this.dPubl = dPubl;
	}
	public String getdDafr() {
		return dDafr;
	}
	public void setdDafr(String dDafr) {
		this.dDafr = dDafr;
	}
	public String getdDato() {
		return dDato;
	}
	public void setdDato(String dDato) {
		this.dDato = dDato;
	}
	public String getdCatg() {
		return dCatg;
	}
	public void setdCatg(String dCatg) {
		this.dCatg = dCatg;
	}
	
	@Override
	public String toString() {
		StringBuffer strBuffer = new StringBuffer();
		if(query!=null) {
			strBuffer.append("?query=");
			strBuffer.append(query);
		}
		if(display!=null) {
			strBuffer.append("&display=");
			strBuffer.append(sort);
		}
		if(start!=null) {
			strBuffer.append("&start=");
			strBuffer.append(start);
		}
		
		return strBuffer.toString();
	}
}
