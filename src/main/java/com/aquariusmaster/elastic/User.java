package com.aquariusmaster.elastic;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldIndex;
import org.springframework.data.elasticsearch.annotations.FieldType;

/**
 * Created by harkonnen on 15.07.16.
 */
@Document(indexName = "customers", type = "user")
public class User {
    @Id
    private long id;
    @Field(type = FieldType.String)
    private String gender;
    @Field(type = FieldType.String)
    private String first_name;
    @Field(type = FieldType.String)
    private String last_name;
    @Field(type = FieldType.String)
    private String email;
    @Field(type = FieldType.String)
    private String ip_address;

    public User(){}

    public User(String gender, String first_name, String last_name, String email, String ip_address) {
        this.gender = gender;
        this.first_name = first_name;
        this.last_name = last_name;
        this.email = email;
        this.ip_address = ip_address;
    }

    public User(long id, String gender, String first_name, String last_name, String email, String ip_address) {
        this.id = id;
        this.gender = gender;
        this.first_name = first_name;
        this.last_name = last_name;
        this.email = email;
        this.ip_address = ip_address;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getIp_address() {
        return ip_address;
    }

    public void setIp_address(String ip_address) {
        this.ip_address = ip_address;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        User user = (User) o;

        if (id != user.id) return false;
        if (gender != null ? !gender.equals(user.gender) : user.gender != null) return false;
        if (first_name != null ? !first_name.equals(user.first_name) : user.first_name != null) return false;
        if (last_name != null ? !last_name.equals(user.last_name) : user.last_name != null) return false;
        if (email != null ? !email.equals(user.email) : user.email != null) return false;
        return ip_address != null ? ip_address.equals(user.ip_address) : user.ip_address == null;

    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + (gender != null ? gender.hashCode() : 0);
        result = 31 * result + (first_name != null ? first_name.hashCode() : 0);
        result = 31 * result + (last_name != null ? last_name.hashCode() : 0);
        result = 31 * result + (email != null ? email.hashCode() : 0);
        result = 31 * result + (ip_address != null ? ip_address.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", gender='" + gender + '\'' +
                ", first_name='" + first_name + '\'' +
                ", last_name='" + last_name + '\'' +
                ", email='" + email + '\'' +
                ", ip_address='" + ip_address + '\'' +
                '}';
    }
}
