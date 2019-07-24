package mschu.books.db;

import java.util.Date;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "keywords")
public class Keywords{

	@EmbeddedId
    private KeywordsIdentity identity;
	
	private Date issue;
	private int search;
	
	public KeywordsIdentity getIdentity() {
		return identity;
	}
	public void setIdentity(KeywordsIdentity identity) {
		this.identity = identity;
	}
	public Date getIssue() {
		return issue;
	}
	public void setIssue(Date issue) {
		this.issue = issue;
	}
	public int getSearch() {
		return search;
	}
	public void setSearch(int search) {
		this.search = search;
	}
}
