package com.standardstate.couch4j.response;

import com.standardstate.couch4j.util.Utils;

public class Information {

    private Integer committed_update_seq = 0;
    private String db_name = null;
    private Integer doc_count = 0;
    private Integer doc_del_count = 0;
    private Integer update_seq = 0;
    private Integer purge_seq = 0;
    private Boolean compact_running = false;
    private Integer data_size = 0;
    private Integer disk_size = 0;
    private Long instance_start_time = 0l;
    private Integer disk_format_version = 0;
    private String couchdb = null;
    private String version = null;

    public Integer getCommitted_update_seq() {
        return committed_update_seq;
    }

    public void setCommitted_update_seq(final Integer committed_update_seq) {
        this.committed_update_seq = committed_update_seq;
    }

    public String getDb_name() {
        return db_name;
    }

    public void setDb_name(final String db_name) {
        this.db_name = db_name;
    }

    public Integer getDoc_count() {
        return doc_count;
    }

    public void setDoc_count(final Integer doc_count) {
        this.doc_count = doc_count;
    }

    public Integer getDoc_del_count() {
        return doc_del_count;
    }

    public void setDoc_del_count(final Integer doc_del_count) {
        this.doc_del_count = doc_del_count;
    }

    public Integer getUpdate_seq() {
        return update_seq;
    }

    public void setUpdate_seq(final Integer update_seq) {
        this.update_seq = update_seq;
    }

    public Integer getPurge_seq() {
        return purge_seq;
    }

    public void setPurge_seq(final Integer purge_seq) {
        this.purge_seq = purge_seq;
    }

    public Boolean isCompact_running() {
        return compact_running;
    }

    public void setCompact_running(final Boolean compact_running) {
        this.compact_running = compact_running;
    }

    public Integer getDisk_size() {
        return disk_size;
    }

    public void setDisk_size(final Integer disk_size) {
        this.disk_size = disk_size;
    }

    public Long getInstance_start_time() {
        return instance_start_time;
    }

    public void setInstance_start_time(final Long instance_start_time) {
        this.instance_start_time = instance_start_time;
    }

    public Integer getDisk_format_version() {
        return disk_format_version;
    }

    public void setDisk_format_version(final Integer disk_format_version) {
        this.disk_format_version = disk_format_version;
    }

    public Integer getData_size() {
        return data_size;
    }

    public void setData_size(Integer data_size) {
        this.data_size = data_size;
    }

    public String getCouchdb() {
        return couchdb;
    }

    public void setCouchdb(String couchdb) {
        this.couchdb = couchdb;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }
    
    @Override
    public String toString() {
        return Utils.objectToJSON(this);
    }
    
}
