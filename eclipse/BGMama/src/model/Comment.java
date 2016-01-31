package model;

public class Comment {
	
	private String commentId;
	private String commentCategory;
	private String commentText;
	
	public Comment(String commentId, String commentCategory, String commentText) {
		setCommentId(commentId);
		setCommentText(commentText);
		setCommentCategory(commentCategory);
	}
	
	public String getCommentId() {
		return commentId;
	}
	public void setCommentId(String commentId) {
		this.commentId = commentId;
	}
	public String getCommentCategory() {
		return commentCategory;
	}
	public void setCommentCategory(String commentCategory) {
		this.commentCategory = commentCategory;
	}
	public String getCommentText() {
		return commentText;
	}
	public void setCommentText(String commentText) {
		this.commentText = commentText;
	}

	@Override
	public String toString() {
		return "\n[commentId=" + commentId + "\n commentCategory=" + commentCategory + "\n commentText="
				+ commentText + "]";
	}

}
