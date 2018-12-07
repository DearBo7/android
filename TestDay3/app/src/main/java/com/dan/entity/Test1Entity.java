package com.dan.entity;

import java.math.BigDecimal;

/**
 * Created by Dan on 2018/9/17 10:16
 */
public class Test1Entity {

    /**
     * 标题
     */
    private String title;

    /**
     * 图片 Id
     */

    private int imageId;

    /**
     * 评分
     */
    private BigDecimal score;

    /**
     * 集数
     */
    private int episode;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public BigDecimal getScore() {
        return score;
    }

    public void setScore(BigDecimal score) {
        this.score = score;
    }

    public int getImageId() {
        return imageId;
    }

    public void setImageId(int imageId) {
        this.imageId = imageId;
    }

    public int getEpisode() {
        return episode;
    }

    public void setEpisode(int episode) {
        this.episode = episode;
    }

    public Test1Entity() {
    }

    public Test1Entity(String title, int imageId, BigDecimal score, int episode) {
        this.title = title;
        this.imageId = imageId;
        this.score = score;
        this.episode = episode;
    }

    @Override
    public String toString() {
        return "Test1Entity{" +
                "title='" + title + '\'' +
                ", imageId=" + imageId +
                ", score=" + score +
                ", episode=" + episode +
                '}';
    }
}
