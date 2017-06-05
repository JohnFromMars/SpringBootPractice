package com.marsbase.springboot.model;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;
import javax.persistence.OrderColumn;
import javax.persistence.Table;
import javax.validation.constraints.Size;

import com.marsbase.springboot.util.StringFormatUtil;

@Entity
@Table(name = "profile")
public class Profile {

	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@OneToOne(targetEntity = SiteUser.class)
	@JoinColumn(name = "user_id", nullable = false)
	private SiteUser user;

	@Column(name = "about", length = 5000)
	@Size(max = 5000, message = "{edit.profile.size}")
	private String about;

	@Column(name = "photo_directory", length = 20)
	private String photoDirectory;

	@Column(name = "photo_name", length = 16)
	private String photoName;

	@Column(name = "photo_extension", length = 5)
	private String photoExtension;

	@ManyToMany(fetch = FetchType.EAGER)
	//@formatter:off
	@JoinTable(name = "profile_interests", 
	           joinColumns = {@JoinColumn(name = "profile_id") },
	           inverseJoinColumns = {@JoinColumn(name = "interest_id") })
	//@formatter:on
	@OrderColumn(name = "display_order")
	private Set<Interest> interests;

	public Profile() {
		this.interests = new HashSet<Interest>();
	}

	public Profile(SiteUser user) {
		this.user = user;
		this.interests = new HashSet<Interest>();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public SiteUser getUser() {
		return user;
	}

	public void setUser(SiteUser user) {
		this.user = user;
	}

	public String getAbout() {
		return about;
	}

	public void setAbout(String about) {
		this.about = about;
	}

	public String getPhotoDirectory() {
		return photoDirectory;
	}

	public void setPhotoDirectory(String photoDirectory) {
		this.photoDirectory = photoDirectory;
	}

	public String getPhotoName() {
		return photoName;
	}

	public void setPhotoName(String photoName) {
		this.photoName = photoName;
	}

	public String getPhotoExtension() {
		return photoExtension;
	}

	public void setPhotoExtension(String photoExtension) {
		this.photoExtension = photoExtension;
	}

	public Set<Interest> getInterests() {
		return interests;
	}

	public void setInterests(Set<Interest> interests) {
		this.interests = interests;
	}

	/**
	 * create a profile which has no user information that is suitable
	 * displaying on JSP.
	 * 
	 * @param other
	 */
	public void safeCopyFrom(Profile other) {

		if (other.about != null) {
			this.about = other.about;
		}

		if (other.interests != null) {
			this.interests = other.interests;
		}
	}

	/**
	 * create a profile without strange HTML or script that is suitable for
	 * saving database
	 * 
	 * @param other
	 * @param policyFactory
	 */
	public void safeMergeFrom(Profile other, StringFormatUtil stringFormatUtil) {
		if (other.about != null) {
			this.about = stringFormatUtil.sanitize(other.about);
		}
	}

	public void setPhotoDetail(FileInfo info) {
		photoDirectory = info.getSubDirectory();
		photoExtension = info.getExtension();
		photoName = info.getBaseName();

	}

	public Path getPhotoPath(String baseDirectory) {
		if (photoName == null) {
			return null;
		}

		Path path = Paths.get(baseDirectory, photoDirectory, photoName + "." + photoExtension);
		return path;
	}

	public void addInterest(Interest interest) {
		if (interest != null) {
			this.interests.add(interest);
		}
	}

	public void removeInterest(String interestName) {
		if (interestName != null) {
			interests.remove(new Interest(interestName));
		}
	}
}
