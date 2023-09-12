package com.example.demo.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.util.Objects;

@Entity
@Table(name = "medias")
public class Media {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String name;
  private String pid;
  private String cid;
  private String rawUrl;

  public Media() {
  }

  public Media(Long id, String name, String pid, String cid, String rawUrl) {
    this.id = id;
    this.name = name;
    this.pid = pid;
    this.cid = cid;
    this.rawUrl = rawUrl;
  }

  public Long getId() {
    return this.id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getName() {
    return this.name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getPid() {
    return this.pid;
  }

  public void setPid(String pid) {
    this.pid = pid;
  }

  public String getCid() {
    return this.cid;
  }

  public void setCid(String cid) {
    this.cid = cid;
  }

  public String getRawUrl() {
    return this.rawUrl;
  }

  public void setRawUrl(String rawUrl) {
    this.rawUrl = rawUrl;
  }

  public Media id(Long id) {
    setId(id);
    return this;
  }

  public Media name(String name) {
    setName(name);
    return this;
  }

  public Media pid(String pid) {
    setPid(pid);
    return this;
  }

  public Media cid(String cid) {
    setCid(cid);
    return this;
  }

  public Media rawUrl(String rawUrl) {
    setRawUrl(rawUrl);
    return this;
  }

  @Override
  public boolean equals(Object o) {
    if (o == this)
      return true;
    if (!(o instanceof Media)) {
      return false;
    }
    Media media = (Media) o;
    return Objects.equals(id, media.id) && Objects.equals(name, media.name) && Objects.equals(pid, media.pid)
        && Objects.equals(cid, media.cid) && Objects.equals(rawUrl, media.rawUrl);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, name, pid, cid, rawUrl);
  }

  @Override
  public String toString() {
    return "{" +
        " id='" + getId() + "'" +
        ", name='" + getName() + "'" +
        ", pid='" + getPid() + "'" +
        ", cid='" + getCid() + "'" +
        ", rawUrl='" + getRawUrl() + "'" +
        "}";
  }

}
