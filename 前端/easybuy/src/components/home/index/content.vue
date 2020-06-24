<template>
  <div class="index_content">
    <div class="block" v-if="isHome">
      <el-row :gutter="24">
        <el-col :span="18">
          <el-carousel height="400px">
            <el-carousel-item v-for="(item,index) in slideshow" :key="index">
              <img :src="item" width="100%" />
            </el-carousel-item>
          </el-carousel>
        </el-col>
        <el-col :span="6">
          <div class="news">
            <p>
              <span>新闻资讯</span>
              <small style="float:right;">
                <a href="/newsList" style=" text-decoration: none;color:#555454">更多></a>
              </small>
            </p>
            <ul style="list-style:none;font-size:12px;padding:20px">
              <li v-for="item in news" :key="item.id">
                <span>[公告]</span>
                <span @click="handleNews(item.title,item.content)">{{item.title}}</span>
              </li>
            </ul>
          </div>
          <div class="new">
            <div class="newsTitle">
              <p>{{title}}</p>
            </div>
            <div class="newsContent">
              <p style="font-size:12px">{{content}}</p>
            </div>
          </div>
        </el-col>
      </el-row>
    </div>
    <div v-else>
      <div v-if="product">
        <ul style="list-style:none;">
          <li v-for="item in product" :key="item.id" class="product" @click="intoInfo(item.id)">
            <div style="display: flex;align-items: center;">
              <img
                :src="'http://39.105.42.250:8080/product/image/'+item.fileName"
                width="150"
                height="150"
                style="margin:0 auto;"
              />
            </div>
            <h3 style="color:red;">{{'￥'+item.price+'.0'}}</h3>
            <p style="color:#999999">{{item.name}}</p>
            <el-button>
              <i class="el-icon-star-off"></i>
              收藏
            </el-button>
            <el-button type="danger" plain @click.stop="addCart(item.id,item.price)">
              <i class="el-icon-plus"></i>
              加入购物车
            </el-button>
          </li>
        </ul>
      </div>
      <p v-else style="color:#999999;text-align:center;">暂无数据</p>
    </div>
  </div>
</template>
<script>
export default {
  created() {
    this.getNewsList();
  },
  data() {
    return {
      slideshow: [
        require("../../../assets/homePage/轮播图.jpg"),
        require("../../..//assets/homePage/轮播图.jpg"),
        require("../../..//assets/homePage/轮播图.jpg")
      ],
      news: [],
      title: "",
      content: "",
      dialogVisible: true
    };
  },
  props: {
    isHome: {
      type: Boolean,
      default: true
    },
    product: {
      type: Array,
      default: []
    }
  },
  methods: {
    handleNews(title, content) {
      this.title = title;
      this.content = content;
    },
    async getNewsList() {
      let resData = new URLSearchParams();
      resData.append("from", 0);
      resData.append("pageSize", 5);
      const { data: res } = await this.$http.post(
        "news/queryNewsList",
        resData
      );
      if (res.code != 200) {
        this.$message.error(res.message);
      } else {
        this.news = res.data;
        this.title = res.data[0].title;
        this.content = res.data[0].content;
      }
    },
    intoInfo(id) {
      // console.log(id);
      this.$router.replace("/goodsDetail");
      sessionStorage.setItem("goodsId", id);
    },
    async addCart(id, price) {
      let userId = sessionStorage.getItem("user");
      userId = userId.split("/");
      userId = userId[1];
      let resData = new URLSearchParams();
      resData.append("productId", id);
      resData.append("userId", userId);
      resData.append("price", price);
      resData.append("quantity", 1);
      const { data: res } = await this.$http.post(
        "shoppingCart/insert",
        resData
      );
      if (res.code != 200) {
        console.log("添加失败");
      } else {
        this.$message.success("添加成功");
      }
    }
  }
};
</script>
<style scoped>
.product {
  float: left;
  padding: 5px;
  margin: 5px;
  border: 1px solid #fff;
}
.product:hover {
  border: 1px solid #e0e0e0;
}
.news {
  border: 1px solid #e0e0e0;
}
.news p {
  font-size: 17px;
  border-bottom: 1px solid #e0e0e0;
  padding: 15px;
  margin: 0;
}
.news li span {
  margin-right: 10px;
  cursor: pointer;
}
.news ul {
  margin: 0;
}
.news ul li {
  padding: 10px 0;
  color: #666666;
}
.new {
  border: 1px solid #e0e0e0;
}
.newsTitle {
  padding:0px 10px;
  border-bottom: 1px solid #e0e0e0;
}
.newsContent {
  height: 100px;
  padding:0px 10px;
  overflow-y: scroll;
}
</style>