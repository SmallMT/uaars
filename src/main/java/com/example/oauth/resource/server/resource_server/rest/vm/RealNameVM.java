package com.example.oauth.resource.server.resource_server.rest.vm;


import com.example.oauth.resource.server.resource_server.validator.FileNotEmptyConstraint;
import com.example.oauth.resource.server.resource_server.validator.FileSizeConstraint;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Nullable;
import javax.validation.constraints.NotNull;

public class RealNameVM {

    @Nullable
    private Integer id;

    @NotNull(message = "真实姓名不为空")
    private String name;

    @NotNull(message = "身份证ID不为空")
    private String identity;


    @FileNotEmptyConstraint(message = "身份证正面照不能为空")
    @FileSizeConstraint
    private MultipartFile frontFile;


    @FileNotEmptyConstraint(message = "身份证反面照不能为空")
    @FileSizeConstraint
    private MultipartFile backFile;

    @FileNotEmptyConstraint(message = "手持身份证照片不能为空")
    @FileSizeConstraint
    private MultipartFile selfieFile;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIdentity() {
        return identity;
    }

    public void setIdentity(String identity) {
        this.identity = identity;
    }

    public MultipartFile getFrontFile() {
        return frontFile;
    }

    public void setFrontFile(MultipartFile frontFile) {
        this.frontFile = frontFile;
    }

    public MultipartFile getBackFile() {
        return backFile;
    }

    public void setBackFile(MultipartFile backFile) {
        this.backFile = backFile;
    }

    public MultipartFile getSelfieFile() {
        return selfieFile;
    }

    public void setSelfieFile(MultipartFile selfieFile) {
        this.selfieFile = selfieFile;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
