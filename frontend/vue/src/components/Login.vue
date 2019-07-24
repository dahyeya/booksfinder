<template>
  <div class="books-login">
    <div class="books-login-information">
        <img src="../assets/kakaobank-wordmark.png">
        <h4>BOOK'S FINDER</h4>
        <div>
            <input type="text" v-model="account" v-on:keyup.enter="login" placeholder="아이디"/>
            <input type="password" v-model="password" v-on:keyup.enter="login" placeholder="비밀번호"/>
            <div v-if="message!=''">{{message}}</div>
            <button v-on:click="login">로그인</button>
            <button v-on:click="register">회원가입</button>
        </div>
    </div>
  </div>
</template>

<script>
export default {
  name: 'Login',
  data () {
    return {
      message : ''
    }
  },
  methods: {
    login () {
        this.message = ''
        this.$Axios.post('/api/session', 'account='+this.account+'&password='+this.password)
        .then(res => {
            this.$router.replace('/index')
        }, res => {
          this.message = '아이디 또는 비밀번호가 잘못되었습니다.'
        })
    },
    register () {
      this.$router.push("/register");
    }
  },
  created () {
    document.body.style.backgroundColor = "#f3db00";
    this.$Axios.delete('/api/session')
    .then(res => {
        console.log("session clear");
    });
  }
}
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
.books-login{
    position: relative;
}
.books-login-information{
    display : flex;
    flex-direction: column;
    width:100%;
    height:100%;
    align-content: center;
    justify-content: center;
    text-align: center;
}
.books-login-information>img{
    width: 80%;
    margin: 0 auto;
    max-width: 260px;
}
.books-login-information>h4{
    font-weight: bold;
    letter-spacing: 2px;
    font-family: 'Nanum Gothic', sans-serif;
}
.books-login-information>div{
    display: flex;
    flex-direction: column;
}
.books-login-information>div>input[type=text]{
    background-color: hsla(0,0%,100%,0.2);
    border-radius: 4px;
    border: 1px solid #EACA01;
    margin-bottom: 2px;
    padding: 12px;
}
.books-login-information>div>input[type=password]{
    background-color: hsla(0,0%,100%,0.2);
    border-radius: 4px;
    border: 1px solid #EACA01;
    padding: 12px;
    margin-bottom : 6px;
}
.books-login-information>div>button{
    border: 1px solid #eaca01;
    background: rgba(255, 255, 255, 0.7);
    outline: none;
    padding: 8px 0;
    font-size:13px;
}
.books-login-information>div>div{
    color: #FF0000;
    font-size: 13px;
    padding: 0 0 6px 0;
    /* height: 20px; */
    font-weight: 500;
}
</style>
