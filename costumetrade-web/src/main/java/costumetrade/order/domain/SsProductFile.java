package costumetrade.order.domain;

import java.util.Date;

import costumetrade.common.Entity;

public class SsProductFile extends Entity {
    /**
     *  
     */
    private Integer id;

    /**
     *  
     */
    private String storeid;

    /**
     *  
     */
    private String productid;

    /**
     *  
     */
    private String filename;
    
    private String productName;

    /**
     *  
     */
    private String url;
    
    private String resizeFixUrl;
    /**
     *  
     */
    private Date createtime;

    /**
     *  
     */
    private String createby;
    
    private Integer pageNum;

    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getStoreid() {
        return storeid;
    }

    public void setStoreid(String storeid) {
        this.storeid = storeid;
    }

    public String getProductid() {
        return productid;
    }

    public void setProductid(String productid) {
        this.productid = productid;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename == null ? null : filename.trim();
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url == null ? null : url.trim();
    }

    public Date getCreatetime() {
        return createtime;
    }

    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
    }

    public String getCreateby() {
        return createby;
    }

    public void setCreateby(String createby) {
        this.createby = createby == null ? null : createby.trim();
    }

	public String getResizeFixUrl() {
		return resizeFixUrl;
	}

	public void setResizeFixUrl(String resizeFixUrl) {
		this.resizeFixUrl = resizeFixUrl;
	}



	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public Integer getPageNum() {
		return pageNum;
	}

	public void setPageNum(Integer pageNum) {
		this.pageNum = pageNum;
	}
    
    
}