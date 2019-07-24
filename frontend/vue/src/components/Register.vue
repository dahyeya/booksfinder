<template>
  <div class="books-register">
    <header class="books-register-header">
        <h4>BOOK'S FINDER</h4>
        <div>회원가입</div>
    </header>
    <section class="books-register-section">
        <div>
            <label for="tos_agree" class="custom-checkbox">
                <input type="checkbox" id="tos_agree" v-model="isTosAgree">
                <span>BOOK'S FINDER 이용약관 동의</span>
                <span>(필수)</span>
                <span class="custom-checkbox-checkmark"></span>
            </label>
            <div>서비스 이용약관 표시</div>
        </div>
        <div>
            <label for="pd_agree" class="custom-checkbox">
                <input type="checkbox" id="pd_agree" v-model="isPdAgress"> 
                <span>개인정보 수집 및 이용에 대한 안내</span>
                <span>(필수)</span>
                <span class="custom-checkbox-checkmark"></span>
            </label>
            <div>개인정보 수집 및 이용에 대한 안내 표시</div> 
        </div>
    </section>
    <footer class="books-register-footer">
      <button v-on:click="agree">가입</button>
      <button v-on:click="cancel">취소</button>
    </footer>
    <template>
      <div v-if="showing" class="books-user">
        <section class="books-user-section">
          <h4>정보 입력</h4>
          <ul>
            <li>
              <span>아이디</span>
              <input type="text" v-model="account" placeholder="사용자 아이디">
              <button v-on:click="valid">검사</button>
            </li>
            <li>
              <span>비밀번호</span>
              <input type="password" v-model="password" v-on:keyup="passwordCheck" placeholder="사용자 비밀번호">
              <span></span>
            </li>
            <li>
              <span>비밀번호 확인</span>
              <input type="password" v-model="passwordValid" v-on:keyup="passwordCheck" placeholder="사용자 비밀번호 재 입력">
              <span></span>
            </li>
            <li>
              <button v-on:click="regist">완료</button>
            </li>
          </ul>
          <button v-on:click="close" v-on:keyup.delete="close"></button>
        </section>
      </div>
    </template>
  </div>
</template>
<script>
export default {
  name: 'Detail',
  data () {
    return {
      isTosAgree: false,
      isPdAgress : false,
      showing : false,
      isValid : false,
      idNotUsed : false,
      passwordEq : false
    }
  },
  methods: {
    agree () {       
      if(!this.isTosAgree){
        alert("서비스 이용약관에 동의하셔야 진행할 수 있습니다.");
      }
      if(!this.isPdAgress){
        alert("개인정보 수집 및 이용에 대한 안내를 확인하시고 동의하셔야 진행할 수 있습니다.");
      }
      this.showing = (this.isTosAgree && this.isPdAgress);
    },
    close(){
      this.isTosAgree = false;
      this.isPdAgress = false;
      this.showing = false;
    },
    cancel(){
      this.$router.replace("/")
    },
    passwordCheck(){
      if(this.password == this.passwordValid){
        this.passwordEq = true;
      }else{
        this.passwordEq = false; 
      }
    },
    valid(){
      if(!this.account || this.account==''){
        alert("아이디가 입력되지 않았습니다.")
        return;
      }
      this.$Axios.head('/api/user/'+this.account)
      .then(res => {
        this.idNotUsed = true;
        alert("사용가능한 아이디 입니다.")
      }, res => {
        this.idNotUsed = false;
        alert("사용중인 아이디 입니다.")
      })
    },
    regist(){
      if(this.account==''){
        alert("아이디가 입력되지 않았습니다.")
        return;
      }
      if(!this.idNotUsed){
        alert("먼저 아이디 중복 검사를 진행해야 합니다.");
        return;
      }
      if(!this.password || this.password==''){
        alert("비밀번호가 입력되지 않았습니다.")
        return;
      }
      if(!this.passwordEq){
        alert("입력된 비밀번호가 불일치 합니다.");
        return;
      }
      this.$Axios.post('/api/user', 'account='+this.account+'&password='+this.password)
        .then(res => {
          alert("가입 완료 이전화면으로 이동합니다.\n 로그인을 시작해주세요.")
          this.$router.go(-1);
        }, res => {
          alert("일시적 오류가 발생하였습니다.\n 잠시 후 다시 시도해 주세요");
        })
    }
  },
  created(){
    document.body.style.backgroundColor = "#FFFFFF";
  }
}
</script>
<style scope>
.books-register{
  width: 100%;
}
.books-register-header{
  position:fixed;
  top:0;
  left: 0;
  display : flex;
  flex-direction: column;
  width:100%;
  height:120px;
  align-content: center;
  justify-content: flex-start;
  text-align: center;
  z-index: 1;
  background-color: #f3db00;
}
.books-register-header>h4{
  font-weight: bold;
  letter-spacing: 2px;
  font-family: 'Nanum Gothic', sans-serif;
}
.books-register-header>div{
  width: 60%;
  max-width: 240px;
  position: absolute;
  left: 50%;
  -webkit-transform: translateX(-50%);
  transform: translateX(-50%);
  top: 72px;
  z-index: 1;
  font-weight: bold;
}
.books-register-section{
  position: fixed;
  width: 100%;
  top: 120px;
  left: 0;
  background-color: #FFFFFF;
  padding:6px 16px;
  bottom: 60px;
  overflow: auto;
}
.books-register-section>div{
  font-size: 13px;
  display: flex;
  flex-direction: column;
  margin-bottom: 18px;
}
.books-register-section>div>label{
  display: flex;
  flex-direction: row;
  padding: 8px 0;
  cursor: pointer;
  outline: none;
}
.books-register-section>div>label:active{
  outline: none;
}
.books-register-section>div>div{
  /* border: 1px solid #eaca01; */
  background-color: #EFEFEF;
  max-height: 40%;
  min-height: 200px;
  padding: 8px;  
  overflow: auto;
}
.books-register-section>div>label>span:nth-of-type(2){
  font-weight: 700;
  color: #1d1d1d;
}
.books-register-section>div>label>span:nth-of-type(2){
  color : #FF0000;
  padding : 0 4px;
  font-weight: 700
}


.books-register-footer{
  position: fixed;
  width: 100%;
  bottom: 0;
  left: 0;
  display: grid;
  grid-template-columns : 1fr 1fr;
  background-color: #f3db00;
  font-size:13px
}
.books-register-footer>button{
  padding : 12px;
  outline: none;
}
.books-register-footer>button:nth-of-type(2){
  background-color: rgba(255, 255, 255, 0.4);
}


.books-user{
  width:100%;
  height:100%;
  background-color : rgba(0,0,0,0.8);
  position: fixed;
  top: 0;
  left: 0;
  z-index: 100;
}
.books-user-section{
  position: absolute;
  top:50%;
  left:50%;
  transform: translate(-50%, -50%);
  background-color: #FFFFFF;
  padding : 8px 16px;
  background-color: #f3db00;
  font-size: 13px;
}
.books-user-section>h4{
  text-align: center;
}
.books-user-section>ul{
  list-style:none;
  padding:0;
  margin:0;
}
.books-user-section>ul>li{
  display: grid;
  grid-template-columns: 90px 1fr 40px;
  align-items: center;
}
.books-user-section>ul>li>input{
  background-color: rgba(255, 255, 255, 0.4);
  border : 1px solid #eaca01;
  padding: 6px 12px;
}
.books-user-section>ul>li:last-of-type{
  display: block;
  text-align: center;
  margin-top: 8px;
}
.books-user-section>ul>li>button{
    border: 1px solid #eaca01;
    background: rgba(255, 255, 255, 0.7);
    outline: none;
    padding: 6px 0;
    width : 30%;
    min-width:40px;
}
.books-user-section>button {
    position: absolute;
    right: 0;
    top: 0;
    width: 32px;
    height: 32px;
    opacity: .3;
}
.books-user-section>button:hover {
  opacity: 1;
}
.books-user-section>button:before, .books-user-section>button:after {
  position: absolute;
  right: 16px;
  content: " ";
  height: 18px;
  width: 2px;
  background-color: #333;
  top: 8px;
}
.books-user-section>button:before {
  transform: rotate(45deg);
}
.books-user-section>button:after {
  transform: rotate(-45deg);
}

.custom-checkbox input[type="checkbox"]{
  display: none;
}
.custom-checkbox input[type="checkbox"]:checked ~ .custom-checkbox-checkmark{
  background-color: #f3db00;
  border : 1px solid #eaca01;
}
.custom-checkbox input[type="checkbox"]:checked ~ .custom-checkbox-checkmark::after{
  content: '';
  width: calc(100% - 6px);
  height: calc(100% - 6px);
  margin: 3px;
  background-color: rgba(0,0,0,0.4);
  display: block;
}

.custom-checkbox-checkmark{
  display: inline-block;
  width:20px;
  height:20px;
  /* border: 1px solid #eaca01; */
  background-color: #EFEFEF;
  margin-left : auto;
  cursor: pointer;
}
</style>