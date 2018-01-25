package com.example.oauth.resource.server.resource_server;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Properties specific to Jhipster.
 * <p>
 * Properties are configured in the application.yml file.
 * See {@link io.github.jhipster.config.JHipsterProperties} for a good example.
 */
@ConfigurationProperties(prefix = "application", ignoreUnknownFields = false)
public class ApplicationProperties {

    private  static  RealName realName=new RealName();

    private static BindEnterprise bindEnterprise=new BindEnterprise();

    public static class RealName {
        private static String filePath="";

        public String getFilePath() {
            return filePath;
        }
        public void setFilePath(String filePath){
            this.filePath=filePath;
        }
    }

    public RealName getRealName() {
        return realName;
    }

    public static class BindEnterprise{
        private static String filePath="";

        public String getFilePath() {
            return filePath;
        }
        public void setFilePath(String filePath){
            this.filePath=filePath;
        }
    }

    public BindEnterprise getBindEnterprise() {
        return bindEnterprise;
    }
}
