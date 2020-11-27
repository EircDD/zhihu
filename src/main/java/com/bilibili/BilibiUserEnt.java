package com.bilibili;

import java.util.List;

public class BilibiUserEnt {

    /**
     * code : 0
     * message : 0
     * ttl : 1
     * data : {"list":{"vlist":[{"comment":2623,"typeid":124,"play":1498735,"pic":"//i1.hdslb.com/bfs/archive/efac798b01131bea543d8c962d3b91c4a5564b64.jpg","subtitle":"","description":"用过就走，才是游戏的真谛","copyright":"","title":"【半佛】游戏的大脚踩在直男的嗨点上","review":0,"author":"硬核的半佛仙人","mid":37663924,"created":1593684011,"length":"11:58","video_review":8078,"aid":796126409,"bvid":"BV1cC4y1Y74g","hide_click":false,"is_pay":0,"is_union_video":0,"is_steins_gate":0}]},"page":{"pn":3,"ps":30,"count":124},"episodic_button":{"text":"播放全部","uri":"//www.bilibili.com/medialist/play/37663924?from=space"}}
     */

    private int code;
    private String message;
    private int ttl;
    private DataBean data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getTtl() {
        return ttl;
    }

    public void setTtl(int ttl) {
        this.ttl = ttl;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {

        /**
         * list : {"vlist":[{"comment":2623,"typeid":124,"play":1498735,"pic":"//i1.hdslb.com/bfs/archive/efac798b01131bea543d8c962d3b91c4a5564b64.jpg","subtitle":"","description":"用过就走，才是游戏的真谛","copyright":"","title":"【半佛】游戏的大脚踩在直男的嗨点上","review":0,"author":"硬核的半佛仙人","mid":37663924,"created":1593684011,"length":"11:58","video_review":8078,"aid":796126409,"bvid":"BV1cC4y1Y74g","hide_click":false,"is_pay":0,"is_union_video":0,"is_steins_gate":0}]}
         * page : {"pn":3,"ps":30,"count":124}
         * episodic_button : {"text":"播放全部","uri":"//www.bilibili.com/medialist/play/37663924?from=space"}
         */

        private ListBean list;
        private PageBean page;
        private EpisodicButtonBean episodic_button;

        public ListBean getList() {
            return list;
        }

        public void setList(ListBean list) {
            this.list = list;
        }

        public PageBean getPage() {
            return page;
        }

        public void setPage(PageBean page) {
            this.page = page;
        }

        public EpisodicButtonBean getEpisodic_button() {
            return episodic_button;
        }

        public void setEpisodic_button(EpisodicButtonBean episodic_button) {
            this.episodic_button = episodic_button;
        }

        public static class ListBean {

            private List<VlistBean> vlist;

            public List<VlistBean> getVlist() {
                return vlist;
            }

            public void setVlist(List<VlistBean> vlist) {
                this.vlist = vlist;
            }

            public static class VlistBean {

                /**
                 * comment : 2623
                 * typeid : 124
                 * play : 1498735
                 * pic : //i1.hdslb.com/bfs/archive/efac798b01131bea543d8c962d3b91c4a5564b64.jpg
                 * subtitle :
                 * description : 用过就走，才是游戏的真谛
                 * copyright :
                 * title : 【半佛】游戏的大脚踩在直男的嗨点上
                 * review : 0
                 * author : 硬核的半佛仙人
                 * mid : 37663924
                 * created : 1593684011
                 * length : 11:58
                 * video_review : 8078
                 * aid : 796126409
                 * bvid : BV1cC4y1Y74g
                 * hide_click : false
                 * is_pay : 0
                 * is_union_video : 0
                 * is_steins_gate : 0
                 */

                private int comment;
                private int typeid;
                private int play;
                private String pic;
                private String subtitle;
                private String description;
                private String copyright;
                private String title;
                private int review;
                private String author;
                private int mid;
                private int created;
                private String length;
                private int video_review;
                private int aid;
                private String bvid;
                private boolean hide_click;
                private int is_pay;
                private int is_union_video;
                private int is_steins_gate;
                private String playUrl;

                public int getComment() {
                    return comment;
                }

                public void setComment(int comment) {
                    this.comment = comment;
                }

                public int getTypeid() {
                    return typeid;
                }

                public void setTypeid(int typeid) {
                    this.typeid = typeid;
                }

                public int getPlay() {
                    return play;
                }

                public void setPlay(int play) {
                    this.play = play;
                }

                public String getPic() {
                    return pic;
                }

                public void setPic(String pic) {
                    this.pic = pic;
                }

                public String getSubtitle() {
                    return subtitle;
                }

                public void setSubtitle(String subtitle) {
                    this.subtitle = subtitle;
                }

                public String getDescription() {
                    return description;
                }

                public void setDescription(String description) {
                    this.description = description;
                }

                public String getCopyright() {
                    return copyright;
                }

                public void setCopyright(String copyright) {
                    this.copyright = copyright;
                }

                public String getTitle() {
                    return title;
                }

                public void setTitle(String title) {
                    this.title = title;
                }

                public int getReview() {
                    return review;
                }

                public void setReview(int review) {
                    this.review = review;
                }

                public String getAuthor() {
                    return author;
                }

                public void setAuthor(String author) {
                    this.author = author;
                }

                public int getMid() {
                    return mid;
                }

                public void setMid(int mid) {
                    this.mid = mid;
                }

                public int getCreated() {
                    return created;
                }

                public void setCreated(int created) {
                    this.created = created;
                }

                public String getLength() {
                    return length;
                }

                public void setLength(String length) {
                    this.length = length;
                }

                public int getVideo_review() {
                    return video_review;
                }

                public void setVideo_review(int video_review) {
                    this.video_review = video_review;
                }

                public int getAid() {
                    return aid;
                }

                public void setAid(int aid) {
                    this.aid = aid;
                }

                public String getBvid() {
                    return bvid;
                }

                public void setBvid(String bvid) {
                    this.bvid = bvid;
                }

                public boolean isHide_click() {
                    return hide_click;
                }

                public void setHide_click(boolean hide_click) {
                    this.hide_click = hide_click;
                }

                public int getIs_pay() {
                    return is_pay;
                }

                public void setIs_pay(int is_pay) {
                    this.is_pay = is_pay;
                }

                public int getIs_union_video() {
                    return is_union_video;
                }

                public void setIs_union_video(int is_union_video) {
                    this.is_union_video = is_union_video;
                }

                public int getIs_steins_gate() {
                    return is_steins_gate;
                }

                public void setIs_steins_gate(int is_steins_gate) {
                    this.is_steins_gate = is_steins_gate;
                }

                public String getPlayUrl() {
                    return playUrl;
                }

                public void setPlayUrl(String playUrl) {
                    this.playUrl = playUrl;
                }
            }
        }

        public static class PageBean {

            /**
             * pn : 3
             * ps : 30
             * count : 124
             */

            private int pn;
            private int ps;
            private int count;

            public int getPn() {
                return pn;
            }

            public void setPn(int pn) {
                this.pn = pn;
            }

            public int getPs() {
                return ps;
            }

            public void setPs(int ps) {
                this.ps = ps;
            }

            public int getCount() {
                return count;
            }

            public void setCount(int count) {
                this.count = count;
            }
        }

        public static class EpisodicButtonBean {

            /**
             * text : 播放全部
             * uri : //www.bilibili.com/medialist/play/37663924?from=space
             */

            private String text;
            private String uri;

            public String getText() {
                return text;
            }

            public void setText(String text) {
                this.text = text;
            }

            public String getUri() {
                return uri;
            }

            public void setUri(String uri) {
                this.uri = uri;
            }
        }
    }
}