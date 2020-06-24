<template>
  <div>
    <div>
      <p style="font-size:17px;margin:0px;">修改用户</p>
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
        <el-form-item prop="name" label="真实姓名">
          <el-input v-model="loginData.name" placeholder="真实姓名"></el-input>
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
        <el-form-item label="用户类型">
          <el-select v-model="loginData.type" placeholder="请选择">
            <el-option label="管理员" value="1"></el-option>
            <el-option label="普通用户" value="0"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="submitForm('loginData')">修改信息</el-button>
          <el-button @click="resetForm('loginData')">重置</el-button>
        </el-form-item>
      </el-form>
    </div>
  </div>
</template>
<script>
export default {
  created() {
      let str=sessionStorage.getItem("row");
      str=str.split("/");
      console.log(str);
      this.id=str[0];
      this.loginData.userName=str[1];
      this.loginData.name=str[2];
      this.loginData.cardID=str[3];
      this.loginData.email=str[4];
      this.loginData.phone=str[5];
      this.loginData.type=str[6];
  },
  data() {
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
        name: "",
        cardID: "",
        email: "",
        phone: "",
        type: ""
      },
      id:"",
      rules: {
        userName: [
          { required: true, message: "请输入用户名", trigger: "blur" },
          { min: 2, max: 15, message: "长度在 2 到 15 个字符", trigger: "blur" }
        ],
        name: [
          { required: true, message: "请输入真实姓名", trigger: "blur" },
          { validator: nameRuel, trigger: "blur" }
        ],
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
          console.log("..................");
            let resData = new URLSearchParams();
            resData.append("id", this.id);
            resData.append("loginName", this.loginData.userName);
            resData.append("userName", this.loginData.name);
            resData.append("identityCode", this.loginData.cardID);
            resData.append("email", this.loginData.email);
            resData.append("mobile", this.loginData.phone);
           switch(this.loginData.type){
               case '':case null:{
                   this.loginData.type=0;
                   break;
               }
               case '普通用户':{
                   this.loginData.type=0;
                   break;
               }
               case '管理员':{
                   this.loginData.type=1;
                   break;
               }
           }
             resData.append("type", this.loginData.type);
            const { data: res } = await this.$http.post("user/updateUserById", resData);
            if (res.code != 200) {
              this.$message.error(res.message);
            } else {
              this.$message.success("修改信息成功");
              this.$refs[formName].resetFields();
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
.in {
  margin-top: 30px;
}
.el-input {
  width: 400px;
}
</style>