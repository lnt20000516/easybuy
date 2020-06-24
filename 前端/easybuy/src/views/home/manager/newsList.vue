<template>
  <div>
    <div>
      <p style="font-size:17px;margin:0px;">新闻列表</p>
      <el-button
        type="primary"
        style="float:right"
        size="small"
        @click="dialogAddVisible = true"
      >添加新闻</el-button>
    </div>
    <div class="table">
      <el-table
        :data="tableData"
        border
        style="width: 100%"
        :header-cell-style="{background:'#f8f8ff'}"
      >
        <el-table-column prop="title" label="文章标题" align="center">
          <template slot-scope="scope">
            <span
              @click="handleNews(scope.row.title,scope.row.content)"
              style="cursor: pointer;"
            >{{scope.row.title}}</span>
          </template>
        </el-table-column>
        <el-table-column prop="createTime" label="创建时间" align="center"></el-table-column>
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
      <el-dialog
        :title="title"
        :visible.sync="dialogVisible"
        width="30%"
        :before-close="handleClose"
      >
        <span>{{content}}</span>
        <span slot="footer" class="dialog-footer">
          <el-button type="primary" @click="dialogVisible = false">确 定</el-button>
        </span>
      </el-dialog>
      <el-dialog title="修改新闻" :visible.sync="dialogFormVisible">
        <el-form :model="form">
          <el-form-item label="新闻标题" label-width="80px">
            <el-input v-model="form.title" autocomplete="off"></el-input>
          </el-form-item>
          <el-form-item label="新闻内容" label-width="80px">
            <el-input type="textarea" v-model="form.content"></el-input>
          </el-form-item>
        </el-form>
        <div slot="footer" class="dialog-footer">
          <el-button @click="cansol()">取 消</el-button>
          <el-button type="primary" @click="submit()">确 定</el-button>
        </div>
      </el-dialog>
      <el-dialog title="添加新闻" :visible.sync="dialogAddVisible">
        <el-form :model="form">
          <el-form-item label="新闻标题" label-width="80px">
            <el-input v-model="form2.title" autocomplete="off"></el-input>
          </el-form-item>
          <el-form-item label="新闻内容" label-width="80px">
            <el-input type="textarea" v-model="form2.content"></el-input>
          </el-form-item>
        </el-form>
        <div slot="footer" class="dialog-footer">
          <el-button @click="dialogAddVisible=false">取 消</el-button>
          <el-button type="primary" @click="submit2()">确 定</el-button>
        </div>
      </el-dialog>
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
      total: 20,
      title: "",
      content: "",
      dialogVisible: false,
      dialogFormVisible: false,
      dialogAddVisible:false,
      form: {
        title: "",
        content: "",
        time: "",
        id: ""
      },
      form2: {
        title: "",
        content: "",
        time: ""
      }
    };
  },
  methods: {
    cansol() {
      this.form = {};
      this.dialogFormVisible = false;
    },
    handleClose() {
      this.dialogVisible = false;
    },
    async submit() {
      let resdata = new URLSearchParams();
      let data = new Date();
      let year = data.getFullYear();
      let month = data.getMonth()+1;
      let day = data.getDate();
      resdata.append("id", this.form.id);
      resdata.append("title", this.form.title);
      resdata.append("content", this.form.content);
      console.log(this.form.content);
      resdata.append("createTime", year + "-" + month + "-" + day);
      const { data: res } = await this.$http.post("news/update", resdata);
      if (res.code != 200) {
        this.$message.error("修改失败");
      } else {
        this.$message({
          type: "success",
          message: "修改成功!"
        });
        this.getList();
        this.form = {};
        this.dialogFormVisible = false;
      }
    },
    async submit2() {
      let resdata = new URLSearchParams();
      let data = new Date();
      let year = data.getFullYear();
      let month = data.getMonth()+1;
      let day = data.getDate();
      resdata.append("title", this.form2.title);
      resdata.append("content", this.form2.content);
      resdata.append("createTime", year + "-" + month + "-" + day);
      const { data: res } = await this.$http.post("news/add", resdata);
      if (res.code != 200) {
        this.$message.error("添加失败");
      } else {
        this.$message({
          type: "success",
          message: "添加成功!"
        });
        this.getList();
        this.form2 = {};
        this.dialogAddVisible = false;
      }
    },
    handleNews(title, content) {
      this.title = title;
      this.content = content;
      this.dialogVisible = true;
    },
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
      const { data: res1 } = await this.$http.post("news/count");
      if (res1.code != 200) {
        this.$message.error(res1.message);
      } else {
        this.total = res1.length;
      }
      let pageSize = this.pageSize;
      let currentPage = this.currentPage;
      let index = pageSize * (currentPage - 1);
      let resData = new URLSearchParams();
      resData.append("from", index);
      resData.append("pageSize", pageSize);
      const { data: res } = await this.$http.post(
        "news/queryNewsList",
        resData
      );
      if (res.code != 200) {
        this.$message.error(res.message);
      } else {
        this.tableData = res.data;
      }
    },
    handleEdit(index, row) {
      this.form.title = row.title;
      this.form.content = row.content;
      this.form.id = row.id;
      this.dialogFormVisible = true;
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
            "news/deleteById",
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