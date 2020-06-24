<template>
  <div>
    <div class="steps">
      <el-steps :active="active" finish-status="success">
        <el-step title="查看购物车"></el-step>
        <el-step title="确认订单信息"></el-step>
        <el-step title="成功提交订单"></el-step>
      </el-steps>
      <div class="settlement" v-if="active==0">
        <el-table :data="settlementData" style="width: 100%">
          <el-table-column prop="product.name" label="商品名称" align="center"></el-table-column>
          <el-table-column prop="price" label="单价" align="center"></el-table-column>
          <el-table-column label="购买数量" align="center">
            <template slot-scope="scope">
              <el-input-number
                v-model="scope.row.quantity"
                @change="handleChange(scope.row.quantity,scope.row.id)"
                :min="1"
                :max="scope.row.product.stock"
                size="mini"
              ></el-input-number>
            </template>
          </el-table-column>
          <el-table-column label="小计" align="center">
            <template slot-scope="scope">
              <span>{{scope.row.price*scope.row.quantity}}</span>
            </template>
          </el-table-column>
          <el-table-column label="操作" align="center">
            <template slot-scope="scope">
              <el-button @click="handleDelete(scope.$index, scope.row)" type="text" size="small">删除</el-button>
            </template>
          </el-table-column>
        </el-table>
        <div class="operation" style="float:right">
          <p style="font-size:12px">
            商品总价
            <span style="font-size:20px;color:#EEB422">￥{{total}}</span>
          </p>
          <el-button>返回购物</el-button>
          <el-button type="primary" @click="confirmSettlement()">确认结算</el-button>
        </div>
      </div>
      <div class="order" v-if="active==1">
        <div class="orderTop">
          <p style="font-size:15px">商品列表</p>
          <el-button type="text" @click="active=0">修改</el-button>
        </div>
        <div class="list">
          <el-table :data="settlementData" style="width: 100%">
            <el-table-column prop="product.name" label="商品名称" align="center"></el-table-column>
            <el-table-column prop="quantity" label="购买数量" align="center"></el-table-column>
            <el-table-column label="小计" align="center">
              <template slot-scope="scope">
                <span>{{scope.row.price*scope.row.quantity}}</span>
              </template>
            </el-table-column>
          </el-table>
        </div>
        <div class="consignee">
          <p style="font-size:15px">收货人信息</p>
          <div class="table">
            <table border="0" class="peo_tab" style="width:1110px;" cellspacing="0" cellpadding="0">
              <tbody>
                <tr>
                  <td class="p_td" width="160">用户名称</td>
                  <td width="395">{{userData.userName}}</td>
                  <td class="p_td">登录名称</td>
                  <td>{{userData.loginName}}</td>
                </tr>
                <tr>
                  <td class="p_td">手机</td>
                  <td>{{userData.mobile}}</td>
                  <td class="p_td" width="160">电子邮件</td>
                  <td width="395">{{userData.email}}</td>
                </tr>
              </tbody>
            </table>
          </div>
        </div>
        <div class="address">
          <p style="font-size:15px">选择地址</p>
          <el-select v-model="value" placeholder="请选择">
            <el-option
              v-for="item in address"
              :key="item.id"
              :label="item.address"
              :value="item.address"
            >
              <span style="float: left">{{ item.remark }}</span>
              <span style="float: right;">{{ item.address }}</span>
            </el-option>
          </el-select>
          <div class="addAddress">
            <p style="font-size:15px">添加收货地址</p>
            <el-input v-model="addre" placeholder="地址"></el-input>
            <el-input v-model="remark" placeholder="备注"></el-input>
            <el-button type="primary" @click="addAddress()">添加</el-button>
          </div>
        </div>
        <div class="operation" style="float:right;margin:20px 0px 20px 0px">
          <p style="font-size:12px">
            商品总价
            <span style="font-size:20px;color:#EEB422">￥{{total}}</span>
          </p>
          <el-button type="primary" @click="confirmOrder()">确认订单</el-button>
        </div>
      </div>
      <div class="warning" v-if="active==2">
        <table border="0" style="width:1000px; text-align:center;" cellspacing="0" cellpadding="0">
          <tbody>
            <tr height="35">
              <td style="font-size:15px;">
                感谢您在本店购物！您的订单已提交成功，请记住您的订单号:
                <font color="#409EFF">{{this.orderID}}</font>
              </td>
            </tr>
            <tr>
              <td
                style="font-size:14px; font-family:'宋体'; padding:10px 0 20px 0; border-bottom:1px solid #b6b6b6;"
              >
                您选定的配送方式为:
                <font color="#409EFF">申通快递</font>； &nbsp; &nbsp;您选定的支付方式为:
                <font color="#409EFF">支付宝</font>； &nbsp; &nbsp;您的应付款金额为:
                <font color="#409EFF">￥{{this.total}}</font>
              </td>
            </tr>
            <tr>
              <td style="padding:20px 0 30px 0; font-family:'宋体';">
                收款人信息：全称:{{this.userData.userName}} ；地址{{this.value}} ；
                <br />
              </td>
            </tr>
            <tr>
              <td>
                <a href="/home">首页</a> &nbsp; &nbsp;
                <a href="/userInfo">用户中心</a>
              </td>
            </tr>
          </tbody>
        </table>
      </div>
    </div>
  </div>
</template>
<script>
export default {
  created() {
    this.getSettlementData();
    this.getUserData();
    this.getAddress();
  },
  data() {
    return {
      active: 0,
      total: 0,
      userId: 0,
      settlementData: [],
      userData: {},
      address: [],
      value: "",
      addre: "",
      remark: "",
      orderID:""
    };
  },

  methods: {
    async handleChange(val, id) {
      let resData = new URLSearchParams();
      resData.append("id", id);
      resData.append("quantity", val);
      const { data: res } = await this.$http.post(
        "shoppingCart/updateQuantityById",
        resData
      );
      if (res.code != 200) {
        console.log(错误);
      } else {
        console.log("成功");
        this.getSettlementData();
      }
    },
    handleDelete(index, row) {
      this.$confirm("此操作将永久删除该文件, 是否继续?", "提示", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning"
      })
        .then(async () => {
          let resdata = new URLSearchParams();
          resdata.append("id", row.id);
          const { data: res } = await this.$http.post(
            "http://39.105.42.250:8080/shoppingCart/deleteCartById",
            resdata
          );
          if (res.code != 200) {
            this.$message.error("删除失败");
          } else {
            this.settlementData.splice(index, 1);
            this.$message({
              type: "success",
              message: "删除成功!"
            });
          }
        })
        .catch(() => {
          this.$message({
            type: "info",
            message: "已取消删除"
          });
        });
    },
    next() {
      if (this.active++ > 2) this.active = 0;
    },
    confirmSettlement() {
      this.next();
    },
    async confirmOrder() {
      console.log(this.value);
      if (this.value == null || this.value == "") {
        this.$message.error("请选择地址");
        return;
      }
      let resData = new URLSearchParams();
      resData.append("userId", this.id);
      resData.append("loginName", this.userData.loginName);
      resData.append("userAddress", this.value);
      resData.append("cost", this.total);
      const { data: res } = await this.$http.post(
        "shoppingCart/submit",
        resData
      );
      if (res.code != 200) {
        this.$message.error("提交失败");
      } else {
        this.$message.success("提交成功");
        this.orderID=res.data[0].serialNumber;
        this.next();
      }
    },
    async getSettlementData() {
      this.total = 0;
      let id = sessionStorage.getItem("user");
      id = id.split("/");
      id = id[1];
      this.id = id;
      let resData = new URLSearchParams();
      resData.append("userId", id);
      const { data: res } = await this.$http.post(
        "shoppingCart/getShopperCartByUserId",
        resData
      );
      if (res.code != 200) {
        console.log("错误");
      } else {
        this.settlementData = res.data;
        this.count = this.settlementData.length;
        console.log(res.data);

        for (let i in this.settlementData) {
          this.total +=
            this.settlementData[i].price * this.settlementData[i].quantity;
          this.settlementData[i].product.fileName =
            "http://39.105.42.250:8080/product/image/" +
            this.settlementData[i].product.fileName;
        }
      }
    },
    async getUserData() {
      let resData = new URLSearchParams();
      resData.append("id", this.id);
      const { data: res } = await this.$http.post("user/getUser", resData);
      if (res.code != 200) {
        this.$message.error("系统错误");
      } else {
        this.userData = res.data[0];
      }
    },
    async getAddress() {
      let resData = new URLSearchParams();
      resData.append("userId", this.id);
      const { data: res } = await this.$http.post(
        "userAddress/queryUserAddressListByUserId",
        resData
      );
      if (res.code != 200) {
        this.$message.error("系统错误");
      } else {
        this.address = res.data;
      }
    },
    async addAddress() {
      let resData = new URLSearchParams();
      resData.append("userId", this.id);
      resData.append("address", this.addre);
      resData.append("remark", this.remark);
      const { data: res } = await this.$http.post(
        "userAddress/insert",
        resData
      );
      if (res.code != 200) {
        this.$message.error("添加失败");
      } else {
        this.$message.success("添加成功");
        this.getAddress();
        this.addre = "";
        this.remark = "";
      }
    }
  }
};
</script>
<style scoped>
.orderTop {
  display: flex;
  flex-direction: row;
  align-items: center;
  justify-content: space-between;
}
.table {
  font-size: 15px;
}
.peo_tab {
  border-collapse: collapse;
  border: 1px solid #eaeaea;
  font-family: "宋体";
  font-size: 14px;
}
tr {
  display: table-row;
  vertical-align: inherit;
  border-color: inherit;
}
table.peo_tab tr td.p_td {
  background-color: #f6f6f6;
  font-size: 14px;
  font-family: "Microsoft YaHei";
  text-align: right;
  padding-right: 20px;
  padding-left: 0;
}
.peo_tab tr td {
  border-collapse: collapse;
  border: 1px solid #eaeaea;
  padding: 10px 20px;
}
.warning {
  overflow: hidden;
  padding: 20px 0 50px 0;
  margin-bottom: 50px;
  border: 1px solid #409eff;
  display: flex;
  flex-direction: column;
  align-items: center;
  margin-top: 50px;
}
a {
  color: rgb(99, 88, 88);
  text-decoration: none;
}
.el-steps {
  margin-bottom: 80px;
}
.el-input {
  width: 200px;
  display: block;
  margin: 15px;
}
.el-button {
  width: 200px;
  margin: 0px 15px;
}
</style>