<template>
  <div>
    <div class="goodsInfo">
      <div class="demo-image__preview">
        <el-image
          style="width: 390px; height: 390px"
          :src="'http://39.105.42.250:8080/product/image/'+goodsData.fileName"
          :preview-src-list="srcList"
        ></el-image>
      </div>
      <div class="info">
        <div>
          <h3>{{goodsData.name}}</h3>
          <p style="color: #888888;font-size: 14px;">“开业巨惠，北京专柜直供”，不光低价，“真”才靠谱！</p>
        </div>
        <p style="font-size: 12px;">
          本店价格：
          <span style="color: #EEB422;font-size: 20px;">￥{{goodsData.price}}</span>
        </p>
        <p style="font-size: 12px;">
          库存：
          <span style="color: #EEB422;font-size: 20px;">{{goodsData.stock}}</span>
        </p>
        <div>
          <p style="color: #888888;font-size: 14px;">型号选择：</p>
          <el-radio label="1" v-model="type" border>30ml</el-radio>
          <el-radio label="2" v-model="type" border>50ml</el-radio>
        </div>
        <div>
          <p style="color: #888888;font-size: 14px;">颜色选择</p>
          <el-radio label="1" v-model="color" border>白色</el-radio>
          <el-radio label="2" v-model="color" border>红色</el-radio>
        </div>
      </div>
    </div>
    <div class="button">
      <el-input-number v-model="quantity" :min="1" :max="goodsData.stock" size="mini"></el-input-number>
      <el-button type="primary" style="margin-left:20px" @click="addCart()">加入购物车</el-button>
    </div>
    <el-tabs type="border-card">
      <el-tab-pane label="商品属性">
        <el-form label-position="left" inline class="demo-table-expand">
          <el-form-item label="商品名称">
            <span>{{ goodsData.name }}</span>
          </el-form-item>
          <el-form-item label="商品价格">
            <span>{{ goodsData.price }}</span>
          </el-form-item>
          <el-form-item label="品牌">
            <span>迪奥(Dior)</span>
          </el-form-item>
          <el-form-item label="上架时间">
            <span>2015-09-06 09:19:09</span>
          </el-form-item>
          <el-form-item label="商品毛重">
            <span>160.00g</span>
          </el-form-item>
          <el-form-item label="商品产地">
            <span>法国</span>
          </el-form-item>
          <el-form-item label="香调">
            <span>果香调香型：淡香水/香露EDT</span>
          </el-form-item>
          <el-form-item label="容量">
            <span>1ml-15ml</span>
          </el-form-item>
          <el-form-item label="类型">
            <span>女士香水，Q版香水，组合套装</span>
          </el-form-item>
        </el-form>
      </el-tab-pane>
      <el-tab-pane label="商品详情">
        <p>{{goodsData.description}}</p>
        <img :src="'http://39.105.42.250:8080/product/image/'+goodsData.fileName" width="100" height="100" />
      </el-tab-pane>
    </el-tabs>
  </div>
</template>
<script>
export default {
  created() {
    this.goodsID = sessionStorage.getItem("goodsId");
    this.getGoodsData();
    let id = sessionStorage.getItem("user");
    id = id.split("/");
    id = id[1];
    this.userID = id;
  },

  data() {
    return {
      url: "",
      srcList: [
        "https://fuss10.elemecdn.com/8/27/f01c15bb73e1ef3793e64e6b7bbccjpeg.jpeg",
        "https://fuss10.elemecdn.com/1/8e/aeffeb4de74e2fde4bd74fc7b4486jpeg.jpeg"
      ],
      type: "1",
      color: "2",
      quantity: 1,
      goodsData: {},
      goodsID: "",
      time: "",
      name: "",
      price: "",
      userID: ""
    };
  },
  methods: {
    async getGoodsData() {
      let resdata = new FormData();
      resdata.append("id", this.goodsID);
      const { data: res } = await this.$http.post(
        "product/getProductById",
        resdata
      );
      if (res.code != 200) {
        console.log("错误");
      } else {
        this.goodsData = res.data[0];
        this.srcList[0] =
          "http://39.105.42.250:8080/product/image/" + this.goodsData.fileName;
        this.srcList[1] =
          "http://39.105.42.250:8080/product/image/" + this.goodsData.fileName;
        this.price = this.goodsData.price;
      }
    },
    async addCart() {
      let resData = new URLSearchParams();
      resData.append("productId", this.goodsID);
      resData.append("userId", this.userID);
      resData.append("price", this.price);
      resData.append("quantity", this.quantity);
      const { data: res } = await this.$http.post(
        "shoppingCart/insert",
        resData
      );
      if (res.code != 200) {
        console.log("添加失败");
      } else {
        location.reload();
        this.$message.success("添加成功");
      }
    }
  }
};
</script>
<style scoped>
.goodsInfo {
  display: flex;
  flex-direction: row;
  justify-content: flex-start;
  align-items: center;
}
.info {
  margin-left: 50px;
  display: flex;
  flex-direction: column;
  justify-content: space-between;
  align-items: flex-start;
}
.button {
  margin-top: 20px;
  margin-left: 430px;
  margin-bottom: 50px;
}
.el-form-item {
  width: 20%;
}
</style>