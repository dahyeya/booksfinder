package mschu.books.db;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class KeywordsIdentity implements Serializable{
	private static final long serialVersionUID = 68536405132802030L;

	@Column(name = "id", nullable = false)
	private int id;
	
	@Column(name = "keyword", nullable = false)
	private String keyword;
	
	public KeywordsIdentity() {}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getKeyword() {
		return keyword;
	}
	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}
	public KeywordsIdentity(int id, String user) {
		super();
		this.id = id;
		this.keyword = user;
	}
}
