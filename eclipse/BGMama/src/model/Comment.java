package model;

public class Comment {
	
	private String commentId;
	private String commentCategoryText;
	private String commentText;
	
	public enum ClassType {
		ClassTypePositive,
		ClassTypeNegative,
		ClassTypeUnknown
	}
	
	private static final String positiveClass = "positive";
	private static final String negativeClass = "negative";
	
	public Comment(String commentId, String commentCategoryText, String commentText) {
		setCommentId(commentId);
		setCommentText(commentText);
		setCommentCategoryText(commentCategoryText);
	}
	
	public String getCommentId() {
		return commentId;
	}
	public void setCommentId(String commentId) {
		this.commentId = commentId;
	}
	public String getCommentCategoryText() {
		return commentCategoryText;
	}
	
	public ClassType getCommentCategory() {
		if (this.getCommentCategoryText().equals(positiveClass)) {
			return ClassType.ClassTypePositive;
		}
		
		if (this.getCommentCategoryText().equals(negativeClass)) {
			return ClassType.ClassTypeNegative;
		}
		
		return ClassType.ClassTypeUnknown;
	}
	
	public void setCommentCategoryText(String commentCategory) {
		this.commentCategoryText = commentCategory;
	}
	public String getCommentText() {
		return commentText;
	}
	public void setCommentText(String commentText) {
		this.commentText = commentText;
	}
	
	public boolean isPositive(){
		return getCommentCategory() == ClassType.ClassTypePositive;
	}
	
	public boolean isNegative(){
		return getCommentCategory() == ClassType.ClassTypeNegative;
	}

	@Override
	public String toString() {
		return "\n[commentId=" + commentId + "\n commentCategory=" + commentCategoryText + "\n commentText="
				+ commentText + "]";
	}

}
