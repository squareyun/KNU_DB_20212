package category;

public class CategoryDTO {
	private int cid;
	private String cname;
	
	public int getCid() {
		return cid;
	}
	public void setCid(int cid) {
		this.cid = cid;
	}
	public String getCname() {
		return cname;
	}
	public void setCname(String cname) {
		this.cname = cname;
	}
	public String getCimg() {
		return cimg;
	}
	public void setCimg(String cimg) {
		this.cimg = cimg;
	}
	private String cimg;
}
