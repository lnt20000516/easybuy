<template>
  <div>
    <div class="title">
      <p style="font-size:25px;">登录</p>
      <p>
        还没有商城账号，
        <span style="color:#409EFF;cursor: pointer;" @click="toRegister()">立即注册</span>
      </p>
    </div>
    <div class="in">
      <el-form :model="loginData" :rules="rules" ref="loginData" class="demo-ruleForm">
        <el-form-item prop="loginName">
          <el-input v-model="loginData.loginName" placeholder="用户名"></el-input>
        </el-form-item>
        <el-form-item prop="password">
          <el-input v-model="loginData.password" placeholder="密码" show-password></el-input>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="submitForm('loginData')">登录</el-button>
        </el-form-item>
      </el-form>
    </div>
  </div>
</template>
<script>
export default {
  data() {
    return {
      loginData: {
        loginName: "",
        password: ""
      },
      rules: {
        loginName: [
          { required: true, message: "请输入用户名称", trigger: "blur" }
        ],
        password: [{ required: true, message: "请输入密码", trigger: "blur" }]
      }
    };
  },
  methods: {
    submitForm(formName) {
      this.$refs[formName].validate(async valid => {
        if (valid) {
          let resData = new URLSearchParams();
          resData.append("loginName", this.loginData.loginName);
          resData.append("password", this.loginData.password);

          const { data: res } = await this.$http.post("user/login", resData);
          if (res.code != 200) {
            this.$message.error(res.message);
          } else {
            sessionStorage.setItem("user",res.data[0].userName+"/"+res.data[0].id+"/"+res.data[0].type)
            this.$message.success("登录成功");
            this.$router.replace("/home");
          }
        } else {
          console.log("error submit!!");
          return false;
        }
      });
    },
    toRegister() {
      this.$router.replace("/register");
    }
  }
};
</script>
<style scoped>
.title {
  display: flex;
  align-items: center;
  justify-content: space-between;
  padding: 0px 20px 0px 20px;
}
.in {
  margin-top: 30px;
}
.in .el-button {
  width: 100%;
}
</style>