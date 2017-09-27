package org.semlab.model;

import lombok.Data;

import java.util.Date;

@Data
public class User {
    private Integer id;

    private String firstName;

    private String password;

    /**
     * 盐
     *
     * @mbggenerated
     */
    private String salt;

    private String email;

    private String lastName;

    private Date regDate;

    /**
     * 头衔
     *
     * @mbggenerated
     */
    private String title;

    /**
     * 部门
     *
     * @mbggenerated
     */
    private String dept;

    /**
     * 研究领域
     *
     * @mbggenerated
     */
    private String researchField;

    /**
     * 锁定
     *
     * @mbggenerated
     */
    private Boolean locked;

}