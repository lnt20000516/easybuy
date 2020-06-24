<template>
  <div>
    <div>
      <p style="font-size:17px;margin-bottom:30px;">添加商品</p>
    </div>

    <div class="from">
      <el-form
        :model="goodsForm"
        :rules="rules"
        ref="goodsForm"
        label-width="100px"
        class="demo-ruleForm"
      >
        <el-form-item label="一级分类" prop="one">
          <el-select v-model="goodsForm.category1" placeholder="请选择" @focus="getOne()">
            <el-option v-for="item in one" :key="item.id" :label="item.name" :value="item.id"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="二级分类" prop="two">
          <el-select v-model="goodsForm.category2" placeholder="请选择" @focus="getTwo()">
            <el-option v-for="item in two" :key="item.id" :label="item.name" :value="item.id"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="三级分类" prop="three">
          <el-select v-model="goodsForm.category3" placeholder="请选择" @focus="getThree()">
            <el-option v-for="item in three" :key="item.id" :label="item.name" :value="item.id"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="商品名称" prop="name">
          <el-input v-model="goodsForm.name"></el-input>
        </el-form-item>
        <el-form-item label="商品图片">
          <div class="uploadBox" prop="fc_icon">
            <input type="file" @change="doUpload" ref="inputFile" />
          </div>
        </el-form-item>
        <el-form-item label="单价" prop="price">
          <el-input v-model="goodsForm.price"></el-input>
        </el-form-item>
        <el-form-item label="库存" prop="stock">
          <el-input v-model="goodsForm.stock"></el-input>
        </el-form-item>
        <el-form-item label="描述" prop="stock">
          <el-input type="textarea" v-model="goodsForm.description" style="width:250px"></el-input>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="onSubmit">立即添加</el-button>
          <el-button @click="goodsForm={}">重置</el-button>
        </el-form-item>
      </el-form>
    </div>
  </div>
</template>
<script>
export default {
  data() {
    return {
      one: [],
      two: [],
      three: [],
      goodsForm: {
        category1: "",
        category2: "",
        category3: "",
        name: "",
        file: "",
        price: "",
        stock: "",
        description: ""
      },
      rules: {},
      categoryLogoIsShow: false
    };
  },
  methods: {
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
          params: { parentId: this.goodsForm.category1 }
        }
      );
      if (res.code != 200) {
        console.log("错误");
      } else {
        this.two = res.data;
      }
    },
    async getThree() {
      const { data: res } = await this.$http.get(
        "productCategory/queryListByParentId",
        {
          params: { parentId: this.goodsForm.category2 }
        }
      );
      if (res.code != 200) {
        console.log("错误");
      } else {
        this.three = res.data;
      }
    },
    doUpload() {
      var that = this;
      let file = this.$refs.inputFile; //正式读取文件
      this.goodsForm.file = file.files[0];
    },
    async onSubmit() {
      let resData = new FormData();
      resData.append("name", this.goodsForm.name);
      resData.append("price", this.goodsForm.price);
      resData.append("stock", this.goodsForm.stock);
      resData.append("description", this.goodsForm.description);
      resData.append("categoryLevel1Id", this.goodsForm.category1);
      resData.append("categoryLevel2Id", this.goodsForm.category2);
      resData.append("categoryLevel3Id", this.goodsForm.category3);
      resData.append("file", this.goodsForm.file);
      const { data: res } = await this.$http.post("product/add", resData);
      if (res.code != 200) {
        this.$message.error("添加失败");
      } else {
        this.$message.success("添加成功");
        this.goodsForm = {};
      }
    }
  }
};
</script>
<style scoped>
.img_item {
  display: flex;
  flex-direction: row;
  align-items: center;
}
.el-input {
  width: 250px;
}
</style>