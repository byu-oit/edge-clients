package edu.byu.edge.wsClient.domain;

public class StudentRatingOutcome {

	protected String id;
	protected String outcomeHTML;
	protected String ownerNetID;
	protected String ownerType;
	protected String title;

	/**
	 * See {@link StudentRatingOutcome#id}
	 * 
	 * @return the id
	 */
	public String getId() {
		return id;
	}

	/**
	 * See {@link StudentRatingOutcome#id}
	 * 
	 * @param id
	 *            the id to set
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * See {@link StudentRatingOutcome#outcomeHTML}
	 * 
	 * @return the outcomeHtml
	 */
	public String getOutcomeHTML() {
		return outcomeHTML;
	}

	/**
	 * See {@link StudentRatingOutcome#outcomeHTML}
	 * 
	 * @param outcomeHtml
	 *            the outcomeHtml to set
	 */
	public void setOutcomeHTML(String outcomeHtml) {
		this.outcomeHTML = outcomeHtml;
	}

	/**
	 * See {@link StudentRatingOutcome#ownerNetID}
	 * 
	 * @return the ownerNetId
	 */
	public String getOwnerNetID() {
		return ownerNetID;
	}

	/**
	 * See {@link StudentRatingOutcome#ownerNetID}
	 * 
	 * @param ownerNetID
	 *            the ownerNetId to set
	 */
	public void setOwnerNetID(String ownerNetID) {
		this.ownerNetID = ownerNetID;
	}

	/**
	 * See {@link StudentRatingOutcome#ownerType}
	 * 
	 * @return the ownerType
	 */
	public String getOwnerType() {
		return ownerType;
	}

	/**
	 * See {@link StudentRatingOutcome#ownerType}
	 * 
	 * @param ownerType
	 *            the ownerType to set
	 */
	public void setOwnerType(String ownerType) {
		this.ownerType = ownerType;
	}

	/**
	 * See {@link StudentRatingOutcome#title}
	 * 
	 * @return the title
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * See {@link StudentRatingOutcome#title}
	 * 
	 * @param title
	 *            the title to set
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((outcomeHTML == null) ? 0 : outcomeHTML.hashCode());
		result = prime * result + ((ownerNetID == null) ? 0 : ownerNetID.hashCode());
		result = prime * result + ((ownerType == null) ? 0 : ownerType.hashCode());
		result = prime * result + ((title == null) ? 0 : title.hashCode());
		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (!(obj instanceof StudentRatingOutcome)) {
			return false;
		}
		StudentRatingOutcome other = (StudentRatingOutcome) obj;
		if (id == null) {
			if (other.id != null) {
				return false;
			}
		} else if (!id.equals(other.id)) {
			return false;
		}
		if (outcomeHTML == null) {
			if (other.outcomeHTML != null) {
				return false;
			}
		} else if (!outcomeHTML.equals(other.outcomeHTML)) {
			return false;
		}
		if (ownerNetID == null) {
			if (other.ownerNetID != null) {
				return false;
			}
		} else if (!ownerNetID.equals(other.ownerNetID)) {
			return false;
		}
		if (ownerType == null) {
			if (other.ownerType != null) {
				return false;
			}
		} else if (!ownerType.equals(other.ownerType)) {
			return false;
		}
		if (title == null) {
			if (other.title != null) {
				return false;
			}
		} else if (!title.equals(other.title)) {
			return false;
		}
		return true;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "StudentRatingOutcome [" + (id != null ? "id=" + id + ", " : "")
				+ (outcomeHTML != null ? "outcomeHtml=" + outcomeHTML + ", " : "")
				+ (ownerNetID != null ? "ownerNetId=" + ownerNetID + ", " : "")
				+ (ownerType != null ? "ownerType=" + ownerType + ", " : "") + (title != null ? "title=" + title : "") + "]";
	}

}
