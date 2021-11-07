package reply;

public class ReplyDTO {
	private int rid;
	private int post_id;
	private int post_creator_id;
	private int creator_id;
	private String Contents;
	private String create_date;

	public ReplyDTO(int rid, int post_id, int post_creator_id, int creator_id, String contents, String create_date) {
		this.rid = rid;
		this.post_id = post_id;
		this.post_creator_id = post_creator_id;
		this.creator_id = creator_id;
		Contents = contents;
		this.create_date = create_date;
	}

	public int getRid() {
		return rid;
	}

	public void setRid(int rid) {
		this.rid = rid;
	}

	public int getPost_id() {
		return post_id;
	}

	public void setPost_id(int post_id) {
		this.post_id = post_id;
	}

	public int getPost_creator_id() {
		return post_creator_id;
	}

	public void setPost_creator_id(int post_creator_id) {
		this.post_creator_id = post_creator_id;
	}

	public int getCreator_id() {
		return creator_id;
	}

	public void setCreator_id(int creator_id) {
		this.creator_id = creator_id;
	}

	public String getContents() {
		return Contents;
	}

	public void setContents(String contents) {
		Contents = contents;
	}

	public String getCreate_date() {
		return create_date;
	}

	public void setCreate_date(String create_date) {
		this.create_date = create_date;
	}
}
