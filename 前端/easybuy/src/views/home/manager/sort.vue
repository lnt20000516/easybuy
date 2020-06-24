<template>
  <div>
    <div>
      <p style="font-size:17px;margin:0px;">分类列表</p>
      <el-button
        type="primary"
        style="float:right"
        size="small"
        @click="dialogFormVisible=true"
      >添加分类</el-button>
    </div>
    <div class="table">
      <el-table
        :data="tableData"
        border
        style="width: 100%"
        :header-cell-style="{background:'#f8f8ff'}"
      >
        <el-table-column prop="name" label="分类名称" align="center"></el-table-column>
        <el-table-column prop="type" label="分类级别" align="center"></el-table-column>
        <el-table-column prop="parentId" label="父级分类" align="center"></el-table-column>
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
      <el-dialog title="添加分类" :visible.sync="dialogFormVisible">
        <el-form :model="sort">
          <el-form-item label="分类级别" label-width="80px">
            <el-select v-model="sort.level" placeholder="请选择分类级别" @change="selectLevel()">
              <el-option label="一级分类" value="1"></el-option>
              <el-option label="二级分类" value="2"></el-option>
              <el-option label="三级分类" value="3"></el-option>
            </el-select>
          </el-form-item>
          <el-form-item label="一级分类" label-width="80px" v-if="showOne">
            <el-select v-model="sort.level1" placeholder="请选择" @focus="getOne()">
              <el-option v-for="item in one" :key="item.id" :label="item.name" :value="item.id"></el-option>
            </el-select>
          </el-form-item>
          <el-form-item label="二级分类" label-width="80px" v-if="showTwo">
            <el-select v-model="sort.level2" placeholder="请选择" @focus="getTwo()">
              <el-option v-for="item in two" :key="item.id" :label="item.name" :value="item.id"></el-option>
            </el-select>
          </el-form-item>
          <el-form-item label="分类名称" label-width="80px">
            <el-input v-model="sort.name" autocomplete="off"></el-input>
          </el-form-item>
        </el-form>
        <div slot="footer" class="dialog-footer">
          <el-button @click="dialogFormVisible = false">取 消</el-button>
          <el-button type="primary" @click="submit()">确 定</el-button>
        </div>
      </el-dialog>
      <el-dialog title="修改分类" :visible.sync="dialogEditVisible">
        <el-form :model="editSort">
          <el-form-item label="分类级别" label-width="80px">
            <el-select v-model="editSort.level" placeholder="请选择分类级别" @change="selectLevel()">
              <el-option label="一级分类" value="1"></el-option>
              <el-option label="二级分类" value="2"></el-option>
              <el-option label="三级分类" value="3"></el-option>
            </el-select>
          </el-form-item>
          <el-form-item label="一级分类" label-width="80px" v-if="showOne">
            <el-select v-model="editSort.level1" placeholder="请选择" @focus="getOne()">
              <el-option v-for="item in one" :key="item.id" :label="item.name" :value="item.id"></el-option>
            </el-select>
          </el-form-item>
          <el-form-item label="二级分类" label-width="80px" v-if="showEditTwo">
            <el-select v-model="editSort.level2" placeholder="请选择" @focus="getTwo()">
              <el-option v-for="item in two" :key="item.id" :label="item.name" :value="item.id"></el-option>
            </el-select>
          </el-form-item>
          <el-form-item label="分类名称" label-width="80px">
            <el-input v-model="editSort.name" autocomplete="off"></el-input>
          </el-form-item>
        </el-form>
        <div slot="footer" class="dialog-footer">
          <el-button @click="dialogEditVisible = false">取 消</el-button>
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
      sort: {
        level: "",
        level1: "",
        level2: "",
        name: ""
      },
      editSort: {
        id: "",
        level: "",
        level1: "",
        level2: "",
        name: ""
      },
      showOne: true,
      showTwo: false,
      showEditTwo: false,
      one: {},
      two: {},
      EditTwo: {},
      dialogFormVisible: false,
      dialogEditVisible: false
    };
  },
  methods: {
    async submit() {
      let parentId = 0;
      console.log(this.sort.level);
      switch (this.sort.level) {
        case "1": {
          parentId = 0;
          break;
        }
        case "2": {
          parentId = this.sort.level1;
          break;
        }
        case "3": {
          parentId = this.sort.level2;
          break;
        }
      }
      let resData = new URLSearchParams();
      resData.append("name", this.sort.name);
      resData.append("parentId", parentId);
      resData.append("type", this.sort.level);
      const { data: res } = await this.$http.post(
        "productCategory/add",
        resData
      );
      if (res.code != 200) {
        this.$message.error("添加失败");
      } else {
        this.$message.success("添加成功");
        this.dialogFormVisible = false;
        this.getList();
      }
    },
    async submit2() {
      let parentId = 0;
      console.log(this.sort.level);
      switch (this.sort.level) {
        case "1": {
          parentId = 0;
          break;
        }
        case "2": {
          parentId = this.editSort.level1;
          break;
        }
        case "3": {
          parentId = this.editSort.level2;
          break;
        }
      }
      let resData = new URLSearchParams();
      resData.append("name", this.editSort.name);
      resData.append("parentId", parentId);
      resData.append("type", this.editSort.level);
      resData.append("id", this.editSort.id);
      const { data: res } = await this.$http.post(
        "productCategory/update",
        resData
      );
      if (res.code != 200) {
        this.$message.error("修改失败");
      } else {
        this.$message.success("修改成功");
        this.dialogEditVisible = false;
        this.getList();
      }
    },
    selectLevel() {
      console.log(this.editSort.level);
      switch (this.sort.level) {
        case "1": {
          this.showOne = false;
          this.showTwo = false;
          break;
        }
        case "2": {
          this.showOne = true;
          this.showTwo = false;
          break;
        }
        case "3": {
          this.showOne = true;
          this.showTwo = true;
          this.showEditTwo = true;
          break;
        }
      }
      switch (this.editSort.level) {
        case "1": {
          this.showOne = false;

          this.showEditTwo = false;
          break;
        }
        case "2": {
          this.showOne = true;

          this.showEditTwo = false;
          break;
        }
        case "3": {
          this.showOne = true;

          this.showEditTwo = true;
          break;
        }
      }
    },
    async getOne() {
      const { data: res } = await this.$http.get(
        "productCategory/queryListByParentId",
        {
          params: { parentId: 0 }
        }
      );
      if (res.code != 200) {
        console.log("错误");
      } else {
        this.one = res.data;
      }
    },
    async getTwo() {
      const { data: res } = await this.$http.get(
        "productCategory/queryListByParentId",
        {
          params: { parentId: this.sort.level1 }
        }
      );
      if (res.code != 200) {
        console.log("错误");
      } else {
        this.two = res.data;
      }
    },
    async getEditTwo() {
      const { data: res } = await this.$http.get(
        "productCategory/queryListByParentId",
        {
          params: { parentId: this.editSort.level1 }
        }
      );
      if (res.code != 200) {
        console.log("错误");
      } else {
        this.EditTwo = res.data;
      }
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
      const { data: res1 } = await this.$http.post("productCategory/count");
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
        "productCategory/queryProductCategoryList",
        resData
      );
      if (res.code != 200) {
        this.$message.error(res.message);
      } else {
        this.tableData = res.data;
      }
      for (let i in this.tableData) {
        switch (this.tableData[i].type) {
          case 1: {
            this.tableData[i].type = "一级分类";
            break;
          }
          case 2: {
            this.tableData[i].type = "二级分类";
            break;
          }
          case 3: {
            this.tableData[i].type = "三级分类";
            break;
          }
        }
        if (this.tableData[i].parentId == 0) {
          this.tableData[i].parentId = "无";
        } else {
          let resID = new URLSearchParams();
          resID.append("id", this.tableData[i].parentId);
          const { data: res2 } = await this.$http.post(
            "productCategory/queryNameById",
            resID
          );
          if (res2.code != 200) {
            console.log("错误");
          } else {
            this.tableData[i].parentId = res2.name;
          }
        }
      }
    },
    handleEdit(index, row) {
      this.editSort.id = row.id;
      this.dialogEditVisible = true;
      this.editSort.name=row.name;
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
            "productCategory/deleteById",
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
            this.getList();
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