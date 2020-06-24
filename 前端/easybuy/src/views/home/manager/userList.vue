<template>
  <div>
    <div>
      <p style="font-size:17px;margin:0px;">用户列表</p>
      <el-button type="primary" style="float:right" size="small" @click="toAddUser()">添加用户</el-button>
    </div>
    <div class="table">
      <el-table
        :data="tableData"
        border
        style="width: 100%"
        :header-cell-style="{background:'#f8f8ff'}"
      >
        <el-table-column prop="loginName" label="用户名称" align="center"></el-table-column>
        <el-table-column prop="userName" label="真实姓名" align="center"></el-table-column>
        <el-table-column prop="sex" label="性别" align="center"></el-table-column>
        <el-table-column prop="type" label="类型" align="center"></el-table-column>
        <el-table-column label="操作" align="center">
          <template slot-scope="scope">
            <el-button @click="handleEdit(scope.$index, scope.row)" type="text" size="small">编辑</el-button>
            <el-button
              @click="handleDelete(scope.$index, scope.row)"
              type="text"
              size="small"
              v-if="scope.row.type=='普通用户'?true:false"
            >删除</el-button>
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
</template>
<script>
export default {
  async created() {
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
    handleSizeChange(val) {
      this.pageSize = val;
      this.getList();
    },
    handleCurrentChange(val) {
      this.currentPage = val;
      console.log(this.currentPage);
      this.getList();
    },
    async getList() {
      const { data: res1 } = await this.$http.post("user/count");
      if (res1.code != 200) {
        this.$message.error(res1.message);
      } else {
        this.total = res1.count;
      }
      let pageSize = this.pageSize;
      let currentPage = this.currentPage;
      let index = pageSize * (currentPage - 1);
      let resData = new URLSearchParams();
      resData.append("from", index);
      resData.append("pageSize", pageSize);
      const { data: res } = await this.$http.post("user/getUserList", resData);
      if (res.code != 200) {
        this.$message.error(res.message);
      } else {
        this.tableData = res.data;
      }
      for (let i in this.tableData) {
        this.tableData[i].type =
          this.tableData[i].type === 0 ? "普通用户" : "管理员";
        this.tableData[i].sex = this.tableData[i].sex === 0 ? "女" : "男";
      }
    },
    handleEdit(index, row) {
      sessionStorage.setItem(
        "row",
        row.id +
          "/" +
          row.loginName +
          "/" +
          row.userName +
          "/" +
          row.identityCode +
          "/" +
          row.email +
          "/" +
          row.mobile +
          "/" +
          row.type
      );
      this.$router.replace("/userModify");
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
            "user/deleteUserById",
            resdata
          );
          if (res.code != 200) {
            this.$message.error("删除失败");
          } else {
            this.tableData.splice(index, 1);
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
    toAddUser() {
      this.$router.replace("/userAdd");
    }
  }
};
</script>
<style>
.table {
  margin-top: 60px;
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
  border-color: #9ac0cd;
}
.el-table--border::after,
.el-table--group::after,
.el-table::before {
  background-color: #9ac0cd;
}
</style>