package com.example.threadtransaction.domain;

import java.util.Date;

/*
 * gen by beetlsql 2018-10-29
 */
public class User {
    //主键ID
    private Integer id ;
    //登录名
    private String loginName ;
    //密码
    private String loginPassword ;
    //用户名
    private String name ;
    //创建时间
    private Date createAt ;
    //修改时间
    private Date updateAt ;

    public User() {
    }

    /**主键ID
     *@return
     */
    public Integer getId(){
        return  id;
    }
    /**主键ID
     *@param  id
     */
    public void setId(Integer id ){
        this.id = id;
    }

    /**登录名
     *@return
     */
    public String getLoginName(){
        return  loginName;
    }
    /**登录名
     *@param  loginName
     */
    public void setLoginName(String loginName ){
        this.loginName = loginName;
    }

    /**密码
     *@return
     */
    public String getLoginPassword(){
        return  loginPassword;
    }
    /**密码
     *@param  loginPassword
     */
    public void setLoginPassword(String loginPassword ){
        this.loginPassword = loginPassword;
    }

    /**用户名
     *@return
     */
    public String getName(){
        return  name;
    }
    /**用户名
     *@param  name
     */
    public void setName(String name ){
        this.name = name;
    }

    /**创建时间
     *@return
     */
    public Date getCreateAt(){
        return  createAt;
    }
    /**创建时间
     *@param  createAt
     */
    public void setCreateAt(Date createAt ){
        this.createAt = createAt;
    }

    /**修改时间
     *@return
     */
    public Date getUpdateAt(){
        return  updateAt;
    }
    /**修改时间
     *@param  updateAt
     */
    public void setUpdateAt(Date updateAt ){
        this.updateAt = updateAt;
    }
}