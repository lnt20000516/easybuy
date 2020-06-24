<template>
    <div class="infoMain">
        <div class="pictrue">
            <img src="../../../assets/img/tx.jpg" >
        </div>
        <div class="info">
            <p class="title">{{userName}}</p>
            <p>性别：{{sex}}</p>
            <p>邮箱：{{email}}</p>
            <p>电话：{{phone}}</p>
        </div>
    </div>
</template>
<script>
export default {
   async created(){
       let id=sessionStorage.getItem("user");
       id=id.split("/");
       let resData=new URLSearchParams();
       resData.append("id",id[1])
       const {data:res}=await this.$http.post("user/getUser",resData);
       if(res.code!=200){
           this.$message.error("系统错误");
       }else{
           this.userName=res.data[0].userName;
           this.sex=res.data[0].sex==0?"女":"男";
           this.email=res.data[0].email;
           this.phone=res.data[0].mobile;
       }
    },
    data(){
        return{
            userName:"",
            sex:"",
            email:"",
            phone:""
        }
    }
}
</script>
<style scoped>
.infoMain{
    display: flex;
    flex-direction: row;
    align-items: center;

}
.info{
    font-size: 15px;
    color: #4F4F4F;
    margin-left: 20px;
}
.info .title{
    color: black;
    font-size: 20px;
}
</style>