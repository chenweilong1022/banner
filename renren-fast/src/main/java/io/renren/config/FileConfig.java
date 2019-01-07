package io.renren.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix="file.upload")
public class FileConfig {

    private String baseurl;
    private String saveurl;


    public String getBaseurl() {
        return baseurl;
    }

    public void setBaseurl(String baseurl) {
        this.baseurl = baseurl;
    }

    public String getSaveurl() {
        return saveurl;
    }

    public void setSaveurl(String saveurl) {
        this.saveurl = saveurl;
    }
}
