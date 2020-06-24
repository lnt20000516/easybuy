<template>
  <div>
    <div class="title">
      <p style="font-size:25px;">注册</p>
      <p>
        已有商城账号，
        <span style="color:#409EFF;cursor: pointer;" @click="toLogin()">我要登录</span>
      </p>
    </div>
    <div class="in">
      <el-form
        :model="loginData"
        :rules="rules"
        ref="loginData"
        class="demo-ruleForm"
        label-width="80px"
        label-position="left"
      >
        <el-form-item prop="userName" label="用户名">
          <el-input v-model="loginData.userName" placeholder="用户名"></el-input>
        </el-form-item>
        <el-form-item prop="password" label="密码">
          <el-input v-model="loginData.password" placeholder="密码" show-password></el-input>
        </el-form-item>
        <el-form-item prop="password2" label="确认密码">
          <el-input v-model="loginData.password2" placeholder="确认密码" show-password></el-input>
        </el-form-item>
        <el-form-item prop="name" label="真实姓名">
          <el-input v-model="loginData.name" placeholder="真实姓名"></el-input>
        </el-form-item>
        <el-form-item prop="sex" label="性别">
          <el-radio-group v-model="loginData.sex">
            <el-radio :label="1">男</el-radio>
            <el-radio :label="0">女</el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item label="身份证号" prop="cardID">
          <el-input v-model="loginData.cardID" placeholder="身份证号"></el-input>
        </el-form-item>
        <el-form-item label="邮箱" prop="email">
          <el-input v-model="loginData.email" placeholder="邮箱地址"></el-input>
        </el-form-item>
        <el-form-item label="手机号" prop="phone">
          <el-input v-model="loginData.phone" placeholder="手机号"></el-input>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="submitForm('loginData')">注册</el-button>
          <el-button @click="resetForm('loginData')">重置</el-button>
        </el-form-item>
      </el-form>
    </div>
  </div>
</template>
<script>
export default {
  data() {
    let passRuel = (rule, value, callback) => {
      if (value === "") {
        callback(new Error("请输入密码"));
      }
      let reg = /^(?![0-9]+$)(?![a-z]+$)(?![A-Z]+$)(?!([^(0-9a-zA-Z)])+$).{6,20}$/;
      if (!reg.test(value)) {
        callback(new Error("密码包含 数字,英文,字符中的两种以上，长度6-20"));
      } else {
        callback();
      }
    };
    let pass2Ruel = (rule, value, callback) => {
      if (value === "") {
        callback(new Error("请确认密码"));
      }
      if (value != this.loginData.password) {
        callback(new Error("两次密码不一致"));
      }
      callback();
    };
    let phoneRuel = (rule, value, callback) => {
      if (value === "") {
        callback();
      } else {
        let reg = /^1[3456789]\d{9}$/;
        if (!reg.test(value)) {
          callback(new Error("电话号码格式错误"));
        }
        callback();
      }
    };
    let emailRuel = (rule, value, callback) => {
      if (value === "") {
        callback();
      } else {
        var reg = /^([a-zA-Z]|[0-9])(\w|\-)+@[a-zA-Z0-9]+\.([a-zA-Z]{2,4})$/;
        if (!reg.test(value)) {
          callback(new Error("邮箱格式不正确"));
        }
        callback();
      }
    };
    let cardIDRuel = (rule, value, callback) => {
      if (value === "") {
        callback();
      } else {
        var regIdNo = /(^\d{15}$)|(^\d{18}$)|(^\d{17}(\d|X|x)$)/;
        if (!regIdNo.test(value)) {
          callback(new Error("身份证号格式错误"));
          return false;
        }
        callback();
      }
    };
    let nameRuel = (rule, value, callback) => {
      var regName = /^[\u4e00-\u9fa5]{2,10}$/;
      if (!regName.test(value)) {
        callback(new Error("真实姓名有误"));
        return false;
      }
      callback();
    };
    return {
      loginData: {
        userName: "",
        password: "",
        password2: "",
        name: "",
        sex: 1,
        cardID: "",
        email: "",
        phone: ""
      },
      rules: {
        userName: [
          { required: true, message: "请输入用户名", trigger: "blur" },
          { min: 2, max: 15, message: "长度在 2 到 15 个字符", trigger: "blur" }
        ],
        password: [
          { required: true, message: "请输入密码", trigger: "blur" },
          { validator: passRuel, trigger: "blur" }
        ],
        password2: [
          { required: true, message: "请确认密码", trigger: "blur" },
          { validator: pass2Ruel, trigger: "blur" }
        ],
        name: [
          { required: true, message: "请输入真实姓名", trigger: "blur" },
          { validator: nameRuel, trigger: "blur" }
        ],
        sex: [{ required: true, message: "请选择性别", trigger: "blur" }],
        cardID: [
          {
            validator: cardIDRuel,
            trigger: "blur"
          }
        ],
        email: [{ validator: emailRuel, trigger: "blur" }],
        phone: [{ validator: phoneRuel, trigger: "blur" }]
      }
    };
  },
  methods: {
    submitForm(formName) {
      this.$refs[formName].validate(async valid => {
        if (valid) {
          let resData = new URLSearchParams();
          resData.append("loginName", this.loginData.userName);
          resData.append("userName", this.loginData.name);
          resData.append("password", this.loginData.password);
          resData.append("sex", this.loginData.sex);
          resData.append("identityCode", this.loginData.cardID);
          resData.append("email", this.loginData.email);
          resData.append("mobile", this.loginData.phone);
          const { data: res } = await this.$http.post("user/add", resData);
          if (res.code != 200) {
            this.$message.error(res.message);
          } else {
            sessionStorage.setItem(
              "user",
              res.data[0].userName +
                "/" +
                res.data[0].email +
                "/" +
                res.data[0].mobile +
                "/" +
                res.data[0].sex +
                "/" +
                res.data[0].type
            );
            this.$message.success("注册成功");
            this.$router.replace("/home");
          }
        } else {
          console.log("错误");
        }
      });
    },
    resetForm(formName) {
      this.$refs[formName].resetFields();
    },
    toLogin() {
      this.$router.replace("/login");
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
</style>