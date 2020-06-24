<template>
  <div style="width:100%;height:100%">
    <div class="contant">
      <div class="top">
        <div style="float:left">
          <a href="/LeavingBook">留言簿</a>
        </div>
        <div style="float:right">
          <p v-if="title===1">
            <a href="/userInfo">{{userName}}</a>
            <span>|</span>
            <a href="/myOrder">我的订单</a>
            <span v-show="manager">|</span>
            <a href="/userList" v-show="manager">后台管理</a>
            <span>|</span>
            <a href="#" @click="toLogin()">注销</a>
          </p>
          <p v-if="title===0" class="login">
            <span>你好，请</span>
            <a href="/login">登录</a>
            <a href="/register">免费注册</a>
          </p>
        </div>
      </div>
      <div class="main">
        <div class="mainTop">
          <img src="../assets/img/logo.png" @click="toHome()" style="margin-left:30px" />
          <div style=" display: flex;flex-direction: row;align-items: center;" v-show="showSearch">
            <el-autocomplete
              class="search-input"
              v-model="state"
              :fetch-suggestions="querySearchAsync"
              @select="handleSelect"
              style="width:600px"
            ></el-autocomplete>
            <el-button
              @click="searchProduct()"
              class="search-icon"
              type="primary"
              style="width:80px;height:38px;font-size:20px"
            >
              <i class="el-icon-search"></i>
            </el-button>
          </div>

          <div class="cart" @mouseover="showCart=true" style="cursor:default  ">
            <svg
              t="1592916089078"
              class="icon"
              viewBox="0 0 1024 1024"
              version="1.1"
              xmlns="http://www.w3.org/2000/svg"
              p-id="2150"
              width="25"
              height="25"
            >
              <path
                d="M1005.94270776 797.03077888H265.53154332c-21.94242333 0-42.52558222-8.54395221-57.86585999-24.0784111-15.3402789-15.53445888-23.88423111-35.92343666-23.69005112-57.86586112l2.71853112-545.25952c0-21.74824334-7.57304889-36.3117989-45.24411334-42.3314011h-0.19417999l-108.74121557-18.64135111c-10.48576-1.74762667-17.67044779-11.84502557-15.92281998-22.33078557 1.74762667-10.48576 11.84502557-17.67044779 22.33078442-15.92282112l108.54703446 18.64135112c51.65207666 8.15559111 77.86647666 35.34089443 77.86647666 80.7791889L222.61759999 715.47486776c0 11.45666333 4.27197667 22.13660445 12.42756779 30.09801558 8.15559111 8.15559111 18.83553223 12.62174777 30.29219555 12.62174777h740.41116445c10.67994112 0 19.41807445 8.73813333 19.41807331 19.41807445s-8.54395221 19.41807445-19.22389333 19.41807332z"
                fill="#fff"
                p-id="2151"
              />
              <path
                d="M950.60119665 685.57103445l-685.65219556-2.13598891c-21.55406222-0.19418112-41.74885888-8.54395221-56.89495665-23.88423111-15.14609778-15.3402789-23.30168889-35.53507555-23.30168889-57.08913777l2.13598777-402.73085554L962.64040333 257.5966811c35.14671445 1.35926557 62.72037888 29.90383445 62.72037888 65.0505489V611.00563c0 41.16631666-33.59326777 74.56540445-74.75958556 74.56540445zM265.33736333 644.59889778l685.45801444 2.13598776c19.80643555 0 35.92343666-16.1170011 35.92343666-35.72925554V322.64723c0-14.17519445-11.26248334-25.8260389-25.43767666-26.2144h-0.77672334l-734.58574222-54.75896888-1.74762667 361.17617778c0 11.06830222 4.27197667 21.55406222 12.03920669 29.51547221 7.37886777 7.76723001 17.86462777 12.23338667 29.1271111 12.23338667z"
                fill="#fff"
                p-id="2152"
              />
              <path
                d="M340.87367111 861.11042332m-72.42941668 0a72.42941667 72.42941667 0 1 0 144.85883335 0 72.42941667 72.42941667 0 1 0-144.85883335 0Z"
                fill="#fff"
                p-id="2153"
              />
              <path
                d="M340.87367111 952.95791445c-50.68117333 0-91.84748999-41.16631666-91.84749-91.84749113s41.16631666-91.84748999 91.84749-91.84748999c50.68117333 0 91.84748999 41.16631666 91.84748998 91.84748999s-41.16631666 91.84748999-91.84748998 91.84749113z m0-144.85883335c-29.32129223 0-53.01134222 23.69004999-53.01134223 53.01134222s23.69004999 53.01134222 53.01134223 53.01134222 53.01134222-23.69004999 53.01134222-53.01134222-23.69004999-53.01134222-53.01134222-53.01134222z"
                fill="#fff"
                p-id="2154"
              />
              <path
                d="M896.81313223 861.11042332m-72.42941668 0a72.42941667 72.42941667 0 1 0 144.85883221 0 72.42941667 72.42941667 0 1 0-144.85883221 0Z"
                fill="#fff"
                p-id="2155"
              />
              <path
                d="M896.81313223 952.95791445c-50.68117333 0-91.84748999-41.16631666-91.84749114-91.84749113s41.16631666-91.84748999 91.84749114-91.84748999 91.84748999 41.16631666 91.84748998 91.84748999-41.16631666 91.84748999-91.84748998 91.84749113z m0-144.85883335c-29.32129223 0-53.01134222 23.69004999-53.01134223 53.01134222s23.69004999 53.01134222 53.01134223 53.01134222 53.01134222-23.69004999 53.01134222-53.01134222-23.69004999-53.01134222-53.01134222-53.01134222z"
                fill="#fff"
                p-id="2156"
              />
              <path
                d="M780.30468778 426.33974556H384.56433777c-10.67994112 0-19.41807445-8.73813333-19.41807445-19.41807445s8.73813333-19.41807445 19.41807445-19.41807445h395.74035001c10.67994112 0 19.41807445 8.73813333 19.41807331 19.41807445s-8.73813333 19.41807445-19.41807331 19.41807445zM780.30468778 537.99367111H384.56433777c-10.67994112 0-19.41807445-8.73813333-19.41807445-19.41807445s8.73813333-19.41807445 19.41807445-19.41807332h395.74035001c10.67994112 0 19.41807445 8.73813333 19.41807331 19.41807332s-8.73813333 19.41807445-19.41807331 19.41807445z"
                fill="#fff"
                p-id="2157"
              />
            </svg>
            <p>购物车[{{count}}]</p>
            <svg
              t="1592916601509"
              class="icon"
              viewBox="0 0 1024 1024"
              version="1.1"
              xmlns="http://www.w3.org/2000/svg"
              p-id="3081"
              width="16"
              height="16"
            >
              <path
                d="M534.32 583.162l94.999-94.999c8.612-8.612 2.513-23.336-9.666-23.336H429.655c-12.179 0-18.278 14.725-9.666 23.336l94.999 94.999c5.339 5.338 13.994 5.338 19.332 0z"
                fill="#ffffff"
                p-id="3082"
              />
              <path
                d="M552.13 815.215l391.607-391.607c35.499-35.499 10.357-96.197-39.846-96.197H120.676c-50.203 0-75.345 60.698-39.846 96.197l391.607 391.607c22.007 22.006 57.686 22.006 79.693 0z"
                fill="#ffffff"
                p-id="3083"
              />
            </svg>
          </div>
          <div
            class="cartList"
            v-if="showCart"
            @mouseover="showCart=true"
            @mouseleave="showCart=false"
          >
            <p style="font-size:12px;margin:0 auto;margin-bottom:15px" v-if="cartTitle==1">我的购物车</p>
            <p style="font-size:12px;margin:0 auto;margin-bottom:15px" v-if="cartTitle==0">
              还未登录！
              <a href="/login">马上登录</a>
            </p>
            <div class="list" v-for="item in cartData" :key="item.id">
              <img :src="item.product.fileName" width="50" height="50" />
              <div class="goodsInfo">
                <p style="font-size:13px;color:#4d4f50">{{item.product.name}}</p>
                <p style="font-size:13px;color:#ddd">
                  <span style="font-size:13px;color:#EEB422;margin-right:2px">￥{{item.price}}</span>X
                  <span>{{item.quantity}}</span>
                </p>
              </div>
            </div>
            <div class="cz">
              <p>
                共计
                <span style="font-size:20px;color:#EEB422;margin-right:2px">￥{{total}}</span>
              </p>
              <button class="btn" @click="toPushOrder()" v-if="cartTitle==1">去结算</button>
              <button class="btn" v-if="cartTitle==0" ><a href="/login" style="color: white;">去登录</a></button>
            </div>
          </div>
        </div>
        <div class="mainContent">
          <router-view></router-view>
        </div>
      </div>
    </div>
  </div>
</template>
<script>
export default {
  created() {
    let str = sessionStorage.getItem("user");
    if (str == null) {
      this.title = 0;
      this.cartTitle = 0;
    } else {
      this.title = 1;
      this.cartTitle = 1;
      str = str.split("/");
      this.userName = str[0];
      this.manager = str[2] == 0 || str[2] == null ? false : true;
      this.id = str[1];
      this.getCart();
    }
  },
  mounted() {
    this.restaurants = [];
  },
  data() {
    return {
      cartTitle: 0,
      showSearch: true,
      id: "",
      count: 0,
      userName: "123",
      manager: true,
      title: 0,
      showCart: false,
      cartData: [],
      total: 0,
      state: "",
      timeout: null,
      product: [],
      isSearch: false
    };
  },
  methods: {
    async querySearchAsync(queryString, cb) {
      var restaurants = this.restaurants;
      var results = await this.search(queryString);
      // console.log(results);
      clearTimeout(this.timeout);
      this.timeout = setTimeout(() => {
        cb(results);
      }, 3000 * Math.random());
    },
    async search(queryString) {
      let form = new FormData();
      form.append("from", "0");
      form.append("pageSize", "10");
      form.append("proName", queryString);
      try {
        let res = await this.$http.post("product/getProductList", form);
        if (res.status === 200) {
          if (res.data.code === 200) {
            let data = res.data.data;
            this.product = data;
            data.forEach(item => {
              item.value = item.name;
            });
            return res.data.data;
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
      return [];
    },
    handleSelect(item) {
      // console.log(item);
    },
    searchProduct() {
      this.isSearch = true;
    },

    toPushOrder() {
      this.$router.replace("/pushOrder");
    },
    toLogin() {
      sessionStorage.removeItem("user");
      this.$router.replace("/login");
    },
    toHome() {
      this.$router.replace("/home");
    },
    async getCart() {
      let resData = new URLSearchParams();
      resData.append("userId", this.id);
      const { data: res } = await this.$http.post(
        "shoppingCart/getShopperCartByUserId",
        resData
      );
      if (res.code != 200) {
        console.log("错误");
      } else {
        this.cartData = res.data;
        this.count = this.cartData.length;
        for (let i in this.cartData) {
          this.total += this.cartData[i].price * this.cartData[i].quantity;
          this.cartData[i].product.fileName =
            "http://39.105.42.250:8080/product/image/" +
            this.cartData[i].product.fileName;
        }
      }
    }
  }
};
</script>
<style scoped>
* {
  padding: 0;
  margin: 0;
  border: 0;
}
a {
  text-decoration: none;
  margin: 0px 10px 0px 10px;
  color: black;
  font-size: 13px;
}
a:hover {
  color: #409eff;
}
.contant {
  width: 80%;
  margin: 0 auto;
}
.top {
  height: 30px;
}
.login {
  font-size: 13px;
}
.login a {
  color: #409eff;
  margin: 0px 10px 0px 0px;
}
.mainTop {
  width: 100%;
  height: 140px;
  background-color: #f8f8ff;
  display: flex;
  flex-direction: row;
  align-items: center;
  justify-content: space-between;
  position: relative;
}
.mainContent {
  margin-top: 30px;
}
.cart {
  display: flex;
  flex-direction: row;
  align-items: center;
  width: 125px;
  height: 30px;
  background-color: #409eff;
  color: #fff;
  justify-content: space-between;
  padding: 10px;
  margin-right: 30px;
  border-radius: 2px;
}
.cartList {
  width: 300px;
  background: #fff;
  position: absolute;
  top: 70%;
  left: 71.5%;
  padding: 20px 10px 20px 10px;
  display: flex;
  flex-direction: column;
  z-index: 1000;
  box-shadow: 0 0 5px #e0e0e0;
}
.list {
  display: flex;
  flex-direction: row;
  align-items: center;
  height: 50px;
  border-bottom: 1px solid #d1eeee;
  padding: 10px;
}
.goodsInfo {
  display: flex;
  flex-direction: column;
  align-items: flex-start;
  height: 100%;
  justify-content: space-between;
  margin-left: 20px;
}
.btn {
  width: 80px;
  height: 30px;
  background-color: #409eff;
  color: #fff;
  border-radius: 2px;
  margin-top: 5px;
  border: none;
  cursor: pointer;
}
.btn a{
  
}
.btn:focus,
.btn:active:focus,
.btn.active:focus,
.btn.focus,
.btn:active.focus,
.btn.active.focus {
  outline: none;
  border-color: transparent;
  box-shadow: none;
}
.cz {
  margin-left: 200px;
  margin-top: 10px;
}
</style>