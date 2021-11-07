package category;

public class CategoryDTO {
	private int cid;
	private String cname;
	
	public CategoryDTO(int cid, String cname) {	
		this.cid = cid;
		this.cname = cname;
	}

	public int getCid() {
		return cid;
	}

	public String getCname() {
		return cname;
	}

	@Override
	public String toString() {
		return cid + ". " + cname;
	}
}
