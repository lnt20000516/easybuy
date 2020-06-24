<template>
  <div>
    <div>
      <p style="font-size:17px;margin-bottom:30px;">商品列表</p>
    </div>
    <div class="table">
      <div class="table">
        <el-table
          :data="tableData"
          border
          style="width: 100%"
          :header-cell-style="{background:'#f8f8ff'}"
        >
          <el-table-column prop="name" label="商品名称" align="center"></el-table-column>
          <el-table-column label="商品图片" align="center">
            <template slot-scope="scope">
              <img :src="scope.row.fileName" width="50" height="50" />
            </template>
          </el-table-column>
          <el-table-column prop="stock" label="库存" align="center"></el-table-column>
          <el-table-column prop="price" label="价格" align="center"></el-table-column>
          <el-table-column label="操作" align="center">
            <template slot-scope="scope">
              <el-button @click="handleEdit(scope.$index, scope.row)" type="text" size="small">编辑</el-button>
              <el-button @click="handleDelete(scope.$index, scope.row)" type="text" size="small">删除</el-button>
            </template>
          </el-table-column>
        </el-table>
        <el-pagination
          @size-change="handleSizeChange"
          @current-change="handleCurrentChange"
          :current-page="currentPage"
          :page-sizes="[5,10,15, 20, 25, 30]"
          :page-size="pageSize"
          layout="total, sizes, prev, pager, next, jumper"
          :total="total"
        ></el-pagination>
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
      tableData: [],
      currentPage: 1,
      pageSize: 5,
      total: 20
    };
  },
  methods: {
    handleEdit(index, row) {},
    handleDelete(index, row) {},
    handleSizeChange(val) {
      this.pageSize = val;
      this.getList();
    },
    handleCurrentChange(val) {
      this.currentPage = val;
      this.getList();
    },
    async getList() {
      const { data: res1 } = await this.$http.post("product/count");
      if (res1.code != 200) {
        this.$message.error(res1.message);
      } else {
        this.total = res1.length;
      }
      let currentPage = this.currentPage;
      let pageSize = this.pageSize;
      let index = (currentPage - 1) * pageSize;
      let resData = new URLSearchParams();
      resData.append("from", index);
      resData.append("pageSize", pageSize);
      const { data: res } = await this.$http.post(
        "product/getProductList",
        resData
      );
      if (res.code != 200) {
        this.$message.error("加载商品失败");
      } else {
        this.tableData = res.data;
        for (let i in this.tableData) {
          this.tableData[i].fileName =
            "http://39.105.42.250:8080/product/image/" +
            this.tableData[i].fileName;
        }
      }
    },
    handleEdit(index, row) {
      console.log(row);
      sessionStorage.setItem(
        "goods",
        row.categoryLevel1Id +
          "*" +
          row.categoryLevel2Id +
          "*" +
          row.categoryLevel3Id +
          "*" +
          row.description +
          "*" +
          row.fileName +
          "*" +
          row.id +
          "*" +
          row.name +
          "*" +
          row.price +
          "*" +
          row.stock
      );
      this.$router.replace("/goodsEdit");
    }
  }
};
</script>
<style >
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
  border-color: #9ac0cd;
}
.el-table--border::after,
.el-table--group::after,
.el-table::before {
  background-color: #9ac0cd;
}
</style>