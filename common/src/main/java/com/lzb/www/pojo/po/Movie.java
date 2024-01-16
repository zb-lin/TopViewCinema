package com.lzb.www.pojo.po;

import com.lzb.www.util.type.annotation.NotEmpty;
import com.lzb.www.util.type.annotation.NotNull;
import com.lzb.www.util.type.annotation.Range;
import lorm.annotation.ColumnName;
import lorm.annotation.TableName;

import java.time.LocalDateTime;

@TableName("tb_movie")
public class Movie {
    /**
     * 电影id(自增)
     */
    private Integer id;
    /**
     * 电影名称
     */
    @NotEmpty
    private String name;
    /**
     * 导演
     */
    @NotEmpty
    private String director;
    /**
     * 主演
     */
    @NotEmpty
    @ColumnName("leading_role")
    private String leadingRole;
    /**
     * 类型
     */
    @NotEmpty
    private String type;
    /**
     * 制片国家
     */
    @NotEmpty
    @ColumnName("production_country")
    private String productionCountry;
    /**
     * 电影时长 单位分钟 比如 100分钟
     */
    @NotNull
    @ColumnName("the_time_of_movie")
    private Integer theTimeOfMovie;
    /**
     * 海报路径
     */
    @NotEmpty
    private String poster;
    /**
     * 上映时间  (大概时间)
     */
    @NotNull
    @ColumnName("release_time")
    private LocalDateTime releaseTime;
    /**
     * 价格
     */
    @NotNull
    @Range(min = 0)
    private Integer price;
    /**
     * 评分
     */
    private Integer evaluate;
    /**
     * 评分人数
     */
    @ColumnName("evaluate_count")
    private Integer evaluateCount;

    public Movie(Integer id, String name, String director, String leadingRole, String type,
                 String productionCountry, Integer theTimeOfMovie, String poster, LocalDateTime releaseTime,
                 Integer price, Integer evaluate, Integer evaluateCount) {
        this.id = id;
        this.name = name;
        this.director = director;
        this.leadingRole = leadingRole;
        this.type = type;
        this.productionCountry = productionCountry;
        this.theTimeOfMovie = theTimeOfMovie;
        this.poster = poster;
        this.releaseTime = releaseTime;
        this.price = price;
        this.evaluate = evaluate;
        this.evaluateCount = evaluateCount;
    }

    public Movie() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDirector() {
        return director;
    }

    public void setDirector(String director) {
        this.director = director;
    }

    public String getLeadingRole() {
        return leadingRole;
    }

    public void setLeadingRole(String leadingRole) {
        this.leadingRole = leadingRole;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getProductionCountry() {
        return productionCountry;
    }

    public void setProductionCountry(String productionCountry) {
        this.productionCountry = productionCountry;
    }

    public Integer getTheTimeOfMovie() {
        return theTimeOfMovie;
    }

    public void setTheTimeOfMovie(Integer theTimeOfMovie) {
        this.theTimeOfMovie = theTimeOfMovie;
    }

    public String getPoster() {
        return poster;
    }

    public void setPoster(String poster) {
        this.poster = poster;
    }

    public LocalDateTime getReleaseTime() {
        return releaseTime;
    }

    public void setReleaseTime(LocalDateTime releaseTime) {
        this.releaseTime = releaseTime;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public Integer getEvaluate() {
        return evaluate;
    }

    public void setEvaluate(Integer evaluate) {
        this.evaluate = evaluate;
    }

    public Integer getEvaluateCount() {
        return evaluateCount;
    }

    public void setEvaluateCount(Integer evaluateCount) {
        this.evaluateCount = evaluateCount;
    }

    @Override
    public String toString() {
        return "Movie{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", director='" + director + '\'' +
                ", leadingRole='" + leadingRole + '\'' +
                ", type='" + type + '\'' +
                ", productionCountry='" + productionCountry + '\'' +
                ", theTimeOfMovie=" + theTimeOfMovie +
                ", poster='" + poster + '\'' +
                ", releaseTime=" + releaseTime +
                ", price=" + price +
                ", evaluate=" + evaluate +
                ", evaluateCount=" + evaluateCount +
                '}';
    }
}
