package mschu.books.dto;

public class Books {
	private Meta meta;
	private BooksDocument document;
	
	public Meta getMeta() {
		return meta;
	}
	public void setMeta(Meta meta) {
		this.meta = meta;
	}
	public BooksDocument getDocument() {
		return document;
	}
	public void setDocument(BooksDocument document) {
		this.document = document;
	}
}
