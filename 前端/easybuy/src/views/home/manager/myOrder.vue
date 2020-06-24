<template>
  <div>
    <div>
      <p style="font-size:17px;margin-bottom:30px;">我的订单</p>
    </div>
    <div class="list" v-for="item in orderList" :key="item.id">
      <div class="listTop">
        <span>用户名:{{item.loginName}}</span>
        <a href="#">订单号:{{item.serialNumber}}</a>
        <span>地址:{{item.userAddress}}</span>
        <span>￥{{item.cost}}</span>
      </div>
      <div class="orderList">
        <el-table
          :data="item.orderDetailList"
          border
          style="width: 100%"
          :header-cell-style="{background:'#f8f8ff'}"
        >
          <el-table-column prop="product.name" label="商品名称" align="center"></el-table-column>
          <el-table-column label="商品图片" align="center">
            <template slot-scope="scope">
              <img :src="scope.row.product.fileName" width="50" height="50" />
            </template>
          </el-table-column>
          <el-table-column prop="quantity" label="数量" align="center"></el-table-column>
          <el-table-column prop="product.price" label="价格" align="center"></el-table-column>
        </el-table>
      </div>
    </div>
  </div>
</template>
<script>
export default {
  created() {
    this.getList();
  },
  data() {
    return {
      orderList: []
    };
  },
  methods: {
    async getList() {
      let id = sessionStorage.getItem("user");
      id = id.split("/");
      id = id[1];
      let resData = new URLSearchParams();
      resData.append("userId", id);
      const { data: res } = await this.$http.post(
        "order/getOrderByUserId",
        resData
      );
      if (res.code != 200) {
        console.log("错误");
      } else {
        for (let i in res.data) {
          for (let j in res.data[i].orderDetailList) {
            res.data[i].orderDetailList[j].product.fileName =
              "http://39.105.42.250:8080/product/image/" +
              res.data[i].orderDetailList[j].product.fileName;
          }
        }
        this.orderList = res.data;
      }
    }
  }
};
</script>
<style >
.list {
  width: 100%;
  border: 1px solid #9ac0cd;
  margin-top: 10px;
}
.listTop {
  height: 25px;
  background: #fff;
  display: flex;
  justify-content: space-between;
  flex-direction: row;
  align-items: center;
  padding: 5px 20px 5px 20px;
  font-size: 13px;
  color: #5c6366;
  border-bottom: 1px solid #9ac0cd;
}
.listTop a {
  color: #5c6366;
  text-decoration: none;
}
.orderList {
  padding: 10px 30px 30px 30px;
}
.el-table {
  background-color: #f8f8ff;
}
.el-table th,
.el-table tr {
  background-color: #f8f8ff;
}
.el-pager li {
  background-color: #f8f8ff;
}
.el-pagination {
  margin-top: 20px;
}
.el-pagination .el-input__inner {
  background-color: #f8f8ff;
}
.el-pagination .btn-next,
.el-pagination .btn-prev {
  background-color: #f8f8ff;
}
.el-pagination button:disabled {
  background-color: #f8f8ff;
}
.el-table td,
.el-table th.is-leaf,
.el-table--border,
.el-table--group {
  border-color: #a6acad;
}
.el-table--border::after,
.el-table--group::after,
.el-table::before {
  background-color: #a6acad;
}
</style>