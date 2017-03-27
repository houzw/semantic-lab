package org.semlab.model;

import java.util.Date;

public class User {
    private Integer id;

    private String firstName;

    private String password;

    /**
     * 盐
     * @mbggenerated
     */
    private String salt;

    private String email;

    private String lastName;

    private Date regDate;

    /**
     * 头衔
     * @mbggenerated
     */
    private String title;

    /**
     * 部门
     * @mbggenerated
     */
    private String dept;

    /**
     * 研究领域
     * @mbggenerated
     */
    private String researchField;

    /**
     * 锁定
     * @mbggenerated
     */
    private Boolean locked;

    /**
     * @mbggenerated
     */
    public Integer getId() {
        return id;
    }

    /**
     * @mbggenerated
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * @mbggenerated
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * @mbggenerated
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName == null ? null : firstName.trim();
    }

    /**
     * @mbggenerated
     */
    public String getPassword() {
        return password;
    }

    /**
     * @mbggenerated
     */
    public void setPassword(String password) {
        this.password = password == null ? null : password.trim();
    }

    /**
     * @mbggenerated
     */
    public String getSalt() {
        return salt;
    }

    /**
     * @mbggenerated
     */
    public void setSalt(String salt) {
        this.salt = salt == null ? null : salt.trim();
    }

    /**
     * @mbggenerated
     */
    public String getEmail() {
        return email;
    }

    /**
     * @mbggenerated
     */
    public void setEmail(String email) {
        this.email = email == null ? null : email.trim();
    }

    /**
     * @mbggenerated
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * @mbggenerated
     */
    public void setLastName(String lastName) {
        this.lastName = lastName == null ? null : lastName.trim();
    }

    /**
     * @mbggenerated
     */
    public Date getRegDate() {
        return regDate;
    }

    /**
     * @mbggenerated
     */
    public void setRegDate(Date regDate) {
        this.regDate = regDate;
    }

    /**
     * @mbggenerated
     */
    public String getTitle() {
        return title;
    }

    /**
     * @mbggenerated
     */
    public void setTitle(String title) {
        this.title = title == null ? null : title.trim();
    }

    /**
     * @mbggenerated
     */
    public String getDept() {
        return dept;
    }

    /**
     * @mbggenerated
     */
    public void setDept(String dept) {
        this.dept = dept == null ? null : dept.trim();
    }

    /**
     * @mbggenerated
     */
    public String getResearchField() {
        return researchField;
    }

    /**
     * @mbggenerated
     */
    public void setResearchField(String researchField) {
        this.researchField = researchField == null ? null : researchField.trim();
    }

    /**
     * @mbggenerated
     */
    public Boolean getLocked() {
        return locked;
    }

    /**
     * @mbggenerated
     */
    public void setLocked(Boolean locked) {
        this.locked = locked;
    }
}