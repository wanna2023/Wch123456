package com.xyu.Vo;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.xyu.Vo.DataVo;
import java.util.List;
public class AjaxResult {
    private String status;
    private List<DataVo> result;
    // 默认构造函数
    public AjaxResult() {
    }
    // 带参数的构造函数
    public AjaxResult(String status, List<DataVo> result) {
        this.status = status;
        this.result = result;
    }
    // Getter 和 Setter 方法
    public String getStatus() {
        return status;
    }
    public void setStatus(String status) {
        this.status = status;
    }
    public List<DataVo> getResult() {
        return result;
    }
    public void setResult(List<DataVo> result) {
        this.result = result;
    }
    // toString 方法，方便打印查看对象内容
    @Override
    public String toString() {
        return "AjaxResult{" +
                "status='" + status + '\'' +
                ", result=" + result +
                '}';
    }

}
