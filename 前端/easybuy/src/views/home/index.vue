<template>
  <div class="homePage" ref="homePage">
    <el-menu
      :default-active="activeIndex"
      class="el-menu-demo"
      mode="horizontal"
      @select="handleSelect"
    >
      <el-menu-item index="0">全部商品分类</el-menu-item>
      <el-menu-item index="-1">首页</el-menu-item>
      <el-menu-item
        v-for="item in productCategory"
        :index="item.id+'&'+item.type"
        :key="item.id"
      >{{item.name}}</el-menu-item>
    </el-menu>
    <el-container>
      <el-aside style="width:130px;">
        <el-menu
          v-if="!isSearch"
          :default-active="activeIndex2"
          class="el-menu-vertical-demo"
          @open="handleOpen"
          @close="handleClose"
          :collapse="true"
          @select="handleSelect"
        >
          <el-submenu
            v-for="parent in productCategory"
            :index="parent.id+'&'+parent.type"
            :key="parent.id"
          >
            <template slot="title">
              <i>{{parent.name}}</i>
            </template>
            <el-menu-item-group
              v-for="item in parent.children"
              :index="item.id+'&'+item.type"
              :key="item.id"
            >
              <span slot="title">{{item.name}}</span>
              <el-menu-item
                v-for="i in item.children"
                :index="i.id+'&'+i.type"
                :key="i.id"
              >{{i.name}}</el-menu-item>
            </el-menu-item-group>
          </el-submenu>
        </el-menu>
        <div v-else>
          <el-button>我的收藏</el-button>
        </div>
      </el-aside>
      <el-main>
        <index-content :isHome="isHome" :product="product"></index-content>
      </el-main>
    </el-container>
    <div v-if="isHome">
      <img :src="footerImg" width="100%" />
    </div>
    <div v-if="isHome">
      <div class="list" v-for="(item,index) in level1" :key="index">
        <div class="i_t mar_10">
          <span class="floor_num">{{index+1}}F</span>
          <span class="fl">{{item.name}}</span>
        </div>
        <div class="listmain">
          <div class="aside">
            <img src="../../assets/img/fre_r.jpg" width="211" height="286" />
            <div></div>
          </div>
          <div class="main">
            <div class="list-row1">
              <div
                class="list-item"
                v-for="item in categoryData[index]"
                :key="item.id"
                @click="intoGoodsInfo(item.id)"
              >
                <div class="name">
                  <a href="javascript:void(0)">{{item.name}}</a>
                </div>
                <div class="price">
                  <font>
                    ￥
                    <span>{{item.price}}</span>
                  </font> &nbsp;
                </div>
                <div class="img">
                  <img
                    :src="'http://39.105.42.250:8080/product/image/'+item.fileName"
                    width="185"
                    height="155"
                  />
                </div>
              </div>
            </div>
            <div class="rIMG">
              <img src="../../assets/homePage/相约过中秋.jpg" width="260" height="220" />
              <img src="../../assets/homePage/进口猕猴桃.jpg" width="260" height="220" />
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>
<script>
import indexContent from "../../components/home/index/content";
import indexFooter from "../../components/home/index/footer.vue";
export default {
  data() {
    return {
      categoryData: [],
      level1: [],
      productCategory: [],
      activeIndex: "-1",
      activeIndex2: "",
      product: [],
      isHome: true,
      isSearch: false,
      footerImg: require("../../assets/homePage/footer.jpg")
    };
  },
  created() {
    this.setProCategory();
    this.getLevel1();
  },
  watch: {
    "$parent.$data.isSearch": function(newVal) {
      if (newVal) {
        this.isSearch = newVal;
        this.product = this.$parent.$data.product;
        this.isHome = false;
        this.activeIndex = "-2";
      }
    }
  },
  methods: {
    intoGoodsInfo(id) {
      //   console.log(id);
      this.$router.replace("/goodsDetail");
      sessionStorage.setItem("goodsId", id);
    },
    async getLevel1() {
      let resData = new URLSearchParams();
      resData.append("parentId", 0);
      const { data: res } = await this.$http.post(
        "productCategory/queryListByParentId",
        resData
      );
      if (res.code != 200) {
        console.log("错误");
      } else {
        this.level1 = res.data;
        for (let i in this.level1) {
          let res2Data = new URLSearchParams();
          res2Data.append("categoryId", this.level1[i].id);
          res2Data.append("level", 1);
          res2Data.append("from", 0);
          res2Data.append("pageSize", 6);
          const { data: res2 } = await this.$http.post(
            "product/getProductList",
            res2Data
          );
          if (res2.code != 200) {
            console.log("错误");
          } else {
            this.categoryData.push(res2.data);
          }
        }
        // console.log(this.categoryData);
      }
    },
    async setProCategory() {
      try {
        let res = await this.$http.post("productCategory/getAll");
        if (res.status === 200) {
          if (res.data.code === 200) {
            // console.log(res.data.data);
            this.productCategory = res.data.data;
            this.activeIndex2 = this.productCategory[0].name;
            if (this.productCategory != null) {
              let r = this.$refs.homePage.children;
              //   console.log(r);
            }
          } else {
            this.$message.error({
              message: res.data.message
            });
          }
        } else {
          this.$message.error({
            message: "请求错误"
          });
        }
      } catch (err) {
        console.log(err);
      }
    },
    async setProduct() {
      try {
        let res = await this.$axios.get("");
        if (res.status === 200) {
          if (res.data.code === 200) {
          } else {
            this.$message.error({
              message: res.data.message
            });
          }
        } else {
          this.$message.error({
            message: "请求错误"
          });
        }
      } catch (err) {
        console.log(err);
      }
    },
    handleOpen(key, keyPath) {
      //   console.log(key, keyPath);
    },
    handleClose(key, keyPath) {
      //   console.log(key, keyPath);
    },
    handleSelect(key, keyPath) {
      this.isSearch = false;
      this.$parent.$data.isSearch = false;
      this.$parent.$data.state = "";

      let cId = key.split("&")[0];
      let level = key.split("&")[1];
      let form = new FormData();
      form.append("from", "0");
      form.append("pageSize", "100");
      if (cId === "-1") {
        //如果选择的是首页则跳转首页
        this.isHome = true;
      } else {
        this.isHome = false;
        if (cId != "0") {
          //否则查询产品
          form.append("categoryId", cId);
          form.append("level", level);
        }
        this.setProduct(form);
      }
    },
    async setProduct(form) {
      try {
        let res = await this.$http.post("product/getProductList", form);
        if (res.status === 200) {
          if (res.data.code === 200) {
            this.product = res.data.data;
          } else {
            this.$message.error({
              message: res.data.message
            });
          }
        } else {
          this.$message.error({
            message: "请求错误"
          });
        }
      } catch (err) {
        console.log(err);
      }
    }
  },
  components: {
    indexContent,
    indexFooter
  }
};
</script>
<style >
.el-menu--collapse {
  width: 125px;
}

.i_t {
  width: 1190px;
  height: 39px;
  line-height: 39px;
  overflow: hidden;
  color: #409eff;
  font-size: 18px;
  padding: 0 5px;
  border-bottom: 2px solid #409eff;
}
.floor_num {
  width: 33px;
  height: 34px;
  line-height: 40px;
  overflow: hidden;
  background: url("../../assets/img/floor.png") no-repeat center bottom;
  color: #fff;
  font-size: 14px;
  text-transform: uppercase;
  float: left;
  display: inline;
  margin-right: 15px;
  text-indent: 7px;
}
.i_t {
  width: 1190px;
  height: 39px;
  line-height: 39px;
  overflow: hidden;
  color: #409eff;
  font-size: 18px;
  padding: 0 5px;
  border-bottom: 2px solid #409eff;
}
.listmain {
  display: flex;
  flex-direction: row;
  align-items: flex-start;
}
.listmain .main {
  display: flex;
  flex-direction: row;
  align-items: center;
}
.list-row1 {
  display: flex;
  flex-direction: row;
  align-items: center;
  width: 729px;
  overflow: hidden;
  flex-wrap: wrap;
}
.list-item {
  width: 242px;
  height: 220px;
  overflow: hidden;
  float: left;
  border-left: 1px solid #eaeaea;
  border-bottom: 1px solid #eaeaea;
  display: flex;
  flex-direction: column;
  align-items: center;
}
.name {
  height: 20px;
  line-height: 20px;
  overflow: hidden;
  font-size: 14px;
  margin-top: 12px;
}
.price {
  height: 20px;
  line-height: 20px;
  overflow: hidden;
  color: #999999;
  text-transform: uppercase;
}
.img {
  width: 188px;
  height: 155px;
  overflow: hidden;
  margin-top: 5px;
}
.rIMG {
  display: flex;
  flex-direction: column;
  align-items: center;
}
a {
  text-decoration: none;
  color: #000;
}
.price {
  color: #409eff;
  font-size: 19px;
  text-indent: 15px;
}
</style>