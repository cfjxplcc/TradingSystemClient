package com.fjnu.trade.model;

/**
 * 人员表
 * <br>
 * Created by LCC on 2018/3/8.
 */
//Entity
//Table(name = "tb_person_info")
public class PersonInfo {

    //Id
    //GeneratedValue(generator = "uuid")
    //GenericGenerator(name = "uuid", strategy = "uuid2")
    //Column(name = "ID", length = 36)
    private String id;

    //Column(name = "LOGIN_NAME", length = 20, nullable = false, unique = true)
    private String loginName;

    //Column(name = "LOGIN_PASSWORD", length = 50, nullable = false)
    private String loginPassword;

    //Column(name = "PERSON_NAME", length = 20)
    private String personName;

    //Column(name = "LEVEL_ID", length = 36)
    private String level_id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }

    public String getLoginPassword() {
        return loginPassword;
    }

    public void setLoginPassword(String loginPassword) {
        this.loginPassword = loginPassword;
    }

    public String getPersonName() {
        return personName;
    }

    public void setPersonName(String personName) {
        this.personName = personName;
    }

    public String getLevel_id() {
        return level_id;
    }

    public void setLevel_id(String level_id) {
        this.level_id = level_id;
    }

    @Override
    public String toString() {
        return "PersonInfo{" +
                "id='" + id + '\'' +
                ", loginName='" + loginName + '\'' +
                ", loginPassword='" + loginPassword + '\'' +
                ", personName='" + personName + '\'' +
                ", level_id='" + level_id + '\'' +
                '}';
    }
}
