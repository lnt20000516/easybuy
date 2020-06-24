<template>
  <div>
    <el-form
      :model="LeavingForm"
      :rules="rules"
      ref="LeavingForm"
      label-width="100px"
      class="demo-ruleForm"
    >
      <el-form-item label="标题" prop="title">
        <el-input v-model="LeavingForm.title"></el-input>
      </el-form-item>
      <el-form-item label="内容" prop="content">
        <el-input v-model="LeavingForm.content" type="textarea" style="width:300px;"></el-input>
      </el-form-item>
      <el-form-item label="姓名" prop="name">
        <el-input v-model="LeavingForm.name" style="width:300px;"></el-input>
      </el-form-item>
      <el-form-item label="电话" prop="phone">
        <el-input v-model="LeavingForm.phone" style="width:300px;"></el-input>
      </el-form-item>
      <el-form-item label="邮箱" prop="email">
        <el-input v-model="LeavingForm.email" style="width:300px;"></el-input>
      </el-form-item>
      <el-form-item label="地址">
        <el-input v-model="LeavingForm.address" style="width:300px;"></el-input>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" @click="submitForm('LeavingForm')">提交留言</el-button>
        <el-button @click="resetForm('LeavingForm')">重置</el-button>
      </el-form-item>
    </el-form>
  </div>
</template>
<script>
export default {
  data() {
    let CheckPhone = (rule, value, callback) => {
      if (value === "") {
        callback(new Error("请输入密码"));
      } else {
        if (!/^1[3456789]\d{9}$/.test(value)) {
          callback("手机号码有误，请重填");
        }
        callback();
      }
    };
    let CheckEmail = (rule, value, callback) => {
      if (value == "" || value == null) {
        callback();
        return;
      } else {
        if (
          !/^([a-zA-Z0-9]+[_|\_|\.]?)*[a-zA-Z0-9]+@([a-zA-Z0-9]+[_|\_|\.]?)*[a-zA-Z0-9]+\.[a-zA-Z]{2,3}$/.test(
            value
          )
        ) {
          callback("邮箱格式错误，请重填");
        }
        callback();
      }
    };
    return {
      LeavingForm: {},
      rules: {
        title: [{ required: true, message: "请输入标题", trigger: "blur" }],
        content: [{ required: true, message: "请输入内容", trigger: "blur" }],
        name: [
          { required: true, message: "请输入姓名", trigger: "blur" },
          { min: 3, max: 5, message: "长度在 3 到 5 个字符", trigger: "blur" }
        ],
        phone: [{ required: true, validator: CheckPhone, trigger: "blur" }],
        email: [{ validator: CheckEmail, trigger: "blur" }]
      }
    };
  },
  methods: {
    submitForm(formName) {
      let id = sessionStorage.getItem("user");
      if (id == null) {
        this.$alert("请先登录", {
          confirmButtonText: "确定",
          callback: action => {
            this.$router.replace("/login");
          }
        });
      } else {
        this.$refs[formName].validate(async valid => {
          if (valid) {
            id = id.split("/");
            id = id[1];
            let resData = new URLSearchParams();
            resData.append("userId", id);
            resData.append("title", this.LeavingForm.title);
            resData.append("name", this.LeavingForm.name);
            resData.append("phone", this.LeavingForm.phone);
            resData.append("content", this.LeavingForm.content);
            resData.append("email", this.LeavingForm.email);
            resData.append("address", this.LeavingForm.address);
            const { data: res } = await this.$http.post(
              "messageBook/insert",
              resData
            );
            if (res.code != 200) {
              this.$message.error("提交失败");
            } else {
              this.$message.success("提交成功");
              console.log(res);
            }
          } else {
            console.log("error submit!!");
            return false;
          }
        });
      }
    },
    resetForm(formName) {
      this.$refs[formName].resetFields();
    }
  }
};
</script>
<style scoped>
.el-input {
  width: 300px;
}
</style>