<template>
  <div class="bg">
    <div class="contant">
      <div class="top">
        <img src="../assets/img/logo.png" @click="toHome()" />
      </div>
      <div class="main_login">
        <div class="imgbox">
          <img src="../assets/img/l_img.png" />
        </div>
        <div class="loginbox">
          <router-view></router-view>
        </div>
      </div>
    </div>
  </div>
</template>
<script>
export default {
  methods:{
    toHome(){
      this.$router.replace("/home");
    }
  }
};
</script>
<style>
.bg {
  width: 100%;
  position: absolute;
  top: 0;
  left: 0;
  bottom: 0;
  overflow-y: scroll;
  background-color: #e0eeee;
}
.contant {
  width: 80%;
  margin: 0 auto;
}
.contant .main_login {
  display: flex;
  flex-direction: row;
  align-items: center;
  justify-content: space-between;
  margin-top: 90px;
}
.contant .main_login .loginbox {
  min-width: 400px;
  width: 400px;
  height: auto;
  background-color: #fff;
  padding: 50px 50px 5px 50px;
  box-shadow: 0 0 5px #e0e0e0;
  border-radius: 5px;
  margin-bottom: 40px;
}
</style>