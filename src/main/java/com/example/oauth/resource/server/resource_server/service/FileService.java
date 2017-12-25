package com.example.oauth.resource.server.resource_server.service;

import com.example.oauth.resource.server.resource_server.ApplicationProperties;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.Optional;

@Service
public class FileService {

    private final ApplicationProperties applicationProperties;


    public FileService(ApplicationProperties applicationProperties) {
        this.applicationProperties = applicationProperties;
    }

    /**
     * 删除指定文件
     * @param name
     */
    public void deleteImage(String name){
        Optional<File> file=Optional.of(new File(name));
        file.ifPresent(file1 -> file1.delete());
    }

    /**
     * 添加指定文件
     * @param name
     */
    public void  addImage(String name){

    }
}
